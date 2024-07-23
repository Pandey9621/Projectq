package com.ideabytes.commonService;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.ideabytes.binding.ClientEntity;


@Service
public class AllServices {

	public String getAuthorization(String authorization) {
		return authorization.split(" ")[1];
	}
//	public HttpHeaders corsHeader(){
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Access-Control-Allow-Origin", "*");
//		return headers;
//	}
	
}
