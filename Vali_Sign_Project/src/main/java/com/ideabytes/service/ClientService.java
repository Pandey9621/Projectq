package com.ideabytes.service;

import com.ideabytes.binding.ClientEntity;
import com.ideabytes.binding.UserApplicationEntity;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor.OptimalPropertyAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideabytes.binding.ClientEntity;
import com.ideabytes.repository.ClientRepository;
import com.ideabytes.repository.UserApplicationsRepository;

@Service
public class ClientService {
	ClientRepository clientRepo;
	JSONObject jsonObject = null;
	ClientEntity entity = null;
	UserApplicationsRepository userAppRepo;
	private static final Logger log = LogManager.getLogger(ClientService.class);

	@Autowired
	ClientService(UserApplicationsRepository userAppRepo, ClientRepository clientRepo) {
		this.userAppRepo = userAppRepo;
		this.clientRepo = clientRepo;
	}

	/**
	 * This method updateClient is declaring for updating the client details based
	 * on their id.
	 * 
	 * @param client_id and clientDetails accepting as a parameter.
	 * @return type is ClientEntity.
	 */
	public ClientEntity updateClient(String client_id, JSONObject clientDetails) {
		try {
			ClientEntity entity = clientRepo.findByClientId(client_id);
			if (entity != null) {
				entity.setName(clientDetails.get(Constants.NAME).toString());
				entity.setEmail(clientDetails.get(Constants.EMAIL).toString());
				entity.setPhone(clientDetails.get(Constants.PHONE).toString());
//			entity.setEmail(clientDetails.get(Constants.PRIMARYPHONE).toString());
//			entity.setPhone(clientDetails.get(Constants.ADDRESS).toString());
				return clientRepo.save(entity);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * This method deleteClient is declaring for deleting the client details based
	 * on their id.
	 * 
	 * @param client_id accepting as a parameter.
	 * @return type is boolean.
	 */
	@SuppressWarnings("unused")
	@Transactional
	public boolean deleteClient(String client_id) {
		try {
			ClientEntity optionalEntity = clientRepo.findByClientId(client_id);
			System.out.println("client_id: " + optionalEntity);
			if (optionalEntity == null) {
				return false;
			} else {
				int userAppEntity = userAppRepo.deleteByAppId(optionalEntity.getId());
				log.debug("userAppEntity: deleted: " + userAppEntity);
				clientRepo.delete(optionalEntity);
				return true;
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method getByClientId is declaring for getting the client details based
	 * on their id.
	 * 
	 * @param client_id accepting as a parameter.
	 * @return type is ClientEntity.
	 */
	@SuppressWarnings("unused")
	public ClientEntity getByClientId(String client_id) {
		ClientEntity optionalEntity = null;
		try {
			optionalEntity = clientRepo.findByClientId(client_id);
			if (optionalEntity == null) {
				return null;
			} else {
				return optionalEntity;
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	public List<ClientEntity> getAllClients() {
        return (List<ClientEntity>) clientRepo.findAll();
    }
}
