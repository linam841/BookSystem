package com.manilvit.bookmanagmentsystem.service;


import com.manilvit.bookmanagmentsystem.model.Book;
import com.manilvit.bookmanagmentsystem.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all books", e);
        }
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        try {
            return bookRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Book not found with id " + id, e);
        }
    }

    @Override
    public Book createBook(Book book) {
        try {
            // Можно добавить проверку на существование книги по названию
            if(bookRepository.existsByTitle(book.getTitle())) {
                throw new RuntimeException("Book with title '" + book.getTitle() + "' already exists");
            }
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create book", e);
        }
    }

    @Override
    public Optional<Book> updateBook(Long id, Book book) {
        try {
            if (bookRepository.existsById(id)) {
                // Устанавливаем id, чтобы обновить именно существующую книгу
                book.setId(id);
                return Optional.of(bookRepository.save(book));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update book with id " + id, e);
        }
    }

    @Override
    public boolean deleteBook(Long id) {
        try {
            if (bookRepository.existsById(id)) {
                bookRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete book with id " + id, e);
        }
    }

    @Override
    public Page<Book> searchBooks(String title, String author, String genre, Pageable pageable) {
        try {
            title = (title != null) ? title : "";
            author = (author != null) ? author : "";
            genre = (genre != null) ? genre : "";
            return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
                    title, author, genre, pageable);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search books", e);
        }
    }
}