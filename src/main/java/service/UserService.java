package service;

import org.springframework.stereotype.*;
import repository.UserRepository;
import Entity.User;

import java.util.*;

@Service
public class UserService {
    private UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }
    public List<User> saveUsers(List<User> users)
    {
        return  repository.saveAll(users);
    }
    public User getUserByEmail(String email)
    {
        return repository.findByEmail(email);
    }
    public User getUserByName(String name)
    {
        return repository.findByName(name);
    }

}
