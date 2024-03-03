package org.ieeervce.gatekeeper.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false, columnDefinition = "integer default 0")
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
    @Lob @Basic(fetch = FetchType.EAGER)
    @Column( nullable = false,columnDefinition = "LONGBLOB")
    private byte[] formPDF;

    @OneToMany(mappedBy = "formId", cascade = CascadeType.ALL)
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
