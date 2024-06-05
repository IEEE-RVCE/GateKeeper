package org.ieeervce.gatekeeper.dto.Role;

public class RoleDTO {
    private int value;
    private String roleName;

    public int getValue() {
        return value;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String role) {
        this.roleName = role;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
