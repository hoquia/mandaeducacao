package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.repository.EmolumentoRepository;
import com.ravunana.longonkelo.service.EmolumentoService;
import com.ravunana.longonkelo.service.dto.EmolumentoDTO;
import com.ravunana.longonkelo.service.mapper.EmolumentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Emolumento}.
 */
@Service
@Transactional
public class EmolumentoServiceImpl implements EmolumentoService {

    private final Logger log = LoggerFactory.getLogger(EmolumentoServiceImpl.class);

    private final EmolumentoRepository emolumentoRepository;

    private final EmolumentoMapper emolumentoMapper;

    public EmolumentoServiceImpl(EmolumentoRepository emolumentoRepository, EmolumentoMapper emolumentoMapper) {
        this.emolumentoRepository = emolumentoRepository;
        this.emolumentoMapper = emolumentoMapper;
    }

    @Override
    public EmolumentoDTO save(EmolumentoDTO emolumentoDTO) {
        log.debug("Request to save Emolumento : {}", emolumentoDTO);
        Emolumento emolumento = emolumentoMapper.toEntity(emolumentoDTO);
        emolumento = emolumentoRepository.save(emolumento);
        return emolumentoMapper.toDto(emolumento);
    }

    @Override
    public EmolumentoDTO update(EmolumentoDTO emolumentoDTO) {
        log.debug("Request to update Emolumento : {}", emolumentoDTO);
        Emolumento emolumento = emolumentoMapper.toEntity(emolumentoDTO);
        emolumento = emolumentoRepository.save(emolumento);
        return emolumentoMapper.toDto(emolumento);
    }

    @Override
    public Optional<EmolumentoDTO> partialUpdate(EmolumentoDTO emolumentoDTO) {
        log.debug("Request to partially update Emolumento : {}", emolumentoDTO);

        return emolumentoRepository
            .findById(emolumentoDTO.getId())
            .map(existingEmolumento -> {
                emolumentoMapper.partialUpdate(existingEmolumento, emolumentoDTO);

                return existingEmolumento;
            })
            .map(emolumentoRepository::save)
            .map(emolumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmolumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Emolumentos");
        return emolumentoRepository.findAll(pageable).map(emolumentoMapper::toDto);
    }

    public Page<EmolumentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return emolumentoRepository.findAllWithEagerRelationships(pageable).map(emolumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmolumentoDTO> findOne(Long id) {
        log.debug("Request to get Emolumento : {}", id);
        return emolumentoRepository.findOneWithEagerRelationships(id).map(emolumentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Emolumento : {}", id);
        emolumentoRepository.deleteById(id);
    }
}
