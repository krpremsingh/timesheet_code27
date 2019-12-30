package com.awcsoftware.app.timesheet;


public enum TimecardMessageConstant {

	Invalid_Timecard("Selected Timecard doesn't exist"),
	Blank_Emp_Id("Invalid employee"),
	Blank_Start_Date("Week start date is not valid "),
	Blank_End_Date("Week end date is not valid "),
	Invalid_Date("Invalid Date, Date must be in YYYY-MM-DD Format"),
	Blank_Approval_Details("approval details is empty, kindly contact adminstrator and try again"),
	Daily_Working_Limit("Working hours cannot exceed by 24 hours for a day"),	
	Blank_Working_Date("Invalid working date"),
	BlankActivity("Select an activity"),
	BlankLocation("Select a location"),
	BlankProject("Select a project"),
	MimimunTimecards("Add 5 days data to submit a timesheet"),
	SingleDayWorkingHourValidationDuringSubmit("Single day working hours can't be less than 8 hours"),
	BlankTimecardDetails("Enter atleast one day to save a timecard"),
	BlankDayTimecardDetails("Enter atleast one day to save a timecard"),
	EditApplicableforDraftAndRejected("Edit is only allowed for draft and rejected status"),

	//Logged-in user and update employee are not equal
	
	DifferentEmployeeIdAndLoggedInUser("User is not authorized to save timecard for other users"),		
	
	WorkDateNotInWeekRange("Working date is not in the selected week range"),
	WorkDateCantBeNull("Work date can't be blank"),
	StartEndTimeCantBeSame("Start time and end time can't be same"),
	StartEndTimeCantBeNull("Start time and end time can't be blank"),
	TimeOverlapping("Working hours for same day is overlapping"),
	
	StartTimeBiggerThanEndTime("Start Time can not be greater than End Time"),
	Enter_Time_is_wrong("Enter time is wrong"),
	
	TimecardNotExistDuringApproval("Invalid timecard"),
	ProjectIdBlankDuringApproval("Project id is blank"),
	RejectCommentIsEmpty("Add comment for rejection"),

	Logged_IN_USER_CANNOT_CHECK_OTHER_USER_DETAIL("Logged in user can not check other user timecard"),
	Timecard_Date_Time_Exist("Data already exist for the selected Time"),		
	Timecard_Data_Exist("Date already exist for the selected week"),		
	TimecardTimeDataExist("Data already exist for the selected date and time"),		
	Timecard_Date_Data_Exist("Date already exist"),
	Timecard_Date_Day_is_Not_Equal("Date and day should be same "),
	TimecardSuccessMessage("Timecard saved successfully"),		
			
	TimecardUpdateMessage("Timecard updated successfully"),			
	TimecardSubmitMessage("Timecard submitted successfully"),			
	TimecardAlreadySubmitMessage("Timecard is already submitted");
	

	
	private final String label;

	private TimecardMessageConstant(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

}
