package org.ieeervce.gatekeeper.controller;

import org.ieeervce.gatekeeper.exception.ItemNotFoundException;
import org.ieeervce.gatekeeper.dto.Role.RoleDTO;
import org.ieeervce.gatekeeper.entity.Role;
import org.ieeervce.gatekeeper.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")

public class RoleController {
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    public RoleController(RoleService roleService, ModelMapper modelMapper){
        this.roleService= roleService;
        this.modelMapper=modelMapper;
    }

    @GetMapping
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
    @GetMapping("/{roleId}")
    public Role getRole(@PathVariable Integer roleId) throws ItemNotFoundException {
         return roleService.findOne(roleId);

    }
    @PostMapping
    public Role addRole(@RequestBody RoleDTO roleDTO){
        Role role = modelMapper.map(roleDTO,Role.class);
        return roleService.add(role);
    }

    @PutMapping("/{roleId}")
    public Role editRole  (@PathVariable Integer roleId, @RequestBody RoleDTO roleDTO) throws ItemNotFoundException{
        Role role = modelMapper.map(roleDTO,Role.class);
        return roleService.edit(roleId,role);

    }

    @DeleteMapping("/{roleId}")
    public void delete(@PathVariable Integer roleId) throws ItemNotFoundException{
        roleService.delete(roleId);
    }

}
