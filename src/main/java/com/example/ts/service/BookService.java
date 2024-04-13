package com.example.ts.service;

import com.example.ts.infrastructure.entity.BookEntity;
import com.example.ts.infrastructure.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    public BookEntity getOne(Integer id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found."));
    }

    public List<BookEntity> getAllBooks(){
        return bookRepository.findAll();
    }

    public BookEntity addBook(BookEntity book){
        return bookRepository.save(book);
    }

    public void delete(Integer id){
        if(!bookRepository.existsById(id)){
            throw new RuntimeException("");
        }
        bookRepository.deleteById(id);

    }
}
