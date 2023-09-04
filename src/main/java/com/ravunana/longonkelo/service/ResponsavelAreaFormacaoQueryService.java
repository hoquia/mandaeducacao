package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.ResponsavelAreaFormacao;
import com.ravunana.longonkelo.repository.ResponsavelAreaFormacaoRepository;
import com.ravunana.longonkelo.service.criteria.ResponsavelAreaFormacaoCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelAreaFormacaoDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelAreaFormacaoMapper;
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
 * Service for executing complex queries for {@link ResponsavelAreaFormacao} entities in the database.
 * The main input is a {@link ResponsavelAreaFormacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResponsavelAreaFormacaoDTO} or a {@link Page} of {@link ResponsavelAreaFormacaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResponsavelAreaFormacaoQueryService extends QueryService<ResponsavelAreaFormacao> {

    private final Logger log = LoggerFactory.getLogger(ResponsavelAreaFormacaoQueryService.class);

    private final ResponsavelAreaFormacaoRepository responsavelAreaFormacaoRepository;

    private final ResponsavelAreaFormacaoMapper responsavelAreaFormacaoMapper;

    public ResponsavelAreaFormacaoQueryService(
        ResponsavelAreaFormacaoRepository responsavelAreaFormacaoRepository,
        ResponsavelAreaFormacaoMapper responsavelAreaFormacaoMapper
    ) {
        this.responsavelAreaFormacaoRepository = responsavelAreaFormacaoRepository;
        this.responsavelAreaFormacaoMapper = responsavelAreaFormacaoMapper;
    }

    /**
     * Return a {@link List} of {@link ResponsavelAreaFormacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResponsavelAreaFormacaoDTO> findByCriteria(ResponsavelAreaFormacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResponsavelAreaFormacao> specification = createSpecification(criteria);
        return responsavelAreaFormacaoMapper.toDto(responsavelAreaFormacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResponsavelAreaFormacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelAreaFormacaoDTO> findByCriteria(ResponsavelAreaFormacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResponsavelAreaFormacao> specification = createSpecification(criteria);
        return responsavelAreaFormacaoRepository.findAll(specification, page).map(responsavelAreaFormacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResponsavelAreaFormacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResponsavelAreaFormacao> specification = createSpecification(criteria);
        return responsavelAreaFormacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link ResponsavelAreaFormacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResponsavelAreaFormacao> createSpecification(ResponsavelAreaFormacaoCriteria criteria) {
        Specification<ResponsavelAreaFormacao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResponsavelAreaFormacao_.id));
            }
            if (criteria.getDe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDe(), ResponsavelAreaFormacao_.de));
            }
            if (criteria.getAte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAte(), ResponsavelAreaFormacao_.ate));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), ResponsavelAreaFormacao_.timestamp));
            }
            if (criteria.getResponsavelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelId(),
                            root -> root.join(ResponsavelAreaFormacao_.responsavels, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(ResponsavelAreaFormacao_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(ResponsavelAreaFormacao_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getAreaFormacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAreaFormacaoId(),
                            root -> root.join(ResponsavelAreaFormacao_.areaFormacao, JoinType.LEFT).get(AreaFormacao_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
