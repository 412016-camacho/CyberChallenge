package ar.edu.utn.frc.tup.piii.services.impl;

import ar.edu.utn.frc.tup.piii.dtos.ClienteDto;
import ar.edu.utn.frc.tup.piii.dtos.ReservaDto;
import ar.edu.utn.frc.tup.piii.entities.ClienteEntity;
import ar.edu.utn.frc.tup.piii.entities.PuestojuegoEntity;
import ar.edu.utn.frc.tup.piii.entities.ReservaEntity;
import ar.edu.utn.frc.tup.piii.entities.VideojuegoEntity;
import ar.edu.utn.frc.tup.piii.repositories.ReservaRepository;
import ar.edu.utn.frc.tup.piii.services.ClienteService;
import ar.edu.utn.frc.tup.piii.services.PuestojuegoService;
import ar.edu.utn.frc.tup.piii.services.ReservaService;
import ar.edu.utn.frc.tup.piii.services.VideojuegoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteService clienteService;
    private final VideojuegoService videojuegoService;
    private final PuestojuegoService puestojuegoService;

    private final ModelMapper modelMapper;

    @Override
    public List<ReservaDto> getAllReservas() {
        List<ReservaEntity> reservas = reservaRepository.findAll();
        return modelMapper.map(reservas, new TypeToken<List<ReservaDto>>() {}.getType());
    }

    @Override
    public List<ReservaDto> getAllReservasFiltradas(Optional<Long> clienteId, Optional<Long> videojuegoId, Optional<Long> puestoId, Optional<LocalDateTime> fechaHora) {
        List<ReservaEntity> reservas = reservaRepository.findAllByClienteIdAndVideojuego_IdAndPuesto_IdAndFechaHora(clienteId,videojuegoId,puestoId,fechaHora);
        List<ReservaDto> reservasFiltradas = new ArrayList<>();
        for(ReservaEntity r : reservas){
            reservasFiltradas.add(modelMapper.map(r, new TypeToken<ReservaDto>(){}.getType()));
        }
        return reservasFiltradas;
    }

    @Override
    public List<ReservaDto> getAllReservasByClienteId(Optional<Long> clienteId) {
        List<ReservaEntity> reservas = reservaRepository.findAllByClienteId(clienteId);
        List<ReservaDto> reservasFiltradas = new ArrayList<>();
        for(ReservaEntity r : reservas){
            reservasFiltradas.add(modelMapper.map(r, new TypeToken<ReservaDto>(){}.getType()));
        }
        return reservasFiltradas;
    }

    @Override
    public List<ReservaDto> getAllReservasByVideojuegoId(Optional<Long> videojuegoId) {
        List<ReservaEntity> reservas = reservaRepository.findAllByVideojuego_Id(videojuegoId);
        List<ReservaDto> reservasFiltradas = new ArrayList<>();
        for(ReservaEntity r : reservas){
            reservasFiltradas.add(modelMapper.map(r, new TypeToken<ReservaDto>(){}.getType()));
        }
        return reservasFiltradas;
    }

    @Override
    public List<ReservaDto> getAllReservasByPuestoId(Optional<Long> puestoId) {
        List<ReservaEntity> reservas = reservaRepository.findAllByPuesto_Id(puestoId);
        List<ReservaDto> reservasFiltradas = new ArrayList<>();
        for(ReservaEntity r : reservas){
            reservasFiltradas.add(modelMapper.map(r, new TypeToken<ReservaDto>(){}.getType()));
        }
        return reservasFiltradas;
    }

    @Override
    public List<ReservaDto> getAllReservasByFechaHora(Optional<LocalDateTime> fechaHora) {
        LocalDate fecha = fechaHora.get().toLocalDate();
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(LocalTime.MAX);

        List<ReservaEntity> reservas = reservaRepository.findAllByFechaHora(inicio, fin);

        List<ReservaDto> reservasFiltradas = new ArrayList<>();
        for(ReservaEntity r : reservas){
            if(r.getFechaHora().toLocalDate().equals(fecha)){
                reservasFiltradas.add(modelMapper.map(r, new TypeToken<ReservaDto>(){}.getType()));
            }
        }
        return reservasFiltradas;
    }

    @Override
    public ReservaDto createReserva(ReservaDto reserva) {
        validar(reserva);

        ReservaEntity reservaEntity = new ReservaEntity();
        Optional<ClienteEntity> cliente = Optional.ofNullable(clienteService.getClienteById(reserva.getClienteId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Cliente no encontrado")));
        Optional<VideojuegoEntity> videojuego = Optional.ofNullable(videojuegoService.findById(reserva.getVideojuegoId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Videojuego no encontrado")));
        Optional<PuestojuegoEntity> puesto = Optional.ofNullable(puestojuegoService.findById(reserva.getPuestoId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Puestojuego no encontrado")));
        reservaEntity.setCliente(cliente.get());
        reservaEntity.setVideojuego(videojuego.get());
        reservaEntity.setPuesto(puesto.get());
        reservaEntity.setFechaHora(reserva.getFechaHora());
        reservaEntity.setDuracionMinutos(reserva.getDuracionMinutos());
        ReservaEntity reservaSaved = reservaRepository.save(reservaEntity);

        return modelMapper.map(reservaSaved, new TypeToken<ReservaDto>(){}.getType());
    }

    private boolean validar(ReservaDto reserva){
        LocalTime horaReserva = reserva.getFechaHora().toLocalTime();
        LocalTime inicio = LocalTime.of(10, 0);
        LocalTime fin = LocalTime.of(22, 0);
        if(horaReserva.isBefore(inicio) || horaReserva.isAfter(fin)){
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "El horario de atención es de 10:00 - 22:00");
        }
        LocalDate dia = reserva.getFechaHora().toLocalDate();
        if(this.getAllReservas().stream().anyMatch(r -> r.getFechaHora().toLocalDate().isEqual(dia)
                && r.getClienteId().equals(reserva.getClienteId()))) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Ya tiene una reserva para este día.");
        }
        if(this.getAllReservas().stream().anyMatch(r -> r.getFechaHora().toLocalDate().isEqual(dia)
                && r.getPuestoId().equals(reserva.getPuestoId()))) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "No se permite solapamiento de reservas en el mismo puesto.");
        }
        Integer duracion = reserva.getDuracionMinutos();
        if(duracion != 30 && duracion != 60 && duracion != 90 && duracion != 120) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Las reservas deben tener duración exacta: 30, 60, 90 o 120 minutos");
        }
        return true;
    }

}
