package com.awcsoftware.app.client;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;

public class ClientDao {
	static Logger logger = Logger.getLogger(ClientDao.class.getName());
	
	public boolean validateClient(ClientInfo info) throws AppException, DbException {
		if(checkClient(info)==true) {
			return true;
		}
		return false;
		
		
	}
	
	
	public boolean checkClient(ClientInfo info) throws AppException,DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.selectOne("Clients.checkClient", info);
			if(result!=0) {
				session.commit();
				return true;	
			}
			return false;		
		} finally

		{
			session.close();
		}		
	}

	public boolean addClient(ClientInfo info) throws AppException, DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.insert("Clients.addClient", info);
			if(result!=0) {
				session.commit();
				return true;	
			}
			return false;		
		} finally

		{
			session.close();
		}
	}
	
	public List<ClientInfo> viewClients(ClientInfo info){
		SqlSession session = MyBatisManager.openSession();
		try {
			List<ClientInfo> result = session.selectList("Clients.viewClients", info);
			if(!Util.isEmptyOrNull(result)) {
				session.commit();
				return result;	
			}
			return null;		
		} finally
		{
			session.close();
		}		
	}
	
	public boolean updateClient(ClientInfo info)throws AppException,DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			 int result = session.update("Clients.updateClient", info);
			if(result!=0) {
				session.commit();
				return true;	
			}
			return false;		
		} finally
		{
			session.close();
		}		
	}
	public List<ClientInfo> getClientList()throws AppException,DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			 List<ClientInfo> result = session.selectList("Clients.getClientList");
			if(result!=null) {
				session.commit();
				return result;	
			}
			return null;		
		} finally
		{
			session.close();
		}		
	}
}
