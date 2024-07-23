package com.ideabytes.controller;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.ClientEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.commonService.GetLang;
import com.ideabytes.constants.ApiStatusConstants;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.ClientRepository;
import com.ideabytes.repository.UserRepository;
import com.ideabytes.service.APIStatusCodes;
import com.ideabytes.service.AuthService;
import com.ideabytes.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
//@CrossOrigin(origins= Mapper.ANGULARAPI)
public class AuthController extends ApiStatusConstants {
	private static final Logger log = LogManager.getLogger(AuthController.class);// Implementation of logger

	ClientEntity client_entity;

	@Autowired
	UserRepository userRepo;

	JSONObject json_object;
	JSONArray json_array = new JSONArray();

	int periodCount = 0;
	UserEntity user_details;

	ClientService client_service;
	AuthService auth_service;
	ClientRepository client_repository;
	UserRepository user_repository;
	GetLang lang;
	APIStatusCodes apiStatusCodes;

	@Autowired // Using @Autowired annotation for injecting UsersRepository into Auth
				// Controller Class
	AuthController(ClientService client_service, AuthService auth_service, ClientRepository client_repository,
			UserRepository user_repository, GetLang lang, APIStatusCodes apiStatusCodes) {
		this.client_service = client_service;
		this.auth_service = auth_service;
		this.client_repository = client_repository;
		this.user_repository = user_repository;
		this.lang = lang;
		this.apiStatusCodes = apiStatusCodes;
	}

	/**
	 * This method signUpApp is declared for the registration of users.
	 * 
	 * @RequestBody is the used for getting the users data from request body.
	 * @return Map Object.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = Mapper.USERREGISTRATION, method = RequestMethod.POST)
	public ResponseEntity<?> signUpApp(@RequestHeader Map<String, String> headers,
			@RequestBody UserEntity userDetails) {
		String lang = null;
		Map<String, Object> res = new HashMap<>();
		try {
			String clientId = headers.get(Constants.XCLIENTID);
			String clientSecretId = headers.get(Constants.XCLIENTSECRET);
			lang = this.lang.getLanguage(headers.get(Constants.LANGUAGE));
			if (clientId != null && clientSecretId != null) {
				ClientEntity client_entity = client_repository.findByClientIdAndClientSecret(clientId, clientSecretId);
				if (client_entity != null) {
					UserEntity userData = userRepo.findByEmailAndPhone(userDetails.getEmail(), userDetails.getPhone());
					// user details fetching
					if (userData != null) {
						auth_service.userApplicationDataStoring(userData.getId(), client_entity.getId());
						res = this.apiStatusCodes.responseData(statusCodeNameForUserSignUp, IBVS_USERSIGNUP_003, lang);
						res.put(Constants.DATA, userData);
						log.info(ApiStatusConstants.IBVS_USERSIGNUP_003 + " User details already registered.");
					} else {
						JSONObject userSavedEn = auth_service.userSignUp(userDetails, client_entity.getId());
						auth_service.userRegisterSentMail(userSavedEn, client_entity);

						res = this.apiStatusCodes.responseData(statusCodeNameForUserSignUp, IBVS_USERSIGNUP_SUCCESS,
								lang);
						res.put(Constants.DATA, userSavedEn);
						log.info(ApiStatusConstants.IBVS_USERSIGNUP_SUCCESS + " userDetail: " + userSavedEn);
					}
				} else {
					res = this.apiStatusCodes.responseData(statusCodeNameForUserSignUp, IBVS_USERSIGNUP_001, lang);
					log.error(IBVS_USERSIGNUP_001 + " There are no clients for this user.");
				}
			} else {
				res = this.apiStatusCodes.responseData(statusCodeNameForUserSignUp, IBVS_USERSIGNUP_002, lang);
				log.error(IBVS_USERSIGNUP_002
						+ " The required headers 'clientId' and 'client secret key' were not included in the request.");
			}
		} catch (Exception e) {
			log.fatal("Exception got in signUpApp: " + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}

	/**
	 * This method clientRegister is declared for the registration of clients.
	 * 
	 * @RequestBody is the used for getting the clients data from request body.
	 * @return Map Object.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.CLIENTREGISTRATION, method = RequestMethod.POST)
	public ResponseEntity<?> clientRegister(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@RequestBody ClientEntity clientDetails) {
		Map<String, Object> res = new HashMap<>();
		try {
			ClientEntity outputDetails = client_repository.findByEmailAndPhone(clientDetails.getEmail(),
					clientDetails.getPhone());
			if (outputDetails == null) {
				String client_id_with_prefix = auth_service.clientIdWithPrefix(clientDetails.getName());
				ClientEntity client_entity = client_service.getByClientId(client_id_with_prefix);
				if (client_entity == null) {
					client_entity = auth_service.clientSave(clientDetails, client_id_with_prefix);
					System.out.println("mail content started: ");

					// Email sending
					auth_service.sendEmail(client_entity);
					res = this.apiStatusCodes.responseData(statusCodeNameForClientSignUp, IBVS_CLIENTSIGNUP_SUCCESS,
							lang);
					res.put(Constants.DATA, client_entity);

				} else {
					// TODO need to work on here regarding to create the random client id and
					// secrete key here again we have to call the method for create the clientid and
					// clientsecret
					res = this.apiStatusCodes.responseData(statusCodeNameForClientSignUp, IBVS_CLIENTSIGNUP_001, lang);
				}
			} else {
				res = this.apiStatusCodes.responseData(statusCodeNameForClientSignUp, IBVS_CLIENTSIGNUP_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}

}
