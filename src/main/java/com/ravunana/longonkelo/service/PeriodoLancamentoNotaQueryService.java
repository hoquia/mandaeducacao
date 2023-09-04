package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.PeriodoLancamentoNota;
import com.ravunana.longonkelo.repository.PeriodoLancamentoNotaRepository;
import com.ravunana.longonkelo.service.criteria.PeriodoLancamentoNotaCriteria;
import com.ravunana.longonkelo.service.dto.PeriodoLancamentoNotaDTO;
import com.ravunana.longonkelo.service.mapper.PeriodoLancamentoNotaMapper;
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
 * Service for executing complex queries for {@link PeriodoLancamentoNota} entities in the database.
 * The main input is a {@link PeriodoLancamentoNotaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PeriodoLancamentoNotaDTO} or a {@link Page} of {@link PeriodoLancamentoNotaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PeriodoLancamentoNotaQueryService extends QueryService<PeriodoLancamentoNota> {

    private final Logger log = LoggerFactory.getLogger(PeriodoLancamentoNotaQueryService.class);

    private final PeriodoLancamentoNotaRepository periodoLancamentoNotaRepository;

    private final PeriodoLancamentoNotaMapper periodoLancamentoNotaMapper;

    public PeriodoLancamentoNotaQueryService(
        PeriodoLancamentoNotaRepository periodoLancamentoNotaRepository,
        PeriodoLancamentoNotaMapper periodoLancamentoNotaMapper
    ) {
        this.periodoLancamentoNotaRepository = periodoLancamentoNotaRepository;
        this.periodoLancamentoNotaMapper = periodoLancamentoNotaMapper;
    }

    /**
     * Return a {@link List} of {@link PeriodoLancamentoNotaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PeriodoLancamentoNotaDTO> findByCriteria(PeriodoLancamentoNotaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PeriodoLancamentoNota> specification = createSpecification(criteria);
        return periodoLancamentoNotaMapper.toDto(periodoLancamentoNotaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PeriodoLancamentoNotaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PeriodoLancamentoNotaDTO> findByCriteria(PeriodoLancamentoNotaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PeriodoLancamentoNota> specification = createSpecification(criteria);
        return periodoLancamentoNotaRepository.findAll(specification, page).map(periodoLancamentoNotaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PeriodoLancamentoNotaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PeriodoLancamentoNota> specification = createSpecification(criteria);
        return periodoLancamentoNotaRepository.count(specification);
    }

    /**
     * Function to convert {@link PeriodoLancamentoNotaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PeriodoLancamentoNota> createSpecification(PeriodoLancamentoNotaCriteria criteria) {
        Specification<PeriodoLancamentoNota> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PeriodoLancamentoNota_.id));
            }
            if (criteria.getTipoAvaliacao() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoAvaliacao(), PeriodoLancamentoNota_.tipoAvaliacao));
            }
            if (criteria.getDe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDe(), PeriodoLancamentoNota_.de));
            }
            if (criteria.getAte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAte(), PeriodoLancamentoNota_.ate));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), PeriodoLancamentoNota_.timestamp));
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(PeriodoLancamentoNota_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getClasseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClasseId(),
                            root -> root.join(PeriodoLancamentoNota_.classes, JoinType.LEFT).get(Classe_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
