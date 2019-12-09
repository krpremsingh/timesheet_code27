package com.awcsoftware.app.timesheet;

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

	TimecardService service;

	@RequestMapping(value = "/tc-add", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> saveTimecard(@RequestBody TimecardInfo timecardInfo) {
		service = new TimecardService();
		String strTimeCardServiceRet = "";
		try {
			strTimeCardServiceRet = service.saveTimecard(timecardInfo);
			return new ResponseEntity<String>(strTimeCardServiceRet, HttpStatus.OK);
		} catch (DbException | AppException | ParseException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/tc-submit", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> submitTimecard(@RequestBody TimecardInfo timecardInfo) {
		service = new TimecardService();
		String strTimeCardServiceRet = "";
		try {
			strTimeCardServiceRet = service.submitTimeCard(timecardInfo);
			return new ResponseEntity<String>(strTimeCardServiceRet, HttpStatus.OK);
		} catch (DbException | AppException | ParseException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/tc-search", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardView>> TimeCardView(@RequestBody TimecardView tcVwObj) {
		service = new TimecardService();
		List<TimecardView> lstTimecardView = null;
		try {
			lstTimecardView = (List<TimecardView>) service.getCurrentWeekTimecard();
			return new ResponseEntity<List<TimecardView>>(lstTimecardView, HttpStatus.OK);
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<TimecardView>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/tc-detail-search", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<TimecardView>> TimecardDetailsView(@RequestBody TimecardView timecardDetailsView) {
		service = new TimecardService();
		List<TimecardView> lstTimecardDetailsView = null;
		try {
			lstTimecardDetailsView = (List<TimecardView>) service.getTimecardDetailsView(timecardDetailsView.getTcId());
			return new ResponseEntity<List<TimecardView>>(lstTimecardDetailsView, HttpStatus.OK);
		} catch (DbException | AppException e) {
			return new ResponseEntity<List<TimecardView>>(HttpStatus.INTERNAL_SERVER_ERROR);
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