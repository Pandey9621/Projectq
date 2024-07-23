package com.ideabytes.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ideabytes.binding.CurrentActionEntity;
import com.ideabytes.binding.TransactionEntity;
import com.ideabytes.commonService.RandomKey;
import com.ideabytes.constants.Constants;
import com.ideabytes.constants.ExceptionConstants;
import com.ideabytes.repository.CurrentActionRepository;
import com.ideabytes.repository.TransactionRepository;
import com.ideaytes.enumm.SourceEnum;

@Service
public class InitTransactionService {

	TransactionEntity transactionEntity;
	@Autowired
	TransactionRepository transactionRepo;
	@Autowired
	CurrentActionRepository CARepo;
	@Autowired
	RandomKey randomKey;
	@Value("${otp.digits}")
	private int otpDigits;

	private static final Logger log = LogManager.getLogger(InitTransactionService.class);

	public TransactionEntity transactionDataSave(JSONObject inputBody, int appId, int userId, String otp,
			String transactionStatus) {
		TransactionEntity transactionEntitySaveData = null;
		try {
			transactionEntity = new TransactionEntity();
			transactionEntity.setAdditionalDetails(inputBody.get("additionalInfo").toString());
			transactionEntity.setNewValue(inputBody.get("oldValue").toString());
			transactionEntity.setOldValue(inputBody.get("newValue").toString());
			transactionEntity.setSource(SourceEnum.CLIENT);
			transactionEntity.setTransaction(transactionStatus);
			transactionEntity.setUserId(userId);
			transactionEntity.setAppId(appId);
			transactionEntity.setCode(otp);
			transactionEntitySaveData = transactionRepo.save(transactionEntity);
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
		return transactionEntitySaveData;
	}

	public TransactionEntity initTransactionData(JSONObject transactionDataInput, int appId, int userId) {
		TransactionEntity transactionEntitySaveData = null;
		String otp = randomKey.generateRandomKey(this.otpDigits);
		try {
			String addlInfo = "";
			if (transactionDataInput.containsKey(Constants.ADDITIONALINFO)) {
				addlInfo = transactionDataInput.get(Constants.ADDITIONALINFO).toString() != ""
						? transactionDataInput.get(Constants.ADDITIONALINFO).toString()
						: "";
			}
			transactionEntity = new TransactionEntity();
			transactionEntity.setNewValue(transactionDataInput.get(Constants.NEWVALUE).toString());
			transactionEntity.setOldValue(transactionDataInput.get(Constants.OLDVALUE).toString());
			transactionEntity.setAdditionalDetails(addlInfo);
			transactionEntity.setTransaction(transactionDataInput.get(Constants.TRANSACTION).toString());
			transactionEntity.setSource(SourceEnum.CLIENT);
			transactionEntity.setAppId(appId);
			transactionEntity.setUserId(userId);
			transactionEntity.setCode(otp);
			transactionEntitySaveData = transactionRepo.save(transactionEntity);
			System.out.println("transactionEntitySaveData: " + transactionEntitySaveData);
			currectActionDataSave(transactionEntitySaveData, appId, userId, otp);
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();

		}
		return transactionEntitySaveData;
	}

	public void currectActionDataSave(TransactionEntity transData, int appId, int userId, String otp) {
		try {
			CurrentActionEntity checkAction = CARepo.findByUserIdAndAppId(userId, appId);
			if (checkAction != null) {
				checkAction.setAction(transData.toString());
				checkAction.setCode(otp);
				System.out.println("currect action data stored: ");
				CARepo.save(checkAction);
			} else {
				CurrentActionEntity CAE = new CurrentActionEntity();
				CAE.setAppId(appId);
				CAE.setUserId(userId);
				CAE.setCode(otp);
				CAE.setAction(transData.toString());
				System.out.println("currect action data stored: ");
				CARepo.save(CAE);
			}
		} catch (Exception e) {
			log.fatal(ExceptionConstants.EXCEPTIONGOTIN + e.getMessage());
			e.printStackTrace();
		}
	}

}
