/*********************** Ideabytes Software India Pvt Ltd *********************                                 
* Here,This is a binding class for binding our classes with database table.
* @Entity is using to map my class with db table name.
* @author  Chandan Pandey
* @version 20.0.1
* @since   2023-06-23.
*/

package com.ideabytes.binding;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Entity;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;
@Entity
@Table(name="user")
@Data
public class UserEntity {
	
	@Id
//@Id annotaion is used for make our id as primary key.
//@GeneratedValue is used for genereating id automatically.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userid;
	private String name;
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String dob;
	private String datetime;
	private String email;
	
//	getId() is used for getting the id.
//	Using setter and getter method for assign and taking the value from method.
// here setId is used for assign  the id.
//	getDob() is used for getting  the dob.
	public String getDob() {
		return dob;
	}
public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	//	setDob() is used for assign  the dob.
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getName() {
		return name;
	}
//	setName() is used for assign  the setName.
	public void setName(String name) {
		this.name = name;
	}

//	setPassword is used for assign  the Password.
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return  password;
	}
//	getDatetime() is used for assign  the getDate and time.
	public String getDatetime() {
		return datetime;
	}
//	setDatetime() is used for assign  the Date and time.
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	public String getEmail() {
		return email;
		
	}
	@Override
	public String toString() {
		return "UserEntity [userid=" + userid + ", name=" + name + ", password=" + password + ", dob=" + dob
				+ ", datetime=" + datetime + ", email=" + email + "]";
	}
	
	
	
}
