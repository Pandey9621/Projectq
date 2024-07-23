//package com.ideabytes.commonService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.ideabytes.binding.ClientEntity;
//import com.ideabytes.repository.ClientRepository;
//
//public class ValidateClientIdAndSecretKey {
//
//	@Autowired
//	ClientRepository client_repository;
//	
//	public ClientEntity validateClientIdAndSecretKey(String clientId, String clientSecretKey) {
//		ClientEntity client_entity = client_repository.findByClientIdAndClientSecret(clientId, clientSecretKey);
//	
//	return client_entity;
//	}
//}
