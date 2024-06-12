package org.ieeervce.gatekeeper.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.ieeervce.gatekeeper.dto.Email.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}") private String sender;
    @Async
    public void sendSimpleMail(EmailDTO emailDetails) throws MessagingException{

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper ;
            try{

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
            catch (MessagingException e) {
                throw e;
            }
    }
}
