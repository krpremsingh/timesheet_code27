package com.awcsoftware.spring.security.auth.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;

import com.awcsoftware.app.employee.EmployeeAddressInfo;
import com.awcsoftware.app.employee.EmployeePhoneInfo;
import com.awcsoftware.app.employee.EmployeeProjectInfo;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(User.class.getName());
	private int empId;

	private String firstName;

	private String middleName;

	private String lastName;

	private String empCode;

	private String dob;

	private String doj;

	private LocalDateTime addedOn;

	private LocalDateTime lastModifiedOn;

	private String email;

	private String password;

	private String currentPassword;

	private String confirmPassword;

	private String newPassword;

	private int firstLoginStatus;

	private String status;

	private int designationId;

	private String mailFlag;

	private String fromDate;

	private String toDate;

	private List<EmployeeAddressInfo> addressInfo;

	private List<EmployeePhoneInfo> phoneInfo;

	private List<EmployeeProjectInfo> projectInfo;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public LocalDateTime getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(LocalDateTime addedOn) {
		this.addedOn = addedOn;
	}

	public LocalDateTime getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getFirstLoginStatus() {
		return firstLoginStatus;
	}

	public void setFirstLoginStatus(int firstLoginStatus) {
		this.firstLoginStatus = firstLoginStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getMailFlag() {
		return mailFlag;
	}

	public void setMailFlag(String mailFlag) {
		this.mailFlag = mailFlag;
	}

	public List<EmployeeAddressInfo> getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(List<EmployeeAddressInfo> addressInfo) {
		this.addressInfo = addressInfo;
	}

	public List<EmployeePhoneInfo> getPhoneInfo() {
		return phoneInfo;
	}

	public void setPhoneInfo(List<EmployeePhoneInfo> phoneInfo) {
		this.phoneInfo = phoneInfo;
	}

	public List<EmployeeProjectInfo> getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(List<EmployeeProjectInfo> projectInfo) {
		this.projectInfo = projectInfo;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "User [empId=" + empId + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", empCode=" + empCode + ", dob=" + dob + ", doj=" + doj + ", addedOn=" + addedOn
				+ ", lastModifiedOn=" + lastModifiedOn + ", email=" + email + ", password=" + password
				+ ", currentPassword=" + currentPassword + ", confirmPassword=" + confirmPassword + ", newPassword="
				+ newPassword + ", firstLoginStatus=" + firstLoginStatus + ", status=" + status + ", designationId="
				+ designationId + ", mailFlag=" + mailFlag + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", addressInfo=" + addressInfo + ", phoneInfo=" + phoneInfo + ", projectInfo=" + projectInfo + "]";
	}

}
