package com.awcsoftware.app.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

@RestController
public class ProjectsController {
	
	@Autowired
	ProjectsService service;
	
	@RequestMapping(value = "/addProject", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> addproject(@RequestBody ProjectsInfo info) {
		try {
			return new ResponseEntity<String>(service.addProject(info), HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@RequestMapping(value="/updateProject",method=RequestMethod.PUT,headers="Accept=application/json")
	public ResponseEntity<String> updateProject(@RequestBody ProjectsInfo info){
		try {
			return new ResponseEntity<String>(service.updateProject(info),HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@RequestMapping(value="/viewProject/{projectId}",method=RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<List<ProjectsInfo>> viewProject(@PathVariable int projectId){
		try {
			return new ResponseEntity<List<ProjectsInfo>>(service.viewProject(projectId),HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<List<ProjectsInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<List<ProjectsInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@RequestMapping(value="/viewProjects",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseEntity<List<ProjectsInfo>> viewProjects(@RequestBody ProjectsInfo info){
		try {
			return new ResponseEntity<List<ProjectsInfo>>(service.viewProjects(info),HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<List<ProjectsInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<List<ProjectsInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
