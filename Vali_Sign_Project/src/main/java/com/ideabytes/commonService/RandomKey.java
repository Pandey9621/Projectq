package com.ideabytes.commonService;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ideabytes.constants.Constants;

@Service
public class RandomKey {
	public String randomPass(int size) {
		Random random = new Random();
		String combine = Constants.CHARACTERSFORPASSWORD;
		char[] password = new char[size];
		for (int i = 0; i < size; i++) {
			password[i] = combine.charAt(random.nextInt(combine.length()));
		}
		String randomPassword = new String(password);
		return randomPassword;
	}
	
	public String generateRandomKey(int digit) {
		SecureRandom random = new SecureRandom();
		StringBuilder otpBuilder = new StringBuilder(digit);
		for (int i = 0; i < digit; i++) {
			int index = random.nextInt(Constants.CHARACTERSFOROTP.length());
			otpBuilder.append(Constants.CHARACTERSFOROTP.charAt(index));
		}
		return otpBuilder.toString().toUpperCase();
	}
}
