package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Classe;
import com.ravunana.longonkelo.repository.ClasseRepository;
import com.ravunana.longonkelo.service.criteria.ClasseCriteria;
import com.ravunana.longonkelo.service.dto.ClasseDTO;
import com.ravunana.longonkelo.service.mapper.ClasseMapper;
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
 * Service for executing complex queries for {@link Classe} entities in the database.
 * The main input is a {@link ClasseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClasseDTO} or a {@link Page} of {@link ClasseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClasseQueryService extends QueryService<Classe> {

    private final Logger log = LoggerFactory.getLogger(ClasseQueryService.class);

    private final ClasseRepository classeRepository;

    private final ClasseMapper classeMapper;

    public ClasseQueryService(ClasseRepository classeRepository, ClasseMapper classeMapper) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
    }

    /**
     * Return a {@link List} of {@link ClasseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClasseDTO> findByCriteria(ClasseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Classe> specification = createSpecification(criteria);
        return classeMapper.toDto(classeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClasseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClasseDTO> findByCriteria(ClasseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Classe> specification = createSpecification(criteria);
        return classeRepository.findAll(specification, page).map(classeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClasseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Classe> specification = createSpecification(criteria);
        return classeRepository.count(specification);
    }

    /**
     * Function to convert {@link ClasseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Classe> createSpecification(ClasseCriteria criteria) {
        Specification<Classe> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Classe_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Classe_.descricao));
            }
            if (criteria.getPlanoCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanoCurricularId(),
                            root -> root.join(Classe_.planoCurriculars, JoinType.LEFT).get(PlanoCurricular_.id)
                        )
                    );
            }
            if (criteria.getPrecoEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrecoEmolumentoId(),
                            root -> root.join(Classe_.precoEmolumentos, JoinType.LEFT).get(PrecoEmolumento_.id)
                        )
                    );
            }
            if (criteria.getNivesEnsinoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNivesEnsinoId(),
                            root -> root.join(Classe_.nivesEnsinos, JoinType.LEFT).get(NivelEnsino_.id)
                        )
                    );
            }
            if (criteria.getPeriodosLancamentoNotaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPeriodosLancamentoNotaId(),
                            root -> root.join(Classe_.periodosLancamentoNotas, JoinType.LEFT).get(PeriodoLancamentoNota_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
