/* 
 * Name: DBCommons
 * Project: ValiSign
 * Version: 0.0.1
 * Description: This class consists of all the common info required for Database based action such as queries, methods
 * Created Date: 2023-08-08
 * Created By: Siddish Gollapelli
 * Modified Date: 2023-08-08
 * Modified By: Siddish Gollapelli
 * 
 */
package com.ideabytes.commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.ideabytes.constants.Constants;

public class DBCommons {
	/* admin DB */
	public static final String SERVER_URL = "jdbc:mysql://ideabytesdb.c6hujshgwzfd.us-east-1.rds.amazonaws.com/vali_sign?autoReconnect=true&useSSL=false";
	public static final String SERVER_USER = "valisign_admin";
	public static final String SERVER_PASSWORD = "vs-adm_pwd";

	// config statements
	public static final String DB_CONNECTION_DRIVER_DEFAULT = "com.mysql.jdbc.Driver";
	public static final String DB_CONNECTION_DRIVER_MYSQL = "com.mysql.jdbc.Driver";
	public static final String DB_CONNECTION_DRIVER_SQLITE = "org.sqlite.JDBC";

	public static final String DB_CONNECTION_DRIVER_STRING_DEFAULT = "jdbc:mysql://";
	public static final String DB_CONNECTION_DRIVER_STRING_MYSQL = "jdbc:mysql://";
	public static final String DB_CONNECTION_DRIVER_STRING_SQLITE = "jdbc:sqlite:";
	public static final String DB_CONNECTION_DRIVER_STRING_RESOURCE_SQLITE = "jdbc:sqlite::resource:";

	public static final String DB_CONNECTION_OK = "Connnected to Database Successfully";
	public static final String DB_CONNECTION_FAIL = "Connnection to Database failed";
	public static final String DB_CONNECTION_ERROR = "Error in connnecting to Database";
	public static final String DB_CONNECTION_INVALID = "Invalid/Insufficient details provided while Connnecting to Database";

	// Tables list

	public static final String CLIENTS_TABLE = "clients";
	public static final String CONFIGURATIONS_TABLE = "configurations";
	public static final String CURRENT_ACTION = "current_action";
	public static final String DEVICES = "devices";
	public static final String DEVICES_USERS = "devices_users";
	public static final String TRANSACTIONS = "transactions";
	public static final String USERS = "users";
	public static final String ROLES = "roles";
	public static final String USER_APPLICATIONS = "user_applications";

	public static final String USERS_SELECT_QUERY = "SELECT * FROM `" + USERS + "`";
	public static final String FILTER_ID_OR_USERID = " where user_id=? OR id=?";
	public static final String FILTER_USERID = " where user_id=?";
	public static final String FILTER_ID ="where id=?";
	public static final String FILTER_EMAIL = " where emailID='<<emailID>>'";
	public static final String USERS_SELECT_QUERY_FILTER_USERID = USERS_SELECT_QUERY + FILTER_USERID;
	public static final String USERS_SELECT_QUERY_FILTER_ID = USERS_SELECT_QUERY + FILTER_ID;
	public static final String USERS_SELECT_QUERY_FILTER_EMAIL = USERS_SELECT_QUERY + FILTER_EMAIL;

	public static final String USERS_SELECT_QUERY_FILTER_ID_OR_USERID = USERS_SELECT_QUERY + FILTER_ID_OR_USERID;

	public static final String GET_USERS_FROM_DEVICE_ID="SELECT u.* FROM `users` u WHERE u.id IN (SELECT du.user_id FROM `devices_users` du where du.device_id=?)";
	/********** SQLITE ***********/

	public static final String INAPP_CREATE = "CREATE TABLE IF NOT EXISTS inApp (id INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
			+ "    StartDate DATETIME,\r\n" + "    LastDate DATETIME, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

	public static final String INAPP_SELECT_ID = "SELECT id FROM inApp ORDER BY id DESC LIMIT 1";

	public static final String INAPP_INSERT = "INSERT into inApp(StartDate,LastDate) values('<<startDate>>','<<endDate>>')";

	public static final String INAPP_SELECT_DATES = "SELECT StartDate,LastDate FROM inApp ORDER BY id ASC LIMIT 1";
	/*************************/

	/********** SQLITE ***********/

	public static final String TEMPMEM_EXEC_CREATE = "CREATE TABLE IF NOT EXISTS aitp_execution_status(\r\n"
			+ "			id	INTEGER,\r\n" + "			table_name	TEXT,\r\n"
			+ "			execution_id	INTEGER,\r\n" + "			step_info	TEXT,\r\n"
			+ "			additional_info	TEXT,\r\n" + "			status	TEXT,\r\n"
			+ "			execution_datetime	DATETIME,\r\n"
			+ "			execution_status_timestamp	DATETIME DEFAULT CURRENT_TIMESTAMP,\r\n"
			+ "			PRIMARY KEY(id AUTOINCREMENT)\r\n" + "			)";

	public static final String TEMPMEM_EXEC_SELECT_ID = "SELECT id FROM aitp_execution_status ORDER BY id DESC LIMIT 1";

	public static final String TEMPMEM_EXEC_INSERT = "INSERT into aitp_execution_status(StartDate,LastDate) values('<<startDate>>','<<endDate>>')";
	public static final String TEMPMEM_EXEC_UPDATE = "UPDATE aitp_execution_status(StartDate,LastDate) values('<<startDate>>','<<endDate>>')";

	public static final String TEMPMEM_EXEC_SELECT_DATES = "SELECT StartDate,LastDate FROM aitp_execution_status ORDER BY id ASC LIMIT 1";

	/*************************/

	/****************************************************************************************************************
	 * Method: getConnectionDetails Description: To get the connection details of
	 * user's database connection from the connection.json file based on the work
	 * environment Ex: in test envoironment, connection_test.json file details are
	 * fetched from src/main/resources/user/ directory Input: environment as String
	 * Output: JSONObject(dbJSON) - url, username, password Note: This method
	 * commonly used to get the users database details.. this can be moved to
	 * DBCommons.. and also need to change how user provides these details(from
	 * plugin/dashboard/file upload/file placing in their local system etc
	 * 
	 * @throws URISyntaxException
	 ***************************************************************************************************************/
	public static org.json.simple.JSONObject getConnectionDetails(String environment)
			throws FileNotFoundException, IOException, ParseException, URISyntaxException {

		// Get the absolute path for user directory under src/main/resources/static/
		// folder
		String dbDir = CommonMethods.getStaticAbsolutePath(Constants.STATIC_DB, environment);
		// Based on the environment get the connection_*.json file from the above folder
		String connectionFile = dbDir + File.separator + "connection_" + environment + Constants.FILE_EXT_JSON;

		JSONParser parser = new JSONParser();
		Object connectionDetailsObject = parser.parse(new FileReader(connectionFile));

		org.json.simple.JSONObject dbJSON = (org.json.simple.JSONObject) connectionDetailsObject;

		return dbJSON;
	}

}
