package com.example.library.service;

import com.example.library.dto.BookWithAuthor;
import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<BookWithAuthor> findAllJoined() {
        return bookRepository.findAllBooksWithAuthor();
    }

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: id=" + id));
    }

    public Book create(Book book, Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found: id=" + authorId));
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public Book update(Long id, Book updated, Long authorId) {
        Book existing = findById(id);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found: id=" + authorId));
        existing.setTitle(updated.getTitle());
        existing.setIsbn(updated.getIsbn());
        existing.setPrice(updated.getPrice());
        existing.setAuthor(author);
        return bookRepository.save(existing);
    }
}
