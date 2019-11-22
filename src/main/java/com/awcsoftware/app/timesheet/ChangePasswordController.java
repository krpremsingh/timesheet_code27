package com.awcsoftware.app.timesheet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;

@RestController
public class ChangePasswordController {
	final static Logger logger = Logger.getLogger(ChangePasswordController.class);
    
	ChangePasswordService service = new ChangePasswordService();
	
	@Bean
	PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> setupPassword(@RequestBody User user, HttpServletRequest request)
			throws AppException, DbException {
		
		service.resetPassword(user);
		return new ResponseEntity<String>("password changed successfully", HttpStatus.OK);
	}
	
	@RequestMapping(value="/generateEmail",method=RequestMethod.POST,headers = "Accept=application/json")
	public ResponseEntity<String> generateEmail(@RequestBody User user, HttpServletRequest request){
		boolean e = service.verifyEmailId(user.getEmail());
		logger.debug("email id found "+e);
		if(e==true) {
			ConfirmationToken token = new ConfirmationToken(user);
			boolean checktoken = service.checkToken(token.getUser().getEmail());
			logger.debug("checktoken " +checktoken);
			if(checktoken==true) {
				service.updateToken(token);
				logger.debug("update token ");
			}
			else if(checktoken==false) {
			service.saveToken(token);
			logger.debug("update token");
			}
		}
			return new ResponseEntity<String>("email id not found", HttpStatus.OK);		
	}
	
}
