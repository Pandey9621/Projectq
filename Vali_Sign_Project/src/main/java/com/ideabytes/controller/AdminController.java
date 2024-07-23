///*********************** Ideabytes Software India Pvt Ltd *********************                                   
//* This controller handles requests related to admin details.
//* It provides endpoints for appRegisterd.
//* @author  Chandan Pandey
//* @version 20.0.1
//* @since   2023-07-28.
//*/
//package com.ideabytes.controller;
//
//import java.io.File;
//
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.URISyntaxException;
//import java.util.HashMap;
//import java.util.Map;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.json.JSONObject;
////import org.json.simple.parser.JSONParser;
////import org.json.simple.parser.ParseException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
////import com.ideabytes.commons.CommonMethods;
//import com.ideabytes.constants.Constants;
//import com.ideabytes.mappingAPIs.Mapper;
//import com.ideabytes.service.AdminService;
//
//@RestController
//public class AdminController {
//	private static final Logger log = LogManager.getLogger(AdminController.class);
//	@Value("${app.name}")
//	private String appName;
//	@Value("${app.version}")
//	private String appVersion;
//	@Autowired
//	AdminService admin_service;
//
//	@GetMapping("/appDetails")
//	/**
//	 * This method appDetails is using for getting the details about application
//	 * 
//	 * @return Map Object.
//	 */
//	public ResponseEntity<Map<String, Object>> appDetails() {
//		JSONObject jsonObject = null;
//		String lang = "en";
//		try {
//			jsonObject = new JSONObject();
//			jsonObject.put(Constants.JAVAVERSION, admin_service.printJavaVersion());
//			jsonObject.put(Constants.TOMCATVERSION, admin_service.printtomcatVersion());
//			jsonObject.put(Constants.OSNAME, admin_service.printOSName());
//			jsonObject.put(Constants.LOCALHOST, admin_service.printlocalHost());
//			jsonObject.put(Constants.APPNAME, appName);
//			jsonObject.put(Constants.APPVERSION, appVersion);
//			jsonObject.put(Constants.CURRENTVALUE, admin_service.printActiveEnvironment());
//			jsonObject.put(Constants.STATUS, HttpStatus.OK.value());
//			log.info(jsonObject);
//			return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toMap());
//
//		}
//
//		catch (Exception e) {
//			jsonObject = new JSONObject();
//			jsonObject.put(Constants.MESSAGE, e.getMessage());
//			jsonObject.put(Constants.STATUS, HttpStatus.BAD_REQUEST.value());
//			jsonObject.put(Constants.ERROR, e.getMessage());
//			jsonObject.put(Constants.ERRORCODE, Constants.IBVS_CONFIG_001);
//			jsonObject.put(Constants.DATA, Constants.EMPTYSTRING);
//			log.error(jsonObject);
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonObject.toMap());
//		}
//
//	}
//	
//	}
//

/*********************** Ideabytes Software India Pvt Ltd *********************                                   
* Here,AdminController class is implemented for AppDetails.
* @author  Chandan Pandey
* @version 0.1.0
* @since   2023-07-28.
*/
package com.ideabytes.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.AdminEntity;
import com.ideabytes.commonService.GetLang;
import com.ideabytes.constants.ApiStatusConstants;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.AdminRepository;
import com.ideabytes.service.APIStatusCodes;
import com.ideabytes.service.AdminService;
import com.ideabytes.service.AuthService;

@RequestMapping(Constants.ADMIN)
@RestController
//@CrossOrigin(origins = Mapper.ANGULARAPI)
public class AdminController extends ApiStatusConstants {
	private static final Logger log = LogManager.getLogger(AdminController.class);
	AdminService adminService;
	APIStatusCodes apiStatusCodes;
	AdminRepository adminRepository;
	AuthService authService;
	JSONObject parsedData;
	AdminEntity admin_entity;
	GetLang lang;

	@Autowired
	AdminController(AdminRepository adminRepository, APIStatusCodes apiStatusCodes, AdminService adminService,
			AuthService authService, GetLang lang) {
		this.adminRepository = adminRepository;
		this.apiStatusCodes = apiStatusCodes;
		this.adminService = adminService;
		this.authService = authService;
		this.lang = lang;
	}

