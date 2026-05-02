package com.example.library.repository;

import com.example.library.dto.BookWithAuthor;
import com.example.library.entity.Author;
import com.example.library.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.mode=never",
        "spring.jpa.defer-datasource-initialization=false"
})
class BookRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findAllBooksWithAuthor_returnsInnerJoinedRows() {
        Author orwell = em.persist(new Author("George Orwell", "British", 1903));
        Author austen = em.persist(new Author("Jane Austen", "British", 1775));
        // Author "C" with NO books -- inner join must drop them
        em.persist(new Author("Author With No Books", "Nowhere", 1900));

        em.persist(new Book("1984", "ISBN-A", new BigDecimal("9.99"), orwell));
        em.persist(new Book("Animal Farm", "ISBN-B", new BigDecimal("7.50"), orwell));
        em.persist(new Book("Pride and Prejudice", "ISBN-C", new BigDecimal("6.95"), austen));
        em.flush();

        List<BookWithAuthor> result = bookRepository.findAllBooksWithAuthor();

        assertThat(result).hasSize(3);
        // Ordered by author name then title -> Orwell's two (G < J), then Austen
        assertThat(result.get(0).getAuthorName()).isEqualTo("George Orwell");
        assertThat(result.get(0).getTitle()).isEqualTo("1984");
        assertThat(result.get(1).getAuthorName()).isEqualTo("George Orwell");
        assertThat(result.get(1).getTitle()).isEqualTo("Animal Farm");
        assertThat(result.get(2).getAuthorName()).isEqualTo("Jane Austen");
        assertThat(result.get(2).getTitle()).isEqualTo("Pride and Prejudice");

        // Author with no books must NOT appear (inner join semantics)
        assertThat(result).noneMatch(r -> r.getAuthorName().equals("Author With No Books"));
    }

    @Test
    void findAllBooksWithAuthor_emptyWhenNoBooks() {
        em.persist(new Author("Lonely Author", "Atlantis", 1900));
        em.flush();

        assertThat(bookRepository.findAllBooksWithAuthor()).isEmpty();
    }
}
