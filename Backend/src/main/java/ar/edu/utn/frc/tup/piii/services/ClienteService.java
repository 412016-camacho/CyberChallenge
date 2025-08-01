package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.ClienteDto;
import ar.edu.utn.frc.tup.piii.entities.ClienteEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ClienteService {
    List<ClienteDto> getAllClientes();

    Optional<ClienteEntity> getClienteById(Long id);
}
