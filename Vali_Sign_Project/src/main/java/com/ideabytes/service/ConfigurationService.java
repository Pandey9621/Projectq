package com.ideabytes.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideabytes.binding.ConfigurationEntity;
import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.commonService.EncryptAndDecrypt;
import com.ideabytes.commonService.RandomKey;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.repository.ConfigureRepository;
import com.ideabytes.repository.DeviceRepository;
import com.ideabytes.service.implementations.DatabaseServicesImpl;
import com.ideabytes.service.implementations.EmailServiceImpl;
import jakarta.mail.MessagingException;

@Service
public class ConfigurationService {
	private static final Logger log = LogManager.getLogger(ConfigurationService.class);

	DatabaseServicesImpl db;
	EmailServiceImpl mailService;
	ConfigureRepository configureRepository;
	DeviceRepository deviceRepository;
	AuthService authService;
	String secondDataKey;
	String deviceIdIdentifier;
	EncryptAndDecrypt encryptDecrypt;
	RandomKey randomKey;
	ConfigurationEntity configEntity;

	@Value("${mailCC.admin}")
	String adminMailCC;

	@Autowired
	ConfigurationService(DatabaseServicesImpl db, EmailServiceImpl mailService, ConfigureRepository configureRepository,
			DeviceRepository deviceRepository, AuthService authService, EncryptAndDecrypt encryptDecrypt,
			RandomKey randomKey) {
		this.db = db;
		this.mailService = mailService;
		this.configureRepository = configureRepository;
		this.deviceRepository = deviceRepository;
		this.authService = authService;
		this.encryptDecrypt = encryptDecrypt;
		this.randomKey = randomKey;
	}

	JSONObject jsonObject = null;
//	String[] keys = { "C4aweNPXb0Unvm5yFjtlVpcuhVZsndiY", "T5$okm.$DgZk2Ub.6AJZs%/Cn5pPvRCu",
//			"Qy2fAMzYFZfqZzkcelcafohr%otYx^kx", ".rkZG^qlBiNc&Zm4byRdXgi8ekvwlLbW", "RsUQ&talPqu7C^tRHA&qyE$0Fg^RhK57",
//			"xX.u42bQ6HoamUc8OBXqLT9fhNy&a8b1", ".ZC.1a4ZmQfVCOYKD9OANKI9SbgEGXUt", "SdEoDJepnzz61zjwKuDyB9&.Q6Se3$w.",
//			"D/2FC5N6PVdd&g1QAC$CDl.4OHunESkZ", "gYF%z07%ekh./P/X.e0LZ4kMHj14BY.o" };
//
//	String iv = "e93jGXDcjXPbSOAE";

