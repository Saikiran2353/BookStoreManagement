package com.example.BookStoreManagement.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BookStoreManagement.entity.Book;
import com.example.BookStoreManagement.repository.BookRepo;
import com.example.BookStoreManagement.service.GitHubService;


@RestController
@RequestMapping("/BookStore")
public class Controller {
	
	@Autowired
    private BookRepo bookRepo;
	
	@Autowired
    private GitHubService gitHubService;



	
	@PostMapping("/addBook/")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
		
	return new ResponseEntity<>(bookRepo.save(book),HttpStatus.CREATED);	
	}
	
	
	 @GetMapping("/searchBooks/")
	   public ResponseEntity<List<Book>> searchBooks(@RequestParam("query") String query){
	        return ResponseEntity.ok(bookRepo.searchBooks(query));
	        
	    }
	
		
	
	
	@GetMapping("/getBook/{id}")
	public ResponseEntity<Book> getBook(@PathVariable long id){
		Optional<Book> book = bookRepo.findById(id);
		if(book.isPresent()) {
			return new ResponseEntity<>(book.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PutMapping("/updateBook/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable long id,@RequestBody Book book1){
		Optional<Book> book = bookRepo.findById(id);
		if(book.isPresent()) {
			book.get().setBookName(book1.getBookName());
			book.get().setBookAuthor(book1.getBookAuthor());
			book.get().setBookPrice(book1.getBookPrice());
			return new ResponseEntity<>(bookRepo.save(book.get()),HttpStatus.OK);
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/deleteBook/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable long id){
		Optional<Book> book = bookRepo.findById(id);
		if(book.isPresent()) {
			bookRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@GetMapping("/getAllBooksWithFollowersCount/")
    public ResponseEntity<List<Book>> getAllBooksWithFollowersCount() {
        List<Book> books = bookRepo.findAll();

        List<CompletableFuture<Void>> futures = books.stream()
              // .filter(book -> book.getBookAuthorGitHubLink() != null)
                .map(book -> {
                    CompletableFuture<Void> no_followers = CompletableFuture.runAsync(() -> {
                    	String userName = book.getBookAuthor();
                    	book.setBookAuthorGitHubLink("https://api.github.com/"+userName);
                    	int followersCount = gitHubService.getFollowersCount(userName);
                    	//book.setBookAuthorGitHubLink(userName);
                        book.setFollowersCount(followersCount);
                        bookRepo.save(book);
                    });
                    return no_followers;
                })
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();
         
       
        return ResponseEntity.ok(books);
    }
	



    
  @GetMapping("/getBookDetailsByIds/")
    public ResponseEntity<List<Book>> getBooksDetailsByIds(@RequestParam String bookIds) {
        String[] idArray = bookIds.split(",");
        List<Long> idList = Arrays.stream(idArray)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<CompletableFuture<Book>> futures = idList.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> bookRepo.findById(id)))
                .map(future -> future.thenApplyAsync(Optional::get))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        CompletableFuture<List<Book>> mergedFuture = allOf.thenApplyAsync(v ->
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );

        List<Book> mergedBooks = mergedFuture.join();

        return ResponseEntity.ok(mergedBooks);
    }
	
}
	
