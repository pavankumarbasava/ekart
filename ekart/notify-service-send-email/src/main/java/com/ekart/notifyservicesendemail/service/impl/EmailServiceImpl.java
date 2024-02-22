package com.ekart.notifyservicesendemail.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ekart.notifyservicesendemail.entity.EmailDetails;
import com.ekart.notifyservicesendemail.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender; // spring-boot-starter-mail

	
	@Value("${spring.mail.username}")
	private String fromEmail;//="pavankumarbasava16@gmail.com";

	private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Override
	public boolean sendSimpleMail(EmailDetails details) {

		// Try block to check for exceptions
		try {

			// Creating a simple mail message
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			// Setting up necessary details
			mailMessage.setFrom(fromEmail);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());

			// Sending the mail
			
			javaMailSender.send(mailMessage);
			return true;
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			logger.error("Error occured while sending mail ", e);
			return false;
		}
	}

	@Override
	public boolean sendMailWithAttachment(EmailDetails details) {
		// Creating a mime message
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {

			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(fromEmail);
			mimeMessageHelper.setTo(details.getRecipient());
			mimeMessageHelper.setText(details.getMsgBody());
			mimeMessageHelper.setSubject(details.getSubject());

			// Adding the attachment
			FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));

			mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

			// Sending the mail
			javaMailSender.send(mimeMessage);
			return true;
		}

		// Catch block to handle MessagingException
		catch (MessagingException e) {
			logger.error("Error occured while sending mail ", e);
			// Display message when exception occurred
			return false;
		}
	}

	@Override
	public boolean sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setFrom(fromEmail);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setCc(cc);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body);

			for (MultipartFile multipartFile : file) {
				mimeMessageHelper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()),
						new ByteArrayResource(multipartFile.getBytes()));
			}

			javaMailSender.send(mimeMessage);
			logger.info("Email send success to " + Arrays.toString(cc));
			return true;

		} catch (Exception e) {
			logger.error("Error occured while sending mail ", e);

			return false;
		}
	}

}
