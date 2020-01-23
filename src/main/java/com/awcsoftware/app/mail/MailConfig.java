package com.awcsoftware.app.mail;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailConfig {
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mailSender.setJavaMailProperties(mailProperties);
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(25);
		mailSender.setProtocol("smtp");
		mailSender.setUsername("awctimesheet@gmail.com");
		mailSender.setPassword("awc@1234");

		return mailSender;
	}
}
