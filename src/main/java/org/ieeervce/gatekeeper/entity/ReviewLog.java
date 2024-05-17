package org.ieeervce.gatekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ReviewLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    @JsonIgnore
    private User userId;
    @ManyToOne
    @JoinColumn(name = "formId",referencedColumnName = "id")
    @JsonIgnore
    private RequestForm formId;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(nullable = true)
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public RequestForm getFormId() {
        return formId;
    }

    public void setFormId(RequestForm formId) {
        this.formId = formId;
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
