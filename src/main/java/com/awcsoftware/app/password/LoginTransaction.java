package com.awcsoftware.app.password;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.awcsoftware.spring.security.auth.user.User;

public class LoginTransaction {
	private int ltId;
	private int empId;
	private LocalDateTime lastLogin;
	private LocalDate lastPasswordChange;
	private User user;

	public LoginTransaction(User user) {
		this.user = user;
		this.lastLogin = LocalDateTime.now();
		this.empId = user.getEmpId();
	}
	public LoginTransaction() {
		
	}

	public int getLtId() {
		return ltId;
	}

	public void setLtId(int ltId) {
		this.ltId = ltId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public LocalDate getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(LocalDate lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
