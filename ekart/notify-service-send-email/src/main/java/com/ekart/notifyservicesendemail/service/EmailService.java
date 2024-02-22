package com.ekart.notifyservicesendemail.service;

import org.springframework.web.multipart.MultipartFile;

import com.ekart.notifyservicesendemail.entity.EmailDetails;

public interface EmailService {
    // To send a simple email
    boolean sendSimpleMail(EmailDetails details);

    // To send an email with attachment
    boolean sendMailWithAttachment(EmailDetails details);

    // send email file
    boolean sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);
}
