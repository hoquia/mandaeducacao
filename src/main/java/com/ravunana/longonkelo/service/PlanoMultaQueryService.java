package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.PlanoMulta;
import com.ravunana.longonkelo.repository.PlanoMultaRepository;
import com.ravunana.longonkelo.service.criteria.PlanoMultaCriteria;
import com.ravunana.longonkelo.service.dto.PlanoMultaDTO;
import com.ravunana.longonkelo.service.mapper.PlanoMultaMapper;
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
 * Service for executing complex queries for {@link PlanoMulta} entities in the database.
 * The main input is a {@link PlanoMultaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoMultaDTO} or a {@link Page} of {@link PlanoMultaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoMultaQueryService extends QueryService<PlanoMulta> {

    private final Logger log = LoggerFactory.getLogger(PlanoMultaQueryService.class);

    private final PlanoMultaRepository planoMultaRepository;

    private final PlanoMultaMapper planoMultaMapper;

    public PlanoMultaQueryService(PlanoMultaRepository planoMultaRepository, PlanoMultaMapper planoMultaMapper) {
        this.planoMultaRepository = planoMultaRepository;
        this.planoMultaMapper = planoMultaMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoMultaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoMultaDTO> findByCriteria(PlanoMultaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoMulta> specification = createSpecification(criteria);
        return planoMultaMapper.toDto(planoMultaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoMultaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoMultaDTO> findByCriteria(PlanoMultaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoMulta> specification = createSpecification(criteria);
        return planoMultaRepository.findAll(specification, page).map(planoMultaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoMultaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoMulta> specification = createSpecification(criteria);
        return planoMultaRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoMultaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoMulta> createSpecification(PlanoMultaCriteria criteria) {
        Specification<PlanoMulta> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoMulta_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PlanoMulta_.descricao));
            }
            if (criteria.getDiaAplicacaoMulta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiaAplicacaoMulta(), PlanoMulta_.diaAplicacaoMulta));
            }
            if (criteria.getMetodoAplicacaoMulta() != null) {
                specification = specification.and(buildSpecification(criteria.getMetodoAplicacaoMulta(), PlanoMulta_.metodoAplicacaoMulta));
            }
            if (criteria.getTaxaMulta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxaMulta(), PlanoMulta_.taxaMulta));
            }
            if (criteria.getIsTaxaMultaPercentual() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsTaxaMultaPercentual(), PlanoMulta_.isTaxaMultaPercentual));
            }
            if (criteria.getDiaAplicacaoJuro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiaAplicacaoJuro(), PlanoMulta_.diaAplicacaoJuro));
            }
            if (criteria.getMetodoAplicacaoJuro() != null) {
                specification = specification.and(buildSpecification(criteria.getMetodoAplicacaoJuro(), PlanoMulta_.metodoAplicacaoJuro));
            }
            if (criteria.getTaxaJuro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxaJuro(), PlanoMulta_.taxaJuro));
            }
            if (criteria.getIsTaxaJuroPercentual() != null) {
                specification = specification.and(buildSpecification(criteria.getIsTaxaJuroPercentual(), PlanoMulta_.isTaxaJuroPercentual));
            }
            if (criteria.getAumentarJuroEmDias() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAumentarJuroEmDias(), PlanoMulta_.aumentarJuroEmDias));
            }
            if (criteria.getIsAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAtivo(), PlanoMulta_.isAtivo));
            }
            if (criteria.getCategoriaEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriaEmolumentoId(),
                            root -> root.join(PlanoMulta_.categoriaEmolumentos, JoinType.LEFT).get(CategoriaEmolumento_.id)
                        )
                    );
            }
            if (criteria.getEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmolumentoId(),
                            root -> root.join(PlanoMulta_.emolumentos, JoinType.LEFT).get(Emolumento_.id)
                        )
                    );
            }
            if (criteria.getPrecoEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPrecoEmolumentoId(),
                            root -> root.join(PlanoMulta_.precoEmolumentos, JoinType.LEFT).get(PrecoEmolumento_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(PlanoMulta_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
