package com.awcsoftware.app.password;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.awcsoftware.mybatis.MyBatisManager;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserDao;

public class UserPasswordDao {
	final static Logger logger = Logger.getLogger(UserPasswordController.class);

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

	public ConfirmationToken findByToken(String token) {
		ConfirmationToken confirmationToken= new ConfirmationToken();
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.update("User.findByToken", token);
			if (result != 0) {
				session.commit();
				return confirmationToken;
			}
			return null;
		} finally {
			session.close();
		}
	}

	public String verifyEmailId(String email) {
		UserDao userdao = new UserDao();
		User user = userdao.getUser(email);
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
	/*
	 * ConfirmationToken findByToken(String token); boolean
	 * updateToken(ConfirmationToken token); ConfirmationToken checkToken(String
	 * token);
	 */
}
