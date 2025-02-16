package com.emailsender.app.services;

import com.emailsender.app.helper.Messages;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface EmailService {

    //sending email to single person
    void sendEmail(String to,String subject,String message);

    //send email to multiple person
    void sendEmail(String []to ,String subject, String message);

    //send emailWithHTML
    void sendEmailWithHtml(String to,String subject,String htmlContent);

    //Sending email with file
    void sendEmailWithFile(String to, String subject, String message, File file);

    //Sending Files dynamically
    void sendEmailWithFile(String to , String subject, String message, InputStream is);

   //Listing all the messages in inbox
    List<Messages> getInboxMessages();
}
