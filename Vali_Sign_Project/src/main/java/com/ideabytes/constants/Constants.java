/*********************** Ideabytes Software India Pvt Ltd *********************               
                  
* Here,This Constants class is implemented for all the different kind of request and resoponse message.
* @author  Chandan Pandey
* @version 20.0.1
* @since   2023-07-27.
*/
package com.ideabytes.constants;

import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Constants {
	static ObjectMapper objectMapper = new ObjectMapper();
	static ObjectNode emptyObject = objectMapper.createObjectNode();
	final public static String EMPTYSTRING = emptyObject.toString();
//	    final public static String STATUSCODE = "statusCode";
//		final public static String STATUS = "status";
//		final public static String STATUSMESSAGE = "statusMessage";
//		final public static String MESSAGE = "message";
//		final public static String DATA = "data";
	final public static String USERNOTFOUND = "User not found";
	final public static String CLIENTNOTFOUND = "Client not found";
	final public static String DATAADDEDSUCCESSFULLY = "User data Inserted successfully";
	final public static String DEVICEREGISTERD = "Device registered successfully";
	final public static String ALREADYREGISTERD = "Details already registered";
	final public static String NOTALLOWED = "Not Allowed";
	final public static String ERROR = "error";
	final public static String ERRORCODE = "errorCode";
	final public static String ERRORMESSAGE = "Bad_Request";
	final public static String USERNOTAVAILABLE = "The requested user ID does not exist in the system.";
	final public static String CLIENTNOTAVAILABLE = "The requested client ID does not exist in the system.";
	final public static String METHODNOTAVAILABLE = "Method_Not_Found";
	final public static String SIGIN = "Sign-in successful";
	final public static String UNAUTHORISED = "Unautorised";
	final public static String INVALID = "Please enter the correct username or password.";
	final public static String IDENTIFIERNOTFOUND = "Identifier not found.";
	final public static String TOKEN = "token";
	final public static String INPUTFIELD = "Please enter the  userName or emailId.";
	final public static String NULLPOINTEREXCEPTION = "Null pointer exception is raised";
	final public static String WISHMESSAGE = "Hello Developer";
	final public static String JAVAVERSION = "javaVersion";
	final public static String TOMCATVERSION = "tomcatVersion";
	final public static String OSNAME = "osName";
	final public static String LOCALHOST = "localHost";
	final public static String CURRENTVALUE = "currentValue";
	final public static String APPNAME = "appName";
	final public static String APPVERSION = "appVersion";
	final public static String ACCESSDENIED = "accessDenied";
	final public static String DEVICETYPE = "Android";
	final public static String CITY = "city";
	final public static String NA = "NA";
	final public static String VERSIONMISMATCH = "Please update your version";
	final public static String CONFIGUREAPPLICATION = "Application confiure successful";
	final public static String IBVS_CONFIG_SUCCESS = "IBVS_CONFIG_SUCESS";
	final public static String IBVS_CONFIG_001 = "IBVS_CONFIG_001";
	final public static String MISMATCHERROR = "Version mismatch error";
	final public static String USERSDATAFETCHEDSUCCESSFULLY = "User details fetched successful";
	final public static String USERDATAUPDATEDSUCCESSFULLY = "User details updated successful";
	final public static String USERDELETEDSUCCESSFULLY = "Deleted successful";
	final public static String USERDELETIONFAILED = " Deletetion failed";
	final public static String USERDETAILSNOTAVAILABLE = "Details not available";
	final public static String USERDATAFETCHSUCESSFULLY = "Details fetched successful";
	final public static String CLIENTSDATAFETCHEDSUCCESSFULLY = "Details fetched successful";
	final public static String CLIENTDATAUPDATEDSUCCESSFULLY = "Details updated successful";
	final public static String CLIENTDATADELETESUCCESSFULLY = "Deleted successful";
	final public static String CLIENTDATAFETCHSUCESSFULLY = "Client details fetched successful";
	final public static String CLIENTDATADELETEUNSUCESSFULLY = "Delete unsuccessful";
	final public static String CLIENTDETAILNOTAVAILABLE = "Details not available";
	final public static String DEVICEDATAUPDATEDSUCCESSFULLY = "Details updated successful";
	final public static String DEVICEDETAILNOTAVAILABLE = "Details not available";
	final public static String OTPGENERATED = "OTP generated successfully";
	final public static String APPLICATIONSELECTED = "Application selected successfully";
	final public static String DATAKEY = "dataKey";
	final public static String DATAKEY_SMALLK="datakey";
	final public static String IDENTIFIER = "identifier";

	public static final String FILE_EXT_TXT = ".txt";
	public static final String FILE_EXT_XLS = ".xls";
	public static final String FILE_EXT_XLSX = ".xlsx";
	public static final String FILE_EXT_JSON = ".json";
	public static final String FILE_CREATED = " File has been created. Check at: ";
	public static final String FILE_CREATION_ISSUE = "Error in creating the file";
	public static final String FILE_EXISTS = "File already exists with the given name";
	// Controller paths
	public static final String ADMIN = "admin";

	// FILE EXTENTIONS
//					public static final String FILE_EXT_TXT = ".txt";
//					public static final String FILE_EXT_XLS = ".xls";
//					public static final String FILE_EXT_XLSX = ".xlsx";
//			a		public static final String FILE_EXT_JSON = ".json";

	public static final String FILEEXT_TXT = "txt";
	public static final String FILEEXT_XLS = "xls";
	public static final String FILEEXT_XLSX = "xlsx";
	public static final String FILEEXT_JSON = "json";

	// Folder names under static resources src/main/resources/static
	public static final String STATIC = "static";

	public static final String STATIC_FILES = "files";
	public static final String STATIC_LOGS = "logs";
	public static final String STATIC_TEMP = "temp";
	public static final String STATIC_USER = "user";
	public static final String STATIC_ADMIN = "admin";
	public static final String STATIC_DB = "db"; // database connections
	public static final String STATIC_STATUS_CODES = "status_codes"; // status code file jsons
	public static final String DATABASE = "database";
	public static final String URL = "url";

	public static final String STATIC_BY_FILES = STATIC + "/files";
	public static final String STATIC_BY_LOGS = STATIC + "/logs";
	public static final String STATIC_BY_TEMP = STATIC + "/temp";
	public static final String STATIC_BY_USER = STATIC + "/user";
	public static final String STATIC_BY_ADMIN = STATIC + "/admin";
	public static final String STATIC_BY_DB = STATIC + "/db";
	public static final String STATIC_BY_STATUS_CODES = STATIC + "/status_codes";

	// APP-DB configurations
	final public static String IOS_STRING = "iOS";
	final public static String ANDROID_STRING = "android";
	final public static String IOS_APP_KEYSTRING = "ios_app_version";
	final public static String ANDROID_APP_KEYSTRING = "android_app_version";
//				     final public static String CHECK_PHRASE = "checkPhrase";
	final public static String CHECK_PHRASE = "checkPhrase";

	// Response keys
	final public static String STATUSCODE = "statusCode";
	final public static String STATUS = "status";
	final public static String STATUSMESSAGE = "statusMessage";
	final public static String MESSAGE = "message";
	final public static String DATA = "data";
	final public static String OTP = "otp";
	final public static String OTPTIME = "OTPTime";
	final public static String VALISIGNOTP = "valisignOTP";
//					   final public static String ERROR = "error";
//					   final public static String ERRORCODE = "errorCode";
//					   
//}
	// Header key
	final public static String LANGUAGE = "language";
	final public static String ENGLISH = "en";
	final public static String XCLIENTID = "x-client-id";
	final public static String XCLIENTSECRET = "x-client-secret";

	final public static String CHARACTERSFOROTP = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final public static String CHARACTERSFORPASSWORD = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_#!";

	// AES keys
	final public static String[] KEYS = { "C4aweNPXb0Unvm5yFjtlVpcuhVZsndiY", "T5$okm.$DgZk2Ub.6AJZs%/Cn5pPvRCu",
			"Qy2fAMzYFZfqZzkcelcafohr%otYx^kx", ".rkZG^qlBiNc&Zm4byRdXgi8ekvwlLbW", "RsUQ&talPqu7C^tRHA&qyE$0Fg^RhK57",
			"xX.u42bQ6HoamUc8OBXqLT9fhNy&a8b1", ".ZC.1a4ZmQfVCOYKD9OANKI9SbgEGXUt", "SdEoDJepnzz61zjwKuDyB9&.Q6Se3$w.",
			"D/2FC5N6PVdd&g1QAC$CDl.4OHunESkZ", "gYF%z07%ekh./P/X.e0LZ4kMHj14BY.o" };
	// AES IV key (initialization vector)
	final public static String IV = "e93jGXDcjXPbSOAE";

	final public static String USERNAME = "username";
	final public static String USERNAMECAPN = "userName";
	final public static String PASSWORD = "password";
	// days*hours*minutes*seconds*milliseconds
	final public static long TOKENTIME = 10 * 24 * 60 * 60 * 1000;
	final public static String LISTOFAPPLICATION = "ListOFApplication";
	final public static String USERID = "userId";
	final public static String USER_ID = "user_id";
	final public static String AUTHORIZATION = "authorization";
	final public static String TRANSACTIONDETAILS = "transactionDetails";
	final public static String APPID = "appId";
	final public static String OTPSUCCESS = "OTP successful";
	final public static String OTPTIMEEXPIRE = "OTP successful";

	final public static String ID = "id";
	final public static String EMAIL = "email";
	final public static String NAME = "name";
	final public static String PHONE = "phone";
	final public static String PRIMARYPHONE = "primaryPhone";
	final public static String PRIMARY_PHONE = "primary_phone";
	final public static String ADDRESS = "address";
	final public static String ACTIVE = "active";
	final public static String CLIENTID = "clientId";
	final public static String CLIENTSECRET = "clientSecret";
	final public static String NEWVALUE = "newValue";
	final public static String OLDVALUE = "oldValue";
	final public static String TRANSACTION = "transaction";
	final public static String ADDITIONALINFO = "additionalInfo";
	final public static String ACTION = "action";
	final public static String ADMIN_LEVEL ="admin_level";
	final public static String ADMINLEVEL="adminLevel";
	final public static String MODIFIED="modified";
	final public static String CREATED="created";
	
	final public static String DEVICEID = "deviceId";
	final public static String IPADDRESS = "ipAddress";
	final public static String LOCATION = "location";
	final public static String DEVICETYPEKEY = "deviceType";
	final public static String VERSION = "version";
	final public static String DEVICEIP = "deviceIp";
	final public static String DEVICELOCATION = "deviceLocation";

	final public static String IBVS_ = "IBVS_";
	final public static String UNDERSCORE = "_";
	final public static String UTF8 = "UTF-8";
final public static String CKEY="ckey";
final public static String CVALUE="cvalue";
	
	final public static String publicKeyBase64 = "MIIFBDANBgkqhkiG9w0BAQEFAAOCBPEAMIIE7AKCBOMAuIKo8ndjQjG7d2l7byC/gj8CauQyU87PeW0pkgkYt5XogXU4u2c2Q/0/fKn51X5fd+JkBFwzzGIKg5MPd5filYl6UMws2IRZaho6RC1cMNVsilekb1mIbk54l/1nqw4G1zMa4aJcE5aQ2ZrHl5MiHNPBh/jE+0DJ7BiemQxIYffsk3MsvZz3dxhzdNNwqJUK0Frx92zg0bYM1Td4sL62J39SH9VOEt/bYuy2DvX4jZQSh0WoJPwPHJpBSDP47gzul1jlZQfme6SDah948SpFdUPZNjDCPRqzmwGPDMV+kYETh8JN/yEritqyTNZCRB2kYNje5ByZdt5WZ6C+1HpbIZKtaa+3JhSl7xBmvJrpnl3poA+HCvOo/8wo8KP9OXea6SsbL7IsMge4rMXXWYt5d6FGFBwpZ6SHq7zVRMBWplHljSRObmlaA/naiZItJwiTEedUNQAke73L9lsM9NzbxUeXFtvCg6Dt+dtLhMmVam9oxOctast1jqHbj+1Mk9ik4sqwHuGFXfAjnUfgO5C3xXlcOAjMJhoyddGGg35uzbudSbiGEqGbQYHaw1YEXBg+cY6ya7emirMrC4zaXejL9oO1aytxkNDYwKm6YqwvtZ/LErRqkdiRuSZE/Po7r9cbd/xqCSIFLYw1/N12r4K4hqCowMOG4CmeKkRk7d49YaCLy0bjyH1ZUlOADx6EZmZdlNzx3ZRR4TvSwr4UD+Z9dThzNgC0FR+lQeX8SyZ15eaIlJ3tookrmhXxPeyMI7V0B1yfqXru4XFZYZkeXmSW+JqoBx7kXfR9CLyf7E6Y7RISQwBXAhx6p3TL5dS2hskbYS9hzOyCzKlDfRaWsBnNVEUuE4KXZndvsR2oG+AweDN5fRdZqMfhuwP1LphlJKx9gYuindbO6Uw/GrNiSWtCM+eDB4OQf/t5FOylvFzAiaAV9qF2vT0TKi/pDT/4Lwe868ar5owF/gYIiv3froJrwqrVO2An1IL9xwehB2kVlGGC1LWPo0TZwYT+gaodoTWRsYfAQm4vNP0Bk9y/BoBv5gN+9saV1jg/3UO0Dw200IFsBAsKpQ1ETz3Oxgz/+vjygUNoT7KVrcKaIxLKTTmxDHYxF3Hnekwo2+PgRTREf7N60mZiLUXz1c/+/oTca7NBxBiqkqbXG9YJT7+j83h7w+kD+AUjM+dyhLB4yi9y4qwyNvwytqvkpThf1VA8w2tvAsSBtTbHGrAPEUdHVqCfdjS1E/3kmFK8vTZhi1IULEyW+OsC7fTRfH2Pooxq8xJwHMTN9/t7dsB02NTGzffHhNtKfj2QQw35RfE/L4j1N5ssskrT/frMv8cNRrIJthODiL/8P9BsYrtVq43oCSj+uP+FZ32yKF6zCPEv3xF5260cI8B6iqaP4k672W9pPHFD9T5/JK6TZFRGHNy4OBsyVogXFp6+EE1aswrJPRXWQGnMdDjezlFOepvDCGxoCIBWQa80MnHaia2mXGUGWiPT/70XhofaxyulrkzzBYi+gr9FlQKaEjH7ecsov5mmc2z8OSq3XyzUzLj+MvJxvuRL+O9rwJmqkndGO/s5zITcUFIiSG+RBNtGEALii5H34ubmZqaP/9jYKhSMjPuSCgTywe2FzpS96IlIdKJX6UkiNbJiYobln3aIoEtqQC4Uc0cJFTMCAwEAAQ==";
	final public static String privateKeyBase64 = "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";
	final public static String aesRandom32Key = "OOLpc+Jp5sDcUYszRrzhp69R92CLxi20";

	// AES for client data key and IV
	final public static String clientIV = "8a998dcb0e0c5d48";
	final public static String clientDataKey = "L1LSAPw19FPcQEEXzhyx1LESog4LJQK0";
	final public static String AESCBCPKCS5PADDING = "AES/CBC/PKCS5Padding";
	final public static String AES = "AES";
	final public static String DEVICEDETAILS = "deviceData";
	final public static String APPDETAILS = "appData";
	final public static String USERDATA = "userData";
	final public static String APP = "app";
	
}
