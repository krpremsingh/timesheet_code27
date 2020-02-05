package com.awcsoftware.app.report;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

public class ReportsService {
	static Logger logger = Logger.getLogger(ReportsService.class);
	
	@Autowired
	ReportsDao dao;

	public List<ReportsInfo> employeeReport(ReportsInfo info) throws AppException, DbException {
	    List<ReportsInfo> result = dao.getEmployeeReport(info);
	    logger.debug("resultpppppp    "+result.toString());
		return result;
	}
}

