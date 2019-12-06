package com.awcsoftware.app.timesheet;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.UserAuthenticationDetail;
import com.awcsoftware.spring.security.auth.user.User;

public class TimecardService {
	static Logger logger = Logger.getLogger(TimecardService.class);

	public String saveTimecard(TimecardInfo addTimecardInfo) throws DbException, AppException, ParseException {
		TimecardValidator validator = new TimecardValidator();
		Set validationValue = validator.validateTimeCard(addTimecardInfo);
		if (validationValue.size() == 0) {
			TimecardDao dao = new TimecardDao();
			return dao.saveTimecard(addTimecardInfo);
		} else
			return validationValue.toString();
	}

	public String submitTimeCard(TimecardInfo submitTimecardInfo) throws DbException, AppException, ParseException {
		TimecardValidator validator = new TimecardValidator();
		Set validationValue = validator.validateSubmitTimeCard(submitTimecardInfo);
		if (validationValue.size() == 0) {
			TimecardDao dao = new TimecardDao();
				return dao.saveTimecard(submitTimecardInfo);

		} else
			return validationValue.toString();
	}

	public List<TimecardView> getCurrentWeekTimecard() throws DbException, AppException {
		UserAuthenticationDetail auth = Util.getLoggedinUser();
		TimecardDao dao = new TimecardDao();
		return (List<TimecardView>) dao.getTimecardView(auth.getEmpId());
	}

	public List<TimecardView> getTimecardDetailsView(int tcId) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<TimecardView>) dao.getTimecardDetailsView(tcId);
	}

	/*
	 * 
	 * Methods are created for Manager view, approve and Reject Functionality
	 * 
	 * 
	 */

	public List<TimecardInfo> getTimecardViewByManager(int approverId) throws DbException, AppException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAuthenticationDetail auth = (UserAuthenticationDetail) authentication;

		TimecardDao dao = new TimecardDao();
		List<TimecardInfo> result = dao.getTimecardByManager(approverId);
		return result;
	}

	public String approveRejectTimecard(TimecardApproverDetails timecardApproverDetails)
			throws DbException, AppException, ParseException {
		TimecardValidator timecardValidator = new TimecardValidator();
		Set validationValue = timecardValidator.validateTimecardApprovalData(timecardApproverDetails);
		if (validationValue.size() == 0) {
			TimecardDao dao = new TimecardDao();
			dao.approveRejectTimecard(timecardApproverDetails);
			return "S";
		} else
			return validationValue.toString();
	}

	public List<User> getEmployeesUnderLoggedInManager(int approverId) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<User>) dao.getEmployeesUnderLoggedInManager(approverId);

	}

	public List<TimecardManagerView> getTimecardViewByManager(TimecardManagerView view)
			throws DbException, AppException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAuthenticationDetail auth = (UserAuthenticationDetail) authentication;

		TimecardDao dao = new TimecardDao();
		List<TimecardManagerView> result = dao.getTimecardByManager(view);
		return result;
	}
}
