package com.awcsoftware.app.common;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.awcsoftware.mybatis.MyBatisManager;

@Repository
public class BaseDao {
	static Logger log = Logger.getLogger(BaseDao.class.getName());
	public List<BasePojo> getDesignation(int designationId){
		
		SqlSession session = MyBatisManager.openSession();
		try {
			List<BasePojo> designation = session.selectList("ParentBasePojo.getDesignation",designationId);
			return designation;
		} finally {
			session.close();
		}		
	}
	
	public List<BasePojo> getActivities(){
		
		SqlSession session = MyBatisManager.openSession();
		try {
			List<BasePojo> activities = session.selectList("ParentBasePojo.getActivities");
			return activities;
		} finally {
			session.close();
		}		
	}
	public List<BasePojo> getProjects(int empId){
		SqlSession session = MyBatisManager.openSession();
		try {
			List<BasePojo> projects = session.selectList("ParentBasePojo.getProjects", empId);
			return projects;
		} finally {
			session.close();
		}	
		
	}
	public List<BasePojo> getProjectLocations(int projectId){
		SqlSession session = MyBatisManager.openSession();
		try {
			List<BasePojo> projects = session.selectList("ParentBasePojo.getProjectLocations", projectId);
			return projects;
		} finally {
			session.close();
		}	
	}
	
	public List<BasePojo> getProjectsListBasedOnClient(int clientId){
		SqlSession session = MyBatisManager.openSession();
		try {
			List<BasePojo> projects = session.selectList("ParentBasePojo.getProjectsListBasedOnClient", clientId);
			log.debug(projects);
			return projects;
		} finally {
			session.close();
		}	
	}
}
