package com.awcsoftware.app.employee;

public enum EmployeeMessageConstants {
    InvalidPassword("New & confirm password must be same"),
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
	ValidatePasswordPattern("Password Must contain at least one uppercase and one lowercase letter and one number digit (ex: 0, 1, 2, 3, etc.) and one following special character (@#$%!&*) and length must be equal to or greater than 8 characters and less than or equal to 20 characters"),
	ValidateEmail("Wrong email address"),
	validatePassword("Wrong Password");
	
	private final String label;

	public String getLabel() {
		return label;
	}

	private EmployeeMessageConstants(String label) {
		this.label = label;
	}
	
}
