package ru.markelova.library.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.markelova.library.dto.BookCreateDto;
import ru.markelova.library.dto.BookDto;
import ru.markelova.library.dto.BookUpdateDto;
import ru.markelova.library.model.Book;
import ru.markelova.library.model.Genre;
import ru.markelova.library.repository.BookRepository;
import ru.markelova.library.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public BookDto getByNameV1(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto getByNameV2(String name) {
        Book book = bookRepository.findBookByNameBySql(name).orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto getByNameV3(String name) {
        Specification<Book> bookSpecification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"), name);
            }
        });
        Book book = bookRepository.findOne(bookSpecification).orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
        return convertEntityToDto(book);
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Genre genre = genreRepository.findById(bookUpdateDto.getGenre())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        book.setName(bookUpdateDto.getName());
        book.setGenre(genre);

        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(book);
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        Genre genre = genreRepository.findById(bookCreateDto.getGenre())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .build();
    }
}
