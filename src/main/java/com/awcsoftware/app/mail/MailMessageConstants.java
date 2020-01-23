package com.awcsoftware.app.mail;

public enum MailMessageConstants {
	ChangePasswordSubject("Change Password Request"), 
	SendEmail("Email send successfully"),
	PasswordChanged("Password changed"),
	ForgotPasswordUriPath("/timesheetfrontend/reset-password.html"),
	InvalidEmail("Email id does not exist"),
	WelcomeEmailSubject("Welcome to AWC Timesheet"),
	WelcomeEmailContent("<html>\r\n" + 
			"<title>Mailer</title>\r\n" + 
			"<body style=\"background-color:#fff;width:100%\">\r\n" + 
			"		<table style=\"width:600px;border-collapse: collapse;\"  align=\"center\">\r\n" + 
			"			<tbody>\r\n" + 
			"				<tr>\r\n" + 
			"					<td style=\"background-color:#0092FF;padding:10px 0; text-align:center;border-top-left-radius: 5px;border-top-right-radius: 5px;\">\r\n" + 
			"						<img src=\"https://i.ibb.co/Ky53DHt/awc.png\" alt=\"AWC\" />\r\n" + 
			"						<hr  style=\"width: 80%;opacity: .5;\">\r\n" + 
			"						<h1 style=\"color: #fff; font: Bold 34px/46px Open Sans; text-align:center; margin-top: 20px;font-family: sans-serif;\">Welcome to AWC</h1>\r\n" + 
			"					</td>				\r\n" + 
			"				</tr>\r\n" + 
			"				<tr style=\"height: 44px;\"><td style=\"background-color: #F6F9FC; \"></td></tr>				\r\n" + 
			"				<tr>\r\n" + 
			"					<td style=\"background-color: #F6F9FC; \">\r\n" + 
			"						<h5 style=\"font-size:18px; color: #343434; text-align:center;font-family: calibri;\">You have successfully registered in AWC Timesheet</h5>\r\n" + 
			"						<p style=\" font-size:14px; color: #343434; text-align:center;font-family: calibri;\">Click on the button to login using the credentials below</p>\r\n" + 
			"						<p style=\" font-size:14px; color: #343434; text-align:center;font-family: calibri;\">Email : <strong>user_email</strong></p>\r\n" + 
			"						<p style=\" font-size:14px; color: #343434; text-align:center;font-family: calibri;\">Password : <strong>user_password</strong></p>\r\n" + 
			"					</td>\r\n" + 
			"				</tr>\r\n" + 
			"				<tr style=\"height: 47px;\"><td style=\"background-color: #F6F9FC; \"></td></tr>\r\n" + 
			"				<tr>\r\n" + 
			"					<td align=\"center\" style=\"background-color: #F6F9FC; \">\r\n" + 
			"						<a href=\"http://127.0.0.1:8080/timesheetfrontend/log-in.html\"> <img src=\"https://i.ibb.co/KjWS6NF/login-now-btn.png\"></a>\r\n" + 
			"					</td>\r\n" + 
			"				</tr>\r\n" + 
			"				<tr style=\"height: 60px;\"><td style=\"border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;background-color: #F6F9FC; \"></td></tr>\r\n" + 
			"			</tbody>	\r\n" + 
			"			<tfoot>\r\n" + 
			"				<tr style=\"height: 20px;\"><td></td></tr>\r\n" + 
			"				<tr>\r\n" + 
			"					<td align=\"center\">\r\n" + 
			"						<a href=\"https://www.facebook.com\" target=\"_blank\">\r\n" + 
			"							<img src=\"https://i.ibb.co/y0ZG2py/facebook.png\" alt=\"facebook\"></a>\r\n" + 
			"						<a href=\"https://www.instagram.com\" target=\"_blank\"> \r\n" + 
			"							<img src=\"https://i.ibb.co/sbPLFPv/instagram.png\" alt=\"instagram\"></a>\r\n" + 
			"						<a href=\"https://www.twitter.com\" target=\"_blank\">\r\n" + 
			"							<img src=\"https://i.ibb.co/S6GK5Dp/twitter.png\" alt=\"twitter\"></a>\r\n" + 
			"						<a href=\"https://www.linkedin.com\" target=\"_blank\">\r\n" + 
			"							<img src=\"https://i.ibb.co/N9Q5hw0/linkedin.jpg\" alt=\"linkedin\"></a>\r\n" + 
			"					</td>\r\n" + 
			"				</tr>\r\n" + 
			"			</tfoot>\r\n" + 
			"		</table>\r\n" + 
			"	</body>\r\n" + 
			"</html>\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			""),
	ForgotPasswordEmailContent("<html>\r\n" + 
			"<title>Mailer</title>\r\n" + 
			"\r\n" + 
			"<body style=\"background-color:#fff;width:100%\">\r\n" + 
			"	<table style=\"width:600px;border-collapse: collapse;\" align=\"center\">\r\n" + 
			"		<tbody>\r\n" + 
			"			<tr>\r\n" + 
			"				<td\r\n" + 
			"					style=\"background-color:#0092FF;padding:10px 0; text-align:center;border-top-left-radius: 5px;border-top-right-radius: 5px;\">\r\n" + 
			"					<img src=\"https://i.ibb.co/Ky53DHt/awc.png\" alt=\"AWC\" />\r\n" + 
			"					<hr style=\"width: 80%;opacity: .5;\">\r\n" + 
			"					<h1\r\n" + 
			"						style=\"color: #fff; font: Bold 34px/46px Open Sans; text-align:center; margin-top: 20px;font-family: sans-serif;\">\r\n" + 
			"						Reset your Password</h1>\r\n" + 
			"				</td>\r\n" + 
			"			</tr>\r\n" + 
			"			<tr style=\"height: 44px;\">\r\n" + 
			"				<td style=\"background-color: #F6F9FC; \"></td>\r\n" + 
			"			</tr>\r\n" + 
			"			<tr>\r\n" + 
			"				<td style=\"background-color: #F6F9FC; \">\r\n" + 
			"					<h5 style=\"font-size:18px; color: #343434; text-align:center;font-family: calibri;\">If you've lost\r\n" + 
			"						your password or wish to reset it, <br> use the link below to get started.</h5>\r\n" + 
			"					<p style=\" font-size:14px; color: #343434; text-align:center;font-family: calibri;opacity: .7;\">If\r\n" + 
			"						you did not request a password reset, you can safely ignore this mail. Only <br> a person with\r\n" + 
			"						access to your email can reset your account password.</p>\r\n" + 
			"\r\n" + 
			"				</td>\r\n" + 
			"			</tr>\r\n" + 
			"			<tr style=\"height: 47px;\">\r\n" + 
			"				<td style=\"background-color: #F6F9FC; \"></td>\r\n" + 
			"			</tr>\r\n" + 
			"			<tr>\r\n" + 
			"				<td align=\"center\" style=\"background-color: #F6F9FC; \">\r\n" + 
			"					<a href=\"user_link\"> <img src=\"https://i.ibb.co/4f9QQP5/reset-your-password.png\"></a>\r\n" + 
			"				</td>\r\n" + 
			"			</tr>\r\n" + 
			"			<tr style=\"height: 60px;\">\r\n" + 
			"				<td style=\"border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;background-color: #F6F9FC; \">\r\n" + 
			"				</td>\r\n" + 
			"			</tr>\r\n" + 
			"		</tbody>\r\n" + 
			"		<tfoot>\r\n" + 
			"			<tr style=\"height: 20px;\">\r\n" + 
			"				<td></td>\r\n" + 
			"			</tr>\r\n" + 
			"			<tr>\r\n" + 
			"				<td align=\"center\">\r\n" + 
			"					<a href=\"https://www.facebook.com\" target=\"_blank\">\r\n" + 
			"						<img src=\"https://i.ibb.co/y0ZG2py/facebook.png\" alt=\"facebook\"></a>\r\n" + 
			"					<a href=\"https://www.instagram.com\" target=\"_blank\">\r\n" + 
			"						<img src=\"https://i.ibb.co/sbPLFPv/instagram.png\" alt=\"instagram\"></a>\r\n" + 
			"					<a href=\"https://www.twitter.com\" target=\"_blank\">\r\n" + 
			"						<img src=\"https://i.ibb.co/S6GK5Dp/twitter.png\" alt=\"twitter\"></a>\r\n" + 
			"					<a href=\"https://www.linkedin.com\" target=\"_blank\">\r\n" + 
			"						<img src=\"https://i.ibb.co/N9Q5hw0/linkedin.jpg\" alt=\"linkedin\"></a>\r\n" + 
			"				</td>\r\n" + 
			"			</tr>\r\n" + 
			"		</tfoot>\r\n" + 
			"	</table>\r\n" + 
			"</body>\r\n" + 
			"\r\n" + 
			"</html>"),
	PasswordChangedEmailContent("<html>\r\n" + 
			"<title>Mailer</title>\r\n" + 
			"<body style=\"background-color:#fff;width:100%\">\r\n" + 
			"		<table style=\"width:600px;border-collapse: collapse;\"  align=\"center\">\r\n" + 
			"			<tbody>\r\n" + 
			"				<tr>\r\n" + 
			"					<td style=\"background-color:#0092FF;padding:10px 0; text-align:center;border-top-left-radius: 5px;border-top-right-radius: 5px;\">\r\n" + 
			"						<img src=\"https://i.ibb.co/Ky53DHt/awc.png\" alt=\"AWC\" />\r\n" + 
			"						<hr  style=\"width: 80%;opacity: .5;\">\r\n" + 
			"						<h1 style=\"color: #fff; font: Bold 34px/46px Open Sans; text-align:center; margin-top: 20px;font-family: sans-serif;\">Password Change Succesfully</h1>\r\n" + 
			"					</td>				\r\n" + 
			"				</tr>\r\n" + 
			"				<tr style=\"height: 44px;\"><td style=\"background-color: #F6F9FC; \"></td></tr>				\r\n" + 
			"				<tr>\r\n" + 
			"					<td style=\"background-color: #F6F9FC; \">\r\n" + 
			"						<h5 style=\"font-size:18px; color: #343434; text-align:center;font-family: calibri;\">Password has been changed succesfully</h5>\r\n" + 
			"						<p style=\" font-size:14px; color: #343434; text-align:center;font-family: calibri;opacity: .7;\">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod <br>tempor incididunt ut labore et dolore magna aliqua.</p>\r\n" + 
			"										\r\n" + 
			"					</td>\r\n" + 
			"				</tr>\r\n" + 
			"				<tr style=\"height: 47px;\"><td style=\"background-color: #F6F9FC; \"></td></tr>\r\n" + 
			"				<tr>\r\n" + 
			"					<td align=\"center\" style=\"background-color: #F6F9FC; \">\r\n" + 
			"						<a href=\"#\"> <img src=\"https://i.ibb.co/KjWS6NF/login-now-btn.png\"></a>\r\n" + 
			"					</td>\r\n" + 
			"				</tr>\r\n" + 
			"				<tr style=\"height: 60px;\"><td style=\"border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;background-color: #F6F9FC; \"></td></tr>\r\n" + 
			"			</tbody>	\r\n" + 
			"			<tfoot>\r\n" + 
			"				<tr style=\"height: 20px;\"><td></td></tr>\r\n" + 
			"				<tr>\r\n" + 
			"					<td align=\"center\">\r\n" + 
			"						<a href=\"https://www.facebook.com\" target=\"_blank\">\r\n" + 
			"							<img src=\"https://i.ibb.co/y0ZG2py/facebook.png\" alt=\"facebook\"></a>\r\n" + 
			"						<a href=\"https://www.instagram.com\" target=\"_blank\"> \r\n" + 
			"							<img src=\"https://i.ibb.co/sbPLFPv/instagram.png\" alt=\"instagram\"></a>\r\n" + 
			"						<a href=\"https://www.twitter.com\" target=\"_blank\">\r\n" + 
			"							<img src=\"https://i.ibb.co/S6GK5Dp/twitter.png\" alt=\"twitter\"></a>\r\n" + 
			"						<a href=\"https://www.linkedin.com\" target=\"_blank\">\r\n" + 
			"							<img src=\"https://i.ibb.co/N9Q5hw0/linkedin.jpg\" alt=\"linkedin\"></a>\r\n" + 
			"					</td>\r\n" + 
			"				</tr>\r\n" + 
			"			</tfoot>\r\n" + 
			"		</table>\r\n" + 
			"	</body>\r\n" + 
			"</html>\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			""),

	;

	private final String label;

	public String getLabel() {
		return label;
	}

	private MailMessageConstants(String label) {
		this.label = label;
	}

}
