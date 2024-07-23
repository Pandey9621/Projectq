//package com.ideabytes.controller;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import com.ideabytes.binding.ClientEntity;
//import com.ideabytes.binding.UserEntity;
//import com.ideabytes.commonService.GetLang;
//import com.ideabytes.constants.ApiStatusConstants;
//import com.ideabytes.constants.Constants;
//import com.ideabytes.mappingAPIs.Mapper;
//import com.ideabytes.repository.ClientRepository;
//import com.ideabytes.repository.ClientRequestRepository;
//import com.ideabytes.repository.UserRepository;
//import com.ideabytes.service.APIStatusCodes;
//import com.ideabytes.service.AuthService;
//
//@RestController
//@CrossOrigin(Mapper.ANGULARAPI)
//public class ClientRequestController {
//	@Autowired
//	GetLang lang;
//
//	AuthService auth_service;
//	@Autowired
//	ClientRepository client_repository;
//	@Autowired
//	UserRepository userRepo;
//
//	@Autowired
//	ClientRequestRepository clientReqRepo;
//	private static final Logger log = LogManager.getLogger(ClientRequestController.class);
//
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = Mapper.CLIENTREQUEST, method = RequestMethod.GET)
//	public ResponseEntity<?> clientRequest(@RequestHeader Map<String, String> headers, @PathVariable String userId) {
//		String lang = null;
//		Map<String, Object> res = new HashMap<>();
//		APIStatusCodes apiStatusCodes = new APIStatusCodes();
//		JSONObject appStatus = apiStatusCodes.getAPIStatusCodes(ApiStatusConstants.statusCodeNameForClientRequest);
//		try {
//			String clientId = headers.get(Constants.XCLIENTID);
//			String clientSecretId = headers.get(Constants.XCLIENTSECRET);
//			lang = this.lang.getLanguage(headers.get(Constants.LANGUAGE));
//
//			if (clientId != null && clientSecretId != null) {
//				ClientEntity client_entity = client_repository.findByClientIdAndClientSecret(clientId, clientSecretId);
//				if (client_entity != null) {
//					UserEntity userData = userRepo.findByUserId(userId);
//					if (userData != null) {
//						ClientRequestEntity checkClientRequest = clientReqRepo.findByUserIdAndAppId(userData.getId(),
//								client_entity.getId());
//						ClientRequestEntity clientReqEntity = new ClientRequestEntity();
//						if (checkClientRequest == null) {
//							clientReqEntity.setAppId(client_entity.getId());
//							clientReqEntity.setUserId(userData.getId());
//							clientReqEntity.setClientRequest(1);
//							clientReqRepo.save(clientReqEntity);
//						} else {
//							clientReqEntity.setId(checkClientRequest.getId());
//							clientReqEntity.setAppId(client_entity.getId());
//							clientReqEntity.setUserId(userData.getId());
//							clientReqEntity.setClientRequest(1);
//							clientReqEntity.setModified(LocalDateTime.now());
//							clientReqRepo.save(clientReqEntity);
//						}
//
//						res = apiStatusCodes.responseStructure(
//								(JSONObject) appStatus.get(ApiStatusConstants.IBVS_CLIENTREQUEST_SUCCESS), lang);
//					} else {
//						res = apiStatusCodes.responseStructure(
//								(JSONObject) appStatus.get(ApiStatusConstants.IBVS_CLIENTREQUEST_003), lang);
//					}
//				} else {
//					res = apiStatusCodes.responseStructure(
//							(JSONObject) appStatus.get(ApiStatusConstants.IBVS_CLIENTREQUEST_002), lang);
//				}
//			} else {
//				res = apiStatusCodes
//						.responseStructure((JSONObject) appStatus.get(ApiStatusConstants.IBVS_CLIENTREQUEST_001), lang);
//			}
//
//		} catch (Exception e) {
//			log.fatal("Exception got in clientRequest: " + e.getMessage());
//			e.printStackTrace();
//		}
//		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
//		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
//	}
//}
