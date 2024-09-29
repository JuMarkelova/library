package ru.markelova.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.markelova.library.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
