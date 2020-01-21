package com.awcsoftware.app.mail;

import java.io.Serializable;
import java.util.List;

import com.awcsoftware.spring.security.auth.user.User;

public class MailPojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<User> userList;
	private int empId;
	private String sendTo;

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	@Override
	public String toString() {
		return "MailPojo [userList=" + userList + ", empId=" + empId + ", sendTo=" + sendTo + "]";
	}

}
