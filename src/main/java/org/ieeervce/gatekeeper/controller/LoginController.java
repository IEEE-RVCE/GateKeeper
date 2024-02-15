package org.ieeervce.gatekeeper.controller;

import org.ieeervce.gatekeeper.ItemNotFoundException;
import org.ieeervce.gatekeeper.dto.UserDTO;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.entity.Society;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.service.RoleService;
import org.ieeervce.gatekeeper.service.SocietyService;
import org.ieeervce.gatekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class LoginController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SocietyService societyService;
    private final RoleService roleService;
    @Autowired
    public LoginController(UserService userService, ModelMapper modelMapper,SocietyService societyService,RoleService roleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.societyService = societyService;
        this.roleService= roleService;
       this.modelMapper.addMappings(skipReferencedFieldsMap);
       this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }
    PropertyMap<UserDTO,User> skipReferencedFieldsMap = new PropertyMap<UserDTO, User>() {
        @Override
        protected void configure() {
            skip().setUserId(null);
        }
    };

    @GetMapping
    String login(){
        return ("register");
    }

    @PostMapping
    User registerUser(@RequestBody UserDTO userDTO){
    User user = modelMapper.map(userDTO,User.class);
        if(userDTO.getSocietyId()!=null && userDTO.getRoleId()!=null){
            try{
            Society society = societyService.findOne(userDTO.getSocietyId());
            user.setSociety(society);
            Role role =  roleService.findOne(userDTO.getRoleId());
            user.setRole(role);

            }
            catch(ItemNotFoundException e){
                //annotate it with an error code
                return user;
            }

        }
        return userService.saveUser(user);
    }
}
