package com.awcsoftware.app.timesheet;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.app.Util;

public class TimeCardService {
	static Logger logger = Logger.getLogger(TimeCardService.class);

	public String addTimeSheetDetails(TimeCardSummaryInfo addTCSumInfo)
			throws DbException, AppException {
		String strTCSummaryRet = "", strTCReturn = "",strTCDetQry="";
		int iCtr=0;
		// validate here
		TimeCardValidator tcv=new TimeCardValidator();
		
		Set validationValue=tcv.validateSaveTimeCard(addTCSumInfo);
		// if success
		// add
		if(validationValue.size()==0)
		{
			TimeCardDao dao = new TimeCardDao();			
			if(addTCSumInfo.getTcId()==0)
			{
				TimeCardDBValidate tcDBVal=(TimeCardDBValidate) dao.vldTCWeekDT(addTCSumInfo);
				if(tcDBVal==null)
				{
					strTCSummaryRet = dao.addTCSummaryInfo(addTCSumInfo);
					if (strTCSummaryRet.equals("S0000")) 
					{
						for (TimeCardDetails theTimeCardObj : addTCSumInfo.getTimeCardDetails()) 
						{
							theTimeCardObj.setTcId(addTCSumInfo.getTcId());
							if(com.awcsoftware.app.Util.chkDateRange(theTimeCardObj.getWorkingDate().toString(), 
									addTCSumInfo.getWeekStart().toString(), addTCSumInfo.getWeekEnd().toString()) ==false)
							{
								if(theTimeCardObj.getTcdId()==0)
								{
									TimeCardTimeValidate TCTimeVld= dao.vldTCDateTime(theTimeCardObj);
									
								}								
							}
							else
							{
								return "Time Card Date Doesn't lies in the Week range ["+addTCSumInfo.getWeekStart()+"] ["+addTCSumInfo.getWeekEnd()+"] ["+theTimeCardObj.getWorkingDate()+"]";
							}
							
						}			
						strTCReturn = dao.addTCDetails(addTCSumInfo.getTimeCardDetails());
						if(!strTCReturn.equals("S0000"))
							return strTCReturn;
						else
							return strTCReturn;
					}
					else
						return strTCSummaryRet;					
				}
				else
					return "Data is already entered for selected week Week range ["+addTCSumInfo.getWeekStart()+"] ["+addTCSumInfo.getWeekEnd()+"] ";
			}
			else
			{
				TimeCardDBValidate tcDBVal=(TimeCardDBValidate) dao.vldTCWeekDT(addTCSumInfo);
				if(tcDBVal.getTcId()==addTCSumInfo.getTcId())
				{
					strTCSummaryRet= dao.updTCSummaryInfo(addTCSumInfo);
					if(strTCSummaryRet.equals("S0000"))
					{
						for (TimeCardDetails theTCObj : addTCSumInfo.getTimeCardDetails()) 
						{
							if(com.awcsoftware.app.Util.chkDateRange(theTCObj.getStartTime(), 
										addTCSumInfo.getWeekStart().toString(), addTCSumInfo.getWeekEnd().toString()) ==false)
							{
								if(theTCObj.getTcdId()==0)
								{
									TimeCardTimeValidate TCTimeVld= dao.vldTCDateTime(theTCObj);
//Validate Time True when time lies in between range
									if(TCTimeVld.getTcdId()==0)
									{
										strTCReturn=dao.updTCDetails(theTCObj);
										if(!strTCReturn.equals("S0000"))
										{
											return strTCReturn;
										}
									}
									else
									{
										return "Data is already entered for given time period";
									}
								}
								else
								{

								}	
																
							}
							else
								return "Timesheet date is not in selected week";
						}						
					}
				}
				else
				{
					return "Incoming Timesheet detail is mismatched";
				}
			}
		}
		else 
		{
			return validationValue.toString();
		}
		return "Stop";		
	}
	
	public String updTimeSheetDetails(TimeCardSummaryInfo updTCSumObj) throws DbException, AppException 
	{
		boolean resultValidateDao; // validate here // if success // add
		String strTCSumRet="No Data Found",strCardDaoReturnVal="No Data Found";
		TimeCardDao dao = new TimeCardDao();
logger.info("updTCSumObj>>>>>>>>"+updTCSumObj);		
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
