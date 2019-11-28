package com.awcsoftware.spring.security.auth.user;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.mail.MailConfig;

@RestController
public class UserPasswordController {
	final static Logger logger = Logger.getLogger(UserPasswordController.class);

	UserService service = new UserService();

	@Bean
	PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> resetPassword(@RequestBody User user, HttpServletRequest request) {
		try {

			service.resetPassword(user);
		   return new ResponseEntity<String>("password changed successfully", HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody User user, HttpServletRequest request) {
		UserService userservice = new UserService();
		MailConfig config = new MailConfig();
		boolean result = false;
		try {
		      boolean e = userservice.isExists(user.getEmail());
		      if(e==false) {
		    	 return new ResponseEntity<String>("email does not exist", HttpStatus.INTERNAL_SERVER_ERROR); 
		      }
			result = service.verifyEmailId(user.getEmail());
			logger.debug("email exists " + result);
			if (result == true) {
				user=userservice.getUser(user.getEmail());
				ConfirmationToken ctoken = new ConfirmationToken(user);
				boolean checktoken = service.checkToken(ctoken.getUser().getEmail());
				logger.debug("checktoken " + checktoken);
				if (checktoken == true) {
					service.updateToken(ctoken);
					logger.debug("update token ");
				} else if (checktoken == false) {
					service.saveToken(ctoken);
					logger.debug("update token");
				}
				
				MimeMessage message = config.javaMailSender().createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(user.getEmail());
				helper.setSubject("Change Password Request");
				helper.setText(service.emailContent(ctoken, request), true);
				config.javaMailSender().send(message);
			}

		} catch (DbException e) {
			return new ResponseEntity<String>("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<String>("email sent successfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/confirm-reset", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<String> validateResetToken(@RequestParam("token") String confirmationToken) {
		try {
			String result = service.findByToken(confirmationToken);
			if (result.equals("token valid") && !Util.isEmptyOrNull(result)) {
				return new ResponseEntity<String>("link valid", HttpStatus.OK);
			}
			return new ResponseEntity<String>("link Expired", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
