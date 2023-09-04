package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.ResponsavelDisciplina;
import com.ravunana.longonkelo.repository.ResponsavelDisciplinaRepository;
import com.ravunana.longonkelo.service.criteria.ResponsavelDisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelDisciplinaMapper;
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
 * Service for executing complex queries for {@link ResponsavelDisciplina} entities in the database.
 * The main input is a {@link ResponsavelDisciplinaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResponsavelDisciplinaDTO} or a {@link Page} of {@link ResponsavelDisciplinaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResponsavelDisciplinaQueryService extends QueryService<ResponsavelDisciplina> {

    private final Logger log = LoggerFactory.getLogger(ResponsavelDisciplinaQueryService.class);

    private final ResponsavelDisciplinaRepository responsavelDisciplinaRepository;

    private final ResponsavelDisciplinaMapper responsavelDisciplinaMapper;

    public ResponsavelDisciplinaQueryService(
        ResponsavelDisciplinaRepository responsavelDisciplinaRepository,
        ResponsavelDisciplinaMapper responsavelDisciplinaMapper
    ) {
        this.responsavelDisciplinaRepository = responsavelDisciplinaRepository;
        this.responsavelDisciplinaMapper = responsavelDisciplinaMapper;
    }

    /**
     * Return a {@link List} of {@link ResponsavelDisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResponsavelDisciplinaDTO> findByCriteria(ResponsavelDisciplinaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResponsavelDisciplina> specification = createSpecification(criteria);
        return responsavelDisciplinaMapper.toDto(responsavelDisciplinaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResponsavelDisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelDisciplinaDTO> findByCriteria(ResponsavelDisciplinaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResponsavelDisciplina> specification = createSpecification(criteria);
        return responsavelDisciplinaRepository.findAll(specification, page).map(responsavelDisciplinaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResponsavelDisciplinaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResponsavelDisciplina> specification = createSpecification(criteria);
        return responsavelDisciplinaRepository.count(specification);
    }

    /**
     * Function to convert {@link ResponsavelDisciplinaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResponsavelDisciplina> createSpecification(ResponsavelDisciplinaCriteria criteria) {
        Specification<ResponsavelDisciplina> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResponsavelDisciplina_.id));
            }
            if (criteria.getDe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDe(), ResponsavelDisciplina_.de));
            }
            if (criteria.getAte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAte(), ResponsavelDisciplina_.ate));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), ResponsavelDisciplina_.timestamp));
            }
            if (criteria.getResponsavelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelId(),
                            root -> root.join(ResponsavelDisciplina_.responsavels, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(ResponsavelDisciplina_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(ResponsavelDisciplina_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDisciplinaId(),
                            root -> root.join(ResponsavelDisciplina_.disciplina, JoinType.LEFT).get(Disciplina_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
