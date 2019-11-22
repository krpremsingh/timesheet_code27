package com.awcsoftware.app.timesheet;

import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

public class TimeCardService {
	static Logger logger = Logger.getLogger(TimeCardDao.class);

	public String addTimeSheetDetails(TimeCardSummaryInfo theTimeCardSummaryInfoSerObj)
			throws DbException, AppException {
		String strReturnCardSummaryDet = "", strReturnCardValue = "";
		// validate here
		TimeCardValidator tcv=new TimeCardValidator();
		Set validationValue=tcv.validateSubmitTimeCard(theTimeCardSummaryInfoSerObj);
		if(validationValue.size()==0){
			TimeCardDao dao = new TimeCardDao();
			strReturnCardSummaryDet = dao.addTimeCardSummaryInfo(theTimeCardSummaryInfoSerObj);
			if (strReturnCardSummaryDet.equals("added to database")) {
				return dao.addTimeCardDetailsInfo(theTimeCardSummaryInfoSerObj);
			} else
				return strReturnCardSummaryDet;
		}
		else {
			return validationValue.toString();
		}
		// if success
		// add
		
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
