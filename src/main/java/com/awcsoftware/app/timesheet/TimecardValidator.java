package com.awcsoftware.app.timesheet;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

import com.awcsoftware.app.AppConstant;
import com.awcsoftware.app.AppException;
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

	public Set<String> validateTimeCard(TimecardInfo timecardInfo) throws ParseException, AppException {

		errorMsg.clear();

		if (timecardInfo.getEmpId() == AppConstant.WORKING_HOURS.Zero.getValue()) {
			errorMsg.add(TimecardMessageConstant.Blank_Emp_Id.getLabel());
		}

		UserAuthenticationDetail auth = Util.getLoggedinUser();
		if (auth.getEmpId() != timecardInfo.getEmpId()) {
			errorMsg.add(TimecardMessageConstant.DifferentEmployeeIdAndLoggedInUser.getLabel());
		}

		if (timecardInfo.getWeekStart() == null
				|| timecardInfo.getWeekStart().toString().length() < AppConstant.WORKING_HOURS.Ten.getValue()) {
			errorMsg.add(TimecardMessageConstant.Blank_Start_Date.getLabel());
		}

		if (timecardInfo.getWeekEnd() == null
				|| timecardInfo.getWeekEnd().toString().length() < AppConstant.WORKING_HOURS.Ten.getValue()) {
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

	public Set<String> validateSubmitTimeCard(TimecardInfo timecardInfo) throws ParseException, AppException {
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
			String weekEndDate) throws ParseException {
		int timecardDayCtr = 0;
		Set dateSet = new HashSet();
		for (timecardDayCtr = 0; timecardDayCtr < timecardDayInfoParam.size(); timecardDayCtr++) {
			TimecardDayInfo timecardDayInfo = (TimecardDayInfo) timecardDayInfoParam.get(timecardDayCtr);

			if (timecardDayInfo.getWorkingDate() == null) {
				errorMsg.add(TimecardMessageConstant.WorkDateCantBeNull.getLabel());
			}

			if (Util.isValidDate(timecardDayInfo.getWorkingDate().toString()) == false) {
				errorMsg.add(TimecardMessageConstant.Invalid_Date.getLabel() + " ["
						+ timecardDayInfo.getWorkingDate().toString() + "]");
			}
			if (dateSet.contains(timecardDayInfo.getWorkingDate().toString())) {
				errorMsg.add(TimecardMessageConstant.Timecard_Date_Data_Exist.getLabel() + " ["
						+ timecardDayInfo.getWorkingDate().toString() + "]");
			}

			if (!Util.getDateDay(timecardDayInfo.getWorkingDate().toString()).trim()
					.equalsIgnoreCase(timecardDayInfo.getWorkingDay().trim())) {
				errorMsg.add(TimecardMessageConstant.Timecard_Date_Day_is_Not_Equal.getLabel() + " ["
						+ timecardDayInfo.getWorkingDate().toString() + "]");
			}

			if (Util.validateDateRange(timecardDayInfo.getWorkingDate().toString(), weekStartDate,
					weekEndDate) == false)
				errorMsg.add(TimecardMessageConstant.WorkDateNotInWeekRange.getLabel() + " Week Start Date ["
						+ weekStartDate + "] Week End Date [" + weekEndDate + "] Input Date["
						+ timecardDayInfo.getWorkingDate().toString() + "]");

			if (timecardDayInfo.getTimecardDayDetails().size() == AppConstant.WORKING_HOURS.Zero.getValue()) {
				errorMsg.add(TimecardMessageConstant.BlankDayTimecardDetails.getLabel());
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
		int leaveCountInSameDay = 0;
		TimecardDayDetails timecardDayDetailsNextRow = null;
		
		Collections.sort(timecardDayDetailsParam, new TimecardDayDetails());
		logger.debug(timecardDayDetailsParam);
		
		for (dayDetCtr = 0; dayDetCtr < (timecardDayDetailsParam.size()); dayDetCtr++) {
			currentRowStartTime = null;
			currentRowEndTime = null;
			nextRowStartTime = null;
			nextRowEndTime = null;

			TimecardDayDetails timecardDayDetails = (TimecardDayDetails) timecardDayDetailsParam.get(dayDetCtr);
			validateTimeCardDetails(timecardDayDetails);

			if (timecardDayDetails.getActivityId() == 18) {
				leaveCountInSameDay++;
			}
			if (timecardDayDetails.getStartTime() == null || timecardDayDetails.getStartTime().trim().equals("")) {
				errorMsg.add(TimecardMessageConstant.StartEndTimeCantBeNull.getLabel() + " ["
						+ timecardDayDetails.getWorkingDate() + "] ");
				break;
			}

			if (timecardDayDetails.getEndTime() == null || timecardDayDetails.getEndTime().trim().equals("")) {
				errorMsg.add(TimecardMessageConstant.StartEndTimeCantBeNull.getLabel() + " ["
						+ timecardDayDetails.getWorkingDate() + "] ");
				break;
			}

			if (Util.isValidTime(timecardDayDetails.getStartTime().toString()) == false) {
				errorMsg.add(TimecardMessageConstant.Enter_Time_is_wrong.getLabel() + "["
						+ timecardDayDetails.getWorkingDate() + "] [" + timecardDayDetails.getStartTime().toString()
						+ "] ");
				break;
			}

			if (Util.isValidTime(timecardDayDetails.getEndTime().toString()) == false) {
				errorMsg.add(TimecardMessageConstant.Enter_Time_is_wrong.getLabel() + "["
						+ timecardDayDetails.getWorkingDate() + "] [" + timecardDayDetails.getEndTime().toString()
						+ "] ");
				break;
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
				errorMsg.add(TimecardMessageConstant.StartEndTimeCantBeSame.getLabel() + " Date "
						+ timecardDayDetails.getWorkingDate() + " ");

				break;
			}

			if (currentRowStartTime.isAfter(currentRowEndTime)) {
				errorMsg.add(TimecardMessageConstant.StartTimeBiggerThanEndTime.getLabel() + " Date ["
						+ timecardDayDetails.getWorkingDate() + " ");
				break;
			}

			if (nextRowStartTime == null)
				nextRowStartTime = currentRowEndTime;

			if (nextRowStartTime.isBefore(currentRowEndTime)) {
				errorMsg.add(TimecardMessageConstant.TimeOverlapping.getLabel() + " Date "
						+ timecardDayDetails.getWorkingDate() + "  ");
				break;
			}

			timeDiff = Util.TimeDiff(timecardDayDetails.getStartTime() + ":00",
					timecardDayDetails.getEndTime() + ":00");

			if (timecardDayDetails.getActivityId() == 20 && Integer.parseInt(timeDiff.substring(0, 2)) > 4) {
				errorMsg.add(TimecardMessageConstant.HALF_DAY_TIME_ERROR.getLabel());
			}

			if (timecardDayDetails.getActivityId() == 18 && Integer.parseInt(timeDiff.substring(0, 2)) > 9) {
				errorMsg.add(TimecardMessageConstant.Leave_cant_be_Less_Parameterized_Hour.getLabel()
						+ AppConstant.WORKING_HOURS.Nine.getValue() + " hours Working Date "
						+ timecardDayDetails.getWorkingDate() + " ");
			}

			timecardDayDetails.setWorkingHours(timeDiff);

			dailyTimeHour = Util.TimeAdd(dailyTimeHour, timeDiff);
		}

		if (leaveCountInSameDay > 1) {
			errorMsg.add(TimecardMessageConstant.Multi_Leave_Same_Day_Msg.getLabel());
		}

		if (Integer.parseInt(dailyTimeHour.substring(0, 2)) > 24) {
			errorMsg.add(TimecardMessageConstant.Daily_Working_Limit.getLabel());
		}
		/*
		 * if (draftFlag.equals(AppConstant.TIME_CARD_STATUS.Pending.toString()) &&
		 * Integer.parseInt(dailyTimeHour.substring(0, 2)) <
		 * AppConstant.WORKING_HOURS.Eight.getValue())
		 * errorMsg.add(TimecardMessageConstant.
		 * SingleDayWorkingHourValidationDuringSubmit.getLabel());
		 */
		return errorMsg;

	}

	public Set<String> validateTimecardApprovalData(List<TimecardInfo> timecardInfoParam) {

		errorMsg.clear();

		for (int vldTCAppCtr = 0; vldTCAppCtr < timecardInfoParam.size(); vldTCAppCtr++) {
			TimecardInfo timecardInfoObj = (TimecardInfo) timecardInfoParam.get(vldTCAppCtr);
			timecardInfoObj.setStatus(AppConstant.TIME_CARD_STATUS.Approved.toString());
			validateProjectProjectDetails(timecardInfoObj);
		}
		return errorMsg;
	}

	public Set<String> validateTimecardRejectData(List<TimecardInfo> timecardInfoParam) {

		errorMsg.clear();

		for (int vldTCAppCtr = 0; vldTCAppCtr < timecardInfoParam.size(); vldTCAppCtr++) {
			TimecardInfo timecardInfoObj = (TimecardInfo) timecardInfoParam.get(vldTCAppCtr);
			timecardInfoObj.setStatus(AppConstant.TIME_CARD_STATUS.Reject.toString());
			validateProjectProjectDetails(timecardInfoObj);
		}
		return errorMsg;
	}

	public Set<String> validateProjectProjectDetails(TimecardInfo timecardInfoParam) {	

		if (timecardInfoParam.getTcId() == 0
				|| String.valueOf(timecardInfoParam.getTcId()).trim().equals("")) {
			errorMsg.add(TimecardMessageConstant.Missing_Timecard_During_Approval_Rejection.getLabel());
		}

		if (String.valueOf(timecardInfoParam.getStatus()).trim().equals("")) {
			errorMsg.add(TimecardMessageConstant.Missing_Status_for_APPROVAL_REJECTION.getLabel());
		}

		if (timecardInfoParam.getEmployeeProjectTimecard().size() == 0) {
			errorMsg.add(TimecardMessageConstant.Missing_Data_for_APPROVAL_REJECTION.getLabel());
		}

		UserAuthenticationDetail auth = Util.getLoggedinUser();
		if (!String.valueOf(auth.getEmpId()).equals(timecardInfoParam.getApproverId())) {
			errorMsg.add(TimecardMessageConstant.Logged_IN_USER_CANNOT_CHECK_OTHER_USER_DETAIL.getLabel());
		}
		
		List<TimecardProjectWorkDetails> timecardProjectWorkDetParam=(List<TimecardProjectWorkDetails>) timecardInfoParam.getEmployeeProjectTimecard();
		for(TimecardProjectWorkDetails timecardProjectWorkDetObj:timecardProjectWorkDetParam)
		{
			if(timecardProjectWorkDetObj.getProjectId()==null||timecardProjectWorkDetObj.getProjectId().trim()==""||
					String.valueOf(timecardProjectWorkDetObj.getProjectId()).equals("0") )
			{
				errorMsg.add(TimecardMessageConstant.Data_Mismatch_During_Approval_Rejection.getLabel());				
			}			

			if(timecardProjectWorkDetObj.getApproverComment().trim().equals(""))
			{
				errorMsg.add(TimecardMessageConstant.Missing_Comment_for_APPROVAL_REJECTION.getLabel());				
			}			

			if(timecardProjectWorkDetObj.getEmpId()==0)
			{
				errorMsg.add(TimecardMessageConstant.Invalid_Employee_Code.getLabel());				
			}			
			timecardInfoParam.setProjectId(timecardProjectWorkDetObj.getProjectId());
			timecardInfoParam.setManagerName(timecardProjectWorkDetObj.getApproverName());
			timecardInfoParam.setProjectName(timecardProjectWorkDetObj.getProjectName());
			timecardProjectWorkDetObj.setStatus(timecardInfoParam.getStatus());
		}	
		return errorMsg;
	}

	public Set<String> validateTimeCardEmployeeView(TimecardInfo timecardInfo) {

		errorMsg.clear();

		UserAuthenticationDetail auth = Util.getLoggedinUser();
		List<Role> employeeRole = auth.getRole();

		if (auth.getEmpId() != timecardInfo.getEmpId()) {
			errorMsg.add(TimecardMessageConstant.Logged_IN_USER_CANNOT_CHECK_OTHER_USER_DETAIL.getLabel());
		}
		return errorMsg;
	}

	public Set<String> validateTimeCardManagerView(TimecardInfo timecardInfo) {

		errorMsg.clear();

		UserAuthenticationDetail auth = Util.getLoggedinUser();
		List<Role> employeeRole = auth.getRole();

		if (auth.getEmpId() != timecardInfo.getEmpId()) {
			errorMsg.add(TimecardMessageConstant.Logged_IN_USER_CANNOT_CHECK_OTHER_USER_DETAIL.getLabel());
		}
		for(Role loggedInUserRole:employeeRole)
		{
			if(loggedInUserRole.getRoleId()==AppConstant.WORKING_HOURS.Two.getValue());
			{
				errorMsg.add(TimecardMessageConstant.Invalid_Manager.getLabel());
				break;
			}
		}
		return errorMsg;
	}

}
