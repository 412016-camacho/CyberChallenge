package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.PuestojuegoDto;
import ar.edu.utn.frc.tup.piii.entities.PuestojuegoEntity;
import ar.edu.utn.frc.tup.piii.services.PuestojuegoService;
import ar.edu.utn.frc.tup.piii.services.VideojuegoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PuestojuegoController.class)
@AutoConfigureMockMvc
class PuestojuegoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PuestojuegoService puestojuegoService;

    @Test
    void getAllPuestos() throws Exception {
        PuestojuegoDto puesto1 = new PuestojuegoDto(1L, "Test 1", "Consola");
        PuestojuegoDto puesto2 = new PuestojuegoDto(2L, "Test 2", "Consola");

        List<PuestojuegoDto> puestos = List.of(puesto1, puesto2);

        when(puestojuegoService.getAllPuestos()).thenReturn(puestos);

        mockMvc.perform(get("/api/v1/puestos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Test 1")))
                .andExpect(jsonPath("$[1].nombre", is("Test 2")));

        verify(puestojuegoService, times(1)).getAllPuestos();

    }
}