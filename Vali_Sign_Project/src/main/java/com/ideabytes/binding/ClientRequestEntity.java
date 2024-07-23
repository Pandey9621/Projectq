//package com.ideabytes.binding;
//
//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "client_request")
//public class ClientRequestEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//	private int userId;
//	private int appId;
//	@CreationTimestamp
//	@Column(name = "created", updatable = false)
//	private LocalDateTime created;
//	@UpdateTimestamp
//	@Column(name = "modified", insertable = false)
//	private LocalDateTime modified;
//	
//	private int clientRequest;
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public int getUserId() {
//		return userId;
//	}
//
//	public void setUserId(int userId) {
//		this.userId = userId;
//	}
//
//	public int getAppId() {
//		return appId;
//	}
//
//	public void setAppId(int appId) {
//		this.appId = appId;
//	}
//
//	public LocalDateTime getCreated() {
//		return created;
//	}
//
//	public void setCreated(LocalDateTime created) {
//		this.created = created;
//	}
//
//	public LocalDateTime getModified() {
//		return modified;
//	}
//
//	public void setModified(LocalDateTime modified) {
//		this.modified = modified;
//	}
//
//	public int getClientRequest() {
//		return clientRequest;
//	}
//
//	public void setClientRequest(int clientRequest) {
//		this.clientRequest = clientRequest;
//	}
//
//	
//
//}
