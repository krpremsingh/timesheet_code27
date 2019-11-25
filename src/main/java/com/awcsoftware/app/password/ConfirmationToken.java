package com.awcsoftware.app.password;

import java.time.LocalDateTime;
import java.util.UUID;

import com.awcsoftware.spring.security.auth.user.User;

public class ConfirmationToken {
	private User user;
	private int tokenId;
	private String token;
	private LocalDateTime tokenCreationDate;
	private LocalDateTime tokenExpiryDate;

	public ConfirmationToken(User user) {
		this.user = user;
		this.token = UUID.randomUUID().toString();
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
