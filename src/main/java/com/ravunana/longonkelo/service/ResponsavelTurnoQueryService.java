package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.ResponsavelTurno;
import com.ravunana.longonkelo.repository.ResponsavelTurnoRepository;
import com.ravunana.longonkelo.service.criteria.ResponsavelTurnoCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelTurnoDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelTurnoMapper;
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
 * Service for executing complex queries for {@link ResponsavelTurno} entities in the database.
 * The main input is a {@link ResponsavelTurnoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResponsavelTurnoDTO} or a {@link Page} of {@link ResponsavelTurnoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResponsavelTurnoQueryService extends QueryService<ResponsavelTurno> {

    private final Logger log = LoggerFactory.getLogger(ResponsavelTurnoQueryService.class);

    private final ResponsavelTurnoRepository responsavelTurnoRepository;

    private final ResponsavelTurnoMapper responsavelTurnoMapper;

    public ResponsavelTurnoQueryService(
        ResponsavelTurnoRepository responsavelTurnoRepository,
        ResponsavelTurnoMapper responsavelTurnoMapper
    ) {
        this.responsavelTurnoRepository = responsavelTurnoRepository;
        this.responsavelTurnoMapper = responsavelTurnoMapper;
    }

    /**
     * Return a {@link List} of {@link ResponsavelTurnoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResponsavelTurnoDTO> findByCriteria(ResponsavelTurnoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResponsavelTurno> specification = createSpecification(criteria);
        return responsavelTurnoMapper.toDto(responsavelTurnoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResponsavelTurnoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelTurnoDTO> findByCriteria(ResponsavelTurnoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResponsavelTurno> specification = createSpecification(criteria);
        return responsavelTurnoRepository.findAll(specification, page).map(responsavelTurnoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResponsavelTurnoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResponsavelTurno> specification = createSpecification(criteria);
        return responsavelTurnoRepository.count(specification);
    }

    /**
     * Function to convert {@link ResponsavelTurnoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResponsavelTurno> createSpecification(ResponsavelTurnoCriteria criteria) {
        Specification<ResponsavelTurno> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResponsavelTurno_.id));
            }
            if (criteria.getDe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDe(), ResponsavelTurno_.de));
            }
            if (criteria.getAte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAte(), ResponsavelTurno_.ate));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), ResponsavelTurno_.timestamp));
            }
            if (criteria.getResponsavelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelId(),
                            root -> root.join(ResponsavelTurno_.responsavels, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(ResponsavelTurno_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(ResponsavelTurno_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getTurnoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurnoId(), root -> root.join(ResponsavelTurno_.turno, JoinType.LEFT).get(Turno_.id))
                    );
            }
        }
        return specification;
    }
}
