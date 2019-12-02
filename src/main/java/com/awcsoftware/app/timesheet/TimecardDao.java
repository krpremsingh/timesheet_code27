package com.awcsoftware.app.timesheet;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;

public class TimecardDao {
	static Logger logger = Logger.getLogger(TimecardDao.class);

	public String addTimecard(TimecardInfo timecardInfo) throws DbException, AppException {
		float totalWeekHour = 0, totalDayHour = 0;
		SqlSession session = MyBatisManager.openSession();
		try {

//Comparing Week Range Overlapping
			if (isDataForCurrentWeekExists(timecardInfo) == false) {
				session.insert("TimecardMapper.addTimecardInfo", timecardInfo);

//Iterating TimecardInfo object to fetch details of table TimecardInfo. 
				for (TimecardDayInfo timecardDayInfo : timecardInfo.getTimecardDayInfo()) {

					totalDayHour = 0;
//Setting tcId of parent (TimecardInfo) table into TimecardDayInfo.
					timecardDayInfo.setTcId(timecardInfo.getTcId());

//Validate current date data is already lying in table TimecardDayInfo
					if (isDataForCurrentDateExists(timecardDayInfo) == false) {
						return TimecardErrorConstants.TimecardTimeDataExist.getLabel();
					}
					session.insert("TimecardMapper.addIndividualTimecardDayInfo", timecardDayInfo);

//Iterating TimecardDayDetails
					for (TimecardDayDetails timecardDayDetails : timecardDayInfo.getTimecardDayDetails()) {
						totalWeekHour += timecardDayDetails.getWorkingHours();
						totalDayHour += timecardDayDetails.getWorkingHours();
						timecardDayDetails.setTcId(timecardDayInfo.getTcId());
						timecardDayDetails.setTcdId(timecardDayInfo.getTcdId());
					}

					timecardDayInfo.setTotalWeekWorkHours(totalDayHour);
					timecardInfo.setTotalHours(totalWeekHour);
					session.update("TimecardMapper.updateTimecardInfoHour", timecardInfo);
					session.update("TimecardMapper.updateTimecardDayHourInfo", timecardDayInfo);
					session.insert("TimecardMapper.addBatchTimecardDayDetails",
							timecardDayInfo.getTimecardDayDetails());
				}
				session.commit();
				return TimecardErrorConstants.TimecardSuccessMessage.getLabel();
			} else
				return TimecardErrorConstants.TimecardDataExist.getLabel();

		} finally {
			session.close();
		}
	}

	public String updateTimecard(TimecardInfo timecardInfo) throws DbException, AppException {
		SqlSession session = MyBatisManager.openSession();
		float totalWeekHour = 0, totalDayHour = 0;
		try {
//Check current timecard id exist or not false means timecard id doesn't exist
			if (isDataForCurrenttimecardExist(timecardInfo) == false)
				return TimecardErrorConstants.InvalidTimecard.getLabel();
//Update Timecard information.
			session.update("TimecardMapper.updateTimecardInfo", timecardInfo);
//Iterating list of Daywise details of time card			
			for (TimecardDayInfo timecardDayInfo : timecardInfo.getTimecardDayInfo()) {
				totalDayHour = 0;

//Checking data exist for current timecard id and current entered date. true means data doesn't exist
				if (isDataForCurrentDateExists(timecardDayInfo) == false) {
					if (timecardDayInfo.getTcdId() == 0)
						return TimecardErrorConstants.TimecardDateDataExist.getLabel();
					session.update("TimecardMapper.updateTimecardDayInfo", timecardDayInfo);
//Iterating list of per day details.
					for (TimecardDayDetails timecardDayDetails : timecardDayInfo.getTimecardDayDetails()) {
						if (timecardDayDetails.getTcddId() != 0) {
							totalWeekHour += timecardDayDetails.getWorkingHours();
							totalDayHour += timecardDayDetails.getWorkingHours();
							timecardDayDetails.setTcId(timecardInfo.getTcId());
							timecardDayDetails.setTcdId(timecardDayInfo.getTcdId());
//Updating timecard details per row
							session.update("TimecardMapper.updateTimecardDayDetails", timecardDayDetails);
						} else {
							if (isDataForCurrentTimecardExists(timecardDayDetails) == false) {
								return TimecardErrorConstants.TimecardTimeDataExist.getLabel();
							}
							totalWeekHour += timecardDayDetails.getWorkingHours();
							totalDayHour += timecardDayDetails.getWorkingHours();

//Insert daywise details If data doesn't exist for new record
							session.insert("TimecardMapper.addIndividualTimecardDayDetails", timecardDayDetails);
						}
					}
				} else {
//Insert new day details If data doesn't exist for new record
					session.insert("TimecardMapper.addIndividualTimecardDayInfo", timecardDayInfo);
					for (TimecardDayDetails timecardDayDetails : timecardDayInfo.getTimecardDayDetails()) {

						totalWeekHour += timecardDayDetails.getWorkingHours();
						totalDayHour += timecardDayDetails.getWorkingHours();
						timecardDayDetails.setTcId(timecardDayInfo.getTcId());
						timecardDayDetails.setTcdId(timecardDayInfo.getTcdId());
					}
//Insert new daywise details If data doesn't exist for new record
					session.insert("TimecardMapper.addBatchTimecardDayDetails",
							timecardDayInfo.getTimecardDayDetails());

				}
//Update total day work hour
				timecardDayInfo.setTotalWeekWorkHours(totalDayHour);
				session.update("TimecardMapper.updateTimecardDayHourInfo", timecardDayInfo);
			}
//Update total Week work hour
			timecardInfo.setTotalHours(totalWeekHour);
			session.update("TimecardMapper.updateTimecardInfoHour", timecardInfo);
			session.commit();
			return TimecardErrorConstants.TimecardSuccessMessage.getLabel();

		} finally {
			session.close();
		}
	}

