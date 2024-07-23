package com.ideabytes.rsaAlgorithm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RSAAlgo {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		// Generate RSA key pair
		KeyPair keyPair = generateKeyPair();

		System.out.println("keyPair: "+keyPair);
		
		// Example message
		String message = "Hello, RSA encryption!";
		JSONArray data = new JSONArray();
		JSONObject object = new JSONObject();
		object.put("name", "saikumar");
		object.put("password", "@saikumar@123");
		data.add(object);

		System.out.println("public key: "+keyPair.getPublic()+" private key: "+keyPair.getPrivate());
		PublicKey dd = keyPair.getPublic();
		PrivateKey ss = keyPair.getPrivate();
		
		System.out.println("public key: "+dd+" private key: "+ss);

		// Encryption
		byte[] ciphertext = encrypt(data.toString(), keyPair.getPublic());

		// Decryption
		String decryptedMessage = decrypt(ciphertext, keyPair.getPrivate());

		System.out.println("Original message: " + data+"   " + ciphertext);
		System.out.println("Decrypted message: " + decryptedMessage);
	}

//	public static KeyPair generateKeyPair() throws Exception {
//		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
//		keyPairGenerator.initialize(2048);
//		return keyPairGenerator.generateKeyPair();
//	}
	  public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
	        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	        keyPairGenerator.initialize(2048); // You can adjust the key size as needed (2048 bits is a common choice)
	        return keyPairGenerator.generateKeyPair();
	    }
	public static byte[] encrypt(String message, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(message.getBytes());
	}

	public static String decrypt(byte[] ciphertext, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedBytes = cipher.doFinal(ciphertext);
		return new String(decryptedBytes);
	}
}
