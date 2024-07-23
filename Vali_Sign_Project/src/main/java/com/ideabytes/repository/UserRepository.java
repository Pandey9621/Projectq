package com.ideabytes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.response.UserApplicationDevice;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	public UserEntity findByUserId(String id);

	public UserEntity findByNameAndPassword(String name, String password);

	public UserEntity findByEmailAndPhone(String email, String phone);

	int deleteByUserId(String userId);
    
	public UserEntity findByEmailAndPassword(String email, String password);
    
	public UserEntity findByEmail(String email);


//   @Query("SELECT new com.example.UserApplicationDevice(user_applications.user_id, user_applications.app_id, devices_users.device_id) FROM UserApplication user_applications INNER JOIN user_applications.devices users_devices")
  
}
