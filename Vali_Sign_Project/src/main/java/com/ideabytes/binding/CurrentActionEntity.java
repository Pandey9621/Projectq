package com.ideabytes.binding;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "current_action")
public class CurrentActionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int userId;
	private int appId;
	@Column(columnDefinition = "TEXT")
	private String action;
	private String code;
	@CreationTimestamp
	@Column(name = "codeExpiry", updatable = true)
	private LocalDateTime codeExpiry;

	public LocalDateTime getCodeExpiry() {
		return codeExpiry;
	}

	public void setCodeExpiry(LocalDateTime codeExpiry) {
		this.codeExpiry = codeExpiry;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "{\"id\":" + this.id + ",\"userId\":" + this.userId + ",\"appId\":" + this.appId + ",\"code\":"
				+ this.code + ",\"codeExpiry\":" + this.codeExpiry + "}";
	}

}
