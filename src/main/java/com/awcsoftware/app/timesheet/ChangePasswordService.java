package com.awcsoftware.app.timesheet;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.awcsoftware.app.Util;
import com.awcsoftware.spring.security.auth.user.User;

@Service
public class ChangePasswordService {
	final static Logger logger = Logger.getLogger(ChangePasswordController.class);
	
	@Bean
	PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
	
	public boolean resetPassword(User model) {
		ChangePasswordDao dao = new ChangePasswordDao();
		if(model.getFirstLoginStatus()==0) {
			model.setFirstLoginStatus(1);
		 }
		model.setPassword(passwordencoder().encode(model.getPassword()));
		boolean e = dao.resetPassword(model);
		return e;
		
	}
	public boolean verifyEmailId(String email) {
		ChangePasswordDao dao = new ChangePasswordDao();
		String emailId = dao.verifyEmailId(email);
		if(Util.validateString.test(email)||Util.validateEmail.test(email)||!emailId.equalsIgnoreCase(email)) {
			return true;
		}
		return false;	
	}
	
	public boolean saveToken(ConfirmationToken token) {
		ChangePasswordDao dao = new ChangePasswordDao();
		if(dao.checkToken(token.getToken())!=null) {
		
		}
		boolean e = dao.saveToken(token);
		if(e==true) {
			return true;
		}
		return false;
		
	}
	public boolean updateToken(ConfirmationToken token) {
		ChangePasswordDao dao = new ChangePasswordDao();
		if(dao.updateToken(token)==true) {
			return true;
		}
		return false;
		
	}
	public boolean checkToken(String email) {
		ChangePasswordDao dao = new ChangePasswordDao();
		ConfirmationToken e = dao.checkToken(email);
		if(Util.isEmptyOrNull(e)) {
		return false;	
		}
		return true;
		
	}
}
