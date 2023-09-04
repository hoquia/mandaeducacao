package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.PlanoCurricular;
import com.ravunana.longonkelo.repository.PlanoCurricularRepository;
import com.ravunana.longonkelo.service.criteria.PlanoCurricularCriteria;
import com.ravunana.longonkelo.service.dto.PlanoCurricularDTO;
import com.ravunana.longonkelo.service.mapper.PlanoCurricularMapper;
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
 * Service for executing complex queries for {@link PlanoCurricular} entities in the database.
 * The main input is a {@link PlanoCurricularCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoCurricularDTO} or a {@link Page} of {@link PlanoCurricularDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoCurricularQueryService extends QueryService<PlanoCurricular> {

    private final Logger log = LoggerFactory.getLogger(PlanoCurricularQueryService.class);

    private final PlanoCurricularRepository planoCurricularRepository;

    private final PlanoCurricularMapper planoCurricularMapper;

    public PlanoCurricularQueryService(PlanoCurricularRepository planoCurricularRepository, PlanoCurricularMapper planoCurricularMapper) {
        this.planoCurricularRepository = planoCurricularRepository;
        this.planoCurricularMapper = planoCurricularMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoCurricularDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoCurricularDTO> findByCriteria(PlanoCurricularCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoCurricular> specification = createSpecification(criteria);
        return planoCurricularMapper.toDto(planoCurricularRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoCurricularDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoCurricularDTO> findByCriteria(PlanoCurricularCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoCurricular> specification = createSpecification(criteria);
        return planoCurricularRepository.findAll(specification, page).map(planoCurricularMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoCurricularCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoCurricular> specification = createSpecification(criteria);
        return planoCurricularRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoCurricularCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoCurricular> createSpecification(PlanoCurricularCriteria criteria) {
        Specification<PlanoCurricular> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoCurricular_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PlanoCurricular_.descricao));
            }
            if (criteria.getFormulaClassificacaoFinal() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getFormulaClassificacaoFinal(), PlanoCurricular_.formulaClassificacaoFinal)
                    );
            }
            if (criteria.getNumeroDisciplinaAprova() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getNumeroDisciplinaAprova(), PlanoCurricular_.numeroDisciplinaAprova)
                    );
            }
            if (criteria.getNumeroDisciplinaReprova() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getNumeroDisciplinaReprova(), PlanoCurricular_.numeroDisciplinaReprova)
                    );
            }
            if (criteria.getNumeroDisciplinaRecurso() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getNumeroDisciplinaRecurso(), PlanoCurricular_.numeroDisciplinaRecurso)
                    );
            }
            if (criteria.getNumeroDisciplinaExame() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumeroDisciplinaExame(), PlanoCurricular_.numeroDisciplinaExame));
            }
            if (criteria.getNumeroDisciplinaExameEspecial() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getNumeroDisciplinaExameEspecial(), PlanoCurricular_.numeroDisciplinaExameEspecial)
                    );
            }
            if (criteria.getNumeroFaltaReprova() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumeroFaltaReprova(), PlanoCurricular_.numeroFaltaReprova));
            }
            if (criteria.getPesoMedia1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPesoMedia1(), PlanoCurricular_.pesoMedia1));
            }
            if (criteria.getPesoMedia2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPesoMedia2(), PlanoCurricular_.pesoMedia2));
            }
            if (criteria.getPesoMedia3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPesoMedia3(), PlanoCurricular_.pesoMedia3));
            }
            if (criteria.getPesoRecurso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPesoRecurso(), PlanoCurricular_.pesoRecurso));
            }
            if (criteria.getPesoExame() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPesoExame(), PlanoCurricular_.pesoExame));
            }
            if (criteria.getPesoExameEspecial() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPesoExameEspecial(), PlanoCurricular_.pesoExameEspecial));
            }
            if (criteria.getPesoNotaCoselho() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPesoNotaCoselho(), PlanoCurricular_.pesoNotaCoselho));
            }
            if (criteria.getSiglaProva1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiglaProva1(), PlanoCurricular_.siglaProva1));
            }
            if (criteria.getSiglaProva2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiglaProva2(), PlanoCurricular_.siglaProva2));
            }
            if (criteria.getSiglaProva3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiglaProva3(), PlanoCurricular_.siglaProva3));
            }
            if (criteria.getSiglaMedia1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiglaMedia1(), PlanoCurricular_.siglaMedia1));
            }
            if (criteria.getSiglaMedia2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiglaMedia2(), PlanoCurricular_.siglaMedia2));
            }
            if (criteria.getSiglaMedia3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiglaMedia3(), PlanoCurricular_.siglaMedia3));
            }
            if (criteria.getFormulaMedia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormulaMedia(), PlanoCurricular_.formulaMedia));
            }
            if (criteria.getFormulaDispensa() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFormulaDispensa(), PlanoCurricular_.formulaDispensa));
            }
            if (criteria.getFormulaExame() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormulaExame(), PlanoCurricular_.formulaExame));
            }
            if (criteria.getFormulaRecurso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormulaRecurso(), PlanoCurricular_.formulaRecurso));
            }
            if (criteria.getFormulaExameEspecial() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFormulaExameEspecial(), PlanoCurricular_.formulaExameEspecial));
            }
            if (criteria.getTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurmaId(), root -> root.join(PlanoCurricular_.turmas, JoinType.LEFT).get(Turma_.id))
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(PlanoCurricular_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getClasseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClasseId(),
                            root -> root.join(PlanoCurricular_.classe, JoinType.LEFT).get(Classe_.id)
                        )
                    );
            }
            if (criteria.getCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCursoId(), root -> root.join(PlanoCurricular_.curso, JoinType.LEFT).get(Curso_.id))
                    );
            }
            if (criteria.getDisciplinasCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDisciplinasCurricularId(),
                            root -> root.join(PlanoCurricular_.disciplinasCurriculars, JoinType.LEFT).get(DisciplinaCurricular_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
