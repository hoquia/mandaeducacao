package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.SequenciaDocumento;
import com.ravunana.longonkelo.repository.SequenciaDocumentoRepository;
import com.ravunana.longonkelo.service.criteria.SequenciaDocumentoCriteria;
import com.ravunana.longonkelo.service.dto.SequenciaDocumentoDTO;
import com.ravunana.longonkelo.service.mapper.SequenciaDocumentoMapper;
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
 * Service for executing complex queries for {@link SequenciaDocumento} entities in the database.
 * The main input is a {@link SequenciaDocumentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenciaDocumentoDTO} or a {@link Page} of {@link SequenciaDocumentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenciaDocumentoQueryService extends QueryService<SequenciaDocumento> {

    private final Logger log = LoggerFactory.getLogger(SequenciaDocumentoQueryService.class);

    private final SequenciaDocumentoRepository sequenciaDocumentoRepository;

    private final SequenciaDocumentoMapper sequenciaDocumentoMapper;

    public SequenciaDocumentoQueryService(
        SequenciaDocumentoRepository sequenciaDocumentoRepository,
        SequenciaDocumentoMapper sequenciaDocumentoMapper
    ) {
        this.sequenciaDocumentoRepository = sequenciaDocumentoRepository;
        this.sequenciaDocumentoMapper = sequenciaDocumentoMapper;
    }

    /**
     * Return a {@link List} of {@link SequenciaDocumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenciaDocumentoDTO> findByCriteria(SequenciaDocumentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenciaDocumento> specification = createSpecification(criteria);
        return sequenciaDocumentoMapper.toDto(sequenciaDocumentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenciaDocumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenciaDocumentoDTO> findByCriteria(SequenciaDocumentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenciaDocumento> specification = createSpecification(criteria);
        return sequenciaDocumentoRepository.findAll(specification, page).map(sequenciaDocumentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenciaDocumentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenciaDocumento> specification = createSpecification(criteria);
        return sequenciaDocumentoRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenciaDocumentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenciaDocumento> createSpecification(SequenciaDocumentoCriteria criteria) {
        Specification<SequenciaDocumento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenciaDocumento_.id));
            }
            if (criteria.getSequencia() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSequencia(), SequenciaDocumento_.sequencia));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), SequenciaDocumento_.data));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), SequenciaDocumento_.hash));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), SequenciaDocumento_.timestamp));
            }
            if (criteria.getSerieId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSerieId(),
                            root -> root.join(SequenciaDocumento_.serie, JoinType.LEFT).get(SerieDocumento_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
