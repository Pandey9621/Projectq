package com.ideabytes.service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.internal.build.AllowSysOut;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ideabytes.binding.ClientEntity;
//import com.ideabytes.binding.ClientEntity2;
import com.ideabytes.binding.UserApplicationEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.commonService.EncryptAndDecrypt;
import com.ideabytes.commonService.RandomKey;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.ClientRepository;
//import com.ideabytes.repository.ClientRepository2;
import com.ideabytes.repository.UserApplicationsRepository;
import com.ideabytes.repository.UserRepository;
import com.ideabytes.service.implementations.EmailServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {

	@Value("${company.ib.jwt.secret}")
	private String secretKey;

	@Value("${domain.url}")
	private String adminAPI;

	@Value("${mailCC.admin}")
	private String adminCCMail;

	@Value("${sample.mailforUserRegister}")
	private String sampleUserInfoMail;
	@Value("${sample.mailforUserRegister1}")
	private String sampleUserInfoMail1;

	@Value("${company.ib.jwt.jwtExpirationInMs}")
	private String jwtExpiration;

	UserEntity user_enEntity;
	ClientEntity client_entity;
	JSONObject jsonObject = null;
	JSONArray js_array = null;

	EmailServiceImpl mailService;
	RandomKey randomKey;
	EncryptAndDecrypt encryptDecrypt;
	UserRepository userRepo;
	ClientRepository clients_repository;
//	ClientRepository2 clients_repository2;
	UserApplicationsRepository userAppRepo;

	private static final Logger log = LogManager.getLogger(AuthService.class);

	@Autowired
	AuthService(EncryptAndDecrypt encryptDecrypt, RandomKey randomKey, EmailServiceImpl mailService,
			UserRepository userRepo,
			ClientRepository clients_repository, /* ClientRepository2 clients_repository2, */
			UserApplicationsRepository userAppRepo) {
		this.encryptDecrypt = encryptDecrypt;
		this.randomKey = randomKey;
		this.mailService = mailService;
		this.userRepo = userRepo;
		this.clients_repository = clients_repository;
//		this.clients_repository2 = clients_repository2;
		this.userAppRepo = userAppRepo;
	}

	public String getDataFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);

	}

	// Function for retrieving any information from token we will need the secret
	// key

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
	}

	// Function to check token has expired or not
	public boolean isTokenExpired(String token) {
		log.debug("token: " + token + "\ntokenData: " + getDataFromToken(token));
		final Date expiration = getExpirationDateFromToken(token);
		System.out.println("expiration123: " + expiration + " date: " + new Date());
		return expiration.before(new Date());
	}

	public String generateJWT(JSONObject userData) {
		String token = Jwts.builder().setSubject(userData.toString())
				.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKENTIME))
