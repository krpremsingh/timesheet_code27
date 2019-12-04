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
		SqlSession session = MyBatisManager.openSession();
		int iProcessReturn = 0;
		try {

//Comparing Week Range Overlapping
			if (isDataForCurrentWeekExists(timecardInfo) == false) {
				session.insert("TimecardMapper.addTimecardInfo", timecardInfo);

				iProcessReturn = processTimecardDayDetails(timecardInfo, session, "Insert");
				if (iProcessReturn == 0)
					return TimecardErrorConstants.TimecardTimeDataExist.getLabel();

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
		int iProcessReturn = 0;
		try {
//Check current timecard id exist or not false means timecard id doesn't exist
			if (isDataForCurrenttimecardExist(timecardInfo) == false)
				return TimecardErrorConstants.InvalidTimecard.getLabel();
//Update Timecard information.
			session.update("TimecardMapper.updateTimecardInfo", timecardInfo);

			iProcessReturn = processTimecardDayDetails(timecardInfo, session, "Update");
			switch (iProcessReturn) {
			case 0:
				return TimecardErrorConstants.TimecardDateDataExist.getLabel();
			case 2:
				return TimecardErrorConstants.TimecardTimeDataExist.getLabel();
			}
			session.commit();
			return TimecardErrorConstants.TimecardSuccessMessage.getLabel();

		} finally {
			session.close();
		}
	}

	public int processTimecardDayDetails(TimecardInfo timecardInfo, SqlSession sqlSession, String strAction)
			throws DbException {
		SqlSession session = sqlSession;
		int iProjectId=0;
		int iSubmitReturn=0;
		try {
			float totalWeekHour = 0, totalDayHour = 0;
			if (strAction.equals("Insert")) {
				for (TimecardDayInfo timecardDayInfo : timecardInfo.getTimecardDayInfo()) {

					totalDayHour = 0;
//Setting tcId of parent (TimecardInfo) table into TimecardDayInfo.
					timecardDayInfo.setTcId(timecardInfo.getTcId());

//Validate current date data is already lying in table TimecardDayInfo
					if (isDataForCurrentDateExists(timecardDayInfo) == false) {
						return 0;
					}
					session.insert("TimecardMapper.addIndividualTimecardDayInfo", timecardDayInfo);

//Iterating TimecardDayDetails
					for (TimecardDayDetails timecardDayDetails : timecardDayInfo.getTimecardDayDetails()) {
						timecardDayDetails.setTcId(timecardDayInfo.getTcId());
						timecardDayDetails.setTcdId(timecardDayInfo.getTcdId());
						if(timecardInfo.getStatus().equals("Pending"))
						{
							if(iProjectId!=timecardDayDetails.getProjectId())
							{
								iSubmitReturn=submitTimecardDetails(timecardDayDetails,session);							
							}
							iProjectId=timecardDayDetails.getProjectId();
						}
					}

//					timecardDayInfo.setTotalWeekWorkHours(totalDayHour);
//					timecardInfo.setTotalHours(totalWeekHour);
					session.update("TimecardMapper.updateTimecardInfoHour", timecardInfo);
					session.update("TimecardMapper.updateTimecardDayHourInfo", timecardDayInfo);
					session.insert("TimecardMapper.addBatchTimecardDayDetails",
							timecardDayInfo.getTimecardDayDetails());
				}
			} else if (strAction.equals("Update")) {
				// Iterating list of Daywise details of time card
				for (TimecardDayInfo timecardDayInfo : timecardInfo.getTimecardDayInfo()) {
					totalDayHour = 0;

					// Checking data exist for current timecard id and current entered date. true
					// means data doesn't exist
					if (isDataForCurrentDateExists(timecardDayInfo) == false) {
						if (timecardDayInfo.getTcdId() == 0)
							return 0;
						session.update("TimecardMapper.updateTimecardDayInfo", timecardDayInfo);
						// Iterating list of per day details.
						for (TimecardDayDetails timecardDayDetails : timecardDayInfo.getTimecardDayDetails()) {
							if (timecardDayDetails.getTcddId() != 0) {
								timecardDayDetails.setTcId(timecardInfo.getTcId());
								timecardDayDetails.setTcdId(timecardDayInfo.getTcdId());
								// Updating timecard details per row
								session.update("TimecardMapper.updateTimecardDayDetails", timecardDayDetails);
								if(timecardInfo.getStatus().equals("Pending"))
								{
									if(iProjectId!=timecardDayDetails.getProjectId())
									{
										iSubmitReturn=submitTimecardDetails(timecardDayDetails,session);							
									}
									iProjectId=timecardDayDetails.getProjectId();
								}

							} else {
								if (isDataForCurrentTimecardExists(timecardDayDetails) == false) {
									return 2;
								}
								/*
								 * totalWeekHour += timecardDayDetails.getWorkingHours(); totalDayHour +=
								 * timecardDayDetails.getWorkingHours();
								 */
								// Insert daywise details If data doesn't exist for new record
								session.insert("TimecardMapper.addIndividualTimecardDayDetails", timecardDayDetails);
								if(timecardInfo.getStatus().equals("Pending"))
								{
									if(iProjectId==0 &&
											iProjectId!=timecardDayDetails.getProjectId())
									{
										iSubmitReturn=submitTimecardDetails(timecardDayDetails,session);							
									}
									iProjectId=timecardDayDetails.getProjectId();
								}

							}
						}
					} else {
						// Insert new day details If data doesn't exist for new record
						session.insert("TimecardMapper.addIndividualTimecardDayInfo", timecardDayInfo);
						for (TimecardDayDetails timecardDayDetails : timecardDayInfo.getTimecardDayDetails()) {

							/*
							 * totalWeekHour += timecardDayDetails.getWorkingHours(); totalDayHour +=
							 * timecardDayDetails.getWorkingHours();
							 */							timecardDayDetails.setTcId(timecardDayInfo.getTcId());
							timecardDayDetails.setTcdId(timecardDayInfo.getTcdId());
							if(timecardInfo.getStatus().equals("Pending"))
							{
								if(iProjectId!=timecardDayDetails.getProjectId())
								{
									iSubmitReturn=submitTimecardDetails(timecardDayDetails,session);							
								}
								iProjectId=timecardDayDetails.getProjectId();
							}

						}
						// Insert new daywise details If data doesn't exist for new record
						session.insert("TimecardMapper.addBatchTimecardDayDetails",
								timecardDayInfo.getTimecardDayDetails());

					}
					// Update total day work hour
	//				timecardDayInfo.setTotalWeekWorkHours(totalDayHour);
					session.update("TimecardMapper.updateTimecardDayHourInfo", timecardDayInfo);
				}
				// Update total Week work hour
//				timecardInfo.setTotalHours(totalWeekHour);
				session.update("TimecardMapper.updateTimecardInfoHour", timecardInfo);
			}
		} finally {
		}
		return 1;
	}

	public int submitTimecardDetails(TimecardDayDetails timecardDayDetails, SqlSession sqlSession)
			throws DbException {
		SqlSession session = sqlSession;
		try {
			List<TimecardApproverDetails> lsttimecardApproverDetails = session
					.selectList("TimecardMapper.getProjectApproverList", timecardDayDetails);
			for(int iApprover=0;iApprover<lsttimecardApproverDetails.size();iApprover++)
			{
				TimecardApproverDetails  timecardApproverDetails=lsttimecardApproverDetails.get(iApprover);
				timecardApproverDetails.setTcId(timecardDayDetails.getTcId());
				if(isApproverExistforCurrentTimecardProject(timecardApproverDetails)==false)
					session.insert("TimecardMapper.addTimecardApproverDetails",timecardApproverDetails);			
			}
			
		} finally {			
		}
		return 1;
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

	public boolean isApproverExistforCurrentTimecardProject(TimecardApproverDetails timecardApproverDetails) {
		SqlSession session = MyBatisManager.openSession();
		int tcId = 0;
		try {
			tcId = session.selectOne("TimecardMapper.isApproverExistforCurrentTimecardProject", timecardApproverDetails);
			if (tcId == -1)
				return false;
			else
				return true;
		} finally {
			session.close();
		}
	}

	public List<TimecardInfo> getTimecardByManager(int approverId) {
		SqlSession session = MyBatisManager.openSession();

		try {
			List<TimecardInfo> result = session.selectList("TimecardMapper.getTimecardByManager", approverId);
			if (result != null)
				return result;
			else
				return null;
		} finally {
			session.close();
		}

	}
}
