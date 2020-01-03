package com.awcsoftware.spring.security.auth;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.awcsoftware.app.Util;
import com.awcsoftware.app.employee.EmployeeDao;
import com.awcsoftware.app.employee.EmployeeLoginTransaction;
import com.awcsoftware.spring.security.auth.user.Role;
import com.awcsoftware.spring.security.auth.user.UserDao;

public class UserAuthenticationDetail extends UsernamePasswordAuthenticationToken implements UserDetails {


	@Autowired
	EmployeeLoginTransaction logintransaction;

	static Logger log = Logger.getLogger(UserAuthenticationDetail.class.getName());

	private List<Role> role;
	private int firstLoginStatus;
	private String token;
	private int empId;
	private String empCode;
	private int designationId;
	private String firstName;

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

	public String getName() {
		return firstName;
	}

	public void setName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getUsername() {
		return (String) super.getPrincipal();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
// to check user password whether expired or not
	@Override
	public boolean isCredentialsNonExpired() {
		UserDao userdao = new UserDao();
		EmployeeDao employeedao = new EmployeeDao();
		logintransaction = new EmployeeLoginTransaction(userdao.getUser(super.getPrincipal().toString()));
		if (!Util.isEmptyOrNull(employeedao.getLoginTransaction(logintransaction))) {
			if (employeedao.getLoginTransaction(logintransaction).getPasswordExpiryDate()
					.isBefore(LocalDateTime.now())) {
				return false;
			}
		}
		return true;

	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return getRole().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
				.collect(Collectors.toList());
	}

}
