package com.ideabytes.controller;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.CurrentActionEntity;
import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.commonService.AllServices;
import com.ideabytes.commonService.EncryptAndDecrypt;
import com.ideabytes.commonService.GetLang;
import com.ideabytes.commonService.RandomKey;
import com.ideabytes.constants.ApiStatusConstants;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.CurrentActionRepository;
import com.ideabytes.repository.DeviceRepository;
import com.ideabytes.repository.TransactionRepository;
import com.ideabytes.repository.UserApplicationsRepository;
import com.ideabytes.repository.UserRepository;
import com.ideabytes.service.APIStatusCodes;
import com.ideabytes.service.AuthService;
import com.ideabytes.service.ValisignService;

@RestController
//@CrossOrigin(origins= Mapper.ANGULARAPI, exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"}, allowedHeaders = "Requestor-Type")
public class ValisignController extends ApiStatusConstants {
	DeviceEntity deviceEntity;

	EncryptAndDecrypt encryptDecrypt;
	UserApplicationsRepository userApplicationRepo;
	RandomKey randomKey;
	GetLang lang;
	CurrentActionRepository currentActionRepo;
	AuthService authService;
	ValisignService valiService;
	AllServices allServices;
	UserRepository userRepo;
	DeviceRepository deviceRepo;
	TransactionRepository transactionRepo;
	APIStatusCodes apiStatusCodes;

	@Autowired
	ValisignController(EncryptAndDecrypt encryptDecrypt, UserApplicationsRepository userApplicationRepo,
			RandomKey randomKey, GetLang lang, CurrentActionRepository currentActionRepo, AuthService authService,
			ValisignService valiService, AllServices allServices, UserRepository userRepo, DeviceRepository deviceRepo,
			TransactionRepository transactionRepo, APIStatusCodes apiStatusCodes) {
		this.encryptDecrypt = encryptDecrypt;
		this.userApplicationRepo = userApplicationRepo;
		this.randomKey = randomKey;
		this.lang = lang;
		this.currentActionRepo = currentActionRepo;
		this.authService = authService;
		this.valiService = valiService;
		this.allServices = allServices;
		this.userRepo = userRepo;
		this.deviceRepo = deviceRepo;
		this.transactionRepo = transactionRepo;
		this.apiStatusCodes = apiStatusCodes;
	}

	private static final Logger log = LogManager.getLogger(InitTranscationController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.GETVALISIGNCODE, method = RequestMethod.POST)
	public ResponseEntity<?> getValisignCode(@RequestHeader Map<String, String> headers,
			@RequestBody JSONObject inputData) {
		Map<String, Object> res = new HashMap<>();
		
		String lang = null;
		try {
			lang = this.lang.getLanguage(headers.get(Constants.LANGUAGE));
			JSONObject appStatus = apiStatusCodes.getAPIStatusCodes(ApiStatusConstants.statusCodeNameForValisign);
			String token = allServices.getAuthorization(headers.get(Constants.AUTHORIZATION));
//			System.out.println("token "+token);
			boolean tokenExpiry = authService.isTokenExpired(token);
			if (!tokenExpiry) {
				String dataKey = valiService.getDataKey(inputData);
				JSONObject dataObject = valiService.getDecryptedData(inputData, dataKey);
				System.out.println("dataObject: " + dataObject);
				int appId = Integer.valueOf(dataObject.get(Constants.APPID).toString());
//				int userId = Integer.valueOf(dataObject.get(Constants.USERID).toString());
				int userId = valiService.getUserId(token);
				if (userId != 0) {
					CurrentActionEntity checkClientRequest = currentActionRepo.findByUserIdAndAppId(userId, appId);
					if (checkClientRequest != null) {
						// Current action data saving
						JSONObject savedData = valiService.saveCurrentAction(checkClientRequest);
						System.out.println("savedData: " + savedData);
//							String encryptedData = valiService.getTransactionData(userId, dataKey, otp, this.otpTime);
						String encryptedData = encryptDecrypt.encrypt(savedData.toString(), dataKey);

						res = apiStatusCodes.responseData(statusCodeNameForValisign, IBVS_VALISIGNCODE_SUCCESS, lang);
						res.put(Constants.DATA, encryptedData);
					} else {
						// No request from client for this user.
						res = apiStatusCodes.responseData(statusCodeNameForValisign, IBVS_VALISIGNCODE_003, lang);
					}
				} else {
					res = apiStatusCodes.responseData(statusCodeNameForValisign, IBVS_VALISIGNCODE_002, lang);
				}
			} else {
				res = apiStatusCodes.responseData(statusCodeNameForValisign, IBVS_VALISIGNCODE_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}
}
