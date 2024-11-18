package ru.markelova.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.markelova.library.dto.AuthorCreateDto;
import ru.markelova.library.dto.AuthorDto;
import ru.markelova.library.dto.AuthorUpdateDto;
import ru.markelova.library.service.AuthorService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthorRestControllerTest {
    @MockBean
    private AuthorService authorService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAuthorById() throws Exception {
        Long id = 1L;
        AuthorDto authorDto = AuthorDto.builder()
                .id(id)
                .name("Александр")
                .surname("Пушкин")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testGetAuthorByName1() throws Exception {
        String name = "Александр";
        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .name(name)
                .surname("Пушкин")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/author/v1").param("name", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testCreateAuthor() throws Exception {
        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .name("Александр")
                .surname("Пушкин")
                .build();

        when(authorService.createAuthor(any(AuthorCreateDto.class))).thenReturn(authorDto);

        mockMvc.perform(post("/author/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Александр\",\"surname\":\"Пушкин\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .name("Александр")
                .surname("Пушкин")
                .build();

        when(authorService.updateAuthor(any(AuthorUpdateDto.class))).thenReturn(authorDto);

        mockMvc.perform(put("/author/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Александр\",\"surname\":\"Пушкин\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Long id = 1L;
        doNothing().when(authorService).deleteAuthor(id);

        mockMvc.perform(delete("/author/delete/{id}", id))
                .andExpect(status().isOk());
    }
}
