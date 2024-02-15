package org.ieeervce.gatekeeper.service;

import org.ieeervce.gatekeeper.ItemNotFoundException;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private static final String ITEM_NOT_FOUND="Role ID Not Found";
    private final RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository= roleRepository;
}
    public Role findOne(Integer roleId) throws ItemNotFoundException {
        return roleRepository.findById(roleId).orElseThrow(()->new ItemNotFoundException(ITEM_NOT_FOUND + roleId));
    }
}


