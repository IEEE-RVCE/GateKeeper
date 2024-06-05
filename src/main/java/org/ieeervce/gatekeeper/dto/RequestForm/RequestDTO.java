package org.ieeervce.gatekeeper.dto.RequestForm;

import org.ieeervce.gatekeeper.dto.User.UserInternalDTO;
import org.ieeervce.gatekeeper.entity.FinalStatus;

import java.time.LocalDateTime;

/*
DTO for sending truncated response
RequestDTO inherits from it
Used for truncating Get all request forms and request form by requester.
*/

public class RequestDTO {
    private Long id;
    private String eventTitle;


    private UserInternalDTO requester;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private FinalStatus status;

    private boolean isFinance;


    public boolean isFinance() {
        return isFinance;
    }

    public void setFinance(boolean finance) {
        isFinance = finance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public UserInternalDTO getRequester() {
        return requester;
    }

    public void setRequester(UserInternalDTO requester) {
        this.requester = requester;
    }


    public FinalStatus getStatus() {
        return status;
    }

    public void setStatus(FinalStatus status) {
        this.status = status;
    }




    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }



}

