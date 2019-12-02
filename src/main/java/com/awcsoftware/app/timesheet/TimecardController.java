package com.awcsoftware.app.timesheet;

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

	static String  strClassName="";
	public TimecardController()
	{	
		strClassName = getClass().getSimpleName();
		strClassName=strClassName+".class";
	}

	static Logger logger = Logger.getLogger(strClassName);

	
	public void saveTimecard() {
		
	}
	
	public void submitTimecard() {
		
	}
	

	@RequestMapping(value = "/tc-add", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> addTimeSheetDetails(@RequestBody TimecardInfo timecardInfo) {
		TimecardService service = new TimecardService();
		String strTimeCardServiceRet="";
		try {
			strTimeCardServiceRet=service.saveTimecard(timecardInfo);
			return new ResponseEntity<String>(strTimeCardServiceRet, HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@RequestMapping(value = "/tc-edit", method = RequestMethod.POST, headers ="Accept=application/json")
	public ResponseEntity<String> updTimeSheetDetails(@RequestBody TimecardInfo timecardInfo) 
	{
		TimecardService service = new TimecardService();
		String strTimeCardServiceRet="";
		try {
			strTimeCardServiceRet=service.saveTimecard(timecardInfo);	//updTimeSheetDetails(tcSumaryInfo);
			return new ResponseEntity<String>(strTimeCardServiceRet, HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	 

	@RequestMapping(value = "/tc-search", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardView>> TimeCardView(@RequestBody TimecardView tcVwObj) {
		logger.debug("Inside tc-search 0");
		TimecardService service = new TimecardService();
		logger.debug("Inside tc-search 0-1-0");

		List<TimecardView> lsttcView=null;
		logger.debug("Inside tc-search 1");

		try {
			logger.debug("Inside tc-search 2");
			lsttcView=(List<TimecardView>)service.getTimecardView();
			logger.debug("Inside tc-search 3");
			return new ResponseEntity<List<TimecardView>>(lsttcView, HttpStatus.OK);
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