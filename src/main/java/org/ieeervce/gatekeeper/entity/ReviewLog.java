package org.ieeervce.gatekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class ReviewLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long reviewLogId;
    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "requestFormId",referencedColumnName = "requestFormId")
    @JsonIgnore
    private RequestForm requestFormId;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(nullable = true)
    private String comments;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Long getReviewLogId() {
        return reviewLogId;
    }

    public void setReviewLogId(Long reviewLogId) {
        this.reviewLogId = reviewLogId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public RequestForm getRequestFormId() {
        return requestFormId;
    }

    public void setRequestFormId(RequestForm formId) {
        this.requestFormId = formId;
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
