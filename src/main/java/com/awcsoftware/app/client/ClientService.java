package com.awcsoftware.app.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

public class ClientService {

	@Autowired
	ClientDao dao;
	
	@Autowired
	ClientInfo clientInfo;
	
	@Autowired
	ClientValidator validator;
	
	public String addClient(ClientInfo info) throws AppException, DbException {
	
		boolean checkclient = dao.validateClient(info);
		if(checkclient==false) {
			if(validator.validateAddClient(info).size()==0) {
				boolean result = dao.addClient(info);
				if(result==true) {
				  return ClientMessageConstants.ClientAddedSuccessFully.getLabel().toString();	
				}
			   }
		}
		else if(checkclient==true) {
			return ClientMessageConstants.ClientAlreadyExists.getLabel().toString();
		}
		return validator.validateAddClient(info).toString();
		
	}
	
	public List<ClientInfo> viewClients(ClientInfo info)throws AppException,DbException{
		   List<ClientInfo> listOfClient = dao.viewClients(info);
		   if(listOfClient.size()!=0) {
			  return listOfClient; 
		   }
		   clientInfo.setStatus(ClientMessageConstants.NoClientFound.getLabel().toString());
		   listOfClient.add(clientInfo);
		   return listOfClient;	
	}
	
	public String updateClient(ClientInfo info)throws AppException,DbException {
		boolean result=false;
		if(validator.validateAddClient(info).size()!=0) {
		   return validator.validateAddClient(info).toString();	
		}
		if(validator.validateViewClient(info).size()!=0) {
			return validator.validateViewClient(info).toString();
		}
		else {
		boolean checkclient = dao.validateClient(info);	
		if(checkclient==false) {
			result = dao.updateClient(info);	
		}
		else if(checkclient==true) {
			return ClientMessageConstants.ClientAlreadyExists.getLabel().toString();
		}
		
		}
		if(result==true) {
			return ClientMessageConstants.ClientUpdatedSuccessfully.getLabel().toString();
		}
		return null;		
	}
	
	public List<ClientInfo> getClientList()throws AppException,DbException {
		 List<ClientInfo> list = dao.getClientList();
		if(list!=null) {
			return list;
		}
		return null;
		
	}
}
