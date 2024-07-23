package com.ideabytes.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.constants.Constants;
import com.ideabytes.exception.Not_Found_Exception;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.service.UserService;
//import javax.servlet.http.HttpServletRequest;

//import javax.servlet.http.HttpServletRequest;
@CrossOrigin("http://localhost:4200")
@RestController
public class Hello_Time {

	JSONObject jo = null;
	@Autowired
	public UserService service;

	@GetMapping(Mapper.hello)
	/**
	 * This method getWishes print simple wish messages.
	 * 
	 * @return String.
	 */

	public Map<String, Object> getWishes() throws Not_Found_Exception {
		String get = "Hi hello,Good Morning.";
		jo = new JSONObject();
		jo.put(Constants.MESSAGE, get);
//		 jo.put(Constants.STATUSCODE,"200");
		jo.put(Constants.STATUS, HttpStatus.OK);
		jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
		return jo.toMap();

	}

	@GetMapping(Mapper.time)
	/**
	 * This method getCurrentTime print simple current time.
	 * 
	 * @return String.
	 */
	public Map<String, Object> getCurrentTime() {
		java.util.Date date = new java.util.Date();
		jo = new JSONObject();
		jo.put(Constants.MESSAGE, "The Current Time now is " + date.toString());
		jo.put(Constants.STATUSCODE, "200");
		jo.put(Constants.STATUS, "OK");
		return jo.toMap();

	}

}
