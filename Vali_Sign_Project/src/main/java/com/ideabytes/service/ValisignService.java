package com.ideabytes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ideabytes.binding.CurrentActionEntity;
import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.binding.TransactionEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.commonService.EncryptAndDecrypt;
import com.ideabytes.commonService.RandomKey;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.repository.CurrentActionRepository;
import com.ideabytes.repository.DeviceRepository;
import com.ideabytes.repository.TransactionRepository;
import com.ideabytes.repository.UserRepository;

@Service
public class ValisignService {
	DeviceEntity deviceEntity;

	@Value("${otp.time}")
	private int otpTime;

	private static final Logger log = LogManager.getLogger(ValisignService.class);
	UserRepository userRepo;
	AuthService authService;
	TransactionRepository transactionRepo;
	CurrentActionRepository currentActionRepo;
	EncryptAndDecrypt encryptDecrypt;
	DeviceRepository deviceRepo;

	@Autowired
	ValisignService(UserRepository userRepo, AuthService authService, TransactionRepository transactionRepo,
			CurrentActionRepository currentActionRepo, EncryptAndDecrypt encryptDecrypt, DeviceRepository deviceRepo) {
		this.userRepo = userRepo;
		this.authService = authService;
		this.transactionRepo = transactionRepo;
		this.currentActionRepo = currentActionRepo;
		this.encryptDecrypt = encryptDecrypt;
		this.deviceRepo = deviceRepo;
	}

	public String getDataKey(JSONObject inputData) {
		String dataKey = null;
		try {
			deviceEntity = deviceRepo.findByIdentifier(inputData.get(Constants.IDENTIFIER).toString());
			dataKey = deviceEntity.getDataKey().toString();
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return dataKey;
	}

	public JSONObject getDecryptedData(JSONObject inputData, String dataKey) {
		JSONObject dataObject = null;
		try {
			String dataDecrypted = encryptDecrypt.decrypt(inputData.get(Constants.DATA).toString(), dataKey);
			log.debug("dataDecrypted: " + dataDecrypted);
			dataObject = getStringToJSONObject(dataDecrypted);

		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return dataObject;
	}

	public JSONObject saveCurrentAction(CurrentActionEntity checkClientRequest) {
		checkClientRequest.setCodeExpiry(LocalDateTime.now());
		CurrentActionEntity finalSavedData = currentActionRepo.save(checkClientRequest);
		JSONObject finalObject = currectActionObject(finalSavedData);
		return finalObject;
	}

	@SuppressWarnings("unchecked")
	private JSONObject currectActionObject(CurrentActionEntity curentActionInput) {
		JSONObject curentActionData = new JSONObject();
		try {
			curentActionData.put(Constants.OTP, curentActionInput.getCode());
			curentActionData.put(Constants.ACTION, curentActionInput.getAction());
			curentActionData.put(Constants.OTPTIME, this.otpTime);
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return curentActionData;
	}

	public JSONObject getStringToJSONObject(String inputData) {
		JSONObject dataObject = null;
		try {
			JSONParser parseData = new JSONParser();
			dataObject = (JSONObject) parseData.parse(inputData);
		} catch (ParseException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return dataObject;
	}

	public int getUserId(String token) {
		int userId = 0;
		try {
			String tokenData = authService.getDataFromToken(token);
			log.debug("tokenData: " + tokenData);
			JSONObject userData = getStringToJSONObject(tokenData);
			String email = userData.get(Constants.USERNAME).toString();// username we using the email id.
			String password = userData.get(Constants.PASSWORD).toString();
			UserEntity userDataFromDb = userRepo.findByEmailAndPassword(email, password);
			if (userDataFromDb != null) {
				userId = userDataFromDb.getId();
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return userId;
	}
}
