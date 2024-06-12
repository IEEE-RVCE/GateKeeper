package org.ieeervce.gatekeeper.dto.ReviewLog;

import org.ieeervce.gatekeeper.dto.User.UserInternalDTO;
import org.ieeervce.gatekeeper.entity.StatusEnum;

import java.time.LocalDateTime;

public class ReviewLogDTO {
    private UserInternalDTO user;
    private StatusEnum status;
    private String comments;
    private LocalDateTime createdAt;

    public UserInternalDTO getUser() {
        return user;
    }

    public void setUser(UserInternalDTO user) {
        this.user = user;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
