package com.ideabytes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideabytes.binding.ClientEntity;
import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.binding.DevicesUsersEntity;
import com.ideabytes.binding.UserApplicationEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.commonService.AllServices;
import com.ideabytes.commonService.GetLang;
import com.ideabytes.constants.ApiStatusConstants;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.ClientRepository;
import com.ideabytes.repository.DeviceRepository;
import com.ideabytes.repository.DevicesUsersRepository;
import com.ideabytes.repository.UserApplicationsRepository;
import com.ideabytes.repository.UserRepository;
import com.ideabytes.service.APIStatusCodes;
import com.ideabytes.service.AuthService;
import com.ideabytes.service.UserAppService;
import com.ideabytes.service.UserService;
import com.ideabytes.service.implementations.DatabaseServicesImpl;

@RestController
//@CrossOrigin(origins= Mapper.ANGULARAPI, exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"}, allowedHeaders = "Requestor-Type")
public class UserController extends ApiStatusConstants {
	private static final Logger log = LogManager.getLogger(UserController.class);
	UserEntity userEntity;
	JSONObject jsonObject;
	@Autowired
    ClientRepository client_repository;
	@Autowired
//    ClientRepository client_repository;
	UserService userService;
	UserRepository userRepo;
	Optional<ClientEntity> client_entity;
	@Autowired
	UserAppService uas;
	APIStatusCodes apiStatusCodes;
	@Autowired
	UserApplicationsRepository user_app_repo;
	@Autowired
	DeviceRepository dr;
	@Autowired
	DevicesUsersRepository device_user_repo;
	@Autowired
	DatabaseServicesImpl dbs;
	AllServices commonServices;
	@Autowired
	AuthService auth_service;
	DeviceEntity device_entity;
	GetLang lang;
    @Autowired
	DatabaseServicesImpl dsi;
	List<UserApplicationEntity> user_app_entity;
	@Autowired
	UserRepository user_repo;
	@Autowired
	UserController(APIStatusCodes apiStatusCodes, UserRepository user_repository, UserService user_service,	AllServices commonServices) {
		this.apiStatusCodes = apiStatusCodes;
		this.userRepo = user_repository;
		this.userService = user_service;
		this.commonServices=commonServices;
		this.client_repository=client_repository;
		this.lang=lang;
	}

