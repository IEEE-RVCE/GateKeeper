package org.ieeervce.gatekeeper.dto;

import org.ieeervce.gatekeeper.entity.StatusEnum;

public class ReviewLogDTO {
    private UserReviewLogDTO user;
    private StatusEnum status;
    private String comments;

    public UserReviewLogDTO getUser() {
        return user;
    }

    public void setUser(UserReviewLogDTO user) {
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
}
