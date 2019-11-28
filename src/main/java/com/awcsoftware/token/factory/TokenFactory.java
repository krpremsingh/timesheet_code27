package com.awcsoftware.token.factory;

import java.util.UUID;

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
			
		case UUID:
			token: UUID.randomUUID().toString();
		    break;
		
		default:
			//TODO
			break;	
		}
		return token;
	}

}
