package com.ideabytes.controller;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.constants.Constants;
import com.ideabytes.exception.Not_Found_Exception;
import com.ideabytes.repository.BookRepository;
import com.ideabytes.service.UserBookService;


import jakarta.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("user/{uid}")
public class UserBooksController {
	
	@Autowired
    public UserBookService userBookService;
	
//	@Autowired
//    public UserBookRepository userSelectRepo;
	
	@Autowired
    public BookRepository br;
	 
	@PostMapping("/books/selectlistofbook")
    public Map<String, Object> selectListOfBook(HttpServletRequest req,@RequestBody List<Integer>bookId,@PathVariable Integer uid) throws Not_Found_Exception {
		  System.out.println(bookId+" "+uid);
		  for(int i=0;i<bookId.size();i++)
		  {
			  System.out.println("RESULT"+bookId.get(i));
		  }
		  String bearerToken=req.getHeader("Authorization");
			int periodCount = 0;
		    for (int i = 0; i <  bearerToken.length(); i++) {
		        if (bearerToken.charAt(i) == '.') {
		            periodCount++;
		        } }
		    System.out.println("RESULT1"+bearerToken);
			if(bearerToken==null||periodCount!= 2)
			{  
				
			   JSONObject jo = new JSONObject();
		       jo.put(Constants.MESSAGE,"Access Denied");
		       return jo.toMap();
			}
			else
			{   
				JSONObject jo = new JSONObject();
				JSONArray liue=new JSONArray();
		        String splitToken[] = bearerToken.split(" ");				
		        String token = splitToken[1];
		        System.out.println("RESULT2"+token);
		        
		        try {
		        	userBookService.setBookAndUserName(token,bookId,uid);
		        	jo=new JSONObject();
		        	jo.put(Constants.MESSAGE, Constants.BOOKSAVE);
		        	jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
		        	jo.put(Constants.STATUS, HttpStatus.OK);
		        	return jo.toMap();
		        }
		        catch(Exception e)
		        {
		        	jo.put(Constants.MESSAGE, Constants.UNAUTHORISED);
		    		jo.put(Constants.STATUSCODE, HttpStatus.UNAUTHORIZED.value());
		    		return jo.toMap();
		        	
		        }}}
	
     @GetMapping("/public/books/listout")
    public Map<String, Object> selectPublicListBook(HttpServletRequest req) throws Not_Found_Exception {
	 String bearerToken=req.getHeader("Authorization");
		int periodCount = 0;
	    for (int i = 0; i <  bearerToken.length(); i++) {
	        if (bearerToken.charAt(i) == '.') {
	            periodCount++;
	        } }
	    System.out.println(bearerToken);
		if(bearerToken==null||periodCount!= 2)
		{  
		  JSONObject jo = new JSONObject();
	       jo.put(Constants.MESSAGE,"Access Denied");
	       return jo.toMap();
		}
		else
		{   
			JSONObject jo = new JSONObject();
			List<String> publist=new ArrayList<String>();
	        String splitToken[] = bearerToken.split(" ");				
	        String token = splitToken[1];
	        System.out.println(token);
	        try {
	        	publist=userBookService.listOutPublicBook(token);
	            JSONObject jso=new JSONObject();
	        	jso.put("BookDetails",publist);
              	jo=new JSONObject();
	        	jo.put(Constants.DATA, jso);
	        	jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
	        	jo.put(Constants.STATUS, HttpStatus.OK);
	        	return jo.toMap();
	        }
	        catch(Exception e)
	        {
	        	jo.put(Constants.MESSAGE, Constants.UNAUTHORISED);
	    		jo.put(Constants.STATUSCODE, HttpStatus.UNAUTHORIZED.value());
	    		return jo.toMap();
	        	
	        }}}
	
	@GetMapping("/choice/savedbooklistout")
    public Map<String, Object> userChoiceSavedBook(HttpServletRequest req,@PathVariable Integer id) throws Not_Found_Exception {
	 String bearerToken=req.getHeader("Authorization");
		int periodCount = 0;
	    for (int i = 0; i <  bearerToken.length(); i++) {
	        if (bearerToken.charAt(i) == '.') {
	            periodCount++;
	        } }
	    System.out.println(bearerToken);
		if(bearerToken==null||periodCount!= 2)
		{  JSONObject jo = new JSONObject();
	       jo.put(Constants.MESSAGE,"Access Denied");
	       return jo.toMap();
		}
		else
		{   
			JSONObject jo = new JSONObject();
			JSONArray userchoicelist=new JSONArray ();
	        String splitToken[] = bearerToken.split(" ");				
	        String token = splitToken[1];
	        System.out.println(token);
	        try {
	        	userchoicelist=userBookService.listOutUserSavedBook(token,id);
	        	jo=new JSONObject();
	        	jo.put("BookDetails",userchoicelist);
	        	jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
	        	jo.put(Constants.STATUS, HttpStatus.OK);
	        	return jo.toMap();
	          }
	        catch(Exception e)
	        {
	        	jo.put(Constants.MESSAGE, Constants.UNAUTHORISED);
	    		jo.put(Constants.STATUSCODE, HttpStatus.UNAUTHORIZED.value());
	    		return jo.toMap();
	        	
	        }
	        
	}
	}}
	
	