	/**
	 * This method gettingAllUserData is declared for finding the all user record.
	 * 
	 * @return type is ResponseEntity.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.READUSERS, method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang) {

		Map<String, Object> response = new HashMap<>();
		try {
			List<UserEntity> user_details = (List<UserEntity>) userRepo.findAll();
			if (user_details != null) {
				response = apiStatusCodes.responseData(statusCodeNameForAllUsers, IBVS_ALLUSERS_SUCCESS, lang);
				response.put(Constants.DATA, user_details);
			} else {
				response = apiStatusCodes.responseData(statusCodeNameForAllUsers, IBVS_ALLUSERS_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE.value()).body(jsonObject);
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Access-Control-Allow-Origin", "*");
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(headers).body(response);

	}

	/**
	 * This method getById is declared for finding the user record based on their
	 * index no.
	 * 
	 * @PathVariable is used as parameter to accept the value from url path.
	 * @return type is ResponseEntity.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.GETBYID, method = RequestMethod.GET)
	public ResponseEntity<?> getById(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable Integer id){
		Map<String, Object> res = new HashMap<>();
		try {
			Optional<UserEntity> userEntity = userRepo.findById(id);
			if (userEntity.isPresent()) {
				res = apiStatusCodes.responseData(statusCodeNameForUsersById, IBVS_USERSBYID_SUCCESS, lang);
//				res.put(Constants.DATA, userEntity);
//			    res.put(Constants.DATA, user_app_repo.getJoinInformation());   
//			List<UserApplicationEntity> uad=userRepo.findByDeviceId();
//			    List li=
//				System.out.println("li12345678 "+li);
	
		
			
				
			} else {
				res = apiStatusCodes.responseData(statusCodeNameForUsersById, IBVS_USERSBYID_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}// end of findUsersByFilter method.

	/**
	 * This method getByUserId is declared for finding the user record based on
	 * their user id.
	 * 
	 * @PathVariable is used as parameter to accept the value from url path.
	 * @return type is ResponseEntity.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.GETBYUSERID, method = RequestMethod.GET)
	public ResponseEntity<?> getUsersByUserId(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable String user_id) {
		Map<String, Object> response = new HashMap<>();

		try {
			UserEntity users_entity = userRepo.findByUserId(user_id);
		    System.out.println("UserEntity "+users_entity);
			                           
			if (users_entity!= null) {
			  JSONObject jsonObject=new JSONObject();
		      DevicesUsersEntity due=device_user_repo.findByUserId(users_entity.getId());
		     //Without using joins concept
		      if(due!=null) {
		      Optional<DeviceEntity>de=dr.findById(due.getDeviceId());
		      if(de.isPresent()) {
		        device_entity=de.get();
		        dsi.getUsersForDevice(due.getDeviceId());
		     }}else {
		    	      device_entity=null;
		    }
//		  	UserEntity users_entity = userRepo.findByUserId(user_id);
			List<UserApplicationEntity>li	=user_app_repo.findByUserId(users_entity.getId());
			List <ClientEntity> list=new ArrayList<ClientEntity>();
			for(UserApplicationEntity ue:li) {
				Optional<ClientEntity> ce=client_repository.findById(ue.getAppId());
				list.add(ce.get());
			}
		      
		      
//		      user_app_entity=user_app_repo.findByUserId(users_entity.getId()); 
//		      ClientEntity clientEntity = null; 
//		      for(UserApplicationEntity uae:user_app_entity) {
//		    	  if(uae.getUserId()==users_entity.getId()) {
//		    		  client_entity=client_repository.findById(uae.getAppId());
//		    		  if(client_entity.isPresent()) {
//		    			 clientEntity =client_entity.get();
//		    		  }
//		    	  }
//		      }
//			try {
//	            ObjectMapper objectMapper = new ObjectMapper();
//	            Object[] objects = objectMapper.readValue(list.toString(), Object[].class);
//	            System.out.println("CHNADNA "+objects.toString());
//	          System.out.println(objectMapper.writeValueAsString(objects[0])+"CHANDAN");
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return null;
//	        }
		         jsonObject.put(Constants.DATA,users_entity);
			     jsonObject.put(Constants.APPDETAILS,list);
			     jsonObject.put(Constants.DEVICEDETAILS,device_entity);	    
			 	 response = apiStatusCodes.responseData(statusCodeNameForUsersByUserId, IBVS_USERSBYUSERID_SUCCESS, lang);
	             response.put(Constants.DATA, jsonObject);
         
         		  
//		      try {
//		    	  //Using join concept 
//		    	  UserEntity users_entity = userRepo.findByUserId(user_id);
//		      Set js=uas.getApplicationDetails(users_entity.getId());
//		      JSONObject js1=uas.getDeviceDetails(users_entity.getId());
//		       if(js1!=null)
//		       {
//		    	     jsonObject.put(Constants.DATA,users_entity);
//				     jsonObject.put(Constants.APPDETAILS,js);
//				     jsonObject.put(Constants.DEVICEDETAILS,js1);	    
//				 	 response = apiStatusCodes.responseData(statusCodeNameForUsersByUserId, IBVS_USERSBYUSERID_SUCCESS, lang);
//		             response.put(Constants.DATA, jsonObject);
//		       }
//		       else {
//		    	     jsonObject.put(Constants.DATA, users_entity);
//				     jsonObject.put(Constants.APPDETAILS, js);
//				     jsonObject.put(Constants.DEVICEDETAILS,js1);	    
//				 	 response = apiStatusCodes.responseData(statusCodeNameForUsersByUserId, IBVS_USERSBYUSERID_SUCCESS, lang);
//		             response.put(Constants.DATA, jsonObject);
//		       }
//		      }
//		      catch(Exception e) {
//		    	  
//		      }
        	} else {
				response = apiStatusCodes.responseData(statusCodeNameForUsersByUserId, IBVS_USERSBYUSERID_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(this.commonServices.corsHeader()).body(response);

	}

	/**
	 * This method deleteByUserId is declared for deleting the user record based on
	 * their user id.
	 * 
	 * @PathVariable is used as parameter to accept the value from url path.
	 * @return type is ResponseEntity.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.DELETEUSER, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteByUserId(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable String user_id) {
		Map<String, Object> response = new HashMap<>();
		try {
			boolean users_entity = userService.userDelete(user_id);
//			boolean users_entity = userService.userDelete1(clientid, user_id);
			log.debug("users_entity: " + users_entity);
			if (users_entity) {
				response = apiStatusCodes.responseData(statusCodeNameForDeleteByUserId, IBVS_DELETEBYUSERID_SUCCESS, lang);
			} else {
				
				response = apiStatusCodes.responseData(statusCodeNameForDeleteByUserId, IBVS_DELETEBYUSERID_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(this.commonServices.corsHeader()).body(response);

	}
	// end of fetchById method.

	/**
	 * This method updateByUserId is declared the updating the user data based on
	 * their id.
	 * 
	 * @PathVariable is the used for getting the value from url path
	 * @return type is ResponseEntity.
	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = Mapper.UPDATEUSER, method = RequestMethod.PUT)
//	public ResponseEntity<?> updateByUserId(
//			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
//			@PathVariable String user_id, @RequestBody UserEntity usersEntity) {
//		Map<String, Object> response = new HashMap<>();
//		try {
//			userEntity = userService.updateUser(user_id, usersEntity);
//			if (userEntity != null) {
//				response = apiStatusCodes.responseData(statusCodeNameForUpdateUser, IBVS_UPDATEUSER_SUCCESS, lang);
//			} else {
//				response = apiStatusCodes.responseData(statusCodeNameForUpdateUser, IBVS_UPDATEUSER_001, lang);
//			}
//		} catch (Exception e) {
//			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
//			e.printStackTrace();
//		}
//		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
//		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
////		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(this.commonServices.corsHeader()).body(response);
////
//	}// end of updateByUserId method.

	/**
	 * This api method addUsers is declared for the additional users.
	 * 
	 * @RequestBody is the used for getting the users data from request body.
	 * @return Map Object.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.ADDUSERS, method = RequestMethod.POST)
	public ResponseEntity<?> addUsers(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@RequestBody UserEntity userDetails) {
		 Map<String, Object> response = new HashMap<>();
		try {
			UserEntity ue  =userRepo.findByEmailAndPhone(userDetails.getEmail(), userDetails.getPhone());
//			userEntity = userService.addUsersData(userDetails);
			if (ue== null) {

				ClientEntity clientEntity=client_repository.findByName(userDetails.getApp());
                System.out.println("cli "+clientEntity);
            	//Modified by me.
				userEntity = userService.addUsersData(userDetails);
				auth_service.userApplicationDataStoring(userEntity.getId(), clientEntity.getId());
			
				auth_service.userRegisterSentMail1(userEntity, clientEntity);
				response = apiStatusCodes.responseData(statusCodeNameForUserInserted, IBVS_USERINSERTED_SUCCESS, lang);
				response.put(Constants.DATA, userEntity);
			} else {
//				response = apiStatusCodes.responseData(statusCodeNameForUserInserted, IBVS_USERINSERTED_001, lang);
                response = apiStatusCodes.responseData(statusCodeNameForUserInserted, IBVS_USERINSERTED_001, lang);
			   	response.put(Constants.DATA, ue);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(this.commonServices.corsHeader()).body(response);

	}
	/**
	 * This method updateByUserId is declared the updating the user data based on
	 * their id.
	 * 
	 * @PathVariable is the used for getting the value from url path
	 * @return type is ResponseEntity.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.UPDATEUSER1, method = RequestMethod.PUT)//Modified by me.
	public ResponseEntity<?> updateByUserId(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable String user_id, @RequestBody JSONObject usersEntity) {
		Map<String, Object> response = new HashMap<>();
		try {
			userEntity = userService.updateUser1(user_id, usersEntity);
			if (userEntity != null) {
				response = apiStatusCodes.responseData(statusCodeNameForUpdateUser, IBVS_UPDATEUSER_SUCCESS, lang);
			} else {
				response = apiStatusCodes.responseData(statusCodeNameForUpdateUser, IBVS_UPDATEUSER_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(this.commonServices.corsHeader()).body(response);
//
	}// end of updateByUserId method.
	
	
	@RequestMapping(value = Mapper.TRANSACTION, method = RequestMethod.GET)
	public ResponseEntity<?>  getTransactionDetails(@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,@PathVariable String user_id) {
		UserEntity ue=user_repo.findByUserId(user_id);
		
		Map<String, Object> response = new HashMap<>();
		if(ue==null) {
			
		}else {
			  String app="";
			user_app_entity=user_app_repo.findByUserId(ue.getId()); 
			System.out.println("user id "+ue.getId());
		      ClientEntity clientEntity = null; 
		      List<String>list=new ArrayList<>();
		      for(UserApplicationEntity uae:user_app_entity) {
		    	
		    		  client_entity=client_repository.findById(uae.getAppId());
		    		  if(client_entity.isPresent()) {
		    			 clientEntity =client_entity.get();
		    			app=clientEntity.getName();
		    			list.add(app);
		    		  }
		    	  }
		 
		      jsonObject=new JSONObject();
		      jsonObject.put(Constants.DATA, ue);
//		      jsonObject.put(Constants.APP, app);
		      jsonObject.put("Details","This user associated with this applications "+list+" their "
		    		  +" user details available.Please approve it");
            auth_service.userRegisterSentMail2(jsonObject);
			response = apiStatusCodes.responseData(statusCodeNameForTransactionDetails, IBVS_TRANSACTIONDETAILS_SUCCESS, lang);
		    response.put(Constants.DATA, jsonObject); 
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@RequestMapping(value = Mapper.USERREGISTRATION1, method = RequestMethod.POST)
//	public ResponseEntity<?> signUpApp(@RequestHeader Map<String, String> headers,
//			@RequestBody UserEntity userDetails) {
//		String lang = null;
//		Map<String, Object> res = new HashMap<>();
//		try {
//			String clientId = headers.get(Constants.XCLIENTID);
//			String clientSecretId = headers.get(Constants.XCLIENTSECRET);
//			lang = this.lang.getLanguage(headers.get(Constants.LANGUAGE));
//			if (clientId != null && clientSecretId != null) {
//				ClientEntity client_entity = client_repository.findByClientIdAndClientSecret(clientId, clientSecretId);
//				if (client_entity != null) {
//					UserEntity userData = userRepo.findByEmailAndPhone(userDetails.getEmail(), userDetails.getPhone());
//					// user details fetching
//					if (userData != null) {
//						auth_service.userApplicationDataStoring(userData.getId(), client_entity.getId());
//						res = this.apiStatusCodes.responseData(statusCodeNameForUserSignUp, IBVS_USERSIGNUP_003, lang);
//						res.put(Constants.DATA, userData);
//						log.info(ApiStatusConstants.IBVS_USERSIGNUP_003 + " User details already registered.");
//					} else {
//						JSONObject userSavedEn = auth_service.userSignUp(userDetails, client_entity.getId());
//						auth_service.userRegisterSentMail(userSavedEn, client_entity);
//
//						res = this.apiStatusCodes.responseData(statusCodeNameForUserSignUp, IBVS_USERSIGNUP_SUCCESS,
//								lang);
//						res.put(Constants.DATA, userSavedEn);
//						log.info(ApiStatusConstants.IBVS_USERSIGNUP_SUCCESS + " userDetail: " + userSavedEn);
//					}
//				} else {
//					res = this.apiStatusCodes.responseData(statusCodeNameForUserSignUp, IBVS_USERSIGNUP_001, lang);
//					log.error(IBVS_USERSIGNUP_001 + " There are no clients for this user.");
//				}
//			} else {
//				res = this.apiStatusCodes.responseData(statusCodeNameForUserSignUp, IBVS_USERSIGNUP_002, lang);
//				log.error(IBVS_USERSIGNUP_002
//						+ " The required headers 'clientId' and 'client secret key' were not included in the request.");
//			}
//		} catch (Exception e) {
//			log.fatal("Exception got in signUpApp: " + e.getMessage());
//			e.printStackTrace();
//		}
//		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
//		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
//	}

}
