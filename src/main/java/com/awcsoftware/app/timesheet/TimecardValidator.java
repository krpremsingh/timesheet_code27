package com.awcsoftware.app.timesheet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.AppValidator;
import com.awcsoftware.app.Util;
import com.awcsoftware.spring.security.auth.UserAuthenticationDetail;

public class TimecardValidator extends AppValidator {
	static Logger logger = Logger.getLogger(TimecardValidator.class);

	static Set<String> errorMsg;
	static String draftFlag;
	private String strWeeklyTimeSum = "00:00:00", strDailyTimeSum = "00:00:00";

	public String getStrWeeklyTimeSum() {
		return strWeeklyTimeSum;
	}

	public void setStrWeeklyTimeSum(String strWeeklyTimeSum) {
		this.strWeeklyTimeSum = strWeeklyTimeSum;
	}

	static {
		errorMsg = new LinkedHashSet<String>();
		draftFlag = "draft";
	}

	public Set<String> validateTimeCard(TimecardInfo timecardInfo) {

		errorMsg.clear();

		if (timecardInfo.getEmpId() == 0) {
			errorMsg.add(TimecardMessageConstant.BlankEmpId.getLabel());
		}

		UserAuthenticationDetail auth = Util.getLoggedinUser();
		if (auth.getEmpId() != timecardInfo.getEmpId()) {
			errorMsg.add(TimecardMessageConstant.DifferentEmployeeIdAndLoggedInUser.getLabel());
		}

		if (timecardInfo.getWeekStart() == null || timecardInfo.getWeekStart().toString().length() < 10) {
			errorMsg.add(TimecardMessageConstant.BlankStartDate.getLabel());
		}

		if (timecardInfo.getWeekEnd() == null || timecardInfo.getWeekEnd().toString().length() < 10) {
			errorMsg.add(TimecardMessageConstant.BlankEndDate.getLabel());
		}

		if (timecardInfo.getTimecardDayInfo() == null) {
			errorMsg.add(TimecardMessageConstant.BlankTimecardDetails.getLabel());
		}
		timecardInfo.setStatus(draftFlag);
		validateTimecardDayInfo(timecardInfo.getTimecardDayInfo(), timecardInfo.getWeekStart().toString(),
				timecardInfo.getWeekEnd().toString());
		timecardInfo.setTotalHours(this.getStrWeeklyTimeSum());
		return errorMsg;
	}

	public Set<String> validateSubmitTimeCard(TimecardInfo timecardInfo) {
		errorMsg.clear();

		draftFlag = "Pending";

		validateTimeCard(timecardInfo);
		if (timecardInfo.getTimecardDayInfo().size() < 5) {
			errorMsg.add(TimecardMessageConstant.MimimunTimecards.getLabel());
		}

		return errorMsg;
	}

	public Set<String> validateTimeCardDetails(TimecardDayDetails tcd) {

		if (Util.validateInt.test(tcd.getProjectId())) {
			errorMsg.add(TimecardMessageConstant.BlankProject.getLabel());
		}
		if (Util.validateInt.test(tcd.getActivityId())) {
			errorMsg.add(TimecardMessageConstant.BlankActivity.getLabel());
		}

		if (Util.validateInt.test(tcd.getLocation())) {
			errorMsg.add(TimecardMessageConstant.BlankLocation.getLabel());
		}

		return errorMsg;
	}

	public Set<String> validateTimecardDayInfo(List<TimecardDayInfo> timecardDayInfoParam, String weekStartDate,
			String weekEndDate) {
		int timecardDayCtr = 0;
		for (timecardDayCtr = 0; timecardDayCtr < timecardDayInfoParam.size(); timecardDayCtr++) {
			TimecardDayInfo timecardDayInfo = (TimecardDayInfo) timecardDayInfoParam.get(timecardDayCtr);

			if (timecardDayInfo.getWorkingDate() == null) {
				errorMsg.add(TimecardMessageConstant.WorkDateCantBeNull.getLabel());
				return errorMsg;
			}

			if (Util.validateDateRange(timecardDayInfo.getWorkingDate().toString(), weekStartDate,
					weekEndDate) == false)
				errorMsg.add(TimecardMessageConstant.WorkDateNotInWeekRange.getLabel());

			timecardDayInfo.setStatus(draftFlag);
			validateTimecardDayDetailsData(timecardDayInfo.getTimecardDayDetails());
			timecardDayInfo.setTotalWeekWorkHours(strDailyTimeSum);
		}
		this.setStrWeeklyTimeSum(strWeeklyTimeSum);
		return errorMsg;
	}

