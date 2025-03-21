package com.manilvit.bookmanagmentsystem.repository;


import com.manilvit.bookmanagmentsystem.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Поиск книг по названию (с пагинацией)
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Поиск книг по автору (с пагинацией)
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    // Поиск книг по жанру (с пагинацией)
    Page<Book> findByGenreContainingIgnoreCase(String genre, Pageable pageable);

    // Объединённый поиск по названию, автору и жанру (с пагинацией)
    Page<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
            String title, String author, String genre, Pageable pageable);

    // Проверка существования книги по названию (для валидации перед добавлением)
    boolean existsByTitle(String title);
}