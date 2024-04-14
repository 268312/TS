package com.example.ts.controller;

import com.example.ts.infrastructure.entity.BookEntity;
import com.example.ts.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling operations related to books
 */
@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    /**
     * Constructor for BookController
     * @param bookService The service for handling book operations
     */
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Adding a new book
     * @param book The book to add
     * @return ResponseEntity containing the added book
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<?> addBook(@RequestBody BookEntity book){
        try {
            BookEntity addedBook = bookService.addBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add book: " + e.getMessage());
        }
    }

    /**
     * Retrieving all books
     * @return ResponseEntity containing a list of all books
     */
    @GetMapping("/getAll")
    public @ResponseBody ResponseEntity<?> getAllBooks(){
        try {
            Iterable<BookEntity> allBooks = bookService.getAllBooks();
            return ResponseEntity.ok(allBooks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve books: " + e.getMessage());
        }
    }

    /**
     * Retrieving a book
     * @param id the id of the book to be retrieved
     * @return ResponseEntity containing the book
     */
    @GetMapping("/getOne/{id}")
    public @ResponseBody ResponseEntity<?> getOne(@PathVariable Integer id){
        try {
            BookEntity book = bookService.getOne(id);
            if (book != null) {
                return ResponseEntity.ok(book);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve book: " + e.getMessage());
        }
    }

    /**
     * Deleting a book
     * @param id the id of the book to be deleted
     * @return ResponseEntity indicating the result of the deletion
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable Integer id){
        try {
            bookService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete book: " + e.getMessage());
        }
    }
}
