package org.ieeervce.gatekeeper.controller;

import org.ieeervce.gatekeeper.exception.InvalidDataException;
import org.ieeervce.gatekeeper.exception.ItemNotFoundException;
import org.ieeervce.gatekeeper.dto.UserDTO;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.entity.Society;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.service.RoleService;
import org.ieeervce.gatekeeper.service.SocietyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.ieeervce.gatekeeper.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SocietyService societyService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    PropertyMap<UserDTO,User> skipReferencedFieldsMap = new PropertyMap<UserDTO, User>() {
        @Override
        protected void configure() {
            skip().setUserId(null);
        }
    };

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper,SocietyService societyService,RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.societyService = societyService;
        this.roleService= roleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper.addMappings(skipReferencedFieldsMap);
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @PostMapping
    User addUser(@RequestBody UserDTO userDTO) throws InvalidDataException {
        User user = modelMapper.map(userDTO,User.class);
             user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            try {
                if(userDTO.getSocietyId()!=null) {
                    Society society = societyService.findOne(userDTO.getSocietyId());
                    user.setSociety(society);
                }
                Role role = roleService.findOne(userDTO.getRoleId());
                user.setRole(role);
            }
            catch (ItemNotFoundException e){
                throw new InvalidDataException("Invalid Data");
            }



        return userService.saveUser(user);
    }

    @GetMapping("/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) throws ItemNotFoundException{
        Optional<User> userOptional = userService.getUserByEmail(email);
        return userOptional
                .map(user->modelMapper.map(user, UserDTO.class))
                .orElseThrow(()->new ItemNotFoundException("User Not Found"));
    }

}
