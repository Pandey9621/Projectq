//package com.ideabytes.binding;
//
//import java.sql.Date;
//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "otp_validate")
//public class OTPValidate {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//	private Integer userId;
//	@CreationTimestamp
//	@Column(name = "otpRequestedTime", updatable = true)
//	private LocalDateTime otpRequestedTime;
//	
//	private String oneTimePassword;
//
//	
//	public LocalDateTime getOtpRequestedTime() {
//		return otpRequestedTime;
//	}
//
//	public void setOtpRequestedTime(LocalDateTime otpRequestedTime) {
//		this.otpRequestedTime = otpRequestedTime;
//	}
//
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getOneTimePassword() {
//		return oneTimePassword;
//	}
//
//	public void setOneTimePassword(String oneTimePassword) {
//		this.oneTimePassword = oneTimePassword;
//	}
//
//
//	public int getUserId() {
//		return userId;
//	}
//
//	public void setUserId(int userId) {
//		this.userId = userId;
//	}
//
//	
//
//}
