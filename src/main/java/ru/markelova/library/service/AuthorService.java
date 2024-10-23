package ru.markelova.library.service;

import ru.markelova.library.dto.AuthorCreateDto;
import ru.markelova.library.dto.AuthorDto;
import ru.markelova.library.dto.AuthorUpdateDto;
import ru.markelova.library.model.Author;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);

    AuthorDto getAuthorByNameV2(String name);

    AuthorDto getAuthorByNameV3(String name);

    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);

    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);

    void deleteAuthor(Long id);

    public List<AuthorDto> getAllAuthors();
}
