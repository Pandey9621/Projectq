package com.ideabytes.binding;



//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Setter;


@Entity
@Table(name="Library")
public class LibraryEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libraryname;
    private String city;
    private String country;
    public int getId() {
        return id;
    }
    public String getLibraryname() {
        return libraryname;
    }
    public void setLibraryname(String libraryname) {
        this.libraryname = libraryname;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    @Override
	public String toString() {
		return "LibraryEntity [id=" + id + ", libraryname=" + libraryname + ", city=" + city + ", country=" + country
				+ "]";
	}


}
