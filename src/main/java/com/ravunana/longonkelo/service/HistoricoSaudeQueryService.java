package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.HistoricoSaude;
import com.ravunana.longonkelo.repository.HistoricoSaudeRepository;
import com.ravunana.longonkelo.service.criteria.HistoricoSaudeCriteria;
import com.ravunana.longonkelo.service.dto.HistoricoSaudeDTO;
import com.ravunana.longonkelo.service.mapper.HistoricoSaudeMapper;
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
 * Service for executing complex queries for {@link HistoricoSaude} entities in the database.
 * The main input is a {@link HistoricoSaudeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HistoricoSaudeDTO} or a {@link Page} of {@link HistoricoSaudeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HistoricoSaudeQueryService extends QueryService<HistoricoSaude> {

    private final Logger log = LoggerFactory.getLogger(HistoricoSaudeQueryService.class);

    private final HistoricoSaudeRepository historicoSaudeRepository;

    private final HistoricoSaudeMapper historicoSaudeMapper;

    public HistoricoSaudeQueryService(HistoricoSaudeRepository historicoSaudeRepository, HistoricoSaudeMapper historicoSaudeMapper) {
        this.historicoSaudeRepository = historicoSaudeRepository;
        this.historicoSaudeMapper = historicoSaudeMapper;
    }

    /**
     * Return a {@link List} of {@link HistoricoSaudeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HistoricoSaudeDTO> findByCriteria(HistoricoSaudeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HistoricoSaude> specification = createSpecification(criteria);
        return historicoSaudeMapper.toDto(historicoSaudeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HistoricoSaudeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HistoricoSaudeDTO> findByCriteria(HistoricoSaudeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HistoricoSaude> specification = createSpecification(criteria);
        return historicoSaudeRepository.findAll(specification, page).map(historicoSaudeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HistoricoSaudeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HistoricoSaude> specification = createSpecification(criteria);
        return historicoSaudeRepository.count(specification);
    }

    /**
     * Function to convert {@link HistoricoSaudeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HistoricoSaude> createSpecification(HistoricoSaudeCriteria criteria) {
        Specification<HistoricoSaude> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), HistoricoSaude_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), HistoricoSaude_.nome));
            }
            if (criteria.getInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInicio(), HistoricoSaude_.inicio));
            }
            if (criteria.getFim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFim(), HistoricoSaude_.fim));
            }
            if (criteria.getSituacaoPrescricao() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSituacaoPrescricao(), HistoricoSaude_.situacaoPrescricao));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), HistoricoSaude_.timestamp));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), HistoricoSaude_.hash));
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(HistoricoSaude_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getDiscenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDiscenteId(),
                            root -> root.join(HistoricoSaude_.discente, JoinType.LEFT).get(Discente_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
