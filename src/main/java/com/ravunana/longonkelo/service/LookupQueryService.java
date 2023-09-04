package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Lookup;
import com.ravunana.longonkelo.repository.LookupRepository;
import com.ravunana.longonkelo.service.criteria.LookupCriteria;
import com.ravunana.longonkelo.service.dto.LookupDTO;
import com.ravunana.longonkelo.service.mapper.LookupMapper;
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
 * Service for executing complex queries for {@link Lookup} entities in the database.
 * The main input is a {@link LookupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LookupDTO} or a {@link Page} of {@link LookupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LookupQueryService extends QueryService<Lookup> {

    private final Logger log = LoggerFactory.getLogger(LookupQueryService.class);

    private final LookupRepository lookupRepository;

    private final LookupMapper lookupMapper;

    public LookupQueryService(LookupRepository lookupRepository, LookupMapper lookupMapper) {
        this.lookupRepository = lookupRepository;
        this.lookupMapper = lookupMapper;
    }

    /**
     * Return a {@link List} of {@link LookupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LookupDTO> findByCriteria(LookupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Lookup> specification = createSpecification(criteria);
        return lookupMapper.toDto(lookupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LookupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LookupDTO> findByCriteria(LookupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Lookup> specification = createSpecification(criteria);
        return lookupRepository.findAll(specification, page).map(lookupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LookupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Lookup> specification = createSpecification(criteria);
        return lookupRepository.count(specification);
    }

    /**
     * Function to convert {@link LookupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Lookup> createSpecification(LookupCriteria criteria) {
        Specification<Lookup> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Lookup_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), Lookup_.codigo));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Lookup_.nome));
            }
            if (criteria.getIsSistema() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSistema(), Lookup_.isSistema));
            }
            if (criteria.getIsModificavel() != null) {
                specification = specification.and(buildSpecification(criteria.getIsModificavel(), Lookup_.isModificavel));
            }
            if (criteria.getLookupItemsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLookupItemsId(),
                            root -> root.join(Lookup_.lookupItems, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
