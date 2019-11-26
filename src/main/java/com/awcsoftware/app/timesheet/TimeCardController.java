package com.awcsoftware.app.timesheet;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.awcsoftware.app.AppException;
import com.awcsoftware.mybatis.DbException;

@RestController
public class TimeCardController {

	static String  strClassName="";
	public TimeCardController()
	{	
		strClassName = getClass().getSimpleName();
		strClassName=strClassName+".class";
	}

	static Logger logger = Logger.getLogger(strClassName);


	@RequestMapping(value = "/tc-add", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> addTimeSheetDetails(@RequestBody TimeCardSummaryInfo tcSumaryInfo) {
		TimeCardService service = new TimeCardService();
		String strTimeCardServiceRet="";
		try {
			strTimeCardServiceRet=service.addTimeSheetDetails(tcSumaryInfo);
			return new ResponseEntity<String>(strTimeCardServiceRet, HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@RequestMapping(value = "/tc-edit", method = RequestMethod.POST, headers ="Accept=application/json")
	public ResponseEntity<String> updTimeSheetDetails(@RequestBody TimeCardSummaryInfo tcSumaryInfo) 
	{
		TimeCardService service = new TimeCardService();
		String strTimeCardServiceRet="";
		try {
			strTimeCardServiceRet=service.updTimeSheetDetails(tcSumaryInfo);
			return new ResponseEntity<String>(strTimeCardServiceRet, HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	 

	@RequestMapping(value = "/tc-search", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<TimeCardView>> TimeCardView(@RequestBody TimeCardView tcVwObj) {
		logger.debug("Inside tc-search 0");
		TimeCardService service = new TimeCardService();
		logger.debug("Inside tc-search 0-1-0");

		List<TimeCardView> lsttcView=null;
		logger.debug("Inside tc-search 1");

		try {
			logger.debug("Inside tc-search 2");
			lsttcView=(List<TimeCardView>)service.getTCView();
			logger.debug("Inside tc-search 3");
			return new ResponseEntity<List<TimeCardView>>(lsttcView, HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<List<TimeCardView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<List<TimeCardView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
