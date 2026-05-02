package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock private BookRepository bookRepository;
    @Mock private AuthorRepository authorRepository;
    @InjectMocks private BookService bookService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Test Author", "Testland", 1980);
        author.setId(1L);
    }

    @Test
    void create_attachesAuthorAndPersists() {
        Book input = new Book("New Title", "ISBN-X", new BigDecimal("12.50"), null);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book saved = bookService.create(input, 1L);

        assertThat(saved.getAuthor()).isSameAs(author);
        assertThat(saved.getTitle()).isEqualTo("New Title");
        verify(bookRepository).save(input);
    }

    @Test
    void create_unknownAuthor_throws() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.create(new Book(), 99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Author not found");
        verify(bookRepository, never()).save(any());
    }

    @Test
    void update_overwritesFieldsAndSaves() {
        Book existing = new Book("Old", "ISBN-OLD", new BigDecimal("5.00"), author);
        existing.setId(7L);
        Book updates = new Book("New", "ISBN-NEW", new BigDecimal("9.99"), null);

        when(bookRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.update(7L, updates, 1L);

        assertThat(result.getTitle()).isEqualTo("New");
        assertThat(result.getIsbn()).isEqualTo("ISBN-NEW");
        assertThat(result.getPrice()).isEqualByComparingTo("9.99");
        assertThat(result.getAuthor()).isSameAs(author);
    }

    @Test
    void update_missingBook_throws() {
        when(bookRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.update(404L, new Book(), 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void findAllJoined_delegatesToRepository() {
        when(bookRepository.findAllBooksWithAuthor()).thenReturn(java.util.List.of());

        bookService.findAllJoined();

        verify(bookRepository).findAllBooksWithAuthor();
    }
}
