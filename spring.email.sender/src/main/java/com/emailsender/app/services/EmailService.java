package com.emailsender.app.services;

import java.io.File;

public interface EmailService {

    //sending email to single person
    void sendEmail(String to,String subject,String message);

    //send email to multiple person
    void sendEmail(String []to ,String subject, String message);

    //send emailWithHTML
    void sendEmailWithHtml(String to,String subject,String htmlContent);

    //Sending email with file
    void sendEmailWithFile(String to, String subject, String message, File file);
}
