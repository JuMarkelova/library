package ru.markelova.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.markelova.library.dto.BookCreateDto;
import ru.markelova.library.dto.BookDto;
import ru.markelova.library.dto.BookUpdateDto;
import ru.markelova.library.model.Author;
import ru.markelova.library.model.Book;
import ru.markelova.library.model.Genre;
import ru.markelova.library.repository.BookRepository;
import ru.markelova.library.repository.GenreRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    public void getBookByName() {
        Long id = 1L;
        String name = "Book name";
        Genre genre = new Genre(1L, "Рассказ", new HashSet<>());
        Set<Author> authors = new HashSet<>();

        Book book = new Book(id, name, genre, authors);

        when(bookRepository.findBookByName(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getByNameV1(name);

        verify(bookRepository, times(1)).findBookByName(name);
        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
        Assertions.assertEquals(bookDto.getGenre(), book.getGenre());
    }

    @Test
    public void getBookByNameFailure() {
        String name = "Book name";

        when(bookRepository.findBookByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalStateException.class, () -> bookService.getByNameV1(name));
        verify(bookRepository, times(1)).findBookByName(name);
    }

    @Test
    public void createBook() {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setName("Book new");
        Genre genre = new Genre(1L, "Рассказ", new HashSet<>());
        bookCreateDto.setGenre(genre);
        Book book = new Book(1L, "Book new", genre, new HashSet<>());

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        BookDto result = bookService.createBook(bookCreateDto);

        verify(bookRepository, times(1)).save(any(Book.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Book new", result.getName());
        Assertions.assertEquals(genre, result.getGenre());
        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    public void updateBookSuccess() {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(1L);
        bookUpdateDto.setName("Book new");
        Genre genre = new Genre(1L, "Рассказ", new HashSet<>());
        bookUpdateDto.setGenre(genre);
        Book book = new Book(1L, "Book new", genre, new HashSet<>());

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto result = bookService.updateBook(bookUpdateDto);

        verify(bookRepository).findById(1L);
        verify(bookRepository).save(any(Book.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Book new", result.getName());
        Assertions.assertEquals(genre, result.getGenre());
    }
}
