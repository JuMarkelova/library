package ru.markelova.library.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.markelova.library.dto.AuthorCreateDto;
import ru.markelova.library.dto.AuthorDto;
import ru.markelova.library.dto.AuthorUpdateDto;
import ru.markelova.library.dto.BookDto;
import ru.markelova.library.model.Author;
import ru.markelova.library.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Try to find author by id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            AuthorDto authorDto = convertToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with id: {} not found", id);
            throw new IllegalStateException("Author is not found");
        }
    }

    @Override
    public AuthorDto getAuthorByNameV1(String name) {
        log.info("Try to find author by name {}", name);
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            AuthorDto authorDto = convertToDto(author.get());
            log.info("Author by name: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name: {} not found", name);
            throw new IllegalStateException("Author is not found");
        }
    }

    @Override
    public AuthorDto getAuthorByNameV2(String name) {
        Author author = authorRepository.findAuthorByNameBySql(name).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameV3(String name) {
        Specification<Author> authorSpecification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"), name);
            }
        });
        Author author = authorRepository.findOne(authorSpecification).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        log.info("Create author");
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        log.info("Author was created successfully");
        return convertToDto(author);
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Update author with id {}", authorUpdateDto.getId());
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow();
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author savedAuthor = authorRepository.save(author);
        AuthorDto authorDto = convertToDto(savedAuthor);
        log.info("Author id {} was updated successfully", authorUpdateDto.getId());
        return authorDto;
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Delete author by id {}", id);
        authorRepository.deleteById(id);
        log.info("Author with id {} was deleted", id);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        log.info("Get all authors");
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    private AuthorDto convertToDto(Author author) {
        List<BookDto> bookDtoList = null;
        if (author.getBooks() != null) {
            bookDtoList = author.getBooks().stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre())
                            .name(book.getName())
                            .id(book.getId())
                            .build()
                    ).toList();
        }
        return AuthorDto.builder()
                .books(bookDtoList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }
}