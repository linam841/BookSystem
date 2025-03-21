package com.manilvit.bookmanagmentsystem.service;

import com.manilvit.bookmanagmentsystem.model.Book;
import com.manilvit.bookmanagmentsystem.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link BookService} interface for managing books.
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Retrieves all books.
     *
     * @return a list of all books
     */
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book
     * @return an optional containing the book if found, or empty if not
     */
    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Creates a new book.
     *
     * @param book the book to be created
     * @return the created book
     * @throws RuntimeException if a book with the same title already exists
     */
    @Override
    public Book createBook(Book book) {
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new RuntimeException("Book with title '" + book.getTitle() + "' already exists");
        }
        return bookRepository.save(book);
    }

    /**
     * Updates an existing book.
     *
     * @param id   the ID of the book to update
     * @param book the updated book details
     * @return an optional containing the updated book if found, or empty if not
     */
    @Override
    public Optional<Book> updateBook(Long id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return Optional.of(bookRepository.save(book));
        }
        return Optional.empty();
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     * @return true if the book was deleted, false if not found
     */
    @Override
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Searches for books by title, author, and genre.
     *
     * @param title    the title keyword (optional)
     * @param author   the author keyword (optional)
     * @param genre    the genre keyword (optional)
     * @param pageable pagination information
     * @return a paginated list of books matching the search criteria
     */
    @Override
    public Page<Book> searchBooks(String title, String author, String genre, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
                title != null ? title : "",
                author != null ? author : "",
                genre != null ? genre : "",
                pageable
        );
    }
}
