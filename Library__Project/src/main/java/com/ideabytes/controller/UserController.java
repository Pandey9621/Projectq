/*********************** Ideabytes Software India Pvt Ltd *****************
****                                   
* Here,UserController class is implemented for all the crud operation on user data.
* @author  Chandan Pandey
* @version 20.0.1
* @since   2023-06-23.
*/
package com.ideabytes.controller;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.constants.Constants;
import com.ideabytes.exception.Method_Not_Found_Exception;
import com.ideabytes.exception.Not_Found_Exception;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.UserRepository;
import netscape.javascript.JSObject;
import com.ideabytes.repository.UserRepository;
import com.ideabytes.service.UserService;

import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

//import javax.servlet.http.HttpServletRequest;
@CrossOrigin("http://localhost:4200")
@RestController
public class UserController {

	JSONObject jo = null;
	JSONArray ja = new JSONArray();
	Optional op = null;
	@Autowired
	public UserService userService;
	@Autowired
	public UserRepository ur;

	UserEntity ue = null;

	@RequestMapping(value=Mapper.user, method=RequestMethod.POST)
	/**
	 * This method getAgeTheUser the getting age of user for inserting record into
	 * database it is allowed or not based on age condition.
	 * 
	 * @RequestBody is the used for getting the data from request body.
	 * @return Map Object.
	 */
	public JSONObject saveData(@RequestBody UserEntity u) throws Not_Found_Exception {
		HttpStatus hs = HttpStatus.METHOD_NOT_ALLOWED;
		String pattern = "^\\d{4}-\\d{2}-\\d{2}$";
		Pattern regexPattern = Pattern.compile(pattern);
		JSONObject jo = null;
		Scanner sc=new Scanner(System.in);
		try {
			String uname = u.getName();
			System.out.println("NAME!@# " + uname);
			String udob = u.getDob();
			String email = u.getEmail();
//			System.out.println("EmailId" + emailId);
			Matcher matcher = regexPattern.matcher(udob);
			boolean t = matcher.matches();
			if (udob == "" || udob == null || uname == null) {
				jo = new JSONObject();
				jo.put(Constants.MESSAGE, Constants.ENTERDATEOFBIRTH);
				return jo;
			}

			else if (t!= true) {
				jo = new JSONObject();
				jo.put(Constants.MESSAGE, Constants.PROERDATEOFBIRTH);
				return jo;
			} else {

				String res1 = calculateAge(udob, uname);
				if (res1 == uname) {
					System.out.println("++++++++++" + res1);
					String urp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
					String low = "abcdefghijklmnopqrstuvwxyz";
					String num = "0123456789";
					String com = urp + low + num;
					int len = 8;
					char[] pass = new char[len];
					Random r = new Random();
					for (int i = 0; i < len; i++) {
						pass[i] = com.charAt(r.nextInt(com.length()));
					}
					String result = new String(pass);
					
					java.util.Date date = new java.util.Date();
					u.setName(uname);
					u.setDob(udob);
					u.setPassword(result);
					u.setEmail(email);
					u.setDatetime(date.toString());
					System.out.println("ABCD" + date.toString());
					ue = ur.save(u);

					System.out.println(ue);
					jo = new JSONObject();
					System.out.println(jo);
					jo.put(Constants.MESSAGE, Constants.DATAADDEDSUCCESSFULLY);
					jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
					jo.put(Constants.DATA, ue);
					jo.put(Constants.STATUS, HttpStatus.OK);

				} else {
					jo = new JSONObject();
					jo.put(Constants.MESSAGE, res1);

				}
				return jo;

			}
		} catch (Exception e) {
			throw new Not_Found_Exception("Not");
		}

	}// end of saveData().

	/**
	 * This method calculateAge the calculating the age.
	 * 
	 * @param dob  is the first parameter to calculateAge() method.
	 * @param name is the second parameter to calculateAge() method.
	 * @return string This returns the user permission or not.
	 */
	public static String calculateAge(String dob, String name) {
		try {
			LocalDate dob1 = LocalDate.parse(dob);
			LocalDate curDate = LocalDate.now();
			if ((dob1 != null) && (curDate != null)) {
				int age = Period.between(dob1, curDate).getYears();
				System.out.println("+++++++" + age);
				if (age < 18)
					return "User Is Not Under Age";
				else
					System.out.println("+_+_+_+_+_+" + name);
				return name;
			} else {
				return "0";
			}
		} catch (InputMismatchException ime) {

		}
		return "0";
// End of switch case.

	}// End of getStatusMessage() block.

