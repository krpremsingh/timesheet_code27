package com.awcsoftware.app.project;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.mybatis.MyBatisManager;

@Component
public class ProjectsDao {

	static Logger logger = Logger.getLogger(ProjectsDao.class.getName());

	public boolean addProject(ProjectsInfo info) throws AppException, DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			if (checkProject(info, session) == false) {
				int result = session.insert("Projects.addProject", info);
				if (result != 0) {
					addProjectLocation(info, session);
					addProjectTeam(info,session);
					addProjectApprover(info,session);
					session.commit();
					return true;
				}
			}
			return false;
		} finally

		{
			session.close();
		}
	}

	public boolean addProjectLocation(ProjectsInfo info, SqlSession session) throws AppException, DbException {
		int result = 0;
		for (ProjectLocations projectInfo : info.getProjectLocations()) {
			projectInfo.setProjectId(info.getProjectId());
			result = session.insert("Projects.addProjectLocation", projectInfo);
		}
		if (result != 0) {
			session.commit();
			return true;
		}
		return false;

	}
	
	public boolean addProjectTeam(ProjectsInfo info,SqlSession session) {
		int result = 0;
		for ( ProjectsTeamDetails projectteam : info.getProjectsTeamDetails()) {
			projectteam.setProjectId(info.getProjectId());
			result = session.insert("Projects.addProjectTeam", projectteam);
		}
		if (result != 0) {
			session.commit();
			return true;
		}
		return false;
		
	}
	
	public boolean addProjectApprover(ProjectsInfo info,SqlSession session) {
		int result = 0;
		for ( ProjectApproverDetails projectapprover : info.getProjectApproverDetails()) {
			projectapprover.setpId(info.getProjectId());
			result = session.insert("Projects.addProjectApproverDetails", projectapprover);
		}
		if (result != 0) {
			session.commit();
			return true;
		}
		return false;
		
	}
	
	public boolean deleteProjectLocations(ProjectsInfo info) {
		SqlSession session = MyBatisManager.openSession();
		try {
		       int result = session.delete("Projects.deleteProjectLocations",info);
		       if(result>0) {
		    	 return true;  
		         }
			return false;
		} finally

		{
			session.close();
		}
	}

	public boolean checkProject(ProjectsInfo info, SqlSession session) throws AppException, DbException {
		int result = session.selectOne("Projects.checkProject", info);
		if (result != 0) {
			return true;
		}
		return false;

	}

	public boolean updateProject(ProjectsInfo info) {
		SqlSession session = MyBatisManager.openSession();
		try {
			int result = session.update("Projects.updateProjectInfo", info);
			logger.debug("Result " + result);
			if (result != 0) {
				updateProjectLocationDetails(info,session);
				updateProjectTeamDetails(info,session);
				updateProjectApproverDetails(info,session);
				session.commit();
				return true;
			}
			return false;
		} finally

		{
			session.close();
		}

	}

	public boolean updateProjectLocationDetails(ProjectsInfo info,SqlSession session) {
		int result = 0;
			for (ProjectLocations projectLocations : info.getProjectLocations()) {
				projectLocations.setProjectId(info.getProjectId());
				result = session.update("Projects.updateProjectLocationDetails", projectLocations);
			}			
			if (result != 0) {
				return true;
			} else {
				return false;
			}
	}
	public boolean updateProjectTeamDetails(ProjectsInfo info,SqlSession session) {
		int result = 0;
			for ( ProjectsTeamDetails projectteam : info.getProjectsTeamDetails()) {
				projectteam.setProjectId(info.getProjectId());
				result = session.update("Projects.updateProjectTeamDetails", projectteam);
			}			
			if (result != 0) {
				return true;
			} else {
				return false;
			}	
	}
	
	public boolean updateProjectApproverDetails(ProjectsInfo info,SqlSession session) {
		int result = 0;
			for ( ProjectApproverDetails projectapprover : info.getProjectApproverDetails()) {
				projectapprover.setpId(info.getProjectId());
				result = session.update("Projects.updateProjectApproverDetails", projectapprover);
			}			
			if (result != 0) {
				return true;
			} else {
				return false;
			}	
	}
	
	public List<ProjectsInfo> viewProject(int projectId)throws AppException,DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			List<ProjectsInfo> result = session.selectList("Projects.viewProject",projectId);
			logger.debug("Result " + result);
			if (result.size()!=0) {
				session.commit();
				return result;
			}
			return null;
		} finally

		{
			session.close();
		}
}
	public List<ProjectsInfo> viewProjects(ProjectsInfo info)throws AppException,DbException {
		SqlSession session = MyBatisManager.openSession();
		try {
			List<ProjectsInfo> result = session.selectList("Projects.viewProjects",info);
			logger.debug("Result " + result);
			if (result.size()!=0) {
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