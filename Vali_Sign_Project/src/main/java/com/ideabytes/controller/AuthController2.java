//package com.ideabytes.controller;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import com.ideabytes.binding.ClientEntity;
//import com.ideabytes.binding.ClientEntity2;
//import com.ideabytes.binding.UserApplicationEntity;
//import com.ideabytes.binding.UserEntity;
//import com.ideabytes.commonService.Base64EncryptAndDecrypt;
//import com.ideabytes.commonService.EncryptAndDecrypt;
//import com.ideabytes.commonService.GetLang;
//import com.ideabytes.constants.ApiStatusConstants;
//import com.ideabytes.constants.Constants;
//import com.ideabytes.mappingAPIs.Mapper;
//import com.ideabytes.repository.ClientRepository;
//import com.ideabytes.repository.ClientRepository2;
//import com.ideabytes.repository.UserRepository;
//import com.ideabytes.service.APIStatusCodes;
//import com.ideabytes.service.AuthService;
//import com.ideabytes.service.ClientService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//@RestController
//@CrossOrigin(Mapper.ANGULARAPI)
//public class AuthController2 {
//	private static final Logger log = LogManager.getLogger(AuthController2.class);// Implementation of logger
//
//	ClientEntity client_entity;
//
//	@Value("${privateKeyBase64}")
//	private String privateKey;
//	@Value("${publicKeyBase64}")
//	private String publicKey;
//	@Autowired
//	UserRepository userRepo;
//	EncryptAndDecrypt encryptAndDecrypt;
//
//	JSONObject json_object;
//	JSONArray json_array = new JSONArray();
//
//	int periodCount = 0;
//	UserEntity user_details;
//
//	ClientService client_service;
//	AuthService auth_service;
//	ClientRepository client_repository;
//	ClientRepository2 client_repository2;
//	UserRepository user_repository;
//	GetLang lang;
//	APIStatusCodes apiStatusCodes = new APIStatusCodes();
//
//	@Autowired // Using @Autowired annotation for injecting UsersRepository into Auth
//				// Controller Class
//	AuthController2(ClientService client_service, AuthService auth_service, ClientRepository client_repository,
//			UserRepository user_repository, GetLang lang, ClientRepository2 client_repository2,
//			EncryptAndDecrypt encryptAndDecrypt) {
//		this.client_service = client_service;
//		this.auth_service = auth_service;
//		this.client_repository = client_repository;
//		this.user_repository = user_repository;
//		this.client_repository2 = client_repository2;
//		this.encryptAndDecrypt = encryptAndDecrypt;
//		this.lang = lang;
//	}
//
//	/**
//	 * This method signUpApp is declared for the registration of users.
//	 * 
//	 * @RequestBody is the used for getting the users data from request body.
//	 * @return Map Object.
//	 */
////	@SuppressWarnings({ "unchecked", "unused" })
////	@RequestMapping(value = Mapper.USERREGISTRATION, method = RequestMethod.POST)
////	public ResponseEntity<?> signUpApp(@RequestHeader Map<String, String> headers,
////			@RequestBody UserEntity userDetails) {
////		String lang = null;
////		Map<String, Object> res = new HashMap<>();
////		APIStatusCodes apiStatusCodes = new APIStatusCodes();
////		JSONObject appStatus = apiStatusCodes.getAPIStatusCodes(ApiStatusConstants.statusCodeNameForUserSignUp);
////		try {
////			String clientId = headers.get(Constants.XCLIENTID);
////			String clientSecretId = headers.get(Constants.XCLIENTSECRET);
////			lang = this.lang.getLanguage(headers.get(Constants.LANGUAGE));
////			if (clientId != null && clientSecretId != null) {
////				ClientEntity client_entity = client_repository.findByClientIdAndClientSecret(clientId, clientSecretId);
////				if (client_entity != null) {
////					UserEntity userData = userRepo.findByEmailOrPhone(userDetails.getEmail(), userDetails.getPhone());
//////					System.out.println("userData: "+userData.toString()+" "+userData.getName());
////					if (userData != null) {
////						res = apiStatusCodes.responseStructure(
////								(JSONObject) appStatus.get(ApiStatusConstants.IBVS_USERSIGNUP_003), lang);
////					} else {
////						JSONArray userSavedEn = auth_service.userSignUp(userDetails, client_entity.getId());
////
////						res = apiStatusCodes.responseStructure(
////								(JSONObject) appStatus.get(ApiStatusConstants.IBVS_USERSIGNUP_SUCCESS), lang);
////						res.put(Constants.DATA, userSavedEn);
////					}
////				} else {
////					res = apiStatusCodes.responseStructure(
////							(JSONObject) appStatus.get(ApiStatusConstants.IBVS_USERSIGNUP_001), lang);
////				}
////			} else {
////				res = apiStatusCodes
////						.responseStructure((JSONObject) appStatus.get(ApiStatusConstants.IBVS_USERSIGNUP_002), lang);
////			}
////		} catch (Exception e) {
////			log.fatal("Exception got in signUpApp: " + e.getMessage());
////			e.printStackTrace();
////		}
////		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
////		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
////	}
//
//	/**
//	 * This method clientRegister is declared for the registration of clients.
//	 * 
//	 * @RequestBody is the used for getting the clients data from request body.
//	 * @return Map Object.
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = Mapper.CLIENTREGISTRATION1, method = RequestMethod.POST)
//	public ResponseEntity<?> clientRegister(
//			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
//			@RequestBody JSONObject clientDetails) {
//		Map<String, Object> res = new HashMap<>();
//		System.out.println("clientDetails: " + clientDetails);
//		JSONObject appStatus = apiStatusCodes.getAPIStatusCodes(ApiStatusConstants.statusCodeNameForClientSignUp);
//		String name = clientDetails.get("name").toString();
//		ClientEntity2 outputDetails = client_repository2.findByNameAndEmail(name,
//				clientDetails.get("email").toString());
//		System.out.println("outputDetails: " + outputDetails);
//		try {
//			if (outputDetails == null) {
//				String client_id_with_prefix = auth_service.clientIdWithPrefix(name);
//				ClientEntity2 client_entity = client_repository2.findByClientId(client_id_with_prefix);
//				if (client_entity == null) {
////					client_entity = auth_service.clientSave(clientDetails, client_id_with_prefix);
//					ClientEntity2 clientSave1 = auth_service.clientSave1(clientDetails, client_id_with_prefix);
//					JSONObject clientJson = auth_service.clientJsonObject(clientSave1);
//					System.out.println("mail content started: " + clientSave1.toString());
//					System.out.println("clientJson: " + clientJson);
//					// Email sending
////					auth_service.sendEmail(client_entity);
//					JSONObject data = new JSONObject();
//					data.put("dataKey", Constants.clientDataKey);
//					data.put("IV", Constants.clientIV);
//					data.put("clientResponse", clientJson);
//					System.out.println("data: " + data);
//					String encryptedData = encryptAndDecrypt.encrypt(data.toString(), Constants.clientDataKey);
//					System.out.println("encryptedData: " + encryptedData);
//					res = apiStatusCodes.responseStructure(
//							(JSONObject) appStatus.get(ApiStatusConstants.IBVS_CLIENTSIGNUP_SUCCESS), lang);
//					res.put(Constants.DATA, encryptedData);
////					res.put("dataKey", Constants.clientDataKey);
////					res.put("IV", Constants.clientIV);
//
//				} else {
//					// TODO need to work on here regarding to create the random client id and
//					// secrete key here again we have to call the method for create the clientid and
//					// clientsecret
//					res = apiStatusCodes.responseStructure(
//							(JSONObject) appStatus.get(ApiStatusConstants.IBVS_CLIENTSIGNUP_001), lang);
//				}
//			} else {
//				res = apiStatusCodes
//						.responseStructure((JSONObject) appStatus.get(ApiStatusConstants.IBVS_CLIENTSIGNUP_001), lang);
//			}
//		} catch (Exception e) {
//			log.fatal("Exception got in clientRegister: " + e.getMessage());
//			e.printStackTrace();
//		}
//		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
//		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
//	}
//
//	/**
//	 * This method signInApp is declared for the login the users.
//	 * 
//	 * @RequestBody is the used for getting the login details of user
//	 * @return Map Object.
//	 */
////	@SuppressWarnings("unchecked")
////	@RequestMapping(value = Mapper.SIGNIN, method = RequestMethod.POST)
////	public ResponseEntity<?> signInApp(@RequestHeader Map<String, String> headers,
////			@RequestBody UserEntity signInRequest) {
////		Map<String, Object> res = new HashMap<>();
////		JSONObject appStatus = apiStatusCodes.getAPIStatusCodes(ApiStatusConstants.statusCodeNameForSignIn);
////		String lang = null;
////		try {
////			String clientId = headers.get(Constants.XCLIENTID);
////			String clientSecretId = headers.get(Constants.XCLIENTSECRET);
////			lang = this.lang.getLanguage(headers.get(Constants.LANGUAGE));
////			String userEmail = signInRequest.getEmail();
////			String password = signInRequest.getPassword();
////
////			// Validate the userName and Password (e.g., check against a database)
//////			boolean validate = auth_service.validateUserNameAndPassword(userEmail, password);
////			UserEntity validate = user_repository.findByEmailAndPassword(userEmail, password);
////
////			if (validate != null) {
////				json_array = new JSONArray();
////				json_object = new JSONObject();
////				String token = auth_service.generateJWT(password);
////				json_object.put(Constants.TOKEN, token);
////				json_object.put(Constants.DATAKEY, auth_service.dataKey());
////
////				json_array.add(json_object);
////				res = apiStatusCodes
////						.responseStructure((JSONObject) appStatus.get(ApiStatusConstants.IBVS_APPLOGIN_SUCCESS), lang);
////				res.put(Constants.DATA, json_array);
////
////			} else {
////				res = apiStatusCodes.responseStructure((JSONObject) appStatus.get(ApiStatusConstants.IBVS_APPLOGIN_001),
////						lang);
////			}
////		} catch (Exception e) {
////			log.error("Exception got in signInApp: " + e.getMessage());
////			e.printStackTrace();
////		}
////		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
////		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
////	}
//
////	@SuppressWarnings("unchecked")
////	@RequestMapping(value = Mapper.LISTOFAPPLICATIONS, method = RequestMethod.POST)
////	public ResponseEntity<?> getListOfApplication(@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
////			@RequestBody List<Integer> appId, @PathVariable Integer user_id) {
////
////		System.out.println("appId: "+appId +"  userid: "+user_id);
////		Map<String, Object> res = new HashMap<>();
////		JSONObject appStatus = apiStatusCodes.getAPIStatusCodes(ApiStatusConstants.statusCodeNameForListOfApp);
////		try {
////			json_array = auth_service.validateUserId(appId, user_id);
////			if (json_array.size() > 0) {
////				res = apiStatusCodes
////						.responseStructure((JSONObject) appStatus.get(ApiStatusConstants.IBVS_LISTOFAPP_SUCCESS), lang);
////				res.put(Constants.DATA, json_array.toString());
////			} else {
////				res = apiStatusCodes
////						.responseStructure((JSONObject) appStatus.get(ApiStatusConstants.IBVS_LISTOFAPP_001), lang);
////			}
////		} catch (Exception e) {
////			log.fatal("Exception got in selectListOfBook: " + e.getMessage());
////			e.printStackTrace();
////
////		}
////		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
////		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
////	}
//}
