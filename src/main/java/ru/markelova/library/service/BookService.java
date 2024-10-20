package ru.markelova.library.service;

import ru.markelova.library.dto.BookCreateDto;
import ru.markelova.library.dto.BookDto;
import ru.markelova.library.dto.BookUpdateDto;

public interface BookService {
    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    BookDto getByNameV3(String name);

    BookDto createBook(BookCreateDto bookCreateDto);

    BookDto updateBook(BookUpdateDto bookUpdateDto);

    void deleteBook(Long id);
}
