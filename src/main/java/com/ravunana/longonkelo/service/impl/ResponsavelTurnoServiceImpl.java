package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ResponsavelTurno;
import com.ravunana.longonkelo.repository.ResponsavelTurnoRepository;
import com.ravunana.longonkelo.service.ResponsavelTurnoService;
import com.ravunana.longonkelo.service.dto.ResponsavelTurnoDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelTurnoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResponsavelTurno}.
 */
@Service
@Transactional
public class ResponsavelTurnoServiceImpl implements ResponsavelTurnoService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelTurnoServiceImpl.class);

    private final ResponsavelTurnoRepository responsavelTurnoRepository;

    private final ResponsavelTurnoMapper responsavelTurnoMapper;

    public ResponsavelTurnoServiceImpl(
        ResponsavelTurnoRepository responsavelTurnoRepository,
        ResponsavelTurnoMapper responsavelTurnoMapper
    ) {
        this.responsavelTurnoRepository = responsavelTurnoRepository;
        this.responsavelTurnoMapper = responsavelTurnoMapper;
    }

    @Override
    public ResponsavelTurnoDTO save(ResponsavelTurnoDTO responsavelTurnoDTO) {
        log.debug("Request to save ResponsavelTurno : {}", responsavelTurnoDTO);
        ResponsavelTurno responsavelTurno = responsavelTurnoMapper.toEntity(responsavelTurnoDTO);
        responsavelTurno = responsavelTurnoRepository.save(responsavelTurno);
        return responsavelTurnoMapper.toDto(responsavelTurno);
    }

    @Override
    public ResponsavelTurnoDTO update(ResponsavelTurnoDTO responsavelTurnoDTO) {
        log.debug("Request to update ResponsavelTurno : {}", responsavelTurnoDTO);
        ResponsavelTurno responsavelTurno = responsavelTurnoMapper.toEntity(responsavelTurnoDTO);
        responsavelTurno = responsavelTurnoRepository.save(responsavelTurno);
        return responsavelTurnoMapper.toDto(responsavelTurno);
    }

    @Override
    public Optional<ResponsavelTurnoDTO> partialUpdate(ResponsavelTurnoDTO responsavelTurnoDTO) {
        log.debug("Request to partially update ResponsavelTurno : {}", responsavelTurnoDTO);

        return responsavelTurnoRepository
            .findById(responsavelTurnoDTO.getId())
            .map(existingResponsavelTurno -> {
                responsavelTurnoMapper.partialUpdate(existingResponsavelTurno, responsavelTurnoDTO);

                return existingResponsavelTurno;
            })
            .map(responsavelTurnoRepository::save)
            .map(responsavelTurnoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsavelTurnoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsavelTurnos");
        return responsavelTurnoRepository.findAll(pageable).map(responsavelTurnoMapper::toDto);
    }

    public Page<ResponsavelTurnoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return responsavelTurnoRepository.findAllWithEagerRelationships(pageable).map(responsavelTurnoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsavelTurnoDTO> findOne(Long id) {
        log.debug("Request to get ResponsavelTurno : {}", id);
        return responsavelTurnoRepository.findOneWithEagerRelationships(id).map(responsavelTurnoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResponsavelTurno : {}", id);
        responsavelTurnoRepository.deleteById(id);
    }
}
