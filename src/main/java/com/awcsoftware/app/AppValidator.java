package com.awcsoftware.app;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
