package org.ieeervce.gatekeeper.dto;

/**
 * Society DTO to create/update a society.
 */
public class SocietyDTO {
    private String name;
    private String email;
    private boolean active;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
