package com.awcsoftware.app.employee;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserDao;

/* 
 * validate the password ttern
 * check current password in database
 * validate current password with new password
 * validate new password and confirm password
 * check confirmation token validity
 * check confirmation token null or not
 */
public class EmployeeValidator {
	static Logger logger = Logger.getLogger(EmployeeValidator.class);
	static Set<String> errorMsg;
	static {
		errorMsg = new LinkedHashSet<String>();
	}
	User result = null;

	private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

	private Pattern pattern;

	private Matcher matcher;

	public EmployeeValidator() {
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	public boolean validatePattern(final String password) {

		matcher = pattern.matcher(password);
		return matcher.matches();

	}

	/*
	 * check current password in database
	 */
	public Set<String> checkCurrentPassword(User user) {
		errorMsg.clear();
		UserDao dao = new UserDao();
		result = dao.getUser(user.getEmail());
		if (!result.getPassword().equals(user.getCurrentPassword())) {
			errorMsg.add(EmployeeMessageConstants.InvalidCurrentPassword.getLabel().toString());
		}
		return errorMsg;

	}

	/*
	 * validate current password and new password
	 */
	public Set<String> validateCurrentPassword(User user) {
		errorMsg.clear();
		UserDao dao = new UserDao();
		result = dao.getUser(user.getEmail());
		if (result.getPassword().equals(user.getNewPassword())) {
			errorMsg.add(EmployeeMessageConstants.SameAsCurrentPassword.getLabel().toString());
		}
		return errorMsg;

	}

	/*
	 * validate new password and confirm password
	 */
	public Set<String> validateNewConfirmPassword(User user) {
		errorMsg.clear();
		if (!user.getConfirmPassword().equalsIgnoreCase(user.getNewPassword())) {
			errorMsg.add(EmployeeMessageConstants.InvalidPassword.getLabel());
		}
		if(validatePattern(user.getNewPassword())==false) {
			errorMsg.add(EmployeeMessageConstants.ValidatePasswordPattern.getLabel().toString());
		}
		return errorMsg;
	}

	public Set<String> verifyEmail(String email) {
		errorMsg.clear();
		EmployeeDao dao = new EmployeeDao();
		String emailId = dao.verifyEmailId(email);
		if (emailId == null) {
			errorMsg.add(EmployeeMessageConstants.WrongEmailId.getLabel());
		}
		return errorMsg;

	}

	public Set<String> checkToken(String email) {
		errorMsg.clear();
		EmployeeDao empDao = new EmployeeDao();
		ConfirmationToken e = empDao.checkToken(email);
		if (Util.isEmptyOrNull(e)) {
			errorMsg.add(EmployeeMessageConstants.LinkExpired.getLabel().toString());
		}
		return errorMsg;
	}

	public Set<String> findByToken(String token) throws AppException, DbException {
		errorMsg.clear();
		EmployeeDao dao = new EmployeeDao();
		ConfirmationToken result = dao.findByToken(token);
		logger.debug("result " + result);
		if (result != null) {
			if (result.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
				errorMsg.add(EmployeeMessageConstants.LinkExpired.getLabel().toString());
			}
		} else if (Util.isEmptyOrNull(result)) {
			errorMsg.add(EmployeeMessageConstants.LinkExpired.getLabel().toString());
		} else {
			errorMsg.add(EmployeeMessageConstants.LinkValid.getLabel().toString());
		}
		return errorMsg;
	}

	/*
	 * public Set<String> verifyCurrentPassword(User user){ UserDao dao= new
	 * UserDao(); String password = dao.getCurrentPassword(user.getEmail());
	 * if(password.equals(user.getNewPassword())) {
	 * errorMsg.add(EmployeeErrorConstants.SameAsCurrentPassword.getLabel().toString
	 * ()); } return errorMsg;
	 * 
	 * }
	 */

}
