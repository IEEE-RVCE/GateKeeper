package org.ieeervce.gatekeeper.dto.User;

import org.ieeervce.gatekeeper.dto.RequestForm.RequestDTO;
import org.ieeervce.gatekeeper.dto.Role.RoleDTO;
import org.ieeervce.gatekeeper.entity.Role;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {
    private String name;
    private String email;

    private Integer societyId;
    private RoleDTO role;

    private boolean enabled;
    private boolean unsubscribed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean firstLogin;
    private List<RequestDTO> approvedRequests;
    private List<RequestDTO> pendingRequests;
    private List<RequestDTO> rejectedRequests;


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<RequestDTO> getApprovedRequests() {
        return approvedRequests;
    }

    public void setApprovedRequests(List<RequestDTO> approvedRequests) {
        this.approvedRequests = approvedRequests;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public Integer getSocietyId() {
        return societyId;
    }

    public void setSocietyId(Integer societyId) {
        this.societyId = societyId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUnsubscribed() {
        return unsubscribed;
    }

    public void setUnsubscribed(boolean unsubscribed) {
        this.unsubscribed = unsubscribed;
    }

    public List<RequestDTO> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(List<RequestDTO> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public List<RequestDTO> getRejectedRequests() {
        return rejectedRequests;
    }

    public void setRejectedRequests(List<RequestDTO> rejectedRequests) {
        this.rejectedRequests = rejectedRequests;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}


