package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import com.ravunana.longonkelo.repository.CategoriaOcorrenciaRepository;
import com.ravunana.longonkelo.service.CategoriaOcorrenciaService;
import com.ravunana.longonkelo.service.dto.CategoriaOcorrenciaDTO;
import com.ravunana.longonkelo.service.mapper.CategoriaOcorrenciaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategoriaOcorrencia}.
 */
@Service
@Transactional
public class CategoriaOcorrenciaServiceImpl implements CategoriaOcorrenciaService {

    private final Logger log = LoggerFactory.getLogger(CategoriaOcorrenciaServiceImpl.class);

    private final CategoriaOcorrenciaRepository categoriaOcorrenciaRepository;

    private final CategoriaOcorrenciaMapper categoriaOcorrenciaMapper;

    public CategoriaOcorrenciaServiceImpl(
        CategoriaOcorrenciaRepository categoriaOcorrenciaRepository,
        CategoriaOcorrenciaMapper categoriaOcorrenciaMapper
    ) {
        this.categoriaOcorrenciaRepository = categoriaOcorrenciaRepository;
        this.categoriaOcorrenciaMapper = categoriaOcorrenciaMapper;
    }

    @Override
    public CategoriaOcorrenciaDTO save(CategoriaOcorrenciaDTO categoriaOcorrenciaDTO) {
        log.debug("Request to save CategoriaOcorrencia : {}", categoriaOcorrenciaDTO);
        CategoriaOcorrencia categoriaOcorrencia = categoriaOcorrenciaMapper.toEntity(categoriaOcorrenciaDTO);
        categoriaOcorrencia = categoriaOcorrenciaRepository.save(categoriaOcorrencia);
        return categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);
    }

    @Override
    public CategoriaOcorrenciaDTO update(CategoriaOcorrenciaDTO categoriaOcorrenciaDTO) {
        log.debug("Request to update CategoriaOcorrencia : {}", categoriaOcorrenciaDTO);
        CategoriaOcorrencia categoriaOcorrencia = categoriaOcorrenciaMapper.toEntity(categoriaOcorrenciaDTO);
        categoriaOcorrencia = categoriaOcorrenciaRepository.save(categoriaOcorrencia);
        return categoriaOcorrenciaMapper.toDto(categoriaOcorrencia);
    }

    @Override
    public Optional<CategoriaOcorrenciaDTO> partialUpdate(CategoriaOcorrenciaDTO categoriaOcorrenciaDTO) {
        log.debug("Request to partially update CategoriaOcorrencia : {}", categoriaOcorrenciaDTO);

        return categoriaOcorrenciaRepository
            .findById(categoriaOcorrenciaDTO.getId())
            .map(existingCategoriaOcorrencia -> {
                categoriaOcorrenciaMapper.partialUpdate(existingCategoriaOcorrencia, categoriaOcorrenciaDTO);

                return existingCategoriaOcorrencia;
            })
            .map(categoriaOcorrenciaRepository::save)
            .map(categoriaOcorrenciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaOcorrenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaOcorrencias");
        return categoriaOcorrenciaRepository.findAll(pageable).map(categoriaOcorrenciaMapper::toDto);
    }

    public Page<CategoriaOcorrenciaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return categoriaOcorrenciaRepository.findAllWithEagerRelationships(pageable).map(categoriaOcorrenciaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaOcorrenciaDTO> findOne(Long id) {
        log.debug("Request to get CategoriaOcorrencia : {}", id);
        return categoriaOcorrenciaRepository.findOneWithEagerRelationships(id).map(categoriaOcorrenciaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoriaOcorrencia : {}", id);
        categoriaOcorrenciaRepository.deleteById(id);
    }
}
