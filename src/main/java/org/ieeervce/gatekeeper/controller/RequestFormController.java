package org.ieeervce.gatekeeper.controller;

import jakarta.mail.Multipart;
import org.ieeervce.gatekeeper.InvalidDataException;
import org.ieeervce.gatekeeper.ItemNotFoundException;
import org.ieeervce.gatekeeper.PDFNotConversionException;
import org.ieeervce.gatekeeper.dto.RequestDTO;
import org.ieeervce.gatekeeper.entity.RequestForm;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.entity.Society;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.service.RequestFormService;
import org.ieeervce.gatekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/requestForm")
public class RequestFormController {

    private final ModelMapper modelMapper;
    private final RequestFormService requestFormService;

    private final UserService userService;
    RequestFormController(RequestFormService requestFormService, ModelMapper modelMapper, UserService userService){
        this.requestFormService = requestFormService;
        this.modelMapper= modelMapper;
        this.userService = userService;
    }

    @GetMapping
    public List<RequestForm> getAll(){
        return requestFormService.list();
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
    public RequestForm postRequestForm(@RequestParam("eventTitle") String eventTitle, @RequestParam("isFinance") boolean isFinance , @RequestParam("requesterUserId") Integer requesterUserId, @RequestParam("formPDF") MultipartFile formPDF ) throws InvalidDataException,PDFNotConversionException{
        RequestForm requestForm = new RequestForm();
        requestForm.setEventTitle(eventTitle);
        requestForm.setFinance(isFinance);

        try {
             requestForm.setFormPDF(formPDF.getBytes());
            if (requesterUserId != null) {
                User user = userService.getUserById(requesterUserId);
                requestForm.setRequester(user);
            }
        }
        catch(ItemNotFoundException e ){
            throw new InvalidDataException("Invalid user Id");
            }
        catch (java.io.IOException e){
            throw new PDFNotConversionException("Could not store pdf");
        }

        return requestFormService.save(requestForm);
    }

    @PutMapping("/{requestFormId}")
    public RequestForm editRequestForm(@RequestBody RequestDTO requestDTO,@PathVariable Long requestFormId) throws ItemNotFoundException{
        RequestForm editedRequestForm = modelMapper.map(requestDTO, RequestForm.class);
        return requestFormService.edit(requestFormId, editedRequestForm);
    }
}
