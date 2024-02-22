package com.ekart.notifyservicesendemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@SpringBootApplication
@EnableEurekaClient
public class NotifyServiceSendEmailApplication {

	public static void main(String[] args) throws MessagingException {
		SpringApplication.run(NotifyServiceSendEmailApplication.class, args);
	}
}
