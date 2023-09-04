package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.PlanoDesconto;
import com.ravunana.longonkelo.repository.PlanoDescontoRepository;
import com.ravunana.longonkelo.service.PlanoDescontoService;
import com.ravunana.longonkelo.service.dto.PlanoDescontoDTO;
import com.ravunana.longonkelo.service.mapper.PlanoDescontoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlanoDesconto}.
 */
@Service
@Transactional
public class PlanoDescontoServiceImpl implements PlanoDescontoService {

    private final Logger log = LoggerFactory.getLogger(PlanoDescontoServiceImpl.class);

    private final PlanoDescontoRepository planoDescontoRepository;

    private final PlanoDescontoMapper planoDescontoMapper;

    public PlanoDescontoServiceImpl(PlanoDescontoRepository planoDescontoRepository, PlanoDescontoMapper planoDescontoMapper) {
        this.planoDescontoRepository = planoDescontoRepository;
        this.planoDescontoMapper = planoDescontoMapper;
    }

    @Override
    public PlanoDescontoDTO save(PlanoDescontoDTO planoDescontoDTO) {
        log.debug("Request to save PlanoDesconto : {}", planoDescontoDTO);
        PlanoDesconto planoDesconto = planoDescontoMapper.toEntity(planoDescontoDTO);
        planoDesconto = planoDescontoRepository.save(planoDesconto);
        return planoDescontoMapper.toDto(planoDesconto);
    }

    @Override
    public PlanoDescontoDTO update(PlanoDescontoDTO planoDescontoDTO) {
        log.debug("Request to update PlanoDesconto : {}", planoDescontoDTO);
        PlanoDesconto planoDesconto = planoDescontoMapper.toEntity(planoDescontoDTO);
        planoDesconto = planoDescontoRepository.save(planoDesconto);
        return planoDescontoMapper.toDto(planoDesconto);
    }

    @Override
    public Optional<PlanoDescontoDTO> partialUpdate(PlanoDescontoDTO planoDescontoDTO) {
        log.debug("Request to partially update PlanoDesconto : {}", planoDescontoDTO);

        return planoDescontoRepository
            .findById(planoDescontoDTO.getId())
            .map(existingPlanoDesconto -> {
                planoDescontoMapper.partialUpdate(existingPlanoDesconto, planoDescontoDTO);

                return existingPlanoDesconto;
            })
            .map(planoDescontoRepository::save)
            .map(planoDescontoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoDescontoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoDescontos");
        return planoDescontoRepository.findAll(pageable).map(planoDescontoMapper::toDto);
    }

    public Page<PlanoDescontoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return planoDescontoRepository.findAllWithEagerRelationships(pageable).map(planoDescontoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoDescontoDTO> findOne(Long id) {
        log.debug("Request to get PlanoDesconto : {}", id);
        return planoDescontoRepository.findOneWithEagerRelationships(id).map(planoDescontoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoDesconto : {}", id);
        planoDescontoRepository.deleteById(id);
    }
}
