package com.ideabytes.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.catalina.util.ServerInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ideabytes.binding.AdminEntity;
import com.ideabytes.commonService.RandomKey;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.repository.AdminRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
	@Autowired
	private Environment environment;
	@Autowired
	AdminRepository admin_repo;
	@Autowired
	RandomKey randomKey;
	AdminEntity admin_entity;
    @Autowired
	AdminRepository adminRepository;
	@Value("${app.name}")
	private String appName;
	@Value("${app.version}")
	private String appVersion;

	private static final Logger log = LogManager.getLogger(AdminService.class);

	public String printActiveEnvironment() {
		String activeEnvironment = environment.getProperty("spring.profiles.active");
		return activeEnvironment;
	}

	public String printJavaVersion() {
		String java_version = System.getProperty("java.version");
		return java_version;// Getting javaVersion
	}

	public String printtomcatVersion() {
		String tomcatVersion = ServerInfo.getServerInfo();
		return tomcatVersion;// Getting tomcatVersion
	}

	public String printOSName() {
		String osName = System.getProperty("os.name");// Getting osName
		return osName;
	}

	public String printlocalHost() throws UnknownHostException {
		InetAddress localHost = InetAddress.getLocalHost();
		String ipAddress = localHost.getHostAddress();
		return ipAddress;
	}

	@SuppressWarnings("unchecked")
	public JSONArray dataForAdmin() {
		JSONArray data = new JSONArray();
		try {
			JSONObject appDataJsonObject = new JSONObject();
			appDataJsonObject.put(Constants.JAVAVERSION, printJavaVersion());
			appDataJsonObject.put(Constants.TOMCATVERSION, printtomcatVersion());
			appDataJsonObject.put(Constants.OSNAME, printOSName());
			appDataJsonObject.put(Constants.LOCALHOST, printlocalHost());
			appDataJsonObject.put(Constants.APPNAME, appName);
			appDataJsonObject.put(Constants.APPVERSION, appVersion);
			appDataJsonObject.put(Constants.CURRENTVALUE, printActiveEnvironment());
			data.add(appDataJsonObject);
		} catch (UnknownHostException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return data;
	}

	public JSONObject adminInsertData(JSONObject adminDetails) {
		JSONObject dataOutput = new JSONObject();
		admin_entity = new AdminEntity();
		admin_entity.setName(adminDetails.get(Constants.NAME).toString());
		admin_entity.setEmail(adminDetails.get(Constants.EMAIL).toString());
		admin_entity.setAdminLevel(adminDetails.get(Constants.ADMIN_LEVEL).toString());
		admin_entity.setActive(true);
		// TODO below line requires
//		userDetails.setPassword(encryptDecrypt.encrypt(randomKey.randomPass(8), datakey) randomKey.randomPass(8));
		admin_entity.setPassword(randomKey.randomPass(8));
		admin_entity = admin_repo.save(admin_entity);

		dataOutput = adminJSONArray();
		return dataOutput;	

	}

	@SuppressWarnings("unchecked")
	private JSONObject adminJSONArray() {
		JSONObject adminDataObject = new JSONObject();
		adminDataObject.put(Constants.EMAIL, admin_entity.getEmail());
//		dataOutput.put(Constants.USERID, userEntity.getUserId());
		adminDataObject.put(Constants.ID, admin_entity.getId());
		adminDataObject.put(Constants.NAME, admin_entity.getName());
		adminDataObject.put(Constants.ADMINLEVEL, admin_entity.getAdminLevel());
		adminDataObject.put(Constants.ACTIVE, admin_entity.isActive());
		return adminDataObject;

	}

	public JSONObject saveAdminDetails(String email) {
		JSONObject adminData = null;
		admin_entity = adminRepository.findByEmail(email);
		if (admin_entity != null) {
			adminData = adminJSONArray();
		}
		return adminData;
	}

	public AdminEntity updateAdminDetails(int id, JSONObject adminDetails) {
		AdminEntity configurationEntity = admin_repo.findById(id);
		if (configurationEntity == null) {
			return null;
		} else {
			AdminEntity ce = configurationEntity;
			configurationEntity.setName(adminDetails.get(Constants.NAME).toString());
			configurationEntity.setEmail(adminDetails.get(Constants.EMAIL).toString());
			configurationEntity.setAdminLevel(adminDetails.get(Constants.ADMIN_LEVEL).toString());
			AdminEntity cer = admin_repo.save(configurationEntity);
			return cer;
		}
	}

	@SuppressWarnings("unused")
	@Transactional
	public boolean deleteAdminDetails(int id) {
		int optionalEntity = admin_repo.deleteById(id);
//		int optionalEntity = admin_repo.deleteByEmail("saikumar1.chedrupu@ideabytes.com");
		System.out.println("optionalEntity: " + optionalEntity);
		if (optionalEntity != 0) {
			return true;
		} else {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public JSONObject adminSign(String email, String password) {
		JSONObject adminData = null;
		AdminEntity adminEntity = adminRepository.findByEmailAndPassword(email, password);
		System.out.println("adminEntity: "+adminEntity);
		if (adminEntity != null) {
			adminData = new JSONObject();
			adminData.put(Constants.ID, adminEntity.getId());
			adminData.put(Constants.NAME, adminEntity.getName());
			adminData.put(Constants.EMAIL, adminEntity.getEmail());
			adminData.put(Constants.PASSWORD, adminEntity.getPassword());
			adminData.put(Constants.ADMINLEVEL, adminEntity.getAdminLevel());
			adminData.put(Constants.MODIFIED, adminEntity.getModified());
			adminData.put(Constants.CREATED, adminEntity.getCreated());
		}
		return adminData;
	}

}
