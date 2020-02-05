package com.awcsoftware.app.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

@RestController
public class ReportsController {
	
	@Autowired
	ReportsService reportservice;
	
	@RequestMapping(value="/employeeReport",method=RequestMethod.POST)
	public ResponseEntity<List<ReportsInfo>> employeeReport(@RequestBody ReportsInfo info){
		try {
			return new ResponseEntity<List<ReportsInfo>>(reportservice.employeeReport(info), HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<List<ReportsInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<List<ReportsInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

}	
}
