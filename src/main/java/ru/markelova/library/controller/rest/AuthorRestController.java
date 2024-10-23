package ru.markelova.library.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.markelova.library.dto.AuthorCreateDto;
import ru.markelova.library.dto.AuthorDto;
import ru.markelova.library.dto.AuthorUpdateDto;
import ru.markelova.library.service.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {
    private final AuthorService authorService;

    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author/v1")
    AuthorDto getAuthorByNameV1(@RequestParam(value = "name", required = false) String name) {
        return authorService.getAuthorByNameV1(name);
    }

    @GetMapping("/author/v2")
    AuthorDto getAuthorByNameV2(@RequestParam(value = "name", required = false) String name) {
        return authorService.getAuthorByNameV2(name);
    }

    @GetMapping("/author/v3")
    AuthorDto getAuthorByNameV3(@RequestParam(value = "name", required = false) String name) {
        return authorService.getAuthorByNameV3(name);
    }

    @PostMapping("/author/create")
    AuthorDto createAuthor(@RequestBody AuthorCreateDto authorCreateDto) {
        return authorService.createAuthor(authorCreateDto);
    }

    @PutMapping("/author/update")
    AuthorDto updateAuthor(@RequestBody AuthorUpdateDto authorUpdateDto) {
        return authorService.updateAuthor(authorUpdateDto);
    }

    @DeleteMapping("/author/delete/{id}")
    void deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }
}