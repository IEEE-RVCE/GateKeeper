package org.ieeervce.gatekeeper.service;

import jakarta.mail.MessagingException;
import org.ieeervce.gatekeeper.controller.RequestFormController;
import org.ieeervce.gatekeeper.dto.Email.EmailDTO;
import org.ieeervce.gatekeeper.exception.ItemNotFoundException;
import org.ieeervce.gatekeeper.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.ieeervce.gatekeeper.repository.UserRepository;


import java.util.*;


@Service
public class UserService {

    private RoleValue roleValue;
    private final String ITEM_NOT_FOUND = "User Id not found";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    @Autowired
    EmailService emailService ;

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
            String messageBody ="Request for approval for the event titled " + requestForm.getEventTitle() + ", submitted by " + requestForm.getRequester().getName() + ".";
            EmailDTO emailDTO = new EmailDTO(u,messageBody,requestForm);
            emailDTO.setSubject("Mail For Approval - IEEE Event");
            try{
            emailService.sendSimpleMail(emailDTO);
            }
            catch (MessagingException e){
                LOGGER.error("Failed To Send Mail For Approval");
            }
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