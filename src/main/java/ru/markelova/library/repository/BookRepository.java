package ru.markelova.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.markelova.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
