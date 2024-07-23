package com.ideabytes;

import java.io.ObjectInputFilter.Config;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.context.ConfigurableApplicationContext;

import com.ideabytes.binding.UserEntity;
import com.ideabytes.repository.UserRepository;

@SpringBootApplication
public class LibraryProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(LibraryProjectApplication.class, args);
		   UserRepository ue=context.getBean(UserRepository.class);
//		List<UserEntity> li=ue.findByPassword("wpMPkHl4");
//		      li.forEach(user->{
//		    	  System.out.println("USER"+user);
//		      });
		    
		System.out.println("Welcome to Library Project");
		
	}

}
