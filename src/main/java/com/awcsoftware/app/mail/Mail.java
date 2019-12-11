package com.awcsoftware.app.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.employee.ConfirmationToken;
import com.awcsoftware.app.employee.EmployeeMessageConstants;
@Component
public class Mail {
	
	@Autowired(required = true)
	@Qualifier("mailconfig")
	MailConfig mailconfig;
	
	@Autowired
	MailContent mailcontent;
	
	public String sendEmail(ConfirmationToken confirmationToken, HttpServletRequest request) throws MessagingException {
		MimeMessage message = mailconfig.javaMailSender().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(confirmationToken.getUser().getEmail());
		helper.setSubject("Change Password Request");
		helper.setText(mailcontent.emailContent(confirmationToken, request), true);
		mailconfig.javaMailSender().send(message);
		return EmployeeMessageConstants.SendEmail.getLabel().toString();

	}
}
