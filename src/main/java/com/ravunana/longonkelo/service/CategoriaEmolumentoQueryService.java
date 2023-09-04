package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import com.ravunana.longonkelo.repository.CategoriaEmolumentoRepository;
import com.ravunana.longonkelo.service.criteria.CategoriaEmolumentoCriteria;
import com.ravunana.longonkelo.service.dto.CategoriaEmolumentoDTO;
import com.ravunana.longonkelo.service.mapper.CategoriaEmolumentoMapper;
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
 * Service for executing complex queries for {@link CategoriaEmolumento} entities in the database.
 * The main input is a {@link CategoriaEmolumentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaEmolumentoDTO} or a {@link Page} of {@link CategoriaEmolumentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaEmolumentoQueryService extends QueryService<CategoriaEmolumento> {

    private final Logger log = LoggerFactory.getLogger(CategoriaEmolumentoQueryService.class);

    private final CategoriaEmolumentoRepository categoriaEmolumentoRepository;

    private final CategoriaEmolumentoMapper categoriaEmolumentoMapper;

    public CategoriaEmolumentoQueryService(
        CategoriaEmolumentoRepository categoriaEmolumentoRepository,
        CategoriaEmolumentoMapper categoriaEmolumentoMapper
    ) {
        this.categoriaEmolumentoRepository = categoriaEmolumentoRepository;
        this.categoriaEmolumentoMapper = categoriaEmolumentoMapper;
    }

    /**
     * Return a {@link List} of {@link CategoriaEmolumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaEmolumentoDTO> findByCriteria(CategoriaEmolumentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoriaEmolumento> specification = createSpecification(criteria);
        return categoriaEmolumentoMapper.toDto(categoriaEmolumentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoriaEmolumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaEmolumentoDTO> findByCriteria(CategoriaEmolumentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoriaEmolumento> specification = createSpecification(criteria);
        return categoriaEmolumentoRepository.findAll(specification, page).map(categoriaEmolumentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriaEmolumentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoriaEmolumento> specification = createSpecification(criteria);
        return categoriaEmolumentoRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriaEmolumentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategoriaEmolumento> createSpecification(CategoriaEmolumentoCriteria criteria) {
        Specification<CategoriaEmolumento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategoriaEmolumento_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), CategoriaEmolumento_.nome));
            }
            if (criteria.getIsServico() != null) {
                specification = specification.and(buildSpecification(criteria.getIsServico(), CategoriaEmolumento_.isServico));
            }
            if (criteria.getCor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCor(), CategoriaEmolumento_.cor));
            }
            if (criteria.getIsIsentoMulta() != null) {
                specification = specification.and(buildSpecification(criteria.getIsIsentoMulta(), CategoriaEmolumento_.isIsentoMulta));
            }
            if (criteria.getIsIsentoJuro() != null) {
                specification = specification.and(buildSpecification(criteria.getIsIsentoJuro(), CategoriaEmolumento_.isIsentoJuro));
            }
            if (criteria.getEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmolumentoId(),
                            root -> root.join(CategoriaEmolumento_.emolumentos, JoinType.LEFT).get(Emolumento_.id)
                        )
                    );
            }
            if (criteria.getPlanoMultaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanoMultaId(),
                            root -> root.join(CategoriaEmolumento_.planoMulta, JoinType.LEFT).get(PlanoMulta_.id)
                        )
                    );
            }
            if (criteria.getPlanosDescontoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanosDescontoId(),
                            root -> root.join(CategoriaEmolumento_.planosDescontos, JoinType.LEFT).get(PlanoDesconto_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
