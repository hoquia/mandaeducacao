package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.repository.DiscenteRepository;
import com.ravunana.longonkelo.service.DiscenteService;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.mapper.DiscenteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Discente}.
 */
@Service
@Transactional
public class DiscenteServiceImpl implements DiscenteService {

    private final Logger log = LoggerFactory.getLogger(DiscenteServiceImpl.class);

    private final DiscenteRepository discenteRepository;

    private final DiscenteMapper discenteMapper;

    public DiscenteServiceImpl(DiscenteRepository discenteRepository, DiscenteMapper discenteMapper) {
        this.discenteRepository = discenteRepository;
        this.discenteMapper = discenteMapper;
    }

    @Override
    public DiscenteDTO save(DiscenteDTO discenteDTO) {
        log.debug("Request to save Discente : {}", discenteDTO);
        Discente discente = discenteMapper.toEntity(discenteDTO);
        discente = discenteRepository.save(discente);
        return discenteMapper.toDto(discente);
    }

    @Override
    public DiscenteDTO update(DiscenteDTO discenteDTO) {
        log.debug("Request to update Discente : {}", discenteDTO);
        Discente discente = discenteMapper.toEntity(discenteDTO);
        discente = discenteRepository.save(discente);
        return discenteMapper.toDto(discente);
    }

    @Override
    public Optional<DiscenteDTO> partialUpdate(DiscenteDTO discenteDTO) {
        log.debug("Request to partially update Discente : {}", discenteDTO);

        return discenteRepository
            .findById(discenteDTO.getId())
            .map(existingDiscente -> {
                discenteMapper.partialUpdate(existingDiscente, discenteDTO);

                return existingDiscente;
            })
            .map(discenteRepository::save)
            .map(discenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiscenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Discentes");
        return discenteRepository.findAll(pageable).map(discenteMapper::toDto);
    }

    public Page<DiscenteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return discenteRepository.findAllWithEagerRelationships(pageable).map(discenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiscenteDTO> findOne(Long id) {
        log.debug("Request to get Discente : {}", id);
        return discenteRepository.findOneWithEagerRelationships(id).map(discenteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discente : {}", id);
        discenteRepository.deleteById(id);
    }
}
