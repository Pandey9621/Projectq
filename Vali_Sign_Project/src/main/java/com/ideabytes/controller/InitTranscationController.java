
package com.ideabytes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.ClientEntity;
import com.ideabytes.binding.TransactionEntity;
import com.ideabytes.binding.UserApplicationEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.commonService.EncryptAndDecrypt;
import com.ideabytes.commonService.GetLang;
import com.ideabytes.commonService.OTPGenerateAndValidate;
import com.ideabytes.commonService.RandomKey;
import com.ideabytes.constants.ApiStatusConstants;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.ClientRepository;
import com.ideabytes.repository.CurrentActionRepository;
import com.ideabytes.repository.DevicesUsersRepository;
import com.ideabytes.repository.TransactionRepository;
import com.ideabytes.repository.UserApplicationsRepository;
import com.ideabytes.repository.UserRepository;
import com.ideabytes.service.APIStatusCodes;
import com.ideabytes.service.InitTransactionService;

@RestController
//@CrossOrigin(origins= Mapper.ANGULARAPI, exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"}, allowedHeaders = "Requestor-Type")
public class InitTranscationController extends ApiStatusConstants {

	UserEntity userEntity;
	TransactionEntity transactionEntity;

	@Value("${otp.time}")
	private int otpTime;

	TransactionRepository transactionRepo;
	ClientRepository client_repository;
	UserApplicationsRepository userApplicationRepo;
	OTPGenerateAndValidate otpVal;
	UserRepository user_repository;
	RandomKey randomKey;
	GetLang lang;
	CurrentActionRepository currentActionRepo;
	EncryptAndDecrypt encryptDecrypt;
	UserRepository userRepo;
	DevicesUsersRepository deviceUserRepo;
	InitTransactionService initTransService;
	APIStatusCodes apiStatusCodes;

	InitTranscationController(InitTransactionService initTransService, DevicesUsersRepository deviceUserRepo,
			UserRepository userRepo, EncryptAndDecrypt encryptDecrypt, CurrentActionRepository currentActionRepo,
			GetLang lang, RandomKey randomKey, UserRepository user_repository,
			UserApplicationsRepository userApplicationRepo, OTPGenerateAndValidate otpVal,
			TransactionRepository transactionRepo, 
			ClientRepository client_repository, 
			APIStatusCodes apiStatusCodes) {
		this.initTransService = initTransService;
		this.deviceUserRepo = deviceUserRepo;
		this.userRepo = userRepo;
		this.encryptDecrypt = encryptDecrypt;
		this.currentActionRepo = currentActionRepo;
		this.lang = lang;
		this.randomKey = randomKey;
		this.user_repository = user_repository;
		this.userApplicationRepo = userApplicationRepo;
		this.otpVal = otpVal;
		this.transactionRepo = transactionRepo;
		this.client_repository = client_repository;
		this.apiStatusCodes = apiStatusCodes;
	}

