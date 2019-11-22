package com.awcsoftware.app.timesheet;

import org.apache.ibatis.session.SqlSession;

import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;

public class TimecardDao {

	public String add(TimecardInfo info) throws DbException {
		SqlSession session = MyBatisManager.openSession();
		
		try {
			session.insert("TimecardInfo.addTimecardInfo", info);
			session.commit();
		} finally {
			session.close();
		}
		
		return "added to database";
	}
}
