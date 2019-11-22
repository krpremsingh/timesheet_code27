package com.awcsoftware.token.factory;

public class TokenFactory {
	
	public static Token getTokenImpl(TokenType type) {
		Token token = null;
		
		switch (type) {
		case Jwt:
			token = new JwtToken();
			break;
		case Base64:
			token = new Base64Token();
			break;
		
		default:
			//TODO
			break;	
		}
		return token;
	}

}
