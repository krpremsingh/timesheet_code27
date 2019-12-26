package com.awcsoftware.spring.security.auth;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.awcsoftware.app.employee.EmployeeDao;
import com.awcsoftware.app.employee.EmployeeLoginTransaction;
import com.awcsoftware.app.employee.EmployeeMessageConstants;
import com.awcsoftware.app.employee.EmployeeService;
import com.awcsoftware.session.store.TokenSession;
import com.awcsoftware.spring.security.auth.token.TokenManager;
import com.awcsoftware.spring.security.auth.user.Role;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
	@Autowired
	EmployeeDao employeedao;
	
	@Autowired
	EmployeeService employeeservice;
	
	@Autowired
	EmployeeLoginTransaction logintransaction;
	
	static Logger log = Logger.getLogger(LoginFilter.class.getName());

	public LoginFilter(String defaultProcessUrl) {
		super(defaultProcessUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		log.debug("LoginFilter");

		ObjectMapper oMapper = new ObjectMapper();
		LoginRequest loginRequest = oMapper.readValue(request.getReader(), LoginRequest.class);
		log.debug("credential " + loginRequest.getEmail());
		log.debug("credential " + loginRequest.getPassword());
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
				loginRequest.getPassword(),Collections.emptyList());
		return authenticate(token);
	}

	private Authentication authenticate(Authentication auth) {
		log.debug("LoginFilter - authenticate");
		String username = auth.getName();
		String password = auth.getCredentials().toString();
        
		log.debug("LoginFilter " + username);
		log.debug("LoginFilter " + password);

		UserService uSrv = new UserService();

		if (uSrv.isExists(username)) {
			log.debug("========" + uSrv.isExists(username));
			User user = uSrv.getUser(username);
			List<Role> roles = uSrv.getRoles(user.getEmpId());
			log.debug("roles  " + roles);
			log.debug("email  " + user.getEmail());

			log.debug("User Found " + uSrv.isExists(username));

			if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
				 
				UserAuthenticationDetail authDetail = new UserAuthenticationDetail(username, password);
				logintransaction= new EmployeeLoginTransaction(user);
				log.debug("auutthhddeetails  "+authDetail+" "+logintransaction);
				
				 if(authDetail.isCredentialsNonExpired()==false) { 
					 employeeservice.setLoginTransactionIfFailed(logintransaction);
					 
					  throw new BadCredentialsException(EmployeeMessageConstants.PasswordExpired.getLabel().toString());
				}
				employeeservice.setLoginTransactionIfSuccess(logintransaction);   
				authDetail.setRole(roles);
				authDetail.setName(user.getFirstName());
				authDetail.setEmpId(user.getEmpId());
				authDetail.setEmpCode(user.getEmpCode());
				authDetail.setFirstLoginStatus(user.getFirstLoginStatus());
				authDetail.setDesignationId(user.getDesignationId());
				return authDetail;
			} 
		
			else {
				throw new BadCredentialsException(EmployeeMessageConstants.validatePassword.getLabel().toString());
			}
		} else {
			throw new BadCredentialsException(EmployeeMessageConstants.ValidateEmail.getLabel().toString());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			javax.servlet.FilterChain chain, Authentication authResult) throws IOException, ServletException {
		log.debug("LoginFilter - successfulAuthentication " + authResult);

		UserAuthenticationDetail auth = (UserAuthenticationDetail) authResult;
		ObjectMapper mapper = new ObjectMapper();
		String token = TokenManager.generateToken(auth.getName(), auth.getCredentials().toString());
		auth.setToken(token);

		TokenSession.getTokenStore().addAuthenticaionDetail(auth);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mapper.writeValue(response.getWriter(), auth);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		log.debug("LoginFilter - unsuccessfulAuthentication ");
		ObjectMapper mapper = new ObjectMapper();
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mapper.writeValue(response.getWriter(), failed.getMessage());
	}

}
