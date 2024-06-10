package org.ieeervce.gatekeeper.dto.Email;

import org.ieeervce.gatekeeper.entity.RequestForm;
import org.ieeervce.gatekeeper.entity.User;

public class EmailDTO {
    private String recipient;
    private String messageBody;
    private String subject;
    private byte[] attachment;
    private String name;
    private String formLink;
    public String getFormLink() {
        return formLink;
    }
    public EmailDTO(User user, String messageBody,RequestForm requestForm){
        this.recipient =user.getEmail();
        this.messageBody=messageBody;
        this.name = user.getName().split(" ")[0];
        this.attachment = requestForm.getFormPDF();
        this.formLink = "gate.ieee-rvce.org/requestForm/"+requestForm.getRequestFormId();
    }
    public void setFormLink(String formLink) {
        this.formLink = formLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
}
