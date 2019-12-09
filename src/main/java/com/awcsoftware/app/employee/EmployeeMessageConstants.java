package com.awcsoftware.app.employee;

public enum EmployeeMessageConstants {
    InvalidPassword("Confirm & new password should be same"),
	InvalidCurrentPassword("Current password is wrong"),
	WrongEmailId("Email id does not exist"),
	SentEmail("Email send successfully"),
	PasswordChanged("Password changed successfully"),
	TokenExpired("Token has expired"),
	CheckToken("No token found"),
	LinkValid("Link is valid"),
	LinkExpired("Link has been expired"),
	SameAsCurrentPassword("New and current password can't be same"),
	ValidEmail("Email id is correct"),
	ValidatePasswordPattern("Password must contain atleast one special character,one lower,one upper case ,one digit and length must be greater or equal to 8 characters");
	
	
	private final String label;

	public String getLabel() {
		return label;
	}

	private EmployeeMessageConstants(String label) {
		this.label = label;
	}
	
}
