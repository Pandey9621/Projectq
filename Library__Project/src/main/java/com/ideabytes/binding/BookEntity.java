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
@Table(name="book")
public class BookEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookid;
	private String book;					
    private String author;
	private String language;
	private Integer firstpublished;	
	private Integer volumesold;
	private Integer price;
	private Integer copies;
	private String 	genre;
	private String books;
	public Integer getBookid() {
		return bookid;
	}
	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Integer getFirstpublished() {
		return firstpublished;
	}
	public void setFirstpublished(Integer firstpublished) {
		this.firstpublished = firstpublished;
	}
	public Integer getVolumesold() {
		return volumesold;
	}
	public void setVolumesold(Integer volumesold) {
		this.volumesold = volumesold;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getCopies() {
		return copies;
	}
	public void setCopies(Integer copies) {
		this.copies = copies;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	@Override
	public String toString() {
		return "BookEntity [bookid=" + bookid + ",book=" + book + ", author=" + author + ", language=" + language
				+ ", firstpublished=" + firstpublished + ", volumesold=" + volumesold + ", price=" + price + ", copies="
				+ copies + ", genre=" + genre + "]";
	}
	
}