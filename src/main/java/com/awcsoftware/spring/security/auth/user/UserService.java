package com.awcsoftware.spring.security.auth.user;

import java.util.List;

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
	
	public List<Role> getRoles(int empId){
		UserDao dao = new UserDao();
		List<Role> roles = dao.getRoles(empId);
		return roles;
		
	}

}