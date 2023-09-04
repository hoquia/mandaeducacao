package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import com.ravunana.longonkelo.repository.DisciplinaCurricularRepository;
import com.ravunana.longonkelo.service.criteria.DisciplinaCurricularCriteria;
import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.mapper.DisciplinaCurricularMapper;
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
 * Service for executing complex queries for {@link DisciplinaCurricular} entities in the database.
 * The main input is a {@link DisciplinaCurricularCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DisciplinaCurricularDTO} or a {@link Page} of {@link DisciplinaCurricularDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DisciplinaCurricularQueryService extends QueryService<DisciplinaCurricular> {

    private final Logger log = LoggerFactory.getLogger(DisciplinaCurricularQueryService.class);

    private final DisciplinaCurricularRepository disciplinaCurricularRepository;

    private final DisciplinaCurricularMapper disciplinaCurricularMapper;

    public DisciplinaCurricularQueryService(
        DisciplinaCurricularRepository disciplinaCurricularRepository,
        DisciplinaCurricularMapper disciplinaCurricularMapper
    ) {
        this.disciplinaCurricularRepository = disciplinaCurricularRepository;
        this.disciplinaCurricularMapper = disciplinaCurricularMapper;
    }

    /**
     * Return a {@link List} of {@link DisciplinaCurricularDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DisciplinaCurricularDTO> findByCriteria(DisciplinaCurricularCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DisciplinaCurricular> specification = createSpecification(criteria);
        return disciplinaCurricularMapper.toDto(disciplinaCurricularRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DisciplinaCurricularDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DisciplinaCurricularDTO> findByCriteria(DisciplinaCurricularCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DisciplinaCurricular> specification = createSpecification(criteria);
        return disciplinaCurricularRepository.findAll(specification, page).map(disciplinaCurricularMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DisciplinaCurricularCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DisciplinaCurricular> specification = createSpecification(criteria);
        return disciplinaCurricularRepository.count(specification);
    }

    /**
     * Function to convert {@link DisciplinaCurricularCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DisciplinaCurricular> createSpecification(DisciplinaCurricularCriteria criteria) {
        Specification<DisciplinaCurricular> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DisciplinaCurricular_.id));
            }
            if (criteria.getUniqueDisciplinaCurricular() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getUniqueDisciplinaCurricular(), DisciplinaCurricular_.uniqueDisciplinaCurricular)
                    );
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), DisciplinaCurricular_.descricao));
            }
            if (criteria.getCargaSemanal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCargaSemanal(), DisciplinaCurricular_.cargaSemanal));
            }
            if (criteria.getIsTerminal() != null) {
                specification = specification.and(buildSpecification(criteria.getIsTerminal(), DisciplinaCurricular_.isTerminal));
            }
            if (criteria.getMediaParaExame() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMediaParaExame(), DisciplinaCurricular_.mediaParaExame));
            }
            if (criteria.getMediaParaRecurso() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMediaParaRecurso(), DisciplinaCurricular_.mediaParaRecurso));
            }
            if (criteria.getMediaParaExameEspecial() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getMediaParaExameEspecial(), DisciplinaCurricular_.mediaParaExameEspecial)
                    );
            }
            if (criteria.getMediaParaDespensar() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMediaParaDespensar(), DisciplinaCurricular_.mediaParaDespensar));
            }
            if (criteria.getDisciplinaCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDisciplinaCurricularId(),
                            root -> root.join(DisciplinaCurricular_.disciplinaCurriculars, JoinType.LEFT).get(DisciplinaCurricular_.id)
                        )
                    );
            }
            if (criteria.getHorarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHorarioId(),
                            root -> root.join(DisciplinaCurricular_.horarios, JoinType.LEFT).get(Horario_.id)
                        )
                    );
            }
            if (criteria.getPlanoAulaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanoAulaId(),
                            root -> root.join(DisciplinaCurricular_.planoAulas, JoinType.LEFT).get(PlanoAula_.id)
                        )
                    );
            }
            if (criteria.getNotasGeralDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotasGeralDisciplinaId(),
                            root -> root.join(DisciplinaCurricular_.notasGeralDisciplinas, JoinType.LEFT).get(NotasGeralDisciplina_.id)
                        )
                    );
            }
            if (criteria.getNotasPeriodicaDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotasPeriodicaDisciplinaId(),
                            root ->
                                root.join(DisciplinaCurricular_.notasPeriodicaDisciplinas, JoinType.LEFT).get(NotasPeriodicaDisciplina_.id)
                        )
                    );
            }
            if (criteria.getComponenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComponenteId(),
                            root -> root.join(DisciplinaCurricular_.componente, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getRegimeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRegimeId(),
                            root -> root.join(DisciplinaCurricular_.regime, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getPlanosCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanosCurricularId(),
                            root -> root.join(DisciplinaCurricular_.planosCurriculars, JoinType.LEFT).get(PlanoCurricular_.id)
                        )
                    );
            }
            if (criteria.getDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDisciplinaId(),
                            root -> root.join(DisciplinaCurricular_.disciplina, JoinType.LEFT).get(Disciplina_.id)
                        )
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenciaId(),
                            root -> root.join(DisciplinaCurricular_.referencia, JoinType.LEFT).get(DisciplinaCurricular_.id)
                        )
                    );
            }
            if (criteria.getEstadosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstadosId(),
                            root -> root.join(DisciplinaCurricular_.estados, JoinType.LEFT).get(EstadoDisciplinaCurricular_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
