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
		
		/*
		 * 	validate for Save - in validation class separate method for save and submit validation
		 * 	if success
		 * 		addTimecard()	
		 * 	
		 * 
		 * check for data already exist for this emp for this week
		 * 			if true
		 * 				call Dao Update method - updateTimecardDetails
	 	*			else 
	 	*				call Dao add method - addTimecard, addTimecardDetails
		 * 
		 * 
		 */

		TimeCardValidator tcv=new TimeCardValidator();
		Set validationValue=tcv.validateSaveTimeCard(addTimecardInfo);
		if (validationValue.size() == 0) {
			TimecardDao dao = new TimecardDao();
			if (addTimecardInfo.getTcId() == 0)
				return dao.addTimecard(addTimecardInfo);
			else				
				return dao.addTimecard(addTimecardInfo);
		}
		else
			return validationValue.toString();
	}

	/*
	 * public String updateTimeCard(TimecardInfo updTimecardInfo) throws
	 * DbException, AppException { TimeCardValidator tcv = new TimeCardValidator();
	 * Set validationValue = tcv.validateSaveTimeCard(updTimecardInfo); if
	 * (validationValue.size() == 0) { TimecardDao dao = new TimecardDao(); if
	 * (updTimecardInfo.getTcId() == 0) return dao.addTimecard(updTimecardInfo);
	 * else return dao.updateTimecard(updTimecardInfo); } else return
	 * validationValue.toString(); }
	 */	

	public List<TimecardView> getTimecardView() throws DbException, AppException  
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAuthenticationDetail auth = (UserAuthenticationDetail) authentication;
		TimecardDao dao = new TimecardDao();
		return  (List<TimecardView>)dao.getTimecardView(auth.getEmpId());
	}
}
