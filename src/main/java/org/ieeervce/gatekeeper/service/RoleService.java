package org.ieeervce.gatekeeper.service;

import jakarta.transaction.Transactional;
import org.ieeervce.gatekeeper.ItemNotFoundException;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.entity.RoleValue;
import org.ieeervce.gatekeeper.entity.User;
import org.ieeervce.gatekeeper.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private RoleValue roleValue;
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
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
    @Transactional
    public Role edit (Integer roleId, Role editedRole) throws ItemNotFoundException{
        Optional<Role> foundRole = roleRepository.findById(roleId);
        return foundRole.map(role -> {
            editedRole.setId(roleId);
            return roleRepository.save(editedRole);
        }).orElseThrow(()->new ItemNotFoundException(ITEM_NOT_FOUND+roleId));

    }
    public List<Role> getAllRolesOrderedByValue()
    {
        return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "value"));
    }


    public List<Role> generateHierarchy(User u, boolean isFinance)
    {
        List<Role> rs=getAllRolesOrderedByValue();
        int val=u.getRole().getValue();

        rs.remove(roleValue.Admin.getValue());
        if(!isFinance)
        {
            rs.remove(roleValue.FinanceHead.getValue());
        }
        if(val==roleValue.MainExecom.getValue())
        {
            rs.remove(roleValue.FacultyAdvisor.getValue());
            rs.remove(roleValue.MainExecom.getValue());
            rs.remove(roleValue.SocietyExecom.getValue());


        }
        else
        {
            rs.remove(roleValue.SocietyExecom.getValue());
        }
        return rs;
    }

    public void delete(Integer roleId){
        roleRepository.deleteById(roleId);
    }
}


