package org.ieeervce.gatekeeper.dto;


import org.ieeervce.gatekeeper.entity.RequestForm;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {
    private String name;
    private String email;

    private String password;
    private Integer societyId;
    private Integer roleId;

    private Long number;
    private boolean enabled;
    private boolean unsubscribed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<RequestForm> approvedRequests;
    private List<RequestForm> pendingRequests;
    private List<RequestForm> rejectedRequests;


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<RequestForm> getApprovedRequests() {
        return approvedRequests;
    }

    public void setApprovedRequests(List<RequestForm> approvedRequests) {
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

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<RequestForm> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(List<RequestForm> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public List<RequestForm> getRejectedRequests() {
        return rejectedRequests;
    }

    public void setRejectedRequests(List<RequestForm> rejectedRequests) {
        this.rejectedRequests = rejectedRequests;
    }
}