	@RequestMapping(value = Mapper.users, method = RequestMethod.GET)
	/**
	 * This method gettingAllUserData is used for fetching the all record of user
	 * from database.
	 * 
	 * @return Map Object.
	 */
	public Map<String, Object> gettingAllUserData(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		System.out.println("Bearer Token " + bearerToken);
		int periodCount = 0;
		for (int i = 0; i < bearerToken.length(); i++) {
			if (bearerToken.charAt(i) == '.') {
				periodCount++;
			}
		}
		if (bearerToken == null) {
			JSONObject jo = new JSONObject();
			jo.put(Constants.MESSAGE, "Access Denied");
			return jo;
		} else {
			JSONObject jo = new JSONObject();
			List<UserEntity> liue = new ArrayList<>();
			String splitToken[] = bearerToken.split(" ");
//		System.out.println("++++++"+Arrays.deepToString(splitToken));
			String token = splitToken[1];
			System.out.println("TOKEN " + token);
			try {
				liue = userService.findAll(token);
				if (liue != null) {
					Iterable<UserEntity> li = ur.findAll();
					JSONArray ja = new JSONArray();
					jo.put(Constants.DATA, li);
					jo.put(Constants.MESSAGE, Constants.USERSDATAFETCHEDSUCCESSFULLY);
					jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
					jo.put(Constants.STATUS, HttpStatus.OK);
					return jo;
				}

			} catch (SignatureException e) {
				jo = new JSONObject();
				jo.put(Constants.MESSAGE, Constants.ERROR);
				return jo;
			}
		}
		jo.put(Constants.MESSAGE, Constants.UNAUTHORISED);
		jo.put(Constants.STATUSCODE, HttpStatus.UNAUTHORIZED.value());
		return jo;

	}// gettingAllUserData().

	/**
	 * This method findUsersByFilter the filtering the user data based on
	 * age-condition and name-condition(age>30&name like 'raj').
	 * 
	 * @RequestBody is the used for getting the data from request body.
	 * @return Map Object.
	 */

	@RequestMapping(value = Mapper.searchusers, method = RequestMethod.GET)
	public Map<String, Object> findUsersByFilter(@RequestParam int age, @RequestParam String name) {

		try {
			System.out.println(age);
			System.out.println(name);
			Iterable<UserEntity> li = ur.findAll();
			System.out.println(li);
			Iterator<UserEntity> li1 = li.iterator();
			while (li1.hasNext()) {
				UserEntity u = (UserEntity) li1.next();
				System.out.println(u);
				String res = u.getName();
				System.out.println(res);
				LocalDate dob1 = LocalDate.parse(u.getDob());
				LocalDate curDate = LocalDate.now();
				int calculatedAge = Period.between(dob1, curDate).getYears();
				System.out.println(calculatedAge);
				if (age < calculatedAge && res.contains(name)) {
					jo = new JSONObject();
					Optional<UserEntity> op = ur.findById(u.getUserid());
//                    ja.put(op);
					jo.put(Constants.DATA, op);
					jo.put(Constants.MESSAGE, Constants.USERSDATAFETCHEDSUCCESSFULLY);
					jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
					jo.put(Constants.STATUS, HttpStatus.OK);
				}
			}
			return jo;
		} catch (Exception e) {
			jo = new JSONObject();
			jo.put(Constants.MESSAGE, Constants.ERROR);
			jo.put(Constants.STATUSCODE, Constants.CODE);
			jo.put(Constants.STATUSCODE, "Chandan");
			return jo;
		}
	}// end of findUsersByFilter method.

