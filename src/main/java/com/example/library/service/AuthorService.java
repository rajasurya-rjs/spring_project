package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found: id=" + id));
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Author update(Long id, Author updated) {
        Author existing = findById(id);
        existing.setName(updated.getName());
        existing.setNationality(updated.getNationality());
        existing.setBirthYear(updated.getBirthYear());
        return authorRepository.save(existing);
    }
}
