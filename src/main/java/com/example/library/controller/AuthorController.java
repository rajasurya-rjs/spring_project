package com.example.library.controller;

import com.example.library.entity.Author;
import com.example.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("mode", "new");
        return "authors/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("author") Author author,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "new");
            return "authors/form";
        }
        authorService.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        model.addAttribute("mode", "edit");
        return "authors/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("author") Author author,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "authors/form";
        }
        authorService.update(id, author);
        return "redirect:/authors";
    }
}
