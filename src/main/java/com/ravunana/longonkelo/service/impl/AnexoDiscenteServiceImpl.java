package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.AnexoDiscente;
import com.ravunana.longonkelo.repository.AnexoDiscenteRepository;
import com.ravunana.longonkelo.service.AnexoDiscenteService;
import com.ravunana.longonkelo.service.dto.AnexoDiscenteDTO;
import com.ravunana.longonkelo.service.mapper.AnexoDiscenteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnexoDiscente}.
 */
@Service
@Transactional
public class AnexoDiscenteServiceImpl implements AnexoDiscenteService {

    private final Logger log = LoggerFactory.getLogger(AnexoDiscenteServiceImpl.class);

    private final AnexoDiscenteRepository anexoDiscenteRepository;

    private final AnexoDiscenteMapper anexoDiscenteMapper;

    public AnexoDiscenteServiceImpl(AnexoDiscenteRepository anexoDiscenteRepository, AnexoDiscenteMapper anexoDiscenteMapper) {
        this.anexoDiscenteRepository = anexoDiscenteRepository;
        this.anexoDiscenteMapper = anexoDiscenteMapper;
    }

    @Override
    public AnexoDiscenteDTO save(AnexoDiscenteDTO anexoDiscenteDTO) {
        log.debug("Request to save AnexoDiscente : {}", anexoDiscenteDTO);
        AnexoDiscente anexoDiscente = anexoDiscenteMapper.toEntity(anexoDiscenteDTO);
        anexoDiscente = anexoDiscenteRepository.save(anexoDiscente);
        return anexoDiscenteMapper.toDto(anexoDiscente);
    }

    @Override
    public AnexoDiscenteDTO update(AnexoDiscenteDTO anexoDiscenteDTO) {
        log.debug("Request to update AnexoDiscente : {}", anexoDiscenteDTO);
        AnexoDiscente anexoDiscente = anexoDiscenteMapper.toEntity(anexoDiscenteDTO);
        anexoDiscente = anexoDiscenteRepository.save(anexoDiscente);
        return anexoDiscenteMapper.toDto(anexoDiscente);
    }

    @Override
    public Optional<AnexoDiscenteDTO> partialUpdate(AnexoDiscenteDTO anexoDiscenteDTO) {
        log.debug("Request to partially update AnexoDiscente : {}", anexoDiscenteDTO);

        return anexoDiscenteRepository
            .findById(anexoDiscenteDTO.getId())
            .map(existingAnexoDiscente -> {
                anexoDiscenteMapper.partialUpdate(existingAnexoDiscente, anexoDiscenteDTO);

                return existingAnexoDiscente;
            })
            .map(anexoDiscenteRepository::save)
            .map(anexoDiscenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnexoDiscenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnexoDiscentes");
        return anexoDiscenteRepository.findAll(pageable).map(anexoDiscenteMapper::toDto);
    }

    public Page<AnexoDiscenteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return anexoDiscenteRepository.findAllWithEagerRelationships(pageable).map(anexoDiscenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnexoDiscenteDTO> findOne(Long id) {
        log.debug("Request to get AnexoDiscente : {}", id);
        return anexoDiscenteRepository.findOneWithEagerRelationships(id).map(anexoDiscenteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnexoDiscente : {}", id);
        anexoDiscenteRepository.deleteById(id);
    }
}
