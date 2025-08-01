package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity,Long> {
    List<ReservaEntity> findAllByClienteIdAndVideojuego_IdAndPuesto_IdAndFechaHora(Optional<Long> clienteId, Optional<Long> videojuegoId, Optional<Long> puestoId, Optional<LocalDateTime> fechaHora);

    List<ReservaEntity> findAllByClienteId(Optional<Long> clienteId);

    List<ReservaEntity> findAllByVideojuego_Id(Optional<Long> videojuegoId);

    List<ReservaEntity> findAllByPuesto_Id(Optional<Long> puestoId);

    @Query("SELECT r FROM ReservaEntity r WHERE r.fechaHora BETWEEN :inicio AND :fin")
    List<ReservaEntity> findAllByFechaHora(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

}
