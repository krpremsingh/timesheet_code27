package com.awcsoftware.app.timesheet;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

import com.awcsoftware.app.AppConstant;
import com.awcsoftware.app.AppValidator;
import com.awcsoftware.app.Util;
import com.awcsoftware.spring.security.auth.UserAuthenticationDetail;
import com.awcsoftware.spring.security.auth.user.Role;

public class TimecardValidator extends AppValidator {
	static Logger logger = Logger.getLogger(TimecardValidator.class);

	static Set<String> errorMsg;
	private String draftFlag = AppConstant.TIME_CARD_STATUS.Draft.toString();

	static {
		errorMsg = new LinkedHashSet<String>();
	}

	public Set<String> validateTimeCard(TimecardInfo timecardInfo) throws ParseException {

		errorMsg.clear();

		if (timecardInfo.getEmpId() == AppConstant.WORKING_HOURS.Zero.getValue()) {
			errorMsg.add(TimecardMessageConstant.Blank_Emp_Id.getLabel());
		}

		UserAuthenticationDetail auth = Util.getLoggedinUser();
		if (auth.getEmpId() != timecardInfo.getEmpId()) {
			errorMsg.add(TimecardMessageConstant.DifferentEmployeeIdAndLoggedInUser.getLabel());
		}

		if (timecardInfo.getWeekStart() == null || timecardInfo.getWeekStart().toString().length() < AppConstant.WORKING_HOURS.Ten.getValue()) {
			errorMsg.add(TimecardMessageConstant.Blank_Start_Date.getLabel());
		}

		if (timecardInfo.getWeekEnd() == null || timecardInfo.getWeekEnd().toString().length() < AppConstant.WORKING_HOURS.Ten.getValue()) {
			errorMsg.add(TimecardMessageConstant.Blank_End_Date.getLabel());
		}

		if (timecardInfo.getTimecardDayInfo() == null) {
			errorMsg.add(TimecardMessageConstant.BlankTimecardDetails.getLabel());
		}

		if (timecardInfo.getTimecardDayInfo().size() == AppConstant.WORKING_HOURS.Zero.getValue()) {
			errorMsg.add(TimecardMessageConstant.BlankTimecardDetails.getLabel());
		}

		validateTimecardDayInfo(timecardInfo.getTimecardDayInfo(), timecardInfo.getWeekStart().toString(),
				timecardInfo.getWeekEnd().toString());
//		timecardInfo.setTotalHours(this.getStrWeeklyTimeSum());
		return errorMsg;
	}

