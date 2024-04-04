package org.ieeervce.gatekeeper.service;

import org.ieeervce.gatekeeper.ItemNotFoundException;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.entity.Society;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.ieeervce.gatekeeper.repository.UserRepository;
import org.ieeervce.gatekeeper.entity.User;

import java.util.*;

@Service
public class UserService {
    private final String ITEM_NOT_FOUND = "User Id not found";

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
    public User getUserById(Integer userId) throws ItemNotFoundException {
        return repository.findById(userId).orElseThrow(()-> new ItemNotFoundException(ITEM_NOT_FOUND+userId));
    }
    public List<User> getUsersByRoleAndSociety(Role role, Society society)
    {
        return repository.findByRoleAndSociety(role,society);
    }


}