//				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpiration)))
				.signWith(SignatureAlgorithm.HS512, this.secretKey) // Use the encoded secretKey
				.compact();
		return token;
	}

	@SuppressWarnings("unchecked")
	public JSONObject userSignUp(UserEntity userDetails, int clientId) {
		JSONObject userDataOutput = new JSONObject();
		UserEntity userEntity = null;
		log.debug("Email: " + userDetails.getEmail() + " phone: " + userDetails.getPhone());

		UUID uuid = UUID.randomUUID();
		String convertUUID = uuid.toString();// converting into string data not insert without convert data insert.

		userDetails.setName(userDetails.getName());
		userDetails.setEmail(userDetails.getEmail());
		userDetails.setPhone(userDetails.getPhone());
		// TODO below line requires
//		userDetails.setPassword(encryptDecrypt.encrypt(randomKey.randomPass(8), datakey) randomKey.randomPass(8));
		userDetails.setPassword(randomKey.randomPass(8));
		userDetails.setUserId(convertUUID);
		userDetails.setActive(true);
		userEntity = userRepo.save(userDetails);

//		userApplicationData.setUserId(userEntity.getId());
//		userApplicationData.setAppId(clientId);
//		userAppRepo.save(userApplicationData);

		userApplicationDataStoring(userEntity.getId(), clientId);
		userDataOutput.put(Constants.EMAIL, userEntity.getEmail());
		userDataOutput.put(Constants.USERID, userEntity.getUserId());
		userDataOutput.put(Constants.ID, userEntity.getId());
		userDataOutput.put(Constants.NAME, userEntity.getName());
		userDataOutput.put(Constants.PHONE, userEntity.getPhone());

		return userDataOutput;
	}

	public int userApplicationDataStoring(int userId, int clientId) {
		System.out.println("userId "+userId +"clientId "+clientId);
		int val = 0;
		if (userId != 0 && clientId != 0) {
			UserApplicationEntity userApplicationData = new UserApplicationEntity();
			userApplicationData.setUserId(userId);
			userApplicationData.setAppId(clientId);
			userApplicationData.setActive(true);
			userAppRepo.save(userApplicationData);
		
			val = 1;
		}
		return val;
	}

	public String clientIdWithPrefix(String clientName) {
		Random random = new Random();
		String randomPassword = randomKey.generateRandomKey(3);
		String client_id_with_prefix = "";
		int randomNumber = 0;
		switch (clientName.length()) {
		case 2:
			randomNumber = random.nextInt(10000) + 90000;
			client_id_with_prefix = Constants.IBVS_ + clientName + randomNumber + Constants.UNDERSCORE + randomPassword;
			break;
		case 3:
			randomNumber = random.nextInt(1000) + 9000;
			client_id_with_prefix = Constants.IBVS_ + clientName + randomNumber + Constants.UNDERSCORE  + randomPassword;
			break;
		case 4:
			randomNumber = random.nextInt(100) + 900;
			client_id_with_prefix = Constants.IBVS_ + clientName + randomNumber + Constants.UNDERSCORE  + randomPassword;
			break;
		case 5:
			randomNumber = random.nextInt(10) + 90;
			client_id_with_prefix = Constants.IBVS_ + clientName + randomNumber + Constants.UNDERSCORE  + randomPassword;
			break;
		default:
			randomNumber = random.nextInt(10000) + 90000;
			client_id_with_prefix = Constants.IBVS_ + clientName + randomNumber + Constants.UNDERSCORE  + randomPassword;
			break;
		}
		return client_id_with_prefix;
	}

	public ClientEntity clientSave(ClientEntity clientDetails, String client_id_with_prefix) {
		String client_secret_key = randomKey.generateRandomKey(16);
		clientDetails.setName(clientDetails.getName());
		clientDetails.setClientId(client_id_with_prefix);
		clientDetails.setClientSecret(client_secret_key);
		clientDetails.setEmail(clientDetails.getEmail());
		clientDetails.setPassword(randomKey.randomPass(8));
		clientDetails.setActive(true);
		client_entity = clients_repository.save(clientDetails);
		return client_entity;
	}

//	public ClientEntity2 clientSave1(JSONObject inputJsonObject, String client_id_with_prefix) {
//		String client_secret_key = randomKey.generateRandomKey(16);
//		ClientEntity2 clientDetails = new ClientEntity2();
//		clientDetails.setName(inputJsonObject.get(Constants.NAME).toString());
//		clientDetails.setEmail(inputJsonObject.get(Constants.EMAIL).toString());
//		clientDetails.setPhone(inputJsonObject.get(Constants.PHONE).toString());
//		clientDetails.setPrimaryPhone(inputJsonObject.get(Constants.PRIMARY_PHONE).toString());
//		clientDetails.setAddress(inputJsonObject.get(Constants.ADDRESS).toString());
//		clientDetails.setActive(true);
//		clientDetails.setClientId(client_id_with_prefix);
//		clientDetails.setClientSecret(client_secret_key);
//		clientDetails.setPassword(randomKey.randomPass(8));
//		ClientEntity2 client_entity = clients_repository2.save(clientDetails);
//		System.out.println("client_entity: " + client_entity);
//		return client_entity;
//	}

