package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.NaturezaTrabalho;
import com.ravunana.longonkelo.repository.NaturezaTrabalhoRepository;
import com.ravunana.longonkelo.service.criteria.NaturezaTrabalhoCriteria;
import com.ravunana.longonkelo.service.dto.NaturezaTrabalhoDTO;
import com.ravunana.longonkelo.service.mapper.NaturezaTrabalhoMapper;
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
 * Service for executing complex queries for {@link NaturezaTrabalho} entities in the database.
 * The main input is a {@link NaturezaTrabalhoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NaturezaTrabalhoDTO} or a {@link Page} of {@link NaturezaTrabalhoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NaturezaTrabalhoQueryService extends QueryService<NaturezaTrabalho> {

    private final Logger log = LoggerFactory.getLogger(NaturezaTrabalhoQueryService.class);

    private final NaturezaTrabalhoRepository naturezaTrabalhoRepository;

    private final NaturezaTrabalhoMapper naturezaTrabalhoMapper;

    public NaturezaTrabalhoQueryService(
        NaturezaTrabalhoRepository naturezaTrabalhoRepository,
        NaturezaTrabalhoMapper naturezaTrabalhoMapper
    ) {
        this.naturezaTrabalhoRepository = naturezaTrabalhoRepository;
        this.naturezaTrabalhoMapper = naturezaTrabalhoMapper;
    }

    /**
     * Return a {@link List} of {@link NaturezaTrabalhoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NaturezaTrabalhoDTO> findByCriteria(NaturezaTrabalhoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NaturezaTrabalho> specification = createSpecification(criteria);
        return naturezaTrabalhoMapper.toDto(naturezaTrabalhoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NaturezaTrabalhoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NaturezaTrabalhoDTO> findByCriteria(NaturezaTrabalhoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NaturezaTrabalho> specification = createSpecification(criteria);
        return naturezaTrabalhoRepository.findAll(specification, page).map(naturezaTrabalhoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NaturezaTrabalhoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NaturezaTrabalho> specification = createSpecification(criteria);
        return naturezaTrabalhoRepository.count(specification);
    }

    /**
     * Function to convert {@link NaturezaTrabalhoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NaturezaTrabalho> createSpecification(NaturezaTrabalhoCriteria criteria) {
        Specification<NaturezaTrabalho> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NaturezaTrabalho_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), NaturezaTrabalho_.nome));
            }
            if (criteria.getIsActivo() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActivo(), NaturezaTrabalho_.isActivo));
            }
            if (criteria.getDissertacaoFinalCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDissertacaoFinalCursoId(),
                            root -> root.join(NaturezaTrabalho_.dissertacaoFinalCursos, JoinType.LEFT).get(DissertacaoFinalCurso_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
