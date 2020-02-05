package com.awcsoftware.app.report;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.MyBatisManager;

@Component
public class ReportsDao {
	static Logger logger = Logger.getLogger(ReportsDao.class.getName());
	public List<ReportsInfo> getEmployeeReport(ReportsInfo info){
		SqlSession session = MyBatisManager.openSession();
		try {
			List<ReportsInfo> result = session.selectList("ReportMapper.getEmployeeReport",info);
			logger.debug("Result " + result);
			if (Util.isEmptyOrNull(result)) {
				session.commit();
				return null;
			}
			return result;
		} finally

		{
			session.close();
		}
		
	}
	
	
}
