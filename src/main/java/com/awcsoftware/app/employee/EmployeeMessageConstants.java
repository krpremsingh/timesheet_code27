package com.awcsoftware.app.employee;

public enum EmployeeMessageConstants {
    InvalidPassword("Confirm & new password should be same"),
	InvalidCurrentPassword("Current password is wrong"),
	WrongEmailId("Email id does not exist"),
	SendEmail("Email send successfully"),
	PasswordChanged("Password changed successfully"),
	TokenExpired("Token expired"),
	CheckToken("No token found"),
	LinkValid("Link is valid"),
	LinkExpired("Link has been expired"),
	SameAsCurrentPassword("New and current password can't be same"),
	ValidEmail("Email id is correct"),
	PasswordNotNull("Password can't be blank"),
	AccessDenied("Access Denied"),
	ValidatePasswordPattern("Your password must be at least 8 characters long, contain at least one number, one special character and have a mixture of uppercase and lowercase letters."),
	ValidateEmail("Incorrect email address"),
	validatePassword("Wrong Password");
	
	private final String label;

	public String getLabel() {
		return label;
	}

	private EmployeeMessageConstants(String label) {
		this.label = label;
	}
	
}
