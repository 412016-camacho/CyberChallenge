package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.VideojuegoDto;
import ar.edu.utn.frc.tup.piii.entities.VideojuegoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface VideojuegoService {
    List<VideojuegoDto> getAllVideojuegos();

    Optional<VideojuegoEntity> findById(@NotNull Long videojuegoId);
}
