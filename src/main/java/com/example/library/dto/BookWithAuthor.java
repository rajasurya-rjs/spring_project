package com.example.library.dto;

import java.math.BigDecimal;

public class BookWithAuthor {
    private final Long bookId;
    private final String title;
    private final String isbn;
    private final BigDecimal price;
    private final String authorName;
    private final String authorNationality;

    public BookWithAuthor(Long bookId, String title, String isbn, BigDecimal price,
                          String authorName, String authorNationality) {
        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.authorName = authorName;
        this.authorNationality = authorNationality;
    }

    public Long getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public BigDecimal getPrice() { return price; }
    public String getAuthorName() { return authorName; }
    public String getAuthorNationality() { return authorNationality; }
}
