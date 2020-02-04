package com.awcsoftware.app.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;

public class ReportsService {
	static Logger logger = Logger.getLogger(ReportsService.class);
	
	@Autowired
	ReportsDao dao;

	@Autowired
	ReportsValidator reportsValidator;

	
	

	public List<ReportsInfo> fetchTimecardsCount(ReportsInfo info) throws AppException, DbException {
		List<ReportsInfo> errors = new ArrayList<>();
		if (reportsValidator.validateReportsDatesInput(info).size()!=0) {
			logger.debug(info);
			info.setStatus(reportsValidator.validateReportsDatesInput(info).toString());
			logger.debug(info.getStatus());
			errors.add(info);
			return errors;
		}
		List<ReportsInfo> result = dao.fetchTimecardcount(info);
		if(Util.isEmptyOrNull(result)) {
			info.setStatus(ReportsMessageConstants.RecordNotFoundBetweenDates.getLabel().toString());
			errors.add(info);
			return errors;
		}
		return result;
	}

	public List<ReportsInfo> getEmployeesReportBasedOnStatus(ReportsInfo info) throws AppException, DbException {
		List<ReportsInfo> errors = new ArrayList<>();
		List<ReportsInfo> result=null;
		if (reportsValidator.validateReportsStatusInput(info).size()!= 0) {
			info.setStatus(reportsValidator.validateReportsStatusInput(info).toString());
			errors.add(info);
			return errors;
		}			
		result=dao.getEmployeesReportBasedOnStatus(info);
		if(Util.isEmptyOrNull(result)) {
			info.setStatus(ReportsMessageConstants.RecordNotFoundForStatus.getLabel().toString());
			errors.add(info);
			return errors;
		}
		return result;
	}
	
	public List<ReportsInfo> getTotalEmployeeAndProjectsAndWorkingHours(ReportsInfo info) throws AppException, DbException {
		List<ReportsInfo> errors = new ArrayList<>();
		if (reportsValidator.validateReportsDatesInput(info).size()!=0) {
			logger.debug(info);
			info.setStatus(reportsValidator.validateReportsDatesInput(info).toString());
			logger.debug(info.getStatus());
			errors.add(info);
			return errors;
		}
		List<ReportsInfo> result = dao.getTotalEmployeeAndProjectsAndWorkingHours(info);
		if(Util.isEmptyOrNull(result)) {
			info.setStatus(ReportsMessageConstants.RecordNotFoundBetweenDates.getLabel().toString());
			errors.add(info);
			return errors;
		}
		return result;
	}
}
