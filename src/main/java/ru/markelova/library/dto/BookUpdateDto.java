package ru.markelova.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.markelova.library.model.Genre;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookUpdateDto {
    @NotNull
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;
    private Genre genre;
}
