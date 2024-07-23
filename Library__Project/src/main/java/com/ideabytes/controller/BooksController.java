package com.ideabytes.controller;

import java.awt.print.Book;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.BookEntity;
import com.ideabytes.binding.UserBookEntity;
//import com.ideabytes.binding.LibraryEntity;
//import com.ideabytes.binding.UserBookEntity;
import com.ideabytes.constants.Constants;
import com.ideabytes.exception.Bad_Request_Exception;
import com.ideabytes.exception.Not_Found_Exception;
import com.ideabytes.repository.BookRepository;
//import com.ideabytes.repository.UserBookRepository;
//import com.ideabytes.repository.LibraryRepository;
import com.ideabytes.service.UserService;
@CrossOrigin("http://localhost:4200")
@RestController
public class BooksController {
	@Autowired
    public BookRepository br;

//	@Autowired
//    public UserBookRepository ubr;
     
	@Autowired
    public UserService us;
     
	 BookEntity bookDetails=null;
	 JSONObject jsonObject = null;
     ResponseEntity<Map> response;
     JSONArray ja=new JSONArray();
     UserBookEntity sbl=null;
    @PostMapping("/book")
    public Map<String,Object> saveBookDeatils(@RequestBody BookEntity bookDetails, BindingResult bindingResult) throws Bad_Request_Exception {
    if(bookDetails.getBook()==null || bindingResult.hasErrors()){
        throw new Bad_Request_Exception(Constants.BOOKDETAILS);
        }
        else {
            bookDetails  = br.save(bookDetails);
            jsonObject=new JSONObject();
        	jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
        	jsonObject.put(Constants.DATA, bookDetails);
        	jsonObject.put(Constants.MESSAGE, Constants.BOOKDATAADDEDSUCCESSFULLY);
        	jsonObject.put(Constants.STATUS, HttpStatus.OK);
            return jsonObject.toMap();
           
         }
      
    }
    @GetMapping("/books")
    public Map<String,Object> getAllBookDetails() {

        Iterable<BookEntity> li=br.findAll();
        if(li==null) {
           
            response = new ResponseEntity<Map>(jsonObject.toMap(), HttpStatus.NOT_FOUND);
        }
        else {
        	jsonObject=new JSONObject();
            jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
            jsonObject.put(Constants.DATA, li);
            jsonObject.put(Constants.MESSAGE, Constants.BOOKDATAFETCHEDSUCESSFULLY);
            jsonObject.put(Constants.STATUS, HttpStatus.OK);
        }
        return jsonObject.toMap();
    }
    @GetMapping("/search/book")
    public Map<String,Object> findByCondition(@RequestParam(value="book") String book, @RequestParam(value="author") String author) 
    {
    	
			Iterable<BookEntity> li=br.findAll();
		    Iterator<BookEntity> li1=li.iterator();
		    System.out.println(book);
		    System.out.println(author);
		    
			while(li1.hasNext())
			{
				BookEntity le=(BookEntity)li1.next();
				 String bname=le.getBook();
				 String authorname=le.getAuthor();
				 System.out.println(book);
				 System.out.println(author);
	         
			     if(book.equals(bname)&&author.equalsIgnoreCase(authorname))
				{
			    jsonObject=new JSONObject();
				Optional<BookEntity>op=br.findById(le.getBookid());
				ja.put(op);
				
				
				jsonObject=new JSONObject();
				jsonObject.put(Constants.DATA,ja);
				jsonObject.put(Constants.MESSAGE,Constants.BOOKDATAFETCHEDSUCESSFULLY);
				jsonObject.put(Constants.STATUSCODE,HttpStatus.OK.value());
				jsonObject.put(Constants.STATUS, HttpStatus.OK);
				return jsonObject.toMap();
				}	}
			return null;
    }
			
//			     else if(lcity.equals(city)&&!lname.equals(libraryname))
//			     {
//			    	 return jsonObject.put(Constants.MESSAGE,"Only city Available Not Lname").toMap();
//			     }
//			     
//			}
//    		return jsonObject.put(Constants.MESSAGE,"Only libraryName Available Not CityName").toMap();
//    		}
//	

    @GetMapping("/book/{id}")
    public Map<String,Object> findById(@PathVariable Integer id)throws Not_Found_Exception {
    Optional<BookEntity> libraryDetails = br.findById(id);
        if(libraryDetails.isEmpty()) {
            throw new Not_Found_Exception(Constants.BOOKDETAILSNOTAVAILABlE);
        }
        else {
        
        	jsonObject=new JSONObject();
            jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
            jsonObject.put(Constants.DATA, libraryDetails);            
            jsonObject.put(Constants.MESSAGE,Constants.BOOKDATAFETCHEDSUCESSFULLY);
            jsonObject.put(Constants.STATUS, HttpStatus.OK);
            return jsonObject.toMap();
            
        }

    }

    @DeleteMapping("/book/{id}")
    public Map<String ,Object>deleteById(@PathVariable Integer id) throws Not_Found_Exception {
        Optional<BookEntity>li=br.findById(id);
        if(li.isPresent()) {
        	 br.deleteById(id);
        	 jsonObject=new JSONObject();
        	 jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
             jsonObject.put(Constants.MESSAGE, Constants.BOOKDATADELETED);
             jsonObject.put(Constants.STATUS, HttpStatus.OK);
             return jsonObject.toMap();
          }
        else
        	throw new Not_Found_Exception(Constants.BOOKDETAILSNOTAVAILABlE+id);
     }

    @PutMapping("/book/{id}")
    public Map<String, Object> updateBookDataById(@PathVariable Integer id,@RequestBody BookEntity bookDetails) throws Not_Found_Exception {
    	System.out.println(id);
    	System.out.println(bookDetails);
       bookDetails = us.bookDetailsUpdateEntity(id,bookDetails);
        if(bookDetails==null) {            
        throw new Not_Found_Exception(Constants.BOOKDETAILSNOTAVAILABlE+id);
        }
        else {
        	jsonObject=new JSONObject();
            jsonObject.put(Constants.STATUSCODE, HttpStatus.OK.value());
//          jsonObject.put(Constants.DATA, bookDetails);
            jsonObject.put(Constants.MESSAGE, Constants.BOOKDETAILSUPDATE);
            jsonObject.put(Constants.STATUS,HttpStatus.OK);
            return jsonObject.toMap();
        }
        }
    
   
    
    
    
    
    
   
    
        

}
    






