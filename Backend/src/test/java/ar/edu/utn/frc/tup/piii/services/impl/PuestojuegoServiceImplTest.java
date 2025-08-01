package ar.edu.utn.frc.tup.piii.services.impl;

import ar.edu.utn.frc.tup.piii.dtos.PuestojuegoDto;
import ar.edu.utn.frc.tup.piii.entities.PuestojuegoEntity;
import ar.edu.utn.frc.tup.piii.entities.VideojuegoEntity;
import ar.edu.utn.frc.tup.piii.repositories.PuestojuegoRepository;
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
class PuestojuegoServiceImplTest {

    @Mock
    private PuestojuegoRepository puestojuegoRepository;

    private PuestojuegoServiceImpl puestojuegoService;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        puestojuegoService = new PuestojuegoServiceImpl(puestojuegoRepository, modelMapper);
    }

    @Test
    void getAllPuestos() {
        PuestojuegoEntity puesto1 = new PuestojuegoEntity(1L, "Test 1", "Consola");
        PuestojuegoEntity puesto2 = new PuestojuegoEntity(2L, "Test 2", "Consola");
        PuestojuegoEntity puesto3 = new PuestojuegoEntity(3L, "Test 3", "Consola");
        List<PuestojuegoEntity> puestos = List.of(puesto1, puesto2, puesto3);

        when(puestojuegoRepository.findAll()).thenReturn(puestos);

        List<PuestojuegoDto>  puestosDto = puestojuegoService.getAllPuestos();

        assertNotNull(puestosDto);
        assertEquals(3, puestosDto.size());
        assertEquals(puesto1.getNombre(), puestosDto.get(0).getNombre());

    }

    @Test
    void findById() {
        PuestojuegoEntity puesto1 = new PuestojuegoEntity(1L, "Test 1", "Consola");

        when(puestojuegoRepository.findById(1L)).thenReturn(Optional.of(puesto1));

        Optional<PuestojuegoEntity> result = puestojuegoService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());
        assertEquals("Test 1", result.get().getNombre());
    }
}