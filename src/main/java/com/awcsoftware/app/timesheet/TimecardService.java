package com.awcsoftware.app.timesheet;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.UserAuthenticationDetail;
import com.awcsoftware.app.Util;

public class TimecardService {
	static Logger logger = Logger.getLogger(TimecardService.class);

	public String saveTimecard(TimecardInfo addTimecardInfo) throws DbException, AppException {
		TimecardValidator timecardValidator = new TimecardValidator();
		Set validationValue = timecardValidator.validateSaveTimeCard(addTimecardInfo);
		if (validationValue.size() == 0) {
			TimecardDao dao = new TimecardDao();
			if (addTimecardInfo.getTcId() == 0)
				return dao.addTimecard(addTimecardInfo);
			else
				return dao.updateTimecard(addTimecardInfo);
		} else
			return validationValue.toString();
	}

	public String updateTimeCard(TimecardInfo updTimecardInfo) throws DbException, AppException {
		UserAuthenticationDetail auth = Util.getLoggedinUser();
		TimecardValidator timecardValidator = new TimecardValidator();
		Set validationValue = timecardValidator.validateSaveTimeCard(updTimecardInfo);
		if (validationValue.size() == 0) {
			TimecardDao dao = new TimecardDao();
			if (updTimecardInfo.getTcId() == 0)
				return dao.addTimecard(updTimecardInfo);
			else {
				if (auth.getEmpId() == updTimecardInfo.getEmpId()) {
					if (updTimecardInfo.getStatus().equalsIgnoreCase("draft")
							|| updTimecardInfo.getStatus().equalsIgnoreCase("Rejected")
							|| updTimecardInfo.getStatus().equalsIgnoreCase("Submitted"))
						return dao.updateTimecard(updTimecardInfo);
					else
						return TimecardErrorConstants.EditApplicableforDraftAndRejected.getLabel();
				} else
					return TimecardErrorConstants.DifferentEmployeeIdAndLoggedInUser.getLabel();
			}
		} else
			return validationValue.toString();
	}

	public String submitTimeCard(TimecardInfo submitTimecardInfo) throws DbException, AppException {
		TimecardValidator timecardValidator = new TimecardValidator();
		Set validationValue = timecardValidator.validateSubmitTimeCard(submitTimecardInfo);
		if (validationValue.size() == 0) {
			TimecardDao dao = new TimecardDao();
			return updateTimeCard(submitTimecardInfo);
		} else
			return validationValue.toString();
	}

	public List<TimecardView> getTimecardView() throws DbException, AppException {
		UserAuthenticationDetail auth = Util.getLoggedinUser();
		TimecardDao dao = new TimecardDao();
		return (List<TimecardView>) dao.getTimecardView(auth.getEmpId());
	}

	public List<TimecardView> getTimecardDetailsView(int tcId) throws DbException, AppException {
		TimecardDao dao = new TimecardDao();
		return (List<TimecardView>) dao.getTimecardDetailsView(tcId);
	}

}