	@RequestMapping(value = Mapper.userid, method = RequestMethod.GET)
	/**
	 * This method fetchById the reterieving the user data based on id.
	 * 
	 * @PathVariable is the used for getting the values from path pameter in url
	 *               pattern.
	 * @return Map Object.
	 */
	public JSONObject fetchById(@PathVariable Integer id) throws Not_Found_Exception {
		Optional<UserEntity> li = ur.findById(id);
		if (li.isPresent()) {
			jo = new JSONObject();
			jo.put(Constants.DATA, li);
			jo.put(Constants.MESSAGE, Constants.USERFETCHEDSUCCESSFULLY);
			jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
			jo.put(Constants.STATUS, HttpStatus.OK);
			return jo;

		} else
			throw new Not_Found_Exception(Constants.USERNOTAVAILABLE + id);

	}// end of fetchById method.

	@RequestMapping(value = Mapper.userid, method = RequestMethod.DELETE)
	/**
	 * This method deleteById the deleting the user data based on id.
	 * 
	 * @PathVariable is the used for getting the values from path pameter in url
	 *               pattern.
	 * @return Map Object.
	 */
	public Map<String, Object> deleteById(@PathVariable Integer id) throws Not_Found_Exception {

		Optional<UserEntity> li = ur.findById(id);
		if (li.isPresent()) {
			jo = new JSONObject();
			ur.deleteById(id);
			jo.put(Constants.MESSAGE, Constants.USERDELETEDSUCCESSFULLY);
			jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
			jo.put(Constants.STATUS, HttpStatus.OK);
			return jo;
		}

		else {
			throw new Not_Found_Exception(Constants.USERNOTAVAILABLE + id);
		}

	}// end of deleteById method.

	@RequestMapping(value = Mapper.userid, method = RequestMethod.PUT)
	/**
	 * This method modifyById for urdating the userdata either name,dob,or both
	 * based on id .
	 * 
	 * @PathVariable is the used for getting the value from path parameter in url.
	 * @RequestBody is using for getting the data from request body.
	 * @return Map Object.
	 */
	public JSONObject modifyById(@PathVariable Integer id, @RequestBody UserEntity updateEntity)
			throws Not_Found_Exception {

		UserEntity u = userService.updateEntity(id, updateEntity);
		if (u != null) {
			jo = new JSONObject();
			jo.put(Constants.MESSAGE, Constants.USERDATAUPDATEDSUCCESSFULLY);
			jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
			jo.put(Constants.STATUS, HttpStatus.OK);
			return jo;
		} else
			throw new Not_Found_Exception(Constants.USERNOTAVAILABLE);

	}// end of modifyById method.

	@RequestMapping(value = Mapper.user)
	public ResponseEntity<String> handleUnsurportedRequest() throws Method_Not_Found_Exception {
		throw new Method_Not_Found_Exception(Constants.METHODNOTAVAILABLE);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = Mapper.forgetpwd, method = RequestMethod.POST)
	public JSONObject forgetPassword(@PathVariable Integer id, @RequestBody JSONObject data) {
		JSONObject json_object = null;
		Optional<UserEntity> li = ur.findById(id);

		System.out.println("DATACP " + data);
		String newPassword = data.get("new_password").toString();
		System.out.println("newPassword" + newPassword);
		String confirmPassword = data.get("confirm_password").toString();
		if (li.isPresent()) {
			if (newPassword.contentEquals(confirmPassword)) {
				UserEntity ue = li.get();
				System.out.println("USERENTITY " + ue);
				ue.setPassword(confirmPassword);
				ur.save(ue);
				json_object = new JSONObject();
				json_object.put(Constants.MESSAGE, "Password Updated");
				json_object.put(Constants.STATUS, "200");
				json_object.put(Constants.STATUSCODE, "OK");
				userService.sentMail();
				return json_object;
			} else {
				json_object = new JSONObject();
				json_object.put(Constants.MESSAGE, "Password mismatch");
				json_object.put(Constants.STATUS, "401");
				json_object.put(Constants.STATUSCODE, "Server not found");
				return json_object;
			}

		} else {
			json_object = new JSONObject();
			json_object.put(Constants.MESSAGE, "User does not exist");
			json_object.put(Constants.STATUS, "OK");
			json_object.put(Constants.STATUSCODE, "400");
			return json_object;

		}

	}
}
