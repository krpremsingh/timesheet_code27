package com.awcsoftware.spring.security.auth.user;

import org.apache.ibatis.session.SqlSession;

import org.apache.log4j.Logger;

import com.awcsoftware.mybatis.MyBatisManager;

public class UserDao {
	static Logger log = Logger.getLogger(UserDao.class.getName());
	
	public User getUser(String username) {
		SqlSession session = MyBatisManager.openSession();
		try {
			User user = session.selectOne("User.getUser", username);
			return user;
		} finally {
			session.close();
		}
	}
	
	public boolean isExists(String username) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.selectOne("User.isExists", username);
			if (result == 1) return true;
			return false;
		} finally {
			session.close();
		}
	}
	
}
