package com.awcsoftware.app.common;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

@RestController
public class BaseController {

	BaseService baseservice = new BaseService();

	@PostMapping("/getActivities")
	public ResponseEntity<List<BasePojo>> getActivitiesList(@Valid @RequestBody BasePojo basepojo) throws AppException, DbException {
		List<BasePojo> setofactivities = baseservice.getActivities(basepojo.getId());
		return new ResponseEntity<List<BasePojo>>(setofactivities,HttpStatus.OK);
	}

	@PostMapping("/getProjects")
	public ResponseEntity<List<BasePojo>> getProjectsList(@Valid @RequestBody BasePojo basepojo) {
		List<BasePojo> setOfProjects = baseservice.getProjects(basepojo.getId());
			return new ResponseEntity<List<BasePojo>>(setOfProjects,HttpStatus.OK);	
		}


	@PostMapping("/getProjectLocations")
	public ResponseEntity<List<BasePojo>> getprojectLocations(@Valid @RequestBody BasePojo basepojo) {
		List<BasePojo> setOfProjectLocations = baseservice.getProjectLocations(basepojo.getId());
		return new ResponseEntity<List<BasePojo>>(setOfProjectLocations,HttpStatus.OK);

	}
}
