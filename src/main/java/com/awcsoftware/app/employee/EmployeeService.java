package com.awcsoftware.app.employee;

import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.mail.MailConfig;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserDao;

@Component
public class EmployeeService {
	
	static Logger log = Logger.getLogger(EmployeeService.class.getName());
	boolean valid = false;
	UriComponents uriComponents = null;
	
	@Autowired(required=true)
	@Qualifier("mailconfig")
	MailConfig mailconfig;
	


	public String resetPassword(User user) throws AppException, DbException {
		User result = null;

		EmployeeValidator validator = new EmployeeValidator();
		EmployeeDao empDao= new EmployeeDao();
		UserDao dao = new UserDao();
		result = dao.getUser(user.getEmail());

		Set<String> validateCurrentPassword = validator.validateCurrentPassword(user);
		if (validateCurrentPassword.size() != 0) {
			return validateCurrentPassword.toString();
		}

		Set<String> checkCurrentPassword = validator.checkCurrentPassword(user);
		if (checkCurrentPassword.size() != 0) {
			return checkCurrentPassword.toString();

		} else {
			Set<String> validateNewConfirmPassword = validator.validateNewConfirmPassword(user);
			if (validateNewConfirmPassword.size() != 0) {
				return validateNewConfirmPassword.toString();
			}
		}
		empDao.resetPassword(user);
		return EmployeeMessageConstants.PasswordChanged.getLabel();
	}

	public String changePassword(User user) throws AppException, DbException {
		UserDao dao = new UserDao();
		EmployeeValidator validator = new EmployeeValidator();
		EmployeeDao empDao= new EmployeeDao();

		Set<String> validateNewConfirmPassword = validator.validateNewConfirmPassword(user);

		if (validateNewConfirmPassword.size() != 0) {
			return validateNewConfirmPassword.toString();
		}
		empDao.updatePassword(user);
		return EmployeeMessageConstants.PasswordChanged.getLabel();
	}

	/*
	 * public boolean passwordVerification(String password) { if
	 * (password.length()<8) { log.debug("password length must not be less than 8");
	 * return valid; }
	 * 
	 * // Checks each character to see if it is acceptable. for (int i = 0; i <
	 * password.length(); i++){ char c = password.charAt(i);
	 * 
	 * if ( ('a' <= c && c <= 'z') // Checks if it is a lower case letter || ('A' <=
	 * c && c <= 'Z') //Checks if it is an upper case letter || ('0' <= c && c <=
	 * '9') //Checks to see if it is a digit ) {
	 * 
	 * valid = true; } else { // tells the user that only letters & digits are
	 * allowed log.debug("Only letter & digits are acceptable."); valid = false;
	 * break; }
	 * 
	 * } return valid; }
	 */

	public String verifyEmailId(String email) throws AppException, DbException {
		EmployeeValidator validator = new EmployeeValidator();
		Set<String> verifyEmail = validator.verifyEmail(email);
		if (verifyEmail.size() == 0) {
			return verifyEmail.toString();
		}
		return EmployeeMessageConstants.ValidEmail.getLabel().toString();
	}

	public boolean saveToken(ConfirmationToken token) throws AppException, DbException {
	    EmployeeDao empDao= new EmployeeDao();
		if (Util.isEmptyOrNull(empDao.checkToken(token.getToken()))) {
			empDao.saveToken(token);
			return true;
		}
		return false;
	}

	public boolean updateToken(ConfirmationToken token) throws AppException, DbException {
		EmployeeDao empDao= new EmployeeDao();
		if (empDao.updateToken(token) == true) {
			return true;
		}
		return false;

	}

	public boolean checkToken(String email) throws AppException, DbException {
	EmployeeValidator validator= new EmployeeValidator();
	Set<String> checkToken = validator.checkToken(email);
	if(checkToken.size()!=0) {
		return false;
	}
		return true;

	}

	public String findByToken(String token) throws AppException, DbException {
		EmployeeValidator validator = new EmployeeValidator();
		Set<String> result = validator.findByToken(token);
		if (result.size() != 0) {
			return result.toString();
		}
		return EmployeeMessageConstants.LinkValid.getLabel().toString();
	}

	public String emailContent(ConfirmationToken confirmationToken, HttpServletRequest request) {

		uriComponents = UriComponentsBuilder.newInstance().scheme(request.getScheme()).host(request.getServerName())
				.port(request.getServerPort()).path("/timesheet/auth/confirm-reset")
				.queryParam("token", confirmationToken.getToken()).build();

		String body = "<table>\r\n" + "    <tbody>\r\n" + "        <tr>\r\n" + "            <td>Hi,</td>\r\n"
				+ confirmationToken.getUser().getFirstName() + "        </tr>\r\n" + "        <tr>\r\n"
				+ "            <td>You recently requested to reset your password for your AWC account. Click the link below to reset it</td>\r\n"
				+ "        </tr>\r\n" + "        <tr>\r\n"
				+ "            <td>If you did not request a password reset, please reply to let us know. The password reset link is only valid for next 24 hours.:</td>\r\n"
				+ "        </tr>\r\n" + "        <tr>\r\n" + uriComponents + "        </tr>\r\n" + "    </tbody>\r\n"
				+ "</table>";
		return body;

	}
	
	public String sendEmail(ConfirmationToken confirmationToken, HttpServletRequest request) throws MessagingException {
		MimeMessage message = mailconfig.javaMailSender().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(confirmationToken.getUser().getEmail());
		helper.setSubject("Change Password Request");
		helper.setText(emailContent(confirmationToken, request), true);
		mailconfig.javaMailSender().send(message);
		return EmployeeMessageConstants.SentEmail.getLabel().toString();
		
	}

}
