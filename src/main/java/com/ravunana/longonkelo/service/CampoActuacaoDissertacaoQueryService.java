package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.CampoActuacaoDissertacao;
import com.ravunana.longonkelo.repository.CampoActuacaoDissertacaoRepository;
import com.ravunana.longonkelo.service.criteria.CampoActuacaoDissertacaoCriteria;
import com.ravunana.longonkelo.service.dto.CampoActuacaoDissertacaoDTO;
import com.ravunana.longonkelo.service.mapper.CampoActuacaoDissertacaoMapper;
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
 * Service for executing complex queries for {@link CampoActuacaoDissertacao} entities in the database.
 * The main input is a {@link CampoActuacaoDissertacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CampoActuacaoDissertacaoDTO} or a {@link Page} of {@link CampoActuacaoDissertacaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CampoActuacaoDissertacaoQueryService extends QueryService<CampoActuacaoDissertacao> {

    private final Logger log = LoggerFactory.getLogger(CampoActuacaoDissertacaoQueryService.class);

    private final CampoActuacaoDissertacaoRepository campoActuacaoDissertacaoRepository;

    private final CampoActuacaoDissertacaoMapper campoActuacaoDissertacaoMapper;

    public CampoActuacaoDissertacaoQueryService(
        CampoActuacaoDissertacaoRepository campoActuacaoDissertacaoRepository,
        CampoActuacaoDissertacaoMapper campoActuacaoDissertacaoMapper
    ) {
        this.campoActuacaoDissertacaoRepository = campoActuacaoDissertacaoRepository;
        this.campoActuacaoDissertacaoMapper = campoActuacaoDissertacaoMapper;
    }

    /**
     * Return a {@link List} of {@link CampoActuacaoDissertacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CampoActuacaoDissertacaoDTO> findByCriteria(CampoActuacaoDissertacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CampoActuacaoDissertacao> specification = createSpecification(criteria);
        return campoActuacaoDissertacaoMapper.toDto(campoActuacaoDissertacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CampoActuacaoDissertacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CampoActuacaoDissertacaoDTO> findByCriteria(CampoActuacaoDissertacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CampoActuacaoDissertacao> specification = createSpecification(criteria);
        return campoActuacaoDissertacaoRepository.findAll(specification, page).map(campoActuacaoDissertacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CampoActuacaoDissertacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CampoActuacaoDissertacao> specification = createSpecification(criteria);
        return campoActuacaoDissertacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link CampoActuacaoDissertacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CampoActuacaoDissertacao> createSpecification(CampoActuacaoDissertacaoCriteria criteria) {
        Specification<CampoActuacaoDissertacao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CampoActuacaoDissertacao_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), CampoActuacaoDissertacao_.nome));
            }
            if (criteria.getIsActivo() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActivo(), CampoActuacaoDissertacao_.isActivo));
            }
            if (criteria.getCursosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCursosId(),
                            root -> root.join(CampoActuacaoDissertacao_.cursos, JoinType.LEFT).get(Curso_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
