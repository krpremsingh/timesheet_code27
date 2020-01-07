package com.awcsoftware.app.timesheet;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.AppConstant;
import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;

@Component
public class TimecardDao {
	static Logger logger = Logger.getLogger(TimecardDao.class);

	/*
	 * ************************************************************ Method Starts
	 * for Filling and view Timecard by an Employee
	 * ************************************************************
	 */

	/*
	 * Parent method for saving/editing of timecard detail by an employee
	 * 
	 */

	public String saveTimecard(TimecardInfo timecardInfo) throws DbException, AppException {
		SqlSession session = MyBatisManager.openSession();
		String saveTimecardReturn = "";

		try {
			if (timecardInfo.getTcId() == AppConstant.WORKING_HOURS.Zero.getValue()) {
				if (addTimecard(timecardInfo, session) == true) {
					saveTimecardReturn = TimecardMessageConstant.TimecardSuccessMessage.getLabel();
				} else
					saveTimecardReturn = TimecardMessageConstant.Timecard_Data_Exist.getLabel();
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
	 * Parent method for Submitting of timecard detail by an employee
	 * 
	 */

	public String submitTimecard(TimecardInfo timecardInfo) throws DbException, AppException {
		SqlSession session = MyBatisManager.openSession();
		String submitTimecardReturn = "";
		try {
			if (isTimecardAlreadySubmitted(timecardInfo, session) == true)
				submitTimecardReturn = TimecardMessageConstant.TimecardAlreadySubmitMessage.getLabel();
			else {
				if (timecardInfo.getTcId() == AppConstant.WORKING_HOURS.Zero.getValue()) {
					if (addTimecard(timecardInfo, session) == true) {
						submitTimecardReturn = TimecardMessageConstant.TimecardSubmitMessage.getLabel();
					} else
						submitTimecardReturn = TimecardMessageConstant.Timecard_Data_Exist.getLabel();
				} else {
					if (updateTimecardByView(timecardInfo, session)
							.equals(TimecardMessageConstant.TimecardUpdateMessage.getLabel())) {
						submitTimecardReturn = TimecardMessageConstant.TimecardSubmitMessage.getLabel();
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
			updateTimecardInfoHour(timecardInfo, session);
			return true;
		} else {
			return false;
		}
	}

	private void addTimecardDayInfo(TimecardInfo timecardInfo, SqlSession session) throws DbException, AppException {
		int timecardDayInfoCtr = -1;
		for (TimecardDayInfo timecardDayInfo : timecardInfo.getTimecardDayInfo()) {
			timecardDayInfo.setTcId(timecardInfo.getTcId());
			timecardDayInfo.setStatus(timecardInfo.getStatus());
			timecardDayInfoCtr = isTimecardExistsForCurrentDate(timecardDayInfo, session);
			if (timecardDayInfoCtr == -1) {
				addTimecardDayInfoQuery(timecardDayInfo, session);
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
		updateTimecardDayInfoHourQuery(timecardDayInfoParam, session);
	}

	private String updateTimecardByView(TimecardInfo timecardInfo, SqlSession session)
			throws DbException, AppException {
		session.update("TimecardMapper.DeactivateTimecardDayDetails", timecardInfo.getTcId());
		addTimecardDayInfo(timecardInfo, session);
		updateTimecardInfoHour(timecardInfo, session);
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
				addTimecardApproverDetails(timecardApproverDetails, session);
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

	private void addTimecardInfo(TimecardInfo timecardInfo, SqlSession session) throws DbException, AppException {
		session.insert("TimecardMapper.addTimecardInfo", timecardInfo);
	}

	private void updateTimecardInfoHour(TimecardInfo timecardInfo, SqlSession session)
			throws DbException, AppException {
		session.insert("TimecardMapper.updateTimecardInfoHour", timecardInfo);
	}

	private void addTimecardDayInfoQuery(TimecardDayInfo timecardDayInfo, SqlSession session)
			throws DbException, AppException {
		session.insert("TimecardMapper.addIndividualTimecardDayInfo", timecardDayInfo);
	}

	private void updateTimecardDayInfoHourQuery(TimecardDayInfo timecardDayInfoParam, SqlSession session)
			throws DbException, AppException {
		session.update("TimecardMapper.updateTimecardDayHourInfo", timecardDayInfoParam);
	}

	private void addTimecardApproverDetails(TimecardApproverDetails timecardApproverDetails, SqlSession session)
			throws DbException, AppException {
		session.insert("TimecardMapper.addTimecardApproverDetails", timecardApproverDetails);
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

	public List<TimecardInfo> getTimecardViewForEmployee(TimecardInfo timecardInfoManager) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {

			List<TimecardInfo> managerTimecardView = session.selectList("TimecardMapper.getTimecardEmployeeView",
					timecardInfoManager);
			managerTimecardView = getTimecardViewPerProjectForManager(managerTimecardView, session);
			return managerTimecardView;
		} finally {
			session.close();
		}
	}

	/*
	 * ************************************************************************
	 * Method Ended for Filling and view Timecard by an Employee
	 * ************************************************************************
	 */

	/*
	 * ************************************************************************
	 * Method Starts for Manager's Action
	 * ************************************************************************
	 */

	public List<TimecardInfo> getTimecardViewForManager(TimecardInfo timecardInfoManager) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		try {

			List<TimecardInfo> managerTimecardView = session.selectList("TimecardMapper.getTimecardApproverSummary",
					timecardInfoManager);
			managerTimecardView = getTimecardViewPerProjectForManager(managerTimecardView, session);
			return managerTimecardView;
		} finally {
			session.close();
		}
	}

	public List<TimecardInfo> getTimecardViewPerProjectForManager(List<TimecardInfo> timecardInfoManager,
			SqlSession session) throws DbException {
		List<Integer> projectValue = null;

		for (int paramCtr = 0; paramCtr < timecardInfoManager.size(); paramCtr++) {
			TimecardInfo timecardInfo = (TimecardInfo) timecardInfoManager.get(paramCtr);
			projectValue = new ArrayList<Integer>();
			for (String projectId : timecardInfo.getProjectGroup().split(",")) {
				logger.debug("projectID>>>>>>>>>>>>" + projectId);
				projectValue.add(Integer.parseInt(projectId));
			}
			timecardInfo.setProjectValue(projectValue);
			List<TimecardProjectWorkDetails> timecardProjectWorkDetails = (List<TimecardProjectWorkDetails>) getTimecardProjectViewForManager(
					timecardInfo, session);
			
			timecardProjectWorkDetails = getTimecardViewPerProjectPerDayForManager(timecardProjectWorkDetails, session);
			timecardInfo.setEmployeeProjectTimecard(timecardProjectWorkDetails);
			timecardInfoManager.set(paramCtr, timecardInfo);
		}
		return timecardInfoManager;
	}

	public List<TimecardProjectWorkDetails> getTimecardViewPerProjectPerDayForManager(
			List<TimecardProjectWorkDetails> timecardProjectWorkDetailsParam, SqlSession session) throws DbException {
		List<Integer> projectValue = new ArrayList<Integer>();

		for (int projectCtr = 0; projectCtr < timecardProjectWorkDetailsParam.size(); projectCtr++) {
			TimecardProjectWorkDetails timecardProjectWorkDetailsObj = (TimecardProjectWorkDetails) timecardProjectWorkDetailsParam
					.get(projectCtr);

			List<TimecardDayDetails> timecardDayDetailsSearch = (List<TimecardDayDetails>) getTimecardViewQueryManagerDayInfo(
					timecardProjectWorkDetailsObj, session);
			timecardProjectWorkDetailsObj.setProjectTimecard(timecardDayDetailsSearch);
			timecardProjectWorkDetailsParam.set(projectCtr, timecardProjectWorkDetailsObj);
		}
		return timecardProjectWorkDetailsParam;
	}

	public List<TimecardDayDetails> getTimecardViewQueryManagerDayInfo(
			TimecardProjectWorkDetails timecardProjectWorkDetails, SqlSession session) throws DbException {

		List<TimecardDayDetails> timecardDetailSearchData = session
				.selectList("TimecardMapper.getEmployeeTimecardPerProjectPerDay", timecardProjectWorkDetails);

		return timecardDetailSearchData;
	}

	public List<TimecardProjectWorkDetails> getTimecardProjectViewForManager(TimecardInfo timecardInfoParam,
			SqlSession session) throws DbException {

		List<TimecardProjectWorkDetails> timecardProjectWorkDetails = session
				.selectList("TimecardMapper.getEmployeeTimecardPerProject", timecardInfoParam);

		return timecardProjectWorkDetails;
	}

	public List<TimecardInfo> getPreviousWeekStatus() throws AppException,DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			 List<TimecardInfo> result = session.selectList("TimecardMapper.getPreviousWeekStatus");
			 logger.debug("result000 "+result);
			session.commit();
			return result;
		} finally {
			session.close();
		}

	}

	/*
	 * Parent method for saving/editing of timecard detail by an employee
	 * 
	 */

	public String approveTimecardByManager(List<TimecardInfo> timecardInfoParam) throws DbException, AppException {
		SqlSession session = MyBatisManager.openSession();
		String saveTimecardReturn = "";
		int notApprovedDayNo = 1;

		try {
			for (TimecardInfo timecardInfoObj : timecardInfoParam) {
				if (validateTimecardStatus(timecardInfoObj, session) == false) {
					saveTimecardReturn = TimecardMessageConstant.Timecard_Is_Already_Approved.getLabel();
					return saveTimecardReturn;
				}

				List<TimecardProjectWorkDetails> timecardProjectWorkDetailsObj = (List<TimecardProjectWorkDetails>) timecardInfoObj
						.getEmployeeProjectTimecard();

				updateTimecardDayDetailsStatus(timecardProjectWorkDetailsObj, session);
				updateTimecardApproverStatus(timecardProjectWorkDetailsObj, session);
				List<TimecardDayInfo> allTimecardDayInfoObj = (List<TimecardDayInfo>) getTimecardWorkHour(
						timecardInfoObj, session);

				for (int dayApproveCtr = 0; dayApproveCtr < allTimecardDayInfoObj.size(); dayApproveCtr++) {
					TimecardDayInfo timecardDayInfo = (TimecardDayInfo) allTimecardDayInfoObj.get(dayApproveCtr);
					if (timecardDayInfo.getTimeRemainToApprove().equals("0")) {
						timecardDayInfo.setStatus(AppConstant.TIME_CARD_STATUS.Approved.toString());
						updateTimecardDayInfoStatus(timecardDayInfo, session);
					}
				}
				timecardInfoObj.setStatus(AppConstant.TIME_CARD_STATUS.Approved.toString());
				notApprovedDayNo = getNotApprovedDay(timecardInfoObj, session);
				if (notApprovedDayNo == 0)
					updateTimecardInfo(timecardInfoObj, session);
				
			}
			saveTimecardReturn=TimecardMessageConstant.Timecard_Approved_successfully.getLabel();

			session.commit();
			return saveTimecardReturn;
		} finally {
			session.close();
		}
	}

	public String rejectTimecardByManager(List<TimecardInfo> timecardInfoParam) throws DbException, AppException {
		SqlSession session = MyBatisManager.openSession();
		String saveTimecardReturn = "";
		int notApprovedDayNo = 1;

		try {
			for (TimecardInfo timecardInfoObj : timecardInfoParam) {
				if (validateTimecardRejectStatus(timecardInfoObj, session) == false) {
					saveTimecardReturn = TimecardMessageConstant.Timecard_Is_Already_Approved.getLabel();
					return saveTimecardReturn;
				}

				List<TimecardProjectWorkDetails> timecardProjectWorkDetailsObj = (List<TimecardProjectWorkDetails>) timecardInfoObj
						.getEmployeeProjectTimecard();
				updateTimecardDayDetailsStatus(timecardProjectWorkDetailsObj, session);
				updateTimecardApproverStatus(timecardProjectWorkDetailsObj, session);

				List<TimecardDayInfo> allTimecardDayInfoObj = (List<TimecardDayInfo>) getTimecardWorkHour(
						timecardInfoObj, session);

				for (int dayApproveCtr = 0; dayApproveCtr < allTimecardDayInfoObj.size(); dayApproveCtr++) {
					TimecardDayInfo timecardDayInfo = (TimecardDayInfo) allTimecardDayInfoObj.get(dayApproveCtr);
					if (timecardDayInfo.getTimeRemainToApprove().equals("0")) {
						timecardDayInfo.setStatus(AppConstant.TIME_CARD_STATUS.Approved.toString());
						updateTimecardDayInfoStatus(timecardDayInfo, session);
					}
				}
				timecardInfoObj.setStatus(AppConstant.TIME_CARD_STATUS.Approved.toString());
				notApprovedDayNo = getNotApprovedDay(timecardInfoObj, session);
				if (notApprovedDayNo == 0)
					updateTimecardInfo(timecardInfoObj, session);

			
			}
			saveTimecardReturn=TimecardMessageConstant.Timecard_Rejected_successfully.getLabel();
			session.commit();
			return saveTimecardReturn;
		} finally {
			session.close();
		}
	}

	public boolean validateTimecardStatus(TimecardInfo timecardInfoParam,SqlSession session)
	{
		int tcddId = 0;
		tcddId = session.selectOne("TimecardMapper.isTimecardProjectApproved", timecardInfoParam);
		if (tcddId == -1)
			return false;
		else
			return true;
	}
	
	public boolean validateTimecardRejectStatus(TimecardInfo timecardInfoParam,SqlSession session)
	{
		int tcddId = 0;
		tcddId = session.selectOne("TimecardMapper.isTimecardProjectReject", timecardInfoParam);
		if (tcddId == -1)
			return false;
		else
			return true;
	}

	public List<TimecardDayInfo> getTimecardWorkHour(TimecardInfo timecardInfoParam,SqlSession session)
	{
		return (List) session.selectList("TimecardMapper.getDayApprovedRecord", timecardInfoParam);
	}

	public int getNotApprovedDay(TimecardInfo timecardInfoParam,SqlSession session)
	{
		return (int)session.selectOne("TimecardMapper.checkNotApprovedDay", timecardInfoParam);
	}

	public int updateTimecardDayDetailsStatus(List<TimecardProjectWorkDetails> timecardProjectWorkDetailsParam,SqlSession session)
	{
		int noOfRecordUpd = 0;
		for(TimecardProjectWorkDetails timecardProjectWorkDetObj:timecardProjectWorkDetailsParam)
		{
			noOfRecordUpd = session.insert("TimecardMapper.updateApproveTimecardProject", timecardProjectWorkDetObj);
		}
		
		return noOfRecordUpd;
	}

	public int updateTimecardDayInfoStatus(TimecardDayInfo timecardDayInfoParam,SqlSession session)
	{
		int noOfRecordUpd = 0;
		noOfRecordUpd = session.insert("TimecardMapper.updateTimecardDayInfo", timecardDayInfoParam);
		return noOfRecordUpd;
	}

	private void updateTimecardInfo(TimecardInfo timecardInfo, SqlSession session)
			throws DbException, AppException {
		session.insert("TimecardMapper.updateTimecardInfo", timecardInfo);
	}

	public int updateTimecardApproverStatus(List<TimecardProjectWorkDetails> timecardProjectWorkDetailsParam,SqlSession session)
	{
		int noOfRecordUpd = 0;
		for(TimecardProjectWorkDetails timecardProjectWorkDetObj:timecardProjectWorkDetailsParam)
		{
			noOfRecordUpd = session.insert("TimecardMapper.updateTimecardApproverProject", timecardProjectWorkDetObj);
		}
		return noOfRecordUpd;
	}

}
