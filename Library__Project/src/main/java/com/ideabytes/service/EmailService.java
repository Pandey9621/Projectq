package com.ideabytes.service;

import jakarta.mail.MessagingException;

public interface EmailService {
	

		public void sendSimpleMessage(String to, String subject, String text);

//		public void sendHTMLMessage(String[] to, String subject, String html, String[] bcc, String cc[])
//				throws MessagingException;
//
//		public void sendMessageWithAttachment(String[] to, String subject, String text, String pathToAttachment,
//				String[] bcc, String cc[]) throws MessagingException;

	}



