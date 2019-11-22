package com.awcsoftware.app.timesheet;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

public class TimeCardService {
	static Logger logger = Logger.getLogger(TimeCardDao.class);

	public String addTimeSheetDetails(TimeCardSummaryInfo theTimeCardSummaryInfoSerObj)
			throws DbException, AppException {
		String strReturnCardSummaryDet = "", strReturnCardValue = "";
		// validate here
		// if success
		// add
		TimeCardDao dao = new TimeCardDao();
		strReturnCardSummaryDet = dao.addTimeCardSummaryInfo(theTimeCardSummaryInfoSerObj);
		if (strReturnCardSummaryDet.equals("added to database")) {
			return dao.addTimeCardDetailsInfo(theTimeCardSummaryInfoSerObj);
		} else
			return strReturnCardSummaryDet;
	}
	/*
	 * public String updTimeSheetDetails(TimeCardSummaryInfo
	 * theTimeCardSummaryInfoSerObj) throws DbException, AppException { boolean
	 * resultValidateDao; // validate here // if success // add
	 * TimeCardSummaryInfoDao dao = new TimeCardSummaryInfoDao();
	 * resultValidateDao=dao.validateTimeSummaryById(theTimeCardSummaryInfoSerObj.
	 * getTcId()); if(resultValidateDao==true) { return
	 * dao.updTimeCardSummaryInfo(theTimeCardSummaryInfoSerObj); }
	 * 
	 * }
	 */
}
