package com.awcsoftware.app.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;


public class ReportsService {

	@Autowired
	ReportsDao dao;
	
	public List<ReportsInfo> fetchTimecardsCount(ReportsInfo info) throws AppException, DbException {
		List<ReportsInfo> result = dao.fetchTimecardcount(info);
		if(result.size()!=0) {
			return result;
		}
		return result;
		
	}
}
