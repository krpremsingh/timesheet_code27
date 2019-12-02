package com.awcsoftware.spring.security.auth;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.awcsoftware.spring.security.auth.user.Role;

public class UserAuthenticationDetail extends UsernamePasswordAuthenticationToken {
	static Logger log = Logger.getLogger(UserAuthenticationDetail.class.getName());
	
	private List<Role> role;
    private int firstLoginStatus;
	private String token;
	private int empId;
	private String empCode;
	private int designationId;

	private static final long serialVersionUID = 1L;

	public UserAuthenticationDetail(Object principal, Object credentials) {
		super(principal, credentials);
		
	}

	public UserAuthenticationDetail(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public int getFirstLoginStatus() {
		return firstLoginStatus;
	}

	public void setFirstLoginStatus(int firstLoginStatus) {
		this.firstLoginStatus = firstLoginStatus;
	}
	

}
