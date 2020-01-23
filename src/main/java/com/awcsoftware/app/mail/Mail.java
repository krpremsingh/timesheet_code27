package com.awcsoftware.app.mail;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.employee.ConfirmationToken;
import com.awcsoftware.app.employee.EmployeeService;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;

public class Mail {
	static Logger logger = Logger.getLogger(Mail.class.getName());

	@Autowired
	MailConfig mailconfig;

	@Autowired
	EmployeeService employeeservice;

	UriComponents uriComponents = null;

	public String changePasswordRequestEmail(ConfirmationToken confirmationToken, HttpServletRequest request)
			throws MessagingException, AppException, DbException {
		uriComponents = UriComponentsBuilder.newInstance().scheme(request.getScheme()).host(request.getServerName())
				.port(request.getServerPort()).path(MailMessageConstants.ForgotPasswordUriPath.getLabel().toString())
				.queryParam("token", confirmationToken.getToken())
				.queryParam("email", confirmationToken.getUser().getEmail()).build();
		String forgotPasswordMailContent = MailMessageConstants.ForgotPasswordEmailContent.getLabel().toString();
		if (forgotPasswordMailContent.contains("user_link")) {
			forgotPasswordMailContent = forgotPasswordMailContent.replace("user_link", uriComponents.toUriString());
		}

		logger.debug("uriComponents>>>>>>>>>>>>>>" + uriComponents);
		MimeMessage message = mailconfig.javaMailSender().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		logger.debug("confirmationToken.getUser().getEmail()>>>>>>>>>>>>>>" + confirmationToken.getUser().getEmail());
		helper.setTo(confirmationToken.getUser().getEmail());
		helper.setSubject(MailMessageConstants.ChangePasswordSubject.getLabel().toString());
		helper.setText(forgotPasswordMailContent, true);
		mailconfig.javaMailSender().send(message);
		return MailMessageConstants.SendEmail.getLabel().toString();
	}

	public String changePasswordSuccessEmail(User user) throws MessagingException {
		MimeMessage message = mailconfig.javaMailSender().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(user.getEmail());
		helper.setSubject(MailMessageConstants.PasswordChanged.getLabel().toString());
		helper.setText(MailMessageConstants.PasswordChangedEmailContent.getLabel().toString(), true);
		mailconfig.javaMailSender().send(message);
		return null;

	}

	public String welcomeEmail(MailPojo mailpojo) throws MessagingException, IOException {
		MimeMessage message = mailconfig.javaMailSender().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		/*
		 * MimeMultipart multipart = new MimeMultipart(); BodyPart messageBodyPart = new
		 * MimeBodyPart();
		 */
		// String content = mailtemplatecontent.welcomeEmail;
		String content = MailMessageConstants.WelcomeEmailContent.getLabel().toString();
		for (User sendTo : mailpojo.getUserList()) {
			String contentupdate = null;
			if (content.contains("user_email")) {
				contentupdate = content.replace("user_email", sendTo.getEmail());
				// System.out.println(contentupdate);
				if (contentupdate.contains("user_password")) {
					contentupdate = contentupdate.replace("user_password", sendTo.getPassword());
				}
			}

			/*
			 * messageBodyPart.setContent(contentupdate, "text/html");
			 * multipart.addBodyPart(messageBodyPart); message.setContent(multipart);
			 */
			helper.setTo(sendTo.getEmail());
			helper.setSubject(MailMessageConstants.WelcomeEmailSubject.getLabel().toString());
			helper.setText(contentupdate, true);
			mailconfig.javaMailSender().send(message);
		}
		return MailMessageConstants.SendEmail.getLabel().toString();

	}
}