//	@SuppressWarnings("unchecked")
//	public JSONObject clientJsonObject(ClientEntity2 client_entity) {
//		JSONObject finalData = new JSONObject();
//		finalData.put(Constants.NAME, client_entity.getName());
//		finalData.put(Constants.EMAIL, client_entity.getEmail());
//		finalData.put(Constants.PASSWORD, client_entity.getPassword());
//		finalData.put(Constants.PHONE, client_entity.getPhone());
//		finalData.put(Constants.PRIMARY_PHONE, client_entity.getPrimaryPhone());
//		finalData.put(Constants.ADDRESS, client_entity.getAddress());
//		finalData.put(Constants.ACTIVE, client_entity.isActive());
//		finalData.put(Constants.CLIENTID, client_entity.getClientId());
//		finalData.put(Constants.CLIENTSECRET, client_entity.getClientSecret());
//		return finalData;
//	}

	public int sendEmail(ClientEntity client_entity) {
		try {
//			String updateLink = "https://valisign.com/updateDevice/12345678";
//			String adminLink = "https://dev1.valisign.com/";
//			String userRegisterAPI = adminLink + "auth/users/signUp";
//			String initTransactionAPI = adminLink + "initTransaction/{user_id}";
//			String authorizationAPI = adminLink + "authorizeTransaction/{user_id}/{OTP}";
//			String adminLink = this.adminAPI + Mapper.USERREGISTRATION;
			String userRegisterAPI = this.adminAPI + Mapper.USERREGISTRATION;
//			String initTransactionAPI = this.adminAPI + Mapper.INITTRANSACTION;
			String clientRequest = this.adminAPI + Mapper.CLIENTREQUEST;
			String authorizationAPI = this.adminAPI + Mapper.AUTHORIZETRANSACTION;

			String mailSubject = "Successful Registration Confirmation";

			String mailBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n"
					+ "    <title>Successful Registration Confirmation</title>\r\n" + "</head>\r\n" + "<body>\r\n"
					+ "    <p>Dear " + client_entity.getName().toString() + ",</p>\r\n" + "\r\n"
					+ "    <p>We're thrilled to welcome you to ValiSign! Your registration has been successfully completed, and your account is now active and ready to use.</p>\r\n"
					+ "\r\n"
//					+ "    <p>Here are the details of your registration:</p>\r\n"
//					+ "    <ul>\r\n"
//					+ "        <li><strong>Client Name:</strong> [Client's Full Name]</li>\r\n"
//					+ "        <li><strong>Username:</strong> [Client's Username]</li>\r\n"
//					+ "        <li><strong>Email Address:</strong> [Client's Email Address]</li>\r\n"
//					+ "        <li><strong>Registration Date:</strong> [Date of Registration]</li>\r\n"
//					+ "    </ul>\r\n"
//					+ "\r\n"
					+ " </ul>" + "    <p>Below mentioned keys to use in headers.</p>\r\n" + "\r\n" + " <ul>\r\n"
					+ "    <b><li>x-client-id: " + client_entity.getClientId().toString() + "</li>\r\n"
					+ "    <li>x-client-secret: " + client_entity.getClientSecret().toString() + "</li>\r\n"
					+ "		<li>language: en</li>\r\n" + " </b></ul>"
					+ "    <p>As part of our commitment to providing you with the best possible experience, we're excited to introduce you to four powerful APIs (Application Programming Interfaces). These APIs will empower you to interact with our services programmatically, leveraging their capabilities to the fullest extent. Below are the key details for each of the APIs:</p>\r\n"
					+ "\r\n" 
					+ "    <ul>\r\n" 
					+ "        <li><strong>API Key:</strong> " + this.adminAPI + "</li>\r\n"
					+ "    </ul>\r\n" 
					+ "    <p><strong>API 1: " + "User Registration" + "</strong></p>\r\n"
					+ "    <ul>\r\n" 
					+ "        <li><strong>API Endpoint: </strong>" + Mapper.USERREGISTRATION+ "</li>\r\n" 
					+ "        <li><strong>Method: </strong>  POST </li>\r\n"
//					+ "        <li><strong>API Documentation:</strong> <a href=\"[Link to API 1 Documentation]\">API 1 Documentation</a></li>\r\n"
					+ "        <li><strong>Sample Payload: </strong> {\"name\":\"XXXXXX\",\"email\":\"XXXXXXXX@XXXXXXX.XXX\",\"phone\":\"98765432122\"}</li>\r\n"
					+ "        <li><strong>Headers: </strong> x-client-id, x-client-secret, language </li>\r\n"
					+ "    </ul>\r\n" 
					+ "\r\n"
					+ "    <p><strong>API 2: " + "Transaction Init" + "</strong></p>\r\n"
					+ "    <ul>\r\n" 
					+ "        <li><strong>API Endpoint: </strong> " + Mapper.INITTRANSACTION+ "</li>\r\n" + "        "
					+ "<li><strong>Method: </strong>  POST </li>\r\n"
					+ "        <li><strong>Sample Payload: </strong> {\"userId\":\"f103d5ad-d76c-4ee7-a4e9-a0da5816772f\",\"additionalInfo\":\"Changing the first name\",\"oldValue\":\"jhon\",\"newValue\":\"jhonny\",\"transaction\":\"data success\"} </li>\r\n"
					+ "        <li><strong>Headers: </strong>  x-client-id, x-client-secret, language </li>\r\n"
					+ "    </ul>\r\n" + "\r\n" 
//					+ "    <p><strong>API 3: " + "Client Request" + "</strong></p>\r\n"
//					+ "    <ul>\r\n" 
//					+ "        <li><strong>API Endpoint: </strong> " + Mapper.CLIENTREQUEST+ "</li>\r\n" 
//					+ "        <li><strong>Method: </strong>  GET </li>\r\n"
//					+ "        <li><strong>Sample Payload: </strong> N/A </li>\r\n"
//					+ "        <li><strong>Headers: </strong>  x-client-id, x-client-secret, language </li>\r\n"
//					+ "    </ul>\r\n" + "\r\n"
					+ "    <p><strong>API 4: " + "Authorize Transaction"+ "</strong></p>\r\n" 
					+ "    <ul>\r\n"
					+ "        <li><strong>API Endpoint:</strong> "+ Mapper.AUTHORIZETRANSACTION + "</li>\r\n"
					+ "        <li><strong>Method: </strong>  POST </li>\r\n"
					+ "        <li><strong>Sample Payload:</strong> {\"userId\":\"f103d5ad-d76c-4ee7-a4e9-a0da5816772f\",\"otp\":\"TAEAZDN\"} </li>\r\n"
					+ "        <li><strong>Headers:</strong>x-client-id, x-client-secret, language</li>\r\n"
					+ "    </ul>\r\n" + "\r\n"
					+ "    <p>Each API serves specific purposes, and you can access them by making HTTP requests to the provided endpoints with the respective API keys and headers. To help you get started, we've included sample payloads.</p>\r\n"
					+ "\r\n" + "    <p>Best regards,<br>\r\n"
					+ "    ValiSign Admin<br><a href=\"https://ideabytes.com/\">Ideabytes Software PVT LTD</a></p>\r\n"
					+ "</body>\r\n" + "</html>\r\n" + "";

			String[] mailList = { client_entity.getEmail().toString() };
			String[] bccList = {};
			String[] ccList = { this.adminCCMail };
			this.mailService.sendHTMLMessage(mailList, mailSubject, mailBody, bccList, ccList);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return 1;
	}

	public int userRegisterSentMail1(UserEntity inputData, ClientEntity clientData) {
		try {
			System.out.println("Client Entity "+clientData);
			System.out.println("userRegisterSentMail: ");
			String username = inputData.getName();
			String phoneNumber = inputData.getPhone();
			String emailId = inputData.getEmail();

			String mailSubject = "Successful User Registration on ValiSign";

//			String mailBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n"
//					+ "    <title>User Registered in Valisign</title>\r\n" + "</head>\r\n" + "<body>\r\n"
//					+ "    <h3>User Information</h3>\r\n" + "    <p><strong>Name:</strong> " + username + "</p>\r\n"
//					+ "    <p><strong>Phone:</strong> " + phoneNumber + "</p>\r\n" + "    <p><strong>Email:</strong> "
//					+ emailId + "</p>\r\n" + "    <h3>Client Information</h3>\r\n"
//					+ "    <p><strong>Client Name:</strong> " + clientData.getName() + "</p>\r\n"
//					+ "    <p><strong>Client Email:</strong> " + clientData.getEmail() + "</p>\r\n"
//					+ "  <p><strong>Status:</strong> Registered</p>\r\n" + "  <br><br>  <p>Best regards,<br>\r\n"
//					+ "    ValiSign Admin<br><a href=\"https://ideabytes.com/\">Ideabytes Software PVT LTD</a></p>\r\n"
//					+ "</body>\r\n" + "</html>\r\n" + "";

			String mailBody="<!DOCTYPE html>\r\n"
					+ "<html>\r\n"
					+ "<head>\r\n"
					+ "    <style>\r\n"
					+ "        body {\r\n"
					+ "            font-family: Arial, sans-serif;\r\n"
					+ "            padding: 20px;\r\n"
					+ "        }\r\n"
					+ "        table {\r\n"
					+ "            border-collapse: collapse;\r\n"
					+ "            width: 90%;\r\n"
					+ "            margin: 20px auto;\r\n"
					+ "        }\r\n"
					+ "        th, td {\r\n"
					+ "            border: 1px solid #ddd;\r\n"
					+ "            padding: 10px;\r\n"
					+ "            text-align: left;\r\n"
					+ "        }\r\n"
					+ "        th {\r\n"
					+ "            background-color: #423795;\r\n"
					+ "            color: #ffffff;\r\n"
					+ "        }\r\n"
					+".pLeft{\r\n"
					+ "        margin-left:15px}"
					+ "    </style>\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+"<p>Hi <strong>"+changeFirstLetterToCapital(username)+",</strong> </p>\r\n"
					+ "<p class=\"pLeft\">\r\n"
					+ "We are pleased to inform you that your registration on ValiSign has been completed successfully. Welcome to our platform!</p>\r\n"
					+ "<p class=\"pLeft\">\r\n"
					+ "Your user account has been created, and you can now access all the features and services that ValiSign has to offer. </p>"
					+ "<h3 class=\"pLeft\">User Information</h3>\r\n"
					+ "    <table>\r\n"
					+ "        <tr>\r\n"
					+ "            <th>Name</th>\r\n"
					+ "			 <th>Phone</th>\r\n"
					+ "			  <th>Email</th>\r\n"
					+ "        </tr>\r\n"
					+ "        <tr>\r\n"
					+ "        <td>"+username+"</td>\r\n"
					+ "        <td>"+phoneNumber+"</td>\r\n"
					+ "	   <td>"+emailId+"</td>\r\n"
					+ "        </tr>\r\n"
					+ "       \r\n"
					+ "    </table>\r\n"
					+ "\r\n"
					+ "<h3 class=\"pLeft\">Client Information</h3>\r\n"
					+ "    <table>\r\n"
					+ "        <tr>\r\n"
					+ "            <th>Client Name</th>\r\n"
					+ "			 <th>Client Email</th>\r\n"
					+ "            <th>Status</th>\r\n"
					+ "        </tr>\r\n"
					+ "        <tr>\r\n"
					+ "          <td>"+clientData.getName()+"</td>\r\n"
					+ "            <td>"+clientData.getEmail()+"</td>\r\n"
					+ "			<td>Registered</td>\r\n"
					+ "        </tr>\r\n"
					+ "    </table>\r\n"
					+ "  <br><br>  "
					+ "<p>Best regards,<br>\r\n"
					+ "ValiSign Admin<br><a href=\"https://ideabytes.com/\">Ideabytes Software PVT LTD</a></p>\r\n"

					+ "</body>\r\n"
					+ "</html>\r\n"
					+ "";
					System.out.println("mailBody: "+mailBody);
			
			
			String[] mailList = splitBycomma(this.sampleUserInfoMail);
			String[] bccList = {};
			String[] ccList = { this.adminCCMail };
//			String[] ccList = {};
			mailService.sendHTMLMessage(mailList, mailSubject, mailBody, bccList, ccList);
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return 1;
	}

	
	
	
	
	
	
	
	
	public int userRegisterSentMail2(JSONObject inputData) {
		System.out.println("ABCDEF");
		try {
			System.out.println("userRegisterSentMail: ");
			String username = inputData.get(Constants.APP).toString();
			String details = inputData.get("Details").toString();
			System.out.println("APPNAME "+username);
//			String phoneNumber = inputData.get(Constants.PHONE).toString();
//			String emailId = inputData.get(Constants.EMAIL).toString();
//			String password = inputData.get(Constants.PASSWORD).toString();


			String mailSubject = "Successful User Registration Confirmation";

			String mailBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n"
					+ "    <title>User Registered in Valisign</title>\r\n" + "</head>\r\n" + "<body>\r\n"
//					+ "    <h3>User Information</h3>\r\n" 
					+"    <p><strong>App Name:</strong> " +username+ "</p>\r\n"
					+ "    <p><strong>Details:</strong> " +details+ "</p>\r\n"
//					+ "    <p><strong>Username:</strong> " + "" + "</p>\r\n" 
			     	+ 
					"</p>\r\n" + "<h3></h3>\r\n"
//					+ "    <p><strong>Client Name:</strong> " + clientData.getName() + "</p>\r\n"
//					+ "    <p><strong>Client Email:</strong> " + clientData.getEmail() + "</p>\r\n"
					+ "  <p><strong>Status:</strong> Registered</p>\r\n" 
					+ "    <p><strong>Please download the valisgn app:</strong> "+"<a href=\"https://www.example.com\">Visit Valisign.com</a>"+ "</p>\r\n"
					+ "  <br><br>  <p>Best regards,<br>\r\n"
					+ "    ValiSign Admin<br><a href=\"https://ideabytes.com/\">Ideabytes Software PVT LTD</a></p>\r\n"
					+ "</body>\r\n" + "</html>\r\n" ;
					

			String[] mailList = splitBycomma(this.sampleUserInfoMail1);
			String[] bccList = {};
			String[] ccList = {};
//			String[] ccList = {};
			mailService.sendHTMLMessage(mailList, mailSubject, mailBody, bccList, ccList);
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return 1;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String[] splitBycomma(String input) {
		String[] finalStringArray = null;
		if (input != null && !input.isEmpty()) {
			finalStringArray = input.split(",");
		}
		return finalStringArray;
	}
	private String changeFirstLetterToCapital(String input) {
	    if (input == null || input.isEmpty()) {
	        return input;
	    }
	    char firstChar = Character.toUpperCase(input.charAt(0));
	    return firstChar + input.substring(1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int userRegisterSentMail(JSONObject inputData, ClientEntity clientData) {
	try {
		System.out.println("userRegisterSentMail: ");
		String username = inputData.get(Constants.NAME).toString();
		String phoneNumber = inputData.get(Constants.PHONE).toString();
		String emailId = inputData.get(Constants.EMAIL).toString();

		String mailSubject = "Successful User Registration on ValiSign";

//		String mailBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n"
//				+ "    <title>User Registered in Valisign</title>\r\n" + "</head>\r\n" + "<body>\r\n"
//				+ "    <h3>User Information</h3>\r\n" + "    <p><strong>Name:</strong> " + username + "</p>\r\n"
//				+ "    <p><strong>Phone:</strong> " + phoneNumber + "</p>\r\n" + "    <p><strong>Email:</strong> "
//				+ emailId + "</p>\r\n" + "    <h3>Client Information</h3>\r\n"
//				+ "    <p><strong>Client Name:</strong> " + clientData.getName() + "</p>\r\n"
//				+ "    <p><strong>Client Email:</strong> " + clientData.getEmail() + "</p>\r\n"
//				+ "  <p><strong>Status:</strong> Registered</p>\r\n" + "  <br><br>  <p>Best regards,<br>\r\n"
//				+ "    ValiSign Admin<br><a href=\"https://ideabytes.com/\">Ideabytes Software PVT LTD</a></p>\r\n"
//				+ "</body>\r\n" + "</html>\r\n" + "";

		String mailBody="<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "    <style>\r\n"
				+ "        body {\r\n"
				+ "            font-family: Arial, sans-serif;\r\n"
				+ "            padding: 20px;\r\n"
				+ "        }\r\n"
				+ "        table {\r\n"
				+ "            border-collapse: collapse;\r\n"
				+ "            width: 90%;\r\n"
				+ "            margin: 20px auto;\r\n"
				+ "        }\r\n"
				+ "        th, td {\r\n"
				+ "            border: 1px solid #ddd;\r\n"
				+ "            padding: 10px;\r\n"
				+ "            text-align: left;\r\n"
				+ "        }\r\n"
				+ "        th {\r\n"
				+ "            background-color: #423795;\r\n"
				+ "            color: #ffffff;\r\n"
				+ "        }\r\n"
				+".pLeft{\r\n"
				+ "        margin-left:15px}"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+"<p>Hi <strong>"+changeFirstLetterToCapital(username)+",</strong> </p>\r\n"
				+ "<p class=\"pLeft\">\r\n"
				+ "We are pleased to inform you that your registration on ValiSign has been completed successfully. Welcome to our platform!</p>\r\n"
				+ "<p class=\"pLeft\">\r\n"
				+ "Your user account has been created, and you can now access all the features and services that ValiSign has to offer. </p>"
				+ "<h3 class=\"pLeft\">User Information</h3>\r\n"
				+ "    <table>\r\n"
				+ "        <tr>\r\n"
				+ "            <th>Name</th>\r\n"
				+ "			 <th>Phone</th>\r\n"
				+ "			  <th>Email</th>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "        <td>"+username+"</td>\r\n"
				+ "        <td>"+phoneNumber+"</td>\r\n"
				+ "	   <td>"+emailId+"</td>\r\n"
				+ "        </tr>\r\n"
				+ "       \r\n"
				+ "    </table>\r\n"
				+ "\r\n"
				+ "<h3 class=\"pLeft\">Client Information</h3>\r\n"
				+ "    <table>\r\n"
				+ "        <tr>\r\n"
				+ "            <th>Client Name</th>\r\n"
				+ "			 <th>Client Email</th>\r\n"
				+ "            <th>Status</th>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "          <td>"+clientData.getName()+"</td>\r\n"
				+ "            <td>"+clientData.getEmail()+"</td>\r\n"
				+ "			<td>Registered</td>\r\n"
				+ "        </tr>\r\n"
				+ "    </table>\r\n"
				+ "  <br><br>  "
				+ "<p>Best regards,<br>\r\n"
				+ "ValiSign Admin<br><a href=\"https://ideabytes.com/\">Ideabytes Software PVT LTD</a></p>\r\n"

				+ "</body>\r\n"
				+ "</html>\r\n"
				+ "";
				System.out.println("mailBody: "+mailBody);
		
		
		String[] mailList = splitBycomma(this.sampleUserInfoMail);
		String[] bccList = {};
		String[] ccList = { this.adminCCMail };
//		String[] ccList = {};
		mailService.sendHTMLMessage(mailList, mailSubject, mailBody, bccList, ccList);
	} catch (Exception e) {
		log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
		e.printStackTrace();
	}
	return 1;

}
}
