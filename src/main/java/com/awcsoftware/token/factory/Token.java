package com.awcsoftware.token.factory;

public interface Token {

	public String generateToken(String username, String password);
	public String parseToken(String token);
	
}
