package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.DocumentoComercial;
import com.ravunana.longonkelo.repository.DocumentoComercialRepository;
import com.ravunana.longonkelo.service.DocumentoComercialService;
import com.ravunana.longonkelo.service.dto.DocumentoComercialDTO;
import com.ravunana.longonkelo.service.mapper.DocumentoComercialMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DocumentoComercial}.
 */
@Service
@Transactional
public class DocumentoComercialServiceImpl implements DocumentoComercialService {

    private final Logger log = LoggerFactory.getLogger(DocumentoComercialServiceImpl.class);

    private final DocumentoComercialRepository documentoComercialRepository;

    private final DocumentoComercialMapper documentoComercialMapper;

    public DocumentoComercialServiceImpl(
        DocumentoComercialRepository documentoComercialRepository,
        DocumentoComercialMapper documentoComercialMapper
    ) {
        this.documentoComercialRepository = documentoComercialRepository;
        this.documentoComercialMapper = documentoComercialMapper;
    }

    @Override
    public DocumentoComercialDTO save(DocumentoComercialDTO documentoComercialDTO) {
        log.debug("Request to save DocumentoComercial : {}", documentoComercialDTO);
        DocumentoComercial documentoComercial = documentoComercialMapper.toEntity(documentoComercialDTO);
        documentoComercial = documentoComercialRepository.save(documentoComercial);
        return documentoComercialMapper.toDto(documentoComercial);
    }

    @Override
    public DocumentoComercialDTO update(DocumentoComercialDTO documentoComercialDTO) {
        log.debug("Request to update DocumentoComercial : {}", documentoComercialDTO);
        DocumentoComercial documentoComercial = documentoComercialMapper.toEntity(documentoComercialDTO);
        documentoComercial = documentoComercialRepository.save(documentoComercial);
        return documentoComercialMapper.toDto(documentoComercial);
    }

    @Override
    public Optional<DocumentoComercialDTO> partialUpdate(DocumentoComercialDTO documentoComercialDTO) {
        log.debug("Request to partially update DocumentoComercial : {}", documentoComercialDTO);

        return documentoComercialRepository
            .findById(documentoComercialDTO.getId())
            .map(existingDocumentoComercial -> {
                documentoComercialMapper.partialUpdate(existingDocumentoComercial, documentoComercialDTO);

                return existingDocumentoComercial;
            })
            .map(documentoComercialRepository::save)
            .map(documentoComercialMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentoComercialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentoComercials");
        return documentoComercialRepository.findAll(pageable).map(documentoComercialMapper::toDto);
    }

    public Page<DocumentoComercialDTO> findAllWithEagerRelationships(Pageable pageable) {
        return documentoComercialRepository.findAllWithEagerRelationships(pageable).map(documentoComercialMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentoComercialDTO> findOne(Long id) {
        log.debug("Request to get DocumentoComercial : {}", id);
        return documentoComercialRepository.findOneWithEagerRelationships(id).map(documentoComercialMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentoComercial : {}", id);
        documentoComercialRepository.deleteById(id);
    }
}
