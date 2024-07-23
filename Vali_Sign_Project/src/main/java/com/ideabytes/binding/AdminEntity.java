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
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Data
public class AdminEntity {

	@Id
//@Id annotaion is used for make our id as primary key.
//@GeneratedValue is used for genereating id automatically.
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	private String name;
	private String email;
	public String password;

	@Column(nullable=false,columnDefinition = "BIT",length = 1)
	private boolean active;
	@CreationTimestamp
	@Column(name = "created", updatable = false)
	private LocalDateTime created;
    @UpdateTimestamp
	@Column(name = "modified", insertable = false)
	private LocalDateTime modified;
	private String adminLevel;

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public boolean isActive() {
//		return active;
//	}
//
//	public void setActive(boolean active) {
//		this.active = active;
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
//	public String getAdminLevel() {
//		return adminLevel;
//	}
//
//	public void setAdminLevel(String adminLevel) {
//		this.adminLevel = adminLevel;
//	}
//
//	@Override
//	public String toString() {
//		return "{\"id\":" + this.id + ",\"name\":" + this.name + ",\"email\":" + this.email + ",\"password\":"
//				+ this.password + ",\"active\":" + this.active + ",\"created\":" + this.created + ",\"modified\":"
//				+ this.modified + ",\"adminLevel\":" + this.adminLevel + "}";
//	}

}
