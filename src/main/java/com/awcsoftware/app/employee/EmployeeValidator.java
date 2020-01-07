package com.awcsoftware.app.employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserDao;

/* 
 * validate the password pattern
 * check current password in database
 * validate current password with new password
 * validate new password and confirm password
 * check confirmation token validity
 * check confirmation token null or not
 */
@Component
public class EmployeeValidator {
	static Logger logger = Logger.getLogger(EmployeeValidator.class);
	static Set<String> errorMsg;
	static {
		errorMsg = new LinkedHashSet<String>();
	}
	User result = null;

	private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!&*]).{8,20})";

	private Pattern pattern;

	private Matcher matcher;

	public EmployeeValidator() {
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	/*
	 * 
	 */
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
		if (user.getNewPassword() == null || user.getNewPassword() == "") {
			errorMsg.add(EmployeeMessageConstants.BlankPassword.getLabel().toString());
		}
		if (validatePattern(user.getNewPassword()) == false) {

			errorMsg.add(EmployeeMessageConstants.ValidatePasswordPattern.getLabel().toString());
		}
		return errorMsg;
	}
	/*
	 * public Set<String> verifyEmail(String email) { errorMsg.clear(); EmployeeDao
	 * dao = new EmployeeDao(); String emailId = dao.verifyEmailId(email); if
	 * (Util.isEmptyOrNull(emailId) || !Util.validateEmail.test(email)) {
	 * logger.debug("email id not found"); return null; } if
	 * (Util.isEmptyOrNull(emailId) || !Util.validateEmail.test(email)) {
	 * errorMsg.add(EmployeeMessageConstants.WrongEmailId.getLabel()); } return
	 * errorMsg;
	 * 
	 * }
	 */

	public Set<String> verifyEmailId(String email) throws AppException, DbException {
		errorMsg.clear();
		UserDao dao = new UserDao();
		logger.debug(dao.getUser(email));
		if (Util.isEmptyOrNull(email) || !Util.validateEmail.test(email) || Util.isEmptyOrNull(dao.getUser(email))) {
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
	
	public LocalDate dateFormatter(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
		
	}

	public Set<String> validateEmployeeBasicDetails(User user) throws AppException, DbException {
		errorMsg.clear();
		if (Util.isEmptyOrNull(user.getEmpCode())) {
			errorMsg.add(EmployeeMessageConstants.BlankEmpCode.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(user.getFirstName())) {
			errorMsg.add(EmployeeMessageConstants.BlankFirstName.getLabel().toString());
			return errorMsg;
		}
		
		if (Util.isEmptyOrNull(user.getLastName())) {
			errorMsg.add(EmployeeMessageConstants.BlankLastName.getLabel().toString());
			return errorMsg;
		}
		
		if (Util.isEmptyOrNull(user.getDob())) {
			errorMsg.add(EmployeeMessageConstants.BlankDob.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(user.getDoj())) {
			errorMsg.add(EmployeeMessageConstants.BlankDoj.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(user.getPassword())) {
			errorMsg.add(EmployeeMessageConstants.BlankPassword.getLabel().toString());
			return errorMsg;
		}
		if (validatePattern(user.getPassword()) == false) {

			errorMsg.add(EmployeeMessageConstants.ValidatePasswordPattern.getLabel().toString());
			return errorMsg;
		}

		if (!Util.validateEmail.test(user.getEmail())) {
			errorMsg.add(EmployeeMessageConstants.ValidateEmail.getLabel().toString());
			return errorMsg;
		}

		if (Util.isEmptyOrNull(user.getEmail())) {
			errorMsg.add(EmployeeMessageConstants.BlankEmail.getLabel().toString());
			return errorMsg;
		}
		if (!Util.isValidDate(user.getDob().toString())) {
			errorMsg.add(EmployeeMessageConstants.InvalidDateFormat.getLabel().toString());
			return errorMsg;
		}
		if (!Util.isValidDate(user.getDoj().toString())) {
			errorMsg.add(EmployeeMessageConstants.InvalidDateFormat.getLabel().toString());
			return errorMsg;
		
		}


		/*
		 * else {
		 * 
		 * if(checkEmployee==false) {
		 * errorMsg.add(EmployeeMessageConstants.EmployeeAlreadyExist.getLabel().
		 * toString()); return errorMsg; } if(checkEmployee==true) {
		 * dao.insertEmployee(user); }
		 * errorMsg.add(EmployeeMessageConstants.EmployeeAdded.getLabel().toString()); }
		 */
		return errorMsg;

	}
	
	public Set<String> validateEmployeeAddress(User user)throws AppException, DbException {	
	errorMsg.clear();	
	for (EmployeeAddressInfo addressInfo : user.getAddressInfo()) {

		if (Util.isEmptyOrNull(addressInfo.getAddressType())) {
			errorMsg.add(EmployeeMessageConstants.BlankAddressType.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(addressInfo.getCity())) {
			errorMsg.add(EmployeeMessageConstants.BlankCity.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(addressInfo.getCountry())) {
			errorMsg.add(EmployeeMessageConstants.BlankCountry.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(addressInfo.getState())) {
			errorMsg.add(EmployeeMessageConstants.BlankState.getLabel().toString());
			return errorMsg;
		}
		if (Util.isEmptyOrNull(addressInfo.getStreet1()) || Util.isEmptyOrNull(addressInfo.getStreet2())) {
			errorMsg.add(EmployeeMessageConstants.BlankStreet.getLabel().toString());
			return errorMsg;
		}
	}
	return errorMsg;

}
	public Set<String> validateEmployeePhone(User user){
		errorMsg.clear();
		for (EmployeePhoneInfo phoneInfo : user.getPhoneInfo()) {
			if (Util.isEmptyOrNull(phoneInfo.getPhoneNumber())) {
				errorMsg.add(EmployeeMessageConstants.BlankPhoneNumber.getLabel().toString());
				return errorMsg;
			}
			if (Util.isEmptyOrNull(phoneInfo.getPhoneNumberType())) {
				errorMsg.add(EmployeeMessageConstants.BlankPhoneType.getLabel().toString());
				return errorMsg;
			}
		}
		return errorMsg;
		
	}
	
	public Set<String> validateEmployeeProjects(User user){
		errorMsg.clear();
		for (EmployeeProjectInfo projectInfo : user.getProjectInfo()) {
			if (Util.isEmptyOrNull(projectInfo.getStartDate())) {
				errorMsg.add(EmployeeMessageConstants.BlankStartDate.getLabel().toString());
				return errorMsg;
			}
			if (Util.isEmptyOrNull(projectInfo.getEndDate())) {
				errorMsg.add(EmployeeMessageConstants.BlankEndDate.getLabel().toString());
				return errorMsg;
			}
			if(dateFormatter(projectInfo.getEndDate()).isBefore(dateFormatter(projectInfo.getStartDate()))) {
				errorMsg.add(EmployeeMessageConstants.StartDateEndDateRange.getLabel().toString());
				return errorMsg;
			}
			
			if (Util.isEmptyOrNull(projectInfo.getWorkingLocation())) {
				errorMsg.add(EmployeeMessageConstants.BlankWorkLocation.getLabel().toString());
				return errorMsg;
			}
			if (Util.isEmptyOrNull(projectInfo.getStatus())) {
				errorMsg.add(EmployeeMessageConstants.BlankProjectStatus.getLabel().toString());
				return errorMsg;
			}
		}
		return errorMsg;
		
	}
}
