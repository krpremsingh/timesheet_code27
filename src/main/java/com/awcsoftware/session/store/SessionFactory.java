/**
 * 
 */
package com.awcsoftware.session.store;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Pratik
 *
 */
public class SessionFactory {
	@Autowired
	TokenSession ts;
	SessionBuilder sb;

	public SessionBuilder getSession(SessionType st) {
		switch (st) {
		case InMemory:
			sb = ts;
			break;
		case Redis:
			sb=null;
			break;
		default:
			//TODO
			break;
		}
		return sb;
	}
}
