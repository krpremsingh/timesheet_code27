package com.awcsoftware.app.timesheet;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

public class TimecardService {


	public String add(TimecardInfo info) throws DbException, AppException {
		// validate here
		// if success
			// add
		TimecardDao dao = new TimecardDao();
		
		
		
		return dao.add(info);
	}
	
}
