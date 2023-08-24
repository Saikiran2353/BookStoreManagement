package com.example.BookStoreManagement.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BookStoreManagement.entity.Book;
import com.example.BookStoreManagement.repository.BookRepo;
import com.example.BookStoreManagement.service.BookService;




@Service
public class BookServiceImpl implements BookService {
	
	   @Autowired
	   private BookRepo bookRepo;
	  
	   

	    public BookServiceImpl(BookRepo bookRepo) {
		
		this.bookRepo = bookRepo;
	}

	
	
		@Override
		public List<Book> searchBooks(String query){
			List<Book> books = bookRepo.searchBooks(query);
	        return books;
		}

	

}
