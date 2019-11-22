package com.awcsoftware.app.timesheet;

import org.apache.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

@RestController
public class TimecardController {

	static Logger logger = Logger.getLogger(TimecardController.class);

	@RequestMapping(value = "/tc-add", method = RequestMethod.POST, headers = "Accept=application/json")
	//public ResponseEntity<String> add(@RequestBody BatchInfo info, UserAuthenticationDetail authDetails) {
	public ResponseEntity<String> add(@RequestBody TimecardInfo info) {
		logger.debug("this is working - timecard add");
		TimecardService service = new TimecardService();
		try {
			service.add(info);
			return new ResponseEntity<String>("Added", HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
