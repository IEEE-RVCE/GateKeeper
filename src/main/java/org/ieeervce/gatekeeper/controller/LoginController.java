package org.ieeervce.gatekeeper.controller;

import org.ieeervce.gatekeeper.dto.UserDTO;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class LoginController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public LoginController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    String login(){
        return ("register");
    }

    @PostMapping
    User registerUser(@RequestBody UserDTO userDTO){
        User user = modelMapper.map(userDTO,User.class);
        return userService.saveUser(user);
    }
}
