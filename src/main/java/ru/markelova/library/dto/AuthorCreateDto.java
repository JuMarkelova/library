package ru.markelova.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorCreateDto {
    @Size(min = 3, max = 10)
    @NotBlank(message = "Name is needed")
    private String name;
    @NotBlank(message = "Surname is needed")
    private String surname;
}
