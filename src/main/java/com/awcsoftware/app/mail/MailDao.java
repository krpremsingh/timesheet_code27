package com.awcsoftware.app.mail;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.awcsoftware.mybatis.MyBatisManager;
import com.awcsoftware.spring.security.auth.user.User;

@Component
public class MailDao {
	static Logger logger = Logger.getLogger(MailDao.class.getName());

	public List<User> getEmailList() {
		SqlSession session = MyBatisManager.openSession();
		try {
			List<User> result = session.selectList("User.getemaillist");
			if (result != null) {
				session.commit();
				return result;
			}
			return null;
		} finally {
			session.close();
		}

	}

	public boolean updatemailFlag(MailPojo mailpojo) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.update("User.updateMailFlag", mailpojo);
			
			if (result == 0) {
				return false;
			}
			session.commit();
			logger.debug("updated rows " + result);
			return true;
		} finally {
			session.close();
		}

	}
}
