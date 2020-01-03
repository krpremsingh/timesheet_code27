package com.awcsoftware.spring.security.auth.user;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.awcsoftware.mybatis.MyBatisManager;

@Component
public class UserDao {
	static Logger logger = Logger.getLogger(UserDao.class.getName());

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
			if (result == 1)
				return true;
			return false;
		} finally {
			session.close();
		}
	}

	public List<Role> getRoles(int empId) {
		SqlSession session = MyBatisManager.openSession();
		try {
			List<Role> result = session.selectList("User.getRoles", empId);
			if (result != null)
				return result;
			return null;
		} finally {
			session.close();
		}

	}

}
