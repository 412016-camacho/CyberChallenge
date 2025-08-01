package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.ClienteDto;
import ar.edu.utn.frc.tup.piii.entities.ClienteEntity;
import ar.edu.utn.frc.tup.piii.services.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void getAllClientes() throws Exception{
        ClienteDto cliente1 = new ClienteDto(1L, "Test 1", "email@email.com");
        ClienteDto cliente2 = new ClienteDto(2L, "Test 2", "email@email.com");
        List<ClienteDto> clientes = Arrays.asList(cliente1, cliente2);

        when(clienteService.getAllClientes()).thenReturn(clientes);

        mockMvc.perform(get("/api/v1/clientes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombreCompleto", is("Test 1")))
                .andExpect(jsonPath("$[1].nombreCompleto", is("Test 2")));

        verify(clienteService, times(1)).getAllClientes();


    }
}