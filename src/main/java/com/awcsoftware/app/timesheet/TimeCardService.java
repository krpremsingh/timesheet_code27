package com.awcsoftware.app.timesheet;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

public class TimeCardService {
	static Logger logger = Logger.getLogger(TimeCardService.class);

	public String addTimeSheetDetails(TimeCardSummaryInfo addTCSumInfo)
			throws DbException, AppException {
		String strTCSummaryRet = "", strTCReturn = "",strTCDetQry="";
		
		// validate here
		TimeCardValidator tcv=new TimeCardValidator();
		
		Set validationValue=tcv.validateSaveTimeCard(addTCSumInfo);
		// if success
		// add
		if(validationValue.size()==0)
		{
			TimeCardDao dao = new TimeCardDao();
			strTCSummaryRet = dao.addTCSummaryInfo(addTCSumInfo);
			if (strTCSummaryRet.equals("S0000")) 
			{
				for (TimeCardDetails theTimeCardObj : addTCSumInfo.getTimeCardDetails()) 
				{
					theTimeCardObj.setTcId(addTCSumInfo.getTcId());
				}			
				strTCReturn = dao.addTCDetails(addTCSumInfo.getTimeCardDetails());
				if(!strTCReturn.equals("S0000"))
					return strTCReturn;

				return strTCReturn;
			}
			else
				return strTCSummaryRet;
		}
		else 
		{
			return validationValue.toString();
		}
		
	}
	
	public String updTimeSheetDetails(TimeCardSummaryInfo updTCSumObj) throws DbException, AppException 
	{
		boolean resultValidateDao; // validate here // if success // add
		String strTCSumRet="No Data Found",strCardDaoReturnVal="No Data Found";
		TimeCardDao dao = new TimeCardDao();
		
		resultValidateDao = dao.vldTimeSummaryId(updTCSumObj.getTcId());
		if (resultValidateDao == true) {
			strTCSumRet= dao.updTCSummaryInfo(updTCSumObj);
			if(strTCSumRet.equals("S0000"))
			{
				for (TimeCardDetails theTimeCardObj : updTCSumObj.getTimeCardDetails()) 
				{
					resultValidateDao = dao.vldTCById(theTimeCardObj);
					if(resultValidateDao==true)
					{
						strCardDaoReturnVal=dao.updTCDetails(theTimeCardObj);
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
				return strTCSumRet;
			}
		}
		else
		{
			return strTCSumRet;
		}

	}	 

	public List<TimeCardView> getTCView() throws DbException, AppException  
	{
		TimeCardDao dao = new TimeCardDao();
		return  (List<TimeCardView>)dao.getTCSummaryView();
	}
}
