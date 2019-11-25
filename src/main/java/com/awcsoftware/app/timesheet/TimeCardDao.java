package com.awcsoftware.app.timesheet;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;

public class TimeCardDao {
	static Logger logger = Logger.getLogger(TimeCardDao.class);

	public String addTimeCardSummaryInfo(TimeCardSummaryInfo theTimeCardSummaryinfoObj) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
//Inserting data into table TimeCardSummaryInfo
			session.insert("TimeSheetSummaryMapper.TimecardSummaryInfoId", theTimeCardSummaryinfoObj);
			session.commit();
		} finally {
			session.close();
		}
		return "S0000";
	}

	public String updTimeCardSummaryInfo(TimeCardSummaryInfo theTimeCardSummaryinfoObj) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			session.insert("TimeSheetSummaryMapper.updateTimeCardSummaryInfo", theTimeCardSummaryinfoObj);
			session.commit();
		} finally {
			session.close();
		}
		return "S0000";
	}

	public String addTimeCardDetailsInfo(TimeCardDetails theTimeCardObj) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try 
		{
			session.insert("TimeSheetSummaryMapper.insTimeCardDetailsID", theTimeCardObj);
			session.commit();
		} finally {
			session.close();
		}
		return "S0000";
	}

	public String updTimeCardDetailsInfo(TimeCardDetails theTimeCardDetailsObj) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try 
		{
			session.insert("TimeSheetSummaryMapper.updateTimeCardDetails", theTimeCardDetailsObj);
			session.commit();
		} finally {
			session.close();
		}
		return "S0000";
	}

	public boolean validateTimeSummaryById(int tcId) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
logger.info("itcId>>>>>>>>>>"+tcId);			
			int iResult = session.selectOne("TimeSheetSummaryMapper.findTimeSummaryInfoById", tcId);
logger.info("iResult>>>>>>>>>>"+iResult);			

			if (iResult == -1)
				return false;
			else
				return true;
		} finally {
			session.close();
		}
	}
	
	public boolean validateTimeCardByIds(TimeCardDetails theTimeCardDetailsObj) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.selectOne("TimeSheetSummaryMapper.findTimeSummaryInfoById", theTimeCardDetailsObj);
			if (result == -1)
				return false;
			else
				return true;
		} finally {
			session.close();
		}
	}
	public List<TimeCardView> getTimeCardView()  throws DbException 
	{
		List<TimeCardView> theTimeCardViewObj = null;
		SqlSession session = MyBatisManager.openSession();
		try
		{
			logger.debug("Going to execute query getTimeViewCard!!!!");
			List<TimeCardView> theTimeCardViewListObj = session.selectList("getTimeViewCard");
			logger.debug("Query getTimeViewCard is executed>>>"+theTimeCardViewListObj);
			return theTimeCardViewListObj;
		} finally {
			session.close();
		}

	}	

}
