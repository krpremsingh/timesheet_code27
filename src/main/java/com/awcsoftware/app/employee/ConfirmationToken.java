package com.awcsoftware.app.employee;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.awcsoftware.app.AppConstant;
import com.awcsoftware.spring.security.auth.token.TokenManager;
import com.awcsoftware.spring.security.auth.user.User;

/*
 * set the token expiry time in confirmation token
 * set current date as tokencreationdate
 * take instance of principal in constructor 
 */
public class ConfirmationToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private int tokenId;
	private String token;
	private LocalDateTime tokenCreationDate;
	private LocalDateTime tokenExpiryDate;

	public ConfirmationToken(User user) {
		this.user = user;
		this.token = TokenManager.generateToken(user.getEmail(), user.getPassword());
		this.tokenCreationDate = LocalDateTime.now();
		this.tokenExpiryDate = tokenCreationDate.plusHours(AppConstant.WORKING_HOURS.TwentyFour.getValue());
	}

	public ConfirmationToken() {
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getTokenCreationDate() {
		return tokenCreationDate;
	}

	public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
		this.tokenCreationDate = tokenCreationDate;
	}

	public LocalDateTime getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	public void setTokenExpiryDate(LocalDateTime tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}

}
