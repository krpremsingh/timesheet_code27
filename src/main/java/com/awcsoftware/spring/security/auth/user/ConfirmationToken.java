package com.awcsoftware.spring.security.auth.user;

import java.time.LocalDateTime;

import com.awcsoftware.spring.security.auth.token.TokenManager;

public class ConfirmationToken {
	private User user;
	private int tokenId;
	private String token;
	private LocalDateTime tokenCreationDate;
	private LocalDateTime tokenExpiryDate;

	public ConfirmationToken(User user) {
		this.user = user;
		this.token = TokenManager.generateToken(user.getEmail(), user.getPassword());
		this.tokenCreationDate = LocalDateTime.now();
		this.tokenExpiryDate = tokenCreationDate.plusHours(24);
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
