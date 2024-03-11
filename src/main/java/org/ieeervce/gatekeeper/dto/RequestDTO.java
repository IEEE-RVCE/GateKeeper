package org.ieeervce.gatekeeper.dto;

import org.ieeervce.gatekeeper.entity.FinalStatus;
import org.ieeervce.gatekeeper.entity.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class RequestDTO {
    private String eventTitle;
    private Integer requesterUserId;

    private boolean isFinance ;
    private MultipartFile formPDF ;

    private List<Role> requestHierarchy;

    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Role> getRequestHierarchy() {
        return requestHierarchy;
    }

    public void setRequestHierarchy(List<Role> requestHierarchy) {
        this.requestHierarchy = requestHierarchy;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Integer getRequesterUserId() {
        return requesterUserId;
    }

    public void setRequesterUserId(Integer requesterUserId) {
        this.requesterUserId = requesterUserId;
    }


    public boolean isFinance() {
        return isFinance;
    }

    public void setFinance(boolean finance) {
        isFinance = finance;
    }

    public MultipartFile getFormPDF() {
        return formPDF;
    }

    public void setFormPDF(MultipartFile formPDF) {
        this.formPDF = formPDF;
    }
}
