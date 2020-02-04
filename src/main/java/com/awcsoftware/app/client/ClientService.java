package com.awcsoftware.app.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

public class ClientService {

	@Autowired
	ClientDao dao;
	
	@Autowired
	ClientValidator validator;
	
	public String addClient(ClientInfo info) throws AppException, DbException {
		
	if(validator.validateAddClient(info).size()==0) {
		boolean result = dao.addClient(info);
		if(result==true) {
		  return ClientMessageConstants.ClientAddedSuccessFully.getLabel().toString();	
		}
	   }
		return validator.validateAddClient(info).toString();
		
	}
	
	public List<ClientInfo> viewClients(ClientInfo info){
		
		return null;
		
	}
}
