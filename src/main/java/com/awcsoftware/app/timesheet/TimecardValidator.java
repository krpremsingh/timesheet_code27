package com.awcsoftware.app.timesheet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.Util;

/**
 * @author Prem Shankar Kumar
 *
 */
public class TimecardValidator {
	static Logger logger = Logger.getLogger(TimecardValidator.class);

	static Set<LocalDate> timeCardDetailsFlag;
	static Set<String> errorMsg;
	static String draftFlag;
	static String submitFlag;
	static Map<LocalDate, Integer> dayHoursMap;
	static {
		errorMsg = new LinkedHashSet<String>();
		timeCardDetailsFlag = new LinkedHashSet<>();
		draftFlag = "draft";
		submitFlag = "submit";
		dayHoursMap = new LinkedHashMap<LocalDate, Integer>();
	}

	public Set<String> validateSaveTimeCard(TimecardInfo timecardInfo) {

		errorMsg.clear();

		if (timecardInfo.getEmpId() == 0) {
			errorMsg.add(TimecardErrorConstants.BlankEmpId.getLabel());
		}

		if (timecardInfo.getWeekStart() == null || timecardInfo.getWeekStart().toString().length() < 10) {
			errorMsg.add(TimecardErrorConstants.BlankStartDate.getLabel());
		}

		if (timecardInfo.getWeekEnd() == null || timecardInfo.getWeekEnd().toString().length() < 10) {
			errorMsg.add(TimecardErrorConstants.BlankEndDate.getLabel());
		}

		if (Util.validateWeeklyHours.test(timecardInfo.getTotalHours())) {
			errorMsg.add("please enter weekly hours less than 168 and greater than 0");
		}

		if (timecardInfo.getTimecardDayInfo() == null) {
			errorMsg.add(TimecardErrorConstants.BlankTimecardDetails.getLabel());
		}

		if (timecardInfo.getTimecardDayInfo().size() == 0) {
			errorMsg.add(TimecardErrorConstants.BlankTimecardDetails.getLabel());
		}

		validateTimecardDayInfo(timecardInfo);
		return errorMsg;
	}

	public Set<String> validateSubmitTimeCard(TimecardInfo timecardInfo) {
		errorMsg.clear();

		if (timecardInfo.getEmpId() == 0) {
			errorMsg.add(TimecardErrorConstants.BlankEmpId.getLabel());
		}

		if (timecardInfo.getWeekStart() == null || timecardInfo.getWeekStart().toString().length() < 10) {
			errorMsg.add(TimecardErrorConstants.BlankStartDate.getLabel());
		}

		if (timecardInfo.getWeekEnd() == null || timecardInfo.getWeekEnd().toString().length() < 10) {
			errorMsg.add(TimecardErrorConstants.BlankEndDate.getLabel());
		}

		if (timecardInfo.getTimecardDayInfo() == null) {
			errorMsg.add(TimecardErrorConstants.BlankTimecardDetails.getLabel());
			return errorMsg;
		}
		validateTimecardDayInfo(timecardInfo);

		if (timecardInfo.getTimecardDayInfo().size() < 5) {
			errorMsg.add(TimecardErrorConstants.MimimunTimecards.getLabel());
		}

		return errorMsg;
	}

	public Set<String> validateTimeCardDetails(TimecardDayDetails tcd, String flag) {

		if (Util.validateInt.test(tcd.getProjectId())) {
			errorMsg.add(TimecardErrorConstants.BlankProject.getLabel());
		}
		if (Util.validateInt.test(tcd.getActivityId())) {
			errorMsg.add(TimecardErrorConstants.BlankActivity.getLabel());
		}

		if (Util.validateInt.test(tcd.getLocation())) {
			errorMsg.add(TimecardErrorConstants.BlankLocation.getLabel());
		}

		if (Util.validateDailyHours.test(tcd.getWorkingHours())) {
			errorMsg.add(TimecardErrorConstants.DailyWorkingLimit.getLabel());
		}

		/*
		 * if (flag.equals("submit")) { if (tcd.getTimeCardApprovalDetails() == null) {
		 * errorMsg.add(ErrorConstants.BlankApprovalDetails.getLabel()); } }
		 */
		return errorMsg;
	}

