package com.awcsoftware.spring.security.auth.token;

import com.awcsoftware.token.factory.Token;
import com.awcsoftware.token.factory.TokenFactory;
import com.awcsoftware.token.factory.TokenType;

public class TokenManager {
	
	static Token objToken;
	
	static {
		// hard coded to return JWT type
		//objToken = new JwtToken();
		objToken = TokenFactory.getTokenImpl(TokenType.Jwt);
	}
	
	public static String generateToken(String username, String password) {
		return objToken.generateToken(username, password);
	}
	
	public static String parseToken(String token) {
		return objToken.parseToken(token);
	}
}
