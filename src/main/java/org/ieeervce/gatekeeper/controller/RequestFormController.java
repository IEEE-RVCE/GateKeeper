package org.ieeervce.gatekeeper.controller;

import jakarta.mail.Multipart;
import org.ieeervce.gatekeeper.InvalidDataException;
import org.ieeervce.gatekeeper.ItemNotFoundException;
import org.ieeervce.gatekeeper.PDFNotConversionException;
import org.ieeervce.gatekeeper.dto.RequestDTO;
import org.ieeervce.gatekeeper.entity.*;

import org.ieeervce.gatekeeper.service.RequestFormService;
import org.ieeervce.gatekeeper.service.RoleService;
import org.ieeervce.gatekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

import java.awt.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.ieeervce.gatekeeper.config.SecurityConfiguration.getRequesterDetails;

@RestController
@RequestMapping("/requestForm")
public class RequestFormController {

    private final ModelMapper modelMapper;
    private final RequestFormService requestFormService;

    private final RoleService roleService;
    private final UserService userService;
    RequestFormController(RequestFormService requestFormService, ModelMapper modelMapper, UserService userService, RoleService roleService){
        this.requestFormService = requestFormService;
        this.modelMapper= modelMapper;
        this.userService = userService;
        this.roleService=roleService;
    }

    @GetMapping
    public List<RequestForm> getAll(){
        return requestFormService.list();
    }

    @GetMapping("/byRequester")
    public List<RequestForm> getByUser()
    {
        return requestFormService.getRequestFormByRequester(userService.getUserByEmail(getRequesterDetails()).get());
    }
    @GetMapping("/{requestFormId}")
    public RequestForm getOne(@PathVariable Long requestFormId) throws ItemNotFoundException {
        return requestFormService.findOne(requestFormId);
    }

    @DeleteMapping("/{requestFormID}")
    public void deleteRequestForm(@PathVariable Long requestFormId) throws ItemNotFoundException{
        requestFormService.delete(requestFormId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RequestForm postRequestForm(@RequestParam("eventTitle") String eventTitle, @RequestParam("isFinance") boolean isFinance ,  @RequestParam("formPDF") MultipartFile formPDF ) throws InvalidDataException,PDFNotConversionException{
        System.out.println("in");
        RequestForm requestForm = new RequestForm();
        requestForm.setEventTitle(eventTitle);
        requestForm.setFinance(isFinance);
        requestForm.setStatus(FinalStatus.PENDING);


        System.out.println(getRequesterDetails());
        try {
            User optionalUser = userService.getUserByEmail(getRequesterDetails()).get();
            requestForm.setRequester(optionalUser);


            requestForm.setRequestHierarchy(roleService.generateHierarchy(optionalUser,isFinance));

        } catch (Exception e) {

            e.printStackTrace();
        }

        try {
             requestForm.setFormPDF(formPDF.getBytes());
//            if (requesterUserId != null) {
//                User user = userService.getUserById(requesterUserId);
//                requestForm.setRequester(user);
//            }
        } catch (java.io.IOException e){
            throw new PDFNotConversionException("Could not store pdf");
        }
      // Role rl=requestForm.getRequestHierarchy().get(requestForm.getRequestIndex());

        return requestFormService.save(requestForm);
    }

    @PutMapping("/{requestFormId}")
    public RequestForm editRequestForm(@RequestBody RequestDTO requestDTO,@PathVariable Long requestFormId) throws ItemNotFoundException{
        RequestForm editedRequestForm = modelMapper.map(requestDTO, RequestForm.class);

        return requestFormService.edit(requestFormId, editedRequestForm);
    }

}
