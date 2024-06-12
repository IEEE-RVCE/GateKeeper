package org.ieeervce.gatekeeper.service;

import org.ieeervce.gatekeeper.exception.ItemNotFoundException;
import org.ieeervce.gatekeeper.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.ieeervce.gatekeeper.repository.UserRepository;


import java.util.*;


@Service
public class UserService {

    private RoleValue roleValue;
    private final String ITEM_NOT_FOUND = "User Id not found";

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> saveUsers(List<User> users) {
        return repository.saveAll(users);
    }

    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User getUserByName(String name) {
        return repository.findByName(name);
    }

    public User getUserById(Integer userId) throws ItemNotFoundException {
        return repository.findById(userId).orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND + userId));
    }

    public List<User> getUsersByRoleAndSociety(Role role, Society society) {
        return repository.findByRoleAndSociety(role, society);
    }

    public List<User> getUsersByRole(Role role) {
        return repository.findByRole(role);
    }


    public void setPendingRequests(RequestForm requestForm, List<Role> requestHierarchy, int requestIndex, User user) {
        Role role = requestHierarchy.get(requestIndex);

        List<User> users;
        if (role.getValue() != RoleValue.FacultyAdvisor.getValue()) {
            users = getUsersByRole(role);
        } else {
            users = getUsersByRoleAndSociety(role, user.getSociety());
        }
        for (User u : users) {
            u.getPendingRequests().add(requestForm);
        }
    }

    public void removePendingRequests(RequestForm requestForm, List<Role> requestHierarchy, int requestIndex, User user, StatusEnum statusEnum) {
        Role role = requestHierarchy.get(requestIndex);

        List<User> users;
        if (role.getValue() != RoleValue.FacultyAdvisor.getValue()) {
            users = getUsersByRole(role);
        } else {
            users = getUsersByRoleAndSociety(role, user.getSociety());
        }
        for (User u : users) {
            u.getPendingRequests().remove(requestForm);
            if (statusEnum.equals(StatusEnum.ACCEPTED)) {
                u.getApprovedRequests().add(requestForm);
            } else {

                u.getRejectedRequests().add(requestForm);

            }
        }
    }

    public void updatePassword(User user, String newEncodedPassword) {
        user.setPassword(newEncodedPassword);
        user.setFirstLogin(false);
        repository.save(user);
    }
}