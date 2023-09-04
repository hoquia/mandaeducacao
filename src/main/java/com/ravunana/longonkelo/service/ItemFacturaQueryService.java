package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.ItemFactura;
import com.ravunana.longonkelo.repository.ItemFacturaRepository;
import com.ravunana.longonkelo.service.criteria.ItemFacturaCriteria;
import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
import com.ravunana.longonkelo.service.mapper.ItemFacturaMapper;
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
 * Service for executing complex queries for {@link ItemFactura} entities in the database.
 * The main input is a {@link ItemFacturaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemFacturaDTO} or a {@link Page} of {@link ItemFacturaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemFacturaQueryService extends QueryService<ItemFactura> {

    private final Logger log = LoggerFactory.getLogger(ItemFacturaQueryService.class);

    private final ItemFacturaRepository itemFacturaRepository;

    private final ItemFacturaMapper itemFacturaMapper;

    public ItemFacturaQueryService(ItemFacturaRepository itemFacturaRepository, ItemFacturaMapper itemFacturaMapper) {
        this.itemFacturaRepository = itemFacturaRepository;
        this.itemFacturaMapper = itemFacturaMapper;
    }

    /**
     * Return a {@link List} of {@link ItemFacturaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemFacturaDTO> findByCriteria(ItemFacturaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemFactura> specification = createSpecification(criteria);
        return itemFacturaMapper.toDto(itemFacturaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemFacturaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemFacturaDTO> findByCriteria(ItemFacturaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemFactura> specification = createSpecification(criteria);
        return itemFacturaRepository.findAll(specification, page).map(itemFacturaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemFacturaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemFactura> specification = createSpecification(criteria);
        return itemFacturaRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemFacturaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemFactura> createSpecification(ItemFacturaCriteria criteria) {
        Specification<ItemFactura> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemFactura_.id));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), ItemFactura_.quantidade));
            }
            if (criteria.getPrecoUnitario() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrecoUnitario(), ItemFactura_.precoUnitario));
            }
            if (criteria.getDesconto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesconto(), ItemFactura_.desconto));
            }
            if (criteria.getMulta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMulta(), ItemFactura_.multa));
            }
            if (criteria.getJuro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJuro(), ItemFactura_.juro));
            }
            if (criteria.getPrecoTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrecoTotal(), ItemFactura_.precoTotal));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), ItemFactura_.estado));
            }
            if (criteria.getTaxType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxType(), ItemFactura_.taxType));
            }
            if (criteria.getTaxCountryRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxCountryRegion(), ItemFactura_.taxCountryRegion));
            }
            if (criteria.getTaxCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxCode(), ItemFactura_.taxCode));
            }
            if (criteria.getTaxPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxPercentage(), ItemFactura_.taxPercentage));
            }
            if (criteria.getTaxExemptionReason() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTaxExemptionReason(), ItemFactura_.taxExemptionReason));
            }
            if (criteria.getTaxExemptionCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxExemptionCode(), ItemFactura_.taxExemptionCode));
            }
            if (criteria.getFacturaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFacturaId(), root -> root.join(ItemFactura_.factura, JoinType.LEFT).get(Factura_.id))
                    );
            }
            if (criteria.getEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmolumentoId(),
                            root -> root.join(ItemFactura_.emolumento, JoinType.LEFT).get(Emolumento_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
