package com.awcsoftware.spring.security.auth.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;

/*
 * check if user is exists in database
 * get principal based on email id
 * get list of assigned roles of principal
 * principal can reset the password if forced or not forced(with method reset password)
 * User can generate password if forgot with method changePassword
 * 
 */

@Component
public class UserService {
	static Logger log = Logger.getLogger(UserService.class.getName());
	boolean valid = false;
	UriComponents uriComponents = null;

	@Bean
	PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

	public User getUser(String username) {
		UserDao dao = new UserDao();
		User user = dao.getUser(username);
		return user;
	}

	public boolean isExists(String username) {
		UserDao dao = new UserDao();
		boolean e = dao.isExists(username);
		return e;
	}

	public List<Role> getRoles(int empId) {
		UserDao dao = new UserDao();
		List<Role> roles = dao.getRoles(empId);
		return roles;

	}



}