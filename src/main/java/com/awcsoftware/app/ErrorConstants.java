/**
 * 
 */
package com.awcsoftware.app;

/**
 * @author Pratik
 *
 */
public enum ErrorConstants {

	BlankApprovalDetails("approval details is empty, kindly contact adminstrator and try again!!!"),
	DailyWorkingLimit("Working hours cannot exceed by 24 hours for a day!!!"),
	BlankWorkingDate("Invalid working date!!!"),
	BlankActivity("Invalid activity!!!"), 
	BlankProject("Invalid project!!!"),
	MimimunTimecards("Please add 5 days data to submit a timesheet!!!"),
	BlankTimecardDetails("kindly enter atleast one day for saving a timecard"),
	TimecardDataExist("Data already exist for the selected week"),		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timesheet
	TimecardTimeDataExist("Data already exist for the selected date and time"),		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timesheet
	TimecardSuccessMessage("Timesheet added successfully");		//Need to discuss with Punit Sir Whether to show this message when user select previous week data during add timesheet

	private final String label;

	private ErrorConstants(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

}
