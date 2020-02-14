package com.awcsoftware.app.report;

public enum ReportsMessageConstants {
	
	WeekStartDateCannotBeBlank("Week start date can't be blank"),
	RecordNotFoundBetweenDates("No record found for this duration"),
	RecordNotFoundForStatus("No record found against this status"),
	WeekEndDateCannotBeBlank("Week end date can't be blank"),
	NoRecordFound("No record found"),
	WeekStartDateFormatIsNotValid("Week start date format is not valid"),
	WeekEndDateFormatIsNotValid("Week end date format is not valid"),
	WeekStartDateNotBeforeEndDate("Week start date can't before week end date"),
	TimecardStatusCannotbeBlank("Timecard status can't be blank");
	
	private final String label;

	public String getLabel() {
		return label;
	}

	private ReportsMessageConstants(String label) {
		this.label = label;
	}
}
