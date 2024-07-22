package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.EmailEntity;
import com.example.service.EmailService;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;

@RestController
public class EmailController {
	@Autowired
	private EmailService emailService;
//	@PostConstructor
//      public EmailController(EmailService emailService) 
//      {
//	    this.emailService=emailService;
//	}

	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailEntity details) {
//		emailService=new EmailService();
		String status = emailService.sendSimpleMail(details);
		return status;
	}

	@PostMapping("/sendMailAttachment")
	public String sendMailAttachment(@RequestParam String recipient,@RequestParam String subject,@RequestParam String body, @RequestParam List<MultipartFile> file) throws MessagingException, IOException
	{       return emailService.sendMailAttachment(recipient, subject, body, file);
		
	}
}
