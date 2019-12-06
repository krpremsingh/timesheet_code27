/**
 * 
 */
package com.awcsoftware.app.timesheet;

/**
 * @author Prem Shankar Kumar
 *
 */
public enum TimecardMessageConstant {

	InvalidTimecard("Selected Timecard doesn't exist"),
	BlankEmpId("Invalid employee"),
	BlankStartDate("Week start date is not valid "),
	BlankEndDate("Week end date is not valid "),
	BlankApprovalDetails("approval details is empty, kindly contact adminstrator and try again"),
	DailyWorkingLimit("Working hours cannot exceed by 24 hours for a day"),	
	BlankWorkingDate("Invalid working date"),
	BlankActivity("Select an activity"),
	BlankLocation("Select a location"),
	BlankProject("Select a project"),
	MimimunTimecards("Add 5 days data to submit a timesheet"),
	SingleDayWorkingHourValidationDuringSubmit("Single day working hours can't be less than 8 hours"),
	BlankTimecardDetails("Enter atleast one day to save a timecard"),
	EditApplicableforDraftAndRejected("Edit is only allowed for draft and rejected status"),

	//Logged-in user and update employee are not equal
	
	DifferentEmployeeIdAndLoggedInUser("User is not authorized to save timecard for other users"),		
	
	WorkDateNotInWeekRange("Working date is not in the selected week range"),
	WorkDateCantBeNull("Work date can't be blank"),
	StartEndTimeCantBeSame("Start time and end time can't be same"),
	StartEndTimeCantBeNull("Start time and end time can't be blank"),
	TimeOverlapping("Working hours for same day is overlapping"),
	
	StartTimeBiggerThanEndTime("Start Time can not be greater than End Time"),
	
	TimecardNotExistDuringApproval("Invalid timecard"),
	ProjectIdBlankDuringApproval("Project id is blank"),
	RejectCommentIsEmpty("Add comment for rejection"),

	
	TimecardDateTimeExist("Data already exist for the selected week"),		
	TimecardDataExist("Data already exist for the selected week"),		
	TimecardTimeDataExist("Data already exist for the selected date and time"),		
	TimecardDateDataExist("Data already exist for the selected date"),		
	TimecardSuccessMessage("Timecard saved successfully"),		

	TimecardDateTimeNotExist("Data does not exist for the selected week"),		
	TimecardUpdateMessage("Timecard updated successfully"),			
	TimecardSubmitMessage("Timecard submitted successfully");			
	

	
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