	public Set<String> validateTimecardDayInfo(TimecardInfo timecardInfo) {
		float totalDayHours = 0, totalWeekHours = 0;
		int iBreakStatus = 0, timecardDayCtr = 0;
		LocalTime currentRowStartTime = null, currentRowEndTime = null, nextRowStartTime = null, nextRowEndTime = null;

		for (timecardDayCtr = 0; timecardDayCtr < timecardInfo.getTimecardDayInfo().size(); timecardDayCtr++) {
			TimecardDayInfo timecardDayInfo = (TimecardDayInfo) timecardInfo.getTimecardDayInfo().get(timecardDayCtr);
			totalDayHours = 0;
			if(Util.validateDateRange(timecardDayInfo.getWorkingDate().toString(), 
						timecardInfo.getWeekStart().toString(), timecardInfo.getWeekEnd().toString())==false)
				errorMsg.add(TimecardErrorConstants.WorkDateNotInWeekRange.getLabel()); 
				
			for (int iDayDetails = 0; iDayDetails < (timecardDayInfo.getTimecardDayDetails().size()); iDayDetails++) {
				TimecardDayDetails timecardDayDetails = (TimecardDayDetails) timecardDayInfo.getTimecardDayDetails()
						.get(iDayDetails);
				validateTimeCardDetails(timecardDayDetails, draftFlag);
				TimecardDayDetails timecardDayDetailsNextRow = null;
				currentRowStartTime = null;
				currentRowEndTime = null;
				nextRowStartTime = null;
				nextRowEndTime = null;

				if ((iDayDetails + 1) < (timecardDayInfo.getTimecardDayDetails().size())) {
					timecardDayDetailsNextRow = (TimecardDayDetails) timecardDayInfo.getTimecardDayDetails()
							.get(iDayDetails + 1);
					nextRowStartTime = LocalTime.parse(timecardDayDetailsNextRow.getStartTime() + ":00",
							DateTimeFormatter.ISO_TIME);
					nextRowEndTime = LocalTime.parse(timecardDayDetailsNextRow.getEndTime() + ":00",
							DateTimeFormatter.ISO_TIME);
				}
				if(timecardDayDetails.getStartTime()==null||
						timecardDayDetails.getEndTime()==null)
				{
					errorMsg.add(TimecardErrorConstants.StartEndTimeCantBeNull.getLabel());
					return errorMsg;
				}
							
				currentRowStartTime = LocalTime.parse(timecardDayDetails.getStartTime() + ":00",
						DateTimeFormatter.ISO_TIME);
				currentRowEndTime = LocalTime.parse(timecardDayDetails.getEndTime() + ":00",
						DateTimeFormatter.ISO_TIME);

				if (currentRowStartTime.compareTo(currentRowEndTime) == 0) {
					errorMsg.add(TimecardErrorConstants.StartEndTimeCantBeSame.getLabel());
					iBreakStatus = 1;
					break;
				}

				if (currentRowStartTime.isAfter(currentRowEndTime)) {
					errorMsg.add(TimecardErrorConstants.StartTimeBiggerThanEndTime.getLabel());
					iBreakStatus = 1;
					break;
				}
				if(nextRowStartTime==null)
					nextRowStartTime=currentRowEndTime;
				if (nextRowStartTime.isBefore(currentRowEndTime)) {
					errorMsg.add(TimecardErrorConstants.TimeOverlapping.getLabel());
					iBreakStatus = 1;
					break;
				}

				totalWeekHours += timecardDayDetails.getWorkingHours();
				totalDayHours += timecardDayDetails.getWorkingHours();
			}
			if (iBreakStatus == 1)
				break;
			if (totalDayHours > 24) {
				errorMsg.add(TimecardErrorConstants.DailyWorkingLimit.getLabel());
			}
		}
		return errorMsg;
	}
}
