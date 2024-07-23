package com.ideabytes.binding;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices")
public class DeviceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String deviceId;
	private String name;
	private String ipAddress;
	private String dataKey;
	private String location;
	private String type;
	private String identifier;
	@Column(columnDefinition = "text", length = 1)
	private String others;
	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	private boolean active;
	@CreationTimestamp
	@Column(name = "created", updatable = false)
	private LocalDateTime created;


	@UpdateTimestamp
	@Column(name = "modified", insertable = false)
	private LocalDateTime modified;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

//	public LocalDateTime getCreated() {
//		return created;
//	}

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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {

		String deviceString = "{\"id\":"+this.id+",\"deviceId\":"+this.deviceId+",\"name\":"+this.name+",\"ipAddress\":"+this.ipAddress+",\"dataKey\":"+this.dataKey+",\"location\":"+this.location+",\"type\":"+this.type+",\"identifier\":"+this.identifier+",\"others\":"+this.others
				+",\"active\":"+this.active+",\"created\":"+ this.created+",\"modified\":"+this.modified+"}";
		System.out.println("deviceString: "+deviceString);
		return deviceString;
	}

}
