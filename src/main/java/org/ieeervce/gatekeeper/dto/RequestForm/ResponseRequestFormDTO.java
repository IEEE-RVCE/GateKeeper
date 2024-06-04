package org.ieeervce.gatekeeper.dto.RequestForm;

import org.ieeervce.gatekeeper.dto.ReviewLog.ReviewLogDTO;
import org.ieeervce.gatekeeper.dto.User.UserInternalDTO;
import org.ieeervce.gatekeeper.entity.FinalStatus;
import org.ieeervce.gatekeeper.entity.Role;

import java.time.LocalDateTime;
import java.util.List;
/*
DTO for sending truncated response
RequestDTO inherits from it
*/
public class ResponseRequestFormDTO {
    private Long id;
    private String eventTitle;
    private int formValue;

    private UserInternalDTO requester;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private FinalStatus status;
    private byte[] formPDF;
    private List<Role> requestHierarchy;


    public List<Role> getRequestHierarchy() {
        return requestHierarchy;
    }

    public void setRequestHierarchy(List<Role> requestHierarchy) {
        this.requestHierarchy = requestHierarchy;
    }
    int requestIndex;

    public int getRequestIndex() {
        return requestIndex;
    }

    public void setRequestIndex(int requestIndex) {
        this.requestIndex = requestIndex;
    }

    private List<ReviewLogDTO> reviewLogs;

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

    public List<ReviewLogDTO> getReviewLogs() {
        return reviewLogs;
    }

    public void setReviewLogs(List<ReviewLogDTO> reviewLogs) {
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

