package com.awcsoftware.session.store;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

import com.awcsoftware.spring.security.auth.UserAuthenticationDetail;

public class TokenSession {
	
	long inactiveTime = 900000; //15 minutes
	
	private static TokenSession instance;
	private Map<String, TokenInfo> tokenMap = new HashMap<String, TokenInfo>();
	
	private TokenSession() {}
	
	public static TokenSession getTokenStore() {
		if (instance == null) {
			instance = new TokenSession();
		}
		return instance;
	}
	
	public void addAuthenticaionDetail(UserAuthenticationDetail authDetail) {
		 TokenInfo info = new TokenInfo();
		 info.setAuthenticationDetail(authDetail);
		 info.setCreationime(LocalDateTime.now());
		 info.setLastAccessedTime(LocalDateTime.now());
		 info.setLastAccessedMillis(System.currentTimeMillis());
		 
		 tokenMap.put(authDetail.getToken(), info);
	}
	
	public UserAuthenticationDetail getAuthenticationDetail(String token) {
		TokenInfo info = tokenMap.get(token);
		return info.getAuthenticationDetail();
	}
	
	public String getToken(String token) {
		TokenInfo info = tokenMap.get(token);
		// reset the last access time 
		info.setLastAccessedMillis(System.currentTimeMillis());
		info.setLastAccessedTime(LocalDateTime.now());
		return info.getAuthenticationDetail().getToken();
	}
	
	public boolean isExists(String token) {
		return tokenMap.containsKey(token);
	}
	
	public void invalidateToken(String token) {
		tokenMap.remove(token);
	}
	
	public boolean isActive(String token) {
		if (isExists(token)) {
			TokenInfo info = tokenMap.get(token);
			long lastAccessed = info.getLastAccessedMillis();
			long timeElapsed = System.currentTimeMillis() - lastAccessed;
			
			if (timeElapsed > inactiveTime) { // 15 minutes
				invalidateToken(token);
				return false;
			} else 
				return true;
		}
		return false;
	}
	
}
