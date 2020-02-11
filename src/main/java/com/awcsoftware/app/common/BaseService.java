package com.awcsoftware.app.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseService {
	static Logger logger = Logger.getLogger(BaseService.class);

	@Autowired
	BaseDao dao;

	public List<BasePojo> getActivities() {
		List<BasePojo> setofactivities = dao.getActivities();
		return setofactivities;
	}

	public List<BasePojo> getProjects(BasePojo basepojo) {
		
		List<BasePojo> setofprojects = dao.getProjects(basepojo.getId());
		//Set<String> set = basevalidator.validateProjects(basepojo.getId());
		if(setofprojects.size()==0) {	
			List<BasePojo> listoferrormsg= new ArrayList<BasePojo>();
			basepojo.setName(BaseMessageConstants.ProjectNotAssigned.getLabel().toString());
			basepojo.setErrorMsg(BaseMessageConstants.ValidateProjects.getLabel().toString());
			listoferrormsg.add(basepojo);
			logger.debug(listoferrormsg);
		  return listoferrormsg;	
		}
		return setofprojects;
	}

	public List<BasePojo> getProjectLocations(BasePojo basepojo) {
		List<BasePojo> setoflocations = dao.getProjectLocations(basepojo.getId());
		//Set<String> set=basevalidator.validateProjectLocations(basepojo.getId());
		if(setoflocations.size()==0) {
			 List<BasePojo> listoferrormsg= new ArrayList<BasePojo>();
			 basepojo.setName(BaseMessageConstants.ValidateProjectLocations.getLabel().toString());
			 basepojo.setErrorMsg(BaseMessageConstants.ValidateProjectLocations.getLabel().toString());
			 listoferrormsg.add(basepojo);
			 return listoferrormsg;	
			}
		return setoflocations;
	}
	
	public List<BasePojo> getDesignation(BasePojo basepojo){
		List<BasePojo> designation = dao.getDesignation(basepojo.getId());
		if(designation.size()!=0) {
			return designation;
		}
		return null;
		
	}
	public List<BasePojo> getProjectsListPerClient(BasePojo basepojo){
		List<BasePojo> projects = dao.getProjectsListBasedOnClient(basepojo.getId());
		if(projects.size()!=0) {
			return projects;
		}
		return null;
		
	}

}
