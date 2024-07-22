package com.example.service;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import com.example.entity.EmailEntity;
import org.springframework.core.io.FileSystemResource;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;
	

//	    @Autowired
//	    public EmailService(JavaMailSender javaMailSender) {
//	        this.javaMailSender = javaMailSender;
//	    }
	
    public String sendSimpleMail(EmailEntity details) {
       try {
           SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());
			System.out.println("CHANDAN PANDEY");
		    javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			return e.getMessage();
		}
	}
    public String sendMailAttachment(String recipient, String subject, String body, List<MultipartFile> file)
			throws MessagingException, IOException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(sender);
		helper.setTo(recipient);
		helper.setSubject(subject);
		helper.setText(body);
		for (MultipartFile files : file) {
		InputStreamSource source = new ByteArrayResource(files.getBytes());
		helper.addAttachment(files.getOriginalFilename(), source);
		}
        javaMailSender.send(message);
        return "Mail sent succefully with attachment";

	}
	}
	

