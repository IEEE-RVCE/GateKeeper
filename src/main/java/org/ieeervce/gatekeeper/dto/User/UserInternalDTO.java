package org.ieeervce.gatekeeper.dto.User;

import org.ieeervce.gatekeeper.dto.Role.RoleDTO;
import org.ieeervce.gatekeeper.dto.Society.SocietyDTO;

public class UserInternalDTO {
    private String name;
    private SocietyDTO society;
    private RoleDTO role;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SocietyDTO getSociety() {
        return society;
    }

    public void setSociety(SocietyDTO society) {
        this.society = society;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
