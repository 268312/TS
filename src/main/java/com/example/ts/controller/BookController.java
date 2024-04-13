package com.example.ts.controller;

import com.example.ts.infrastructure.entity.BookEntity;
import com.example.ts.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody BookEntity addBook(@RequestBody BookEntity book){
        return bookService.addBook(book);
    }


    @GetMapping("/getAll")
    public @ResponseBody Iterable<BookEntity> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/getOne/{id}")
    public @ResponseBody BookEntity getOne(@PathVariable Integer id){
        return bookService.getOne(id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void delete(@PathVariable Integer id){
        bookService.delete(id);
    }
}
