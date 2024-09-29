package ru.markelova.library.service;

import ru.markelova.library.dto.AuthorDto;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);
}
