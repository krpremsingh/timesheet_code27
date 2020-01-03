package com.awcsoftware.spring.security.auth;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.awcsoftware.session.store.TokenSession;
import com.awcsoftware.spring.security.auth.token.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogoutFilter extends AbstractAuthenticationProcessingFilter{
	static Logger log = Logger.getLogger(LogoutFilter.class.getName());
	
	public LogoutFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
        String header = request.getHeader("Authorization");
        log.debug("LogoutFilter header " + header);

        String authToken = "";
        if (header == null || !header.startsWith("Bearer ")) {
        	log.debug("No token");
        } else {
        	authToken = header.substring(7);
        	log.debug("LogoutFilter authToken " + authToken);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("token", authToken);
        return authenticate(token);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			javax.servlet.FilterChain chain, Authentication authResult) throws IOException, ServletException {
		log.debug("LogoutFilter - successfulAuthentication " + authResult);
		
		ObjectMapper mapper = new ObjectMapper();
		TokenSession.getTokenStore().invalidateToken(authResult.getCredentials().toString());
		response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), "Successfully logged out");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		log.debug("LogoutFilter - unsuccessfulAuthentication ");
		ObjectMapper mapper = new ObjectMapper();
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), "Logout attempt failed " + failed.getMessage());
	}
	
	private Authentication authenticate(Authentication auth) {
		log.debug("LogoutFilter - authenticate");
		String token = auth.getCredentials().toString();
		
		log.debug("LogoutFilter token " + token);
		if (token == null || token.equals("")) {
			 throw new
             BadCredentialsException("Authentication Token missing");
		}
		
		String username = ""; 
		// check for active token session
		if (TokenSession.getTokenStore().isActive(token)) {
			try {
				// retrieve from token store to reset the last accessed time
				username = TokenManager.parseToken(TokenSession.getTokenStore().getToken(token));
			} catch (RuntimeException e) {
				throw new
	            BadCredentialsException(e.getMessage());
			}
		} else {
			throw new
            BadCredentialsException("Logout Token not valid");
		}
		List<GrantedAuthority> authorityList = Collections.<GrantedAuthority>emptyList();
		return new UsernamePasswordAuthenticationToken
                (username, token, authorityList);
	}
}
