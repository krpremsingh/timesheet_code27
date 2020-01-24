package com.awcsoftware.app.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.AppException;
import com.awcsoftware.app.Util;
import com.awcsoftware.mybatis.DbException;

@Component
public class ProjectsService {

	@Autowired
	ProjectsDao dao;

	@Autowired
	ProjectsValidator validator;

	

	public String addProject(ProjectsInfo info) throws AppException, DbException {
		boolean result = false;
		if (validator.validateProject(info).size() == 0) {
			result = dao.addProject(info);
			if (result == true) {
				return ProjectsMessageConstants.ProjectAddedSuccessFully.toString();
			} else if (result == false) {
				return ProjectsMessageConstants.ProjectAlreadyExist.toString();
			}
		}
		return validator.validateProject(info).toString();
	}

	public String updateProject(ProjectsInfo info) throws AppException, DbException {
		boolean result = false;
		if (validator.validateProject(info).size() == 0) {
			if(dao.deleteProjectLocations(info)==true) {
				result = dao.updateProject(info);	
			}			
		}
		if (result == true) {
			return ProjectsMessageConstants.ProjectUpdated.toString();
		}
		return validator.validateProject(info).toString();
	}

	public List<ProjectsInfo> viewProject(int projectId) throws AppException, DbException {
		List<ProjectsInfo> project = dao.viewProject(projectId);
		if (project != null) {
			return project;
		} else {
			return null;
		}
	}

	public List<ProjectsInfo> viewProjects(ProjectsInfo info) throws AppException, DbException {
		List<ProjectsInfo> list= new ArrayList<ProjectsInfo>();
		if(validator.validateStartDateEndDate(info).size()==0) {
			List<ProjectsInfo> projects = dao.viewProjects(info);
			if (!Util.isEmptyOrNull(projects)) {
				return projects;
			}
			if(Util.isEmptyOrNull(projects)) {
				info.setStatus(ProjectsMessageConstants.ProjectNotFound.getLabel().toString());
				list.add(info);
				return list;
			}
		}
		list= new ArrayList<ProjectsInfo>();
		info.setStatus(validator.validateStartDateEndDate(info).toString());
		list.add(info);
		return list;

	}
}
