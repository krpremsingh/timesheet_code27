package com.awcsoftware.app.employee;

public enum EmployeeMessageConstants {
    InvalidPassword("New & confirm password must be same"),
	InvalidCurrentPassword("Current password is wrong"),
	WrongEmailId("Email id does not exist"),
	PasswordChanged("Password changed"),
	BlankEmpCode("Employee code can't be blank"),
	BlankFirstName("First Name can't be blank"),
	BlankLastName("Last Name can't be blank"),
	BlankDob("Date of birth can't be blank"),
	BlankDoj("Date of Joining can't be blank"),
	BlankEmail("Email id can't be blank"),
	InvalidDateFormat("Date format is invalid"),
	TokenExpired("Please login to continue"),
	CheckToken("No token found"),
	LinkValid("Link is valid"),
	LinkExpired("Link has been expired"),
	SameAsCurrentPassword("New and current password can't be same"),
	ValidEmail("Email id is correct"),
	BlankPassword("Password can't be blank"),
	AccessDenied("Access Denied"),
	PasswordExpired("Password has been expired"),
	ValidatePasswordPattern("Password Must contain at least one uppercase and one lowercase letter and one number digit (ex: 0, 1, 2, 3, etc.) and one following special character (@#$%!&*) and length must be equal to or greater than 8 characters and less than or equal to 20 characters"),
	ValidateEmail("Wrong email address"),
	EmployeeAlreadyExist("Employee already exist"),
	LoginSuccess("Success"),
	EmployeeAdded("Employee added successfully"),
	LoginFailed("Failed"),
	LoginSuccessReason("Credentials matched"),
	validatePassword("Wrong Password");
                                 
	
	private final String label;

	public String getLabel() {
		return label;
	}

	private EmployeeMessageConstants(String label) {
		this.label = label;
	}
	
}
