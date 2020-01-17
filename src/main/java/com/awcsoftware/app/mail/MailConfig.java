package com.awcsoftware.app.mail;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component("mailconfig")
public class MailConfig {
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "awcsoftware.net");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "false");
		props.put("mail.smtp.socketFactory.port", 465);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		mailSender.setJavaMailProperties(props);
		mailSender.setHost("awcsoftware.net");
		mailSender.setPort(465);
		mailSender.setProtocol("smtp");
		mailSender.setUsername("akumar1@awcsoftware.net");
		mailSender.setPassword("welcome@123");

		return mailSender;
	}
}
