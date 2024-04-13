package com.example.ts.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book", schema = "ksiazki")
public class BookEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic
    @Column(name = "isbn")
    private String isbn;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "author")
    private String author;

    @Basic
    @Column(name = "publisher")
    private String publisher;

    @Basic
    @Column(name = "publish_year")
    private Integer publishYear;

    @Basic
    @Column(name = "available_copies")
    private Integer availableCopies;

    //@OneToMany(mappedBy = "book")
    //private List<Loan> loans;

    public void setId(Integer id) {
        this.id = id;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setAvailableCopies(Integer avaiableCopies) {
        this.availableCopies = avaiableCopies;
    }
    public Integer getId() {
        return id;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public Integer getPublishYear() {
        return publishYear;
    }
    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }
    public Integer getAvailableCopies() {
        return availableCopies;
    }
}
