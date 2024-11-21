package ru.markelova.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.markelova.library.dto.AuthorCreateDto;
import ru.markelova.library.dto.AuthorDto;
import ru.markelova.library.dto.AuthorUpdateDto;
import ru.markelova.library.model.Author;
import ru.markelova.library.model.Book;
import ru.markelova.library.repository.AuthorRepository;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorServiceTest {

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    public void getAuthorById() {
        Long id = 1L;
        String name = "John";
        String surname = "Boo";
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorById(id);
        verify(authorRepository).findById(id);

        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void getAuthorByIdFailed() {
        Long id = 1L;

        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> authorService.getAuthorById(id));
        verify(authorRepository).findById(id);
    }

    @Test
    public void getAuthorByName1() {
        Long id = 1L;
        String name = "Mary";
        String surname = "Boo";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameV1(name);

        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void getAuthorByName1Failed() {
        String name = "Mary";

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> authorService.getAuthorByNameV1(name));
        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void getAuthorByName2() {
        Long id = 1L;
        String name = "Mary";
        String surname = "Boo";
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameV2(name);

        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void getAuthorByName2Failed() {
        String name = "Mary";

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> authorService.getAuthorByNameV2(name));
        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void getAuthorByName3() {
        Long id = 1L;
        String name = "Mary";
        String surname = "Boo";
        Set<Book> books = new HashSet<>();

        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameV3(name);
        verify(authorRepository).findAuthorByName(name);

        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void getAuthorByName3Failed() {
        String name = "Mary";

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> authorService.getAuthorByNameV3(name));
        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void createAuthorSuccess() {
        AuthorCreateDto authorCreateDto = new AuthorCreateDto();
        authorCreateDto.setName("Mary");
        authorCreateDto.setSurname("Boo");

        Set<Book> books = new HashSet<>();
        Author author = new Author(1L, "Mary", "Boo", books);

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDto result = authorService.createAuthor(authorCreateDto);

        verify(authorRepository, times(1)).save(any(Author.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Mary", result.getName());
        Assertions.assertEquals("Boo", result.getSurname());
        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    public void updateAuthorSuccess() {
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto();
        authorUpdateDto.setId(1L);
        authorUpdateDto.setName("Mary");
        authorUpdateDto.setSurname("Boo");
        Set<Book> books = new HashSet<>();
        Author author = new Author(1L, "Mary", "Boo", books);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDto result = authorService.updateAuthor(authorUpdateDto);

        verify(authorRepository).findById(1L);
        verify(authorRepository).save(any(Author.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Mary", result.getName());
        Assertions.assertEquals("Boo", result.getSurname());
        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    public void updateAuthorFailure() {
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto();
        authorUpdateDto.setId(1L);
        authorUpdateDto.setName("Mary");
        authorUpdateDto.setSurname("Boo");

        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            authorService.updateAuthor(authorUpdateDto);
        });

        verify(authorRepository).findById(1L);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    public void deleteAuthor() {
        Long id = 1L;
        authorService.deleteAuthor(id);
        verify(authorRepository, times(1)).deleteById(id);
    }
}
