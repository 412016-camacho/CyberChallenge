package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.ClienteDto;
import ar.edu.utn.frc.tup.piii.dtos.PuestojuegoDto;
import ar.edu.utn.frc.tup.piii.services.PuestojuegoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/puestos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PuestojuegoController {

    private final PuestojuegoService puestojuegoService;

    @GetMapping
    public ResponseEntity<List<PuestojuegoDto>> getAllPuestos(){
        return ResponseEntity.ok(puestojuegoService.getAllPuestos());
    }
}
