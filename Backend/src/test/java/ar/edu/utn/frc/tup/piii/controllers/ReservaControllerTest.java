package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.ReservaDto;
import ar.edu.utn.frc.tup.piii.entities.ClienteEntity;
import ar.edu.utn.frc.tup.piii.entities.PuestojuegoEntity;
import ar.edu.utn.frc.tup.piii.entities.ReservaEntity;
import ar.edu.utn.frc.tup.piii.entities.VideojuegoEntity;
import ar.edu.utn.frc.tup.piii.services.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ReservaController.class)
@AutoConfigureMockMvc
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllReservas() throws Exception{
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        List<ReservaDto> reservas = List.of(
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), LocalDateTime.now(),60, ""),
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), LocalDateTime.now(),60, "")
        );

        when(reservaService.getAllReservas()).thenReturn(reservas);

        mockMvc.perform(get("/api/v1/reservas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].puesto_id", is(1)));

        verify(reservaService, times(1)).getAllReservas();

    }

    @Test
    void getAllReservas_Filtradas() throws Exception{
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");

        LocalDateTime fecha = LocalDateTime.of(2025, 7, 28, 15, 0);

        List<ReservaDto> reservas = List.of(
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, ""),
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, "")
        );

        when(reservaService.getAllReservasFiltradas(Optional.ofNullable(cliente.getId()),
                Optional.ofNullable(juego.getId()),
                Optional.ofNullable(puesto.getId()),
                Optional.of(fecha))).thenReturn(reservas);

        mockMvc.perform(get("/api/v1/reservas")
                        .param("clienteId", cliente.getId().toString())
                        .param("videojuegoId", juego.getId().toString())
                        .param("puestoId", puesto.getId().toString())
                        .param("fechaHora", fecha.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].puesto_id", is(1)));

        verify(reservaService, times(1)).getAllReservasFiltradas(Optional.ofNullable(cliente.getId()),
                Optional.ofNullable(juego.getId()),
                Optional.ofNullable(puesto.getId()),
                Optional.ofNullable(reservas.get(0).getFechaHora()));

    }

    @Test
    void getAllReservas_ByClienteId() throws Exception{
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");

        LocalDateTime fecha = LocalDateTime.of(2025, 7, 28, 15, 0);

        List<ReservaDto> reservas = List.of(
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, ""),
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, "")
        );

        when(reservaService.getAllReservasByClienteId(Optional.ofNullable(cliente.getId()))).thenReturn(reservas);

        mockMvc.perform(get("/api/v1/reservas")
                        .param("clienteId", cliente.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].puesto_id", is(1)));

        verify(reservaService, times(1)).getAllReservasByClienteId(Optional.ofNullable(cliente.getId()));

    }

    @Test
    void getAllReservas_ByVideoJuegoId() throws Exception{
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");

        LocalDateTime fecha = LocalDateTime.of(2025, 7, 28, 15, 0);

        List<ReservaDto> reservas = List.of(
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, ""),
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, "")
        );

        when(reservaService.getAllReservasByVideojuegoId(Optional.ofNullable(juego.getId()))).thenReturn(reservas);

        mockMvc.perform(get("/api/v1/reservas")
                        .param("videojuegoId", juego.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].puesto_id", is(1)));

        verify(reservaService, times(1)).getAllReservasByVideojuegoId(Optional.ofNullable(juego.getId()));

    }

    @Test
    void getAllReservas_ByPuestoId() throws Exception{
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");

        LocalDateTime fecha = LocalDateTime.of(2025, 7, 28, 15, 0);

        List<ReservaDto> reservas = List.of(
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, ""),
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, "")
        );

        when(reservaService.getAllReservasByPuestoId(Optional.ofNullable(puesto.getId()))).thenReturn(reservas);

        mockMvc.perform(get("/api/v1/reservas")
                        .param("puestoId", puesto.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].puesto_id", is(1)));

        verify(reservaService, times(1)).getAllReservasByPuestoId(Optional.ofNullable(puesto.getId()));

    }

    @Test
    void getAllReservas_FechaHora() throws Exception{
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");

        LocalDateTime fecha = LocalDateTime.of(2025, 7, 28, 15, 0);

        List<ReservaDto> reservas = List.of(
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, ""),
                new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fecha,60, "")
        );

        when(reservaService.getAllReservasByFechaHora(Optional.of(fecha))).thenReturn(reservas);

        mockMvc.perform(get("/api/v1/reservas")
                        .param("fechaHora", fecha.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].cliente_id", is(1)))
                .andExpect(jsonPath("$[1].puesto_id", is(1)));

        verify(reservaService, times(1)).getAllReservasByFechaHora(Optional.ofNullable(reservas.get(0).getFechaHora()));

    }

    @Test
    void createReserva() throws Exception{
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");

        ReservaDto reserva = new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), LocalDateTime.now(),60,"");

        when(reservaService.createReserva(any())).thenReturn(reserva);

        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk());

    }
}