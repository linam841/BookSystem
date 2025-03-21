package com.manilvit.bookmanagmentsystem.controller;

import com.manilvit.bookmanagmentsystem.dto.BookDTO;
import com.manilvit.bookmanagmentsystem.dto.BookMapper;
import com.manilvit.bookmanagmentsystem.model.Book;
import com.manilvit.bookmanagmentsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for managing books.
 * Provides endpoints for retrieving, creating, updating, and deleting books.
 */
@Controller
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    /**
     * Retrieves all books.
     * Accessible only to authenticated users.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDTO> bookDTOs = books.stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves a book by its ID.
     * Accessible only to authenticated users.
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);

        return book.map(b -> new ResponseEntity<>(bookMapper.toDTO(b), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates a new book.
     * Accessible only to users with the ADMIN role.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Book createdBook = bookService.createBook(book);
        BookDTO createdBookDTO = bookMapper.toDTO(createdBook);
        return new ResponseEntity<>(createdBookDTO, HttpStatus.CREATED);
    }

    /**
     * Updates an existing book by its ID.
     * Accessible only to users with the ADMIN role.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Optional<Book> updatedBook = bookService.updateBook(id, book);

        return updatedBook.map(b -> new ResponseEntity<>(bookMapper.toDTO(b), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a book by its ID.
     * Accessible only to users with the ADMIN role.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBook(id);

        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}