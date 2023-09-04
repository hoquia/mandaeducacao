package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.repository.PlanoMultaRepository;
import com.ravunana.longonkelo.service.PlanoMultaService;
import com.ravunana.longonkelo.service.dto.PlanoMultaDTO;
import com.ravunana.longonkelo.service.mapper.PlanoMultaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlanoMulta}.
 */
@Service
@Transactional
public class PlanoMultaServiceImpl implements PlanoMultaService {

    private final Logger log = LoggerFactory.getLogger(PlanoMultaServiceImpl.class);

    private final PlanoMultaRepository planoMultaRepository;

    private final PlanoMultaMapper planoMultaMapper;

    public PlanoMultaServiceImpl(PlanoMultaRepository planoMultaRepository, PlanoMultaMapper planoMultaMapper) {
        this.planoMultaRepository = planoMultaRepository;
        this.planoMultaMapper = planoMultaMapper;
    }

    @Override
    public PlanoMultaDTO save(PlanoMultaDTO planoMultaDTO) {
        log.debug("Request to save PlanoMulta : {}", planoMultaDTO);
        PlanoMulta planoMulta = planoMultaMapper.toEntity(planoMultaDTO);
        planoMulta = planoMultaRepository.save(planoMulta);
        return planoMultaMapper.toDto(planoMulta);
    }

    @Override
    public PlanoMultaDTO update(PlanoMultaDTO planoMultaDTO) {
        log.debug("Request to update PlanoMulta : {}", planoMultaDTO);
        PlanoMulta planoMulta = planoMultaMapper.toEntity(planoMultaDTO);
        planoMulta = planoMultaRepository.save(planoMulta);
        return planoMultaMapper.toDto(planoMulta);
    }

    @Override
    public Optional<PlanoMultaDTO> partialUpdate(PlanoMultaDTO planoMultaDTO) {
        log.debug("Request to partially update PlanoMulta : {}", planoMultaDTO);

        return planoMultaRepository
            .findById(planoMultaDTO.getId())
            .map(existingPlanoMulta -> {
                planoMultaMapper.partialUpdate(existingPlanoMulta, planoMultaDTO);

                return existingPlanoMulta;
            })
            .map(planoMultaRepository::save)
            .map(planoMultaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoMultaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoMultas");
        return planoMultaRepository.findAll(pageable).map(planoMultaMapper::toDto);
    }

    public Page<PlanoMultaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return planoMultaRepository.findAllWithEagerRelationships(pageable).map(planoMultaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoMultaDTO> findOne(Long id) {
        log.debug("Request to get PlanoMulta : {}", id);
        return planoMultaRepository.findOneWithEagerRelationships(id).map(planoMultaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoMulta : {}", id);
        planoMultaRepository.deleteById(id);
    }
}
