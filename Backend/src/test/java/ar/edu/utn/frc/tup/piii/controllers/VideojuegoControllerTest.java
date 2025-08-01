package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.VideojuegoDto;
import ar.edu.utn.frc.tup.piii.entities.VideojuegoEntity;
import ar.edu.utn.frc.tup.piii.services.VideojuegoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(VideojuegoController.class)
@AutoConfigureMockMvc
class VideojuegoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideojuegoService videojuegoService;

    @Test
    void getAllVideojuegos() throws Exception{
        VideojuegoDto juego1 = new VideojuegoDto(1L, "Test 1", "Ficción");
        VideojuegoDto juego2 = new VideojuegoDto(2L, "Test 2", "Ficción");
        List<VideojuegoDto> videojuegos = List.of(juego1, juego2);

        when(videojuegoService.getAllVideojuegos()).thenReturn(videojuegos);

        mockMvc.perform(get("/api/v1/videojuegos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].titulo", is("Test 1")))
                .andExpect(jsonPath("$[1].titulo", is("Test 2")));

        verify(videojuegoService, times(1)).getAllVideojuegos();

    }
}