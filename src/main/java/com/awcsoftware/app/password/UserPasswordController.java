package com.awcsoftware.app.password;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mail.MailConfig;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserService;

@RestController
public class UserPasswordController {
	final static Logger logger = Logger.getLogger(UserPasswordController.class);

	MailConfig config = new MailConfig();

	UriComponents uriComponents = null;

	UserPasswordService service = new UserPasswordService();

	@Bean
	PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> resetPassword(@RequestBody User user, HttpServletRequest request)
		{
		try {
			service.resetPassword(user);
			return new ResponseEntity<String>("password changed successfully", HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@RequestMapping(value = "/generateEmail", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> generateEmail(@RequestBody User user, HttpServletRequest request) {
		UserService userservice = new UserService();
		boolean result = false;
		try {

			user = userservice.getUser(user.getEmail());
            logger.debug(user);
			if (Util.isEmptyOrNull(user)) {
				return new ResponseEntity<String>("email id not found", HttpStatus.NOT_FOUND);
			}
			result=service.verifyEmailId(user.getEmail());
			logger.debug("email id found " + result);
			if (result == true) {
				ConfirmationToken token = new ConfirmationToken(user);
				boolean checktoken = service.checkToken(token.getUser().getEmail());
				logger.debug("checktoken " + checktoken);
				if (checktoken == true) {
					service.updateToken(token);
					logger.debug("update token ");
				} else if (checktoken == false) {
					service.saveToken(token);
					logger.debug("update token");
				}

				uriComponents = UriComponentsBuilder.newInstance().scheme(request.getScheme())
						.host(request.getServerName()).port(request.getServerPort()).path("/changePassword")
						.queryParam("token", token.getToken()).build();
				String body = "<table>\r\n" + "    <tbody>\r\n" + "        <tr>\r\n" + "            <td>Hi,</td>\r\n"
						+ user.getFirstName() + "        </tr>\r\n" + "        <tr>\r\n"
						+ "            <td>You recently requested to reset your password for your AWC acount. Click the link below to reset it</td>\r\n"
						+ "        </tr>\r\n" + "        <tr>\r\n"
						+ "            <td>If you did not request a password reset, please reply to let us know. The password reset link is only valid for next 24 hours.:</td>\r\n"
						+ "        </tr>\r\n" + "        <tr>\r\n" + uriComponents + "        </tr>\r\n"
						+ "    </tbody>\r\n" + "</table>";
				MimeMessage message = config.javaMailSender().createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(user.getEmail());
				// helper.setText(body);
				helper.setSubject("Change Password Request");
				helper.setText(body, true);
				config.javaMailSender().send(message);	
			}

		} catch (DbException e) {
			return new ResponseEntity<String>("Database Error", HttpStatus.NOT_FOUND);

		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return new ResponseEntity<String>("email sent successfully", HttpStatus.OK);
	}
	
	@RequestMapping(value="/changePassword" ,method=RequestMethod.GET,headers = "Accept=application/json")
	public ResponseEntity<String> changePassword(ConfirmationToken confirmationtoken,HttpServletRequest request) {
		String token = request.getQueryString().substring(6);
		logger.debug("confiramtion token "  +token);
		try {
			service.findByToken(token);
			logger.debug("------"+service.findByToken(token));
			return new ResponseEntity<String>("token valid", HttpStatus.OK);	
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (DbException e) {
			return new ResponseEntity<String>("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
