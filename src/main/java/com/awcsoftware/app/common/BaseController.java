package com.awcsoftware.app.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

@RestController
public class BaseController {
	
    @Autowired
	BaseService baseservice;

	@GetMapping("/getActivities")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<List<BasePojo>> getActivitiesList() throws AppException, DbException {
		List<BasePojo> setofactivities = baseservice.getActivities();
		return new ResponseEntity<List<BasePojo>>(setofactivities,HttpStatus.OK);
	}

	@PostMapping("/getProjects")
	public ResponseEntity<List<BasePojo>> getProjectsList(@RequestBody BasePojo basepojo) {
		List<BasePojo> setOfProjects = baseservice.getProjects(basepojo.getId());
			return new ResponseEntity<List<BasePojo>>(setOfProjects,HttpStatus.OK);	
		}


	@PostMapping("/getProjectLocations")
	public ResponseEntity<List<BasePojo>> getprojectLocations(@RequestBody BasePojo basepojo) {
		List<BasePojo> setOfProjectLocations = baseservice.getProjectLocations(basepojo.getId());
		return new ResponseEntity<List<BasePojo>>(setOfProjectLocations,HttpStatus.OK);

	}
}
