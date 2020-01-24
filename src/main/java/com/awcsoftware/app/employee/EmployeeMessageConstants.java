package com.awcsoftware.app.employee;

public enum EmployeeMessageConstants {
    InvalidPassword("New & confirm password must be same"),
	InvalidCurrentPassword("Current password is wrong"),
	WrongEmailId("Email id does not exist"),
	TokenMissing("Authentication Token missing"),
	PasswordChanged("Password changed"),
	BlankEmpCode("Employee code can't be blank"),
	BlankFirstName("First Name can't be blank"),
	BlankLastName("Last Name can't be blank"),
	BlankStartDate("Project start date can't be blank"),
	BlankEndDate("Project end date can't be blank"),
	BlankWorkLocation("Work location can't be blank"),
	BlankProjectStatus("Project status can't be blank"),
	StartDateEndDateRange("End date can't be less than start date"),
	EmailIdCantBeChanged("Can't update the email id"),
	EmployeeNotFound("Employee does'nt exist"),
	EmployeeCodeCantbechanged("Can't update the employee code"),
	EmployeeUpdated("Employee updated successfully"),
	BlankDob("Date of birth can't be blank"),
	BlankDoj("Date of Joining can't be blank"),
	BlankEmail("Email id can't be blank"),
	BlankAddressType("Address type can't be blank"),
	BlankPincode("Pincode can't be blank"),
	BlankStreet("Address can't be blank"),
	DojNotBeforeDob("Date of joining can't before Date of birth"),
	BlankLandmark("Landmark can't be blank"),
	BlankCountry("Country can't be blank"),
	BlankState("State can't be blank"),
	BlankCity("City can't be blank"),
	BlankPhoneNumber("Phone number can't be blank"),
	BlankPhoneType("Phone type can't be blank"),
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
	ValidatePasswordPattern("Password Must contain at least one uppercase and one lowercase letter and one number digit (ex: 0, 1, 2, 3, etc.) and one following special character (#@$*%&) and length must be equal to or greater than 8 characters and less than or equal to 20 characters"),
	ValidateEmail("Please enter correct email id"),
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
