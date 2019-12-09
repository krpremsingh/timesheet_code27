package com.awcsoftware.app.timesheet;
/*
 * @author Prem Shankar Kumar
 * 
 */

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;
import com.awcsoftware.spring.security.auth.user.User;

@RestController
public class TimecardController {

	static Logger logger = Logger.getLogger(TimecardController.class);

	@RequestMapping(value = "/tc-add", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> saveTimecardInfo(@RequestBody TimecardInfo timecardInfo) {
		TimecardService service = new TimecardService();
		String strTimeCardServiceRet = "";
		try {
			strTimeCardServiceRet = service.saveTimecard(timecardInfo);
			return new ResponseEntity<String>(strTimeCardServiceRet, HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@RequestMapping(value = "/tc-edit", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> updateTimecardInfo(@RequestBody TimecardInfo timecardInfo) {
		TimecardService service = new TimecardService();
		String strTimeCardServiceRet = "";
		try {
			strTimeCardServiceRet = service.updateTimeCard(timecardInfo);
			return new ResponseEntity<String>(strTimeCardServiceRet, HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	@RequestMapping(value = "/tc-submit", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> submitTimecardInfo(@RequestBody TimecardInfo timecardInfo) {
		TimecardService service = new TimecardService();
		String strTimeCardServiceRet = "";
		try {
			strTimeCardServiceRet = service.submitTimeCard(timecardInfo);
			return new ResponseEntity<String>(strTimeCardServiceRet, HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/tc-search", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardView>> TimeCardView(@RequestBody TimecardView tcVwObj) {
		TimecardService service = new TimecardService();
		List<TimecardView> lstTimecardView = null;
		try {
			lstTimecardView = (List<TimecardView>) service.getTimecardView();
			return new ResponseEntity<List<TimecardView>>(lstTimecardView, HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<List<TimecardView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<List<TimecardView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/tc-detail-search", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardView>> TimecardDetailsView(@RequestBody TimecardView timecardDetailsView) {
		TimecardService service = new TimecardService();
		List<TimecardView> lstTimecardDetailsView = null;
		try {
			lstTimecardDetailsView = (List<TimecardView>) service.getTimecardDetailsView(6);
			return new ResponseEntity<List<TimecardView>>(lstTimecardDetailsView, HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<List<TimecardView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<List<TimecardView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
	@RequestMapping(value="/getTimecardsByManager",method=RequestMethod.POST,headers = "Accept=application/json")
	public ResponseEntity<List<TimecardManagerView>> TimecardViewByManager(@RequestBody TimecardManagerView view ){
		TimecardService service = new TimecardService();
		try {
			List<TimecardManagerView> result = service.getTimecardViewByManager(view);
			logger.debug("result   "+result);
			return new ResponseEntity<List<TimecardManagerView>>(result, HttpStatus.OK); 
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<TimecardManagerView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

}
	@RequestMapping(value="/getEmployees",method=RequestMethod.POST,headers = "Accept=application/json")
	public ResponseEntity<List<User>>getEmployees(@RequestBody User user){
		TimecardService service = new TimecardService();
		try {
			List<User> result = service.getEmployees(user.getEmpId());
			logger.debug("result   "+result);
			return new ResponseEntity<List<User>>(result, HttpStatus.OK); 
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
    @RequestMapping(value="/sample",headers = "Accept=application/json",method=RequestMethod.GET)
    @PreAuthorize("hasRole('MANAGER')")
	public String sample() {
		return "hello world";
		
	}
}