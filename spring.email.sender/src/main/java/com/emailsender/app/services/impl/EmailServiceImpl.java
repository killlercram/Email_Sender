package com.emailsender.app.services.impl;

import com.emailsender.app.helper.Messages;
import com.emailsender.app.services.EmailService;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


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
        simpleMailMessage.setFrom("Shashwat_Tandon");
        mailSender.send(simpleMailMessage);
        logger.info("Email has been sent..");
    }

    @Override
    public void sendEmail(String[] to, String subject, String message) {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("Shashwat_Tandon");
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
            helper.setFrom("Shashwat_Tandon");
            mailSender.send((mimeMessage));
            logger.info("Email Sent..");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, File file) {
        MimeMessage mimeMessage=mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true,"UTF-8");
            helper.setTo(to);
            helper.setFrom("Shashwattandon@gmail.com");
            helper.setSubject(subject);
            helper.setText(message);
            //This will ge the file details
            FileSystemResource fileSystemResource=new FileSystemResource(file);
            helper.addAttachment(fileSystemResource.getFilename(), file);
            mailSender.send(mimeMessage);
            logger.info("Email File Sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmailWithFile(String to, String subject, String message, InputStream is) {
        MimeMessage mimeMessage=mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true,"UTF-8");
            helper.setTo(to);
            helper.setFrom("Shashwattandon@gmail.com");
            helper.setSubject(subject);
            helper.setText(message,true);
            //This will ge the file details
            File file=new File("//home/killercram/Email_Project/spring.email.sender/src/resources/static/images/test.png");
            Files.copy(is,file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            FileSystemResource fileSystemResource=new FileSystemResource(file);
            helper.addAttachment(fileSystemResource.getFilename(),file);

            mailSender.send(mimeMessage);
            logger.info("Email File Sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Receiving Emails
    @Value("${mail.store.protocol}")
    String protocol;
    @Value("${mail.imaps.host}")
    String host;
    @Value("${mail.imaps.port}")
    String port;

   @Value("${spring.mail.username}")
    String userName;
    @Value("${spring.mail.password}")
    String password;

    @Override
    public List<Messages> getInboxMessages() {
        //code to receive all emails
        Properties configurations=new Properties();
        configurations.setProperty("mail.store.protocol","imaps");
        configurations.setProperty("mail.imaps.host",host);
        configurations.setProperty("mail.imaps.port",port);

        Session session=Session.getDefaultInstance(configurations);
        try {
            Store store= session.getStore(protocol);//store made
            store.connect(userName,password);
            //got the Folder from the store
            Folder inbox = store.getFolder("INBOX");
            //opening Folder
            inbox.open(Folder.READ_ONLY);
            //getting All messages Array
            jakarta.mail.Message[] messages=inbox.getMessages();
            //Now we can go through all the message
            List<Messages> list=new ArrayList<>();

                for (jakarta.mail.Message message : messages) {
//                    System.out.println(message.getSubject());
                    String content=getContentFromEmailMessage(message);
                    List<String> files=getFilesFromEmailMessage(message);
//                    System.out.println("______________****____________________");
                    list.add(Messages.builder().subjects(message.getSubject()).content(content).files(files).build());
                }

            return list;
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
        
    }

    private List<String> getFilesFromEmailMessage(Message message) throws MessagingException, IOException {
        List<String>files=new ArrayList<>();
        if(message.isMimeType("multipart/*")){
            Multipart content=(Multipart)message.getContent();
            for(int i=0;i<content.getCount();i++){
                BodyPart bodyPart=content.getBodyPart(i);
                if(Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())){//attachment is present

                    InputStream inputStream=bodyPart.getInputStream();
                    File file=new
                            File("/home/killercram/Email_Project/spring.email.sender/src/resources/email"
                            +
                            bodyPart.getFileName());
                    Files.copy(inputStream,file.toPath(),StandardCopyOption.REPLACE_EXISTING);
                    files.add((file.getAbsolutePath()));

                }
            }
        }
        return files;
    }

    private String getContentFromEmailMessage(Message message) throws MessagingException, IOException {
        if(message.isMimeType("text/plain")
                || message.isMimeType("text/html")){
           String content=(String)message.getContent();
           return content;
        }else if(message.isMimeType("multipart/*")) {
            Multipart part = (Multipart) message.getContent();
            for (int i = 0; i < part.getCount(); i++) {
                BodyPart bodyPart = part.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")){

                    return (String) bodyPart.getContent();
                }
            }
        }
        return null;
    }


}
