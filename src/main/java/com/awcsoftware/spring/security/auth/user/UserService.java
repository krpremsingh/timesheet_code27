package com.awcsoftware.spring.security.auth.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;

@Component
public class UserService {
	static Logger log = Logger.getLogger(UserService.class.getName());

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
	
	public List<Role> getRoles(int empId){
		UserDao dao = new UserDao();
		List<Role> roles = dao.getRoles(empId);
		return roles;
		
	}
	
	public boolean resetPassword(User model)throws AppException,DbException {
		UserDao dao = new UserDao();
		boolean e = dao.resetPassword(model);
		return e;
		
	}
	public boolean verifyEmailId(String email)throws AppException,DbException {
		UserDao dao = new UserDao();
		String emailId = dao.verifyEmailId(email);
		if(Util.validateEmail.test(email)||!emailId.equalsIgnoreCase(email)) {
			return true;
		}
		return false;	
	}
	
	public boolean saveToken(ConfirmationToken token)throws AppException,DbException {
		UserDao dao = new UserDao();
		if(Util.isEmptyOrNull(dao.checkToken(token.getToken()))) {
			dao.saveToken(token);
			return true;
		}
		return false;		
	}
	public boolean updateToken(ConfirmationToken token)throws AppException,DbException {
		UserDao dao = new UserDao();
		if(dao.updateToken(token)==true) {
			return true;
		}
		return false;
		
	}
	public boolean checkToken(String email)throws AppException,DbException {
		UserDao dao = new UserDao();
		ConfirmationToken e = dao.checkToken(email);
		if(Util.isEmptyOrNull(e)) {
		return false;	
		}
		return true;
		
	}
	public String findByToken(String token)throws AppException,DbException {
		UserDao dao = new UserDao();
		 ConfirmationToken result = dao.findByToken(token);
		 if(result!=null) {
			 if(result.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
				 return "token expired"; 
			 } 
		 }
		 else if(Util.isEmptyOrNull(result)) {
			return "no token found"; 
		 }
		return "token valid";
		
	}
	public String emailContent(ConfirmationToken confirmationToken,HttpServletRequest request) {
		
		uriComponents = UriComponentsBuilder.newInstance().scheme(request.getScheme())
				.host(request.getServerName()).port(request.getServerPort()).path("/timesheet/confirm-reset")
				.queryParam("token", confirmationToken.getToken()).build();
		
		String body = "<table>\r\n" + "    <tbody>\r\n" + "        <tr>\r\n" + "            <td>Hi,</td>\r\n"
				+ confirmationToken.getUser().getFirstName() + "        </tr>\r\n" + "        <tr>\r\n"
				+ "            <td>You recently requested to reset your password for your AWC account. Click the link below to reset it</td>\r\n"
				+ "        </tr>\r\n" + "        <tr>\r\n"
				+ "            <td>If you did not request a password reset, please reply to let us know. The password reset link is only valid for next 24 hours.:</td>\r\n"
				+ "        </tr>\r\n" + "        <tr>\r\n" + uriComponents + "        </tr>\r\n"
				+ "    </tbody>\r\n" + "</table>";
		return body;
		
	}

}