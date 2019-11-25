package com.awcsoftware.app.timesheet;

import java.util.List;
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
		
		Set validationValue=tcv.validateSaveTimeCard(theTimeCardSummaryInfoSerObj);
		// if success
		// add
		if(validationValue.size()==0)
		{
			TimeCardDao dao = new TimeCardDao();
			strReturnCardSummaryDet = dao.addTimeCardSummaryInfo(theTimeCardSummaryInfoSerObj);
			if (strReturnCardSummaryDet.equals("S0000")) 
			{
				for (TimeCardDetails theTimeCardObj : theTimeCardSummaryInfoSerObj.getTimeCardDetails()) 
				{
					theTimeCardObj.setTcId(theTimeCardSummaryInfoSerObj.getTcId());
					strReturnCardValue = dao.addTimeCardDetailsInfo(theTimeCardObj);
					if(!strReturnCardValue.equals("S0000"))
						return strReturnCardValue;
				}
				return strReturnCardValue;
			}
			else
				return strReturnCardSummaryDet;
		}
		else 
		{
			return validationValue.toString();
		}
		
	}
	
	public String updTimeSheetDetails(TimeCardSummaryInfo theTimeCardSummaryInfoSerObj) throws DbException, AppException 
	{
		boolean resultValidateDao; // validate here // if success // add
		String strDaoReturnVal="No Data Found",strCardDaoReturnVal="No Data Found";
		TimeCardDao dao = new TimeCardDao();
		
		resultValidateDao = dao.validateTimeSummaryById(theTimeCardSummaryInfoSerObj.getTcId());
		if (resultValidateDao == true) {
			strDaoReturnVal= dao.updTimeCardSummaryInfo(theTimeCardSummaryInfoSerObj);
			if(strDaoReturnVal.equals("S0000"))
			{
				for (TimeCardDetails theTimeCardObj : theTimeCardSummaryInfoSerObj.getTimeCardDetails()) 
				{
					resultValidateDao = dao.validateTimeCardByIds(theTimeCardObj);
					if(resultValidateDao==true)
					{
						strCardDaoReturnVal=dao.updTimeCardDetailsInfo(theTimeCardObj);
						return strCardDaoReturnVal;
					}
					else
					{
						return strCardDaoReturnVal;
					}					
				}
				return strCardDaoReturnVal;
			}
			else
			{
				return strDaoReturnVal;
			}
		}
		else
		{
			return strDaoReturnVal;
		}

	}	 

	public List<TimeCardView> getTimeCardView() throws DbException, AppException  
	{
		logger.debug("Inside getTimeCardView creating DAO Object");
		TimeCardDao dao = new TimeCardDao();
		logger.debug("Inside getTimeCardView creating DAO Object 2");
		return  (List<TimeCardView>)dao.getTimeCardView();
	}
}
