/**
 * 
 */
package com.awcsoftware.session.store;

/**
 * @author Pratik
 *
 */
public class SessionFactoryTest {
	public static void main(String[] args){
		SessionFactory sf=new SessionFactory();
		SessionBuilder sb=sf.getSession(SessionType.InMemory);
		System.out.println(sb.isExists("dsdsa"));
	}
}
