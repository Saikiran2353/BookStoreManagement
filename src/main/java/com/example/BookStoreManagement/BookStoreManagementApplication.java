package com.example.BookStoreManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@ComponentScan(basePackages = "com.example.BookStoreManagement")
public class BookStoreManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreManagementApplication.class, args);
	}

}
