package ar.edu.utn.frc.tup.piii.services.impl;

import ar.edu.utn.frc.tup.piii.dtos.VideojuegoDto;
import ar.edu.utn.frc.tup.piii.entities.VideojuegoEntity;
import ar.edu.utn.frc.tup.piii.repositories.VideojuegoRepository;
import ar.edu.utn.frc.tup.piii.services.VideojuegoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideojuegoServiceImpl implements VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<VideojuegoDto> getAllVideojuegos() {
        List<VideojuegoEntity> videojuegos = videojuegoRepository.findAll();
        return modelMapper.map(videojuegos, new TypeToken<List<VideojuegoDto>>() {}.getType());
    }

    @Override
    public Optional<VideojuegoEntity> findById(Long videojuegoId) {
        return videojuegoRepository.findById(videojuegoId);

    }
}
