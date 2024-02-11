package controller;

import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/addUser")

    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }
    @GetMapping("/getUser")

    public User getUserByEmail(String email) {
        return service.getUserByEmail(email);
    }
}
