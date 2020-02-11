package com.awcsoftware.app.client;

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
public class ClientController {
	
	@Autowired
	ClientService service;

	@RequestMapping(value = "/addClient", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> addClient(@RequestBody ClientInfo info) {
		try {
			return new ResponseEntity<String>(service.addClient(info), HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>("Db error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(value = "/viewClient", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<List<ClientInfo>> viewClient(@RequestBody ClientInfo info) {
		try {
			return new ResponseEntity<List<ClientInfo>>(service.viewClients(info),HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<List<ClientInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<List<ClientInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateClient", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateClient(@RequestBody ClientInfo info) {
		try {
			return new ResponseEntity<String>(service.updateClient(info),HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/getClientList", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<ClientInfo>> getClientList() {
		try {
			return new ResponseEntity<List<ClientInfo>>(service.getClientList(),HttpStatus.OK);
		} catch (AppException e) {
			return new ResponseEntity<List<ClientInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (DbException e) {
			return new ResponseEntity<List<ClientInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
