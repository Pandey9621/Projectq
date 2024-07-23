package com.ideabytes.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.*;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.ConfigurationEntity;
import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.constants.ApiStatusConstants;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.ConfigureRepository;
import com.ideabytes.service.APIStatusCodes;
import com.ideabytes.service.ConfigurationService;
import jakarta.mail.MessagingException;

@RestController
//@CrossOrigin(origins= Mapper.ANGULARAPI, exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"}, allowedHeaders = "Requestor-Type")
public class ConfigurationController extends ApiStatusConstants {
	ConfigurationEntity configurationEntity;
	JSONObject parsedData;
    APIStatusCodes apiStatusCodes;
	ConfigureRepository configureRepository;
	ConfigurationService configurationService;
	private static final Logger log = LogManager.getLogger(ConfigurationController.class);

	@Autowired
	ConfigurationController(APIStatusCodes apiStatusCodes, ConfigurationService configurationService,
		ConfigureRepository configureRepository) {
		this.apiStatusCodes = apiStatusCodes;
		this.configurationService = configurationService;
		this.configureRepository = configureRepository;
	}

	@SuppressWarnings({"unchecked","unused" })
	@RequestMapping(value = Mapper.CONFIGUREAPI, method = RequestMethod.POST)
	public ResponseEntity<?> configureApp(@RequestBody JSONObject dataInput,
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang) {
		Map<String, Object> response = new HashMap<>();
		String version = dataInput.get(Constants.VERSION).toString();
		String deviceType = dataInput.get(Constants.DEVICETYPEKEY).toString();
		String data = dataInput.get(Constants.DATA).toString();
		/*
		 * "device_id":"d70d88e5-85e7-4bc3-81b7-9eaec5d1532d", "device_type":"Android",
		 * "ip_address":"192.168.29.237", "location":"city",
		 * "checkPhrase":"Vali Sign Calling"
		 */
		try {
			if (!configurationService.checkVersion(version, deviceType)) {
				// Application version mismatch error
				response = apiStatusCodes.responseData(statusCodeNameForConfig, IBVS_CONF_001, lang);
			} else {
				parsedData = configurationService.parseAndValidateData(data);
				System.out.println("parsedData: " + parsedData);
				log.debug("parsedData: " + parsedData);
				if (parsedData == null) {
					// Encryption or data error
					response = apiStatusCodes.responseData(statusCodeNameForConfig, IBVS_CONF_002, lang);
				} else {
					DeviceEntity device = configurationService.checkDevice(parsedData);
					if (device != null) {
						System.out.println("device detail: " + device.toString());
						log.debug("device detail: " + device.toString());
						log.debug("device id: " + device.getId());
						int notified = configurationService.sendNotification(device.getId());
						if (notified == 0) {
							// No user found for the device
							log.error("No user found for the device Mail not sent");
							int deviceRegistered = configurationService.registerDevice(parsedData);
							response = apiStatusCodes.responseData(statusCodeNameForConfig, IBVS_CONF_003, lang);
							response.put(Constants.DATA, parsedData.get(Constants.DATA));
						} else {
							// sent
							log.info("Mail sent");
							response = apiStatusCodes.responseData(statusCodeNameForConfig, IBVS_CONF_004, lang);
						}
					} else {
						int deviceRegistered = configurationService.registerDevice(parsedData);
						log.debug("deviceRegistered: " + deviceRegistered);
						if (deviceRegistered==1) {
							// error in saving
							log.error("device not registered");
							response = apiStatusCodes.responseData(statusCodeNameForConfig, IBVS_CONF_005, lang);
						} else {
							// Device configured successfully
							log.info("device registered");
							response = apiStatusCodes.responseData(statusCodeNameForConfig, IBVS_CONF_SUCCESS, lang);
							response.put(Constants.DATA, parsedData.get(Constants.DATA));
						}
					}

				}
			}
		} catch (FileNotFoundException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		} catch (URISyntaxException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
	}

	@RequestMapping(value = Mapper.INSERTCONFIGAPI, method = RequestMethod.POST)
	public ResponseEntity<?> insertConfigDetails(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@RequestBody JSONObject configurtionDetails) {
		{
			Map<String, Object> res = new HashMap<>();
			try {
				configurationEntity = configurationService.addConfigData(configurtionDetails);
				System.out.println("config " + configurationEntity);
				if (configurationEntity != null) {
					res = apiStatusCodes.responseData(statusCodeNameForInsertConfig, IBVS_INSERTCONFIG_SUCCESS, lang);
					res.put(Constants.DATA, configurationEntity);
				} else {
					res = apiStatusCodes.responseData(statusCodeNameForInsertConfig, IBVS_INSERTCONFIG_001, lang);
				}
			} catch (Exception e) {
				log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
				e.printStackTrace();
			}
			int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
			return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.READCONFIGAPI, method = RequestMethod.GET)
	public ResponseEntity<?> readsConfigDetails(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang) {
		{
			Map<String, Object> res = new HashMap<>();
			try {
				List<ConfigurationEntity> config_details = (List<ConfigurationEntity>) configureRepository.findAll();
				System.out.println("config_details: " + config_details);
				if (config_details != null) {
					res = apiStatusCodes.responseData(statusCodeNameForReadConfig, IBVS_READCONFIG_SUCCESS, lang);
					res.put(Constants.DATA, config_details);
				} else {
					res = apiStatusCodes.responseData(statusCodeNameForReadConfig, IBVS_READCONFIG_001, lang);
				}
			} catch (Exception e) {
				log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
				e.printStackTrace();
			}
			int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
			return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
		}

	}

	@RequestMapping(value = Mapper.UPDATAECONFIGAPI, method = RequestMethod.PUT)
	public ResponseEntity<?> updateConfigDetails(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable Integer id, @RequestBody JSONObject configDetails) {
		Map<String, Object> res = new HashMap<>();
		configurationEntity = configurationService.updateConfigurationDetails(id, configDetails);
		if (configurationEntity != null) {
			res = apiStatusCodes.responseData(statusCodeNameForUpdateConfig, IBVS_UPDATECONFIG_SUCCESS, lang);
		} else {
			res = apiStatusCodes.responseData(statusCodeNameForUpdateConfig, IBVS_UPDATECONFIG_001, lang);
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}// end of updateByUserId method.

	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.DELETECONFIGAPI, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteByUserId(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable int id) {
		Map<String, Object> res = new HashMap<>();
		try {
			boolean configurationEntity = configurationService.deleteConfigDetails(id);
			if (configurationEntity != false) {
				res = apiStatusCodes.responseData(statusCodeNameForDeleteConfig, IBVS_DELETECONFIG_SUCCESS, lang);
			} else {
				res = apiStatusCodes.responseData(statusCodeNameForDeleteConfig, IBVS_DELETECONFIG_001, lang);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) res.get(Constants.STATUS));
		return new ResponseEntity<>(res, HttpStatus.valueOf(httpStatusCode));
	}

}