	/**
	 * This method appDetails is using for getting the details about application
	 * 
	 * @return Map Object.
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.APP, method = RequestMethod.GET)
	ResponseEntity<?> appDetails(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang)
			throws IOException, URISyntaxException, ParseException {
		Map<String, Object> response = new HashMap<>();
		try {
			JSONArray data = adminService.dataForAdmin();
			if (data.size() > 0) {
				response = apiStatusCodes.responseData(statusCodeNameForApp, IBVS_APP_SUCCESS, lang);
				response.put(Constants.DATA, data);
			} else {
				response = apiStatusCodes.responseData(statusCodeNameForApp, IBVS_APP_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			response = apiStatusCodes.responseData(statusCodeNameForApp, IBVS_APP_002, lang);
		}

		int httpStatusCode = (int)((long) response.get(Constants.STATUS));
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Access-Control-Allow-Origin", "*");
//		return ResponseEntity.ok().headers(headers).body(response);
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
	}

	@GetMapping("/getBaseUrl")
	public ResponseEntity<String> getBaseUrl(@RequestHeader HttpHeaders headers) {
		InetSocketAddress host = headers.getHost();
		String url = "http://" + host.getHostName() + ":" + host.getPort();
		return new ResponseEntity<String>(String.format("Base URL = %s", url), HttpStatus.OK);
	}

	@RequestMapping(value = Mapper.ADMINREGISTRATION, method = RequestMethod.POST)
	public ResponseEntity<?> adminSignUpApp(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@RequestBody JSONObject adminDetails) {
		Map<String, Object> res = new HashMap<>();
		try {
//			log.debug("admin details"+adminDetails);
			System.out.println("admin details "+adminDetails);
			JSONObject adminData = adminService.saveAdminDetails(adminDetails.get(Constants.EMAIL).toString());
//			AdminEntity adminData = adminRepository.findByEmail(adminDetails.get(Constants.EMAIL).toString());
			// user details fetching
			if (adminData == null) {
				JSONObject adminSavedEn = adminService.adminInsertData(adminDetails);
				res = apiStatusCodes.responseData(statusCodeNameForAdminSignUp, IBVS_ADMINSIGNUP_SUCCESS, lang);
				res.put(Constants.DATA, adminSavedEn);
			} else {
				res = apiStatusCodes.responseData(statusCodeNameForAdminSignUp, IBVS_ADMINSIGNUP_001, lang);
				res.put(Constants.DATA, adminData);
				log.error(ApiStatusConstants.IBVS_ADMINSIGNUP_001 + " User details already registered.");
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}

	@RequestMapping(value = Mapper.READADMIN, method = RequestMethod.GET)
	public ResponseEntity<?> readsConfigDetails(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang) {
		Map<String, Object> res = new HashMap<>();
		try {
			List<AdminEntity> admin_details = (List<AdminEntity>) adminRepository.findAll();
			if (admin_details != null) {
				res = apiStatusCodes.responseData(statusCodeNameForReadAdmin, IBVS_READADMIN_SUCCESS, lang);
				res.put(Constants.DATA, admin_details);
			} else {
				res = apiStatusCodes.responseData(statusCodeNameForReadAdmin, IBVS_READADMIN_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("allowedOriginPatterns", "*"); // Set the CORS header to allow all origins (for demonstration purposes; consider setting it to your specific origin)
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(headers).body(res);
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}

	@RequestMapping(value = Mapper.UPDATEADMIN, method = RequestMethod.PUT)
	public ResponseEntity<?> updateAdminDetails(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable Integer id, @RequestBody JSONObject adminDetails) {
		Map<String, Object> res = new HashMap<>();
		admin_entity = adminService.updateAdminDetails(id, adminDetails);
		if (admin_entity != null) {
			res = apiStatusCodes.responseData(statusCodeNameForUpdateAdmin, IBVS_UPDATEADMIN_SUCCESS, lang);
		} else {
			res = apiStatusCodes.responseData(statusCodeNameForUpdateAdmin, IBVS_UPDATEADMIN_001, lang);
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}// end of updateByUserId method.

	@RequestMapping(value = Mapper.DELETEADMIN, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAdminDetails(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable int id) {
		Map<String, Object> res = new HashMap<>();
		try {

			boolean adminEntity = adminService.deleteAdminDetails(id);
			if (adminEntity) {
				res = apiStatusCodes.responseData(statusCodeNameForDeleteAdmin, IBVS_DELETEADMIN_SUCCESS, lang);
			} else {
				res = apiStatusCodes.responseData(statusCodeNameForDeleteAdmin, IBVS_DELETEADMIN_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.ADMINSIGNIN, method = RequestMethod.POST)
	public ResponseEntity<?> signIn(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@RequestBody JSONObject adminRequestForSignIn) {
		System.out.println("adminRequestForSignIn: "+adminRequestForSignIn);
		Map<String, Object> res = new HashMap<>();
		String email = adminRequestForSignIn.get(Constants.EMAIL).toString();
		String password = adminRequestForSignIn.get(Constants.PASSWORD).toString();
		System.out.println("adminEntity: " + adminRequestForSignIn);
		try {
			parsedData = adminService.adminSign(email, password);
			System.out.println("parsedData: "+parsedData);
			if (parsedData != null) {
				String token = authService.generateJWT(adminRequestForSignIn);
				System.out.println("token " + token);
				parsedData.put(Constants.TOKEN, token);
				System.out.println("parsedDta " + parsedData);
				res = apiStatusCodes.responseData(statusCodeNameForAdminSignIn, IBVS_ADMINSIGNIN_SUCCESS, lang);
				res.put(Constants.DATA, parsedData);
			} else {
				res = apiStatusCodes.responseData(statusCodeNameForAdminSignIn, IBVS_ADMINSIGNIN_001, lang);
			}

		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}

}
