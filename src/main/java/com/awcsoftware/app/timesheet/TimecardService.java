package com.awcsoftware.app.timesheet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.AppConstant;
import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;

public class TimecardService {
	static Logger logger = Logger.getLogger(TimecardService.class);

	/*		************************************************************************
	 * 			Method Starts for Filling and view Timecard by an Employee 
	 *		************************************************************************ 
	 */

	
	/*
	 *  This function is called when employee will save his/her timecard information. 
	 *  This function will store data in table as draft mode so employee can 
	 *  change timecard as many time as s/he wants. This url/method will allow 
	 *  user to add new record  or update the existing timecard details 
	 * 
	 */

	public String saveTimecard(TimecardInfo addTimecardInfo) throws DbException, AppException, ParseException {
		TimecardValidator validator = new TimecardValidator();
		Set<String> validatorResult = validator.validateTimeCard(addTimecardInfo);
		if (validatorResult.size() == AppConstant.WORKING_HOURS.Zero.getValue()) {
			TimecardDao dao = new TimecardDao();
			addTimecardInfo.setStatus(AppConstant.TIME_CARD_STATUS.Draft.toString());
			return dao.saveTimecard(addTimecardInfo);
		} else
			return validatorResult.toString();
	}

	/*
	 *  This function is called when employee will submit his/her timecard information. 
	 *  This function will store data in table as submit mode.
	 *  Once the timecard data is submitted, employee can not resubmitted his/her timecard details again
	 *  until manager reject the particular time card. This url/method will allow 
	 *  user to add new record  or update the existing timecard details 
	 * 
	 */


	public String submitTimeCard(TimecardInfo submitTimecardInfo) throws DbException, AppException, ParseException {
		TimecardValidator validator = new TimecardValidator();
		Set<String> validatorResult = validator.validateSubmitTimeCard(submitTimecardInfo);
		if (validatorResult.size() == AppConstant.WORKING_HOURS.Zero.getValue()) {
			TimecardDao dao = new TimecardDao();
			submitTimecardInfo.setStatus(AppConstant.TIME_CARD_STATUS.Pending.toString());
			return dao.submitTimecard(submitTimecardInfo);

		} else
			return validatorResult.toString();
	}

	/*
	 *  This function is called when employee wants to check his/her timecard.
	 *  This function will show the detail view of entered 
	 * 
	 */

	public List<TimecardInfo> getEmployeeTimeCard(TimecardInfo timecardInfoParam) throws DbException, AppException {

		TimecardValidator validator = new TimecardValidator();
		Set<String> validatorResult = validator.validateTimeCardEmployeeView(timecardInfoParam);
		if (validatorResult.size() == AppConstant.WORKING_HOURS.Zero.getValue()) {
			TimecardDao dao = new TimecardDao();
			return (List<TimecardInfo>) dao.getEmployeeTimeCard(timecardInfoParam);
		} else
		{
			List<TimecardInfo> timecardReturn = new ArrayList<TimecardInfo>();
			timecardInfoParam.setSearchStatus(validatorResult.toString());
			timecardReturn.add(timecardInfoParam);
			return timecardReturn;
		}
			
	}

	/*
	 *  when user will click on result of method EmployeeTimecardView, 
	 *  This method will show detail view of selected TimecardInfo.tcId 
	 * 
	 */
	
	public List<TimecardView> getTimecardEmployeeDetailView(int tcId) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<TimecardView>) dao.getTimecardEmployeeDetailView(tcId);
	}

	/*
	 *  when user either save his/her timecard or submit his/her timecard, 
	 *  system will show entered details using this method.
	 * 
	 */

	public TimecardInfo getTimecardSavedRecord(TimecardInfo TimecardInfoParam) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (TimecardInfo) dao.getTimecardSavedRecord(TimecardInfoParam);
	}

	public List<TimecardDayDetails> getTimecardSavedRecordData(TimecardInfo TimecardInfoParam) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<TimecardDayDetails>) dao.getTimecardSavedRecordData(TimecardInfoParam);
	}

	/*		************************************************************************
	 * 			Method Ends for Filling and view Timecard by an Employee 
	 *		************************************************************************ 
	 */
	
	/*		*******************************************
	 * 			Method Starts for Manager Operation 
	 *		******************************************* 
	 */

	public List<TimecardInfo> getTimecardViewForManager(TimecardInfo timecardInfoParam) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<TimecardInfo>) dao.getTimecardViewForManager(timecardInfoParam);

		/*
		 * TimecardValidator validator = new TimecardValidator(); Set<String>
		 * validatorResult = validator.validateTimeCardEmployeeView(timecardInfoParam);
		 * if (validatorResult.size() == 0) { TimecardDao dao = new TimecardDao();
		 * return (List<TimecardInfo>) dao.getTimecardViewForManager(timecardInfoParam);
		 * } else { List<TimecardInfo> timecardReturn = new ArrayList<TimecardInfo>();
		 * timecardInfoParam.setSearchStatus(validatorResult.toString());
		 * timecardReturn.add(timecardInfoParam); return timecardReturn; }
		 */			
	}

	public List<TimecardInfo> getTimecardViewByManager(int approverId) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		List<TimecardInfo> result = dao.getTimecardByManager(approverId);
		return result;
	}

	public String approveRejectTimecard(TimecardApproverDetails timecardApproverDetails)
			throws DbException, AppException, ParseException {
		TimecardValidator validator = new TimecardValidator();
		Set<String> validatorResult = validator.validateTimecardApprovalData(timecardApproverDetails);
		if (validatorResult.size() == 0) {
			TimecardDao dao = new TimecardDao();
			/* dao.approveRejectTimecard(timecardApproverDetails); */
			return "S";
		} else
			return validatorResult.toString();
	}

	public List<User> getEmployeesUnderLoggedInManager(int approverId) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<User>) dao.getEmployeesUnderLoggedInManager(approverId);

	}

	public List<TimecardManagerView> getTimecardViewByManager(TimecardManagerView view)
			throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		List<TimecardManagerView> result = dao.getTimecardByManager(view);
		return result;
	}
}
