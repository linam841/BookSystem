package com.manilvit.bookmanagmentsystem.repository;

import com.manilvit.bookmanagmentsystem.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Book} entities.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds books by title containing a given keyword (case-insensitive) with pagination.
     *
     * @param title    the title keyword to search for
     * @param pageable the pagination information
     * @return a page of books matching the search criteria
     */
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    /**
     * Finds books by author containing a given keyword (case-insensitive) with pagination.
     *
     * @param author   the author keyword to search for
     * @param pageable the pagination information
     * @return a page of books matching the search criteria
     */
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    /**
     * Finds books by genre containing a given keyword (case-insensitive) with pagination.
     *
     * @param genre    the genre keyword to search for
     * @param pageable the pagination information
     * @return a page of books matching the search criteria
     */
    Page<Book> findByGenreContainingIgnoreCase(String genre, Pageable pageable);

    /**
     * Finds books by title, author, and genre containing given keywords (case-insensitive) with pagination.
     *
     * @param title    the title keyword to search for
     * @param author   the author keyword to search for
     * @param genre    the genre keyword to search for
     * @param pageable the pagination information
     * @return a page of books matching all search criteria
     */
    Page<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
            String title, String author, String genre, Pageable pageable);

    /**
     * Checks if a book with the given title exists.
     *
     * @param title the title to check
     * @return true if a book with the given title exists, false otherwise
     */
    boolean existsByTitle(String title);
}
