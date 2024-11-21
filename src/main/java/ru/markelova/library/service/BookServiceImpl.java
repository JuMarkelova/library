package ru.markelova.library.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.markelova.library.dto.BookCreateDto;
import ru.markelova.library.dto.BookDto;
import ru.markelova.library.dto.BookUpdateDto;
import ru.markelova.library.model.Book;
import ru.markelova.library.model.Genre;
import ru.markelova.library.repository.BookRepository;
import ru.markelova.library.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public BookDto getByNameV1(String name) {
        log.info("Try to find book by id {}", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} is not found", name);
            throw new IllegalStateException("Book is not found");
        }
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
        log.info("Create book");
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
        log.info("Book was created successfully");
        return convertEntityToDto(book);
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Update book with id {}", bookUpdateDto.getId());
        Optional<Book> book = bookRepository.findById(bookUpdateDto.getId());
        Optional<Genre> genre = genreRepository.findById(bookUpdateDto.getGenre().getId());
        if (book.isPresent()) {
            if (genre.isPresent()) {
                book.get().setName(bookUpdateDto.getName());
                book.get().setGenre(genre.get());
                Book savedBook = bookRepository.save(book.get());
                BookDto bookDto = convertEntityToDto(book.get());
                return bookDto;

            } else {
                log.error("Genre id {} is not found", bookUpdateDto.getId());
                throw new IllegalStateException("Genre is not found");
            }
        } else {
            log.error("Book id {} is not found", bookUpdateDto.getId());
            throw new IllegalStateException("Book is not found");
        }
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Delete book by id {}", id);
        bookRepository.deleteById(id);
        log.info("Book with id {} was deleted", id);
    }

    @Override
    public List<BookDto> getAllBooks() {
        log.info("Get all books");
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre())
                .name(book.getName())
                .build();
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        Genre genre = genreRepository.findById(bookCreateDto.getGenre().getId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .build();
    }
}