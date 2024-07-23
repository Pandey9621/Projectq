package com.ideabytes.exception;

import java.util.HashMap;
import java.util.Map;
import com.ideabytes.constants.Constants;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.ideabytes.mappingAPIs.Mapper;
import jakarta.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
@RestControllerAdvice
public class Custom_Exception_Handler {
	JSONObject jo=null;
	@ResponseStatus(HttpStatus.NOT_FOUND)
	 @ExceptionHandler(Not_Found_Exception.class)
	    public Map<String,Object> handleNotFoundException(Not_Found_Exception ex,HttpServletResponse res) {
	     Map<String,Object>em=new HashMap();
	        em.put("error",HttpStatus.NOT_FOUND);
		    em.put(Constants.STATUS,HttpStatus.NOT_FOUND.value());
		    em.put("message", ex.getMessage());
	        return em;
	    }
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	 @ExceptionHandler(Method_Not_Found_Exception.class)
	    public Map<String,Object> handleMethodNotFoundException(Method_Not_Found_Exception ex,HttpServletResponse res) {
	     Map<String,Object>em=new HashMap();
	        em.put("error",HttpStatus.METHOD_NOT_ALLOWED);
		    em.put(Constants.STATUS,HttpStatus.METHOD_NOT_ALLOWED.value());
		    em.put("message", ex.getMessage());
	        return em;

}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	 @ExceptionHandler(Bad_Request_Exception.class)
	    public Map<String,Object> handleMethodNotFoundException(Bad_Request_Exception ex,HttpServletResponse res) {
	     Map<String,Object>em=new HashMap();
	        em.put("error",HttpStatus.BAD_REQUEST);
		    em.put(Constants.STATUS,HttpStatus.BAD_REQUEST.value());
		    em.put("message", ex.getMessage());
	        return em;
}
	





}