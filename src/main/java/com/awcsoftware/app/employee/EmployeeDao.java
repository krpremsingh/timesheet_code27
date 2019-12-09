package com.awcsoftware.app.employee;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserDao;

public class EmployeeDao {
	static Logger logger = Logger.getLogger(EmployeeDao.class.getName());

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

	public boolean updatePassword(User user) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.update("User.updatePassword", user);
			if (result != 0) {
				session.commit();
				deleteToken(user);
				logger.debug(user.getEmail()+" " +result+" ");
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
		if (Util.isEmptyOrNull(user) || !Util.validateEmail.test(email)) {
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

/*	public String getCurrentPassword(String email) {
		SqlSession session = MyBatisManager.openSession();
		try {
			String result = session.selectOne("User.getCurrentPassword");
			if (result != null) {
				session.commit();
				return result;
			}
			return null;
		} finally {
			session.close();
		}

	}*/
	public int deleteToken(User user) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.delete("User.deleteToken",user);
			if (result != 0) {
				session.commit();
				return result;
			}
			return 0;
		} finally {
			session.close();
		}
		
	}
}
