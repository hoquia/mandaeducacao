package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.PeriodoHorario;
import com.ravunana.longonkelo.repository.PeriodoHorarioRepository;
import com.ravunana.longonkelo.service.criteria.PeriodoHorarioCriteria;
import com.ravunana.longonkelo.service.dto.PeriodoHorarioDTO;
import com.ravunana.longonkelo.service.mapper.PeriodoHorarioMapper;
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
 * Service for executing complex queries for {@link PeriodoHorario} entities in the database.
 * The main input is a {@link PeriodoHorarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PeriodoHorarioDTO} or a {@link Page} of {@link PeriodoHorarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PeriodoHorarioQueryService extends QueryService<PeriodoHorario> {

    private final Logger log = LoggerFactory.getLogger(PeriodoHorarioQueryService.class);

    private final PeriodoHorarioRepository periodoHorarioRepository;

    private final PeriodoHorarioMapper periodoHorarioMapper;

    public PeriodoHorarioQueryService(PeriodoHorarioRepository periodoHorarioRepository, PeriodoHorarioMapper periodoHorarioMapper) {
        this.periodoHorarioRepository = periodoHorarioRepository;
        this.periodoHorarioMapper = periodoHorarioMapper;
    }

    /**
     * Return a {@link List} of {@link PeriodoHorarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PeriodoHorarioDTO> findByCriteria(PeriodoHorarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PeriodoHorario> specification = createSpecification(criteria);
        return periodoHorarioMapper.toDto(periodoHorarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PeriodoHorarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PeriodoHorarioDTO> findByCriteria(PeriodoHorarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PeriodoHorario> specification = createSpecification(criteria);
        return periodoHorarioRepository.findAll(specification, page).map(periodoHorarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PeriodoHorarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PeriodoHorario> specification = createSpecification(criteria);
        return periodoHorarioRepository.count(specification);
    }

    /**
     * Function to convert {@link PeriodoHorarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PeriodoHorario> createSpecification(PeriodoHorarioCriteria criteria) {
        Specification<PeriodoHorario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PeriodoHorario_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PeriodoHorario_.descricao));
            }
            if (criteria.getTempo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTempo(), PeriodoHorario_.tempo));
            }
            if (criteria.getInicio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInicio(), PeriodoHorario_.inicio));
            }
            if (criteria.getFim() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFim(), PeriodoHorario_.fim));
            }
            if (criteria.getHorarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHorarioId(),
                            root -> root.join(PeriodoHorario_.horarios, JoinType.LEFT).get(Horario_.id)
                        )
                    );
            }
            if (criteria.getTurnoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurnoId(), root -> root.join(PeriodoHorario_.turno, JoinType.LEFT).get(Turno_.id))
                    );
            }
        }
        return specification;
    }
}
