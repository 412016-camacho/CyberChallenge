package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.ReservaDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ReservaService {
    List<ReservaDto> getAllReservas();

    List<ReservaDto> getAllReservasFiltradas(Optional<Long> clienteId, Optional<Long> videojuegoId, Optional<Long> puestoId, Optional<LocalDateTime> fechaHora);

    List<ReservaDto> getAllReservasByClienteId(Optional<Long> clienteId);

    List<ReservaDto> getAllReservasByVideojuegoId(Optional<Long> videojuegoId);

    List<ReservaDto> getAllReservasByPuestoId(Optional<Long> puestoId);

    List<ReservaDto> getAllReservasByFechaHora(Optional<LocalDateTime> fechaHora);

    ReservaDto createReserva(@Valid ReservaDto reserva);
}
