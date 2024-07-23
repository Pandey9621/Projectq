package com.ideabytes.service;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideabytes.binding.ClientEntity;
import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.commonService.RandomKey;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.repository.ClientRepository;
import com.ideabytes.repository.DeviceRepository;
import com.ideabytes.repository.UserApplicationsRepository;
import com.ideabytes.repository.UserRepository;

@Service
public class UserService {

	JSONObject jsonObject;
	UserEntity entity;
	ClientEntity clientEntity;
	RandomKey randomKey;
	UserRepository userRepo;
	UserApplicationsRepository userAppRepo;
	@Autowired
	ClientRepository clientRepo;
	@Autowired
	DeviceRepository deviceRepo;
	private static final Logger log = LogManager.getLogger(UserService.class);

	@Autowired
	UserService(UserRepository user_repository, RandomKey randomKey, UserApplicationsRepository userAppRepo,
			ClientRepository clientRepo) {
		this.userRepo = user_repository;
		this.randomKey = randomKey;
		this.userAppRepo = userAppRepo;
		this.clientRepo = clientRepo;
	}

	/**
	 * This method updateUser is declaring for updating the user details based on
	 * their id.
	 * 
	 * @param user_id used and userDetails accepting as a parameter.
	 * @return type is UsersEntity.
	 */

	public UserEntity updateUser(String user_id, UserEntity userDetails) {
		entity = userRepo.findByUserId(user_id);
		if (entity == null) {
			return null;
		} else {
			entity.setName(userDetails.getName());
			return userRepo.save(entity);
		}
	}

	@SuppressWarnings("unused")
	/**
	 * This method getById is declaring for getting the user details based on their
	 * id.
	 * 
	 * @param user_id used accepting as a parameter.
	 * @return type is UsersEntity.
	 */
	public UserEntity getById(String user_id) {
		UserEntity optionalEntity = userRepo.findByUserId(user_id);
		System.out.println(optionalEntity.toString());
		if (optionalEntity == null) {
			return null;
		} else {

			return optionalEntity;
		}

	}

	public UserEntity addUsersData(UserEntity userDetails) {
		UserEntity users_entity = null;
		try {
			UUID uuid = UUID.randomUUID();
			String convertUUID = uuid.toString();// converting into string data not insert without convert data insert.
			userDetails.setName(userDetails.getName());
			userDetails.setEmail(userDetails.getEmail());
			userDetails.setPhone(userDetails.getPhone());
			userDetails.setPassword(randomKey.randomPass(8));
			userDetails.setUserId(convertUUID);
			userDetails.setActive(true);
			userDetails.setApp(userDetails.getApp());
			users_entity = userRepo.save(userDetails);
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return users_entity;
	}

	@Transactional
	public boolean userDelete(String userId) {
		// TODO It need to delete from userApplication also so regarding this need
		// client id for this user.
		int userEntity = userRepo.deleteByUserId(userId);
		boolean value = false;
		if (userEntity != 0) {
			value = true;
		}
		return value;
	}

	//Future purpose
	@Transactional
	public boolean userDelete1(String clientId, String userId) {
		ClientEntity clientData = clientRepo.findByClientId(clientId);
		UserEntity userData = userRepo.findByUserId(userId);
		int userAppRes = userAppRepo.deleteByAppIdAndUserId(clientData.getId(), userData.getId());
		int userEntity = userRepo.deleteByUserId(userId);
		boolean value = false;
		if (userEntity != 0) {
			value = true;
		}
		return value;
	}
	//modified by me.
	public UserEntity updateUser1(String user_id,JSONObject userDetails) {
		entity = userRepo.findByUserId(user_id);
		if (entity == null) {
			return null;
		} else {
			System.out.println("ABCDE");
			entity.setName(userDetails.get("name").toString());
			entity.setEmail(userDetails.get("email").toString());
			entity.setPhone(userDetails.get("phone").toString());
			
//	        DeviceEntity de=new DeviceEntity();
//	        de.setDeviceId(userDetails.get("did").toString());
//			de.setName(userDetails.get("dname").toString());
//			de.setIpAddress(userDetails.get("address").toString());
//		    de.setLocation(userDetails.get("location").toString());
//			de.setType(userDetails.get("type").toString());
//			deviceRepo.save(de);
//		    ClientEntity ce=new ClientEntity();
//	        ce.setEmail(userDetails.get("clientEmail").toString());
//			ce.setPhone(userDetails.get("clientPhone").toString());
//			clientRepo.save(ce);
	        return userRepo.save(entity);
		}
	}
}
