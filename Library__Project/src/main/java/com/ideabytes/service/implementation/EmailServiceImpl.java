package com.ideabytes.service.implementation;
import java.io.File;
import com.ideabytes.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

	/**
	 * @author Admin
	 *
	 */
	@Component
	@Service
	public class EmailServiceImpl implements EmailService {

		 @Autowired
		private JavaMailSender emailSender;
		 
//		    public EmailServiceImpl(JavaMailSender emailSender) {
//		        this.emailSender = emailSender;
//		    }
		private String mailFrom = "vinayakbsb997@gmail.com";

		public void sendSimpleMessage(String to, String subject, String text) {

			SimpleMailMessage message = new SimpleMailMessage();
//			message.setFrom(mailFrom);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			System.out.println("Message "+message);
			emailSender.send(message);
			System.out.println("Message Sent");
			

		}

	}