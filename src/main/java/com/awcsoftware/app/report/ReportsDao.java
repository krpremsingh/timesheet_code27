package com.awcsoftware.app.report;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;

@Component
public class ReportsDao {
	static Logger logger = Logger.getLogger(ReportsDao.class.getName());
	public List<ReportsInfo> fetchTimecardcount(ReportsInfo info)throws AppException,DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			List<ReportsInfo> result = session.selectList("ReportMapper.timecardsCount",info);
			logger.debug("Result " + result);
			if (result.size()!=0) {
				session.commit();
				return result;
			}
			return null;
		} finally

		{
			session.close();
		}
}
}
