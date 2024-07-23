/* Name: EmailServiceImpl.java
 * Project: ValiSign
 * Version: 0.2.0
 * Description: This class handles implementations of mailing service
 * Created Date: 2023-08-11
 * Developed By: Siddish Gollapelli
 * Modified Date: 2023-08-11
 * Modified By: Siddish Gollapelli
 * 
 */
package com.ideabytes.service.implementations;

import java.io.File;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import com.ideabytes.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

	private String mailFrom = "ValiSign Admin<admin@aitestpro.com>";

	public void sendSimpleMessage(String[] to, String subject, String text, String[] bcc, String cc[]) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mailFrom);
		message.setTo(to);
		if (bcc.length > 0)
			message.setBcc(bcc);
		if (cc.length > 0)
			message.setCc(cc);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);

	}

	public void sendHTMLMessage(String[] mailTo, String subject, String html, String[] bcc, String cc[]) {
		try {
			MimeMessage message = emailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
			helper.setFrom(mailFrom);
			helper.setTo(mailTo);
			if (bcc.length > 0)
				helper.setBcc(bcc);
			if (cc.length > 0)
				helper.setCc(cc);
			helper.setSubject(subject);
			// message.setContent(html, "text/html"); /** Use this or below line **/
			helper.setText(html, true); // Use this or above line.
			// helper.setText(html,"");

//    	        FileSystemResource file 
//    	          = new FileSystemResource(new File(pathToAttachment));
//    	        helper.addAttachment("Attachment", file);

			emailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessageWithAttachment(String[] to, String subject, String text, String pathToAttachment,
			String[] bcc, String cc[]) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(mailFrom);
		helper.setTo(to);
		if (bcc.length > 0)
			helper.setBcc(bcc);
		if (cc.length > 0)
			helper.setCc(cc);
		helper.setSubject(subject);
		helper.setText(text);

		FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
		helper.addAttachment("Attachment", file);

		emailSender.send(message);

	}

}
