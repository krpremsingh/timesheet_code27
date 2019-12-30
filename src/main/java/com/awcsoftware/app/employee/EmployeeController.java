package com.awcsoftware.app.employee;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserDao;

@RestController
public class EmployeeController {
	final static Logger logger = Logger.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeservice;

	@Autowired
	UserDao userdao;

	@Bean
	PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
     
	
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> resetPassword(@RequestBody User user, HttpServletRequest request) {
		try {
			return new ResponseEntity<String>(employeeservice.resetPassword(user).toString(), HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/auth/forgotPassword", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> forgotPassword(@RequestBody User user, HttpServletRequest request)
			throws MessagingException {
		try {
			
			return new ResponseEntity<String>(employeeservice.sendEmail(user.getEmail(), request), HttpStatus.OK);
		} catch (DbException e) {
			return new ResponseEntity<String>("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@RequestMapping(value = "/auth/confirm-reset", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<String> validateResetToken(@RequestParam("token") String confirmationToken) {
		try {

			return new ResponseEntity<String>(employeeservice.findByToken(confirmationToken), HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/auth/changePassword", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> changePassword(@RequestBody User user) {
		try {
			return new ResponseEntity<String>(employeeservice.changePassword(user).toString(), HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (MessagingException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(value="/auth/insertEmployee",method=RequestMethod.POST)
	public ResponseEntity<String> insertEmployee(@RequestBody User user){
		try {
			return new ResponseEntity<String>(employeeservice.insertEmployee(user).toString(), HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);	
	}
		
	}

}
