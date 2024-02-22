package com.ekart.notifyservicesendemail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ekart.notifyservicesendemail.entity.EmailDetails;
import com.ekart.notifyservicesendemail.service.EmailService;
import com.ekart.notifyservicesendemail.service.impl.EmailServiceImpl;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public boolean sendMail(@RequestBody EmailDetails details) {
    	boolean status = emailService.sendSimpleMail(details);

        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public boolean sendMailWithAttachment(@RequestBody EmailDetails details) {
    	boolean status = emailService.sendMailWithAttachment(details);

        return status;
    }

    @PostMapping("/send")
    public boolean sendMail(@RequestParam(value = "file", required = false) MultipartFile[] file,
                           String to,
                           String[] cc,
                           String subject,
                           String body) {
        return emailService.sendMail(file, to, cc, subject, body);
    }

}
