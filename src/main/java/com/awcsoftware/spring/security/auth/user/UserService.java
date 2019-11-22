package com.awcsoftware.spring.security.auth.user;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UserService {
	static Logger log = Logger.getLogger(UserService.class.getName());
	
	public User getUser(String username) {
		UserDao dao = new UserDao();
		User user = dao.getUser(username);
		return user;
	}
	
	public boolean isExists(String username) {
		UserDao dao = new UserDao();
		boolean e = dao.isExists(username);
		return e;
	}
}