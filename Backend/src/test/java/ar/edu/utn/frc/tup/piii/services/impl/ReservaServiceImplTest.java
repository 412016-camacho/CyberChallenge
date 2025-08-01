package ar.edu.utn.frc.tup.piii.services.impl;

import ar.edu.utn.frc.tup.piii.dtos.ReservaDto;
import ar.edu.utn.frc.tup.piii.entities.ClienteEntity;
import ar.edu.utn.frc.tup.piii.entities.PuestojuegoEntity;
import ar.edu.utn.frc.tup.piii.entities.ReservaEntity;
import ar.edu.utn.frc.tup.piii.entities.VideojuegoEntity;
import ar.edu.utn.frc.tup.piii.repositories.PuestojuegoRepository;
import ar.edu.utn.frc.tup.piii.repositories.ReservaRepository;
import ar.edu.utn.frc.tup.piii.services.ClienteService;
import ar.edu.utn.frc.tup.piii.services.PuestojuegoService;
import ar.edu.utn.frc.tup.piii.services.VideojuegoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    @Mock
    private ReservaRepository reservaRepository;

    private ReservaServiceImpl reservaService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private PuestojuegoService puestojuegoService;

    @Mock
    private VideojuegoService videojuegoService;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        reservaService = new ReservaServiceImpl(reservaRepository, clienteService, videojuegoService, puestojuegoService, modelMapper);
    }

    @Test
    void getAllReservas() {
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        ReservaEntity reserva1 = new ReservaEntity(1L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva2 = new ReservaEntity(2L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva3 = new ReservaEntity(3L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        List<ReservaEntity> reservas = List.of(reserva1,reserva2,reserva3);

        when(reservaRepository.findAll()).thenReturn(reservas);

        List<ReservaDto> reservasDto = reservaService.getAllReservas();

        assertNotNull(reservasDto);
        assertEquals(3, reservasDto.size());
        assertEquals(reserva1.getCliente().getId(), reservasDto.get(0).getClienteId());

    }

    @Test
    void getAllReservasFiltradas() {
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        ReservaEntity reserva1 = new ReservaEntity(1L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva2 = new ReservaEntity(2L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva3 = new ReservaEntity(3L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        List<ReservaEntity> reservas = List.of(reserva1,reserva2,reserva3);

        when(reservaRepository.findAllByClienteIdAndVideojuego_IdAndPuesto_IdAndFechaHora(
                Optional.ofNullable(cliente.getId()),
                Optional.ofNullable(juego.getId()),
                Optional.ofNullable(puesto.getId()),
                Optional.ofNullable(reserva1.getFechaHora()))).thenReturn(reservas);

        List<ReservaDto> result = reservaService.getAllReservasFiltradas(Optional.ofNullable(cliente.getId()),
                Optional.ofNullable(juego.getId()),
                Optional.ofNullable(puesto.getId()),
                Optional.ofNullable(reserva1.getFechaHora()));

        assertNotNull(result);
        assertEquals(reservas.size(), result.size());

    }

    @Test
    void getAllReservasByClienteId() {
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        ReservaEntity reserva1 = new ReservaEntity(1L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva2 = new ReservaEntity(2L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva3 = new ReservaEntity(3L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        List<ReservaEntity> reservas = List.of(reserva1,reserva2,reserva3);

        when(reservaRepository.findAllByClienteId(
                Optional.ofNullable(cliente.getId()))).thenReturn(reservas);

        List<ReservaDto> result = reservaService.getAllReservasByClienteId(Optional.ofNullable(cliente.getId()));

        assertNotNull(result);
        assertEquals(reservas.size(), result.size());
    }

    @Test
    void getAllReservasByVideojuegoId() {
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        ReservaEntity reserva1 = new ReservaEntity(1L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva2 = new ReservaEntity(2L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva3 = new ReservaEntity(3L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        List<ReservaEntity> reservas = List.of(reserva1,reserva2,reserva3);

        when(reservaRepository.findAllByVideojuego_Id(
                Optional.ofNullable(juego.getId()))).thenReturn(reservas);

        List<ReservaDto> result = reservaService.getAllReservasByVideojuegoId(Optional.ofNullable(juego.getId()));

        assertNotNull(result);
        assertEquals(reservas.size(), result.size());

    }

    @Test
    void getAllReservasByPuestoId() {
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        ReservaEntity reserva1 = new ReservaEntity(1L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva2 = new ReservaEntity(2L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva3 = new ReservaEntity(3L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        List<ReservaEntity> reservas = List.of(reserva1,reserva2,reserva3);

        when(reservaRepository.findAllByPuesto_Id(
                Optional.ofNullable(puesto.getId()))).thenReturn(reservas);

        List<ReservaDto> result = reservaService.getAllReservasByPuestoId(Optional.ofNullable(puesto.getId()));

        assertNotNull(result);
        assertEquals(reservas.size(), result.size());

    }

    @Test
    void getAllReservasByFechaHora() {
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        ReservaEntity reserva1 = new ReservaEntity(1L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva2 = new ReservaEntity(2L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        ReservaEntity reserva3 = new ReservaEntity(3L,cliente,juego,puesto, LocalDateTime.now(),60, "");
        List<ReservaEntity> reservas = List.of(reserva1,reserva2,reserva3);

        LocalDate fecha = reserva1.getFechaHora().toLocalDate();
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(LocalTime.MAX);

        when(reservaRepository.findAllByFechaHora(inicio,fin)).thenReturn(reservas);

        List<ReservaDto> result = reservaService.getAllReservasByFechaHora(Optional.ofNullable(reserva1.getFechaHora()));

        assertNotNull(result);
        assertEquals(reservas.size(), result.size());
    }

    @Test
    void createReserva() {
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        LocalDateTime fechaValida = LocalDateTime.now().withHour(15).withMinute(0);
        ReservaEntity reserva = new ReservaEntity(1L,cliente,juego,puesto, fechaValida,60, "");


        when(clienteService.getClienteById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(puestojuegoService.findById(puesto.getId())).thenReturn(Optional.of(puesto));
        when(videojuegoService.findById(juego.getId())).thenReturn(Optional.of(juego));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reserva);
        when(reservaService.getAllReservas()).thenReturn(Collections.emptyList());

        ReservaDto result = reservaService.createReserva(modelMapper.map(reserva, ReservaDto.class));

        assertNotNull(result);
        assertEquals(1L, result.getClienteId());
        assertEquals(1L, result.getPuestoId());
        assertEquals(1L, result.getVideojuegoId());
        assertEquals(fechaValida, result.getFechaHora());
    }

    @Test
    void createReserva_Null(){
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        LocalDateTime fechaHoraInvalida = LocalDateTime.now().withHour(23);
        ReservaDto reserva = new ReservaDto(cliente.getId(),juego.getId(),puesto.getId(), fechaHoraInvalida,60, "");

    }

    @Test
    void validar_FueraDeHorario() throws NoSuchMethodException {
        ReservaDto reserva = new ReservaDto(1L,1L,1L, LocalDateTime.of(2025,7,29,9,0),60, "");

        Method method = reservaService.getClass().getDeclaredMethod("validar", ReservaDto.class);
        method.setAccessible(true);

        InvocationTargetException ex = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(reservaService, reserva);
        });

        Throwable cause = ex.getCause();
        assertInstanceOf(HttpClientErrorException.class, cause);
        assertEquals("409 El horario de atención es de 10:00 - 22:00", cause.getMessage());
    }

    @Test
    void validar_DosReservasEnUnDia() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");

        ReservaDto reserva1 = new ReservaDto(cliente.getId(), juego.getId(), puesto.getId(), LocalDateTime.of(2025,7,29,19,0),60, "");
        ReservaDto reserva2 = new ReservaDto(cliente.getId(), juego.getId(), puesto.getId(), LocalDateTime.of(2025,7,29,19,0),60, "");

        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setCliente(cliente);
        reservaEntity.setVideojuego(juego);
        reservaEntity.setPuesto(puesto);
        reservaEntity.setFechaHora(LocalDateTime.of(2025,7,29,19,0));
        reservaEntity.setDuracionMinutos(60);

        when(clienteService.getClienteById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(puestojuegoService.findById(puesto.getId())).thenReturn(Optional.of(puesto));
        when(videojuegoService.findById(juego.getId())).thenReturn(Optional.of(juego));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reservaEntity);

        reservaService.createReserva(reserva1);

        when(reservaRepository.findAll()).thenReturn(List.of(reservaEntity));

        Method method = reservaService.getClass().getDeclaredMethod("validar", ReservaDto.class);
        method.setAccessible(true);

        InvocationTargetException ex = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(reservaService, reserva2);
        });

        Throwable cause = ex.getCause();
        assertInstanceOf(HttpClientErrorException.class, cause);
        assertEquals("409 Ya tiene una reserva para este día.", cause.getMessage());
    }

    @Test
    void validar_SolapamientoDeReservas() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClienteEntity cliente1 = new ClienteEntity(1L, "Test 1", "email@email.com");
        ClienteEntity cliente2 = new ClienteEntity(2L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");

        ReservaDto reserva1 = new ReservaDto(cliente1.getId(), juego.getId(), puesto.getId(), LocalDateTime.of(2025,7,29,19,0),60, "");
        ReservaDto reserva2 = new ReservaDto(cliente2.getId(), juego.getId(), puesto.getId(), LocalDateTime.of(2025,7,29,19,0),60, "");

        ReservaEntity reservaEntity1 = new ReservaEntity();
        reservaEntity1.setCliente(cliente1);
        reservaEntity1.setVideojuego(juego);
        reservaEntity1.setPuesto(puesto);
        reservaEntity1.setFechaHora(LocalDateTime.of(2025,7,29,19,0));
        reservaEntity1.setDuracionMinutos(60);

        ReservaEntity reservaEntity2 = new ReservaEntity();
        reservaEntity2.setCliente(cliente2);
        reservaEntity2.setVideojuego(juego);
        reservaEntity2.setPuesto(puesto);
        reservaEntity2.setFechaHora(LocalDateTime.of(2025,7,29,19,0));
        reservaEntity2.setDuracionMinutos(60);

        when(clienteService.getClienteById(cliente1.getId())).thenReturn(Optional.of(cliente1));
        when(puestojuegoService.findById(puesto.getId())).thenReturn(Optional.of(puesto));
        when(videojuegoService.findById(juego.getId())).thenReturn(Optional.of(juego));
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(reservaEntity1);

        reservaService.createReserva(reserva1);

        when(reservaRepository.findAll()).thenReturn(List.of(reservaEntity1));

        Method method = reservaService.getClass().getDeclaredMethod("validar", ReservaDto.class);
        method.setAccessible(true);

        InvocationTargetException ex = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(reservaService, reserva2);
        });

        Throwable cause = ex.getCause();
        assertInstanceOf(HttpClientErrorException.class, cause);
        assertEquals("409 No se permite solapamiento de reservas en el mismo puesto.", cause.getMessage());
    }

    @Test
    void validar_DuracionMinutosIncorrecta() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClienteEntity cliente = new ClienteEntity(1L, "Test 1", "email@email.com");
        ClienteEntity cliente1 = new ClienteEntity(2L, "Test 1", "email@email.com");
        VideojuegoEntity juego = new VideojuegoEntity(1L, "Test 1", "Ficción");
        PuestojuegoEntity puesto = new PuestojuegoEntity(1L, "Test 1", "Consola");
        PuestojuegoEntity puesto1 = new PuestojuegoEntity(2L, "Test 1", "Consola");

        ReservaDto reserva = new ReservaDto(cliente.getId(), juego.getId(), puesto.getId(), LocalDateTime.of(2025,7,29,19,0),50, "");

        ReservaEntity reservaEntity = new ReservaEntity();
        reservaEntity.setCliente(cliente1);
        reservaEntity.setVideojuego(juego);
        reservaEntity.setPuesto(puesto1);
        reservaEntity.setFechaHora(LocalDateTime.of(2025,7,29,20,0));
        reservaEntity.setDuracionMinutos(50);

        when(reservaRepository.findAll()).thenReturn(List.of(reservaEntity));

        Method method = reservaService.getClass().getDeclaredMethod("validar", ReservaDto.class);
        method.setAccessible(true);

        InvocationTargetException ex = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(reservaService, reserva);
        });

        Throwable cause = ex.getCause();
        assertInstanceOf(HttpClientErrorException.class, cause);
        assertEquals("409 Las reservas deben tener duración exacta: 30, 60, 90 o 120 minutos", cause.getMessage());
    }
}