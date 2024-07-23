/*********************** Ideabytes Software India Pvt Ltd *********************                                 
* Here,This is a binding class for binding our classes with database table.
* @Entity is using to map my class with db table name.
* @author  Chandan Pandey
* @version 20.0.1
* @since   2023-06-23.
*/

package com.ideabytes.binding;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientEntity {

	@Id
//@Id annotaion is used for make our id as primary key.
//@GeneratedValue is used for genereating id automatically.
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	private String clientId;
	private String name;
	private String clientSecret;
//	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String email;
	private String phone;
	@Column(nullable = false, columnDefinition = "BIT", length = 1)
	private boolean active;

	@CreationTimestamp
	@Column(name = "created", updatable = false)
	private LocalDateTime created;

	@UpdateTimestamp
	@Column(name = "modified", insertable = false)
	private LocalDateTime modified;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="a_id",referencedColumnName="id")
	Set<UserApplicationEntity> user_app_entity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	@Override
	public String toString() {
		return "{\"id\":" + this.id + ",\"name\":" + this.name + ",\"clientId\":" + this.clientId + ",\"password\":"
				+ this.password + ",\"email\":" + this.email + ",\"phone\":" + this.phone + ",\"modified\":"
				+ this.modified + ",\"created\":" + this.created+ ",\"active\":" + this.active + "}";
	}


}
