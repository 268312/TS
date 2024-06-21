package com.example.ts.controller.dto.BOOK;

public class DeleteBookDto {
    private Integer bookId;

    public DeleteBookDto(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
