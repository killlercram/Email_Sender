package com.emailsender.app.controllers.api;

import com.emailsender.app.helper.CustomeResponse;
import com.emailsender.app.helper.EmailRequest;
import com.emailsender.app.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/email")
public class EmailController {


    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    //send Email

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request){
        emailService.sendEmailWithHtml(request.getTo(),
                request.getSubject(),
                request.getMessage());
        return ResponseEntity.ok(
                CustomeResponse.builder().message("Email Sent Successfully !!").
                        httpStatus(HttpStatus.OK).
                        success(true).build()

        );
    }

    @PostMapping("/send_with_file")
    public ResponseEntity<?> sendWithFile(@RequestPart EmailRequest request,
                                          @RequestPart MultipartFile file){
        try {
            emailService.sendEmailWithFile(request.getTo(),
                    request.getSubject(), request.getMessage(),file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(
                CustomeResponse.builder().message("Email Sent Successfully !!").
                        httpStatus(HttpStatus.OK).
                        success(true).build()

        );

    }
}
