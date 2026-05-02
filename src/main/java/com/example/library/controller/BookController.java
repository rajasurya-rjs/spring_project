package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAllJoined());
        return "books/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("mode", "new");
        return "books/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("book") Book book,
                         BindingResult bindingResult,
                         @RequestParam(value = "authorId", required = false) Long authorId,
                         Model model) {
        if (authorId == null) {
            bindingResult.rejectValue("author", "author.required", "Please select an author");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("mode", "new");
            return "books/form";
        }
        bookService.create(book, authorId);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("selectedAuthorId", book.getAuthor().getId());
        model.addAttribute("mode", "edit");
        return "books/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("book") Book book,
                         BindingResult bindingResult,
                         @RequestParam(value = "authorId", required = false) Long authorId,
                         Model model) {
        if (authorId == null) {
            bindingResult.rejectValue("author", "author.required", "Please select an author");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("selectedAuthorId", authorId);
            model.addAttribute("mode", "edit");
            return "books/form";
        }
        bookService.update(id, book, authorId);
        return "redirect:/books";
    }
}
