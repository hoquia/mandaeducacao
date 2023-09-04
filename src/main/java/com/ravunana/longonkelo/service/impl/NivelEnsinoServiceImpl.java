package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.NivelEnsino;
import com.ravunana.longonkelo.repository.NivelEnsinoRepository;
import com.ravunana.longonkelo.service.NivelEnsinoService;
import com.ravunana.longonkelo.service.dto.NivelEnsinoDTO;
import com.ravunana.longonkelo.service.mapper.NivelEnsinoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NivelEnsino}.
 */
@Service
@Transactional
public class NivelEnsinoServiceImpl implements NivelEnsinoService {

    private final Logger log = LoggerFactory.getLogger(NivelEnsinoServiceImpl.class);

    private final NivelEnsinoRepository nivelEnsinoRepository;

    private final NivelEnsinoMapper nivelEnsinoMapper;

    public NivelEnsinoServiceImpl(NivelEnsinoRepository nivelEnsinoRepository, NivelEnsinoMapper nivelEnsinoMapper) {
        this.nivelEnsinoRepository = nivelEnsinoRepository;
        this.nivelEnsinoMapper = nivelEnsinoMapper;
    }

    @Override
    public NivelEnsinoDTO save(NivelEnsinoDTO nivelEnsinoDTO) {
        log.debug("Request to save NivelEnsino : {}", nivelEnsinoDTO);
        NivelEnsino nivelEnsino = nivelEnsinoMapper.toEntity(nivelEnsinoDTO);
        nivelEnsino = nivelEnsinoRepository.save(nivelEnsino);
        return nivelEnsinoMapper.toDto(nivelEnsino);
    }

    @Override
    public NivelEnsinoDTO update(NivelEnsinoDTO nivelEnsinoDTO) {
        log.debug("Request to update NivelEnsino : {}", nivelEnsinoDTO);
        NivelEnsino nivelEnsino = nivelEnsinoMapper.toEntity(nivelEnsinoDTO);
        nivelEnsino = nivelEnsinoRepository.save(nivelEnsino);
        return nivelEnsinoMapper.toDto(nivelEnsino);
    }

    @Override
    public Optional<NivelEnsinoDTO> partialUpdate(NivelEnsinoDTO nivelEnsinoDTO) {
        log.debug("Request to partially update NivelEnsino : {}", nivelEnsinoDTO);

        return nivelEnsinoRepository
            .findById(nivelEnsinoDTO.getId())
            .map(existingNivelEnsino -> {
                nivelEnsinoMapper.partialUpdate(existingNivelEnsino, nivelEnsinoDTO);

                return existingNivelEnsino;
            })
            .map(nivelEnsinoRepository::save)
            .map(nivelEnsinoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NivelEnsinoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NivelEnsinos");
        return nivelEnsinoRepository.findAll(pageable).map(nivelEnsinoMapper::toDto);
    }

    public Page<NivelEnsinoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return nivelEnsinoRepository.findAllWithEagerRelationships(pageable).map(nivelEnsinoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NivelEnsinoDTO> findOne(Long id) {
        log.debug("Request to get NivelEnsino : {}", id);
        return nivelEnsinoRepository.findOneWithEagerRelationships(id).map(nivelEnsinoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NivelEnsino : {}", id);
        nivelEnsinoRepository.deleteById(id);
    }
}
