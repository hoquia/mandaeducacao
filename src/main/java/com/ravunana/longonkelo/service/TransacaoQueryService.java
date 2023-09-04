package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Transacao;
import com.ravunana.longonkelo.repository.TransacaoRepository;
import com.ravunana.longonkelo.service.criteria.TransacaoCriteria;
import com.ravunana.longonkelo.service.dto.TransacaoDTO;
import com.ravunana.longonkelo.service.mapper.TransacaoMapper;
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
 * Service for executing complex queries for {@link Transacao} entities in the database.
 * The main input is a {@link TransacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransacaoDTO} or a {@link Page} of {@link TransacaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransacaoQueryService extends QueryService<Transacao> {

    private final Logger log = LoggerFactory.getLogger(TransacaoQueryService.class);

    private final TransacaoRepository transacaoRepository;

    private final TransacaoMapper transacaoMapper;

    public TransacaoQueryService(TransacaoRepository transacaoRepository, TransacaoMapper transacaoMapper) {
        this.transacaoRepository = transacaoRepository;
        this.transacaoMapper = transacaoMapper;
    }

    /**
     * Return a {@link List} of {@link TransacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransacaoDTO> findByCriteria(TransacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Transacao> specification = createSpecification(criteria);
        return transacaoMapper.toDto(transacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransacaoDTO> findByCriteria(TransacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Transacao> specification = createSpecification(criteria);
        return transacaoRepository.findAll(specification, page).map(transacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Transacao> specification = createSpecification(criteria);
        return transacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link TransacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transacao> createSpecification(TransacaoCriteria criteria) {
        Specification<Transacao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transacao_.id));
            }
            if (criteria.getMontante() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontante(), Transacao_.montante));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), Transacao_.data));
            }
            if (criteria.getReferencia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferencia(), Transacao_.referencia));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), Transacao_.estado));
            }
            if (criteria.getSaldo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSaldo(), Transacao_.saldo));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), Transacao_.timestamp));
            }
            if (criteria.getRecibosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRecibosId(), root -> root.join(Transacao_.recibos, JoinType.LEFT).get(Recibo_.id))
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(Transacao_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getMoedaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMoedaId(), root -> root.join(Transacao_.moeda, JoinType.LEFT).get(LookupItem_.id))
                    );
            }
            if (criteria.getMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculaId(),
                            root -> root.join(Transacao_.matricula, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getMeioPagamentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMeioPagamentoId(),
                            root -> root.join(Transacao_.meioPagamento, JoinType.LEFT).get(MeioPagamento_.id)
                        )
                    );
            }
            if (criteria.getContaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getContaId(), root -> root.join(Transacao_.conta, JoinType.LEFT).get(Conta_.id))
                    );
            }
            if (criteria.getTransferenciaSaldoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferenciaSaldoId(),
                            root -> root.join(Transacao_.transferenciaSaldos, JoinType.LEFT).get(TransferenciaSaldo_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
