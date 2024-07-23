package com.ideabytes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.ClientEntity;
import com.ideabytes.commonService.AllServices;
import com.ideabytes.commonService.GetLang;
import com.ideabytes.constants.ApiStatusConstants;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.ClientRepository;
import com.ideabytes.service.APIStatusCodes;
import com.ideabytes.service.ClientService;

@RestController
//@CrossOrigin(origins = Mapper.ANGULARAPI)
public class ClientController extends ApiStatusConstants {
	private static final Logger log = LogManager.getLogger(ClientController.class);
	@Autowired
	ClientRepository client_repository;
	@Autowired
	ClientService clients_service;
	JSONObject json_object = null;
	ClientEntity clients_entity;
	@Autowired
	APIStatusCodes apiStatusCodes;
	@Autowired
	AllServices commonServices;

	/**
	 * This method getClients is declared for the registration of users.
	 * 
	 * @RequestBody is the used for getting the users data from request body.
	 * @return type is ResponseEntity.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.READCLIENTS, method = RequestMethod.GET)
	public ResponseEntity<?> getAllClients(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang) {
		Map<String, Object> response = new HashMap<>();
		try {

			List<ClientEntity> clients_details = clients_service.getAllClients();
			if (clients_details != null) {
				response = apiStatusCodes.responseData(statusCodeNameForReadClient, IBVS_READCLIENT_SUCCESS, lang);
				response.put(Constants.DATA, clients_details);
				log.info("getAllClients data response: " + clients_details);
			} else {
				response = apiStatusCodes.responseData(statusCodeNameForReadClient, IBVS_READCLIENT_001, lang);
			}
		} catch (Exception e) {
			log.error(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Access-Control-Allow-Origin", "*");
//		headers.set("X-Debug-Value", "2");
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(headers).body(response);
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
	}

	/**
	 * This method getByClientId is declared for the find client details based on
	 * their id.
	 * 
	 * @PathVariable is the used as parameter for getting the client id data from
	 *               the url path.
	 * @return type is ResponseEntity.
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = Mapper.FETCHCLENT, method = RequestMethod.GET)
	public ResponseEntity<?> getClientsByClientId(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable String client_id) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (!client_id.isEmpty()) {
				clients_entity = clients_service.getByClientId(client_id);
				if (clients_entity != null) {
					response = apiStatusCodes.responseData(statusCodeNameForClientsById, IBVS_CLIENTSBYID_SUCCESS,
							lang);
					response.put(Constants.DATA, clients_entity);
				} else {
					response = apiStatusCodes.responseData(statusCodeNameForClientsById, IBVS_CLIENTSBYID_001, lang);
				}
			} else {
				response = apiStatusCodes.responseData(statusCodeNameForClientsById, IBVS_CLIENTSBYID_002, lang);
			}
		} catch (Exception e) {
			log.fatal("Exception got in getByClientId: " + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Access-Control-Allow-Origin", "*");
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(headers).body(response);

	}

	// end of findUsersByFilter method.

	/**
	 * This method updateByClientId is declared for the update client details based
	 * on their id.
	 * 
	 * @PathVariable is the used as parameter for getting the client id data from
	 *               the url path.
	 * @return type is ResponseEntity.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.UPDATECLIENT, method = RequestMethod.PUT)
	public ResponseEntity<?> updateByClientId(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable String client_id, @RequestBody JSONObject input) {
		Map<String, Object> response = new HashMap<>();
		clients_entity = clients_service.updateClient(client_id, input);
		if (clients_entity!= null) {

			response = apiStatusCodes.responseData(statusCodeNameForClientUpdate, IBVS_CLIENTUPDATE_SUCCESS, lang);
			response.put(Constants.DATA, clients_entity);
		} else {
			response = apiStatusCodes.responseData(statusCodeNameForClientUpdate, IBVS_CLIENTUPDATE_001, lang);
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(commonServices.corsHeader())
//				.body(response);

	}

	/**
	 * This method deleteByClientId is declared for the deleting client details
	 * based on their id.
	 * 
	 * @PathVariable is the used as parameter for getting the client id data from
	 *               the url path.
	 * @return type is ResponseEntity.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.DELETECLIENT, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteByClientId(
			@RequestHeader(value = Constants.LANGUAGE, defaultValue = Constants.ENGLISH) String lang,
			@PathVariable String client_id) {
		System.out.println(client_id);
		Map<String, Object> response = new HashMap<>();

		try {
			if (clients_service.deleteClient(client_id)) {
				response = apiStatusCodes.responseData(statusCodeNameForClientDelete, IBVS_CLIENTDELETE_SUCCESS, lang);
			} else {
				response = apiStatusCodes.responseData(statusCodeNameForClientDelete, IBVS_CLIENTDELETE_001, lang);
			}
		} catch (Exception e) {
			log.fatal("Exception got in deleteByClientId: " + e.getMessage());
			e.printStackTrace();
		}
		int httpStatusCode = (int) ((long) response.get(Constants.STATUS));
		return new ResponseEntity<>(response, HttpStatus.valueOf(httpStatusCode));
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Access-Control-Allow-Origin", "*");
//		return ResponseEntity.status(HttpStatus.valueOf(httpStatusCode)).headers(headers).body(response);

	}

	@RequestMapping(value = "sampleGetData", method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public String getData() {
		return "Data from the server";
	}
}
