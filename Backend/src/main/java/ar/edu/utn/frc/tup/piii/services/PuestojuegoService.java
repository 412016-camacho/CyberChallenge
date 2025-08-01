package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.PuestojuegoDto;
import ar.edu.utn.frc.tup.piii.entities.PuestojuegoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PuestojuegoService {
    List<PuestojuegoDto> getAllPuestos();

    Optional<PuestojuegoEntity> findById(@NotNull Long puestoId);
}
