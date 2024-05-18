package org.ieeervce.gatekeeper.service;

import org.ieeervce.gatekeeper.entity.RoleValue;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.repository.RoleRepository;
import org.ieeervce.gatekeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ieeervce.gatekeeper.entity.Role;

import java.math.BigInteger;

@Service
public class InitializerService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public void initializeRoles() {
        createRoleIfNotFound("Society Execom", RoleValue.SocietyExecom.getValue());
        createRoleIfNotFound("Main Execom", RoleValue.MainExecom.getValue());
        createRoleIfNotFound("Faculty Advisor", RoleValue.FacultyAdvisor.getValue());
        createRoleIfNotFound("Finance Head", RoleValue.FinanceHead.getValue());
        createRoleIfNotFound("Branch Counsellor", RoleValue.BranchCounsellor.getValue());
        createRoleIfNotFound("Admin", RoleValue.Admin.getValue());

    }

    private void createRoleIfNotFound(String roleName,int value) {
        if (!roleRepository.findByRoleName(roleName).isPresent()) {
            Role role = new Role();
            role.setRoleName(roleName);
            role.setValue(value);
            roleRepository.save(role);
        }
    }


}