	private static final Logger log = LogManager.getLogger(InitTranscationController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.AUTHORIZETRANSACTION, method = RequestMethod.POST)
	public ResponseEntity<?> authorizeTransaction(@RequestHeader Map<String, String> headers,
			@RequestBody JSONObject input) {
		Map<String, Object> res = new HashMap<>();
		String lang = null;
		String clientId = headers.get(Constants.XCLIENTID);
		String clientSecretId = headers.get(Constants.XCLIENTSECRET);
		try {
			String otp = input.get(Constants.OTP).toString();
			String userId = input.get(Constants.USERID).toString();
			lang = this.lang.getLanguage(headers.get(Constants.LANGUAGE));
//			JSONObject appStatus = apiStatusCodes
//					.getAPIStatusCodes(ApiStatusConstants.statusCodeNameForAuthorizeTransaction);
			if (clientId != null && clientSecretId != null) {
				ClientEntity client_entity = client_repository.findByClientIdAndClientSecret(clientId, clientSecretId);
				log.debug("client_entity: " + client_entity);
				if (client_entity != null) {
					userEntity = user_repository.findByUserId(userId);
					UserApplicationEntity userAppEntity = userApplicationRepo.findByUserIdAndAppId(userEntity.getId(),
							client_entity.getId());
					if (userAppEntity != null) {
						// OTP validation
						int otpValidate = this.otpVal.validateOTP(otp, this.otpTime, userEntity.getId(),
								client_entity.getId());
						switch (otpValidate) {
						// zero means time expired
						case 0: {
							res = apiStatusCodes.responseData(statusCodeNameForAuthorizeTransaction,
									IBVS_AUTHORIZETRANSACTION_003, lang);
							break;
						}
						// one means verified successful
						case 1: {
							res = apiStatusCodes.responseData(statusCodeNameForAuthorizeTransaction,
									IBVS_AUTHORIZETRANSACTION_SUCCESS, lang);
							break;
						}
						// two means invalid OTP
						case 2: {
							res = apiStatusCodes.responseData(statusCodeNameForAuthorizeTransaction,
									IBVS_AUTHORIZETRANSACTION_004, lang);
							break;
						}
						default:
							break;
						}

					} else {
						System.out.println("This user don't have a application.");
					}
				} else {

					res = apiStatusCodes.responseData(statusCodeNameForAuthorizeTransaction,
							IBVS_AUTHORIZETRANSACTION_002, lang);
				}
			} else {
				res = apiStatusCodes.responseData(statusCodeNameForAuthorizeTransaction, IBVS_AUTHORIZETRANSACTION_001,
						lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();

		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.INITTRANSACTION, method = RequestMethod.POST)
	public ResponseEntity<?> getInitTransaction(@RequestHeader Map<String, String> headers,
			@RequestBody JSONObject inputJsonObject) {
		Map<String, Object> res = new HashMap<>();
		String lang = null;
		String clientId = headers.get(Constants.XCLIENTID);
		String clientSecretId = headers.get(Constants.XCLIENTSECRET);
		JSONObject appStatus = apiStatusCodes.getAPIStatusCodes(ApiStatusConstants.statusCodeNameForInitTransaction);
		System.out.println(appStatus);
		try {
			String userId = inputJsonObject.get(Constants.USERID).toString();
			lang = this.lang.getLanguage(lang);
			System.out.println("clientId: " + clientId + " clientSecretId: " + clientSecretId);
			if (clientId != null && clientSecretId != null) {
				ClientEntity client_entity = client_repository.findByClientIdAndClientSecret(clientId, clientSecretId);
				if (client_entity != null) {
					userEntity = userRepo.findByUserId(userId);
					if (userEntity != null) {
						int id = userEntity.getId();
						int appId = client_entity.getId();
						TransactionEntity transData = initTransService.initTransactionData(inputJsonObject, appId, id);
						if (transData != null) {
							res=apiStatusCodes.responseData(statusCodeNameForInitTransaction, IBVS_INITTRANSACTION_SUCCESS, lang);
						} else {
							// The init transaction details is not inserted in DB.
							res=apiStatusCodes.responseData(statusCodeNameForInitTransaction, IBVS_INITTRANSACTION_003, lang);
						}
					} else {
						// The requested userId details not found.
						res=apiStatusCodes.responseData(statusCodeNameForInitTransaction, IBVS_INITTRANSACTION_004, lang);
					}
				} else {
					res=apiStatusCodes.responseData(statusCodeNameForInitTransaction, IBVS_INITTRANSACTION_002, lang);
				}
			} else {
				res=apiStatusCodes.responseData(statusCodeNameForInitTransaction, IBVS_INITTRANSACTION_001, lang);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = Mapper.GETTRANSACTION)
	public ResponseEntity<?> getTransactionDetails(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable String userId) {
		Map<String, Object> res = new HashMap<>();
		try {
			userEntity = userRepo.findByUserId(userId);
			List<TransactionEntity> transactionEntity = transactionRepo.findByUserId(userEntity.getId());
			log.debug("transactionEntity " + transactionEntity.toString());
			if (transactionEntity != null) {
				res=apiStatusCodes.responseData(statusCodeNameForTransactionDetails, IBVS_TRANSACTIONDETAILS_SUCCESS, lang);
				res.put(Constants.DATA, transactionEntity);
			} else {
				res=apiStatusCodes.responseData(statusCodeNameForTransactionDetails, IBVS_TRANSACTIONDETAILS_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}
}
