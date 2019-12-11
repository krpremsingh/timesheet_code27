package com.awcsoftware.app.timesheet;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.awcsoftware.app.AppConstant;
import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;
import com.awcsoftware.spring.security.auth.user.User;

public class TimecardDao {
	static Logger logger = Logger.getLogger(TimecardDao.class);

/*		************************************************************************
 * 			Method Starts for Filling and view Timecard by an Employee 
 *		************************************************************************ 
 */
	
/*
 *  Parent method for saving/editing of timecard detail by an employee
 * 
 */
	
	public String saveTimecard(TimecardInfo timecardInfo) throws DbException, AppException {
		SqlSession session = MyBatisManager.openSession();
		String saveTimecardReturn = "";
		
		try {
			if (timecardInfo.getTcId() == AppConstant.WORKING_HOURS.Zero.getValue()) {
				if (addTimecard(timecardInfo, session) == true) {
					saveTimecardReturn=TimecardMessageConstant.TimecardSuccessMessage.getLabel();
				} else
					saveTimecardReturn=TimecardMessageConstant.Timecard_Data_Exist.getLabel();
			} else {
				saveTimecardReturn = updateTimecardByView(timecardInfo, session);
			}
			session.commit();
			return saveTimecardReturn;
		} finally {
			session.close();
		}
	}

	/*
	 *  Parent method for Submitting of timecard detail by an employee
	 * 
	 */

	public String submitTimecard(TimecardInfo timecardInfo) throws DbException, AppException {
		SqlSession session = MyBatisManager.openSession();
		String submitTimecardReturn = "";
		try {
			if (isTimecardAlreadySubmitted(timecardInfo, session) == true)
				submitTimecardReturn=TimecardMessageConstant.TimecardAlreadySubmitMessage.getLabel();
			else {
				if (timecardInfo.getTcId() == AppConstant.WORKING_HOURS.Zero.getValue()) {
					if (addTimecard(timecardInfo, session) == true) {						
						submitTimecardReturn=TimecardMessageConstant.TimecardSubmitMessage.getLabel();
					} 					
					else
						submitTimecardReturn=TimecardMessageConstant.Timecard_Data_Exist.getLabel();	
				} else {
					if (updateTimecardByView(timecardInfo, session)
							.equals(TimecardMessageConstant.TimecardUpdateMessage.getLabel())) {					
						submitTimecardReturn=TimecardMessageConstant.TimecardSubmitMessage.getLabel();
					}

				}
			}
			session.commit();
			return submitTimecardReturn;

		} finally {
			session.close();
		}
	}

	public boolean addTimecard(TimecardInfo timecardInfo, SqlSession session) throws DbException, AppException {
		if (isTimecardExistsForCurrentWeek(timecardInfo, session) == false) {
			addTimecardInfo(timecardInfo, session);
			addTimecardDayInfo(timecardInfo, session);
			session.update("TimecardMapper.updateTimecardInfoHour", timecardInfo);
			return true;
		} else {
			return false;
		}
	}

	private void addTimecardInfo(TimecardInfo timecardInfo, SqlSession session) throws DbException, AppException {
		session.insert("TimecardMapper.addTimecardInfo", timecardInfo);
	}

	private void addTimecardDayInfo(TimecardInfo timecardInfo, SqlSession session) throws DbException, AppException {
		int timecardDayInfoCtr = -1;
		for (TimecardDayInfo timecardDayInfo : timecardInfo.getTimecardDayInfo()) {
			timecardDayInfo.setTcId(timecardInfo.getTcId());
			timecardDayInfo.setStatus(timecardInfo.getStatus());
			timecardDayInfoCtr = isTimecardExistsForCurrentDate(timecardDayInfo, session);
			if (timecardDayInfoCtr == -1) {
				session.insert("TimecardMapper.addIndividualTimecardDayInfo", timecardDayInfo);
				addTimecardDayDetails(timecardDayInfo, session);
			} else {
				timecardDayInfo.setTcdId(timecardDayInfoCtr);
				addTimecardDayDetails(timecardDayInfo, session);
			}
		}
	}

	private void addTimecardDayDetails(TimecardDayInfo timecardDayInfoParam, SqlSession session)
			throws DbException, AppException {
		int projectId = 0;
		for (TimecardDayDetails timecardDayDetails : timecardDayInfoParam.getTimecardDayDetails()) {

			timecardDayDetails.setTcId(timecardDayInfoParam.getTcId());
			timecardDayDetails.setTcdId(timecardDayInfoParam.getTcdId());
			timecardDayDetails.setStatus(timecardDayInfoParam.getStatus());
			timecardDayDetails.setRecordType(AppConstant.RECORD_TYPE.Active.toString());

			if (timecardDayInfoParam.getStatus().equals(AppConstant.TIME_CARD_STATUS.Pending.toString())) {
				if (projectId != timecardDayDetails.getProjectId()) {
					submitTimecardDetails(timecardDayDetails, session);
				}
				projectId = timecardDayDetails.getProjectId();
			}
		}
		session.insert("TimecardMapper.addBatchTimecardDayDetails", timecardDayInfoParam.getTimecardDayDetails());
		session.update("TimecardMapper.updateTimecardDayHourInfo", timecardDayInfoParam);
	}

	private String updateTimecardByView(TimecardInfo timecardInfo, SqlSession session)
			throws DbException, AppException {
		session.update("TimecardMapper.DeactivateTimecardDayDetails", timecardInfo.getTcId());
		addTimecardDayInfo(timecardInfo, session);
		session.update("TimecardMapper.updateTimecardInfoHour", timecardInfo);
		return TimecardMessageConstant.TimecardUpdateMessage.getLabel();
	}

