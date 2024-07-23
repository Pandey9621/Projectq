package com.ideabytes.controller;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.binding.DevicesUsersEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.binding.UserValidationEntity;
import com.ideabytes.commonService.EncryptAndDecrypt;
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
import com.ideabytes.service.ConfigurationService;
import com.ideabytes.service.SignInService;

@RestController
//@CrossOrigin(origins= Mapper.ANGULARAPI, exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"}, allowedHeaders = "Requestor-Type")
public class SignIn extends ApiStatusConstants {

	DeviceEntity deviceEntity;
	JSONObject jsonObject;

	EncryptAndDecrypt encryptDecrypt;
	UserRepository userRepo;
	SignInService signInService;
	UserApplicationsRepository userAppRepo;
	ClientRepository clientRepo;
	DevicesUsersRepository deviceUserRepo;
	APIStatusCodes apiStatusCodes;
	AuthService authService;
	ConfigurationService configureService;
	DeviceRepository deviceRepoIdentifier;

	@Autowired
	SignIn(APIStatusCodes apiStatusCodes, DevicesUsersRepository deviceUserRepo, ClientRepository clientRepo,
			UserApplicationsRepository userAppRepo, SignInService signInService, AuthService authService,
			UserRepository userRepo, EncryptAndDecrypt encryptDecrypt, ConfigurationService configureService,
			DeviceRepository deviceRepoIdentifier) {
		this.apiStatusCodes = apiStatusCodes;
		this.deviceUserRepo = deviceUserRepo;
		this.clientRepo = clientRepo;
		this.userAppRepo = userAppRepo;
		this.signInService = signInService;
		this.authService = authService;
		this.userRepo = userRepo;
		this.encryptDecrypt = encryptDecrypt;
		this.configureService = configureService;
		this.deviceRepoIdentifier = deviceRepoIdentifier;

	}

	private static final Logger log = LogManager.getLogger(SignIn.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.SIGNIN, method = RequestMethod.POST)
	public ResponseEntity<?> signIn(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@RequestBody UserValidationEntity userValidationRequest) {
		Map<String, Object> response = new HashMap<>();
		try {
			String id = userValidationRequest.getIdentifier();
			String data = userValidationRequest.getData();
			deviceEntity = deviceRepoIdentifier.findByIdentifier(id);
			if (deviceEntity != null) {
				String key = deviceEntity.getDataKey();
				String decryptedText = encryptDecrypt.decrypt(data, key);
				if (decryptedText != null) {
					System.out.println("decryptedText: " + decryptedText);
					JSONParser parser = new JSONParser();
					JSONObject originalData = (JSONObject) parser.parse(decryptedText);
					log.debug("originalData: " + originalData);
					String email = originalData.get(Constants.USERNAME).toString();
					String password = originalData.get(Constants.PASSWORD).toString();
					// TODO this is for encrypted password. The decrypted password but while signUp
					// the password
					// is not encrypting
//				UserEntity getPass = userRepo.findByEmail(email);
//				UserEntity userEntity = userRepo.findByEmailAndPassword(email,
//						encryptDecrypt.decrypt(getPass.getPassword(), key));
					UserEntity userEntity = userRepo.findByEmailAndPassword(email, password);
					if (userEntity != null) {
						int userId = userEntity.getId();
						DevicesUsersEntity checkDevice = deviceUserRepo.findByUserIdAndDeviceId(userId,
								deviceEntity.getId());
						log.debug("checkDevice: " + checkDevice);
						String token = authService.generateJWT(originalData);
						if (checkDevice != null) {
							signInService.storeUserDeviceData(checkDevice.getId(), deviceEntity.getId(), userId, key,
									token);
						} else {
							// Zero means new device is storing in device users table.
							signInService.storeUserDeviceData(0, deviceEntity.getId(), userId, key, token);
						}
						log.debug("token: " + token);
						JSONArray listOfApp = signInService.getListOfApp(userId);
						JSONObject finalObjectforSignin = new JSONObject();
						finalObjectforSignin.put(Constants.TOKEN, token);
						finalObjectforSignin.put(Constants.LISTOFAPPLICATION, listOfApp);
						finalObjectforSignin.put(Constants.USERID, userEntity.getUserId());
						String encryptedData = encryptDecrypt.encrypt(finalObjectforSignin.toString(), key);

						response = apiStatusCodes.responseData(statusCodeNameForSignIn, IBVS_APPLOGIN_SUCCESS, lang);
						response.put(Constants.DATA, encryptedData);
					} else {
						response = apiStatusCodes.responseData(statusCodeNameForSignIn, IBVS_APPLOGIN_001, lang);
					}
				} else {
					response = apiStatusCodes.responseData(statusCodeNameForSignIn, IBVS_APPLOGIN_004, lang);
				}
			} else {
				response = apiStatusCodes.responseData(statusCodeNameForSignIn, IBVS_APPLOGIN_002, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
	}
}