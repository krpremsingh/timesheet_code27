package com.awcsoftware.app.timesheet;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;

public class TimeCardDao {
	static Logger logger = Logger.getLogger(TimeCardDao.class);

	public String addTCSummaryInfo(TimeCardSummaryInfo objTCSummary) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
//Inserting data into table TimeCardSummaryInfo
			session.insert("TimeSheetSummaryMapper.addTCSummaryInfo", objTCSummary);
			session.commit();
		} finally {
			session.close();
		}
		return "S0000";
	}

	public String updTCSummaryInfo(TimeCardSummaryInfo objTCSummary) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			session.insert("TimeSheetSummaryMapper.updTCSummaryInfo", objTCSummary);
			session.commit();
		} finally {
			session.close();
		}
		return "S0000";
	}

	public String addTCDetails(List<TimeCardDetails> lstTCDetails) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try 
		{
			session.insert("TimeSheetSummaryMapper.addTCDetails", lstTCDetails);
			session.commit();
		} finally {
			session.close();
		}
		return "S0000";
	}

	public String updTCDetails(TimeCardDetails objTCDetails) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try 
		{
			session.insert("TimeSheetSummaryMapper.updTCDetails", objTCDetails);
			session.commit();
		} finally {
			session.close();
		}
		return "S0000";
	}

	public boolean vldTimeSummaryId(int tcId) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
logger.info("itcId>>>>>>>>>>"+tcId);			
			int iResult = session.selectOne("TimeSheetSummaryMapper.srhTCSummaryById", tcId);
logger.info("iResult>>>>>>>>>>"+iResult);			

			if (iResult == -1)
				return false;
			else
				return true;
		} finally {
			session.close();
		}
	}
	
	public boolean vldTCById(TimeCardDetails objTCDetails) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.selectOne("TimeSheetSummaryMapper.srhTCByID", objTCDetails);
			if (result == -1)
				return false;
			else
				return true;
		} finally {
			session.close();
		}
	}
	public List<TimeCardView> getTCSummaryView()  throws DbException 
	{
		List<TimeCardView> theTimeCardViewObj = null;
		SqlSession session = MyBatisManager.openSession();
		try
		{
			List<TimeCardView> lstTCView = session.selectList("getTCView");
			return lstTCView;
		} finally {
			session.close();
		}
	}	

}
