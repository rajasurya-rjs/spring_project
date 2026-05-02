package com.example.library.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleIntegrityViolation(DataIntegrityViolationException ex, Model model) {
        String rootMessage = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();
        model.addAttribute("title", "Data Integrity Violation");
        model.addAttribute("message",
                "The operation could not be completed because it would break a database constraint "
                        + "(for example: a duplicate ISBN, or a missing author).");
        model.addAttribute("detail", rootMessage);
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("title", "Invalid Request");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("detail", "");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("title", "Unexpected Error");
        model.addAttribute("message", "Something went wrong while processing your request.");
        model.addAttribute("detail", ex.getMessage());
        return "error";
    }
}
