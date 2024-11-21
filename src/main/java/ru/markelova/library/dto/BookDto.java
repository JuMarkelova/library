package ru.markelova.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.markelova.library.model.Author;
import ru.markelova.library.model.Genre;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDto {
    private Long id;
    private String name;
    private Genre genre;
    private List<Author> authors;
}
