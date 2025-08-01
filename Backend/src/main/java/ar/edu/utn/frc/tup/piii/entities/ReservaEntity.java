package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="reservas")
@Data
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "videojuego_id")
    private VideojuegoEntity videojuego;

    @ManyToOne
    @JoinColumn(name = "puestojuego_id")
    private PuestojuegoEntity puesto;

    @Column
    private LocalDateTime fechaHora;

    @Column
    private Integer duracionMinutos;

    @Column
    private String observaciones;
}
