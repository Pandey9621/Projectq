///*********************** Ideabytes Software India Pvt Ltd *********************                                   
//* Here,EncryptionAndDecryptionText class is using  for encryptng and decrypting text .
//* @author  Chandan Pandey
//* @version 20.0.1
//* @since  2023-08-08.
//*/
//package com.ideabytes.controller;
//
//import java.io.File;
//
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.Key;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.util.Base64;
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.KeyGenerator;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
////import com.ideabytes.binding.UsersEntity;
//import com.ideabytes.mappingAPIs.Mapper;
//
//@RestController
//@CrossOrigin(Mapper.ANGULARAPI)
//public class EncryptionAndDecryptionText {
//
//	private static final Logger log = LogManager.getLogger(EncryptionAndDecryptionText.class);
//
//
//	/**
//	 * Here,encryptData method is declare for encrypting data or text.
//	 * 
//	 * @RequestBody is the used for getting the data from request body.
//	 * @return type is void.
//	 */
//	@PostMapping("api/encrypt")
//	public void encryptData(@RequestBody String data) throws Exception {
//
//		JSONParser parser = new JSONParser();
//		JSONObject json_object = (JSONObject) parser.parse(data);
//		String text = json_object.get("text").toString();
////        System.out.println("text"+text);
//		SecretKey secretKey = generateKey(128);// method calling
//		IvParameterSpec ivParameterSpec = generateIv();
//		String algorithm = "AES/CBC/PKCS5Padding";
//		String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//		// method calling
//		String cipherText = encrypt(algorithm, text, secretKey, ivParameterSpec);
//
//	}
//
//	// method declaration for encrypt the text.
//	public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv)
//			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
//			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//		Cipher cipher = Cipher.getInstance(algorithm);
//		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
//		byte[] cipherText = cipher.doFinal(input.getBytes());
//		return Base64.getEncoder().encodeToString(cipherText);
//	}
//
//	// method declaration for decrypt the encrypted text.
//	public static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv)
//			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
//			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//		Cipher cipher = Cipher.getInstance(algorithm);
//		cipher.init(Cipher.DECRYPT_MODE, key, iv);
//		byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(new String(cipherText)));
//		return new String(plainText);
//	}
//
////generateIV method is declared for generating the IV parameter.
//	public static IvParameterSpec generateIv() {
//		byte[] iv = new byte[16];
//		return new IvParameterSpec(iv);
//	}
//
//	public static String generateRandomKey() {
//		// Define the length of the key in bytes (16 bytes for AES-128, 32 bytes for
//		// AES-256)
//		int keyLengthBytes = 32;
//
//		// Generate a random key
//		SecureRandom secureRandom = new SecureRandom();
//		byte[] keyBytes = new byte[keyLengthBytes];
//		secureRandom.nextBytes(keyBytes);
//
//		// Convert the byte array to a hexadecimal string representation
//		StringBuilder sb = new StringBuilder();
//		for (byte b : keyBytes) {
//			sb.append(String.format("%02x", b));
//		}
//		return sb.toString();
//
//	}
//
////Here,generate key method is declare for generating the randomly secret key.
//	public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
//		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//		keyGenerator.init(n);
//		SecretKey key = keyGenerator.generateKey();
//		return key;
//	}
//
//	@PostMapping("api/decrypt")
//	/**
//	 * Here,api decryptData method is declare for decrypting data or text.
//	 * 
//	 * @RequestBody is the used for getting the data from request body.
//	 * @return type is void.
//	 */
//	public void decryptData(@RequestBody String data) throws Exception {
//
//		JSONParser parser = new JSONParser();
//		JSONObject js = (JSONObject) parser.parse(data);
//
//		String encodedKey = js.get("key").toString();
//		// decode the base64 encoded string
//		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
//		System.out.println("decodedKey " + decodedKey.toString());
//		// rebuild key using SecretKeySpec
//		SecretKey encryptionKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
//		System.out.println("Enter the key:" + encryptionKey);
//		// Generate a 16-byte IV
//		IvParameterSpec iv = generateIv();
//		String algorithm = "AES/CBC/PKCS5Padding";
//		String cipherText = js.get("text").toString();
//		String orgText = decrypt(algorithm, cipherText, encryptionKey, iv);
//		// String encryptedData = decrypt(data.getString("text"), encryptionKey, iv);
//		System.out.println("plainText:" + orgText);
//	}
//}