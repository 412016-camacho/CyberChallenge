package ar.edu.utn.frc.tup.piii.services.impl;

import ar.edu.utn.frc.tup.piii.dtos.ClienteDto;
import ar.edu.utn.frc.tup.piii.dtos.PuestojuegoDto;
import ar.edu.utn.frc.tup.piii.dtos.VideojuegoDto;
import ar.edu.utn.frc.tup.piii.entities.ClienteEntity;
import ar.edu.utn.frc.tup.piii.entities.PuestojuegoEntity;
import ar.edu.utn.frc.tup.piii.entities.VideojuegoEntity;
import ar.edu.utn.frc.tup.piii.repositories.ClienteRepository;
import ar.edu.utn.frc.tup.piii.repositories.PuestojuegoRepository;
import ar.edu.utn.frc.tup.piii.services.PuestojuegoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PuestojuegoServiceImpl implements PuestojuegoService {

    private final PuestojuegoRepository puestoRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<PuestojuegoDto> getAllPuestos() {
        List<PuestojuegoEntity> puestos = puestoRepository.findAll();
        return modelMapper.map(puestos, new TypeToken<List<PuestojuegoDto>>() {}.getType());
    }

    @Override
    public Optional<PuestojuegoEntity> findById(Long puestoId) {
       return puestoRepository.findById(puestoId);
    }
}
