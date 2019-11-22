package com.awcsoftware.app.timesheet;

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

			for (TimeCardDetails tcd : theTimeCardSummaryinfoObj.getTimeCardDetails()) {
				tcd.setTcId(theTimeCardSummaryinfoObj.getTcId());
//Inserting data into table TimeCardDetails				
				session.insert("TimeSheetSummaryMapper.insTimeCardDetailsID", tcd);
			}
			session.commit();
		} finally {
			session.close();
		}
		return "added to database";
	}

	public String updTimeCardSummaryInfo(TimeCardSummaryInfo theTimeCardSummaryinfoObj) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			session.insert("TimeSheetSummaryMapper.TimecardSummaryInfoId", theTimeCardSummaryinfoObj);
			session.commit();
		} finally {
			session.close();
		}
		return "added to database";
	}

	public String addTimeCardDetailsInfo(TimeCardSummaryInfo theTimeCardSummaryinfoObj) throws DbException {
		SqlSession session = MyBatisManager.openSession();

		try {
			for (TimeCardDetails theTimeCardObj : theTimeCardSummaryinfoObj.getTimeCardDetails()) {
				theTimeCardObj.setTcId(theTimeCardSummaryinfoObj.getTcId());
				session.insert("TimeSheetSummaryMapper.insTimeCardDetailsID", theTimeCardObj);
			}

			session.commit();
		} finally {
			session.close();
		}
		return "added to database";
	}

	public boolean validateTimeSummaryById(int itcId) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.selectOne("TimeSheetSummaryMapper.findTimeSummaryInfoById", itcId);
			if (result == -1)
				return false;
			else
				return true;
		} finally {
			session.close();
		}
	}

}
