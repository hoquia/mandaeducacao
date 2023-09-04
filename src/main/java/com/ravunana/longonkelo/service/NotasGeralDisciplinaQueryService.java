package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.repository.NotasGeralDisciplinaRepository;
import com.ravunana.longonkelo.service.criteria.NotasGeralDisciplinaCriteria;
import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.NotasGeralDisciplinaMapper;
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
 * Service for executing complex queries for {@link NotasGeralDisciplina} entities in the database.
 * The main input is a {@link NotasGeralDisciplinaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotasGeralDisciplinaDTO} or a {@link Page} of {@link NotasGeralDisciplinaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotasGeralDisciplinaQueryService extends QueryService<NotasGeralDisciplina> {

    private final Logger log = LoggerFactory.getLogger(NotasGeralDisciplinaQueryService.class);

    private final NotasGeralDisciplinaRepository notasGeralDisciplinaRepository;

    private final NotasGeralDisciplinaMapper notasGeralDisciplinaMapper;

    public NotasGeralDisciplinaQueryService(
        NotasGeralDisciplinaRepository notasGeralDisciplinaRepository,
        NotasGeralDisciplinaMapper notasGeralDisciplinaMapper
    ) {
        this.notasGeralDisciplinaRepository = notasGeralDisciplinaRepository;
        this.notasGeralDisciplinaMapper = notasGeralDisciplinaMapper;
    }

    /**
     * Return a {@link List} of {@link NotasGeralDisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotasGeralDisciplinaDTO> findByCriteria(NotasGeralDisciplinaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotasGeralDisciplina> specification = createSpecification(criteria);
        return notasGeralDisciplinaMapper.toDto(notasGeralDisciplinaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NotasGeralDisciplinaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotasGeralDisciplinaDTO> findByCriteria(NotasGeralDisciplinaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotasGeralDisciplina> specification = createSpecification(criteria);
        return notasGeralDisciplinaRepository.findAll(specification, page).map(notasGeralDisciplinaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotasGeralDisciplinaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotasGeralDisciplina> specification = createSpecification(criteria);
        return notasGeralDisciplinaRepository.count(specification);
    }

    /**
     * Function to convert {@link NotasGeralDisciplinaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotasGeralDisciplina> createSpecification(NotasGeralDisciplinaCriteria criteria) {
        Specification<NotasGeralDisciplina> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotasGeralDisciplina_.id));
            }
            if (criteria.getChaveComposta() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getChaveComposta(), NotasGeralDisciplina_.chaveComposta));
            }
            if (criteria.getPeriodoLancamento() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPeriodoLancamento(), NotasGeralDisciplina_.periodoLancamento));
            }
            if (criteria.getMedia1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedia1(), NotasGeralDisciplina_.media1));
            }
            if (criteria.getMedia2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedia2(), NotasGeralDisciplina_.media2));
            }
            if (criteria.getMedia3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedia3(), NotasGeralDisciplina_.media3));
            }
            if (criteria.getExame() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExame(), NotasGeralDisciplina_.exame));
            }
            if (criteria.getRecurso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRecurso(), NotasGeralDisciplina_.recurso));
            }
            if (criteria.getExameEspecial() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExameEspecial(), NotasGeralDisciplina_.exameEspecial));
            }
            if (criteria.getNotaConselho() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotaConselho(), NotasGeralDisciplina_.notaConselho));
            }
            if (criteria.getMediaFinalDisciplina() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getMediaFinalDisciplina(), NotasGeralDisciplina_.mediaFinalDisciplina)
                    );
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), NotasGeralDisciplina_.timestamp));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), NotasGeralDisciplina_.hash));
            }
            if (criteria.getFaltaJusticada() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFaltaJusticada(), NotasGeralDisciplina_.faltaJusticada));
            }
            if (criteria.getFaltaInjustificada() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFaltaInjustificada(), NotasGeralDisciplina_.faltaInjustificada));
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(NotasGeralDisciplina_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(NotasGeralDisciplina_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getDocenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocenteId(),
                            root -> root.join(NotasGeralDisciplina_.docente, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
            if (criteria.getDisciplinaCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDisciplinaCurricularId(),
                            root -> root.join(NotasGeralDisciplina_.disciplinaCurricular, JoinType.LEFT).get(DisciplinaCurricular_.id)
                        )
                    );
            }
            if (criteria.getMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculaId(),
                            root -> root.join(NotasGeralDisciplina_.matricula, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getEstadoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstadoId(),
                            root -> root.join(NotasGeralDisciplina_.estado, JoinType.LEFT).get(EstadoDisciplinaCurricular_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
