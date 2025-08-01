package ar.edu.utn.frc.tup.piii.services.impl;

import ar.edu.utn.frc.tup.piii.dtos.ClienteDto;
import ar.edu.utn.frc.tup.piii.entities.ClienteEntity;
import ar.edu.utn.frc.tup.piii.repositories.ClienteRepository;
import ar.edu.utn.frc.tup.piii.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<ClienteDto> getAllClientes() {
        List<ClienteEntity> clientes = clienteRepository.findAll();
        return modelMapper.map(clientes, new TypeToken<List<ClienteDto>>() {}.getType());
    }

    @Override
    public Optional<ClienteEntity> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }



}
