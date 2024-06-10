package org.ieeervce.gatekeeper.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.ieeervce.gatekeeper.dto.Email.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

import java.util.Objects;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}") private String sender;

    public String sendSimpleMail(EmailDTO emailDetails) throws MessagingException{

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
                String htmlContent = templateEngine.process("emailTemplate",context);
                mimeMessageHelper.setText(htmlContent,true);

                FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
                mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()),file);
                javaMailSender.send(mimeMessage);
                return "Mail sent successfully";
            }
            catch (MessagingException e) {
                throw e;
            }
    }


}
