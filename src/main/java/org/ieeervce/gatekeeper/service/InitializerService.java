package org.ieeervce.gatekeeper.service;

import org.ieeervce.gatekeeper.entity.RoleValue;
import org.ieeervce.gatekeeper.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.ieeervce.gatekeeper.entity.Role;

import java.util.Map;

/**
 * Pre-populate a list of roles if not already present
 */
@Service
public class InitializerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializerService.class);
    private static final Map<String,RoleValue> INITIAL_ROLES_MAP = Map.of(
            "Society Execom", RoleValue.SocietyExecom,
            "Main Execom", RoleValue.MainExecom,
            "Faculty Advisor", RoleValue.FacultyAdvisor,
            "Finance Head", RoleValue.FinanceHead,
            "Branch Counsellor", RoleValue.BranchCounsellor,
            "Admin", RoleValue.Admin
    );
    private final RoleRepository roleRepository;

    public InitializerService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void initializeRoles() {
        LOGGER.info("Starting Role Initializer");
        for (Map.Entry<String, RoleValue> entry : INITIAL_ROLES_MAP.entrySet()) {
            createRoleIfNotFound(entry.getKey(), entry.getValue());
        }
        LOGGER.debug("Ending Role Initializer");
    }

    private void createRoleIfNotFound(String roleName, RoleValue roleEnum) {
        int value = roleEnum.getValue();
        if (roleRepository.findByRoleName(roleName).isEmpty()) {
            Role role = new Role();
            role.setRoleName(roleName);
            role.setValue(value);
            roleRepository.save(role);
        }
    }


}