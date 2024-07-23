/* 
 * Name: DevicesUsersRepository.java
 * Project: ValiSign
 * Version: 0.2.0
 * Description:Repository interface for managing {@link DevicesUsersEntit} entities.
 * 
 * Created Date: 2023-08-11
 * Developed By: Chandan Pandey
 * Modified Date: 2023-08-11
 * Modified By: Chandan Pandey
 * 
 */
package com.ideabytes.repository;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.binding.DevicesUsersEntity;

@Repository
public interface DevicesUsersRepository extends CrudRepository<DevicesUsersEntity, Integer> {
	DevicesUsersEntity findByUserIdAndDeviceId(int userId, int deviceId);

	DevicesUsersEntity findByUserId(Integer id);
	public Integer findByDeviceId(int id);
//	 List<DevicesUsersEntity>findById(int id);
//	@Query(value ="SELECT devices.device_id, devices.data_key " +
//			  "FROM devices "+
//			  "INNER JOIN devices_users ON devices.id =devices_users.id", nativeQuery = true)
//			      List findById();
	@Query(value ="SELECT devices.id,devices.device_id,name,type,ip_address,location,user_id FROM devices inner join devices_users on devices.id=:id",nativeQuery = true)
     JSONObject getJoinInfo(int id);

} 
