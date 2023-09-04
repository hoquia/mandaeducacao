package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Turno;
import com.ravunana.longonkelo.repository.TurnoRepository;
import com.ravunana.longonkelo.service.TurnoService;
import com.ravunana.longonkelo.service.dto.TurnoDTO;
import com.ravunana.longonkelo.service.mapper.TurnoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Turno}.
 */
@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {

    private final Logger log = LoggerFactory.getLogger(TurnoServiceImpl.class);

    private final TurnoRepository turnoRepository;

    private final TurnoMapper turnoMapper;

    public TurnoServiceImpl(TurnoRepository turnoRepository, TurnoMapper turnoMapper) {
        this.turnoRepository = turnoRepository;
        this.turnoMapper = turnoMapper;
    }

    @Override
    public TurnoDTO save(TurnoDTO turnoDTO) {
        log.debug("Request to save Turno : {}", turnoDTO);
        Turno turno = turnoMapper.toEntity(turnoDTO);
        turno = turnoRepository.save(turno);
        return turnoMapper.toDto(turno);
    }

    @Override
    public TurnoDTO update(TurnoDTO turnoDTO) {
        log.debug("Request to update Turno : {}", turnoDTO);
        Turno turno = turnoMapper.toEntity(turnoDTO);
        turno = turnoRepository.save(turno);
        return turnoMapper.toDto(turno);
    }

    @Override
    public Optional<TurnoDTO> partialUpdate(TurnoDTO turnoDTO) {
        log.debug("Request to partially update Turno : {}", turnoDTO);

        return turnoRepository
            .findById(turnoDTO.getId())
            .map(existingTurno -> {
                turnoMapper.partialUpdate(existingTurno, turnoDTO);

                return existingTurno;
            })
            .map(turnoRepository::save)
            .map(turnoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TurnoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Turnos");
        return turnoRepository.findAll(pageable).map(turnoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TurnoDTO> findOne(Long id) {
        log.debug("Request to get Turno : {}", id);
        return turnoRepository.findById(id).map(turnoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Turno : {}", id);
        turnoRepository.deleteById(id);
    }
}
