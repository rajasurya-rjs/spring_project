package com.example.library.repository;

import com.example.library.dto.BookWithAuthor;
import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
            SELECT new com.example.library.dto.BookWithAuthor(
                b.id, b.title, b.isbn, b.price, a.name, a.nationality)
            FROM Book b INNER JOIN b.author a
            ORDER BY a.name, b.title
            """)
    List<BookWithAuthor> findAllBooksWithAuthor();
}
