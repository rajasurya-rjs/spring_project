package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock private AuthorRepository authorRepository;
    @InjectMocks private AuthorService authorService;

    @Test
    void findById_returnsAuthor() {
        Author a = new Author("X", "Y", 1900);
        a.setId(5L);
        when(authorRepository.findById(5L)).thenReturn(Optional.of(a));

        assertThat(authorService.findById(5L)).isSameAs(a);
    }

    @Test
    void findById_notFound_throws() {
        when(authorRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.findById(404L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Author not found");
    }

    @Test
    void update_overwritesAndSaves() {
        Author existing = new Author("Old", "OldLand", 1900);
        existing.setId(3L);
        Author changes = new Author("New", "NewLand", 1999);

        when(authorRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author result = authorService.update(3L, changes);

        assertThat(result.getName()).isEqualTo("New");
        assertThat(result.getNationality()).isEqualTo("NewLand");
        assertThat(result.getBirthYear()).isEqualTo(1999);
    }
}
