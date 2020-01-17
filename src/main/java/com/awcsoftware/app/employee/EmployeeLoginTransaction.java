package com.awcsoftware.app.employee;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.awcsoftware.app.AppConstant;
import com.awcsoftware.spring.security.auth.user.User;

@Component
public class EmployeeLoginTransaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private int empId;
	private LocalDateTime loginTimestamp;
	//private String loginToken;
	private String activityStatus;
	private String statusReason;
	private LocalDateTime lastPasswordChange;
	private LocalDateTime passwordExpiryDate;

	public EmployeeLoginTransaction() {
	}

	public EmployeeLoginTransaction(User user) {
		super();
		this.user = user;
		this.empId=user.getEmpId();
		this.loginTimestamp = AppConstant.TIME_FORMAT_CONST.DATETIME_FORMAT(LocalDateTime.now());
		this.lastPasswordChange = AppConstant.TIME_FORMAT_CONST.DATETIME_FORMAT(LocalDateTime.now());
		this.passwordExpiryDate = AppConstant.TIME_FORMAT_CONST.DATETIME_FORMAT(LocalDateTime.now()).plusMonths(AppConstant.WORKING_HOURS.Two.getValue());
	}


	public User getUser() {
		return user;
	}
	
/*	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}*/

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public LocalDateTime getPasswordExpiryDate() {
		return passwordExpiryDate;
	}

	public void setPasswordExpiryDate(LocalDateTime passwordExpiryDate) {
		this.passwordExpiryDate = passwordExpiryDate;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public LocalDateTime getLoginTimestamp() {
		return loginTimestamp;
	}

	public void setLoginTimestamp(LocalDateTime loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

}
