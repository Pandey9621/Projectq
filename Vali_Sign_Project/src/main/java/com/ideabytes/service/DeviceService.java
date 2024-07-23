package com.ideabytes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.repository.DeviceRepository;

@Service
public class DeviceService {
	@Autowired
	DeviceRepository devicesRepository;

	/**
	 * This method updateDevice is using for updating the device details along with
	 * data base.
	 * 
	 * @String udid andcdevicesDetails is accepting as parameter.
	 * @return type is DevicesEntity.
	 */
	public DeviceEntity updateDevice(String udid, DeviceEntity devicesDetails) {
		try {
			DeviceEntity entity = devicesRepository.findByDeviceId(udid);
			if (entity == null) {
				System.out.println(entity);
				return null;
			} else {
				String name = devicesDetails.getName();
				entity.setName(name);
				return devicesRepository.save(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
