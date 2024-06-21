package com.example.ts.service;

import com.example.ts.controller.dto.BOOK.GetBookDto;
import com.example.ts.infrastructure.entity.BookEntity;
import com.example.ts.infrastructure.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public List<GetBookDto> getAllBooks() {

        var books =  bookRepository.findAll();
        return StreamSupport.stream(books.spliterator(), false)
                .map(this::mapBook).collect(Collectors.toList());
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
    public GetBookDto getById(Integer id){
        var bookEntity = bookRepository.findById(id).orElseThrow();
        return mapBook(bookEntity);
    }
    public GetBookDto mapBook(BookEntity bookEntity){

        return new GetBookDto(bookEntity.getId(),
                bookEntity.getIsbn(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getPublisher(),
                bookEntity.getPublishYear(),
                bookEntity.getAvailableCopies());
    }
}
