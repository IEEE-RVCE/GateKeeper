package org.ieeervce.gatekeeper.controller;

import jakarta.mail.MessagingException;
import org.ieeervce.gatekeeper.dto.User.UserResponseDTO;
import org.ieeervce.gatekeeper.exception.IncorrectPasswordException;
import org.ieeervce.gatekeeper.exception.InvalidDataException;
import org.ieeervce.gatekeeper.exception.ItemNotFoundException;
import org.ieeervce.gatekeeper.dto.User.UserDTO;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.entity.Society;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.service.EmailService;
import org.ieeervce.gatekeeper.service.RoleService;
import org.ieeervce.gatekeeper.service.SocietyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.ieeervce.gatekeeper.service.UserService;

import java.util.Optional;

import static org.ieeervce.gatekeeper.config.SecurityConfiguration.getRequesterDetails;

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
    @Autowired
    EmailService emailService;

    @PostMapping
    User addUser(@RequestBody UserDTO userDTO) throws InvalidDataException, MessagingException {
            User user = modelMapper.map(userDTO,User.class);
            user.setEmail(user.getEmail().trim());
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
         User savedUser = userService.saveUser(user);
         emailService.sendUserCredentials(userDTO.getName(), userDTO.getEmail(),userDTO.getPassword());
         return savedUser;
    }
    @GetMapping
    public String getUser()
    {
        return getRequesterDetails();
    }
    @GetMapping("/{email}")
    public UserResponseDTO getUserByEmail(@PathVariable String email) throws ItemNotFoundException{
        Optional<User> userOptional = userService.getUserByEmail(email);
        return userOptional
                .map(user->modelMapper.map(user, UserResponseDTO.class))
                .orElseThrow(()->new ItemNotFoundException("User Not Found"));
    }
    @PutMapping()
    public ResponseEntity<?> userPasswordUpdate(String currentPassword, String newPassword) throws IncorrectPasswordException
    {
        User user= userService.getUserByEmail(getRequesterDetails()).get();
        if(!passwordEncoder.matches(currentPassword,user.getPassword()))
            throw new IncorrectPasswordException("Wrong password");
        userService.updatePassword(user,passwordEncoder.encode(newPassword));

        return ResponseEntity.ok().build();
    }

}
