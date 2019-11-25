package com.awcsoftware.app.password;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;

@Service
public class UserPasswordService {
	final static Logger logger = Logger.getLogger(UserPasswordController.class);
	
	@Bean
	PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
	
	public boolean resetPassword(User model)throws AppException,DbException {
		UserPasswordDao dao = new UserPasswordDao();
		if(model.getFirstLoginStatus()==0) {
			model.setFirstLoginStatus(1);
		 }
		model.setPassword(passwordencoder().encode(model.getPassword()));
		boolean e = dao.resetPassword(model);
		return e;
		
	}
	public boolean verifyEmailId(String email)throws AppException,DbException {
		UserPasswordDao dao = new UserPasswordDao();
		String emailId = dao.verifyEmailId(email);
		if(Util.validateString.test(email)||Util.validateEmail.test(email)||!emailId.equalsIgnoreCase(email)) {
			return true;
		}
		return false;	
	}
	
	public boolean saveToken(ConfirmationToken token)throws AppException,DbException {
		UserPasswordDao dao = new UserPasswordDao();
		if(dao.checkToken(token.getToken())!=null) {
		
		}
		boolean e = dao.saveToken(token);
		if(e==true) {
			return true;
		}
		return false;
		
	}
	public boolean updateToken(ConfirmationToken token)throws AppException,DbException {
		UserPasswordDao dao = new UserPasswordDao();
		if(dao.updateToken(token)==true) {
			return true;
		}
		return false;
		
	}
	public boolean checkToken(String email)throws AppException,DbException {
		UserPasswordDao dao = new UserPasswordDao();
		ConfirmationToken e = dao.checkToken(email);
		if(Util.isEmptyOrNull(e)) {
		return false;	
		}
		return true;
		
	}
	public String findByToken(String token)throws AppException,DbException {
		UserPasswordDao dao = new UserPasswordDao();
		 ConfirmationToken result = dao.findByToken(token);
		 if(result.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
			 return "token expired"; 
		 }
		 if(Util.isEmptyOrNull(result)) {
			return null; 
		 }
		return "token valid";
		
	}
}
