package org.ieeervce.gatekeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class RequestForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventTitle;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int formValue;


    @ManyToOne
    @JoinColumn(name = "requester",referencedColumnName = "userId")
    private User requester;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @Enumerated(EnumType.STRING)
    private FinalStatus status;
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column( nullable = false,columnDefinition = "LONGBLOB")
    @JsonIgnore
    private byte[] formPDF;

    @ManyToMany
    @JoinTable(
            name = "request_form_roles",
            joinColumns = @JoinColumn(name = "request_form_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> requestHierarchy;


    public List<Role> getRequestHierarchy() {
        return requestHierarchy;
    }

    public void setRequestHierarchy(List<Role> requestHierarchy) {
        this.requestHierarchy = requestHierarchy;
    }

    @Column(nullable = false)
    int requestIndex;

    public int getRequestIndex() {
        return requestIndex;
    }

    public void setRequestIndex(int requestIndex) {
        this.requestIndex = requestIndex;
    }

    @ManyToMany(mappedBy = "formId", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewLog> reviewLogs;

    @Column(nullable = false)
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

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }


    public FinalStatus getStatus() {
        return status;
    }

    public void setStatus(FinalStatus status) {
        this.status = status;
    }

    public void setFormValue(int formValue) {
        this.formValue = formValue;
    }

    public int getFormValue() {
        return formValue;
    }

    public byte[] getFormPDF() {
        return formPDF;
    }

    public void setFormPDF(byte[] formPDF) {
        this.formPDF = formPDF;
    }

    public List<ReviewLog> getReviewLogs() {
        return reviewLogs;
    }

    public void setReviewLogs(List<ReviewLog> reviewLogs) {
        this.reviewLogs = reviewLogs;
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
