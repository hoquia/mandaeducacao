package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import com.ravunana.longonkelo.repository.CategoriaEmolumentoRepository;
import com.ravunana.longonkelo.service.CategoriaEmolumentoService;
import com.ravunana.longonkelo.service.dto.CategoriaEmolumentoDTO;
import com.ravunana.longonkelo.service.mapper.CategoriaEmolumentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategoriaEmolumento}.
 */
@Service
@Transactional
public class CategoriaEmolumentoServiceImpl implements CategoriaEmolumentoService {

    private final Logger log = LoggerFactory.getLogger(CategoriaEmolumentoServiceImpl.class);

    private final CategoriaEmolumentoRepository categoriaEmolumentoRepository;

    private final CategoriaEmolumentoMapper categoriaEmolumentoMapper;

    public CategoriaEmolumentoServiceImpl(
        CategoriaEmolumentoRepository categoriaEmolumentoRepository,
        CategoriaEmolumentoMapper categoriaEmolumentoMapper
    ) {
        this.categoriaEmolumentoRepository = categoriaEmolumentoRepository;
        this.categoriaEmolumentoMapper = categoriaEmolumentoMapper;
    }

    @Override
    public CategoriaEmolumentoDTO save(CategoriaEmolumentoDTO categoriaEmolumentoDTO) {
        log.debug("Request to save CategoriaEmolumento : {}", categoriaEmolumentoDTO);
        CategoriaEmolumento categoriaEmolumento = categoriaEmolumentoMapper.toEntity(categoriaEmolumentoDTO);
        categoriaEmolumento = categoriaEmolumentoRepository.save(categoriaEmolumento);
        return categoriaEmolumentoMapper.toDto(categoriaEmolumento);
    }

    @Override
    public CategoriaEmolumentoDTO update(CategoriaEmolumentoDTO categoriaEmolumentoDTO) {
        log.debug("Request to update CategoriaEmolumento : {}", categoriaEmolumentoDTO);
        CategoriaEmolumento categoriaEmolumento = categoriaEmolumentoMapper.toEntity(categoriaEmolumentoDTO);
        categoriaEmolumento = categoriaEmolumentoRepository.save(categoriaEmolumento);
        return categoriaEmolumentoMapper.toDto(categoriaEmolumento);
    }

    @Override
    public Optional<CategoriaEmolumentoDTO> partialUpdate(CategoriaEmolumentoDTO categoriaEmolumentoDTO) {
        log.debug("Request to partially update CategoriaEmolumento : {}", categoriaEmolumentoDTO);

        return categoriaEmolumentoRepository
            .findById(categoriaEmolumentoDTO.getId())
            .map(existingCategoriaEmolumento -> {
                categoriaEmolumentoMapper.partialUpdate(existingCategoriaEmolumento, categoriaEmolumentoDTO);

                return existingCategoriaEmolumento;
            })
            .map(categoriaEmolumentoRepository::save)
            .map(categoriaEmolumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaEmolumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaEmolumentos");
        return categoriaEmolumentoRepository.findAll(pageable).map(categoriaEmolumentoMapper::toDto);
    }

    public Page<CategoriaEmolumentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return categoriaEmolumentoRepository.findAllWithEagerRelationships(pageable).map(categoriaEmolumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaEmolumentoDTO> findOne(Long id) {
        log.debug("Request to get CategoriaEmolumento : {}", id);
        return categoriaEmolumentoRepository.findOneWithEagerRelationships(id).map(categoriaEmolumentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoriaEmolumento : {}", id);
        categoriaEmolumentoRepository.deleteById(id);
    }
}
