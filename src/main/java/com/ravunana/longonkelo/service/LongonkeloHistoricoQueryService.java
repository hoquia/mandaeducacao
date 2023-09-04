package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.LongonkeloHistorico;
import com.ravunana.longonkelo.repository.LongonkeloHistoricoRepository;
import com.ravunana.longonkelo.service.criteria.LongonkeloHistoricoCriteria;
import com.ravunana.longonkelo.service.dto.LongonkeloHistoricoDTO;
import com.ravunana.longonkelo.service.mapper.LongonkeloHistoricoMapper;
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
 * Service for executing complex queries for {@link LongonkeloHistorico} entities in the database.
 * The main input is a {@link LongonkeloHistoricoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LongonkeloHistoricoDTO} or a {@link Page} of {@link LongonkeloHistoricoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LongonkeloHistoricoQueryService extends QueryService<LongonkeloHistorico> {

    private final Logger log = LoggerFactory.getLogger(LongonkeloHistoricoQueryService.class);

    private final LongonkeloHistoricoRepository longonkeloHistoricoRepository;

    private final LongonkeloHistoricoMapper longonkeloHistoricoMapper;

    public LongonkeloHistoricoQueryService(
        LongonkeloHistoricoRepository longonkeloHistoricoRepository,
        LongonkeloHistoricoMapper longonkeloHistoricoMapper
    ) {
        this.longonkeloHistoricoRepository = longonkeloHistoricoRepository;
        this.longonkeloHistoricoMapper = longonkeloHistoricoMapper;
    }

    /**
     * Return a {@link List} of {@link LongonkeloHistoricoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LongonkeloHistoricoDTO> findByCriteria(LongonkeloHistoricoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LongonkeloHistorico> specification = createSpecification(criteria);
        return longonkeloHistoricoMapper.toDto(longonkeloHistoricoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LongonkeloHistoricoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LongonkeloHistoricoDTO> findByCriteria(LongonkeloHistoricoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LongonkeloHistorico> specification = createSpecification(criteria);
        return longonkeloHistoricoRepository.findAll(specification, page).map(longonkeloHistoricoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LongonkeloHistoricoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LongonkeloHistorico> specification = createSpecification(criteria);
        return longonkeloHistoricoRepository.count(specification);
    }

    /**
     * Function to convert {@link LongonkeloHistoricoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LongonkeloHistorico> createSpecification(LongonkeloHistoricoCriteria criteria) {
        Specification<LongonkeloHistorico> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LongonkeloHistorico_.id));
            }
            if (criteria.getOperacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOperacao(), LongonkeloHistorico_.operacao));
            }
            if (criteria.getEntidadeNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEntidadeNome(), LongonkeloHistorico_.entidadeNome));
            }
            if (criteria.getEntidadeCodigo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEntidadeCodigo(), LongonkeloHistorico_.entidadeCodigo));
            }
            if (criteria.getHost() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHost(), LongonkeloHistorico_.host));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), LongonkeloHistorico_.hash));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), LongonkeloHistorico_.timestamp));
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(LongonkeloHistorico_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
