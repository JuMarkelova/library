package ru.markelova.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.markelova.library.dto.BookDto;
import ru.markelova.library.dto.GenreDto;
import ru.markelova.library.model.Author;
import ru.markelova.library.model.Genre;
import ru.markelova.library.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        return convertToDto(genre);
    }

    private GenreDto convertToDto(Genre genre) {
        List<BookDto> bookDtoList = genre.getBooks().stream()
                .map(book -> {
                    List<Author> authors = book.getAuthors().stream()
                            .peek(it -> it.setBooks(null))
                            .toList();

                    return BookDto.builder()
                            .id(book.getId())
                            .name(book.getName())
                            .authors(authors)
                            .build();
                })
                .toList();

        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .books(bookDtoList)
                .build();
    }
}