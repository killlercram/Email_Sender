package com.emailsender.app;

import com.emailsender.app.services.EmailService;
import com.emailsender.app.services.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}


