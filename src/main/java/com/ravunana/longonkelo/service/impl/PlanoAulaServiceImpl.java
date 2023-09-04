package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.repository.PlanoAulaRepository;
import com.ravunana.longonkelo.service.PlanoAulaService;
import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import com.ravunana.longonkelo.service.mapper.PlanoAulaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlanoAula}.
 */
@Service
@Transactional
public class PlanoAulaServiceImpl implements PlanoAulaService {

    private final Logger log = LoggerFactory.getLogger(PlanoAulaServiceImpl.class);

    private final PlanoAulaRepository planoAulaRepository;

    private final PlanoAulaMapper planoAulaMapper;

    public PlanoAulaServiceImpl(PlanoAulaRepository planoAulaRepository, PlanoAulaMapper planoAulaMapper) {
        this.planoAulaRepository = planoAulaRepository;
        this.planoAulaMapper = planoAulaMapper;
    }

    @Override
    public PlanoAulaDTO save(PlanoAulaDTO planoAulaDTO) {
        log.debug("Request to save PlanoAula : {}", planoAulaDTO);
        PlanoAula planoAula = planoAulaMapper.toEntity(planoAulaDTO);
        planoAula = planoAulaRepository.save(planoAula);
        return planoAulaMapper.toDto(planoAula);
    }

    @Override
    public PlanoAulaDTO update(PlanoAulaDTO planoAulaDTO) {
        log.debug("Request to update PlanoAula : {}", planoAulaDTO);
        PlanoAula planoAula = planoAulaMapper.toEntity(planoAulaDTO);
        planoAula = planoAulaRepository.save(planoAula);
        return planoAulaMapper.toDto(planoAula);
    }

    @Override
    public Optional<PlanoAulaDTO> partialUpdate(PlanoAulaDTO planoAulaDTO) {
        log.debug("Request to partially update PlanoAula : {}", planoAulaDTO);

        return planoAulaRepository
            .findById(planoAulaDTO.getId())
            .map(existingPlanoAula -> {
                planoAulaMapper.partialUpdate(existingPlanoAula, planoAulaDTO);

                return existingPlanoAula;
            })
            .map(planoAulaRepository::save)
            .map(planoAulaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoAulaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoAulas");
        return planoAulaRepository.findAll(pageable).map(planoAulaMapper::toDto);
    }

    public Page<PlanoAulaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return planoAulaRepository.findAllWithEagerRelationships(pageable).map(planoAulaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoAulaDTO> findOne(Long id) {
        log.debug("Request to get PlanoAula : {}", id);
        return planoAulaRepository.findOneWithEagerRelationships(id).map(planoAulaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoAula : {}", id);
        planoAulaRepository.deleteById(id);
    }
}
