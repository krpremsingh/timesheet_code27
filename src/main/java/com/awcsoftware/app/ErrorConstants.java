/**
 * 
 */
package com.awcsoftware.app;

/**
 * @author Pratik
 *
 */
public enum ErrorConstants {
	BlankApprovalDetails("approval details is empty, kindly contact adminstrator and try again"),
	DailyWorkingLimit("please enter working hours between 0-23.59"),
	BlankWorkingDate("working date is blank, please enter a valid working date"),
	BlankActivity("please enter a valid activity"), 
	BlankProject("please enter a valid project"),
	MimimunTimecards("please send atleast 5 timecard details for submitting a time card"),
	BlankTimecardDetails("kindly enter atleast one day for saving a timecard");

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
