package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.ResponsavelTurma;
import com.ravunana.longonkelo.repository.ResponsavelTurmaRepository;
import com.ravunana.longonkelo.service.criteria.ResponsavelTurmaCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelTurmaDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelTurmaMapper;
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
 * Service for executing complex queries for {@link ResponsavelTurma} entities in the database.
 * The main input is a {@link ResponsavelTurmaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResponsavelTurmaDTO} or a {@link Page} of {@link ResponsavelTurmaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResponsavelTurmaQueryService extends QueryService<ResponsavelTurma> {

    private final Logger log = LoggerFactory.getLogger(ResponsavelTurmaQueryService.class);

    private final ResponsavelTurmaRepository responsavelTurmaRepository;

    private final ResponsavelTurmaMapper responsavelTurmaMapper;

    public ResponsavelTurmaQueryService(
        ResponsavelTurmaRepository responsavelTurmaRepository,
        ResponsavelTurmaMapper responsavelTurmaMapper
    ) {
        this.responsavelTurmaRepository = responsavelTurmaRepository;
        this.responsavelTurmaMapper = responsavelTurmaMapper;
    }

    /**
     * Return a {@link List} of {@link ResponsavelTurmaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResponsavelTurmaDTO> findByCriteria(ResponsavelTurmaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResponsavelTurma> specification = createSpecification(criteria);
        return responsavelTurmaMapper.toDto(responsavelTurmaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResponsavelTurmaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelTurmaDTO> findByCriteria(ResponsavelTurmaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResponsavelTurma> specification = createSpecification(criteria);
        return responsavelTurmaRepository.findAll(specification, page).map(responsavelTurmaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResponsavelTurmaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResponsavelTurma> specification = createSpecification(criteria);
        return responsavelTurmaRepository.count(specification);
    }

    /**
     * Function to convert {@link ResponsavelTurmaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResponsavelTurma> createSpecification(ResponsavelTurmaCriteria criteria) {
        Specification<ResponsavelTurma> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResponsavelTurma_.id));
            }
            if (criteria.getDe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDe(), ResponsavelTurma_.de));
            }
            if (criteria.getAte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAte(), ResponsavelTurma_.ate));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), ResponsavelTurma_.timestamp));
            }
            if (criteria.getResponsavelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelId(),
                            root -> root.join(ResponsavelTurma_.responsavels, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(ResponsavelTurma_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(ResponsavelTurma_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurmaId(), root -> root.join(ResponsavelTurma_.turma, JoinType.LEFT).get(Turma_.id))
                    );
            }
        }
        return specification;
    }
}
