package org.ieeervce.gatekeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.ieeervce.gatekeeper.repository.UserRepository;
import org.ieeervce.gatekeeper.entity.User;

import java.util.*;

@Service
public class UserService {

    private final UserRepository repository;
    @Autowired
    public UserService(UserRepository repository){
        this.repository= repository;
    }

    public User saveUser(User user) {
        return repository.save(user);
    }
    public List<User> saveUsers(List<User> users)
    {
        return  repository.saveAll(users);
    }
    public Optional<User> getUserByEmail(String email)
    {
        return repository.findByEmail(email);
    }
    public  User getUserByName(String name)
    {
        return repository.findByName(name);
    }



}
