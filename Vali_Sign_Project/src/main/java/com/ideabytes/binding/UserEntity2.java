package com.ideabytes.binding;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="users2")
public class UserEntity2 {
	
	//@NamedQuery(name = "User.findUsersByDeviceId",
	//query = "SELECT u FROM users u where u.id in (SELECT du.user_id from devices_users du where du.device_id= ?1")

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer id;
		private String userId; 
	    private String name;
	    private String email;
		private String password;
	    private String phone;
	    private String cname;
		private String app;
	    public String getApp() {
			return app;
		}
		public void setApp(String app) {
			this.app = app;
		}
	
	    @Column(nullable = false, columnDefinition = "BIT", length = 1)
		private boolean active;

		@CreationTimestamp
		@Column(name="created",updatable=false)
		private LocalDateTime created;
	    @UpdateTimestamp
		@Column(name="modified",insertable=false)
		private LocalDateTime modified;
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
		public String getCname() {
			return cname;
		}
		public void setCname(String cname) {
			this.cname = cname;
		}
		public boolean isActive() {
			return active;
		}
		public void setActive(boolean active) {
			this.active = active;
		}
		@Override
		public String toString() {
			return "UsersEntity [id=" + id + ", userId=" + userId + ", name=" + name + ", email=" + email + ", password="
					+ password + ", phone=" + phone + ", created=" + created + ", modified=" + modified + "]";
		}
		
		
		

	}

	


