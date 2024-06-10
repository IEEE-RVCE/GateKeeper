package org.ieeervce.gatekeeper.controller;

import static org.ieeervce.gatekeeper.config.SecurityConfiguration.getRequesterDetails;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.mail.MessagingException;
import org.ieeervce.gatekeeper.dto.Email.EmailDTO;
import org.ieeervce.gatekeeper.dto.RequestForm.RequestDTO;
import org.ieeervce.gatekeeper.dto.RequestForm.RequestFormPdfDTO;
import org.ieeervce.gatekeeper.dto.RequestForm.ResponseRequestFormDTO;
import org.ieeervce.gatekeeper.entity.*;
import org.ieeervce.gatekeeper.exception.InvalidDataException;
import org.ieeervce.gatekeeper.exception.ItemNotFoundException;
import org.ieeervce.gatekeeper.exception.PDFNotConversionException;
import org.ieeervce.gatekeeper.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/requestForm")

public class RequestFormController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFormController.class);

    private final ModelMapper modelMapper;

    private final RequestFormService requestFormService;

    private final RoleService roleService;
    private final UserService userService;
    private final ReviewLogService reviewLogService;
    @Autowired
    EmailService emailService ;

    PropertyMap<RequestForm, ResponseRequestFormDTO> skipReferencedFieldsMap = new PropertyMap<RequestForm, ResponseRequestFormDTO>() {
        @Override
        protected void configure() {
            skip().setFormPDF(null);
        }
    };

    RequestFormController(RequestFormService requestFormService, ModelMapper modelMapper, UserService userService,
            RoleService roleService, ReviewLogService reviewLogService) {
        this.requestFormService = requestFormService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.reviewLogService = reviewLogService;
        this.modelMapper.addMappings(skipReferencedFieldsMap);
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);

    }

    @GetMapping
    public List<RequestDTO> getAll() {
        String requesterEmail = getRequesterDetails();
        User optionalUser = userService.getUserByEmail(requesterEmail).get();
        List<RequestForm> requestFormList = new ArrayList<>();
        Integer userRoleValue=optionalUser.getRole().getValue();
        if(userRoleValue.equals(RoleValue.SocietyExecom.getValue())||userRoleValue.equals(RoleValue.FacultyAdvisor.getValue()))
        {
            Integer societyId=optionalUser.getSociety().getSocietyId();
            requestFormList=requestFormService.findRequestsBySociety(societyId);
        }
        else
        {
            requestFormList=requestFormService.list();
        }

        Type listType = new TypeToken<List<RequestDTO>>() {
        }.getType();
        return modelMapper.map(requestFormList, listType);
    }

    @GetMapping("/byRequester")
    public List<RequestDTO> getByUser() {
        List<RequestForm> requestFormList = requestFormService
                .getRequestFormByRequester(userService.getUserByEmail(getRequesterDetails()).get());
        Type listType = new TypeToken<List<RequestDTO>>() {
        }.getType();
        return modelMapper.map(requestFormList, listType);
    }

    @GetMapping("/{requestFormId}")
    public ResponseRequestFormDTO getOne(@PathVariable Long requestFormId) throws ItemNotFoundException {
        Optional<User> optionalUser = userService.getUserByEmail(getRequesterDetails());

        if (!optionalUser.isPresent()) {
            throw new ItemNotFoundException("User not found with email: " + getRequesterDetails());
        }

        User user = optionalUser.get();
        RequestForm requestForm = requestFormService.findOne(requestFormId);

        if (requestForm == null) {
            throw new ItemNotFoundException("Request form not found with ID: " + requestFormId);
        }

        ResponseRequestFormDTO responseDTO = modelMapper.map(requestForm, ResponseRequestFormDTO.class);
        List<RequestForm> pendingRequests = user.getPendingRequests();

        for (RequestForm pendingRequest : pendingRequests) {
            if (pendingRequest.getRequestFormId().equals(requestFormId)) {
                responseDTO.setActionable(true);
                break;
            }
        }

        return responseDTO;
    }

    @DeleteMapping("/{requestFormId}")
    public void deleteRequestForm(@PathVariable Long requestFormId) throws ItemNotFoundException {
        requestFormService.delete(requestFormId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseRequestFormDTO postRequestForm(@RequestParam("eventTitle") String eventTitle,
            @RequestParam("isFinance") boolean isFinance, @RequestParam("formPDF") MultipartFile formPDF)
            throws InvalidDataException, PDFNotConversionException {
        LOGGER.info("in: post request form");
        RequestForm requestForm = new RequestForm();
        requestForm.setEventTitle(eventTitle);
        requestForm.setFinance(isFinance);
        requestForm.setStatus(FinalStatus.PENDING);

        LOGGER.debug("Requester Details: {}", getRequesterDetails());
        try {
            User optionalUser = userService.getUserByEmail(getRequesterDetails()).get();
            requestForm.setRequester(optionalUser);

            requestForm.setRequestHierarchy(roleService.generateHierarchy(optionalUser, isFinance));

            userService.setPendingRequests(requestForm, requestForm.getRequestHierarchy(),
                    requestForm.getRequestIndex(), optionalUser);
        } catch (Exception e) {
            LOGGER.warn("Exception getting user and hierarchy", e);
        }

        try {
            requestForm.setFormPDF(formPDF.getBytes());
        } catch (java.io.IOException e) {
            throw new PDFNotConversionException("Could not store pdf");
        }

        RequestForm savedRequestForm = requestFormService.save(requestForm);
        return modelMapper.map(savedRequestForm, ResponseRequestFormDTO.class);// truncated
    }

    @PutMapping("/{requestFormId}")
    public ResponseRequestFormDTO editRequestForm(@RequestBody RequestDTO requestDTO, @PathVariable Long requestFormId)
            throws ItemNotFoundException {
        RequestForm editedRequestForm = modelMapper.map(requestDTO, RequestForm.class);

        return modelMapper.map(requestFormService.edit(requestFormId, editedRequestForm), ResponseRequestFormDTO.class); // truncated
    }

    @PostMapping("/{requestFormId}/approve")
    public ResponseRequestFormDTO approveRequest(@PathVariable Long requestFormId, String comment)
            throws ItemNotFoundException {
        User optionalUser = userService.getUserByEmail(getRequesterDetails()).get();
        List<RequestForm> pendingRequests=optionalUser.getPendingRequests();
        boolean canApprove=false;
        for(RequestForm requestForm:pendingRequests)
        {
            if(requestForm.getRequestFormId().equals(requestFormId))
                canApprove=true;
        }
        if(!canApprove)
        {
            throw new AccessDeniedException("User not authorized to approve this request form.");
        }

        RequestForm requestForm = requestFormService.findOne(requestFormId);

        if (requestForm.getStatus() != FinalStatus.PENDING)
            return modelMapper.map(requestForm, ResponseRequestFormDTO.class);
        int index = requestForm.getRequestIndex();
        userService.removePendingRequests(requestForm, requestForm.getRequestHierarchy(), index, optionalUser,
                StatusEnum.ACCEPTED);
        ReviewLog reviewLog = new ReviewLog();
        reviewLog.setComments(comment);
        reviewLog.setStatus(StatusEnum.ACCEPTED);
        reviewLog.setUserId(optionalUser);

        reviewLog.setRequestFormId(requestForm);

        reviewLogService.addReview(reviewLog);
        requestForm.getReviewLogs().add(reviewLog);

        //Logic for Sending Mails to Requester
        try{
            String messageBody = "Your request for Event Titled" + requestForm.getEventTitle() +" has successfully been approved by "+ optionalUser.getName() +".";
            User requester = requestForm.getRequester();
            EmailDTO emailDTO = new EmailDTO(requester,messageBody,requestForm);
            emailDTO.setSubject("Approved By "+optionalUser.getName());
            emailService.sendSimpleMail(emailDTO);
        }
        catch (MessagingException e){
            LOGGER.error("Approval Confirmation Mail To Requester Failed");
        }

        requestForm.setRequestIndex(index + 1);
        index++;
        if (index < requestForm.getRequestHierarchy().size())
            userService.setPendingRequests(requestForm, requestForm.getRequestHierarchy(), index,
                    requestForm.getRequester());
        else {
            requestForm.setStatus(FinalStatus.ACCEPTED);
            try{
                String messageBody = "Your request for Event Titled , " + requestForm.getEventTitle() +" has been approved.";
                User requester = requestForm.getRequester();
                EmailDTO emailDTO = new EmailDTO(requester,messageBody,requestForm);
                emailDTO.setSubject("Event Request Approved");
                emailService.sendSimpleMail(emailDTO);
            }
            catch (MessagingException e)
            {
                LOGGER.error("ACCEPTED Request Mail Failed");
            }
        }
        return modelMapper.map(requestFormService.save(requestForm), ResponseRequestFormDTO.class);// truncated
    }

    @PostMapping("/{requestFormId}/reject")
    public ResponseRequestFormDTO rejectRequest(@PathVariable Long requestFormId, String comment)
            throws ItemNotFoundException {

        RequestForm requestForm = requestFormService.findOne(requestFormId);
        if (requestForm.getStatus() != FinalStatus.PENDING)
            return modelMapper.map(requestForm, ResponseRequestFormDTO.class);
        User optionalUser = userService.getUserByEmail(getRequesterDetails()).get();
        List<RequestForm> pendingRequests=optionalUser.getPendingRequests();
        boolean canReject=false;
        for(RequestForm form:pendingRequests)
        {
            if(form.getRequestFormId().equals(requestFormId))
                canReject=true;
        }
        if(!canReject)
        {
            throw new AccessDeniedException("User not authorized to reject this request form.");
        }

        int index = requestForm.getRequestIndex();
        userService.removePendingRequests(requestForm, requestForm.getRequestHierarchy(), index, optionalUser,
                StatusEnum.REJECTED);
        requestForm.setRequestIndex((index + 1));
        ReviewLog reviewLog = new ReviewLog();
        reviewLog.setComments(comment);
        reviewLog.setStatus(StatusEnum.REJECTED);
        reviewLog.setUserId(optionalUser);
        reviewLog.setRequestFormId(requestForm);
        reviewLogService.addReview(reviewLog);
        requestForm.setStatus(FinalStatus.REJECTED);
        requestForm.getReviewLogs().add(reviewLog);
        try{
            String messageBody = "Your request for Event Titled , " + requestForm.getEventTitle() +" has been rejected.";
            User requester = requestForm.getRequester();
            EmailDTO emailDTO = new EmailDTO(requester,messageBody,requestForm);
            emailDTO.setSubject("Event Request Rejected");
            emailService.sendSimpleMail(emailDTO);
        }
        catch (MessagingException e){
            LOGGER.error("Failed To Send Rejection Mail");
        }
        return modelMapper.map(requestFormService.save(requestForm), ResponseRequestFormDTO.class);// truncated
    }

    @GetMapping("/pdf/{requestFormId}")
    private RequestFormPdfDTO formPdf(@PathVariable Long requestFormId) throws ItemNotFoundException {
        RequestForm requestForm = requestFormService.findOne(requestFormId);
        return modelMapper.map(requestForm, RequestFormPdfDTO.class);

    }

}
