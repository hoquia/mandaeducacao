package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.EnderecoDiscente;
import com.ravunana.longonkelo.repository.EnderecoDiscenteRepository;
import com.ravunana.longonkelo.service.criteria.EnderecoDiscenteCriteria;
import com.ravunana.longonkelo.service.dto.EnderecoDiscenteDTO;
import com.ravunana.longonkelo.service.mapper.EnderecoDiscenteMapper;
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
 * Service for executing complex queries for {@link EnderecoDiscente} entities in the database.
 * The main input is a {@link EnderecoDiscenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnderecoDiscenteDTO} or a {@link Page} of {@link EnderecoDiscenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnderecoDiscenteQueryService extends QueryService<EnderecoDiscente> {

    private final Logger log = LoggerFactory.getLogger(EnderecoDiscenteQueryService.class);

    private final EnderecoDiscenteRepository enderecoDiscenteRepository;

    private final EnderecoDiscenteMapper enderecoDiscenteMapper;

    public EnderecoDiscenteQueryService(
        EnderecoDiscenteRepository enderecoDiscenteRepository,
        EnderecoDiscenteMapper enderecoDiscenteMapper
    ) {
        this.enderecoDiscenteRepository = enderecoDiscenteRepository;
        this.enderecoDiscenteMapper = enderecoDiscenteMapper;
    }

    /**
     * Return a {@link List} of {@link EnderecoDiscenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnderecoDiscenteDTO> findByCriteria(EnderecoDiscenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EnderecoDiscente> specification = createSpecification(criteria);
        return enderecoDiscenteMapper.toDto(enderecoDiscenteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnderecoDiscenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnderecoDiscenteDTO> findByCriteria(EnderecoDiscenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnderecoDiscente> specification = createSpecification(criteria);
        return enderecoDiscenteRepository.findAll(specification, page).map(enderecoDiscenteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnderecoDiscenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnderecoDiscente> specification = createSpecification(criteria);
        return enderecoDiscenteRepository.count(specification);
    }

    /**
     * Function to convert {@link EnderecoDiscenteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EnderecoDiscente> createSpecification(EnderecoDiscenteCriteria criteria) {
        Specification<EnderecoDiscente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EnderecoDiscente_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), EnderecoDiscente_.tipo));
            }
            if (criteria.getBairro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBairro(), EnderecoDiscente_.bairro));
            }
            if (criteria.getRua() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRua(), EnderecoDiscente_.rua));
            }
            if (criteria.getNumeroCasa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroCasa(), EnderecoDiscente_.numeroCasa));
            }
            if (criteria.getCodigoPostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigoPostal(), EnderecoDiscente_.codigoPostal));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), EnderecoDiscente_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), EnderecoDiscente_.longitude));
            }
            if (criteria.getPaisId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaisId(),
                            root -> root.join(EnderecoDiscente_.pais, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getProvinciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProvinciaId(),
                            root -> root.join(EnderecoDiscente_.provincia, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getMunicipioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMunicipioId(),
                            root -> root.join(EnderecoDiscente_.municipio, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getDiscenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDiscenteId(),
                            root -> root.join(EnderecoDiscente_.discente, JoinType.LEFT).get(Discente_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
