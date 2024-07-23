/* 
 * Name: CommonMethods

 * Project: ValiSign
 * Version: 0.0.1
 * Description: This class consists of common methods which are used over different services or controllers
 * Created Date: 2023-08-08
 * Created By: Siddish Gollapelli
 * Modified Date: 2023-08-08
 * Modified By: Siddish Gollapelli
 */

package com.ideabytes.commons;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.Collections;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.hibernate.internal.build.AllowSysOut;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import com.ideabytes.constants.Constants;

public class CommonMethods {

	@Autowired
	static Environment env;

	/****************************************************************************************************************
	 * Method: getStaticAbsolutePath Description: To get the absolute path of
	 * 'static' folder from resources(src/main/resources/static) Input: fileOrFolder
	 * name as String Output: absolute path as String to the given fileOrFolder
	 * Note: This method used commonly for fetching excel or driver folder contents
	 * while running with player
	 * 
	 * @param profile
	 * @throws IOException
	 * @throws URISyntaxException
	 ***************************************************************************************************************/
	public static final String getStaticAbsolutePath(String fileOrFolder, String profile)
			throws IOException, URISyntaxException {

		String absolutePath = "";
		// checking for a resource folder file
		URL resource = CommonMethods.class.getClassLoader()
				.getResource(Constants.STATIC + File.separator + fileOrFolder);
		// getClass().getClassLoader().getResource("static/"+fileOrFolder); //used in
		// older versions
		URI uri = null;
		Boolean clientEnv = false;
		String active_profile = profile;
		// env.getProperty("spring.profiles.active");
		if (resource != null) {
			uri = resource.toURI();
			if ("jar".equals(uri.getScheme())) {
				clientEnv = true;
			}
		} else {
			clientEnv = (active_profile.equals("beta") || active_profile.equals("onpremise")
					|| active_profile.equals("devbeta") || active_profile.equals("on-premise")) ? true : false;
			// clientEnv=(active_profile.equals("beta") ||
			// active_profile.equals("onpremise"))?true:false;
		}

		if (clientEnv) {
			// return getStaticAbsolutePathJAR(fileOrFolder);

			return getAbsolutePath(fileOrFolder);
		} else {

			File file = null;
			try {
				if (resource != null)
					file = Paths.get(resource.toURI()).toFile();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (file != null) {
				absolutePath = file.getAbsolutePath();
			}

			return absolutePath;
		}
	}

	public static String getAbsolutePath(String fileOrFolder) throws URISyntaxException {

		CodeSource codeSource = CommonMethods.class.getProtectionDomain().getCodeSource();
		String jarPath = removeJARpathOverheads(codeSource.getLocation().toURI());
		File jarFile = new File(jarPath);
		String jarDir = jarFile.getParentFile().getPath();

		if (System.getProperty("os.name").toLowerCase().equals("linux")) {
			jarDir = File.separator + jarDir;
		}

		return jarDir.toString() + File.separator + fileOrFolder;
	}

	public static String removeJARpathOverheads(URI uri) {

		String mainPath = uri.toString().replace("jar:file:/", "");
		String[] mpArr = mainPath.split("!");
		return mpArr[0];
	}

	public static String getStaticAbsolutePathJAR(String fileOrFolder) throws IOException {

		// checking for a resource folder file
		URL resource = CommonMethods.class.getClassLoader()
				.getResource(Constants.STATIC + File.separator + fileOrFolder);

		File file = null;
		Path path = null;
		try {
			URI uri = null;

			if (resource != null)

				uri = resource.toURI();

			final String[] array = uri.toString().split("!");
			final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), Collections.emptyMap());
			path = fs.getPath(array[1]);

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String absolutePath = "";
		if (path != null) {

			absolutePath = path.toAbsolutePath().toString();
		}

		return absolutePath;
	}

