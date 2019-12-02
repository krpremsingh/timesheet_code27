package com.awcsoftware.app.timesheet;
/*
 * @author Prem Shankar Kumar
 * 
 */

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
	public ResponseEntity<List<TimecardInfo>> TimecardViewByManager(@RequestBody TimecardApproval timecardapproval ){
		TimecardService service = new TimecardService();
		try {
			List<TimecardInfo> result = service.getTimecardViewByManager(timecardapproval.getApproverId());
			logger.debug("result   "+result);
			return new ResponseEntity<List<TimecardInfo>>(result, HttpStatus.OK); 
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<TimecardInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

}
    @GetMapping("/sample")
    @PreAuthorize("hasRole('EMPLOYEE')")
	public String sample() {
		return "hello world";
		
	}
}