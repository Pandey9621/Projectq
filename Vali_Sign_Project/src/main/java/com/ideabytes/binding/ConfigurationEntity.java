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
@Table(name="configurations")
public class ConfigurationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String ckey;
	private String cvalue;
	@Column(nullable = false, columnDefinition = "TINYINT", length = 1)
	private boolean active;

	@CreationTimestamp
	@Column(name = "created", updatable = false)
	private LocalDateTime created;
	@UpdateTimestamp
	@Column(name = "modified", insertable = false)
	private LocalDateTime modified;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		this.ckey = ckey;
	}

	public String getCvalue() {
		return cvalue;
	}

	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreateDateAndTime() {
		return created;
	}

	public void setCreateDateAndTime(LocalDateTime createDateAndTime) {
		this.created = createDateAndTime;
	}

	public LocalDateTime getUpdatedDateAndTime() {
		return modified;
	}

	public void setUpdatedDateAndTime(LocalDateTime updatedDateAndTime) {
		this.modified = updatedDateAndTime;
	}
	@Override
	public String toString() {
		return "{\"id\":" + this.id + ",\"name\":" + this.name + ",\"ckey\":" + this.ckey + ",\"cvalue\":"
				+ this.cvalue + ",\"modified\":"
				+ this.modified + ",\"created\":" + this.created + ",\"active\":" + this.active + "}";
	}
}
