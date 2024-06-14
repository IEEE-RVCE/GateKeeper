package org.ieeervce.gatekeeper.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.ieeervce.gatekeeper.dto.Email.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {
    //in this implementation the unchecked exception MailException has not been handled, if anything breaks do check this out too
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}") private String sender;
    @Async
    public void sendUserCredentials(String userEmail,String userPassword) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject("GATEKEEPER USER CREDENTIALS");
        simpleMailMessage.setText("EMAIL: " + userEmail + "\nPASSWORD: "+userPassword);
        javaMailSender.send(simpleMailMessage);
    }
    @Async
    public void sendSimpleMail(EmailDTO emailDetails) throws MessagingException{

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper ;

                mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
                mimeMessageHelper.setFrom(sender);
                mimeMessageHelper.setTo(emailDetails.getRecipient());
                mimeMessageHelper.setSubject(emailDetails.getSubject());
                //Setting context of variables for the html file
                Context context = new Context();
                context.setVariable("header",emailDetails.getSubject());
                context.setVariable("name",emailDetails.getName().split(" ")[0]);
                context.setVariable("messageBody",emailDetails.getMessageBody());
                context.setVariable("linkUrl",emailDetails.getFormLink());

                String htmlContent = templateEngine.process("emailTemplate",context);
                mimeMessageHelper.setText(htmlContent,true);
                mimeMessageHelper.addAttachment("Event PDF.pdf",new ByteArrayResource(emailDetails.getAttachment()));
                javaMailSender.send(mimeMessage);

    }
}
