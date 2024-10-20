package ru.markelova.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.markelova.library.dto.BookCreateDto;
import ru.markelova.library.dto.BookDto;
import ru.markelova.library.dto.BookUpdateDto;
import ru.markelova.library.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/book/v1")
    BookDto getBookByName(@RequestParam(value = "name", required = false) String name) {
        return bookService.getByNameV1(name);
    }

    @GetMapping("/book/v2")
    BookDto getBookByNameV2(@RequestParam(value = "name", required = false) String name) {
        return bookService.getByNameV2(name);
    }

    @GetMapping("/book/v3")
    BookDto getBookByNameV3(@RequestParam(value = "name", required = false) String name) {
        return bookService.getByNameV3(name);
    }

    @PostMapping("/book/create")
    BookDto createBook(@RequestBody BookCreateDto bookCreateDto) {
        return bookService.createBook(bookCreateDto);
    }

    @PutMapping("/book/update")
    BookDto update(@RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.updateBook(bookUpdateDto);
    }

    @DeleteMapping("book/delete/{id}")
    void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }
}
