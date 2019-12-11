package com.awcsoftware.app;

import java.util.LinkedHashSet;
import java.util.Set;

public class AppValidator 
{
	public static Set<String> errorMsg;
	public static String draftFlag;
	static {
		errorMsg = new LinkedHashSet<String>();
		draftFlag = "draft";
	}


}
