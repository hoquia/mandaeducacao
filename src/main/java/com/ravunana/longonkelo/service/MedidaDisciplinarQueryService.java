package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.MedidaDisciplinar;
import com.ravunana.longonkelo.repository.MedidaDisciplinarRepository;
import com.ravunana.longonkelo.service.criteria.MedidaDisciplinarCriteria;
import com.ravunana.longonkelo.service.dto.MedidaDisciplinarDTO;
import com.ravunana.longonkelo.service.mapper.MedidaDisciplinarMapper;
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
 * Service for executing complex queries for {@link MedidaDisciplinar} entities in the database.
 * The main input is a {@link MedidaDisciplinarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MedidaDisciplinarDTO} or a {@link Page} of {@link MedidaDisciplinarDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MedidaDisciplinarQueryService extends QueryService<MedidaDisciplinar> {

    private final Logger log = LoggerFactory.getLogger(MedidaDisciplinarQueryService.class);

    private final MedidaDisciplinarRepository medidaDisciplinarRepository;

    private final MedidaDisciplinarMapper medidaDisciplinarMapper;

    public MedidaDisciplinarQueryService(
        MedidaDisciplinarRepository medidaDisciplinarRepository,
        MedidaDisciplinarMapper medidaDisciplinarMapper
    ) {
        this.medidaDisciplinarRepository = medidaDisciplinarRepository;
        this.medidaDisciplinarMapper = medidaDisciplinarMapper;
    }

    /**
     * Return a {@link List} of {@link MedidaDisciplinarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MedidaDisciplinarDTO> findByCriteria(MedidaDisciplinarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MedidaDisciplinar> specification = createSpecification(criteria);
        return medidaDisciplinarMapper.toDto(medidaDisciplinarRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MedidaDisciplinarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MedidaDisciplinarDTO> findByCriteria(MedidaDisciplinarCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MedidaDisciplinar> specification = createSpecification(criteria);
        return medidaDisciplinarRepository.findAll(specification, page).map(medidaDisciplinarMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MedidaDisciplinarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MedidaDisciplinar> specification = createSpecification(criteria);
        return medidaDisciplinarRepository.count(specification);
    }

    /**
     * Function to convert {@link MedidaDisciplinarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MedidaDisciplinar> createSpecification(MedidaDisciplinarCriteria criteria) {
        Specification<MedidaDisciplinar> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MedidaDisciplinar_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), MedidaDisciplinar_.descricao));
            }
            if (criteria.getPeriodo() != null) {
                specification = specification.and(buildSpecification(criteria.getPeriodo(), MedidaDisciplinar_.periodo));
            }
            if (criteria.getSuspensao() != null) {
                specification = specification.and(buildSpecification(criteria.getSuspensao(), MedidaDisciplinar_.suspensao));
            }
            if (criteria.getTempo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTempo(), MedidaDisciplinar_.tempo));
            }
            if (criteria.getCategoriaOcorrenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriaOcorrenciaId(),
                            root -> root.join(MedidaDisciplinar_.categoriaOcorrencias, JoinType.LEFT).get(CategoriaOcorrencia_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
