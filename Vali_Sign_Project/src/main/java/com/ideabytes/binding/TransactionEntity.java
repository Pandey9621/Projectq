package com.ideabytes.binding;

import java.time.Instant;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.ideaytes.enumm.SourceEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String transaction;
	@Column(columnDefinition = "TEXT")
	private String additionalDetails;
	@Column(columnDefinition = "TEXT")
	private String newValue;
	@Column(columnDefinition = "TEXT")
	private String oldValue;
	private int userId;
//	private int deviceId;
	private int appId;
	@CreationTimestamp
	@Column(name = "created", updatable = false)
//	private LocalDateTime created;
	private Instant created;
	@Enumerated(EnumType.STRING)
	private SourceEnum source;

	private String code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

//	public int getDeviceId() {
//		return deviceId;
//	}
//	public void setDeviceId(int deviceId) {
//		this.deviceId = deviceId;
//	}
	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public SourceEnum getSource() {
		return source;
	}

	public void setSource(SourceEnum source) {
		this.source = source;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "{\"oldValue\":" + this.oldValue + ",\"newValue\":" + this.newValue + ",\"transaction\":"
				+ this.transaction + ",\"userId\":" + this.userId + ",\"appId\":" + this.appId + ",\"source\":"
				+ this.source + "}";
	}

}
