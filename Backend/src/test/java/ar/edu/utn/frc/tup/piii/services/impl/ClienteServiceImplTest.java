package ar.edu.utn.frc.tup.piii.services.impl;

import ar.edu.utn.frc.tup.piii.dtos.ClienteDto;
import ar.edu.utn.frc.tup.piii.entities.ClienteEntity;
import ar.edu.utn.frc.tup.piii.repositories.ClienteRepository;
import ar.edu.utn.frc.tup.piii.repositories.PuestojuegoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteServiceImpl clienteService;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        clienteService = new ClienteServiceImpl(clienteRepository, modelMapper);
    }

    @Test
    void getAllClientes() {
        ClienteEntity cliente1 = new ClienteEntity(1L, "Test 1", "email@email.com");
        ClienteEntity cliente2 = new ClienteEntity(2L, "Test 2", "email@email.com");
        ClienteEntity cliente3 = new ClienteEntity(3L, "Test 3", "email@email.com");
        List<ClienteEntity> clientes = Arrays.asList(cliente1, cliente2, cliente3);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.getAllClientes();

        assertNotNull(clienteDtos);
        assertEquals(3, clienteDtos.size());
        assertEquals(cliente1.getNombreCompleto(), clienteDtos.get(0).getNombreCompleto());

    }

    @Test
    void getClienteById() {
        ClienteEntity cliente1 = new ClienteEntity(1L, "Test 1", "email@email.com");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente1));

        Optional<ClienteEntity> cliente = clienteService.getClienteById(1L);

        assertNotNull(cliente);
        assertEquals(1L, cliente.get().getId());
        assertEquals("Test 1", cliente.get().getNombreCompleto());
    }
}