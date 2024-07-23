package com.ideabytes.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.internal.build.AllowSysOut;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ideabytes.binding.ClientEntity;
import com.ideabytes.binding.DeviceEntity;
import com.ideabytes.binding.DevicesUsersEntity;
import com.ideabytes.binding.UserApplicationEntity;
import com.ideabytes.repository.ClientRepository;
import com.ideabytes.repository.DeviceRepository;
import com.ideabytes.repository.DevicesUsersRepository;
import com.ideabytes.repository.UserApplicationsRepository;

@Service
public class UserAppService {
	@Autowired
	UserApplicationsRepository uar;
	@Autowired
	ClientRepository client_repository;
	@Autowired
	DevicesUsersRepository devices_users_repo;
	JSONObject js=null;
	@Autowired
	DeviceRepository dr;
Set li=null;
	  public Set getApplicationDetails(int id) {
		  List<UserApplicationEntity> uae=uar.findByUserId(id);
		  for(UserApplicationEntity appData:uae) {
			  if(id==appData.getUserId()) {
			                
          	Optional<ClientEntity>ce=client_repository.findById(appData.getAppId());
          	if(ce.isPresent()) {
              ClientEntity client_entity=ce.get();
              li=client_repository.getJoinInformations(client_entity.getId());
            		  }
            }
			  else {
				  break;
			  }
		  return li;
	  }
		return null;
		  }
		

	  public JSONObject getDeviceDetails(int id) {
		        DevicesUsersEntity due=devices_users_repo.findByUserId(id);
		        if(due!=null) {
//		        System.out.println("124556 "+due.getDeviceId());
		        try {
		        if(due.getDeviceId()!=null) {
		        Optional<DeviceEntity> de=dr.findById(due.getDeviceId());
		        if(de.isPresent()) {
		         DeviceEntity device_entity=de.get();
		         js=devices_users_repo.getJoinInfo(device_entity.getId());
		        
		        }
		        }
		        else {
		    
		        	return null;
		        }
				return js;
		        }
		        catch(Exception e) {
		        	System.out.println(e.getMessage());
		        }
				return js;
		  
		  
	  }
		        else {
		        	System.out.println("AWEWEW");
		        	return null;
		        }
				
}
}