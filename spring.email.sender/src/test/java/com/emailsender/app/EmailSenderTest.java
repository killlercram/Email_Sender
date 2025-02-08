package com.emailsender.app;

import com.emailsender.app.services.EmailService;
import com.emailsender.app.services.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLOutput;

@SpringBootTest
public class EmailSenderTest {
    @Autowired
    private EmailService emailService;
    //testing code for single as well as multi person email
    @Test
    void emailSendTest(){
        System.out.println("Sending Email");
        emailService.sendEmail("mailforshashwattandon@gmail.com","Email Testing","This is the Testing of the Email Service which we are doing right now.");
    }

    //testing for send the emails part.
    @Test
    void sendHtmlContent(){
        String html="" +
                "<h1 style='color:red;border:2px solid red;'>Hello This is Email Testing for html</h1>" +
                "";
        System.out.println("Html Content sent.");
        emailService.sendEmailWithHtml("mailforshashwattandon@gmail.com","Email Testing",html);

    }

    //Testing for sending the file
    @Test
    void sendEmailFile(){
        System.out.println("Sending mail after verifying");
        emailService.sendEmailWithFile("mailforshashwattandon@gmail.com",
                "sending the file",
                "Testing for sending files via Emails.",
                new File("/home/killercram/Email_Project/spring.email.sender/src/resources/static/images/harsh.jpg"));
    }
    @Test
    void sendEmailFileWithStream(){
        System.out.println("Sending mail after verifying");
        File file=new File( "/home/killercram/Email_Project/spring.email.sender/src/resources/static/images/harsh.jpg");
        try {
            InputStream is=new FileInputStream(file);
            emailService.sendEmailWithFile("mailforshashwattandon@gmail.com",
                    "sending the file",
                    "Testing for sending files via Emails.",is
                    );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}


