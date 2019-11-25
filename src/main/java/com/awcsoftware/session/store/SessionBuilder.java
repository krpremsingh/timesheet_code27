package com.awcsoftware.session.store;

import com.awcsoftware.spring.security.auth.UserAuthenticationDetail;

public interface SessionBuilder {
	//public SessionBuilder getTokenSession();
	public void addAuthenticaionDetail(UserAuthenticationDetail authDetail);
	public UserAuthenticationDetail getAuthenticationDetail(String token);
	public String getToken(String token);
	public boolean isExists(String token);
	public void invalidateToken(String token);
	public boolean isActive(String token);
}
