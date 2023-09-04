package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.InstituicaoEnsino;
import com.ravunana.longonkelo.repository.InstituicaoEnsinoRepository;
import com.ravunana.longonkelo.service.InstituicaoEnsinoService;
import com.ravunana.longonkelo.service.dto.InstituicaoEnsinoDTO;
import com.ravunana.longonkelo.service.mapper.InstituicaoEnsinoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InstituicaoEnsino}.
 */
@Service
@Transactional
public class InstituicaoEnsinoServiceImpl implements InstituicaoEnsinoService {

    private final Logger log = LoggerFactory.getLogger(InstituicaoEnsinoServiceImpl.class);

    private final InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    private final InstituicaoEnsinoMapper instituicaoEnsinoMapper;

    public InstituicaoEnsinoServiceImpl(
        InstituicaoEnsinoRepository instituicaoEnsinoRepository,
        InstituicaoEnsinoMapper instituicaoEnsinoMapper
    ) {
        this.instituicaoEnsinoRepository = instituicaoEnsinoRepository;
        this.instituicaoEnsinoMapper = instituicaoEnsinoMapper;
    }

    @Override
    public InstituicaoEnsinoDTO save(InstituicaoEnsinoDTO instituicaoEnsinoDTO) {
        log.debug("Request to save InstituicaoEnsino : {}", instituicaoEnsinoDTO);
        InstituicaoEnsino instituicaoEnsino = instituicaoEnsinoMapper.toEntity(instituicaoEnsinoDTO);
        instituicaoEnsino = instituicaoEnsinoRepository.save(instituicaoEnsino);
        return instituicaoEnsinoMapper.toDto(instituicaoEnsino);
    }

    @Override
    public InstituicaoEnsinoDTO update(InstituicaoEnsinoDTO instituicaoEnsinoDTO) {
        log.debug("Request to update InstituicaoEnsino : {}", instituicaoEnsinoDTO);
        InstituicaoEnsino instituicaoEnsino = instituicaoEnsinoMapper.toEntity(instituicaoEnsinoDTO);
        instituicaoEnsino = instituicaoEnsinoRepository.save(instituicaoEnsino);
        return instituicaoEnsinoMapper.toDto(instituicaoEnsino);
    }

    @Override
    public Optional<InstituicaoEnsinoDTO> partialUpdate(InstituicaoEnsinoDTO instituicaoEnsinoDTO) {
        log.debug("Request to partially update InstituicaoEnsino : {}", instituicaoEnsinoDTO);

        return instituicaoEnsinoRepository
            .findById(instituicaoEnsinoDTO.getId())
            .map(existingInstituicaoEnsino -> {
                instituicaoEnsinoMapper.partialUpdate(existingInstituicaoEnsino, instituicaoEnsinoDTO);

                return existingInstituicaoEnsino;
            })
            .map(instituicaoEnsinoRepository::save)
            .map(instituicaoEnsinoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InstituicaoEnsinoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InstituicaoEnsinos");
        return instituicaoEnsinoRepository.findAll(pageable).map(instituicaoEnsinoMapper::toDto);
    }

    public Page<InstituicaoEnsinoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return instituicaoEnsinoRepository.findAllWithEagerRelationships(pageable).map(instituicaoEnsinoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InstituicaoEnsinoDTO> findOne(Long id) {
        log.debug("Request to get InstituicaoEnsino : {}", id);
        return instituicaoEnsinoRepository.findOneWithEagerRelationships(id).map(instituicaoEnsinoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InstituicaoEnsino : {}", id);
        instituicaoEnsinoRepository.deleteById(id);
    }
}
