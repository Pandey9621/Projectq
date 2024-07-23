package com.ideabytes.repository;

import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideabytes.binding.ClientEntity;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Integer> {
	public ClientEntity findByClientId(String client_id);

	public ClientEntity findByClientIdAndClientSecret(String clientId, String clientSecret);

	public ClientEntity findByEmailAndPhone(String email, String phone);
	public ClientEntity findByName(String app);
	
	@Query(value="SELECT client_id,client_secret,name,email,phone,app_id from clients inner join user_applications on clients.id=:id",nativeQuery=true)
	public Set getJoinInformations(int id);
}
