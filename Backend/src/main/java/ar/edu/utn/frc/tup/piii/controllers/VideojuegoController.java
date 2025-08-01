package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.PuestojuegoDto;
import ar.edu.utn.frc.tup.piii.dtos.VideojuegoDto;
import ar.edu.utn.frc.tup.piii.services.VideojuegoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/videojuegos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VideojuegoController {

    private final VideojuegoService videojuegoService;

    @GetMapping
    public ResponseEntity<List<VideojuegoDto>> getAllVideojuegos(){
        return ResponseEntity.ok(videojuegoService.getAllVideojuegos());
    }
}
