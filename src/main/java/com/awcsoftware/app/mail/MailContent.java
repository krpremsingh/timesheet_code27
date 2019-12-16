package com.awcsoftware.app.mail;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.awcsoftware.app.employee.ConfirmationToken;

@Component
public class MailContent {
	
	UriComponents uriComponents =null;
	public String emailContent(ConfirmationToken confirmationToken, HttpServletRequest request) {

		uriComponents = UriComponentsBuilder.newInstance().scheme(request.getScheme()).host(request.getServerName())
				.port(request.getServerPort()).path("/timesheet/auth/confirm-reset")
				.queryParam("token", confirmationToken.getToken()).build();
		String body = "<table>\r\n" + "    <tbody>\r\n" + "<tr>\r\n" + "<td>Hi,</td>\r\n"
				+ confirmationToken.getUser().getFirstName()+"</tr>\r\n" + "<tr>\r\n"
				+ "<td>You recently requested to reset your password for your AWC account. Click the link below to reset it</td>\r\n"
				+ "</tr>\r\n" + "<tr>\r\n"
				+ "<td>If you did not request a password reset, please reply to let us know. The password reset link is only valid for next 24 hours.:</td>\r\n"
				+ "</tr>\r\n" + "<tr>\r\n" + uriComponents + "</tr>\r\n" + "</tbody>\r\n"
				+ "</table>";
		return body;

	}
}
