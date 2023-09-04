package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.EstadoDissertacao;
import com.ravunana.longonkelo.repository.EstadoDissertacaoRepository;
import com.ravunana.longonkelo.service.criteria.EstadoDissertacaoCriteria;
import com.ravunana.longonkelo.service.dto.EstadoDissertacaoDTO;
import com.ravunana.longonkelo.service.mapper.EstadoDissertacaoMapper;
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
 * Service for executing complex queries for {@link EstadoDissertacao} entities in the database.
 * The main input is a {@link EstadoDissertacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EstadoDissertacaoDTO} or a {@link Page} of {@link EstadoDissertacaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstadoDissertacaoQueryService extends QueryService<EstadoDissertacao> {

    private final Logger log = LoggerFactory.getLogger(EstadoDissertacaoQueryService.class);

    private final EstadoDissertacaoRepository estadoDissertacaoRepository;

    private final EstadoDissertacaoMapper estadoDissertacaoMapper;

    public EstadoDissertacaoQueryService(
        EstadoDissertacaoRepository estadoDissertacaoRepository,
        EstadoDissertacaoMapper estadoDissertacaoMapper
    ) {
        this.estadoDissertacaoRepository = estadoDissertacaoRepository;
        this.estadoDissertacaoMapper = estadoDissertacaoMapper;
    }

    /**
     * Return a {@link List} of {@link EstadoDissertacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EstadoDissertacaoDTO> findByCriteria(EstadoDissertacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EstadoDissertacao> specification = createSpecification(criteria);
        return estadoDissertacaoMapper.toDto(estadoDissertacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EstadoDissertacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EstadoDissertacaoDTO> findByCriteria(EstadoDissertacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EstadoDissertacao> specification = createSpecification(criteria);
        return estadoDissertacaoRepository.findAll(specification, page).map(estadoDissertacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EstadoDissertacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EstadoDissertacao> specification = createSpecification(criteria);
        return estadoDissertacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link EstadoDissertacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EstadoDissertacao> createSpecification(EstadoDissertacaoCriteria criteria) {
        Specification<EstadoDissertacao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EstadoDissertacao_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), EstadoDissertacao_.codigo));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), EstadoDissertacao_.nome));
            }
            if (criteria.getEtapa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEtapa(), EstadoDissertacao_.etapa));
            }
            if (criteria.getDissertacaoFinalCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDissertacaoFinalCursoId(),
                            root -> root.join(EstadoDissertacao_.dissertacaoFinalCursos, JoinType.LEFT).get(DissertacaoFinalCurso_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
