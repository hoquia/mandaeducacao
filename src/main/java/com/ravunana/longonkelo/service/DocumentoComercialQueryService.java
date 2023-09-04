package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.DocumentoComercial;
import com.ravunana.longonkelo.repository.DocumentoComercialRepository;
import com.ravunana.longonkelo.service.criteria.DocumentoComercialCriteria;
import com.ravunana.longonkelo.service.dto.DocumentoComercialDTO;
import com.ravunana.longonkelo.service.mapper.DocumentoComercialMapper;
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
 * Service for executing complex queries for {@link DocumentoComercial} entities in the database.
 * The main input is a {@link DocumentoComercialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentoComercialDTO} or a {@link Page} of {@link DocumentoComercialDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentoComercialQueryService extends QueryService<DocumentoComercial> {

    private final Logger log = LoggerFactory.getLogger(DocumentoComercialQueryService.class);

    private final DocumentoComercialRepository documentoComercialRepository;

    private final DocumentoComercialMapper documentoComercialMapper;

    public DocumentoComercialQueryService(
        DocumentoComercialRepository documentoComercialRepository,
        DocumentoComercialMapper documentoComercialMapper
    ) {
        this.documentoComercialRepository = documentoComercialRepository;
        this.documentoComercialMapper = documentoComercialMapper;
    }

    /**
     * Return a {@link List} of {@link DocumentoComercialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentoComercialDTO> findByCriteria(DocumentoComercialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DocumentoComercial> specification = createSpecification(criteria);
        return documentoComercialMapper.toDto(documentoComercialRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DocumentoComercialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoComercialDTO> findByCriteria(DocumentoComercialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentoComercial> specification = createSpecification(criteria);
        return documentoComercialRepository.findAll(specification, page).map(documentoComercialMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentoComercialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DocumentoComercial> specification = createSpecification(criteria);
        return documentoComercialRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentoComercialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentoComercial> createSpecification(DocumentoComercialCriteria criteria) {
        Specification<DocumentoComercial> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentoComercial_.id));
            }
            if (criteria.getModulo() != null) {
                specification = specification.and(buildSpecification(criteria.getModulo(), DocumentoComercial_.modulo));
            }
            if (criteria.getOrigem() != null) {
                specification = specification.and(buildSpecification(criteria.getOrigem(), DocumentoComercial_.origem));
            }
            if (criteria.getSiglaInterna() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiglaInterna(), DocumentoComercial_.siglaInterna));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), DocumentoComercial_.descricao));
            }
            if (criteria.getSiglaFiscal() != null) {
                specification = specification.and(buildSpecification(criteria.getSiglaFiscal(), DocumentoComercial_.siglaFiscal));
            }
            if (criteria.getIsMovimentaEstoque() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsMovimentaEstoque(), DocumentoComercial_.isMovimentaEstoque));
            }
            if (criteria.getIsMovimentaCaixa() != null) {
                specification = specification.and(buildSpecification(criteria.getIsMovimentaCaixa(), DocumentoComercial_.isMovimentaCaixa));
            }
            if (criteria.getIsNotificaEntidade() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsNotificaEntidade(), DocumentoComercial_.isNotificaEntidade));
            }
            if (criteria.getIsNotificaGerente() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsNotificaGerente(), DocumentoComercial_.isNotificaGerente));
            }
            if (criteria.getIsEnviaSMS() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEnviaSMS(), DocumentoComercial_.isEnviaSMS));
            }
            if (criteria.getIsEnviaEmail() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEnviaEmail(), DocumentoComercial_.isEnviaEmail));
            }
            if (criteria.getIsEnviaPush() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEnviaPush(), DocumentoComercial_.isEnviaPush));
            }
            if (criteria.getValidaCreditoDisponivel() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getValidaCreditoDisponivel(), DocumentoComercial_.validaCreditoDisponivel)
                    );
            }
            if (criteria.getSerieDocumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSerieDocumentoId(),
                            root -> root.join(DocumentoComercial_.serieDocumentos, JoinType.LEFT).get(SerieDocumento_.id)
                        )
                    );
            }
            if (criteria.getFacturaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFacturaId(),
                            root -> root.join(DocumentoComercial_.facturas, JoinType.LEFT).get(Factura_.id)
                        )
                    );
            }
            if (criteria.getReciboId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReciboId(),
                            root -> root.join(DocumentoComercial_.recibos, JoinType.LEFT).get(Recibo_.id)
                        )
                    );
            }
            if (criteria.getTransformaEmId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransformaEmId(),
                            root -> root.join(DocumentoComercial_.transformaEm, JoinType.LEFT).get(DocumentoComercial_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
