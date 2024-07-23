package com.ideabytes.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideabytes.binding.ClientEntity;
import com.ideabytes.binding.DevicesUsersEntity;
import com.ideabytes.binding.UserApplicationEntity;
import com.ideabytes.commonService.EncryptAndDecrypt;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.repository.ClientRepository;
import com.ideabytes.repository.DevicesUsersRepository;
import com.ideabytes.repository.UserApplicationsRepository;

@Service
public class SignInService {
	UserApplicationsRepository userAppRepo;
	ClientRepository clientRepo;
	EncryptAndDecrypt encryptDecrypt;
	DevicesUsersRepository deviceUserRepo;

	@Autowired
	SignInService(DevicesUsersRepository deviceUserRepo, EncryptAndDecrypt encryptDecrypt, ClientRepository clientRepo,
			UserApplicationsRepository userAppRepo) {
		this.deviceUserRepo = deviceUserRepo;
		this.encryptDecrypt = encryptDecrypt;
		this.clientRepo = clientRepo;
		this.userAppRepo = userAppRepo;
	}

	private static final Logger log = LogManager.getLogger(SignInService.class);

	@SuppressWarnings("unchecked")
	public JSONArray getListOfApp(int userId) {
		List<UserApplicationEntity> userAppList = userAppRepo.findByUserId(userId);
		JSONArray clientNames = new JSONArray();
		try {
		JSONObject listOfAppObject = null;
		if (userAppList != null) {
			for (int i = 0; i < userAppList.size(); i++) {
//				clientNames.add(clientRepo.findById(userAppList.get(i).getAppId()));
				listOfAppObject = new JSONObject();
				Optional<ClientEntity> clientEntity = clientRepo.findById(userAppList.get(i).getAppId());
				System.out.println("clientEntity: " + clientEntity);
				listOfAppObject.put("id", clientEntity.get().getId());
				listOfAppObject.put("name", clientEntity.get().getName());
				clientNames.add(listOfAppObject);
			}
		}
		}catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return clientNames;
	}

	public void storeUserDeviceData(int autoGenerateId, int deviceId, int userId, String key, String token) {
		try {
			DevicesUsersEntity deviceUserEntity = new DevicesUsersEntity();
			// TODO I trying to store in encrypted format getting exception data too long
			// for session key
//			String encryptedToken = encryptDecrypt.encrypt(token, key);

			if (autoGenerateId != 0) {
				deviceUserEntity.setId(autoGenerateId);
			}
			deviceUserEntity.setDeviceId(autoGenerateId);
			deviceUserEntity.setUserId(userId);
			deviceUserEntity.setDataKey(key);
			deviceUserEntity.setLastLogin(LocalDateTime.now());
			deviceUserEntity.setSessionKey(token);
			deviceUserEntity.setActive(true);
			deviceUserRepo.save(deviceUserEntity);
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
	}

}
