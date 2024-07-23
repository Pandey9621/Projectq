/* Name: EmailService.java
 * Project: Aitestpro (earlier Tracer)-Tracer2
 * Version: 0.6.5
 * Description: This class handles the player to run script for the given options like tool(Selenium) and logs the steps
 * Created Date: 2022-03-08
 * Developed By: Siddish Gollapelli
 * Modified Date: 01-November-2021
 * Modified By: Siddish Gollapelli
 * 
 */
package com.ideabytes.service;

import jakarta.mail.MessagingException;

/**
 * @author Admin
 *
 */
public interface EmailService {

	public void sendSimpleMessage(String[] to, String subject, String text, String[] bcc, String cc[]);

	public void sendHTMLMessage(String[] to, String subject, String html, String[] bcc, String cc[])
			throws MessagingException;

	public void sendMessageWithAttachment(String[] to, String subject, String text, String pathToAttachment,
			String[] bcc, String cc[]) throws MessagingException;

}
