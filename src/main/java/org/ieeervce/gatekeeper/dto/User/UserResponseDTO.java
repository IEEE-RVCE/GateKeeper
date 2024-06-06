package org.ieeervce.gatekeeper.dto.User;

import org.ieeervce.gatekeeper.dto.RequestForm.RequestDTO;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {
    private String name;
    private String email;

    private Integer societyId;
    private Integer roleId;

    private boolean enabled;
    private boolean unsubscribed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
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


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
    public void sortPendingRequestsByDate() {
        if (pendingRequests != null) {
            pendingRequests.sort((r2, r1) -> r1.getCreatedAt().compareTo(r2.getCreatedAt()));
        }
    }
}


