package com.ideabytes.commonService;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.controller.SignIn;

@Service
public class EncryptAndDecrypt {
	private static final Logger log = LogManager.getLogger(EncryptAndDecrypt.class);


	public String encrypt(String plainText, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(Constants.AESCBCPKCS5PADDING);
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), Constants.AES);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(Constants.IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public String decrypt(String encryptedText, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(Constants.AESCBCPKCS5PADDING);
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), Constants.AES);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(Constants.IV.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
		return new String(decryptedBytes);
	}

	public static String generateAESIV() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[8];
		secureRandom.nextBytes(key);
		StringBuilder keyHex = new StringBuilder();
		for (byte b : key) {
			keyHex.append(String.format("%02x", b));
		}
		return keyHex.toString();
	}

	public String dataKey() {
		String encodedKey = null;
		try {
			String algorithm = Constants.AES; // Data Encryption Standard
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			int keySize = 192;
			SecureRandom secureRandom = new SecureRandom();
			keyGenerator.init(keySize, secureRandom);
			SecretKey encryptionKey = keyGenerator.generateKey();
			encodedKey = Base64.getEncoder().encodeToString(encryptionKey.getEncoded());
			return encodedKey;
		} catch (NoSuchAlgorithmException e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return encodedKey;
	}

	public static IvParameterSpec generateIv() {
		byte[] iv = new byte[16];
		return new IvParameterSpec(iv);
	}

//	public static void main(String[] args) throws Exception {
//		EncryptAndDecrypt dd = new EncryptAndDecrypt();
//		String input = "{\"username\":\"saikum12\",\"password\":\"RD79L4fv\",\"phone\":\"98765432\"}";
//		System.out.println("input: " + input);
//		String encrypt = dd.encrypt(input, Constants.clientDataKey, Constants.clientIV);
//		System.out.println("encrypt: " + encrypt);
//
//	}
}
