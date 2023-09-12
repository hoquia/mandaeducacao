package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.repository.DisciplinaCurricularRepository;
import com.ravunana.longonkelo.service.DisciplinaCurricularService;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.mapper.DisciplinaCurricularMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DisciplinaCurricular}.
 */
@Service
@Transactional
public class DisciplinaCurricularServiceImpl implements DisciplinaCurricularService {

    private final Logger log = LoggerFactory.getLogger(DisciplinaCurricularServiceImpl.class);

    private final DisciplinaCurricularRepository disciplinaCurricularRepository;

    private final DisciplinaCurricularMapper disciplinaCurricularMapper;

    public DisciplinaCurricularServiceImpl(
        DisciplinaCurricularRepository disciplinaCurricularRepository,
        DisciplinaCurricularMapper disciplinaCurricularMapper
    ) {
        this.disciplinaCurricularRepository = disciplinaCurricularRepository;
        this.disciplinaCurricularMapper = disciplinaCurricularMapper;
    }

    @Override
    public DisciplinaCurricularDTO save(DisciplinaCurricularDTO disciplinaCurricularDTO) {
        log.debug("Request to save DisciplinaCurricular : {}", disciplinaCurricularDTO);

        var planoCurricular = disciplinaCurricularDTO.getPlanosCurriculars();

        for (var planoCurricul : planoCurricular) {
            var descricao = disciplinaCurricularDTO.getDisciplina().getNome() + ", " + planoCurricul.getDescricao();
            disciplinaCurricularDTO.setDescricao(descricao);
            disciplinaCurricularDTO.setUniqueDisciplinaCurricular(descricao);
        }

        DisciplinaCurricular disciplinaCurricular = disciplinaCurricularMapper.toEntity(disciplinaCurricularDTO);
        disciplinaCurricular = disciplinaCurricularRepository.save(disciplinaCurricular);
        return disciplinaCurricularMapper.toDto(disciplinaCurricular);
    }

    @Override
    public DisciplinaCurricularDTO update(DisciplinaCurricularDTO disciplinaCurricularDTO) {
        log.debug("Request to update DisciplinaCurricular : {}", disciplinaCurricularDTO);
        DisciplinaCurricular disciplinaCurricular = disciplinaCurricularMapper.toEntity(disciplinaCurricularDTO);
        disciplinaCurricular = disciplinaCurricularRepository.save(disciplinaCurricular);
        return disciplinaCurricularMapper.toDto(disciplinaCurricular);
    }

    @Override
    public Optional<DisciplinaCurricularDTO> partialUpdate(DisciplinaCurricularDTO disciplinaCurricularDTO) {
        log.debug("Request to partially update DisciplinaCurricular : {}", disciplinaCurricularDTO);

        return disciplinaCurricularRepository
            .findById(disciplinaCurricularDTO.getId())
            .map(existingDisciplinaCurricular -> {
                disciplinaCurricularMapper.partialUpdate(existingDisciplinaCurricular, disciplinaCurricularDTO);

                return existingDisciplinaCurricular;
            })
            .map(disciplinaCurricularRepository::save)
            .map(disciplinaCurricularMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisciplinaCurricularDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DisciplinaCurriculars");
        return disciplinaCurricularRepository.findAll(pageable).map(disciplinaCurricularMapper::toDto);
    }

    public Page<DisciplinaCurricularDTO> findAllWithEagerRelationships(Pageable pageable) {
        return disciplinaCurricularRepository.findAllWithEagerRelationships(pageable).map(disciplinaCurricularMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DisciplinaCurricularDTO> findOne(Long id) {
        log.debug("Request to get DisciplinaCurricular : {}", id);
        return disciplinaCurricularRepository.findOneWithEagerRelationships(id).map(disciplinaCurricularMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DisciplinaCurricular : {}", id);
        disciplinaCurricularRepository.deleteById(id);
    }
}
