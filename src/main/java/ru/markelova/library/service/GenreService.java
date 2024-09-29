package ru.markelova.library.service;

import ru.markelova.library.dto.GenreDto;

public interface GenreService {
    GenreDto getGenreById(Long id);
}
