package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.repository.NotasPeriodicaDisciplinaRepository;
import com.ravunana.longonkelo.service.criteria.NotasPeriodicaDisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.NotasPeriodicaDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.NotasPeriodicaDisciplinaMapper;
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
 * Service for executing complex queries for {@link NotasPeriodicaDisciplina} entities in the database.
 * The main input is a {@link NotasPeriodicaDisciplinaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotasPeriodicaDisciplinaDTO} or a {@link Page} of {@link NotasPeriodicaDisciplinaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotasPeriodicaDisciplinaQueryService extends QueryService<NotasPeriodicaDisciplina> {

    private final Logger log = LoggerFactory.getLogger(NotasPeriodicaDisciplinaQueryService.class);

    private final NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository;

    private final NotasPeriodicaDisciplinaMapper notasPeriodicaDisciplinaMapper;

    public NotasPeriodicaDisciplinaQueryService(
        NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository,
        NotasPeriodicaDisciplinaMapper notasPeriodicaDisciplinaMapper
    ) {
        this.notasPeriodicaDisciplinaRepository = notasPeriodicaDisciplinaRepository;
        this.notasPeriodicaDisciplinaMapper = notasPeriodicaDisciplinaMapper;
    }

    /**
     * Return a {@link List} of {@link NotasPeriodicaDisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotasPeriodicaDisciplinaDTO> findByCriteria(NotasPeriodicaDisciplinaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotasPeriodicaDisciplina> specification = createSpecification(criteria);
        return notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplinaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NotasPeriodicaDisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotasPeriodicaDisciplinaDTO> findByCriteria(NotasPeriodicaDisciplinaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotasPeriodicaDisciplina> specification = createSpecification(criteria);
        return notasPeriodicaDisciplinaRepository.findAll(specification, page).map(notasPeriodicaDisciplinaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotasPeriodicaDisciplinaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotasPeriodicaDisciplina> specification = createSpecification(criteria);
        return notasPeriodicaDisciplinaRepository.count(specification);
    }

    /**
     * Function to convert {@link NotasPeriodicaDisciplinaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotasPeriodicaDisciplina> createSpecification(NotasPeriodicaDisciplinaCriteria criteria) {
        Specification<NotasPeriodicaDisciplina> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotasPeriodicaDisciplina_.id));
            }
            if (criteria.getChaveComposta() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getChaveComposta(), NotasPeriodicaDisciplina_.chaveComposta));
            }
            if (criteria.getPeriodoLancamento() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getPeriodoLancamento(), NotasPeriodicaDisciplina_.periodoLancamento)
                    );
            }
            if (criteria.getNota1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNota1(), NotasPeriodicaDisciplina_.nota1));
            }
            if (criteria.getNota2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNota2(), NotasPeriodicaDisciplina_.nota2));
            }
            if (criteria.getNota3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNota3(), NotasPeriodicaDisciplina_.nota3));
            }
            if (criteria.getMedia() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedia(), NotasPeriodicaDisciplina_.media));
            }
            if (criteria.getFaltaJusticada() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFaltaJusticada(), NotasPeriodicaDisciplina_.faltaJusticada));
            }
            if (criteria.getFaltaInjustificada() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFaltaInjustificada(), NotasPeriodicaDisciplina_.faltaInjustificada)
                    );
            }
            if (criteria.getComportamento() != null) {
                specification = specification.and(buildSpecification(criteria.getComportamento(), NotasPeriodicaDisciplina_.comportamento));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), NotasPeriodicaDisciplina_.hash));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), NotasPeriodicaDisciplina_.timestamp));
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(NotasPeriodicaDisciplina_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(NotasPeriodicaDisciplina_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTurmaId(),
                            root -> root.join(NotasPeriodicaDisciplina_.turma, JoinType.LEFT).get(Turma_.id)
                        )
                    );
            }
            if (criteria.getDocenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocenteId(),
                            root -> root.join(NotasPeriodicaDisciplina_.docente, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
            if (criteria.getDisciplinaCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDisciplinaCurricularId(),
                            root -> root.join(NotasPeriodicaDisciplina_.disciplinaCurricular, JoinType.LEFT).get(DisciplinaCurricular_.id)
                        )
                    );
            }
            if (criteria.getMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculaId(),
                            root -> root.join(NotasPeriodicaDisciplina_.matricula, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getEstadoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstadoId(),
                            root -> root.join(NotasPeriodicaDisciplina_.estado, JoinType.LEFT).get(EstadoDisciplinaCurricular_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