	public List<TimecardView> getTimecardView(int empId) throws DbException {
		List<TimecardView> theTimeCardViewObj = null;
		SqlSession session = MyBatisManager.openSession();
		try {
			List<TimecardView> lstTimecardView = session.selectList("TimecardMapper.getTimecardView", empId);
			return lstTimecardView;
		} finally {
			session.close();
		}
	}

	public List<TimecardView> getTimecardDetailsView(int tcId) throws DbException {
		List<TimecardView> theTimeCardViewObj = null;
		SqlSession session = MyBatisManager.openSession();
		try {
			List<TimecardView> lstTimecardDetailView = session.selectList("TimecardMapper.getTimecardDetailView", tcId);
			return lstTimecardDetailView;
		} finally {
			session.close();
		}
	}

	public boolean isDataForCurrentWeekExists(TimecardInfo timecardInfo) {
		SqlSession session = MyBatisManager.openSession();
		int tcId = 0;
		try {
			tcId = session.selectOne("TimecardMapper.isDataForCurrentWeekExists", timecardInfo);
			if (tcId == -1)
				return false;
			else
				return true;
		} finally {
			session.close();
		}
	}

	public boolean isDataForCurrentDateExists(TimecardDayInfo timecardDayInfo) {
		SqlSession session = MyBatisManager.openSession();
		int tcdId = 0;
		try {
			tcdId = session.selectOne("TimecardMapper.isDataForCurrentDateExists", timecardDayInfo);
			if (tcdId == -1)
				return true;
			else
				return false;
		} finally {
			session.close();
		}
	}

	public boolean isDataForCurrentDateTimeExists(TimecardDayDetails timecardDayDetails) {
		SqlSession session = MyBatisManager.openSession();
		int tcdId = 0;
		try {
			tcdId = session.selectOne("TimecardMapper.isDataForCurrentDateTimeExists", timecardDayDetails);
			if (tcdId == -1)
				return true;
			else
				return false;
		} finally {
			session.close();
		}
	}

	public boolean isDataForCurrentTimecardExists(TimecardDayDetails timecardDayDetails) {
		SqlSession session = MyBatisManager.openSession();
		int tcdId = 0;
		try {
			tcdId = session.selectOne("TimecardMapper.isDataForCurrentTimecardExists", timecardDayDetails);
			if (tcdId == -1)
				return true;
			else
				return false;
		} finally {
			session.close();
		}
	}

	public boolean isDataForCurrenttimecardExist(TimecardInfo timecardInfo) {
		SqlSession session = MyBatisManager.openSession();
		int tcId = 0;
		try {
			tcId = session.selectOne("TimecardMapper.isCurrentTimecardExist", timecardInfo);
			if (tcId == -1)
				return false;
			else
				return true;
		} finally {
			session.close();
		}
	}
	
	public List<TimecardInfo> getTimecardByManager(int approverId){
		SqlSession session = MyBatisManager.openSession();

		try {
			List<TimecardInfo> result = session.selectList("TimeSheetSummaryMapper.getTimecardByManager",approverId);
			if (result!=null)
				return result;
			else
				return null;
		} finally {
			session.close();
		}
		
	}
}
