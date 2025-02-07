package com.emailsender.app.services.impl;

import com.emailsender.app.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //Logger for message after sending mail
    private static final Logger logger= LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmail(String to, String subject, String message) {
        //inbuilt class for simple mails
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        //setting the parameters
        simpleMailMessage.setTo(to);//to whom we have to send mail
        simpleMailMessage.setSubject(subject);//what is the subject of mail
        simpleMailMessage.setText(message);//message to sent
        simpleMailMessage.setFrom("herosviral@gmail.com");
        mailSender.send(simpleMailMessage);
        logger.info("Email has been sent..");
    }

    @Override
    public void sendEmail(String[] to, String subject, String message) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("hersoviral@gmail.com");
        mailSender.send(simpleMailMessage);
        logger.info("Email Sent..");
    }

    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlContent) {
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper= new MimeMessageHelper(mimeMessage,true,"UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent,true);
            helper.setFrom("herosviral@gmail.com");
            mailSender.send((mimeMessage));
            logger.info("Email Sent..");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, File file) {

    }
}
