package com.ideabytes.exception;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ideabytes.binding.UserEntity;
import com.ideabytes.repository.UserRepository;

public class Not_Found_Exception extends Exception {
	    public Not_Found_Exception(String message) {
	        super(message);
	    }
	}
