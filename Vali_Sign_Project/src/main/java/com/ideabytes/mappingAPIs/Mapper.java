/*********************** Ideabytes Software India Pvt Ltd *********************                                   
* Here,Mapper class is implement for mapping all the api seperately.
* *@author  Chandan Pandey
* @version 20.0.1
* @since   2023-06-23.
*/
package com.ideabytes.mappingAPIs;

public class Mapper {
	// cors api
	final public static String ANGULARAPI = "http://localhost/4200";

	public final static String CLIENTREGISTRATION = "auth/clients/register";
	public final static String CLIENTREGISTRATION1 = "auth/clients/register1";
	public final static String USERREGISTRATION = "auth/users/signUp";
//	public final static String USERREGISTRATION1 = "auth/users/signUp1";
	public final static String APP = "/appDetails";
	public final static String SIGNIN = "auth/signIn";
	public final static String FORGOTPASSWORD = "auth/forgotPassword";
	public final static String ENCRYPTDATA = "api/encryptData";
	public final static String DECRYPTDATA = "api/decryptData";
	public final static String CONFIGUREAPI = "app/configure";
	public final static String READUSERS = "users/read";
	public final static String GETBYUSERID = "user/{user_id}";
	public final static String GETBYID = "users/{id}";
	public final static String UPDATEUSER = "users/{user_id}";
	public final static String DELETEUSER = "users/{user_id}";
	public final static String READCLIENTS = "clients/read";
	public final static String FETCHCLENT = "clients/{client_id}";
	public final static String UPDATECLIENT = "clients/{client_id}";
	public final static String DELETECLIENT = "clients/{client_id}";
	public final static String DEVICEREGISTRATION = "devices/register";
	public final static String UPDATEDEVICE = "devices/{device_id}";
	
	public final static String ADDUSERS = "users/create";
	
	public final static String INITTRANSACTION = "transaction/init";
	public final static String AUTHORIZETRANSACTION = "transaction/authorize";
	public final static String GETVALISIGNCODE = "getValisignCode";
	public final static String CLIENTREQUEST = "clientRequest/{userId}";
	public final static String GETTRANSACTION = "getTransactionDetails/{userId}";

	public final static String INSERTCONFIGAPI = "app/configure/add";
	public final static String READCONFIGAPI = "app/configure/read";
	public final static String UPDATAECONFIGAPI = "app/configure/update/{id}";
	public final static String DELETECONFIGAPI = "app/configure/remove/{id}";

	public final static String ADMINREGISTRATION = "/register";
	public final static String READADMIN = "/read";
	public final static String UPDATEADMIN = "update/{id}";
	public final static String DELETEADMIN = "remove/{id}";
	public final static String ADMINSIGNIN = "/signIn";
	
	
	public final static String UPDATEUSER1 = "users/{user_id}";
    public final static String TRANSACTION = "transaction/{user_id}";

}
