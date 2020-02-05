package com.awcsoftware.app.timesheet;


public enum TimecardMessageConstant {

	Invalid_Timecard("Selected Timecard doesn't exist"),
	Blank_Emp_Id("Unauthorized access"),
	Blank_Start_Date("Invalid start date"),
	Blank_End_Date("Invalid end date"),
	Invalid_Date("Date must be in YYYY-MM-DD Format"),
	Blank_Approval_Details("No manager assigned. Please contact adminstrator and try again"),
	Daily_Working_Limit("Daily working hours cannot be more than 24"),	
	Blank_Working_Date("Invalid working date"),
	BlankActivity("Activity cannot be blank"),
	BlankLocation("Location cannot be blank"),
	BlankProject("Project cannot be blank"),
	MimimunTimecards("Add 5 days data to submit a timesheet"),
	//SingleDayWorkingHourValidationDuringSubmit("Single day working hours can't be less than 8 hours"),
	BlankTimecardDetails("Enter atleast one day to save a timecard"),
	BlankDayTimecardDetails("Enter atleast one day to save a timecard"),
	EditApplicableforDraftAndRejected("Edit is only allowed for draft and rejected status"),

	//Logged-in user and update employee are not equal
	
	DifferentEmployeeIdAndLoggedInUser("You are not authorized to save timecard for other users"),		
	
	WorkDateNotInWeekRange("Working date is not in the selected week range"),
	WorkDateCantBeNull("Work date can't be blank"),
	StartEndTimeCantBeSame("Start time and end time can't be same"),
	StartEndTimeCantBeNull("Start time and end time can't be blank"),
	TimeOverlapping("Working hours cannot be same for "),
	HALF_DAY_TIME_ERROR("Half day leave cannot be more than 4 hours"),
	Leave_cant_be_Less_Parameterized_Hour("Leave should be of "),
	Multi_Leave_Same_Day_Msg("Cannot apply multiple leaves on same day"),
	Work_And_Leave_Cannot_on_Same_Day_Msg("cannot add leave & working hrs for same day"),	
	StartTimeBiggerThanEndTime("Start Time can not be greater than End Time"),
	Enter_Time_is_wrong("Entered time is incorrect"),
		
	RejectCommentIsEmpty("Add comment for rejection"),

	Logged_IN_USER_CANNOT_CHECK_OTHER_USER_DETAIL("Logged in user can not check other user timecard"),
	Timecard_Date_Time_Exist("Data already exist for the selected Time"),		
	Timecard_Data_Exist("Data already exist for the selected week"),		
	TimecardTimeDataExist("Data already exist for the selected date and time"),		
	Timecard_Date_Data_Exist("Data already exist"),
	Timecard_Date_Day_is_Not_Equal("Date and day should be same "),
	TimecardSuccessMessage("Timecard saved successfully"),		
			
	TimecardUpdateMessage("Timecard updated successfully"),			
	TimecardSubmitMessage("Timecard submitted successfully"),			
	TimecardAlreadySubmitMessage("Timecard is already submitted"),


	Invalid_Manager("You are not authorized to view the current details"),
	/*
	 * Validation message for manager Approval or Reject of Timecard 
	 * 
	 */
	Missing_Timecard_During_Approval_Rejection("Invalid timecard"),
	Missing_Project_ID_During_Approval_Rejection("Project is blank"),		
	Missing_Status_for_APPROVAL_REJECTION("Status is blank"),
	Missing_Comment_for_APPROVAL_REJECTION("Comment is mandatory for Rejection"),
	Missing_Data_for_APPROVAL_REJECTION("There is no any data for approval or Rejection"),
	Data_Mismatch_During_Approval_Rejection("Data is mismatch for approval and rejection"),
	Invalid_Employee_Code("Employee code is not valid"),
	Timecard_Approved_successfully("Timecard approved successfully"),
	Timecard_Rejected_successfully("Timecard rejected successfully"),
	Timecard_Is_Already_Approved("Data is mismatch for approval and rejection");
	

	
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
