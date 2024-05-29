package org.ieeervce.gatekeeper.controller;

import org.ieeervce.gatekeeper.exception.InvalidDataException;
import org.ieeervce.gatekeeper.exception.ItemNotFoundException;
import org.ieeervce.gatekeeper.exception.PDFNotConversionException;
import org.ieeervce.gatekeeper.dto.RequestDTO;
import org.ieeervce.gatekeeper.dto.RequestFormPdfDTO;
import org.ieeervce.gatekeeper.dto.ResponseRequestFormDTO;
import org.ieeervce.gatekeeper.entity.*;

import org.ieeervce.gatekeeper.service.RequestFormService;
import org.ieeervce.gatekeeper.service.ReviewLogService;
import org.ieeervce.gatekeeper.service.RoleService;
import org.ieeervce.gatekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;

import java.util.List;

import static org.ieeervce.gatekeeper.config.SecurityConfiguration.getRequesterDetails;

@RestController
@RequestMapping("/requestForm")


public class RequestFormController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFormController.class);

    private final ModelMapper modelMapper;

    private final RequestFormService requestFormService;

    private final RoleService roleService;
    private final UserService userService;
    private final ReviewLogService reviewLogService;
    PropertyMap<RequestForm, ResponseRequestFormDTO> skipReferencedFieldsMap = new PropertyMap<RequestForm, ResponseRequestFormDTO>() {
        @Override
        protected void configure() {
            skip().setFormPDF(null);
        }
    };

    RequestFormController(RequestFormService requestFormService, ModelMapper modelMapper, UserService userService, RoleService roleService, ReviewLogService reviewLogService) {
        this.requestFormService = requestFormService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.reviewLogService = reviewLogService;
        this.modelMapper.addMappings(skipReferencedFieldsMap);
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);


    }

    @GetMapping
    public List<RequestForm> getAll() {
        return requestFormService.list();
    }

    @GetMapping("/byRequester")
    public List<RequestFormPdfDTO> getByUser() {
        List<RequestForm> requestFormList = requestFormService.getRequestFormByRequester(userService.getUserByEmail(getRequesterDetails()).get());
        Type listType = new TypeToken<List<ResponseRequestFormDTO>>() {
        }.getType();
        return modelMapper.map(requestFormList, listType);
    }

    @GetMapping("/{requestFormId}")
    public ResponseRequestFormDTO getOne(@PathVariable Long requestFormId) throws ItemNotFoundException {

        return modelMapper.map(requestFormService.findOne(requestFormId), ResponseRequestFormDTO.class);
    }

    @DeleteMapping("/{requestFormId}")
    public void deleteRequestForm(@PathVariable Long requestFormId) throws ItemNotFoundException {
        requestFormService.delete(requestFormId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseRequestFormDTO postRequestForm(@RequestParam("eventTitle") String eventTitle, @RequestParam("isFinance") boolean isFinance, @RequestParam("formPDF") MultipartFile formPDF) throws InvalidDataException, PDFNotConversionException {
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

            userService.setPendingRequests(requestForm, requestForm.getRequestHierarchy(), requestForm.getRequestIndex(), optionalUser);
        } catch (Exception e) {
            LOGGER.warn("Exception getting user and hierarchy", e);
        }

        try {
            requestForm.setFormPDF(formPDF.getBytes());
        } catch (java.io.IOException e) {
            throw new PDFNotConversionException("Could not store pdf");
        }


        RequestForm savedRequestForm = requestFormService.save(requestForm);
        return modelMapper.map(savedRequestForm, ResponseRequestFormDTO.class);//truncated
    }

    @PutMapping("/{requestFormId}")
    public ResponseRequestFormDTO editRequestForm(@RequestBody RequestDTO requestDTO, @PathVariable Long requestFormId) throws ItemNotFoundException {
        RequestForm editedRequestForm = modelMapper.map(requestDTO, RequestForm.class);

        return modelMapper.map(requestFormService.edit(requestFormId, editedRequestForm), ResponseRequestFormDTO.class); //truncated
    }

    @PostMapping("/{requestFormId}/approve")
    public ResponseRequestFormDTO approveRequest(@PathVariable Long requestFormId, String comment) throws ItemNotFoundException {
        User optionalUser = userService.getUserByEmail(getRequesterDetails()).get();
        RequestForm requestForm = requestFormService.findOne(requestFormId);
        if (requestForm.getStatus() != FinalStatus.PENDING)
            return modelMapper.map(requestForm, ResponseRequestFormDTO.class);
        int index = requestForm.getRequestIndex();
        userService.removePendingRequests(requestForm, requestForm.getRequestHierarchy(), index, optionalUser, StatusEnum.ACCEPTED);
        ReviewLog reviewLog = new ReviewLog();
        reviewLog.setComments(comment);
        reviewLog.setStatus(StatusEnum.ACCEPTED);
        reviewLog.setUserId(optionalUser);

        reviewLog.setRequestFormId(requestForm);

        reviewLogService.addReview(reviewLog);
        requestForm.getReviewLogs().add(reviewLog);
        requestForm.setRequestIndex(index + 1);
        index++;
        if (index < requestForm.getRequestHierarchy().size())
            userService.setPendingRequests(requestForm, requestForm.getRequestHierarchy(), index, requestForm.getRequester());
        else {
            requestForm.setStatus(FinalStatus.ACCEPTED);
        }
        //TODO send mails to requester at every step and send mail to the next set of users assigned(update setPendingRequests() method to add this)
        return modelMapper.map(requestFormService.save(requestForm), ResponseRequestFormDTO.class);//truncated
    }

    @PostMapping("/{requestFormId}/reject")
    public ResponseRequestFormDTO rejectRequest(@PathVariable Long requestFormId, String comment) throws ItemNotFoundException {

        RequestForm requestForm = requestFormService.findOne(requestFormId);
        if (requestForm.getStatus() != FinalStatus.PENDING)
            return modelMapper.map(requestForm, ResponseRequestFormDTO.class);
        User optionalUser = userService.getUserByEmail(getRequesterDetails()).get();
        int index = requestForm.getRequestIndex();
        userService.removePendingRequests(requestForm, requestForm.getRequestHierarchy(), index, optionalUser, StatusEnum.REJECTED);
        requestForm.setRequestIndex((index + 1));
        ReviewLog reviewLog = new ReviewLog();
        reviewLog.setComments(comment);
        reviewLog.setStatus(StatusEnum.REJECTED);
        reviewLog.setUserId(optionalUser);
        reviewLog.setRequestFormId(requestForm);
        reviewLogService.addReview(reviewLog);
        requestForm.setStatus(FinalStatus.REJECTED);
        requestForm.getReviewLogs().add(reviewLog);
        return modelMapper.map(requestFormService.save(requestForm), ResponseRequestFormDTO.class);//truncated
        //TODO update requester with email
    }

    @GetMapping("/pdf/{requestFormId}")
    private RequestFormPdfDTO formPdf(@PathVariable Long requestFormId) throws ItemNotFoundException {
        RequestForm requestForm = requestFormService.findOne(requestFormId);
        return modelMapper.map(requestForm, RequestFormPdfDTO.class);

    }

    @GetMapping("/")
    private List<ResponseRequestFormDTO> getAllRequests() {
        List<RequestForm> requestFormList = requestFormService.getAllRequests();
        Type listType = new TypeToken<List<ResponseRequestFormDTO>>() {
        }.getType();
        return modelMapper.map(requestFormList, listType);
    }

}