	public Set<String> validateSubmitTimeCard(TimecardInfo timecardInfo) throws ParseException {
		errorMsg.clear();

		draftFlag = AppConstant.TIME_CARD_STATUS.Pending.toString();
		validateTimeCard(timecardInfo);
		if (timecardInfo.getTimecardDayInfo().size() < AppConstant.WORKING_HOURS.Five.getValue()) {
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
			String weekEndDate) throws ParseException{
		int timecardDayCtr = 0;
		Set dateSet=new HashSet();
		for (timecardDayCtr = 0; timecardDayCtr < timecardDayInfoParam.size(); timecardDayCtr++) {
			TimecardDayInfo timecardDayInfo = (TimecardDayInfo) timecardDayInfoParam.get(timecardDayCtr);

			if (timecardDayInfo.getWorkingDate() == null) {
				errorMsg.add(TimecardMessageConstant.WorkDateCantBeNull.getLabel());
				return errorMsg;
			}

			if(Util.isValidDate(timecardDayInfo.getWorkingDate().toString()) == false )
			{
				errorMsg.add(TimecardMessageConstant.Invalid_Date.getLabel());
				return errorMsg;				
			}
			if (dateSet.contains(timecardDayInfo.getWorkingDate().toString())) {
				errorMsg.add(TimecardMessageConstant.Timecard_Date_Data_Exist.getLabel());
				return errorMsg;
			}

			if (!Util.getDateDay(timecardDayInfo.getWorkingDate().toString()).trim()
					.equalsIgnoreCase(timecardDayInfo.getWorkingDay().trim()))
			{
				errorMsg.add(TimecardMessageConstant.Timecard_Date_Day_is_Not_Equal.getLabel());
				return errorMsg;				
			}

			if (Util.validateDateRange(timecardDayInfo.getWorkingDate().toString(), weekStartDate,
					weekEndDate) == false)
				errorMsg.add(TimecardMessageConstant.WorkDateNotInWeekRange.getLabel());

			if (timecardDayInfo.getTimecardDayDetails().size() == AppConstant.WORKING_HOURS.Zero.getValue()) {
				errorMsg.add(TimecardMessageConstant.BlankDayTimecardDetails.getLabel());
				return errorMsg;
			}

			validateTimecardDayDetailsData(timecardDayInfo.getTimecardDayDetails());
			dateSet.add(timecardDayInfo.getWorkingDate().toString());
		}
		return errorMsg;
	}

	public Set<String> validateTimecardDayDetailsData(List<TimecardDayDetails> timecardDayDetailsParam) {
		int loopBrkCtr = 0, dayDetCtr = 0;
		String timeDiff = "00:00:00";
		String dailyTimeHour = "00:00:00";
		LocalTime currentRowStartTime = null, currentRowEndTime = null, nextRowStartTime = null, nextRowEndTime = null;
		TimecardDayDetails timecardDayDetailsNextRow = null;

		for (dayDetCtr = 0; dayDetCtr < (timecardDayDetailsParam.size()); dayDetCtr++) {
			currentRowStartTime = null;
			currentRowEndTime = null;
			nextRowStartTime = null;
			nextRowEndTime = null;

			TimecardDayDetails timecardDayDetails = (TimecardDayDetails) timecardDayDetailsParam.get(dayDetCtr);
			validateTimeCardDetails(timecardDayDetails);

			if (timecardDayDetails.getStartTime() == null || timecardDayDetails.getStartTime().trim().equals("")) {
				errorMsg.add(TimecardMessageConstant.StartEndTimeCantBeNull.getLabel());
				return errorMsg;
			}

			if (timecardDayDetails.getEndTime() == null || timecardDayDetails.getEndTime().trim().equals("")) {
				errorMsg.add(TimecardMessageConstant.StartEndTimeCantBeNull.getLabel());
				return errorMsg;
			}

			if(Util.isValidTime(timecardDayDetails.getStartTime().toString())==false)
			{
				errorMsg.add(TimecardMessageConstant.Enter_Time_is_not_wrong.getLabel());
				return errorMsg;
			}
			
			if(Util.isValidTime(timecardDayDetails.getEndTime().toString())==false)
			{
				errorMsg.add(TimecardMessageConstant.Enter_Time_is_not_wrong.getLabel());
				return errorMsg;
			}			
				
			if ((dayDetCtr + 1) < (timecardDayDetailsParam.size()) && (timecardDayDetailsParam.size() > 1)) {
				timecardDayDetailsNextRow = (TimecardDayDetails) timecardDayDetailsParam.get(dayDetCtr + 1);
				nextRowStartTime = LocalTime.parse(timecardDayDetailsNextRow.getStartTime() + ":00",
						DateTimeFormatter.ISO_TIME);
				nextRowEndTime = LocalTime.parse(timecardDayDetailsNextRow.getEndTime() + ":00",
						DateTimeFormatter.ISO_TIME);
			}

			currentRowStartTime = LocalTime.parse(timecardDayDetails.getStartTime() + ":00",
					DateTimeFormatter.ISO_TIME);
			currentRowEndTime = LocalTime.parse(timecardDayDetails.getEndTime() + ":00", DateTimeFormatter.ISO_TIME);

			if (currentRowStartTime.compareTo(currentRowEndTime) == 0) {
				errorMsg.add(TimecardMessageConstant.StartEndTimeCantBeSame.getLabel());
				loopBrkCtr = 1;
				break;
			}

			if (currentRowStartTime.isAfter(currentRowEndTime)) {
				errorMsg.add(TimecardMessageConstant.StartTimeBiggerThanEndTime.getLabel());
				loopBrkCtr = 1;
				break;
			}

			if (nextRowStartTime == null)
				nextRowStartTime = currentRowEndTime;

			if (nextRowStartTime.isBefore(currentRowEndTime)) {
				errorMsg.add(TimecardMessageConstant.TimeOverlapping.getLabel());
				loopBrkCtr = 1;
				break;
			}

			timeDiff = Util.TimeDiff(timecardDayDetails.getStartTime() + ":00",
					timecardDayDetails.getEndTime() + ":00");
			timecardDayDetails.setWorkingHours(timeDiff);

			dailyTimeHour = Util.TimeAdd(dailyTimeHour, timeDiff);
		}

		if (Integer.parseInt(dailyTimeHour.substring(0, 2)) > 24) {
			errorMsg.add(TimecardMessageConstant.Daily_Working_Limit.getLabel());
		}
/*
		if (draftFlag.equals(AppConstant.TIME_CARD_STATUS.Pending.toString())
				&& Integer.parseInt(dailyTimeHour.substring(0, 2)) < AppConstant.WORKING_HOURS.Eight.getValue())
			errorMsg.add(TimecardMessageConstant.SingleDayWorkingHourValidationDuringSubmit.getLabel());
*/
		return errorMsg;

	}

	public Set<String> validateTimecardApprovalData(TimecardApproverDetails timecardApproverDetails) {

		errorMsg.clear();

		if (timecardApproverDetails.getTcId() == AppConstant.WORKING_HOURS.Zero.getValue()) {
			errorMsg.add(TimecardMessageConstant.TimecardNotExistDuringApproval.getLabel());
		}

		if (timecardApproverDetails.getProjectId() == AppConstant.WORKING_HOURS.Zero.getValue()) {
			errorMsg.add(TimecardMessageConstant.ProjectIdBlankDuringApproval.getLabel());
		}

		if (timecardApproverDetails.getStatus().equalsIgnoreCase(AppConstant.TIME_CARD_STATUS.Reject.toString())
				&& timecardApproverDetails.getComments().equals("")) {
			errorMsg.add(TimecardMessageConstant.RejectCommentIsEmpty.getLabel());
		}

		return errorMsg;
	}


	public Set<String> validateTimeCardEmployeeView(TimecardInfo timecardInfo) {

		errorMsg.clear();

		UserAuthenticationDetail auth = Util.getLoggedinUser();

		logger.info(auth.getEmpId());
		logger.info(auth.getRole());
		logger.info(auth.getAuthorities());
		logger.info(auth.getEmpCode());
		logger.info(auth.getDesignationId());
		
		List<Role> employeeRole=auth.getRole();
		
		if (auth.getEmpId() != timecardInfo.getEmpId()) {
			errorMsg.add(TimecardMessageConstant.Logged_IN_USER_CANNOT_CHECK_OTHER_USER_DETAIL.getLabel());
		}
		return errorMsg;
	}

}