	public void submitTimecardDetails(TimecardDayDetails timecardDayDetails, SqlSession session)
			throws DbException, AppException {
		List<TimecardApproverDetails> timecardAppDetails = session.selectList("TimecardMapper.getProjectApproverList",
				timecardDayDetails);
		for (int approverCtr = 0; approverCtr < timecardAppDetails.size(); approverCtr++) {
			TimecardApproverDetails timecardApproverDetails = timecardAppDetails.get(approverCtr);
			timecardApproverDetails.setTcId(timecardDayDetails.getTcId());
			if (isApproverExistforCurrentTimecardProject(timecardApproverDetails, session) == false)
				session.insert("TimecardMapper.addTimecardApproverDetails", timecardApproverDetails);
		}
	}

	public List<TimecardInfo> getEmployeeTimeCard(TimecardInfo timecardInfoParam) throws DbException {
		List<TimecardInfo> timecardInfo = null;
		SqlSession session = MyBatisManager.openSession();
		try {
			timecardInfo = session.selectList("TimecardMapper.getTimecardView", timecardInfoParam);
			return timecardInfo;
		} finally {
			session.close();
		}
	}

	public List<TimecardView> getTimecardEmployeeDetailView(int tcId) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			List<TimecardView> timecardDetailView = session.selectList("TimecardMapper.getTimecardDetailView", tcId);
			return timecardDetailView;
		} finally {
			session.close();
		}
	}

	public TimecardInfo getTimecardSavedRecord(TimecardInfo timecardInfoParam) throws DbException {
		TimecardInfo timecardInfo = null;
		SqlSession session = MyBatisManager.openSession();
		try {
			timecardInfo = session.selectOne("TimecardMapper.getTimecardInfoPerTCID", timecardInfoParam);
			return timecardInfo;
		} finally {
			session.close();
		}
	}

	public List<TimecardDayDetails> getTimecardSavedRecordData(TimecardInfo timecardInfoParam) throws DbException {
		List<TimecardDayDetails> timecardDayDetails = null;
		SqlSession session = MyBatisManager.openSession();
		try {
			timecardDayDetails = session.selectList("TimecardMapper.getTimecardViewDetails", timecardInfoParam);
			return timecardDayDetails;
		} finally {
			session.close();
		}
	}

	public boolean isTimecardExistsForCurrentWeek(TimecardInfo timecardInfo, SqlSession session) {
		int tcId = 0;
		tcId = session.selectOne("TimecardMapper.isDataForCurrentWeekExists", timecardInfo);
		if (tcId == -1)
			return false;
		else
			return true;
	}

	public int isTimecardExistsForCurrentDate(TimecardDayInfo timecardDayInfo, SqlSession session) throws DbException {
		int tcdId = 0;
		tcdId = session.selectOne("TimecardMapper.isTimecardExistsForCurrentDateForCurrentEmployee", timecardDayInfo);
		return tcdId;
	}

	public boolean isCurrentTimecardExistForCurrentEmployee(TimecardInfo timecardInfo, SqlSession session)
			throws DbException {
		int tcId = 0;
		tcId = session.selectOne("TimecardMapper.isCurrentTimecardExistForCurrentEmployee", timecardInfo);
		if (tcId == -1)
			return false;
		else
			return true;
	}

	public boolean isTimecardAlreadySubmitted(TimecardInfo timecardInfo, SqlSession session) {
		int tcId = 0;
		tcId = session.selectOne("TimecardMapper.isTimecardAlreadySubmitted", timecardInfo);
		if (tcId == -1)
			return false;
		else
			return true;
	}

	public boolean isApproverExistforCurrentTimecardProject(TimecardApproverDetails timecardApproverDetails,
			SqlSession session) throws DbException {
		int tcId = 0;
		tcId = session.selectOne("TimecardMapper.isApproverExistforCurrentTimecardProject", timecardApproverDetails);
		if (tcId == -1)
			return false;
		else
			return true;
	}

/*		************************************************************************
* 			Method Ended for Filling and view Timecard by an Employee 
*		************************************************************************ 
*/
	
/*		************************************************************************
* 			Method Starts for Manager's Action 
*		************************************************************************ 
*/	

	public List<TimecardInfo> getTimecardViewForManager(TimecardInfo timecardInfoManager) {
		SqlSession session = MyBatisManager.openSession();
		try {

			List<TimecardInfo> result = session.selectList("TimecardMapper.getTimecardApproverSummary", timecardInfoManager);
			logger.debug(result);			
			if (result != null)
				return result;
			else
				return null;
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

	public List<User> getEmployeesUnderLoggedInManager(int approverId) {
		SqlSession session = MyBatisManager.openSession();

		try {
			List<User> result = session.selectList("TimecardMapper.getEmployeesUnderLoggedInManager", approverId);
			if (result != null)
				return result;
			else
				return null;
		} finally {
			session.close();
		}

	}

	public List<User> getEmployeeNames(int approverId) {
		SqlSession session = MyBatisManager.openSession();

		try {
			List<User> result = session.selectList("TimecardMapper.getEmployeeNames", approverId);
			if (result != null)
				return result;
			else
				return null;
		} finally {
			session.close();
		}

	}

	public List<TimecardManagerView> getTimecardByManager(TimecardManagerView view) {
		SqlSession session = MyBatisManager.openSession();

		try {
			List<TimecardManagerView> result = session.selectList("TimecardMapper.getTimecardByManager", view);
			if (result != null)
				return result;
			else
				return null;
		} finally {
			session.close();
		}

	}
}
