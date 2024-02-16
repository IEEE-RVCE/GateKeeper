package org.ieeervce.gatekeeper.service;

import jakarta.transaction.Transactional;
import org.ieeervce.gatekeeper.ItemNotFoundException;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

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
    public Role add(Role role){
        return roleRepository.save(role);
    }
    @Transactional
    public Role edit (Integer roleId, Role editedRole) throws ItemNotFoundException{
        Optional<Role> foundRole = roleRepository.findById(roleId);
        return foundRole.map(role -> {
            editedRole.setId(roleId);
            return roleRepository.save(editedRole);
        }).orElseThrow(()->new ItemNotFoundException(ITEM_NOT_FOUND+roleId));

    }

    public void delete(Integer roleId){
        roleRepository.deleteById(roleId);
    }
}


