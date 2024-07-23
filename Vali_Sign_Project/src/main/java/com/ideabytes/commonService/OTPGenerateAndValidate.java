package com.ideabytes.commonService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideabytes.binding.CurrentActionEntity;
import com.ideabytes.repository.CurrentActionRepository;

@Service
public class OTPGenerateAndValidate {
	@Autowired
	private CurrentActionRepository currentActionRepo;
	@Autowired
	RandomKey randomKey;
	private static final Logger log = LogManager.getLogger(OTPGenerateAndValidate.class);
	@Transactional
	public int validateOTP(String enteredOTP, int timeInSeconds, int userId, int appId) {
		int otpValidation = 0;
		try {
			System.out.println("The valid otp time: " + timeInSeconds + " :: otp: " + enteredOTP + ":: userId: "
					+ userId + " appId: " + appId);

			CurrentActionEntity currentActionData = currentActionRepo.findByUserIdAndAppId(userId, appId);
			String otpdata = currentActionData.getCode();
			LocalDateTime dateTime = currentActionData.getCodeExpiry();
			LocalDateTime currentTime = LocalDateTime.now();
			System.out.println("otpdata: " + otpdata + " enteredOTP: " + enteredOTP);

			if (otpdata.equalsIgnoreCase(enteredOTP)) {
				if (currentTime.minusSeconds(timeInSeconds).isBefore(dateTime)) {
//				if (otpExpirationTime.isBefore(currentTime)) {
					// one means OTP validate successful
					otpValidation = 1;
					currentActionDelete(currentActionData, userId, appId);
//					clientRequestSave(4);
					System.out.println("OTP validate successful");
				} else {
					// zero means time expired
					otpValidation = 0;
					// Below line is replacing the OTP value to OTP used in database because for
					// second time it wont use one it is successful
//					clientRequestSave(3);
					System.out.println("zero means time expired");
				}
			} else {
				// two means invalid OTP
				otpValidation = 2;
			}
			System.out.println("otpValidation: " + otpValidation);
		} catch (Exception e) {
			log.fatal("Exception got in validateOTP: " + e.getMessage());
			e.printStackTrace();
		}
		return otpValidation;
	}

	@Transactional
	public void currentActionDelete(CurrentActionEntity currentActionData, int userId, int appId) {
		try {
			int delete = currentActionRepo.deleteByUserIdAndAppId(userId, appId);
			log.info("The client acton data is deleted. "+delete);
		} catch (Exception e) {
			log.fatal("Exception got in currentActionSave: " + e.getMessage());
			e.printStackTrace();
		}
	}

//	private void clientRequestSave(int requestNum) {
//		try {
//			ClientRequestEntity clientRequest = clientReqRepo.findByUserIdAndAppId(this.userId, this.appId);
//			clientRequest.setId(clientRequest.getId());
//			clientRequest.setClientRequest(requestNum);
//			clientReqRepo.save(clientRequest);
//			log.fatal("The client request data is updated: " + clientReqRepo);
//		} catch (Exception e) {
//			log.fatal("Exception got in clientRequestSave: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}

//	public static void main(String[] args) {
//		String inputDateTimeString = "2023-09-09T13:50:12.602579";
//		LocalDateTime currentTime = LocalDateTime.now();
//		System.out.println(
//				"currentTime: " + currentTime.getSecond() + " " + inputDateTimeString + " inputDateTimeString");
//		// Define the input and output format patterns
//		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
//		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//
//		try {
//			// Parse the input string into a LocalDateTime object
//			LocalDateTime dateTime = LocalDateTime.parse(currentTime.toString(), inputFormatter);
//
//			// Format the LocalDateTime object into the desired format
//			String formattedDateTime = dateTime.format(outputFormatter);
//
//			// Print the formatted date and time
//			System.out.println("Formatted DateTime: " + formattedDateTime);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
