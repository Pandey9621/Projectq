/*********************** Ideabytes Software India Pvt Ltd *********************                                 
* Here,UserService class is implemented for performing  the buisness logic.
* @author  Chandan Pandey
* @version 20.0.1
* @since   2023-06-23.
*/
package com.ideabytes.service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ideabytes.mappingAPIs.Mapper;
import com.ideabytes.repository.BookRepository;
import com.ideabytes.repository.LibraryRepository;
import com.ideabytes.repository.UserRepository;
import com.ideabytes.service.implementation.EmailServiceImpl;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.apache.tomcat.jni.Library;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;


//import com.ideabytes.binding.BookEntity;
//import com.ideabytes.binding.LibraryEntity;
//import com.ideabytes.binding.UserBookEntity;
//import com.ideabytes.binding.UserEntity;
//import com.ideabytes.exception.Not_Found_Exception;
//import com.ideabytes.repository.BookRepository;
//import com.ideabytes.repository.UserBookRepository;
//import com.ideabytes.repository.LibraryRepository;
//import com.ideabytes.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
//import javax.servlet.http.HttpServletRequest;
import com.ideabytes.binding.BookEntity;
import com.ideabytes.binding.LibraryEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.constants.Constants;
@CrossOrigin("http://localhost:4200")
@Service
public class UserService {
	JSONArray ja=null;

//	@Autowired
//	public UserBookRepository ubr;
	@Autowired
	public UserRepository up;
	@Autowired
	public EmailServiceImpl esi;
	@Autowired
	public BookRepository br;
//	Optional<BookEntity> opbe;
//	Optional<UserBookEntity> opube;
//	public BookEntity be = new BookEntity();
//	 @Value("${sample.mailforUserRegister}")
//	 private String sampleUserInfoMail;
//	 @Value("${mailCC.admin}")
//	 private String mailAdmin;
	JSONObject jo = null;
	@Autowired
	public UserRepository userRepository;
	JSONArray liue = new JSONArray();
	
	@Autowired
	public LibraryRepository libraryRepository;
	@Autowired
	public BookRepository bookRepository;

//	@Autowired
//	public UserBookRepository bsue;

	/**
	 * This method updateEntity the updating the data based on id.
	 * @param id is the first parameter to getting the data available in that id.
	 * @param updatedEntity is the second parameter for update the either name,dob
	 * or both..
	 * @return User Object.
	 */
	public UserEntity updateEntity(Integer id, UserEntity updatedEntity) {
		Optional<UserEntity> optionalEntity = userRepository.findById(id);
		if (optionalEntity.isPresent()) {
//        getting the value from optionalEntity.
			UserEntity entity = optionalEntity.get();
			// Update the entity with the new data
			String name = updatedEntity.getName();
			String dob = updatedEntity.getDob();

			if (name != null && (dob != null)) {
				entity.setName(updatedEntity.getName());
				entity.setDob(updatedEntity.getDob());
				return userRepository.save(entity);
			} else if (dob != null) {
				entity.setDob(dob);
//				System.out.println(updatedEntity.getDob());
				return userRepository.save(entity);
			} else {

				entity.setName(name);
				return userRepository.save(entity);
			}

		}
		return null;
	}

//	public Map<String, Object> validURL(String end, HttpServletRequest req) throws Not_Found_Exception {
//		String baseUrl = "http://localhost:9090"+end;
//		System.out.println(baseUrl);
//		String endpointUrl = req.getRequestURL().toString();
//		boolean t = baseUrl.equals(endpointUrl);
//		System.out.println("+++++++" + endpointUrl);
//		if (t == true) {
//			String get = "Hi hello,Good Morning.";
//			jo = new JSONObject();
//			jo.put(Constants.MESSAGE, get);
//			jo.put(Constants.STATUS, HttpStatus.OK);
//			jo.put(Constants.STATUSCODE, HttpStatus.OK.value());
//			return jo.toMap();
//		} else
//			throw new Not_Found_Exception("Invalid_URL");
//	}
//	
	
