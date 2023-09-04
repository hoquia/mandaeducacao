package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.SequenciaDocumento;
import com.ravunana.longonkelo.repository.SequenciaDocumentoRepository;
import com.ravunana.longonkelo.service.SequenciaDocumentoService;
import com.ravunana.longonkelo.service.dto.SequenciaDocumentoDTO;
import com.ravunana.longonkelo.service.mapper.SequenciaDocumentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SequenciaDocumento}.
 */
@Service
@Transactional
public class SequenciaDocumentoServiceImpl implements SequenciaDocumentoService {

    private final Logger log = LoggerFactory.getLogger(SequenciaDocumentoServiceImpl.class);

    private final SequenciaDocumentoRepository sequenciaDocumentoRepository;

    private final SequenciaDocumentoMapper sequenciaDocumentoMapper;

    public SequenciaDocumentoServiceImpl(
        SequenciaDocumentoRepository sequenciaDocumentoRepository,
        SequenciaDocumentoMapper sequenciaDocumentoMapper
    ) {
        this.sequenciaDocumentoRepository = sequenciaDocumentoRepository;
        this.sequenciaDocumentoMapper = sequenciaDocumentoMapper;
    }

    @Override
    public SequenciaDocumentoDTO save(SequenciaDocumentoDTO sequenciaDocumentoDTO) {
        log.debug("Request to save SequenciaDocumento : {}", sequenciaDocumentoDTO);
        SequenciaDocumento sequenciaDocumento = sequenciaDocumentoMapper.toEntity(sequenciaDocumentoDTO);
        sequenciaDocumento = sequenciaDocumentoRepository.save(sequenciaDocumento);
        return sequenciaDocumentoMapper.toDto(sequenciaDocumento);
    }

    @Override
    public SequenciaDocumentoDTO update(SequenciaDocumentoDTO sequenciaDocumentoDTO) {
        log.debug("Request to update SequenciaDocumento : {}", sequenciaDocumentoDTO);
        SequenciaDocumento sequenciaDocumento = sequenciaDocumentoMapper.toEntity(sequenciaDocumentoDTO);
        sequenciaDocumento = sequenciaDocumentoRepository.save(sequenciaDocumento);
        return sequenciaDocumentoMapper.toDto(sequenciaDocumento);
    }

    @Override
    public Optional<SequenciaDocumentoDTO> partialUpdate(SequenciaDocumentoDTO sequenciaDocumentoDTO) {
        log.debug("Request to partially update SequenciaDocumento : {}", sequenciaDocumentoDTO);

        return sequenciaDocumentoRepository
            .findById(sequenciaDocumentoDTO.getId())
            .map(existingSequenciaDocumento -> {
                sequenciaDocumentoMapper.partialUpdate(existingSequenciaDocumento, sequenciaDocumentoDTO);

                return existingSequenciaDocumento;
            })
            .map(sequenciaDocumentoRepository::save)
            .map(sequenciaDocumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SequenciaDocumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenciaDocumentos");
        return sequenciaDocumentoRepository.findAll(pageable).map(sequenciaDocumentoMapper::toDto);
    }

    public Page<SequenciaDocumentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sequenciaDocumentoRepository.findAllWithEagerRelationships(pageable).map(sequenciaDocumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SequenciaDocumentoDTO> findOne(Long id) {
        log.debug("Request to get SequenciaDocumento : {}", id);
        return sequenciaDocumentoRepository.findOneWithEagerRelationships(id).map(sequenciaDocumentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SequenciaDocumento : {}", id);
        sequenciaDocumentoRepository.deleteById(id);
    }
}
