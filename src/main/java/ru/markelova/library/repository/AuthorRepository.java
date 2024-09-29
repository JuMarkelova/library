package ru.markelova.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.markelova.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
