package test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import com.ideabytes.commonService.EncryptAndDecrypt;
import com.ideabytes.constants.Constants;

import java.security.*;
import java.util.Base64;

public class LongDataEncryption {

	public static String encryptString(String input, String secretKey) throws Exception {
		try {
			// Convert the secret key string to a byte array
			byte[] decodedKey = Base64.getDecoder().decode(secretKey);
			SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");

			// Initialize the AES cipher for encryption
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			// Encrypt the input string
			byte[] encryptedBytes = cipher.doFinal(input.getBytes());

			// Encode the encrypted bytes as a Base64 string
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			throw new Exception("Encryption failed: " + e.getMessage());
		}
	}

	public static String decryptString(String encryptedInput, String secretKey) throws Exception {
		try {
			// Convert the secret key string to a byte array
			byte[] decodedKey = Base64.getDecoder().decode(secretKey);
			SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");

			// Initialize the AES cipher for decryption
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

			// Decode the Base64-encoded input and decrypt it
			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedInput));

			// Convert the decrypted bytes to a string
			return new String(decryptedBytes);
		} catch (Exception e) {
			throw new Exception("Decryption failed: " + e.getMessage());
		}
	}

//	public static void main(String[] args) throws Exception {
//		EncryptAndDecrypt en = new EncryptAndDecrypt();
//		String input = "";
//		String encryptAes = en.encrypt(input, Constants.aesRandom32Key);
//		System.out.println("encryptAes: " + encryptAes);
//		String decryptData = en.decrypt(encryptAes, Constants.aesRandom32Key);
//		System.out.println("decryptData: " + decryptData);
//	}
}
