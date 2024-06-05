package org.ieeervce.gatekeeper.dto.User;

import org.ieeervce.gatekeeper.dto.Society.SocietyDTO;

public class UserInternalDTO {
    private String name;
    private SocietyDTO society;
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
}
