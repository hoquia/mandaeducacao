package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.repository.EstadoDisciplinaCurricularRepository;
import com.ravunana.longonkelo.service.criteria.EstadoDisciplinaCurricularCriteria;
import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.mapper.EstadoDisciplinaCurricularMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EstadoDisciplinaCurricular} entities in the database.
 * The main input is a {@link EstadoDisciplinaCurricularCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstadoDisciplinaCurricularDTO} or a {@link Page} of {@link EstadoDisciplinaCurricularDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstadoDisciplinaCurricularQueryService extends QueryService<EstadoDisciplinaCurricular> {

    private final Logger log = LoggerFactory.getLogger(EstadoDisciplinaCurricularQueryService.class);

    private final EstadoDisciplinaCurricularRepository estadoDisciplinaCurricularRepository;

    private final EstadoDisciplinaCurricularMapper estadoDisciplinaCurricularMapper;

    public EstadoDisciplinaCurricularQueryService(
        EstadoDisciplinaCurricularRepository estadoDisciplinaCurricularRepository,
        EstadoDisciplinaCurricularMapper estadoDisciplinaCurricularMapper
    ) {
        this.estadoDisciplinaCurricularRepository = estadoDisciplinaCurricularRepository;
        this.estadoDisciplinaCurricularMapper = estadoDisciplinaCurricularMapper;
    }

    /**
     * Return a {@link List} of {@link EstadoDisciplinaCurricularDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstadoDisciplinaCurricularDTO> findByCriteria(EstadoDisciplinaCurricularCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstadoDisciplinaCurricular> specification = createSpecification(criteria);
        return estadoDisciplinaCurricularMapper.toDto(estadoDisciplinaCurricularRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstadoDisciplinaCurricularDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstadoDisciplinaCurricularDTO> findByCriteria(EstadoDisciplinaCurricularCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstadoDisciplinaCurricular> specification = createSpecification(criteria);
        return estadoDisciplinaCurricularRepository.findAll(specification, page).map(estadoDisciplinaCurricularMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstadoDisciplinaCurricularCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstadoDisciplinaCurricular> specification = createSpecification(criteria);
        return estadoDisciplinaCurricularRepository.count(specification);
    }

    /**
     * Function to convert {@link EstadoDisciplinaCurricularCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EstadoDisciplinaCurricular> createSpecification(EstadoDisciplinaCurricularCriteria criteria) {
        Specification<EstadoDisciplinaCurricular> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EstadoDisciplinaCurricular_.id));
            }
            if (criteria.getUniqueSituacaoDisciplina() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getUniqueSituacaoDisciplina(),
                            EstadoDisciplinaCurricular_.uniqueSituacaoDisciplina
                        )
                    );
            }
            if (criteria.getClassificacao() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getClassificacao(), EstadoDisciplinaCurricular_.classificacao));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), EstadoDisciplinaCurricular_.codigo));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), EstadoDisciplinaCurricular_.descricao));
            }
            if (criteria.getCor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCor(), EstadoDisciplinaCurricular_.cor));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), EstadoDisciplinaCurricular_.valor));
            }
            if (criteria.getEstadoDisciplinaCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstadoDisciplinaCurricularId(),
                            root ->
                                root
                                    .join(EstadoDisciplinaCurricular_.estadoDisciplinaCurriculars, JoinType.LEFT)
                                    .get(EstadoDisciplinaCurricular_.id)
                        )
                    );
            }
            if (criteria.getNotasPeriodicaDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotasPeriodicaDisciplinaId(),
                            root ->
                                root
                                    .join(EstadoDisciplinaCurricular_.notasPeriodicaDisciplinas, JoinType.LEFT)
                                    .get(NotasPeriodicaDisciplina_.id)
                        )
                    );
            }
            if (criteria.getNotasGeralDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotasGeralDisciplinaId(),
                            root ->
                                root.join(EstadoDisciplinaCurricular_.notasGeralDisciplinas, JoinType.LEFT).get(NotasGeralDisciplina_.id)
                        )
                    );
            }
            if (criteria.getResumoAcademicoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResumoAcademicoId(),
                            root -> root.join(EstadoDisciplinaCurricular_.resumoAcademicos, JoinType.LEFT).get(ResumoAcademico_.id)
                        )
                    );
            }
            if (criteria.getDisciplinasCurricularsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDisciplinasCurricularsId(),
                            root ->
                                root.join(EstadoDisciplinaCurricular_.disciplinasCurriculars, JoinType.LEFT).get(DisciplinaCurricular_.id)
                        )
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenciaId(),
                            root -> root.join(EstadoDisciplinaCurricular_.referencia, JoinType.LEFT).get(EstadoDisciplinaCurricular_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
