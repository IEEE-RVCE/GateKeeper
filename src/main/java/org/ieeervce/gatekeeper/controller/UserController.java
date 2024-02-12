package org.ieeervce.gatekeeper.controller;

import org.ieeervce.gatekeeper.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ieeervce.gatekeeper.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping()
    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }
    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return service.getUserByEmail(email);

    }
}
