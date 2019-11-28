package com.awcsoftware.app.timesheet;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.awcsoftware.app.ErrorConstants;
import com.awcsoftware.app.Util;

/**
 * @author Pratik Gaurav
 *
 */
public class TimeCardValidator {

	static Set<LocalDate> timeCardDetailsFlag;
	static Set<String> errorMsg;
	static String draftFlag;
	static String submitFlag;
	static Map<LocalDate,Integer> dayHoursMap;
	static {
		errorMsg = new LinkedHashSet<String>();
		timeCardDetailsFlag = new LinkedHashSet<>();
		draftFlag = "draft";
		submitFlag = "submit";
		dayHoursMap = new LinkedHashMap<LocalDate,Integer>();
	}

	public Set<String> validateSaveTimeCard(TimecardInfo tci) {

		errorMsg.clear();
		/*if (Util.validateWeeklyHours.test(tci.getTotalHours())) {
			errorMsg.add("please enter weekly hours less than 168 and greater than 0");
		}*/

		if (tci.getTimeCardDetails() == null) {
			errorMsg.add(ErrorConstants.BlankTimecardDetails.getLabel());
		}
		for (TimecardDetails tcd : tci.getTimeCardDetails()) {
			validateTimeCardDetails(tcd, draftFlag);
		}
		return errorMsg;
	}

	public Set<String> validateSubmitTimeCard(TimecardInfo tci) {
		errorMsg.clear();

		/*if (Util.validateWeeklyHours.test(tci.getTotalHours())) {
			errorMsg.add("please enter weekly hours less than 168 and greater than 0");
		}*/

		if (tci.getTimeCardDetails() == null) {
			errorMsg.add(ErrorConstants.BlankTimecardDetails.getLabel());
			return errorMsg;
		}
		for (TimecardDetails tcd : tci.getTimeCardDetails()) {
			timeCardDetailsFlag.add(tcd.getWorkingDate());
		}
		if (timeCardDetailsFlag.size() < 5) {
			errorMsg.add(ErrorConstants.MimimunTimecards.getLabel());
			return errorMsg;
		}
		for (TimecardDetails tcd : tci.getTimeCardDetails()) {
			validateTimeCardDetails(tcd, submitFlag);
			
		}

		return errorMsg;
	}

	public Set<String> validateTimeCardDetails(TimecardDetails tcd, String flag) {

		if (Util.validateInt.test(tcd.getProjectId())) {
			errorMsg.add(ErrorConstants.BlankProject.getLabel());
		}
		if (Util.validateInt.test(tcd.getActivityId())) {
			errorMsg.add(ErrorConstants.BlankActivity.getLabel());
		}
		if (tcd.getWorkingDate() == null) {
			errorMsg.add(ErrorConstants.BlankWorkingDate.getLabel());
		}
/*		if (Util.validateDailyHours.test(tcd.getWorkingHours())) {
			errorMsg.add(ErrorConstants.DailyWorkingLimit.getLabel());
		}*/
		if (flag.equals("submit")) {
			if (tcd.getTimeCardApprovalDetails() == null) {
				errorMsg.add(ErrorConstants.BlankApprovalDetails.getLabel());
			}
		}
		return errorMsg;
	}

}
