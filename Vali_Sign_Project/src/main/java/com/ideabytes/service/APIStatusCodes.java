package com.ideabytes.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ideabytes.commons.CommonMethods;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;

@Service
public class APIStatusCodes {
	@Value("${spring.profiles.active}")
	private String environment;

	private static final Logger log = LogManager.getLogger(APIStatusCodes.class);

	public JSONObject getAPIStatusCodes(String statusCodeName) {
		JSONObject appStatus = null;
		try {
			String statusCodesDir = CommonMethods.getStaticAbsolutePath(Constants.STATIC_STATUS_CODES, environment);
			// Based on the environment get the connection_*.json file from the above folder
			String statusCodesFile = statusCodesDir + File.separator + "ApiStatusCodes" + Constants.FILE_EXT_JSON;

			JSONParser parser = new JSONParser();
			Object statusCodeDetailsObject = parser.parse(new FileReader(statusCodesFile));
			org.json.simple.JSONObject statusCodes = (org.json.simple.JSONObject) statusCodeDetailsObject;
			appStatus = (org.json.simple.JSONObject) statusCodes.get(statusCodeName);
		} catch (IOException | ParseException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		} catch (URISyntaxException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return appStatus;
	}

	public Map<String, Object> responseStructure(JSONObject responseInput, String lang) {
		Map<String, Object> response = new HashMap<>();
		try {
//			JSONObject message = (JSONObject) responseInput.get(Constants.MESSAGE);
			System.out.println("responseInput: "+responseInput);
			response.put(Constants.MESSAGE, ((JSONObject) responseInput.get(Constants.MESSAGE)).get(lang).toString());
			response.put(Constants.ERROR, ((JSONObject) responseInput.get(Constants.ERROR)).get(lang).toString());
			response.put(Constants.STATUSCODE, responseInput.get(Constants.STATUSCODE));
			response.put(Constants.STATUS, responseInput.get(Constants.STATUS));
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> responseData(String statusCode, String IBVSKey, String lang) {
		Map<String, Object> response = new HashMap<>();
		try {
			log.debug("statusCode: " + statusCode + " ** IBVSKey: " + IBVSKey + " ** lang: " + lang);
			System.out.println("statusCode: " + statusCode + " ** IBVSKey: " + IBVSKey + " ** lang: " + lang);
			JSONObject appStatus = getAPIStatusCodes(statusCode);
			System.out.println("appStatus: "+appStatus);
			response = responseStructure((JSONObject) appStatus.get(IBVSKey), lang);
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

}
