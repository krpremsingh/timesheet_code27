package com.awcsoftware.app.employee;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.app.mail.MailConfig;
import com.awcsoftware.app.mail.MailContent;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserDao;

@Component
public class EmployeeService {

	static Logger log = Logger.getLogger(EmployeeService.class.getName());
	boolean valid = false;
	UriComponents uriComponents = null;

	@Autowired(required = true)
	@Qualifier("mailconfig")
	MailConfig mailconfig;

	@Autowired(required = true)
	@Qualifier("empDao")
	EmployeeDao empDao;

	@Autowired(required = true)
	@Qualifier("userDao")
	UserDao userDao;

	@Autowired
	MailContent mailcontent;

	public String resetPassword(User user) throws AppException, DbException {
		User result = null;
		EmployeeValidator validator = new EmployeeValidator();
		result = userDao.getUser(user.getEmail());
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
		EmployeeValidator validator = new EmployeeValidator();

		Set<String> validateNewConfirmPassword = validator.validateNewConfirmPassword(user);

		if (validateNewConfirmPassword.size() != 0) {
			return validateNewConfirmPassword.toString();
		}
		empDao.updatePassword(user);
		return EmployeeMessageConstants.PasswordChanged.getLabel();
	}

	public String verifyEmailId(String email) throws AppException, DbException {

		EmployeeValidator validator = new EmployeeValidator();
		Set<String> verifyEmail = validator.verifyEmail(email);
		if (verifyEmail.size() == 0) {
			return verifyEmail.toString();
		}
		return EmployeeMessageConstants.ValidEmail.getLabel().toString();
	}

	public boolean saveToken(ConfirmationToken token) throws AppException, DbException {
		if (Util.isEmptyOrNull(empDao.checkToken(token.getToken()))) {
			empDao.saveToken(token);
			return true;
		}
		return false;
	}

	public boolean updateToken(ConfirmationToken token) throws AppException, DbException {
		if (Util.isEmptyOrNull(empDao.updateToken(token))) {
			return true;
		}
		return false;

	}

	public boolean checkToken(String email) throws AppException, DbException {
		EmployeeValidator validator = new EmployeeValidator();
		Set<String> checkToken = validator.checkToken(email);
		if (checkToken.size() != 0) {
			return false;
		}
		return true;

	}

	public boolean saveUpdateToken(ConfirmationToken confirmationtoken) throws AppException, DbException {
		boolean result = checkToken(confirmationtoken.getUser().getEmail());
		if (result == true) {
			updateToken(confirmationtoken);
		} else {
			saveToken(confirmationtoken);
		}
		return result;

	}

	public String findByToken(String token) throws AppException, DbException {
		EmployeeValidator validator = new EmployeeValidator();
		Set<String> result = validator.findByToken(token);
		if (result.size() != 0) {
			return result.toString();
		}
		return EmployeeMessageConstants.LinkValid.getLabel().toString();
	}

}
