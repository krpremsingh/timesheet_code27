package com.awcsoftware.app.timesheet;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;
import com.awcsoftware.spring.security.auth.user.User;

public class TimecardDao {
	static Logger logger = Logger.getLogger(TimecardDao.class);

	public String saveTimecard(TimecardInfo timecardInfo) throws DbException, AppException {
		SqlSession session = MyBatisManager.openSession();
		try {
			if(timecardInfo.getTcId()==0)
				return addTimecard(timecardInfo, session);
			else
				return updateTimecardByView(timecardInfo, session);
		} finally {
			session.close();
		}
	}


	public String submitTimecard(TimecardInfo timecardInfo) throws DbException, AppException {
		SqlSession session = MyBatisManager.openSession();
		try {
			return updateTimecardByView(timecardInfo, session);
		} finally {
			session.close();
		}
	}

	public String addTimecard(TimecardInfo timecardInfo, SqlSession sessionParam) throws DbException, AppException {
		SqlSession session = sessionParam;
		try {

			/*
			 * isTimecardExistsForCurrentWeekForCurrentEmployee == false Data Doesn't exist
			 * else Data exist
			 */

			if (isTimecardExistsForCurrentWeekForCurrentEmployee(timecardInfo) == false) {
				addTimecardInfo(timecardInfo, session);
				addTimecardDayInfo(timecardInfo, session);
				session.update("TimecardMapper.updateTimecardInfoHour", timecardInfo);
				session.commit();
				return TimecardMessageConstant.TimecardSuccessMessage.getLabel();
			} else {
				return TimecardMessageConstant.TimecardDataExist.getLabel();	
			}

		} finally {
		}
	}
	
	private String updateTimecardByView(TimecardInfo timecardInfo, SqlSession sessionParam) throws DbException, AppException {
		SqlSession session = sessionParam;
		try
		{
			session.update("TimecardMapper.DeactivateTimecardDayDetails", timecardInfo.getTcId());
			addTimecardDayInfo(timecardInfo, session);
			session.update("TimecardMapper.updateTimecardInfoHour", timecardInfo);
			session.commit();
			return TimecardMessageConstant.TimecardUpdateMessage.getLabel();			
		}
		finally
		{}
		
	}

	private void addTimecardInfo(TimecardInfo timecardInfo, SqlSession sessionParam) throws DbException, AppException {
		SqlSession session = sessionParam;
		try {
			session.insert("TimecardMapper.addTimecardInfo", timecardInfo);
		} finally {
		}
	}

	private void addTimecardDayInfo(TimecardInfo timecardInfo, SqlSession sessionParam)
			throws DbException, AppException {
		SqlSession session = sessionParam;
		int timecardDayInfoCtr=-1;
		try {
			for (TimecardDayInfo timecardDayInfo : timecardInfo.getTimecardDayInfo()) {
				timecardDayInfo.setTcId(timecardInfo.getTcId());
				timecardDayInfoCtr=isTimecardExistsForCurrentDateForCurrentEmployee(timecardDayInfo);
				if ( timecardDayInfoCtr== -1) {
					session.insert("TimecardMapper.addIndividualTimecardDayInfo", timecardDayInfo);
					addTimecardDayDetails(timecardDayInfo, session);
				} else
				{
					timecardDayInfo.setTcdId(timecardDayInfoCtr);
					addTimecardDayDetails(timecardDayInfo, session);
				}
			}

		} finally {
		}
	}

	private void addTimecardDayDetails(TimecardDayInfo timecardDayInfoParam, SqlSession sessionParam)
			throws DbException, AppException {
		SqlSession session = sessionParam;
		int ProjectId = 0, submitReturn = 0;
		try {
			for (TimecardDayDetails timecardDayDetails : timecardDayInfoParam.getTimecardDayDetails()) {
				timecardDayDetails.setTcId(timecardDayInfoParam.getTcId());
				timecardDayDetails.setTcdId(timecardDayInfoParam.getTcdId());
				if (timecardDayInfoParam.getStatus().equals("Pending")) {
					if (ProjectId != timecardDayDetails.getProjectId()) {
						submitReturn = submitTimecardDetails(timecardDayDetails, session);
					}
					ProjectId = timecardDayDetails.getProjectId();
				}
			}
			session.insert("TimecardMapper.addBatchTimecardDayDetails", timecardDayInfoParam.getTimecardDayDetails());
			session.update("TimecardMapper.updateTimecardDayHourInfo", timecardDayInfoParam);
		} finally {
		}
	}

	public int submitTimecardDetails(TimecardDayDetails timecardDayDetails, SqlSession sqlSession) throws DbException {
		SqlSession session = sqlSession;
		try {
			List<TimecardApproverDetails> lsttimecardApproverDetails = session
					.selectList("TimecardMapper.getProjectApproverList", timecardDayDetails);
			for (int iApprover = 0; iApprover < lsttimecardApproverDetails.size(); iApprover++) {
				TimecardApproverDetails timecardApproverDetails = lsttimecardApproverDetails.get(iApprover);
				timecardApproverDetails.setTcId(timecardDayDetails.getTcId());
				if (isApproverExistforCurrentTimecardProject(timecardApproverDetails) == false)
					session.insert("TimecardMapper.addTimecardApproverDetails", timecardApproverDetails);
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

	public boolean isTimecardExistsForCurrentWeekForCurrentEmployee(TimecardInfo timecardInfo) {
		
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

	public int isTimecardExistsForCurrentDateForCurrentEmployee(TimecardDayInfo timecardDayInfo) {
		SqlSession session = MyBatisManager.openSession();
		int tcdId = 0;
		try {
			tcdId = session.selectOne("TimecardMapper.isTimecardExistsForCurrentDateForCurrentEmployee",
					timecardDayInfo);
			return tcdId;
		} finally {
			session.close();
		}
	}

	public boolean isCurrentTimecardExistForCurrentEmployee(TimecardInfo timecardInfo) {
		SqlSession session = MyBatisManager.openSession();
		int tcId = 0;
		try {
			tcId = session.selectOne("TimecardMapper.isCurrentTimecardExistForCurrentEmployee", timecardInfo);
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
			tcId = session.selectOne("TimecardMapper.isApproverExistforCurrentTimecardProject",
					timecardApproverDetails);
			if (tcId == -1)
				return false;
			else
				return true;
		} finally {
			session.close();
		}
	}

	public void approveRejectTimecard(TimecardApproverDetails timecardApproverDetails) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {

		} finally {
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
	
	public List<User> getEmployeeNames(int approverId){
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
