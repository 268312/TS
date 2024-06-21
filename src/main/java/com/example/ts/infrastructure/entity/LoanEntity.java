package com.example.ts.infrastructure.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Optional;

@Entity
@Table(name = "loan", schema = "ksiazki")
public class LoanEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @Basic
    @Column(name = "loan_date", nullable = false)
    private Date loanDate;

    @Basic
    @Column(name = "due_date", nullable = false)
    private Date dueDate;
    @Basic
    @Column(name = "return_date")
    private Date returnDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BookEntity getBook() {
        return book;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }
}
