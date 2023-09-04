package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.DetalhePlanoAula;
import com.ravunana.longonkelo.repository.DetalhePlanoAulaRepository;
import com.ravunana.longonkelo.service.criteria.DetalhePlanoAulaCriteria;
import com.ravunana.longonkelo.service.dto.DetalhePlanoAulaDTO;
import com.ravunana.longonkelo.service.mapper.DetalhePlanoAulaMapper;
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
 * Service for executing complex queries for {@link DetalhePlanoAula} entities in the database.
 * The main input is a {@link DetalhePlanoAulaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DetalhePlanoAulaDTO} or a {@link Page} of {@link DetalhePlanoAulaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DetalhePlanoAulaQueryService extends QueryService<DetalhePlanoAula> {

    private final Logger log = LoggerFactory.getLogger(DetalhePlanoAulaQueryService.class);

    private final DetalhePlanoAulaRepository detalhePlanoAulaRepository;

    private final DetalhePlanoAulaMapper detalhePlanoAulaMapper;

    public DetalhePlanoAulaQueryService(
        DetalhePlanoAulaRepository detalhePlanoAulaRepository,
        DetalhePlanoAulaMapper detalhePlanoAulaMapper
    ) {
        this.detalhePlanoAulaRepository = detalhePlanoAulaRepository;
        this.detalhePlanoAulaMapper = detalhePlanoAulaMapper;
    }

    /**
     * Return a {@link List} of {@link DetalhePlanoAulaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DetalhePlanoAulaDTO> findByCriteria(DetalhePlanoAulaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DetalhePlanoAula> specification = createSpecification(criteria);
        return detalhePlanoAulaMapper.toDto(detalhePlanoAulaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DetalhePlanoAulaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalhePlanoAulaDTO> findByCriteria(DetalhePlanoAulaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DetalhePlanoAula> specification = createSpecification(criteria);
        return detalhePlanoAulaRepository.findAll(specification, page).map(detalhePlanoAulaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DetalhePlanoAulaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DetalhePlanoAula> specification = createSpecification(criteria);
        return detalhePlanoAulaRepository.count(specification);
    }

    /**
     * Function to convert {@link DetalhePlanoAulaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DetalhePlanoAula> createSpecification(DetalhePlanoAulaCriteria criteria) {
        Specification<DetalhePlanoAula> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DetalhePlanoAula_.id));
            }
            if (criteria.getTempoActividade() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTempoActividade(), DetalhePlanoAula_.tempoActividade));
            }
            if (criteria.getTituloActividade() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTituloActividade(), DetalhePlanoAula_.tituloActividade));
            }
            if (criteria.getPlanoAulaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanoAulaId(),
                            root -> root.join(DetalhePlanoAula_.planoAula, JoinType.LEFT).get(PlanoAula_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
