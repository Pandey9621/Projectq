package com.ideabytes.response;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public class UserApplicationDevice {
    private Long userId;
    private Long appId;
    private Long deviceId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	@Override
	public String toString() {
		return "UserApplicationDevice [userId=" + userId + ", appId=" + appId + ", deviceId=" + deviceId + "]";
	}



}