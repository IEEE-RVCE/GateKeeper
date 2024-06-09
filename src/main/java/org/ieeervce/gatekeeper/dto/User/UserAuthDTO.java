package org.ieeervce.gatekeeper.dto.User;
import org.ieeervce.gatekeeper.dto.Role.RoleDTO;
import org.ieeervce.gatekeeper.dto.Society.SocietyDTO;

public class UserAuthDTO {
    private String name;
    private String email;
    private RoleDTO role;
    private SocietyDTO society;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public SocietyDTO getSociety() {
        return society;
    }

    public void setSociety(SocietyDTO society) {
        this.society = society;
    }
}
