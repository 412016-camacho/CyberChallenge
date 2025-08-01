package ar.edu.utn.frc.tup.piii.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDto {

    @NotNull
    @JsonProperty("cliente_id")
    private Long clienteId;

    @NotNull
    @JsonProperty("videojuego_id")
    private Long videojuegoId;

    @NotNull
    @JsonProperty("puesto_id")
    private Long puestoId;

    @NotNull
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHora;

    @NotNull
    private Integer duracionMinutos;

    private String observaciones;
}
