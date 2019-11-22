package com.awcsoftware.app.timesheet;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import com.awcsoftware.app.Util;

/**
 * @author Pratik
 *
 */
public class TimeCardValidator {
	static Set<LocalDate> timeCardDetailsFlag;
	static Set<String> errorMsg;
	
	static {
		errorMsg = new LinkedHashSet<String>();
		timeCardDetailsFlag = new LinkedHashSet<>();
	}

	public Set<String> validateSaveTimeCard(TimeCardSummaryInfo tci) {
		errorMsg.clear();
		if (Util.validateWeeklyHours.test(tci.getTotalHours())) {
			errorMsg.add("please enter weekly hours less than 168 and greater than 0");
		}

		if (tci.getTimeCardDetails() == null) {
			errorMsg.add("kindly enter atleast one day for saving a timecard");
		}
		return errorMsg;
	}

	public Set<String> validateSubmitTimeCard(TimeCardSummaryInfo tci) {
		errorMsg.clear();

		if (Util.validateWeeklyHours.test(tci.getTotalHours())) {
			errorMsg.add("please enter weekly hours less than 168 and greater than 0");
		}

		if (tci.getTimeCardDetails() == null) {
			errorMsg.add("kindly enter atleast one day for saving a timecard");
			return errorMsg;
		}
		for (TimeCardDetails tcd : tci.getTimeCardDetails()) {
			timeCardDetailsFlag.add(tcd.getWorkingDate());
		}
		if (timeCardDetailsFlag.size() < 5) {
			errorMsg.add("please send atleast 5 timecard details for submitting a time card");
			return errorMsg;
		}
		for (TimeCardDetails tcd : tci.getTimeCardDetails()) {
			validateTimeCardDetails(tcd);
		}

		return errorMsg;
	}

	public Set<String> validateTimeCardDetails(TimeCardDetails tcd) {

		if (Util.validateInt.test(tcd.getProjectId())) {
			errorMsg.add("please enter a valid project");
		}
		if (Util.validateInt.test(tcd.getActivityId())) {
			errorMsg.add("please enter a valid activity");
		}
		if (tcd.getWorkingDate() == null) {
			errorMsg.add("working date is blank, please enter a valid working date");
		}
		if (Util.validateDailyHours.test(tcd.getWorkingHours())) {
			errorMsg.add("please enter working hours between 0-23.59");
		}
		if (tcd.getTimeCardApprovalDetails() == null) {
			errorMsg.add("approval details is empty, kindly contact adminstrator and try again");
		}
		return errorMsg;
	}
	
}
