package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.ResponsavelCurso;
import com.ravunana.longonkelo.repository.ResponsavelCursoRepository;
import com.ravunana.longonkelo.service.criteria.ResponsavelCursoCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelCursoDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelCursoMapper;
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
 * Service for executing complex queries for {@link ResponsavelCurso} entities in the database.
 * The main input is a {@link ResponsavelCursoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResponsavelCursoDTO} or a {@link Page} of {@link ResponsavelCursoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResponsavelCursoQueryService extends QueryService<ResponsavelCurso> {

    private final Logger log = LoggerFactory.getLogger(ResponsavelCursoQueryService.class);

    private final ResponsavelCursoRepository responsavelCursoRepository;

    private final ResponsavelCursoMapper responsavelCursoMapper;

    public ResponsavelCursoQueryService(
        ResponsavelCursoRepository responsavelCursoRepository,
        ResponsavelCursoMapper responsavelCursoMapper
    ) {
        this.responsavelCursoRepository = responsavelCursoRepository;
        this.responsavelCursoMapper = responsavelCursoMapper;
    }

    /**
     * Return a {@link List} of {@link ResponsavelCursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResponsavelCursoDTO> findByCriteria(ResponsavelCursoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResponsavelCurso> specification = createSpecification(criteria);
        return responsavelCursoMapper.toDto(responsavelCursoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResponsavelCursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelCursoDTO> findByCriteria(ResponsavelCursoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResponsavelCurso> specification = createSpecification(criteria);
        return responsavelCursoRepository.findAll(specification, page).map(responsavelCursoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResponsavelCursoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResponsavelCurso> specification = createSpecification(criteria);
        return responsavelCursoRepository.count(specification);
    }

    /**
     * Function to convert {@link ResponsavelCursoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResponsavelCurso> createSpecification(ResponsavelCursoCriteria criteria) {
        Specification<ResponsavelCurso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResponsavelCurso_.id));
            }
            if (criteria.getDe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDe(), ResponsavelCurso_.de));
            }
            if (criteria.getAte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAte(), ResponsavelCurso_.ate));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), ResponsavelCurso_.timestamp));
            }
            if (criteria.getResponsavelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelId(),
                            root -> root.join(ResponsavelCurso_.responsavels, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(ResponsavelCurso_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(ResponsavelCurso_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCursoId(), root -> root.join(ResponsavelCurso_.curso, JoinType.LEFT).get(Curso_.id))
                    );
            }
        }
        return specification;
    }
}
