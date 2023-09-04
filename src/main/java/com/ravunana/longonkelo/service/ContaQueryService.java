package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Conta;
import com.ravunana.longonkelo.repository.ContaRepository;
import com.ravunana.longonkelo.service.criteria.ContaCriteria;
import com.ravunana.longonkelo.service.dto.ContaDTO;
import com.ravunana.longonkelo.service.mapper.ContaMapper;
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
 * Service for executing complex queries for {@link Conta} entities in the database.
 * The main input is a {@link ContaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContaDTO} or a {@link Page} of {@link ContaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContaQueryService extends QueryService<Conta> {

    private final Logger log = LoggerFactory.getLogger(ContaQueryService.class);

    private final ContaRepository contaRepository;

    private final ContaMapper contaMapper;

    public ContaQueryService(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    /**
     * Return a {@link List} of {@link ContaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContaDTO> findByCriteria(ContaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Conta> specification = createSpecification(criteria);
        return contaMapper.toDto(contaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContaDTO> findByCriteria(ContaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Conta> specification = createSpecification(criteria);
        return contaRepository.findAll(specification, page).map(contaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Conta> specification = createSpecification(criteria);
        return contaRepository.count(specification);
    }

    /**
     * Function to convert {@link ContaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Conta> createSpecification(ContaCriteria criteria) {
        Specification<Conta> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Conta_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), Conta_.tipo));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Conta_.titulo));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Conta_.numero));
            }
            if (criteria.getIban() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIban(), Conta_.iban));
            }
            if (criteria.getTitular() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitular(), Conta_.titular));
            }
            if (criteria.getIsPadrao() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPadrao(), Conta_.isPadrao));
            }
            if (criteria.getTransacoesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransacoesId(),
                            root -> root.join(Conta_.transacoes, JoinType.LEFT).get(Transacao_.id)
                        )
                    );
            }
            if (criteria.getMoedaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMoedaId(), root -> root.join(Conta_.moeda, JoinType.LEFT).get(LookupItem_.id))
                    );
            }
        }
        return specification;
    }
}
