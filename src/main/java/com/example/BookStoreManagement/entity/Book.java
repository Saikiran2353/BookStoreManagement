package com.example.BookStoreManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long  id;
	private String bookName;
	private String bookAuthor;
	private long bookPrice;
	private String bookAuthorGitHubLink;
	private int followersCount;
	
	
	
	
	public Book() {
		
	}
	
	



	public Book(Long id, String bookName, String bookAuthor, long bookPrice, String bookAuthorGitHubLink,
			int followersCount) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPrice = bookPrice;
		this.bookAuthorGitHubLink = bookAuthorGitHubLink;
		this.followersCount = followersCount;
	}





	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	

	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}





	public long getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(long bookPrice) {
		this.bookPrice = bookPrice;
	}



	public String getBookAuthorGitHubLink() {
		return bookAuthorGitHubLink;
	}
	public void setBookAuthorGitHubLink(String bookAuthorGitHubLink) {
		this.bookAuthorGitHubLink = bookAuthorGitHubLink;
	}



	public int getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}



	@Override
	public String toString() {
		return "Book [id=" + id + ", bookName=" + bookName + ", bookAuthor=" + bookAuthor + ", bookPrice=" + bookPrice
				+ ", bookAuthorGitHubLink=" + bookAuthorGitHubLink + ", followersCount=" + followersCount + "]";
	}
	
	
}
