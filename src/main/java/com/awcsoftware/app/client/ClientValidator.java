package com.awcsoftware.app.client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.awcsoftware.app.Util;


public class ClientValidator {
	
	static Logger logger = Logger.getLogger(ClientValidator.class);
	static Set<String> errorMsg;
	static {
		errorMsg = new LinkedHashSet<String>();
	}

	public LocalDate dateFormatter(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;

	}
	
	public Set<String> validateAddClient(ClientInfo info){
		errorMsg.clear();
		if(Util.isEmptyOrNull(info.getClientName())) {
			errorMsg.add(ClientMessageConstants.BlankClientName.getLabel().toString());
			return errorMsg;
		}
		if(Util.isEmptyOrNull(info.getStatus())) {
			errorMsg.add(ClientMessageConstants.BlankStatus.getLabel().toString());
			return errorMsg;
		}
		if(Util.isEmptyOrNull(info.getStartDate())) {
			errorMsg.add(ClientMessageConstants.BlankStartDate.getLabel().toString());
			return errorMsg;
		}
		if(Util.isEmptyOrNull(info.getEndDate())) {
			errorMsg.add(ClientMessageConstants.BlankEndDate.getLabel().toString());
			return errorMsg;
		}
		if(Util.isEmptyOrNull(info)) {
			errorMsg.add(ClientMessageConstants.DataNotFound.getLabel().toString());
			return errorMsg;	
		}
		if(dateFormatter(info.getEndDate()).isBefore(dateFormatter(info.getStartDate()))) {
			errorMsg.add(ClientMessageConstants.EndDateCantBeforeStartDate.getLabel().toString());
			return errorMsg;
		}
		return errorMsg;
		
	}
	
	public Set<String> validateViewClient(ClientInfo info){
		errorMsg.clear();
		if(Util.isEmptyOrNull(info.getStartDate())) {
			errorMsg.add(ClientMessageConstants.BlankStartDate.getLabel().toString());
			return errorMsg;
		}
		if(Util.isEmptyOrNull(info.getEndDate())) {
			errorMsg.add(ClientMessageConstants.BlankEndDate.getLabel().toString());
			return errorMsg;
		}
		if(dateFormatter(info.getEndDate()).isBefore(dateFormatter(info.getStartDate()))) {
			errorMsg.add(ClientMessageConstants.EndDateCantBeforeStartDate.getLabel().toString());
			return errorMsg;
		}
		return errorMsg;
		
	}

}
