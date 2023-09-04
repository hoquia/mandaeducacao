package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Emolumento;
import com.ravunana.longonkelo.repository.EmolumentoRepository;
import com.ravunana.longonkelo.service.criteria.EmolumentoCriteria;
import com.ravunana.longonkelo.service.dto.EmolumentoDTO;
import com.ravunana.longonkelo.service.mapper.EmolumentoMapper;
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
 * Service for executing complex queries for {@link Emolumento} entities in the database.
 * The main input is a {@link EmolumentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmolumentoDTO} or a {@link Page} of {@link EmolumentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmolumentoQueryService extends QueryService<Emolumento> {

    private final Logger log = LoggerFactory.getLogger(EmolumentoQueryService.class);

    private final EmolumentoRepository emolumentoRepository;

    private final EmolumentoMapper emolumentoMapper;

    public EmolumentoQueryService(EmolumentoRepository emolumentoRepository, EmolumentoMapper emolumentoMapper) {
        this.emolumentoRepository = emolumentoRepository;
        this.emolumentoMapper = emolumentoMapper;
    }

    /**
     * Return a {@link List} of {@link EmolumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmolumentoDTO> findByCriteria(EmolumentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Emolumento> specification = createSpecification(criteria);
        return emolumentoMapper.toDto(emolumentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmolumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmolumentoDTO> findByCriteria(EmolumentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Emolumento> specification = createSpecification(criteria);
        return emolumentoRepository.findAll(specification, page).map(emolumentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmolumentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Emolumento> specification = createSpecification(criteria);
        return emolumentoRepository.count(specification);
    }

    /**
     * Function to convert {@link EmolumentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Emolumento> createSpecification(EmolumentoCriteria criteria) {
        Specification<Emolumento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Emolumento_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Emolumento_.numero));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Emolumento_.nome));
            }
            if (criteria.getPreco() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreco(), Emolumento_.preco));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), Emolumento_.quantidade));
            }
            if (criteria.getPeriodo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodo(), Emolumento_.periodo));
            }
            if (criteria.getInicioPeriodo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInicioPeriodo(), Emolumento_.inicioPeriodo));
            }
            if (criteria.getFimPeriodo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFimPeriodo(), Emolumento_.fimPeriodo));
            }
            if (criteria.getIsObrigatorioMatricula() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsObrigatorioMatricula(), Emolumento_.isObrigatorioMatricula));
            }
            if (criteria.getIsObrigatorioConfirmacao() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsObrigatorioConfirmacao(), Emolumento_.isObrigatorioConfirmacao));
            }
            if (criteria.getItemFacturaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getItemFacturaId(),
                            root -> root.join(Emolumento_.itemFacturas, JoinType.LEFT).get(ItemFactura_.id)
                        )
                    );
            }
            if (criteria.getEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmolumentoId(),
                            root -> root.join(Emolumento_.emolumentos, JoinType.LEFT).get(Emolumento_.id)
                        )
                    );
            }
            if (criteria.getPrecosEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrecosEmolumentoId(),
                            root -> root.join(Emolumento_.precosEmolumentos, JoinType.LEFT).get(PrecoEmolumento_.id)
                        )
                    );
            }
            if (criteria.getCategoriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriaId(),
                            root -> root.join(Emolumento_.categoria, JoinType.LEFT).get(CategoriaEmolumento_.id)
                        )
                    );
            }
            if (criteria.getImpostoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getImpostoId(), root -> root.join(Emolumento_.imposto, JoinType.LEFT).get(Imposto_.id))
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenciaId(),
                            root -> root.join(Emolumento_.referencia, JoinType.LEFT).get(Emolumento_.id)
                        )
                    );
            }
            if (criteria.getPlanoMultaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanoMultaId(),
                            root -> root.join(Emolumento_.planoMulta, JoinType.LEFT).get(PlanoMulta_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