	public boolean checkVersion(String version, String device_type) {
		try {
			String keystring = (device_type.equals(Constants.ANDROID_STRING) ? Constants.IOS_APP_KEYSTRING
					: Constants.ANDROID_APP_KEYSTRING);
			// run this query and store the value..
			ConfigurationEntity config = configureRepository.findByCkey(keystring);
			String versionInDB = config.getCvalue(); // SELECT `value` FROM vali_sign.configurations where `key`=?;
			log.info("version from database:" + versionInDB);
			if (version.equals(versionInDB)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public JSONObject parseAndValidateData(String data) throws Exception {

		String keystring = Constants.CHECK_PHRASE;
		ConfigurationEntity config = configureRepository.findByCkey(keystring);
		String checkPhraseDB = config.getCvalue();// SELECT `value` FROM vali_sign.configurations where `key`=?;
		String actualEncryptionkey = "";
		JSONObject originalData = null;
		for (String key : Constants.KEYS) {
			try {
				String decryptedText = this.encryptDecrypt.decrypt(data, key);
				JSONParser parser = new JSONParser();
				originalData = (JSONObject) parser.parse(decryptedText);
				if (originalData != null) {
					String checkPhraseData = originalData.get("checkPhrase").toString();
					if (checkPhraseData.equals(checkPhraseDB)) {
						System.out.println("Found key:" + key);
						actualEncryptionkey = key;
						break;
					} else {
						System.out.println("Not matched:" + key);
					}
				} else {
					System.out.println("No org data");
				}
			} catch (Exception e) {
				log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
				System.out.println("Not matched:" + key);
			}
		}

		System.out.println("actualEncryptionkey:" + actualEncryptionkey);

		// checkDevice()
		if (actualEncryptionkey == null || actualEncryptionkey.equals("")) {
			// no proper key found for the given data encryption
			return null;
		}

		System.out.println("originalData after decrypting configure: " + originalData);
		String firstDataKey = originalData.get(Constants.DATAKEY_SMALLK).toString();

		System.out.println("first data key decrypted form: " + firstDataKey);
		// update in device table
		// secondDataKey which generated randomly
		secondDataKey = encryptDecrypt.dataKey();
		System.out.println("secondDataKey " + secondDataKey);
		String encryptedSecondDataKey = this.encryptDecrypt.encrypt(secondDataKey, firstDataKey);
		System.out.println("This is response for configure " + encryptedSecondDataKey);
		// randomly generated deviceIdIdentifier for device.
		deviceIdIdentifier = randomKey.randomPass(8);
		JSONObject combinedDataJson = new JSONObject();
		combinedDataJson.put(Constants.DATAKEY, secondDataKey);
		combinedDataJson.put(Constants.IDENTIFIER, deviceIdIdentifier);
		System.out.println("combinedDataJson.toString(): " + combinedDataJson.toString());
		String encryptedPayload = this.encryptDecrypt.encrypt(combinedDataJson.toString(), firstDataKey);

		// update in device table
		JSONObject resp = new JSONObject();
		resp.put(Constants.DATA, encryptedPayload);
		resp.put(Constants.DEVICEID, originalData.get(Constants.DEVICEID));
		resp.put(Constants.IPADDRESS, originalData.get(Constants.DEVICEIP));
		resp.put(Constants.LOCATION, originalData.get(Constants.DEVICELOCATION).toString());
		resp.put(Constants.DEVICETYPEKEY, originalData.get(Constants.DEVICETYPEKEY));
		return resp;
	}

	public DeviceEntity checkDevice(JSONObject parsedData) {
		String deviceId = parsedData.get(Constants.DEVICEID) == null ? null
				: parsedData.get(Constants.DEVICEID).toString();
		DeviceEntity device = null;
		if (deviceId != null) {
			device = deviceRepository.findByDeviceId(deviceId);
		}
		return device;
	}

	public int sendNotification(int deviceId)
			throws FileNotFoundException, IOException, ParseException, URISyntaxException, MessagingException {
		JSONArray users = this.db.getUsersForDevice(deviceId);
		System.out.println("users details: " + users);
		if (users == null || users.size() == 0) {
			return 0;
		} else {
			JSONObject user = (JSONObject) users.get(0);
			String userEmail = user.get(Constants.EMAIL).toString();
			String userName = user.get(Constants.USERNAMECAPN).toString();
			String updateLink = "https://valisign.com/updateDevice/12345678";
			String mailSubject = "ValiSign - Device upgrade notification";
			String mailBody = "Hi " + userName
					+ ", <br> Please click on the below link to update your device otherwise just ignore this mail. <br> "
					+ updateLink + "<br><br> <p>Regards,<br>ValiSign Admin</p>";
			String[] mailList = { userEmail };
//			String[] mailList = { "saikumar.chedrupu@ideabytes.com" };
			String[] bccList = {};
			String[] ccList = { this.adminMailCC };
			this.mailService.sendHTMLMessage(mailList, mailSubject, mailBody, bccList, ccList);
			return 1;
		}
	}

	public int registerDevice(JSONObject parsedData) {
		// TODO Auto-generated method stub
		int deviceVal = 0;
		try {
			String deviceId = parsedData.get(Constants.DEVICEID) == null ? null
					: parsedData.get(Constants.DEVICEID).toString();
			boolean value = false;
			DeviceEntity checkDeviceData = this.deviceRepository.findByDeviceId(deviceId);
			if (checkDeviceData != null) {
				checkDeviceData.setDeviceId(deviceId);
				checkDeviceData.setIpAddress(parsedData.get(Constants.IPADDRESS) == null ? null
						: parsedData.get(Constants.IPADDRESS).toString());
				checkDeviceData.setLocation(parsedData.get(Constants.LOCATION) == null ? null
						: parsedData.get(Constants.LOCATION).toString());
				checkDeviceData.setDataKey(secondDataKey);
				checkDeviceData.setIdentifier(deviceIdIdentifier);
				checkDeviceData.setType(parsedData.get(Constants.DEVICETYPEKEY) == null ? null
						: parsedData.get(Constants.DEVICETYPEKEY).toString());
				checkDeviceData.setActive(true);
				checkDeviceData = deviceRepository.save(checkDeviceData);

				System.out.println("check device data in DB: " + checkDeviceData.toString());
				deviceVal = 2;
			} else {
				System.out.println("parsedData object: " + parsedData + "\n " + "secondDataKey: " + secondDataKey
						+ " \n deviceIdIdentifier" + deviceIdIdentifier);
				DeviceEntity device = new DeviceEntity();
				device.setDeviceId(deviceId);
				device.setIpAddress(parsedData.get(Constants.IPADDRESS) == null ? null
						: parsedData.get(Constants.IPADDRESS).toString());
				device.setLocation(parsedData.get(Constants.LOCATION) == null ? null
						: parsedData.get(Constants.LOCATION).toString());
				device.setDataKey(secondDataKey);
				device.setIdentifier(deviceIdIdentifier);
				device.setType(parsedData.get(Constants.DEVICETYPEKEY) == null ? null
						: parsedData.get(Constants.DEVICETYPEKEY).toString());
				device.setActive(true);
				checkDeviceData = deviceRepository.save(device);
				deviceVal = 1;
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return deviceVal;

	}

	public ConfigurationEntity addConfigData(JSONObject configDetails) {
		// converting into string data not insert without convert data insert.
		configEntity = new ConfigurationEntity();
		configEntity.setName(configDetails.get(Constants.NAME).toString());
		configEntity.setCkey(configDetails.get(Constants.CKEY).toString());
		configEntity.setCvalue(configDetails.get(Constants.CVALUE).toString());
		configEntity.setActive(true);
		ConfigurationEntity configurationEntity = configureRepository.save(configEntity);
		return configurationEntity;
	}

	public ConfigurationEntity updateConfigurationDetails(int id, JSONObject configurationDetails) {
		ConfigurationEntity configurationEntity = configureRepository.findById(id);
		System.out.println("configurationEntity: "+configurationEntity.toString());
		if (configurationEntity == null) {
			return null;
		} else {
			ConfigurationEntity configEntity = new ConfigurationEntity();
			configEntity.setId(id);
			configEntity.setCkey(configurationDetails.get(Constants.CKEY).toString());
			configEntity.setCvalue(configurationDetails.get(Constants.CVALUE).toString());
			configEntity.setName(configurationDetails.get(Constants.NAME).toString());
			configEntity.setActive(true);
			ConfigurationEntity cer = configureRepository.save(configEntity);
			return cer;
		}
	}

	@SuppressWarnings("unused")
	@Transactional
	public boolean deleteConfigDetails(int id) {
		int optionalEntity = configureRepository.deleteById(id);
		System.out.println("optionalEntity: "+optionalEntity);
		if (optionalEntity!= 0) {
			return true;
		} else {
			return false;
		}

	}
}
