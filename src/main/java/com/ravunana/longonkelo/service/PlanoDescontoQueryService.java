package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.PlanoDesconto;
import com.ravunana.longonkelo.repository.PlanoDescontoRepository;
import com.ravunana.longonkelo.service.criteria.PlanoDescontoCriteria;
import com.ravunana.longonkelo.service.dto.PlanoDescontoDTO;
import com.ravunana.longonkelo.service.mapper.PlanoDescontoMapper;
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
 * Service for executing complex queries for {@link PlanoDesconto} entities in the database.
 * The main input is a {@link PlanoDescontoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoDescontoDTO} or a {@link Page} of {@link PlanoDescontoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoDescontoQueryService extends QueryService<PlanoDesconto> {

    private final Logger log = LoggerFactory.getLogger(PlanoDescontoQueryService.class);

    private final PlanoDescontoRepository planoDescontoRepository;

    private final PlanoDescontoMapper planoDescontoMapper;

    public PlanoDescontoQueryService(PlanoDescontoRepository planoDescontoRepository, PlanoDescontoMapper planoDescontoMapper) {
        this.planoDescontoRepository = planoDescontoRepository;
        this.planoDescontoMapper = planoDescontoMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoDescontoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoDescontoDTO> findByCriteria(PlanoDescontoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoDesconto> specification = createSpecification(criteria);
        return planoDescontoMapper.toDto(planoDescontoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoDescontoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoDescontoDTO> findByCriteria(PlanoDescontoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoDesconto> specification = createSpecification(criteria);
        return planoDescontoRepository.findAll(specification, page).map(planoDescontoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoDescontoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoDesconto> specification = createSpecification(criteria);
        return planoDescontoRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoDescontoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoDesconto> createSpecification(PlanoDescontoCriteria criteria) {
        Specification<PlanoDesconto> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoDesconto_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), PlanoDesconto_.codigo));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), PlanoDesconto_.nome));
            }
            if (criteria.getIsIsentoMulta() != null) {
                specification = specification.and(buildSpecification(criteria.getIsIsentoMulta(), PlanoDesconto_.isIsentoMulta));
            }
            if (criteria.getIsIsentoJuro() != null) {
                specification = specification.and(buildSpecification(criteria.getIsIsentoJuro(), PlanoDesconto_.isIsentoJuro));
            }
            if (criteria.getDesconto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesconto(), PlanoDesconto_.desconto));
            }
            if (criteria.getCategoriasEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriasEmolumentoId(),
                            root -> root.join(PlanoDesconto_.categoriasEmolumentos, JoinType.LEFT).get(CategoriaEmolumento_.id)
                        )
                    );
            }
            if (criteria.getMatriculasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculasId(),
                            root -> root.join(PlanoDesconto_.matriculas, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
