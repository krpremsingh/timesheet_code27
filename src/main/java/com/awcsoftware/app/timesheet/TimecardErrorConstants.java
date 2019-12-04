/**
 * 
 */
package com.awcsoftware.app.timesheet;

/**
 * @author Prem Shankar Kumar
 *
 */
public enum TimecardErrorConstants {

	InvalidTimecard("Selected Timecard doesn't exist"),
	BlankEmpId("Invalid employee"),
	BlankStartDate("Week start date is not valid "),
	BlankEndDate("Week end date is not valid "),
	BlankApprovalDetails("approval details is empty, kindly contact adminstrator and try again"),
	DailyWorkingLimit("Working hours cannot exceed by 24 hours for a day"),
	WeeklyWorkingLimit("Weekly working hours cannot exceed by 168 hours for a day"),
	MinWeeklyWorkingLimit("Weekly work hour can't be less than 40 hours"),
	BlankWorkingDate("Invalid working date"),
	BlankActivity("Please select an activity"),
	BlankLocation("Please select a location"),
	BlankProject("Please select a project"),
	MimimunTimecards("Please add 5 days data to submit a timesheet"),
	SingleDayWorkingHourValidationDuringSubmit("Single day working hours can't be less than 8 hours"),
	BlankTimecardDetails("kindly enter atleast one day for saving a timecard"),
	EditApplicableforDraftAndRejected("Edit is only allowed for draft and rejected status"),
	DifferentEmployeeIdAndLoggedInUser("Logged-in user and update employee are not equal"),
	WorkDateNotInWeekRange("Working date is not selected week range"),
	WorkDateCantBeNull("Work date can't be null"),
	StartEndTimeCantBeSame("Start time and end time can't be same"),
	StartEndTimeCantBeNull("Start time and end time can't be null"),
	TimeOverlapping("Time Period is overlapping"),
	StartTimeBiggerThanEndTime("Start Time > End Time"),
	
	TimecardDateTimeExist("Data already exist for the selected week"),		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timesheet
	TimecardDataExist("Data already exist for the selected week"),		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timesheet
	TimecardTimeDataExist("Data already exist for the selected date and time"),		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timesheet
	TimecardDateDataExist("Data already exist for the selected date"),		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timeshee
	TimecardSuccessMessage("Timecard has been added"),		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timesheet

	TimecardDateTimeNotExist("Data does not exist for the selected week"),		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timesheet
	TimecardUpdateMessage("Timecard has been updated");		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timesheet
	

	
	private final String label;

	private TimecardErrorConstants(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

}
