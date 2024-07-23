package com.ideabytes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ideabytes.binding.DeviceEntity;

@Repository
public interface DeviceRepository extends CrudRepository<DeviceEntity, Integer> {
//  @Query( value="select * from devices where device_id=?",nativeQuery =true)
	DeviceEntity findByDeviceId(String device_id);
	int findById(int device_id);

	DeviceEntity findByIdentifier(String identified);
//	
	

}
