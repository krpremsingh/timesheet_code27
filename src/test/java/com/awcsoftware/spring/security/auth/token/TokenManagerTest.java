package com.awcsoftware.spring.security.auth.token;

public class TokenManagerTest {
	
	public static void main(String args[]) {
		
		String t = TokenManager.generateToken("p", "p");
		
		
		System.out.println("token " + t);
		
		System.out.println("token parsed " + TokenManager.parseToken(t));
		
	}

}
