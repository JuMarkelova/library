package ru.markelova.library.service;

import ru.markelova.library.dto.BookDto;

public interface BookService {
    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    BookDto getByNameV3(String name);
}
