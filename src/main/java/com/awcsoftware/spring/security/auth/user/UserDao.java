package com.awcsoftware.spring.security.auth.user;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;

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

	public boolean resetPassword(User user) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.update("User.resetPassword", user);
			if (result != 0) {
				session.commit();
				return true;
			}
			return false;
		} finally {
			session.close();
		}

	}

	public ConfirmationToken findByToken(String token) throws AppException, DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			ConfirmationToken result = session.selectOne("User.findByToken", token);
			logger.debug("token result  " + result);

			if (result != null) {
				session.commit();
				return result;
			}

		} finally {
			session.close();
		}
		return null;

	}

	public String verifyEmailId(String email) {
		User user = null;
		UserDao userdao = new UserDao();
		user = userdao.getUser(email);
		if (Util.isEmptyOrNull(user)) {
			logger.debug("email id not found");
			return null;
		}
		return user.getEmail();

	}

	public ConfirmationToken checkToken(String email) {
		SqlSession session = MyBatisManager.openSession();
		try {
			ConfirmationToken result = session.selectOne("User.checkToken", email);
			logger.debug("token result  " + result);

			if (result != null) {
				session.commit();
				return result;
			}

		} finally {
			session.close();
		}
		return null;

	}

	public boolean saveToken(ConfirmationToken token) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.insert("User.saveToken", token);
			if (result != 0) {
				session.commit();
				return true;
			}
			return false;
		} finally {
			session.close();
		}
	}

	public boolean updateToken(ConfirmationToken token) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.update("User.updateToken", token);
			if (result != 0) {
				session.commit();
				return true;
			}
			return false;
		} finally {
			session.close();
		}

	}

}
