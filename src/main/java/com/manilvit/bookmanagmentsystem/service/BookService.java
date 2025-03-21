package com.manilvit.bookmanagmentsystem.service;

import com.manilvit.bookmanagmentsystem.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Book createBook(Book book);

    Optional<Book> updateBook(Long id, Book book);

    boolean deleteBook(Long id);

    Page<Book> searchBooks(String title, String author, String genre, Pageable pageable);
}