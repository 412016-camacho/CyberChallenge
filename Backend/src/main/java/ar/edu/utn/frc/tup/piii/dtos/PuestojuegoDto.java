package ar.edu.utn.frc.tup.piii.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuestojuegoDto {

    private Long id;

    @NotNull
    @NotBlank
    private String nombre;

    private String tipo;
}
