/* 
 * Name: DatabaseServicesImpl.java
 * Project: ValiSign
 * Version: 0.2.0
 * Description: This class handles all the database related services directed from the controllers
 * Created Date: 2023-08-11
 * Developed By: Siddish Gollapelli
 * Modified Date: 2023-08-11
 * Modified By: Siddish Gollapelli
 * 
 */
package com.ideabytes.service.implementations;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import com.ideabytes.commons.CommonMethods;
import com.ideabytes.commons.DBCommons;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.service.ValisignService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.internal.build.AllowSysOut;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author Admin
 *
 */

@Service
public class DatabaseServicesImpl {

	@Autowired
	private Environment env;
    private static final Logger log = LogManager.getLogger(DatabaseServicesImpl.class);
    @SuppressWarnings("unchecked")
	public JSONArray getUsersForDevice(int deviceId)
			throws FileNotFoundException, IOException, ParseException, URISyntaxException {
		// TODO Auto-generated method stub
		JSONArray users = new JSONArray();
		String environ = env.getProperty("spring.profiles.active");
		System.out.println("environ "+environ);

//		System.out.println("");
		System.out.println("environ: " + environ);
		org.json.simple.JSONObject jso = CommonMethods.getConnectionDetails(environ);
		String url = (String) jso.get("url");
	    System.out.println("URL1 "+url);
		System.out.println("==================");
		String database = (String) jso.get("database");
        System.out.println("URL2 "+database);
		System.out.println("==================");
		String username = (String) jso.get("username");
	    System.out.println("URL3"+username);
		System.out.println("==================");
		String password = (String) jso.get("password");
		System.out.println("password "+password);

		try {
			Class.forName(DBCommons.DB_CONNECTION_DRIVER_DEFAULT);

			if (!database.equals("") && !url.contains(database)) {
				url = url +"/" +database;
			}
			Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("CON  "+con.toString());
			Statement stmt = con.createStatement();
			System.out.println("Statement "+stmt);
			DatabaseMetaData meta = con.getMetaData();
			System.out.println("Meta "+meta.toString());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
			LocalDateTime now = LocalDateTime.now();
			String timestamp = dtf.format(now);

			PreparedStatement preparedStatement = con.prepareStatement(DBCommons.GET_USERS_FROM_DEVICE_ID);
			preparedStatement.setInt(1, deviceId);
//			System.out.println("PreparedStatement"+preparedStatement.getString("device_id"));
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				JSONObject user = new JSONObject();
				user.put(Constants.ID, rs.getInt(Constants.ID));
				user.put(Constants.USERID, rs.getString(Constants.USER_ID));
				user.put(Constants.EMAIL, rs.getString(Constants.EMAIL));
				user.put(Constants.USERNAMECAPN, rs.getString(Constants.NAME));
			
                users.add(user);
			}

			// rows affected
			// if(debug || devDebug) System.out.println("row: " + row); // 1

			con.close();
			// return execution_id;
			// return "Data inserted successfully";

		} catch (Exception e) {
			e.printStackTrace();
			users = null;
		}

		return users;

	}
 

	/**
	 * @param environ
	 * @return
	 * @throws URISyntaxException
	 * @throws ParseException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public Connection getConnection(String environ)
			throws FileNotFoundException, IOException, ParseException, URISyntaxException {
		// TODO Auto-generated method stub
		org.json.simple.JSONObject jso = CommonMethods.getConnectionDetails(environ);
		String url = (String) jso.get(Constants.URL);
		String database = (String) jso.get(Constants.DATABASE);
		String username = (String) jso.get(Constants.USERNAME);
		String password = (String) jso.get(Constants.PASSWORD);
          System.out.println("DATABASE "+database);
		try {
			Class.forName(DBCommons.DB_CONNECTION_DRIVER_DEFAULT);

			if (!database.equals("") && !url.contains(database)) {
				url = url + "/" + database;
			}
			Connection con = DriverManager.getConnection(url, username, password);
			return con;
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());

			return null;
		}
	}
 
}
