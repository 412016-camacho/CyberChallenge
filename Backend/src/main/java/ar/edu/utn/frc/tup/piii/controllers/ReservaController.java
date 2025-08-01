package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.ClienteDto;
import ar.edu.utn.frc.tup.piii.dtos.ReservaDto;
import ar.edu.utn.frc.tup.piii.services.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDto>> getAllReservas(@RequestParam Optional<Long> clienteId,
                                                           @RequestParam Optional<Long> videojuegoId,
                                                           @RequestParam Optional<Long> puestoId,
                                                           @RequestParam Optional<LocalDateTime> fechaHora){
        if(clienteId.isPresent() && videojuegoId.isPresent() && puestoId.isPresent() && fechaHora.isPresent()){
            return ResponseEntity.ok(reservaService.getAllReservasFiltradas(clienteId,videojuegoId,puestoId,fechaHora));
        }
        if(clienteId.isPresent()){
            return ResponseEntity.ok(reservaService.getAllReservasByClienteId(clienteId));
        }
        if(videojuegoId.isPresent()){
            return ResponseEntity.ok(reservaService.getAllReservasByVideojuegoId(videojuegoId));
        }
        if(puestoId.isPresent()){
            return ResponseEntity.ok(reservaService.getAllReservasByPuestoId(puestoId));
        }
        if(fechaHora.isPresent()){
            return ResponseEntity.ok(reservaService.getAllReservasByFechaHora(fechaHora));
        }
        return ResponseEntity.ok(reservaService.getAllReservas());
    }

    @PostMapping
    public ResponseEntity<ReservaDto> createReserva(@RequestBody @Valid ReservaDto reserva){
        ReservaDto reservaSaved = reservaService.createReserva(reserva);

        return ResponseEntity.ok(reservaSaved);
    }

}
