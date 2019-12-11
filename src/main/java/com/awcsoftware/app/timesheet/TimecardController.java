package com.awcsoftware.app.timesheet;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	TimecardService service;

	/*
	 *  This function is called when employee will save his/her timecard information. 
	 *  This function will store data in table as draft mode so employee can 
	 *  change timecard as many time as s/he wants. This url/method will allow 
	 *  user to add new record  or update the existing timecard details 
	 * 
	 */
	
	@RequestMapping(value = "/tc-add", method = RequestMethod.POST, headers = "Accept=application/json")	 
	public ResponseEntity<String> saveTimecard(@RequestBody TimecardInfo timecardInfo) {
		service = new TimecardService();
		String timecardSaveResult = "";
		try {
			timecardSaveResult = service.saveTimecard(timecardInfo);
			return new ResponseEntity<String>(timecardSaveResult, HttpStatus.OK);
		} catch (DbException | AppException | ParseException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 *  This function is called when employee will submit his/her timecard information. 
	 *  This function will store data in table as submit mode.
	 *  Once the timecard data is submitted, employee can not resubmitted his/her timecard details again
	 *  until manager reject the particular time card. This url/method will allow 
	 *  user to add new record  or update the existing timecard details 
	 * 
	 */
	
	@RequestMapping(value = "/tc-submit", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> submitTimecard(@RequestBody TimecardInfo timecardInfo) {
		service = new TimecardService();
		String timecardSubmitResult = "";
		try {
			timecardSubmitResult = service.submitTimeCard(timecardInfo);
			return new ResponseEntity<String>(timecardSubmitResult, HttpStatus.OK);
		} catch (DbException | AppException | ParseException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 *  This function is called when employee wants to check his/her timecard.
	 *  This function will show the detail view of entered 
	 * 
	 */
	
	@RequestMapping(value = "/tc-search", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardInfo>> getEmployeeTimeCard(@RequestBody TimecardInfo timecardInfoParam) {
		service = new TimecardService();
		List<TimecardInfo> timecardInfo = null;
		try {
			timecardInfo = (List<TimecardInfo>) service.getEmployeeTimeCard(timecardInfoParam);
			return new ResponseEntity<List<TimecardInfo>>(timecardInfo, HttpStatus.OK);
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<TimecardInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 *  when user will click on result of method EmployeeTimecardView, 
	 *  This method will show detail view of selected TimecardInfo.tcId 
	 * 
	 */

	@RequestMapping(value = "/tc-detail-search", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardView>> getTimecardEmployeeDetailView(@RequestBody TimecardView timecardDetailsViewParam) {
		service = new TimecardService();
		List<TimecardView> timecardDetailsView = null;
		try {
			timecardDetailsView = (List<TimecardView>) service.getTimecardEmployeeDetailView(timecardDetailsViewParam.getTcId());
			return new ResponseEntity<List<TimecardView>>(timecardDetailsView, HttpStatus.OK);
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<TimecardView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 *  when user either save his/her timecard or submit his/her timecard, 
	 *  system will show entered details using this method.
	 * 
	 */

	@RequestMapping(value = "/tc-savedData", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<TimecardInfo> getTimecardSavedRecord(@RequestBody TimecardInfo timecardInfoParam) {
		service = new TimecardService();
		TimecardInfo timecardInfo = null;
		try {
			timecardInfo = (TimecardInfo) service.getTimecardSavedRecord(timecardInfoParam);
			return new ResponseEntity<TimecardInfo>(timecardInfo, HttpStatus.OK);
		} catch (DbException | AppException e) {
			return new ResponseEntity<TimecardInfo>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/tc-savedView", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardDayDetails>> getTimecardSavedRecordData(@RequestBody TimecardInfo timecardInfoParam) {
		service = new TimecardService();
		List<TimecardDayDetails> timecardDayDetails = null;
		try {
			timecardDayDetails = (List<TimecardDayDetails>) service.getTimecardSavedRecordData(timecardInfoParam);
			return new ResponseEntity<List<TimecardDayDetails>>(timecardDayDetails, HttpStatus.OK);
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<TimecardDayDetails>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 *  This method is created for manager view details.
	 * 
	 */

	@RequestMapping(value = "/tc-submitView", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardInfo>> getTimecardViewForManager(@RequestBody TimecardInfo timecardInfoParam) {
		service = new TimecardService();
		List<TimecardInfo> timecardManagerView= null;
		try {
			timecardManagerView = (List<TimecardInfo>) service.getTimecardViewForManager(timecardInfoParam);
			return new ResponseEntity<List<TimecardInfo>>(timecardManagerView, HttpStatus.OK);
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<TimecardInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	/*
	 * 
	 * Method created for Manager View, Approval and Rejection
	 * 
	 * 
	 * 
	 */

	/*
	 * @RequestMapping(value = "/getTimecardsByManager", method =
	 * RequestMethod.POST, headers = "Accept=application/json") public
	 * ResponseEntity<List<TimecardInfo>> TimecardViewByManager(
	 * 
	 * @RequestBody TimecardApproverDetails timecardapproval) { TimecardService
	 * service = new TimecardService(); try { List<TimecardInfo> result =
	 * service.getTimecardViewByManager(timecardapproval.getApproverId());
	 * logger.debug("result   " + result); return new
	 * ResponseEntity<List<TimecardInfo>>(result, HttpStatus.OK); } catch
	 * (DbException | AppException e) { return new
	 * ResponseEntity<List<TimecardInfo>>(HttpStatus.INTERNAL_SERVER_ERROR); }
	 * 
	 * }
	 */

	@RequestMapping(value = "/tc-approve", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<String> approveTimecard(@RequestBody TimecardApproverDetails timecardApproverDetails) {
		service = new TimecardService();
		String TimecardApproverResult = "";
		try {
			TimecardApproverResult = service.approveRejectTimecard(timecardApproverDetails);
			return new ResponseEntity<String>(TimecardApproverResult, HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (AppException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/getEmployees", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<List<User>> getEmployeesUnderLoggedInManager(@RequestBody User user) {
		service = new TimecardService();
		try {
			List<User> result = service.getEmployeesUnderLoggedInManager(user.getEmpId());
			logger.debug("result   " + result);
			return new ResponseEntity<List<User>>(result, HttpStatus.OK);
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/getTimecardsByManager", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardManagerView>> getEmployeeTimecardforManager(
			@RequestBody TimecardManagerView view) {
		service = new TimecardService();
		try {
			List<TimecardManagerView> result = service.getTimecardViewByManager(view);
			logger.debug("result   " + result);
			return new ResponseEntity<List<TimecardManagerView>>(result, HttpStatus.OK);
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<TimecardManagerView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	/*
	 * @GetMapping("/sample")
	 * 
	 * @PreAuthorize("hasRole('ROLE_EMPLOYEE')") public String sample() { return
	 * "hello world";
	 * 
	 * }
	 */
}