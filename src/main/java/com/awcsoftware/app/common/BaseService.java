package com.awcsoftware.app.common;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseService {

	@Autowired
	BaseDao dao;

	public List<BasePojo> getActivities() {
		List<BasePojo> setofactivities = dao.getActivities();
		return setofactivities;
	}

	public List<BasePojo> getProjects(int empId) {
		List<BasePojo> setofprojects = dao.getProjects(empId);
		return setofprojects;
	}

	public List<BasePojo> getProjectLocations(int projectId) {
		List<BasePojo> setoflocations = dao.getProjectLocations(projectId);
		return setoflocations;
	}

}
