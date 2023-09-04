package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.repository.HorarioRepository;
import com.ravunana.longonkelo.service.criteria.HorarioCriteria;
import com.ravunana.longonkelo.service.dto.HorarioDTO;
import com.ravunana.longonkelo.service.mapper.HorarioMapper;
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
 * Service for executing complex queries for {@link Horario} entities in the database.
 * The main input is a {@link HorarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HorarioDTO} or a {@link Page} of {@link HorarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HorarioQueryService extends QueryService<Horario> {

    private final Logger log = LoggerFactory.getLogger(HorarioQueryService.class);

    private final HorarioRepository horarioRepository;

    private final HorarioMapper horarioMapper;

    public HorarioQueryService(HorarioRepository horarioRepository, HorarioMapper horarioMapper) {
        this.horarioRepository = horarioRepository;
        this.horarioMapper = horarioMapper;
    }

    /**
     * Return a {@link List} of {@link HorarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HorarioDTO> findByCriteria(HorarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Horario> specification = createSpecification(criteria);
        return horarioMapper.toDto(horarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HorarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HorarioDTO> findByCriteria(HorarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Horario> specification = createSpecification(criteria);
        return horarioRepository.findAll(specification, page).map(horarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HorarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Horario> specification = createSpecification(criteria);
        return horarioRepository.count(specification);
    }

    /**
     * Function to convert {@link HorarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Horario> createSpecification(HorarioCriteria criteria) {
        Specification<Horario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Horario_.id));
            }
            if (criteria.getChaveComposta1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChaveComposta1(), Horario_.chaveComposta1));
            }
            if (criteria.getChaveComposta2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChaveComposta2(), Horario_.chaveComposta2));
            }
            if (criteria.getDiaSemana() != null) {
                specification = specification.and(buildSpecification(criteria.getDiaSemana(), Horario_.diaSemana));
            }
            if (criteria.getHorarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHorarioId(), root -> root.join(Horario_.horarios, JoinType.LEFT).get(Horario_.id))
                    );
            }
            if (criteria.getLicaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLicaoId(), root -> root.join(Horario_.licaos, JoinType.LEFT).get(Licao_.id))
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(Horario_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUtilizadorId(), root -> root.join(Horario_.utilizador, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurmaId(), root -> root.join(Horario_.turma, JoinType.LEFT).get(Turma_.id))
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenciaId(),
                            root -> root.join(Horario_.referencia, JoinType.LEFT).get(Horario_.id)
                        )
                    );
            }
            if (criteria.getPeriodoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPeriodoId(),
                            root -> root.join(Horario_.periodo, JoinType.LEFT).get(PeriodoHorario_.id)
                        )
                    );
            }
            if (criteria.getDocenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDocenteId(), root -> root.join(Horario_.docente, JoinType.LEFT).get(Docente_.id))
                    );
            }
            if (criteria.getDisciplinaCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDisciplinaCurricularId(),
                            root -> root.join(Horario_.disciplinaCurricular, JoinType.LEFT).get(DisciplinaCurricular_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
