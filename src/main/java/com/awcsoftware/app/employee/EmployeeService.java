package com.awcsoftware.app.employee;

import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.app.mail.Mail;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserDao;

/*
 * methods to reset and change password
 * 1.resetPasssword
 * 2.changePassword
 * method to send email with password reset link
 * 1.sendEmail
 * methods to save,update,check confirmationToken
 * 1.checkToken
 * 2.saveToken
 * 3.updateToken
 * 4.saveUpdateToken
 * 5.findByToken
 * methods to maintain user state
 * 1.setLoginTransactionIfFailed
 * 2.setLoginTransactionIfSuccess 
 */
@Component
public class EmployeeService {

	static Logger log = Logger.getLogger(EmployeeService.class.getName());
	boolean valid = false;
	UriComponents uriComponents = null;

	@Autowired(required = true)
	@Qualifier("empDao")
	EmployeeDao empDao;

	@Autowired(required = true)
	@Qualifier("userDao")
	UserDao userDao;

	@Autowired
	ConfirmationToken ctoken;

	@Autowired
	Mail mail;

	@Autowired
	EmployeeValidator employeevalidator;

	public String resetPassword(User user) throws AppException, DbException {
		User result = null;
		EmployeeValidator validator = new EmployeeValidator();
		result = userDao.getUser(user.getEmail());
		log.debug("user " + user);
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

	public String changePassword(User user) throws AppException, DbException, MessagingException {
		EmployeeValidator validator = new EmployeeValidator();

		Set<String> validateNewConfirmPassword = validator.validateNewConfirmPassword(user);
		log.debug("validateNewConfirmPassword  " + validateNewConfirmPassword);
		if (validateNewConfirmPassword.size() != 0) {
			return validateNewConfirmPassword.toString();
		}
		boolean result = empDao.updatePassword(user);
		if (result == true) {
			mail.changePasswordSuccessEmail(user);
		}
		return EmployeeMessageConstants.PasswordChanged.getLabel();
	}

	public String sendEmail(String email, HttpServletRequest request)
			throws AppException, DbException, MessagingException {
		EmployeeValidator validator = new EmployeeValidator();
		Set<String> verifyemail = validator.verifyEmailId(email);
		if (verifyemail.size() != 0) {
			return verifyemail.toString();
		}
		User user = userDao.getUser(email);
		ctoken = new ConfirmationToken(user);
		log.debug(user + " " + ctoken);
		saveUpdateToken(ctoken);
		return mail.changePasswordRequestEmail(ctoken, request);
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

	public EmployeeLoginTransaction setLoginTransactionIfFailed(EmployeeLoginTransaction transaction) {
		EmployeeLoginTransaction result = empDao.getLoginTransaction(transaction);
		transaction.setPasswordExpiryDate(result.getPasswordExpiryDate());
		transaction.setLastPasswordChange(result.getLastPasswordChange());
		// transaction.setLoginTimestamp(result.getLoginTimestamp());
		transaction.setActivityStatus(EmployeeMessageConstants.LoginFailed.getLabel().toString());
		transaction.setStatusReason(EmployeeMessageConstants.PasswordExpired.getLabel().toString());
		empDao.saveLastLogin(transaction);
		return transaction;

	}

	public EmployeeLoginTransaction setLoginTransactionIfSuccess(EmployeeLoginTransaction transaction) {
		transaction.setActivityStatus(EmployeeMessageConstants.LoginSuccess.getLabel().toString());
		EmployeeLoginTransaction result = empDao.getLoginTransaction(transaction);
		log.debug(result);
		if (Util.isEmptyOrNull(result)) {
			transaction.setStatusReason(EmployeeMessageConstants.LoginSuccessReason.getLabel().toString());
			empDao.saveLastLogin(transaction);
			return transaction;
		}
		transaction.setPasswordExpiryDate(result.getPasswordExpiryDate());
		transaction.setLastPasswordChange(result.getLastPasswordChange());
		transaction.setStatusReason(EmployeeMessageConstants.LoginSuccessReason.getLabel().toString());
		empDao.saveLastLogin(transaction);

		return transaction;

	}

	public String insertEmployee(User user) throws AppException, DbException {

		if (employeevalidator.validateEmployeeBasicDetails(user).size() != 0) {
			return employeevalidator.validateEmployeeBasicDetails(user).toString();
		}
		if (employeevalidator.validateEmployeeAddress(user).size() != 0) {
			return employeevalidator.validateEmployeeAddress(user).toString();
		}
		if (employeevalidator.validateEmployeePhone(user).size() != 0) {
			return employeevalidator.validateEmployeePhone(user).toString();
		}
		if (employeevalidator.validateEmployeeProjects(user).size() != 0) {
			return employeevalidator.validateEmployeeProjects(user).toString();
		} else {

			if (empDao.validateEmployee(user) == false) {
				return EmployeeMessageConstants.EmployeeAlreadyExist.getLabel().toString();
			}
		}
		return EmployeeMessageConstants.EmployeeAdded.getLabel().toString();

	}

	public String updateEmployee(User user) throws AppException, DbException {
		if (employeevalidator.validateEmployeeBasicDetails(user).size() != 0) {
			return employeevalidator.validateEmployeeBasicDetails(user).toString();
		}
		if (employeevalidator.validateEmployeeAddress(user).size() != 0) {
			return employeevalidator.validateEmployeeAddress(user).toString();
		}
		if (employeevalidator.validateEmployeePhone(user).size() != 0) {
			return employeevalidator.validateEmployeePhone(user).toString();
		}
		if (employeevalidator.validateEmployeeProjects(user).size() != 0) {
			return employeevalidator.validateEmployeeProjects(user).toString();
		}
		else {
			empDao.updateEmployee(user);	
		}		
		return EmployeeMessageConstants.EmployeeUpdated.getLabel().toString();

	}

}
