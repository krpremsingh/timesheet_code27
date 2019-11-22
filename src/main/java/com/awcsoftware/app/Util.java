package com.awcsoftware.app;

public class Util {
	
	public static boolean isEmptyOrNull(Object object) {
		if(object == null) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmptyOrNull( String string ) {
		if(string == null || string.trim().length() == 0) {
			return true;
		}
		return false;
	}

}
