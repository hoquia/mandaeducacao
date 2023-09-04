package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Recibo;
import com.ravunana.longonkelo.repository.ReciboRepository;
import com.ravunana.longonkelo.service.criteria.ReciboCriteria;
import com.ravunana.longonkelo.service.dto.ReciboDTO;
import com.ravunana.longonkelo.service.mapper.ReciboMapper;
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
 * Service for executing complex queries for {@link Recibo} entities in the database.
 * The main input is a {@link ReciboCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReciboDTO} or a {@link Page} of {@link ReciboDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReciboQueryService extends QueryService<Recibo> {

    private final Logger log = LoggerFactory.getLogger(ReciboQueryService.class);

    private final ReciboRepository reciboRepository;

    private final ReciboMapper reciboMapper;

    public ReciboQueryService(ReciboRepository reciboRepository, ReciboMapper reciboMapper) {
        this.reciboRepository = reciboRepository;
        this.reciboMapper = reciboMapper;
    }

    /**
     * Return a {@link List} of {@link ReciboDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReciboDTO> findByCriteria(ReciboCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Recibo> specification = createSpecification(criteria);
        return reciboMapper.toDto(reciboRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReciboDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReciboDTO> findByCriteria(ReciboCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Recibo> specification = createSpecification(criteria);
        return reciboRepository.findAll(specification, page).map(reciboMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReciboCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Recibo> specification = createSpecification(criteria);
        return reciboRepository.count(specification);
    }

    /**
     * Function to convert {@link ReciboCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Recibo> createSpecification(ReciboCriteria criteria) {
        Specification<Recibo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Recibo_.id));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), Recibo_.data));
            }
            if (criteria.getVencimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVencimento(), Recibo_.vencimento));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Recibo_.numero));
            }
            if (criteria.getTotalSemImposto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalSemImposto(), Recibo_.totalSemImposto));
            }
            if (criteria.getTotalComImposto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalComImposto(), Recibo_.totalComImposto));
            }
            if (criteria.getTotalDescontoComercial() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDescontoComercial(), Recibo_.totalDescontoComercial));
            }
            if (criteria.getTotalDescontoFinanceiro() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDescontoFinanceiro(), Recibo_.totalDescontoFinanceiro));
            }
            if (criteria.getTotalIVA() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalIVA(), Recibo_.totalIVA));
            }
            if (criteria.getTotalRetencao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalRetencao(), Recibo_.totalRetencao));
            }
            if (criteria.getTotalJuro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalJuro(), Recibo_.totalJuro));
            }
            if (criteria.getCambio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCambio(), Recibo_.cambio));
            }
            if (criteria.getTotalMoedaEstrangeira() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalMoedaEstrangeira(), Recibo_.totalMoedaEstrangeira));
            }
            if (criteria.getTotalPagar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPagar(), Recibo_.totalPagar));
            }
            if (criteria.getTotalPago() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPago(), Recibo_.totalPago));
            }
            if (criteria.getTotalFalta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalFalta(), Recibo_.totalFalta));
            }
            if (criteria.getTotalTroco() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalTroco(), Recibo_.totalTroco));
            }
            if (criteria.getIsNovo() != null) {
                specification = specification.and(buildSpecification(criteria.getIsNovo(), Recibo_.isNovo));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), Recibo_.timestamp));
            }
            if (criteria.getDebito() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDebito(), Recibo_.debito));
            }
            if (criteria.getCredito() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCredito(), Recibo_.credito));
            }
            if (criteria.getIsFiscalizado() != null) {
                specification = specification.and(buildSpecification(criteria.getIsFiscalizado(), Recibo_.isFiscalizado));
            }
            if (criteria.getSignText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSignText(), Recibo_.signText));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), Recibo_.hash));
            }
            if (criteria.getHashShort() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHashShort(), Recibo_.hashShort));
            }
            if (criteria.getHashControl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHashControl(), Recibo_.hashControl));
            }
            if (criteria.getKeyVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKeyVersion(), Recibo_.keyVersion));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), Recibo_.estado));
            }
            if (criteria.getOrigem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrigem(), Recibo_.origem));
            }
            if (criteria.getAplicacoesReciboId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAplicacoesReciboId(),
                            root -> root.join(Recibo_.aplicacoesRecibos, JoinType.LEFT).get(AplicacaoRecibo_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(Recibo_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUtilizadorId(), root -> root.join(Recibo_.utilizador, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculaId(),
                            root -> root.join(Recibo_.matricula, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getDocumentoComercialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocumentoComercialId(),
                            root -> root.join(Recibo_.documentoComercial, JoinType.LEFT).get(DocumentoComercial_.id)
                        )
                    );
            }
            if (criteria.getTransacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransacaoId(),
                            root -> root.join(Recibo_.transacao, JoinType.LEFT).get(Transacao_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
