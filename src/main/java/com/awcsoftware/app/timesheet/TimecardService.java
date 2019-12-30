package com.awcsoftware.app.timesheet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.AppConstant;
import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

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

	public List<TimecardInfo> getTimecardViewForEmployee(TimecardInfo timecardInfoParam) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<TimecardInfo>) dao.getTimecardViewForEmployee(timecardInfoParam);
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
	}

	/*
	 *  This function is called when employee will save his/her timecard information. 
	 *  This function will store data in table as draft mode so employee can 
	 *  change timecard as many time as s/he wants. This url/method will allow 
	 *  user to add new record  or update the existing timecard details 
	 * 
	 */
/*
	public String approveTimecardByManager(TimecardProjectWorkDetails timecardProjectWorkDetailsParam) throws DbException, AppException, ParseException {
		TimecardValidator validator = new TimecardValidator();
		Set<String> validatorResult = validator.validateTimecardApprovalData(timecardProjectWorkDetailsParam);
		if (validatorResult.size() == AppConstant.WORKING_HOURS.Zero.getValue()) {
			TimecardDao dao = new TimecardDao();
			timecardProjectWorkDetailsParam.setStatus(AppConstant.TIME_CARD_STATUS.Draft.toString());
			return dao.saveTimecard(timecardProjectWorkDetailsParam);
		} else
			return validatorResult.toString();
	}
*/
/*	public List<TimecardDayDetails> getWeekTimecard(TimecardInfo timecardInfo) throws AppException, DbException {
		TimecardDao dao = new TimecardDao();
		List<TimecardDayDetails> weekTimecard = dao.getWeekTimecard(timecardInfo);
		if(weekTimecard!=null) {
			return weekTimecard;
		}
		return null;
		
	}*/
}
