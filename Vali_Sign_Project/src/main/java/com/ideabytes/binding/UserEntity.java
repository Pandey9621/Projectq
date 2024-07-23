/*********************** Ideabytes Software India Pvt Ltd *********************                                 
* Here,This is a binding class for communicating with data base table.
* @Entity is using to map my class with db table name.
* @author  Chandan Pandey
* @version 20.0.1
* @since   2023-06-23.
*/
package com.ideabytes.binding;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "users")
//@NamedQuery(name = "User.findUsersByDeviceId",
//query = "SELECT u FROM users u where u.id in (SELECT du.user_id from devices_users du where du.device_id= ?1")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String userId;
	private String name;
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String phone;
	@Column(nullable = false, columnDefinition="BIT",length=1)
	private boolean active;
    @CreationTimestamp
	@Column(name = "created", updatable = false)
	private LocalDateTime created;
	@UpdateTimestamp
	@Column(name = "modified", insertable = false)
	private LocalDateTime modified;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String app;
	
//	 @ManyToMany
//	    @JoinTable(name = "user_applications",
//	               joinColumns = @JoinColumn(name = "user_id"),
//	               inverseJoinColumns = @JoinColumn(name = "app_id"))
//	    private List<UserApplicationEntity> applications;
//	@Transient
//	private String deviceName;
//	@Transient
//	private String  IPAddress;
//	@Transient
//	private String location;
//	@Transient
//	private String type;
//	@Transient
//	private String clientEmail;
//	@Transient
//	private String clientPhone;
	
	

     
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

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

	public Integer id() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String uuid) {
		this.userId = uuid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@Override
	public String toString() {
		return "{\"id\":" + this.id + ",\"userId\":" + this.userId + ",\"name\":" + this.name + ",\"email\":"
				+ this.email + ",\"password\":" + this.password + ",\"created\":" + this.created + ",\"active\":"
				+ this.active + ",\"phone\":" + this.phone + "}";
	}

//	public String getDeviceName() {
//		return deviceName;
//	}
//
//	public void setDeviceName(String deviceName) {
//		this.deviceName = deviceName;
//	}
//
//	public String getIPAddress() {
//		return IPAddress;
//	}
//
//	public void setIPAddress(String iPAddress) {
//		IPAddress = iPAddress;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public String getClientEmail() {
//		return clientEmail;
//	}
//
//	public void setClientEmail(String clientEmail) {
//		this.clientEmail = clientEmail;
//	}
//
//	public String getClientPhone() {
//		return clientPhone;
//	}
//
//	public void setClientPhone(String clientPhone) {
//		this.clientPhone = clientPhone;
//	}

}
