package com.ideabytes.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.hibernate.internal.build.AllowSysOut;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ideabytes.binding.BookEntity;
import com.ideabytes.binding.UserBookEntity;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.repository.BookRepository;
import com.ideabytes.repository.UserBookRepository;
import com.ideabytes.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//@CrossOrigin("http://localhost:4200")
@Service
public class UserBookService {
	@Autowired
	public UserBookRepository ubr;
	@Autowired
	public UserRepository ur;
	Optional<BookEntity> opbe;
	Optional<UserBookEntity> opube;
	
	@Autowired
	public BookRepository br;
	@Autowired
	public UserService us;
	JSONArray liue = new JSONArray();
	org.json.JSONObject js=new org.json.JSONObject();
	public BookEntity be = new BookEntity();
	
	List<Integer> libe = new ArrayList<>();
	List<Integer> liube = new ArrayList<>();
	List<String> libebn = new ArrayList<>();
	List<Integer> ids = new ArrayList<>();
	public org.json.JSONObject setBookAndUserName(String token, List<Integer> bookId,Integer id) {
           if (token != null) {
			int uid = 0;
			String name = getUsernameFromToken(token);
	    	boolean tokenExpiration = isTokenExpired(token);
	    	if (!tokenExpiration) {
				List<UserBookEntity>booklist=ubr.findByUserid(id);
				for(int k=0;k<booklist.size();k++) {
					ids.add(booklist.get(k).getBookid());
				}
				for(int i=0;i<bookId.size();i++) {
					int singlebook=bookId.get(i);
					int j=0;
					int hs=0;
					for(j=hs;j<ids.size();j++)
					{
						if(singlebook==ids.get(j))
						{
							break;
						}
					}
					if(j==ids.size())
					{
						opbe=br.findById(bookId.get(i));
						System.out.println("book"+opbe);
						if(opbe.isPresent())
						{
						UserBookEntity ube=new UserBookEntity();
						be=opbe.get();
						int booksid=be.getBookid();
						ube.setBookid(booksid);
						ube.setUserid(id);
			            ubr.save(ube);
			            js.put("Data", ube);
						
						}
						
						}
							
						}return js;
			
				}
            }
			return null;}
					
	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {

		return getClaimFromToken(token, Claims::getSubject);

	}

	// retrieve expiration date from jwt token

	
	// check if the token has expired
	private boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		System.out.println("RESULT "+expiration);
		return expiration.before(new Date());

	}
	public Date getExpirationDateFromToken(String token) {

		return getClaimFromToken(token, Claims::getExpiration);

	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);

	}

	// for retrieving any information from token we will need the secret key

	private Claims getAllClaimsFromToken(String token) {
		System.out.println("" + Jwts.parser());
		return Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();

	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<String> listOutPublicBook(String token) {

		if (token != null) {
			int uid = 0;
			String name = getUsernameFromToken(token);
			boolean tokenExpiration = isTokenExpired(token);
			if (name != null && !tokenExpiration) {
				Iterable<BookEntity> itbe = br.findAll();
				System.out.println(itbe);
				Iterator<BookEntity> it = itbe.iterator();
				while (it.hasNext()) {
					 be = (BookEntity) it.next();
					libe.add(be.getBookid());
					libebn.add(be.getBook());

				}
				System.out.println("Chandan"+      libe);
				System.out.println(libebn);
		
				Iterable<UserBookEntity> iteube = ubr.findAll();
				Iterator<UserBookEntity> itube = iteube.iterator();
				while (itube.hasNext()) {
					UserBookEntity ube = (UserBookEntity) itube.next();
					liube.add(ube.getBookid());
				}
				System.out.println("+++++++" + liube);
				for (int i = 0; i < liube.size(); i++) {
					for (int j = 0; j < libe.size(); j++) {
						if (liube.get(i)==libe.get(j)) {
							 for(int k=0;k<libebn.size();k++)
							 {
								 libebn.remove(k);
								 break;
							 }
					   }
					}
				}
				return libebn;

			} else
				return null;
		} else
			return null;
	}

	public JSONArray listOutUserSavedBook(String token, Integer id) {
		if (token != null) {
			String name = getUsernameFromToken(token);
			boolean tokenExpiration = isTokenExpired(token);
			if (name != null && !tokenExpiration) {
				Iterable<UserBookEntity> ubeite = ubr.findAll();
				Iterator<UserBookEntity> ubeit = ubeite.iterator();
				while (ubeit.hasNext()) {
					UserBookEntity ube = (UserBookEntity) ubeit.next();
					Iterable<BookEntity> beite = br.findAll();
					Iterator<BookEntity> beit = beite.iterator();
					while (beit.hasNext()) {
				BookEntity be = (BookEntity) beit.next();
				if (ube.getBookid() == be.getBookid()) {
				opbe = br.findById(be.getBookid());
				liue.put(opbe);
						}

						
					}
				}
				return liue;
			} else
				return null;
		} else
			return null;
	}
	
	
//	private JSONArray findByBookid(List<Integer>bookId,int uid) {
//		System.out.println("+++++"+bookId.size());
////	     Optional<UserBookEntity>opube=ubr.findById(bookId.get(i));
//		int c=0;
//		for(int i=0;i<bookId.size();i++)
//		{   Optional<BookEntity>opbe=br.findById(bookId.get(i));
//		        
//		            
//		             if(opbe.isPresent())
//		            {  be=opbe.get();
//		            	 
//		             Iterable<UserBookEntity>iteube=ubr.findAll();
//		              Iterator<UserBookEntity>itbe=iteube.iterator();
//		            	 while(itbe.hasNext())
//			              {
//			                 UserBookEntity ube=(UserBookEntity)itbe.next();
//			            	  if(be.getBookId()==ube.getBook_id())
//			            	      break;
//			            	  else {
//			            		  c++;
//			            		  if(c>1)
//			            		  {
//			            			  ube=new UserBookEntity();
//			 		            	 ube.setBook_id(bookId.get(i));
//			 						  ube.setUser_id(uid);
//			 						  ubr.save(ube);
//			 						  liue.put(ubr);
//			 					
//			            		  }
//			            			  
//			            		  }
//			            	  }
//		            }
//		}
//		return liue;
//	  }
}
