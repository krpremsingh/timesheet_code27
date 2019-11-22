package com.awcsoftware.session.store;

import java.time.LocalDateTime;

import com.awcsoftware.spring.security.auth.UserAuthenticationDetail;


public class TokenInfo {
	
	private LocalDateTime lastAccessedTime;
	private LocalDateTime creationime;
	private long lastAccessedMillis;
	private boolean loggedOut = false;
	private UserAuthenticationDetail authenticationDetail;
	
	public LocalDateTime getLastAccessedTime() {
		return lastAccessedTime;
	}
	public void setLastAccessedTime(LocalDateTime lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}
	public LocalDateTime getCreationime() {
		return creationime;
	}
	public void setCreationime(LocalDateTime creationime) {
		this.creationime = creationime;
	}
	public long getLastAccessedMillis() {
		return lastAccessedMillis;
	}
	public void setLastAccessedMillis(long lastAccessedMillis) {
		this.lastAccessedMillis = lastAccessedMillis;
	}
	public boolean isLoggedOut() {
		return loggedOut;
	}
	public void setLoggedOut(boolean loggedOut) {
		this.loggedOut = loggedOut;
	}
	public UserAuthenticationDetail getAuthenticationDetail() {
		return authenticationDetail;
	}
	public void setAuthenticationDetail(UserAuthenticationDetail authenticationDetail) {
		this.authenticationDetail = authenticationDetail;
	}
}
