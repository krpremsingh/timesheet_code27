package com.awcsoftware.app.timesheet;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.AppConstant;
import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;

public class TimecardService {
	static Logger logger = Logger.getLogger(TimecardService.class);

	public String saveTimecard(TimecardInfo addTimecardInfo) throws DbException, AppException, ParseException {
		TimecardValidator validator = new TimecardValidator();
		Set<String> validatorResult = validator.validateTimeCard(addTimecardInfo);
		if (validatorResult.size() == 0) {
			TimecardDao dao = new TimecardDao();
			addTimecardInfo.setStatus(AppConstant.TIME_CARD_STATUS.Draft.toString());
			return dao.saveTimecard(addTimecardInfo);
		} else
			return validatorResult.toString();
	}

	public String submitTimeCard(TimecardInfo submitTimecardInfo) throws DbException, AppException, ParseException {
		TimecardValidator validator = new TimecardValidator();
		Set<String> validatorResult = validator.validateSubmitTimeCard(submitTimecardInfo);
		if (validatorResult.size() == 0) {
			TimecardDao dao = new TimecardDao();
			submitTimecardInfo.setStatus(AppConstant.TIME_CARD_STATUS.Pending.toString());
			return dao.submitTimecard(submitTimecardInfo);

		} else
			return validatorResult.toString();
	}

	public List<TimecardInfo> getEmployeeTimeCard(TimecardInfo timecardInfoParam) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<TimecardInfo>) dao.getEmployeeTimeCard(timecardInfoParam);
	}

	public List<TimecardView> getTimecardDetailsView(int tcId) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<TimecardView>) dao.getTimecardDetailsView(tcId);
	}

	public TimecardInfo getTimecardRecord(TimecardInfo TimecardInfoParam) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (TimecardInfo) dao.getTimecardRecord(TimecardInfoParam);
	}

	/*
	 * 
	 * Methods are created for Manager view, approve and Reject Functionality
	 * 
	 * 
	 */

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
			dao.approveRejectTimecard(timecardApproverDetails);
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
