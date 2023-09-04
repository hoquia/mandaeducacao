package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.MeioPagamento;
import com.ravunana.longonkelo.repository.MeioPagamentoRepository;
import com.ravunana.longonkelo.service.criteria.MeioPagamentoCriteria;
import com.ravunana.longonkelo.service.dto.MeioPagamentoDTO;
import com.ravunana.longonkelo.service.mapper.MeioPagamentoMapper;
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
 * Service for executing complex queries for {@link MeioPagamento} entities in the database.
 * The main input is a {@link MeioPagamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MeioPagamentoDTO} or a {@link Page} of {@link MeioPagamentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MeioPagamentoQueryService extends QueryService<MeioPagamento> {

    private final Logger log = LoggerFactory.getLogger(MeioPagamentoQueryService.class);

    private final MeioPagamentoRepository meioPagamentoRepository;

    private final MeioPagamentoMapper meioPagamentoMapper;

    public MeioPagamentoQueryService(MeioPagamentoRepository meioPagamentoRepository, MeioPagamentoMapper meioPagamentoMapper) {
        this.meioPagamentoRepository = meioPagamentoRepository;
        this.meioPagamentoMapper = meioPagamentoMapper;
    }

    /**
     * Return a {@link List} of {@link MeioPagamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MeioPagamentoDTO> findByCriteria(MeioPagamentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MeioPagamento> specification = createSpecification(criteria);
        return meioPagamentoMapper.toDto(meioPagamentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MeioPagamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MeioPagamentoDTO> findByCriteria(MeioPagamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MeioPagamento> specification = createSpecification(criteria);
        return meioPagamentoRepository.findAll(specification, page).map(meioPagamentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MeioPagamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MeioPagamento> specification = createSpecification(criteria);
        return meioPagamentoRepository.count(specification);
    }

    /**
     * Function to convert {@link MeioPagamentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MeioPagamento> createSpecification(MeioPagamentoCriteria criteria) {
        Specification<MeioPagamento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MeioPagamento_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), MeioPagamento_.codigo));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), MeioPagamento_.nome));
            }
            if (criteria.getNumeroDigitoReferencia() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumeroDigitoReferencia(), MeioPagamento_.numeroDigitoReferencia));
            }
            if (criteria.getIsPagamentoInstantanio() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsPagamentoInstantanio(), MeioPagamento_.isPagamentoInstantanio));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), MeioPagamento_.hash));
            }
            if (criteria.getLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLink(), MeioPagamento_.link));
            }
            if (criteria.getToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToken(), MeioPagamento_.token));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), MeioPagamento_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), MeioPagamento_.password));
            }
            if (criteria.getFormatoReferencia() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFormatoReferencia(), MeioPagamento_.formatoReferencia));
            }
            if (criteria.getTransacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransacaoId(),
                            root -> root.join(MeioPagamento_.transacaos, JoinType.LEFT).get(Transacao_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
