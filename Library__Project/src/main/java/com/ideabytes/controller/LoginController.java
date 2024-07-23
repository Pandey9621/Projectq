package com.ideabytes.controller;
import java.util.Date;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.constants.Constants;
import com.ideabytes.repository.UserRepository;
//import com.ideabytes.responseentity.SignInResponse;
import com.ideabytes.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@CrossOrigin("http://localhost:4200")
@RestController
public class LoginController {
	
	@Autowired
	public UserRepository up;
	@Autowired
	public UserService us;
	JSONObject jo = null;
	JSONArray ja=null;

	@PostMapping("/auth/signin")
	public Map<String, Object> signIn(@RequestBody UserEntity signInRequest) {
		// Retrieve the username and password from the request body
		String username = signInRequest.getName();
		String password = signInRequest.getPassword();
		// Validate the credentials (e.g., check against a database)
		if (validateCredentials(username, password)) {
			jo = new JSONObject();
			JSONObject jo1=new JSONObject();
			// Credentials are valid
			String token = generateJWT(username);
		
			jo1=us.fetchUserId(password);
			jo.put(Constants.STATUS, HttpStatus.OK);
			jo.put(Constants.MESSAGE, Constants.SIGIN);
			jo.put(Constants.TOKEN, token);
			jo.put(Constants.DATA, jo1);
			
			return jo.toMap();
	

		} else {
			// Credentials are invalid
			jo=new JSONObject();
			return jo.put(Constants.MESSAGE,Constants.USERINVALID).toMap();
		}
	}

	// Function to validate credentials (example implementation)
	private boolean validateCredentials(String username, String password) {
		System.out.println(username);
		System.out.println(password);
		
		Iterable<UserEntity> userEntity = up.findAll();
		Iterator<UserEntity> iterating = userEntity.iterator();
		while (iterating.hasNext()) {
			UserEntity ue2 = (UserEntity) iterating.next();
			if (username.equals(ue2.getName()) && password.equals(ue2.getPassword())) {
			return true;
			}
	}
		return false;

	}

	// Function to generate a JWT token
	private String generateJWT(String username) {
		String token = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + 864000000)) // 10 days
				.signWith(SignatureAlgorithm.HS512, "secretkey")
				.compact();
                return token;

	}
	
}