	public Set<String> validateTimecardDayDetailsData(List<TimecardDayDetails> timecardDayDetailsParam) {
		int iBreakStatus = 0, dayDetailsCtr = 0;
		String strTimeDiff = "00:00:00";
		strDailyTimeSum = "00:00:00";
		LocalTime currentRowStartTime = null, currentRowEndTime = null, nextRowStartTime = null, nextRowEndTime = null;
		TimecardDayDetails timecardDayDetailsNextRow = null;

		for (dayDetailsCtr = 0; dayDetailsCtr < (timecardDayDetailsParam.size()); dayDetailsCtr++) {
			TimecardDayDetails timecardDayDetails = (TimecardDayDetails) timecardDayDetailsParam.get(dayDetailsCtr);
			currentRowStartTime = null;
			currentRowEndTime = null;
			nextRowStartTime = null;
			nextRowEndTime = null;

			validateTimeCardDetails(timecardDayDetails);

			timecardDayDetails.setStatus(draftFlag);
			if ((dayDetailsCtr + 1) < (timecardDayDetailsParam.size())) {
				timecardDayDetailsNextRow = (TimecardDayDetails) timecardDayDetailsParam.get(dayDetailsCtr + 1);
				nextRowStartTime = LocalTime.parse(timecardDayDetailsNextRow.getStartTime() + ":00",
						DateTimeFormatter.ISO_TIME);
				nextRowEndTime = LocalTime.parse(timecardDayDetailsNextRow.getEndTime() + ":00",
						DateTimeFormatter.ISO_TIME);
			}

			if (timecardDayDetails.getStartTime() == null || timecardDayDetails.getEndTime() == null) {
				errorMsg.add(TimecardMessageConstant.StartEndTimeCantBeNull.getLabel());
				return errorMsg;
			}

			currentRowStartTime = LocalTime.parse(timecardDayDetails.getStartTime() + ":00",
					DateTimeFormatter.ISO_TIME);
			currentRowEndTime = LocalTime.parse(timecardDayDetails.getEndTime() + ":00", DateTimeFormatter.ISO_TIME);

			if (currentRowStartTime.compareTo(currentRowEndTime) == 0) {
				errorMsg.add(TimecardMessageConstant.StartEndTimeCantBeSame.getLabel());
				iBreakStatus = 1;
				break;
			}

			if (currentRowStartTime.isAfter(currentRowEndTime)) {
				errorMsg.add(TimecardMessageConstant.StartTimeBiggerThanEndTime.getLabel());
				iBreakStatus = 1;
				break;
			}

			if (nextRowStartTime == null)
				nextRowStartTime = currentRowEndTime;

			if (nextRowStartTime.isBefore(currentRowEndTime)) {
				errorMsg.add(TimecardMessageConstant.TimeOverlapping.getLabel());
				iBreakStatus = 1;
				break;
			}

			strTimeDiff = Util.TimeDiff(timecardDayDetails.getStartTime() + ":00",
					timecardDayDetails.getEndTime() + ":00");
			timecardDayDetails.setWorkingHours(strTimeDiff);
			strDailyTimeSum = Util.TimeAdd(strDailyTimeSum, strTimeDiff);
			strWeeklyTimeSum = Util.TimeAdd(strWeeklyTimeSum, strTimeDiff);
		}

		if (Integer.parseInt(strDailyTimeSum.substring(0, 2)) > 24) {
			errorMsg.add(TimecardMessageConstant.DailyWorkingLimit.getLabel());
		}

		if (draftFlag.equals("Pending") && Integer.parseInt(strDailyTimeSum.substring(0, 2)) < 8)
			errorMsg.add(TimecardMessageConstant.SingleDayWorkingHourValidationDuringSubmit.getLabel());

		return errorMsg;

	}

	public Set<String> validateTimecardApprovalData(TimecardApproverDetails timecardApproverDetails) {

		errorMsg.clear();

		if (timecardApproverDetails.getTcId() == 0) {
			errorMsg.add(TimecardMessageConstant.TimecardNotExistDuringApproval.getLabel());
		}

		if (timecardApproverDetails.getProjectId() == 0) {
			errorMsg.add(TimecardMessageConstant.ProjectIdBlankDuringApproval.getLabel());
		}

		if (timecardApproverDetails.getStatus() == "Reject" && timecardApproverDetails.getComments().equals("")) {
			errorMsg.add(TimecardMessageConstant.RejectCommentIsEmpty.getLabel());
		}

		return errorMsg;
	}

}
