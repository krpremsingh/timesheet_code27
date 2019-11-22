package com.awcsoftware.app.common;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BaseService{

	
	public List<BasePojo> getActivities(int designationId) {
		BaseDao dao=new BaseDao();
		List<BasePojo> setofactivities = dao.getActivities(designationId);
		return setofactivities;
	}

	
	public List<BasePojo> getProjects(int empId) {
		BaseDao dao=new BaseDao();
		List<BasePojo> setofprojects = dao.getProjects(empId);
		return setofprojects;
	}

	
	public List<BasePojo> getProjectLocations(int projectId) {
		BaseDao dao=new BaseDao();
		List<BasePojo> setoflocations = dao.getProjectLocations(projectId);
		return setoflocations;
	}

}
