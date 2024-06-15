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
    public void sendUserCredentials(String userName, String userEmail, String userPassword) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(userEmail);
        mimeMessageHelper.setSubject("GateKeeper Account Successfully Created!");

        Context context = new Context();
        context.setVariable("header", "GateKeeper Account Successfully Created!");
        context.setVariable("name", userName);
        context.setVariable("messageBody", "Your GateKeeper account has successfully been created! Please login to https://gate.ieee-rvce.org/ and change your password as soon as you can.");
        context.setVariable("emailId","Email: " + userEmail);
        context.setVariable("password","Password: " + userPassword);
        context.setVariable("linkUrl", "https://gate.ieee-rvce.org/login");
        context.setVariable("buttonText", "Click here to Login");

        String htmlContent = templateEngine.process("userEmailTemplate", context);
        mimeMessageHelper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
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
                context.setVariable("buttonText","Click here to view the Request");

                String htmlContent = templateEngine.process("emailTemplate",context);
                mimeMessageHelper.setText(htmlContent,true);
                mimeMessageHelper.addAttachment("Event PDF.pdf",new ByteArrayResource(emailDetails.getAttachment()));
                javaMailSender.send(mimeMessage);

    }
}
