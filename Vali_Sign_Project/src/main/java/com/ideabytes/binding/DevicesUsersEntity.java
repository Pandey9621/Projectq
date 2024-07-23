/*********************** Ideabytes Software India Pvt Ltd *********************                                 
* Here,This is a binding class for binding our classes with database table.
* @Entity is using to map my class with db table name.
* @author  Chandan Pandey
* @version 20.0.1
* @since   2023-06-23.
*/

package com.ideabytes.binding;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices_users")
public class DevicesUsersEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer deviceId;
	private Integer userId;
	private String dataKey;
	private String sessionKey;
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	private boolean active;
	@CreationTimestamp
	@Column(name = "created", updatable = false)
	private LocalDateTime created;
	@UpdateTimestamp
	@Column(name = "modified", insertable = false)
	private LocalDateTime modified;
//	@OneToOne(targetEntity =DeviceEntity.class,cascade=CascadeType.ALL)
//    @JoinColumn(referencedColumnName="id")
//     private DeviceEntity entity;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
//
//	public LocalDateTime getCreated() {
//		return created;
//	}
//
//	public void setCreated(LocalDateTime created) {
//		this.created = created;
//	}
//
//	public LocalDateTime getUpdated() {
//		return modified;
//	}
//
//	public void setUpdated(LocalDateTime updated) {
//		this.modified = updated;
//	}

	@Override
	public String toString() {
		return "{\"id\":" + this.id + ",\"deviceId\":" + this.deviceId + ",\"userId\":" + this.userId + ",\"dataKey\":"
				+ this.dataKey + ",\"modified\":" + this.modified + ",\"created\":" + this.created + ",\"active\":"
				+ this.active + ",\"sessionKey\":" + this.sessionKey + ",\"dataKey\":" + this.dataKey
				+ ",\"lastLogin\":" + this.lastLogin + "}";
	}
}
