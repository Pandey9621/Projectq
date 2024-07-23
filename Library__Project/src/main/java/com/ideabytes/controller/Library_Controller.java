
package com.ideabytes.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.LibraryEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.constants.Constants;
import com.ideabytes.exception.Bad_Request_Exception;
import com.ideabytes.exception.Not_Found_Exception;
import com.ideabytes.repository.LibraryRepository;
import com.ideabytes.service.UserService;

@RestController
public class Library_Controller {
	@Autowired
	public LibraryRepository lr;
	@Autowired
	public UserService us;
	LibraryEntity libraryDetails = null;
	JSONObject jsonObject = new JSONObject();
	ResponseEntity<Map> response;
	JSONArray ja = new JSONArray();

	@PostMapping("/library")
	public Map<String, Object> save(@RequestBody LibraryEntity libraryDetails, BindingResult bindingResult)
			throws Bad_Request_Exception {
		if (libraryDetails.getLibraryname() == null || bindingResult.hasErrors()) {
			throw new Bad_Request_Exception(Constants.LIBRARYDETAILS);
		} else {
			libraryDetails = lr.save(libraryDetails);
			jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
			jsonObject.put(Constants.DATA, libraryDetails);
			jsonObject.put(Constants.MESSAGE, Constants.DATAADDEDSUCCESSFULLY);
			jsonObject.put(Constants.STATUS, HttpStatus.OK);
			return jsonObject.toMap();

		}
	}

	@GetMapping("/libraries")
	public Map<String, Object> getAllLibraryDetails() {

		Iterable<LibraryEntity> li = lr.findAll();
		if (li == null) {

			response = new ResponseEntity<Map>(jsonObject.toMap(), HttpStatus.NOT_FOUND);
		} else {
			jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
			jsonObject.put(Constants.DATA, li);
			jsonObject.put(Constants.MESSAGE, Constants.LIBRARYDATAFETCHEDSUCESSFULLY);
			jsonObject.put(Constants.STATUS, HttpStatus.OK);
		}
		return jsonObject.toMap();
	}

	@GetMapping("/search/library")
	public Map<String, Object> findByCondition(@RequestParam(value = "city") String city,
			@RequestParam(value = "libraryname") String libraryname) {

		Iterable<LibraryEntity> li = lr.findAll();
		Iterator<LibraryEntity> li1 = li.iterator();
		System.out.println(city);
		System.out.println(libraryname);

		while (li1.hasNext()) {
			LibraryEntity le = (LibraryEntity) li1.next();
			String lname = le.getLibraryname();
			String lcity = le.getCity();
			System.out.println(lname);
			System.out.println(lcity);

			if (lcity.equals(city) && lname.equalsIgnoreCase(libraryname)) {
				jsonObject = new JSONObject();
				Optional<LibraryEntity> op = lr.findById(le.getId());
				ja.put(op);
				jsonObject.put(Constants.DATA, ja);
				jsonObject.put(Constants.MESSAGE, Constants.LIBRARYDATAFETCHEDSUCESSFULLY);
				jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
				jsonObject.put(Constants.STATUS, HttpStatus.OK);
				return jsonObject.toMap();
			} else if (lcity.equals(city) && !lname.equals(libraryname)) {
				return jsonObject.put(Constants.MESSAGE, "Only city Available Not Lname").toMap();
			}

		}
		return jsonObject.put(Constants.MESSAGE, "Only libraryName Available Not CityName").toMap();
	}

	@GetMapping("/library/{id}")
	public Map<String, Object> findById(@PathVariable Integer id) throws Not_Found_Exception {
		Optional<LibraryEntity> libraryDetails = lr.findById(id);
		if (libraryDetails.isEmpty()) {
			throw new Not_Found_Exception(Constants.LIBRARYDETAILSNOTAVAILABlE);
		} else {
			jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
			jsonObject.put(Constants.DATA, libraryDetails);
			jsonObject.put(Constants.MESSAGE, Constants.LIBRARYDATAFETCHEDSUCESSFULLY);
			jsonObject.put(Constants.STATUS, HttpStatus.OK);
			return jsonObject.toMap();
		}

	}

	@DeleteMapping("/library/{id}")
	public Map<String, Object> deleteById(@PathVariable Integer id) throws Not_Found_Exception {
		Optional<LibraryEntity> li = lr.findById(id);
		if (li.isPresent()) {
			lr.deleteById(id);
			jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
			jsonObject.put(Constants.MESSAGE, Constants.LIBRARYDATADELETED);
			jsonObject.put(Constants.STATUS, HttpStatus.OK);
			return jsonObject.toMap();
		} else
			throw new Not_Found_Exception(Constants.LIBRARYDETAILSNOTAVAILABlE + id);
	}

	@PutMapping("/library/{id}")
	public Map<String, Object> updateLibraryDataById(@PathVariable Integer id,
			@RequestBody LibraryEntity libraryDetails) throws Not_Found_Exception {
		System.out.println(id);
		System.out.println(libraryDetails);
		libraryDetails = us.libraryDetailsUpdaEntity(id, libraryDetails);
		if (libraryDetails == null) {
			throw new Not_Found_Exception(Constants.LIBRARYDETAILSNOTAVAILABlE + id);
		} else {
			jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
			jsonObject.put(Constants.DATA, libraryDetails);
			jsonObject.put(Constants.MESSAGE, Constants.LIBRARYDETAILSUPDATE);
			jsonObject.put(Constants.STATUS, HttpStatus.OK);
			return jsonObject.toMap();
		}
	}

}
