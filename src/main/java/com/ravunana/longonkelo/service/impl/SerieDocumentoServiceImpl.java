package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.SerieDocumento;
import com.ravunana.longonkelo.repository.SerieDocumentoRepository;
import com.ravunana.longonkelo.service.SerieDocumentoService;
import com.ravunana.longonkelo.service.dto.SerieDocumentoDTO;
import com.ravunana.longonkelo.service.mapper.SerieDocumentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SerieDocumento}.
 */
@Service
@Transactional
public class SerieDocumentoServiceImpl implements SerieDocumentoService {

    private final Logger log = LoggerFactory.getLogger(SerieDocumentoServiceImpl.class);

    private final SerieDocumentoRepository serieDocumentoRepository;

    private final SerieDocumentoMapper serieDocumentoMapper;

    public SerieDocumentoServiceImpl(SerieDocumentoRepository serieDocumentoRepository, SerieDocumentoMapper serieDocumentoMapper) {
        this.serieDocumentoRepository = serieDocumentoRepository;
        this.serieDocumentoMapper = serieDocumentoMapper;
    }

    @Override
    public SerieDocumentoDTO save(SerieDocumentoDTO serieDocumentoDTO) {
        log.debug("Request to save SerieDocumento : {}", serieDocumentoDTO);
        SerieDocumento serieDocumento = serieDocumentoMapper.toEntity(serieDocumentoDTO);
        serieDocumento = serieDocumentoRepository.save(serieDocumento);
        return serieDocumentoMapper.toDto(serieDocumento);
    }

    @Override
    public SerieDocumentoDTO update(SerieDocumentoDTO serieDocumentoDTO) {
        log.debug("Request to update SerieDocumento : {}", serieDocumentoDTO);
        SerieDocumento serieDocumento = serieDocumentoMapper.toEntity(serieDocumentoDTO);
        serieDocumento = serieDocumentoRepository.save(serieDocumento);
        return serieDocumentoMapper.toDto(serieDocumento);
    }

    @Override
    public Optional<SerieDocumentoDTO> partialUpdate(SerieDocumentoDTO serieDocumentoDTO) {
        log.debug("Request to partially update SerieDocumento : {}", serieDocumentoDTO);

        return serieDocumentoRepository
            .findById(serieDocumentoDTO.getId())
            .map(existingSerieDocumento -> {
                serieDocumentoMapper.partialUpdate(existingSerieDocumento, serieDocumentoDTO);

                return existingSerieDocumento;
            })
            .map(serieDocumentoRepository::save)
            .map(serieDocumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SerieDocumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SerieDocumentos");
        return serieDocumentoRepository.findAll(pageable).map(serieDocumentoMapper::toDto);
    }

    public Page<SerieDocumentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return serieDocumentoRepository.findAllWithEagerRelationships(pageable).map(serieDocumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SerieDocumentoDTO> findOne(Long id) {
        log.debug("Request to get SerieDocumento : {}", id);
        return serieDocumentoRepository.findOneWithEagerRelationships(id).map(serieDocumentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SerieDocumento : {}", id);
        serieDocumentoRepository.deleteById(id);
    }
}