	public LibraryEntity libraryDetailsUpdaEntity(Integer id, LibraryEntity updatedEntity) {
		Optional<LibraryEntity> optionalEntity = libraryRepository.findById(id);
		if (optionalEntity.isPresent()) {
//        getting the value from optionalEntity.
			LibraryEntity entity = optionalEntity.get();
			// Update the entity with the new data
			String lname = updatedEntity.getLibraryname();
			String lcity = updatedEntity.getCity();
			String lcountry = updatedEntity.getCountry();

			if (lname != null && (lcity != null) &&(lcountry!=null)) {
				entity.setLibraryname(lname);
				entity.setCity(lcity);
				entity.setCountry(lcountry);
				return libraryRepository.save(entity);
			} else if (lcity!= null) {
				entity.setCity(lcity);
				return libraryRepository.save(entity);
			} else if(lcountry!=null) {

				entity.setCountry(lcountry);
				return libraryRepository.save(entity);
			}else
			{
				entity.setLibraryname(lname);
				return libraryRepository.save(entity);
				
			}

		}
		return null;
	}
	
//	
	public BookEntity bookDetailsUpdateEntity(Integer id, BookEntity updatedEntity) {
		Optional<BookEntity> optionalEntity = bookRepository.findById(id);
		if (optionalEntity.isPresent()) {
//        getting the value from optionalEntity.
			BookEntity entity = optionalEntity.get();
			// Update the entity with the new data
			String bname = updatedEntity.getBook();
			String bauthor = updatedEntity.getAuthor();
            if (bname != null && (bauthor != null)) {
				entity.setBook(bname);
				entity.setAuthor(bauthor);
			   return bookRepository.save(entity);
			} else if (bauthor!= null) {
				entity.setAuthor(bauthor);
				return bookRepository.save(entity);
			}  
			else
                return bookRepository.save(entity);
			}
		return null;
	}

            public List<UserEntity> findAll(String token) {
    	    List<UserEntity>liue=new ArrayList();
    	    if(token!=null) {
            String name = getUsernameFromToken(token);
            System.out.println("++++++++++++"+name);
            boolean tokenExpiration = isTokenExpired(token);
            System.out.println("TOKEN EXPIRATION "+tokenExpiration);
             if(name!=null&&!tokenExpiration) {
             liue= (List<UserEntity>) userRepository.findAll();
             System.out.println(liue);
             return liue;
          }
             else
             
            	 return null;
             

        }
	return null;
    	
    	

    }
     
   //retrieve username from jwt token

     public String getUsernameFromToken(String token) {

         return getClaimFromToken(token, Claims::getSubject);

     }

  

     //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {

         return getClaimFromToken(token, Claims::getExpiration);

     }

  

     public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

         final Claims claims = getAllClaimsFromToken(token);

         return claimsResolver.apply(claims);

     }

     //for retrieving any information from token we will need the secret key

     private Claims getAllClaimsFromToken(String token) {
       System.out.println(""+Jwts.parser());
         return Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();

     }
     //check if the token has expired
      private boolean isTokenExpired(String token) {
      final Date expiration = getExpirationDateFromToken(token);
      System.out.println(expiration +"Expiration");
      return expiration.before(new Date());

     }
   public JSONObject fetchUserId(String Password)
  	  {
  		Iterable<UserEntity> userEntity = up.findAll();
  		Iterator<UserEntity> iterating = userEntity.iterator();
  		 while (iterating.hasNext()) {
  			UserEntity ue2 = (UserEntity) iterating.next();
  			if (Password.equals(ue2.getPassword())) {
  			 Optional<UserEntity>op=up.findById(ue2.getUserid());
  			 jo=new JSONObject();
  			 jo.put("userdata",op);
  			 return jo;
  			}
  			
  	}
  		return null;
    
  		
  	}
        public void sentMail() {
        	String text="Your password has been updated";
        	String subject="Password Update";
        	String mailList="bobbylko1998@gmail.com";
        	esi.sendSimpleMessage(mailList, subject,text);
        	
        }

	
	
}