	/****************************************************************************************************************
	 * Method: createFile Description: To create a new file(.txt) in the 'files'
	 * folder of static resources(src/main/resources/static/files) Input: filename
	 * as String Output: response of file creation as String Note: This method used
	 * from an api to check the newly created files permissions or content access
	 * (experimental)
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 ***************************************************************************************************************/
	public static String createFile(String filename) throws IOException, URISyntaxException {
		String file = filename;
		filename = getStaticAbsolutePath(Constants.STATIC_FILES, "") + File.separator + filename;
		String response = "";
		try {
			File myObj = new File(filename + Constants.FILE_EXT_TXT);
			if (myObj.createNewFile()) {
				response = myObj.getName() + Constants.FILE_CREATED + env.getProperty("serverurl")
						+ Constants.STATIC_FILES + File.separator + file + Constants.FILE_EXT_TXT;
				// "files/"+file+".txt"
				// System.out.println("File created: " + myObj.getName());
			} else {
				response = Constants.FILE_EXISTS;
				// System.out.println("File already exists.");
			}
		} catch (IOException e) {
			response = Constants.FILE_CREATION_ISSUE;
			// System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return response;
	}

	/****************************************************************************************************************
	 * Method: checkFile Description: To check new file exists at given path Input:
	 * filename as String Output: True/False based file exists or not Note: This
	 * method commonly used to check that file(mostly driver file or excel file)
	 ***************************************************************************************************************/
	public static boolean checkFile(String filename) {
		File file = new File(filename);
		// checking file exists and it is not a directory type
		if (file.exists() && !file.isDirectory()) {
			// do something
			return true;
		} else {
			return false;
		}
	}

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
		String userDir = getStaticAbsolutePath(Constants.STATIC_DB, environment);
         System.out.println("UserDir "+userDir);
		// Based on the environment get the connection_*.json file from the above folder
		String connectionFile = userDir + File.separator + "connection_" + environment + Constants.FILE_EXT_JSON;
//         System.out.println("connectionFile "+connectionFile);
//         String connectionFile = "C:\\Users\\chandan.pandey\\Documents\\Pictures\\Vali_Sign_Project\\target\\classes\\static\\db\\connection_local.json";

		JSONParser parser = new JSONParser();
		
		Object connectionDetailsObject = parser.parse(new FileReader(connectionFile));
        org.json.simple.JSONObject dbJSON = (org.json.simple.JSONObject) connectionDetailsObject;
        System.out.println("dbJSON "+dbJSON);
        return dbJSON;
	}

	

	/****************************************************************************************************************
	 * Method: deleteUserFiles Description: To delete all the user files or with
	 * matching filter Input: userDir as String Output: list of files with details
	 * -name, date, size
	 * 
	 * @param filter
	 *
	 ***************************************************************************************************************/
	public static String deleteUserFiles(String dirName, String filter, int deleteAll) {
		File dir = new File(dirName);

		if (deleteAll == 1) {
			try {
				FileUtils.cleanDirectory(dir);
				return "Successfully deleted all files";
			} catch (Exception e) {
				e.printStackTrace();
				return "Unable to delete all files";
			}
		} else {
			int total = 0;
			int matched = 0;
			int deleted = 0;
			int failed = 0;

			File[] files = dir.listFiles();

			if (files != null && files.length > 0) {
				for (File file : files) {
					// Check if the file is a directory
					if (!file.isDirectory() && !filter.equals("")) {
						total++;
						// System.out.println("not a dir chekc for files matching filter");

						if (file.getName().contains(filter)) {
							matched++;
							try {
								file.delete();
								deleted++;
							} catch (Exception e) {
								e.printStackTrace();
								failed++;
							}
						} else {
							// System.out.println("filter: " + filter);
							// System.out.println("file: " + file.getName());
							// System.out.println("Other user table/repo related file..dont delte");
						}
					}
				} // end_of_for

				return "Files Summary: Total-" + total + " Matched-" + matched + " Deleted-" + deleted + " Not Deleted-"
						+ failed;

			} else {
				return "No files found";
			}

		}

	}

	// Arralist
	public static int countDuplicate(ArrayList<String> arr, String element) {

		int i = 0;
		int count = 0;

		while (i < arr.size()) {

			if (arr.get(i).equals(element))
				count++;

			i++;
		}

		return count;
	}

