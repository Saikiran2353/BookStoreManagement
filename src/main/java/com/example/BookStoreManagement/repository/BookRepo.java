package com.example.BookStoreManagement.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.BookStoreManagement.entity.Book;


public interface BookRepo extends JpaRepository<Book, Long>{

	@Query( "SELECT p FROM Book p WHERE " +
            "p.bookName LIKE CONCAT('%',:query, '%')" +
			 "Or p.bookAuthor LIKE CONCAT('%', :query, '%')")         
    List<Book> searchBooks(String query);
}
