package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.config.LongonkeloException;
import com.ravunana.longonkelo.domain.PlanoCurricular;
import com.ravunana.longonkelo.repository.PlanoCurricularRepository;
import com.ravunana.longonkelo.service.PlanoCurricularService;
import com.ravunana.longonkelo.service.dto.PlanoCurricularDTO;
import com.ravunana.longonkelo.service.mapper.PlanoCurricularMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlanoCurricular}.
 */
@Service
@Transactional
public class PlanoCurricularServiceImpl implements PlanoCurricularService {

    private final Logger log = LoggerFactory.getLogger(PlanoCurricularServiceImpl.class);

    private final PlanoCurricularRepository planoCurricularRepository;

    private final PlanoCurricularMapper planoCurricularMapper;

    public PlanoCurricularServiceImpl(PlanoCurricularRepository planoCurricularRepository, PlanoCurricularMapper planoCurricularMapper) {
        this.planoCurricularRepository = planoCurricularRepository;
        this.planoCurricularMapper = planoCurricularMapper;
    }

    @Override
    public PlanoCurricularDTO save(PlanoCurricularDTO planoCurricularDTO) {
        log.debug("Request to save PlanoCurricular : {}", planoCurricularDTO);

        planoCurricularDTO.setDescricao(
            getDescricaoPlanoCurricular(planoCurricularDTO.getCurso().getNome(), planoCurricularDTO.getClasse().getDescricao())
        );

        var result = planoCurricularRepository
            .findAll()
            .stream()
            .filter(x -> x.getDescricao().equals(planoCurricularDTO.getDescricao()))
            .findFirst();

        if (result.isPresent()) {
            if (result.get().getDescricao().equals(planoCurricularDTO.getDescricao())) {
                throw new LongonkeloException("O Plano curricular que pretende registrar já existe, altera a classe ou  curso");
            }
        }

        PlanoCurricular planoCurricular = planoCurricularMapper.toEntity(planoCurricularDTO);
        planoCurricular = planoCurricularRepository.save(planoCurricular);
        return planoCurricularMapper.toDto(planoCurricular);
    }

    @Override
    public PlanoCurricularDTO update(PlanoCurricularDTO planoCurricularDTO) {
        log.debug("Request to update PlanoCurricular : {}", planoCurricularDTO);
        PlanoCurricular planoCurricular = planoCurricularMapper.toEntity(planoCurricularDTO);
        planoCurricular = planoCurricularRepository.save(planoCurricular);
        return planoCurricularMapper.toDto(planoCurricular);
    }

    @Override
    public Optional<PlanoCurricularDTO> partialUpdate(PlanoCurricularDTO planoCurricularDTO) {
        log.debug("Request to partially update PlanoCurricular : {}", planoCurricularDTO);

        return planoCurricularRepository
            .findById(planoCurricularDTO.getId())
            .map(existingPlanoCurricular -> {
                planoCurricularMapper.partialUpdate(existingPlanoCurricular, planoCurricularDTO);

                return existingPlanoCurricular;
            })
            .map(planoCurricularRepository::save)
            .map(planoCurricularMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanoCurricularDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlanoCurriculars");
        return planoCurricularRepository.findAll(pageable).map(planoCurricularMapper::toDto);
    }

    public Page<PlanoCurricularDTO> findAllWithEagerRelationships(Pageable pageable) {
        return planoCurricularRepository.findAllWithEagerRelationships(pageable).map(planoCurricularMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanoCurricularDTO> findOne(Long id) {
        log.debug("Request to get PlanoCurricular : {}", id);
        return planoCurricularRepository.findOneWithEagerRelationships(id).map(planoCurricularMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanoCurricular : {}", id);
        planoCurricularRepository.deleteById(id);
    }

    @Override
    public String getDescricaoPlanoCurricular(String curso, String classe) {
        return curso + " " + classe;
    }
}