	// String array
	public static int countDuplicates(String[] arr, String element) {

		int i = 0;
		int count = 0;

		while (i < arr.length) {

			if (arr[i].equals(element))
				count++;

			i++;
		}

		return count;
	}

	public static String[] replaceDuplicates(String[] names) {

		// Store the frequency of strings
		HashMap<String, Integer> hash = new HashMap<>();

		// Iterate over the array
		for (int i = 0; i < names.length; i++) {

			// For the first occurrence,
			// update the frequency count
			if (!hash.containsKey(names[i]))
				hash.put(names[i], 1);

			// Otherwise
			else {
				int count = hash.get(names[i]);
				hash.put(names[i], hash.get(names[i]) + 1);

				// Append frequency count
				// to end of the string
				names[i] += Integer.toString(count);
			}
		}

		return names;

	}

	public static void listUpOldFiles(String folderPath, int expirationPeriod) throws IOException {
		try {
			File targetDir = new File(folderPath);
			if (!targetDir.exists()) {
				throw new RuntimeException(
						String.format("Log files directory '%s' " + "does not exist in the environment", folderPath));
			}

			File[] files = targetDir.listFiles();

			for (File file : files) {

				if (file.isDirectory()) {
					// user's folder
					System.out.println("folder:" + file.getName());

					File[] iFiles = file.listFiles();
					System.out.println("iFiles length:" + iFiles.length);
					for (File ifile : iFiles) {

						long diff = new Date().getTime() - ifile.lastModified();
						System.out.println("diff:" + diff);

						// Granularity = DAYS;
						long desiredLifespan = TimeUnit.DAYS.toMillis(expirationPeriod);
						System.out.println("desired:" + desiredLifespan);

						if (diff > desiredLifespan) {
							// file.delete();
							System.out.println(ifile.getName());

							if (ifile.isDirectory()) {
								// directory again
								// FileUtils.deleteDirectory(ifile);
								System.out.println("dir:" + ifile.getName());
							} else {
								// file
								System.out.println("file:" + ifile.getName());

								// boolean fileDeleted=ifile.delete();
								System.out.println("deleted file:" + ifile.getName());

							}

						}

					}

				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void cleanUpOldFiles(String folderPath, int expirationPeriod) throws IOException {
		try {
			File targetDir = new File(folderPath);
			if (!targetDir.exists()) {
				throw new RuntimeException(
						String.format("Log files directory '%s' " + "does not exist in the environment", folderPath));
			}

			File[] files = targetDir.listFiles();

			for (File file : files) {

				if (file.isDirectory()) {
					// user's folder
					System.out.println("folder:" + file.getName());

					File[] iFiles = file.listFiles();
					System.out.println("iFiles length:" + iFiles.length);
					for (File ifile : iFiles) {

						long diff = new Date().getTime() - ifile.lastModified();
						System.out.println("diff:" + diff);

						// Granularity = DAYS;
						long desiredLifespan = TimeUnit.DAYS.toMillis(expirationPeriod);
						System.out.println("desired:" + desiredLifespan);

						if (diff > desiredLifespan) {
							// file.delete();
							System.out.println(ifile.getName());

							if (ifile.isDirectory()) {
								// directory again
								FileUtils.deleteDirectory(ifile);
								System.out.println("dir:" + ifile.getName());
							} else {
								// file
								System.out.println("file:" + ifile.getName());

								boolean fileDeleted = ifile.delete();
								System.out.println("deleted file:" + ifile.getName() + fileDeleted);

							}

						}

					}

				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	

//	public static String randomString(int length, String chars) {
//		String result = "";
//		for (int i = length; i > 0; --i) {
//			double rand = Math.random();
//			int index = (int) Math.floor(rand * chars.length());
//			result += chars.charAt(index);
//		}
//
//		return result;
//	}

//	public static String generateRandomPassword() {
//		return randomString(8, Constants.CHARACTERS);
//	}

}
