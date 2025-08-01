package ar.edu.utn.frc.tup.piii.services.impl;

import ar.edu.utn.frc.tup.piii.dtos.VideojuegoDto;
import ar.edu.utn.frc.tup.piii.entities.VideojuegoEntity;
import ar.edu.utn.frc.tup.piii.repositories.VideojuegoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideojuegoServiceImplTest {


    @Mock
    private VideojuegoRepository videojuegoRepository;

    private VideojuegoServiceImpl videojuegoService;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        videojuegoService = new VideojuegoServiceImpl(videojuegoRepository, modelMapper);
    }

    @Test
    void getAllVideojuegos() {
        VideojuegoEntity juego1 = new VideojuegoEntity(1L, "Test 1", "Ficción");
        VideojuegoEntity juego2 = new VideojuegoEntity(2L, "Test 2", "Ficción");
        VideojuegoEntity juego3 = new VideojuegoEntity(3L, "Test 3", "Ficción");
        List<VideojuegoEntity> videojuegos = List.of(juego1, juego2, juego3);

        when(videojuegoRepository.findAll()).thenReturn(videojuegos);

        List<VideojuegoDto> juegosDto = videojuegoService.getAllVideojuegos();

        assertNotNull(juegosDto);
        assertEquals(3, juegosDto.size());
        assertEquals(juego1.getTitulo(), juegosDto.get(0).getTitulo());

    }

    @Test
    void findById() {
        VideojuegoEntity juego1 = new VideojuegoEntity(1L, "Test 1", "Ficción");

        when(videojuegoRepository.findById(1L)).thenReturn(Optional.of(juego1));

        Optional<VideojuegoEntity> result = videojuegoService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());
        assertEquals("Test 1", result.get().getTitulo());
    }
}