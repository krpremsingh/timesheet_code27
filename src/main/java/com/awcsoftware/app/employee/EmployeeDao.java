package com.awcsoftware.app.employee;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;
import com.awcsoftware.spring.security.auth.user.User;
import com.awcsoftware.spring.security.auth.user.UserDao;

/**
 * 
 * @author Arun methods to generate and verify confirmation token
 *         1.saveToken(assigned a token to user with expiry date)
 *         2.updateToken(update token if user generate a link more than one time
 *         without changing the password) 3.checkToken(to check token in the
 *         database) 4.findByToken(to verify the user entered token with
 *         database token) 5.deleteToken(delete the token from database after
 *         the password changed by user) 6.verifyEmailId(verify the email id
 *         entered by user for link generation to change password) methods to
 *         reset and change password 1.resetPassword 2.updatePassword methods to
 *         maintain the state of user 1.getLoginTransaction 2.saveLastLogin
 *         3.saveLoginTransaction
 */
@Component("empDao")
public class EmployeeDao {

	@Autowired
	UserDao userdao;
	
	@Autowired
	EmployeeValidator empValidator;

	@Autowired
	EmployeeLoginTransaction logintransaction;

	static Logger logger = Logger.getLogger(EmployeeDao.class.getName());

	/*
	 * reset old password or force user to change password and update the login
	 * transaction table
	 * 
	 */

	public boolean insertEmployee(User user) throws AppException, DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
				int result = session.insert("User.insertEmployee", user);
				if (result != 0) {
					addEmployeeAddress(user,session);
					addEmployeePhone(user,session);
					addEmployeeProject(user,session);
					session.commit();
					return true;
				}		
			return false;
		} finally

		{
			session.close();
		}

	}
	
	public String validateEmployee(User user) throws AppException, DbException{
		if(empValidator.validateEmployeeBasicDetails(user).size()==0) {
			if(checkEmployee(user)==true) {
				insertEmployee(user);
				return EmployeeMessageConstants.EmployeeAdded.getLabel().toString();
		}
			else if(checkEmployee(user)==false) {
				return EmployeeMessageConstants.EmployeeAlreadyExist.getLabel().toString();
			}
			
		}
			return empValidator.validateEmployeeBasicDetails(user).toString(); 
		
		
	}

	public boolean checkEmployee(User user) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.selectOne("User.checkEmployee", user);
			logger.debug("result " + result);
			if (result == 0) {
				session.commit();
				return true;
			}
			return false;
		} finally {
			session.close();
		}

	}

	public boolean addEmployeeAddress(User user,SqlSession session) {
		int result=0;
			for (EmployeeAddressInfo empAddressInfo : user.getAddressInfo()) {
				empAddressInfo.setEmpId(user.getEmpId());
				result = session.insert("User.addEmployeeAddress", empAddressInfo);
			}
			if (result != 0) {
				return true;
			}
			return false;
	
	}

	public boolean addEmployeePhone(User user,SqlSession session) {
		//SqlSession session = MyBatisManager.openSession();
		int result=0;
		 for(EmployeePhoneInfo empPhoneInfo:user.getPhoneInfo()) {
			empPhoneInfo.setEmpId(user.getEmpId());
			result = session.insert("User.addEmployeePhone", empPhoneInfo);
		  }		
			if (result != 0) {
				return true;
			}
			return false;
	}
	
	
	public boolean addEmployeeProject(User user,SqlSession session) {
		int result=0;
		 for(EmployeeProjectInfo empProjectInfo:user.getProjectInfo()) {
			empProjectInfo.setEmpId(user.getEmpId());
			result = session.insert("User.addEmployeeProjects", empProjectInfo);
		  }		
			if (result != 0) {
				return true;
			}
			return false;
	}
	
	public boolean updateEmployee(User user){
		SqlSession session = MyBatisManager.openSession();
		try {
				int result = session.update("User.updateEmployee", user);
				if (result != 0) {
					session.commit();
					return true;
				}		
			return false;
		} finally

		{
			session.close();
		}
		
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

	/*
	 * public String getCurrentPassword(String email) { SqlSession session =
	 * MyBatisManager.openSession(); try { String result =
	 * session.selectOne("User.getCurrentPassword"); if (result != null) {
	 * session.commit(); return result; } return null; } finally { session.close();
	 * }
	 * 
	 * }
	 */
	public int deleteToken(User user) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.delete("User.deleteToken", user);
			if (result != 0) {
				session.commit();
				return result;
			}
			return 0;
		} finally {
			session.close();
		}

	}

	public String verifyEmailId(String email) {
		User user = null;
		user = userdao.getUser(email);
		return user.getEmail();

	}

	public boolean resetPassword(User user) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.update("User.resetPassword", user);
			if (result != 0) {
				session.commit();
				logintransaction = new EmployeeLoginTransaction(user);
				logintransaction.setActivityStatus(EmployeeMessageConstants.PasswordChanged.getLabel().toString());
				logintransaction.setStatusReason(" ");
				saveLoginTransaction(logintransaction);
				return true;
			}
			return false;
		} finally {
			session.close();
		}

	}

	// generate new password for user if forgot and update the login transaction
	// table

	public boolean updatePassword(User user) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.update("User.updatePassword", user);
			if (result != 0) {
				session.commit();
				deleteToken(user);
				logger.debug(user.getEmail() + " " + result + " ");
				logintransaction = new EmployeeLoginTransaction(user);
				logintransaction.setActivityStatus(EmployeeMessageConstants.PasswordChanged.getLabel().toString());
				logintransaction.setStatusReason(" ");
				saveLoginTransaction(logintransaction);
				return true;
			}
			return false;
		} finally {
			session.close();
		}
	}

	public EmployeeLoginTransaction getLoginTransaction(EmployeeLoginTransaction logintransaction) {
		SqlSession session = MyBatisManager.openSession();
		try {
			EmployeeLoginTransaction result = session.selectOne("User.checkLoginTransaction", logintransaction);
			session.commit();
			return result;

		} finally {
			session.close();
		}

	}

	public boolean saveLoginTransaction(EmployeeLoginTransaction logintransaction) {
		logger.debug(logintransaction);
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.insert("User.saveLoginTransaction", logintransaction);
			if (result != 0) {
				session.commit();
				return true;
			}
			return false;
		} finally {
			session.close();
		}

	}

//update loginTimestamp in login transaction
	public boolean saveLastLogin(EmployeeLoginTransaction logintransaction) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.insert("User.saveLastLogin", logintransaction);
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
