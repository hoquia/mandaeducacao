package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Factura;
import com.ravunana.longonkelo.repository.FacturaRepository;
import com.ravunana.longonkelo.service.criteria.FacturaCriteria;
import com.ravunana.longonkelo.service.dto.FacturaDTO;
import com.ravunana.longonkelo.service.mapper.FacturaMapper;
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
 * Service for executing complex queries for {@link Factura} entities in the database.
 * The main input is a {@link FacturaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FacturaDTO} or a {@link Page} of {@link FacturaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FacturaQueryService extends QueryService<Factura> {

    private final Logger log = LoggerFactory.getLogger(FacturaQueryService.class);

    private final FacturaRepository facturaRepository;

    private final FacturaMapper facturaMapper;

    public FacturaQueryService(FacturaRepository facturaRepository, FacturaMapper facturaMapper) {
        this.facturaRepository = facturaRepository;
        this.facturaMapper = facturaMapper;
    }

    /**
     * Return a {@link List} of {@link FacturaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FacturaDTO> findByCriteria(FacturaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Factura> specification = createSpecification(criteria);
        return facturaMapper.toDto(facturaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FacturaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FacturaDTO> findByCriteria(FacturaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Factura> specification = createSpecification(criteria);
        return facturaRepository.findAll(specification, page).map(facturaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FacturaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Factura> specification = createSpecification(criteria);
        return facturaRepository.count(specification);
    }

    /**
     * Function to convert {@link FacturaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Factura> createSpecification(FacturaCriteria criteria) {
        Specification<Factura> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Factura_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Factura_.numero));
            }
            if (criteria.getCodigoEntrega() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigoEntrega(), Factura_.codigoEntrega));
            }
            if (criteria.getDataEmissao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataEmissao(), Factura_.dataEmissao));
            }
            if (criteria.getDataVencimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataVencimento(), Factura_.dataVencimento));
            }
            if (criteria.getCae() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCae(), Factura_.cae));
            }
            if (criteria.getInicioTransporte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInicioTransporte(), Factura_.inicioTransporte));
            }
            if (criteria.getFimTransporte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFimTransporte(), Factura_.fimTransporte));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), Factura_.estado));
            }
            if (criteria.getOrigem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrigem(), Factura_.origem));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), Factura_.timestamp));
            }
            if (criteria.getIsMoedaEntrangeira() != null) {
                specification = specification.and(buildSpecification(criteria.getIsMoedaEntrangeira(), Factura_.isMoedaEntrangeira));
            }
            if (criteria.getMoeda() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoeda(), Factura_.moeda));
            }
            if (criteria.getCambio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCambio(), Factura_.cambio));
            }
            if (criteria.getTotalMoedaEntrangeira() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalMoedaEntrangeira(), Factura_.totalMoedaEntrangeira));
            }
            if (criteria.getTotalIliquido() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalIliquido(), Factura_.totalIliquido));
            }
            if (criteria.getTotalDescontoComercial() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDescontoComercial(), Factura_.totalDescontoComercial));
            }
            if (criteria.getTotalLiquido() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalLiquido(), Factura_.totalLiquido));
            }
            if (criteria.getTotalImpostoIVA() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalImpostoIVA(), Factura_.totalImpostoIVA));
            }
            if (criteria.getTotalImpostoEspecialConsumo() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getTotalImpostoEspecialConsumo(), Factura_.totalImpostoEspecialConsumo)
                    );
            }
            if (criteria.getTotalDescontoFinanceiro() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalDescontoFinanceiro(), Factura_.totalDescontoFinanceiro));
            }
            if (criteria.getTotalFactura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalFactura(), Factura_.totalFactura));
            }
            if (criteria.getTotalImpostoRetencaoFonte() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalImpostoRetencaoFonte(), Factura_.totalImpostoRetencaoFonte));
            }
            if (criteria.getTotalPagar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPagar(), Factura_.totalPagar));
            }
            if (criteria.getDebito() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDebito(), Factura_.debito));
            }
            if (criteria.getCredito() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCredito(), Factura_.credito));
            }
            if (criteria.getTotalPago() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPago(), Factura_.totalPago));
            }
            if (criteria.getTotalDiferenca() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDiferenca(), Factura_.totalDiferenca));
            }
            if (criteria.getIsAutoFacturacao() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAutoFacturacao(), Factura_.isAutoFacturacao));
            }
            if (criteria.getIsRegimeCaixa() != null) {
                specification = specification.and(buildSpecification(criteria.getIsRegimeCaixa(), Factura_.isRegimeCaixa));
            }
            if (criteria.getIsEmitidaNomeEContaTerceiro() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsEmitidaNomeEContaTerceiro(), Factura_.isEmitidaNomeEContaTerceiro));
            }
            if (criteria.getIsNovo() != null) {
                specification = specification.and(buildSpecification(criteria.getIsNovo(), Factura_.isNovo));
            }
            if (criteria.getIsFiscalizado() != null) {
                specification = specification.and(buildSpecification(criteria.getIsFiscalizado(), Factura_.isFiscalizado));
            }
            if (criteria.getSignText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSignText(), Factura_.signText));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), Factura_.hash));
            }
            if (criteria.getHashShort() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHashShort(), Factura_.hashShort));
            }
            if (criteria.getHashControl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHashControl(), Factura_.hashControl));
            }
            if (criteria.getKeyVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKeyVersion(), Factura_.keyVersion));
            }
            if (criteria.getFacturaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFacturaId(), root -> root.join(Factura_.facturas, JoinType.LEFT).get(Factura_.id))
                    );
            }
            if (criteria.getItemsFacturaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getItemsFacturaId(),
                            root -> root.join(Factura_.itemsFacturas, JoinType.LEFT).get(ItemFactura_.id)
                        )
                    );
            }
            if (criteria.getAplicacoesFacturaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAplicacoesFacturaId(),
                            root -> root.join(Factura_.aplicacoesFacturas, JoinType.LEFT).get(AplicacaoRecibo_.id)
                        )
                    );
            }
            if (criteria.getResumosImpostoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResumosImpostoId(),
                            root -> root.join(Factura_.resumosImpostos, JoinType.LEFT).get(ResumoImpostoFactura_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(Factura_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUtilizadorId(), root -> root.join(Factura_.utilizador, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getMotivoAnulacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMotivoAnulacaoId(),
                            root -> root.join(Factura_.motivoAnulacao, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculaId(),
                            root -> root.join(Factura_.matricula, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenciaId(),
                            root -> root.join(Factura_.referencia, JoinType.LEFT).get(Factura_.id)
                        )
                    );
            }
            if (criteria.getDocumentoComercialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocumentoComercialId(),
                            root -> root.join(Factura_.documentoComercial, JoinType.LEFT).get(DocumentoComercial_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
