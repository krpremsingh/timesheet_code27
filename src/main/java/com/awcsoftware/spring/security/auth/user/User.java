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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addedOn == null) ? 0 : addedOn.hashCode());
		result = prime * result + ((addressInfo == null) ? 0 : addressInfo.hashCode());
		result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((currentPassword == null) ? 0 : currentPassword.hashCode());
		result = prime * result + designationId;
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((doj == null) ? 0 : doj.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((empCode == null) ? 0 : empCode.hashCode());
		result = prime * result + empId;
		result = prime * result + firstLoginStatus;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastModifiedOn == null) ? 0 : lastModifiedOn.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((mailFlag == null) ? 0 : mailFlag.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneInfo == null) ? 0 : phoneInfo.hashCode());
		result = prime * result + ((projectInfo == null) ? 0 : projectInfo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (addedOn == null) {
			if (other.addedOn != null)
				return false;
		} else if (!addedOn.equals(other.addedOn))
			return false;
		if (addressInfo == null) {
			if (other.addressInfo != null)
				return false;
		} else if (!addressInfo.equals(other.addressInfo))
			return false;
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (currentPassword == null) {
			if (other.currentPassword != null)
				return false;
		} else if (!currentPassword.equals(other.currentPassword))
			return false;
		if (designationId != other.designationId)
			return false;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (doj == null) {
			if (other.doj != null)
				return false;
		} else if (!doj.equals(other.doj))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (empCode == null) {
			if (other.empCode != null)
				return false;
		} else if (!empCode.equals(other.empCode))
			return false;
		if (empId != other.empId)
			return false;
		if (firstLoginStatus != other.firstLoginStatus)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastModifiedOn == null) {
			if (other.lastModifiedOn != null)
				return false;
		} else if (!lastModifiedOn.equals(other.lastModifiedOn))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mailFlag == null) {
			if (other.mailFlag != null)
				return false;
		} else if (!mailFlag.equals(other.mailFlag))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} else if (!newPassword.equals(other.newPassword))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneInfo == null) {
			if (other.phoneInfo != null)
				return false;
		} else if (!phoneInfo.equals(other.phoneInfo))
			return false;
		if (projectInfo == null) {
			if (other.projectInfo != null)
				return false;
		} else if (!projectInfo.equals(other.projectInfo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [empId=" + empId + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", empCode=" + empCode + ", dob=" + dob + ", doj=" + doj + ", addedOn=" + addedOn
				+ ", lastModifiedOn=" + lastModifiedOn + ", email=" + email + ", password=" + password
				+ ", currentPassword=" + currentPassword + ", confirmPassword=" + confirmPassword + ", newPassword="
				+ newPassword + ", firstLoginStatus=" + firstLoginStatus + ", status=" + status + ", designationId="
				+ designationId + ", mailFlag=" + mailFlag + ", addressInfo=" + addressInfo + ", phoneInfo=" + phoneInfo
				+ ", projectInfo=" + projectInfo + "]";
	}



	

}
