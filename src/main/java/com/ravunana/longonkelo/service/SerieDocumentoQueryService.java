package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.SerieDocumento;
import com.ravunana.longonkelo.repository.SerieDocumentoRepository;
import com.ravunana.longonkelo.service.criteria.SerieDocumentoCriteria;
import com.ravunana.longonkelo.service.dto.SerieDocumentoDTO;
import com.ravunana.longonkelo.service.mapper.SerieDocumentoMapper;
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
 * Service for executing complex queries for {@link SerieDocumento} entities in the database.
 * The main input is a {@link SerieDocumentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SerieDocumentoDTO} or a {@link Page} of {@link SerieDocumentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SerieDocumentoQueryService extends QueryService<SerieDocumento> {

    private final Logger log = LoggerFactory.getLogger(SerieDocumentoQueryService.class);

    private final SerieDocumentoRepository serieDocumentoRepository;

    private final SerieDocumentoMapper serieDocumentoMapper;

    public SerieDocumentoQueryService(SerieDocumentoRepository serieDocumentoRepository, SerieDocumentoMapper serieDocumentoMapper) {
        this.serieDocumentoRepository = serieDocumentoRepository;
        this.serieDocumentoMapper = serieDocumentoMapper;
    }

    /**
     * Return a {@link List} of {@link SerieDocumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SerieDocumentoDTO> findByCriteria(SerieDocumentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SerieDocumento> specification = createSpecification(criteria);
        return serieDocumentoMapper.toDto(serieDocumentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SerieDocumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SerieDocumentoDTO> findByCriteria(SerieDocumentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SerieDocumento> specification = createSpecification(criteria);
        return serieDocumentoRepository.findAll(specification, page).map(serieDocumentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SerieDocumentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SerieDocumento> specification = createSpecification(criteria);
        return serieDocumentoRepository.count(specification);
    }

    /**
     * Function to convert {@link SerieDocumentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SerieDocumento> createSpecification(SerieDocumentoCriteria criteria) {
        Specification<SerieDocumento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SerieDocumento_.id));
            }
            if (criteria.getAnoFiscal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnoFiscal(), SerieDocumento_.anoFiscal));
            }
            if (criteria.getVersao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersao(), SerieDocumento_.versao));
            }
            if (criteria.getSerie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerie(), SerieDocumento_.serie));
            }
            if (criteria.getIsAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAtivo(), SerieDocumento_.isAtivo));
            }
            if (criteria.getIsPadrao() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPadrao(), SerieDocumento_.isPadrao));
            }
            if (criteria.getSequenciaDocumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSequenciaDocumentoId(),
                            root -> root.join(SerieDocumento_.sequenciaDocumentos, JoinType.LEFT).get(SequenciaDocumento_.id)
                        )
                    );
            }
            if (criteria.getTipoDocumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoDocumentoId(),
                            root -> root.join(SerieDocumento_.tipoDocumento, JoinType.LEFT).get(DocumentoComercial_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
