package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Turma;
import com.ravunana.longonkelo.repository.TurmaRepository;
import com.ravunana.longonkelo.service.criteria.TurmaCriteria;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.mapper.TurmaMapper;
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
 * Service for executing complex queries for {@link Turma} entities in the database.
 * The main input is a {@link TurmaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TurmaDTO} or a {@link Page} of {@link TurmaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TurmaQueryService extends QueryService<Turma> {

    private final Logger log = LoggerFactory.getLogger(TurmaQueryService.class);

    private final TurmaRepository turmaRepository;

    private final TurmaMapper turmaMapper;

    public TurmaQueryService(TurmaRepository turmaRepository, TurmaMapper turmaMapper) {
        this.turmaRepository = turmaRepository;
        this.turmaMapper = turmaMapper;
    }

    /**
     * Return a {@link List} of {@link TurmaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TurmaDTO> findByCriteria(TurmaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Turma> specification = createSpecification(criteria);
        return turmaMapper.toDto(turmaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TurmaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TurmaDTO> findByCriteria(TurmaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Turma> specification = createSpecification(criteria);
        return turmaRepository.findAll(specification, page).map(turmaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TurmaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Turma> specification = createSpecification(criteria);
        return turmaRepository.count(specification);
    }

    /**
     * Function to convert {@link TurmaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Turma> createSpecification(TurmaCriteria criteria) {
        Specification<Turma> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Turma_.id));
            }
            if (criteria.getChaveComposta() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChaveComposta(), Turma_.chaveComposta));
            }
            if (criteria.getTipoTurma() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoTurma(), Turma_.tipoTurma));
            }
            if (criteria.getSala() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSala(), Turma_.sala));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Turma_.descricao));
            }
            if (criteria.getLotacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLotacao(), Turma_.lotacao));
            }
            if (criteria.getConfirmado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConfirmado(), Turma_.confirmado));
            }
            if (criteria.getAbertura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAbertura(), Turma_.abertura));
            }
            if (criteria.getEncerramento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEncerramento(), Turma_.encerramento));
            }
            if (criteria.getCriterioDescricao() != null) {
                specification = specification.and(buildSpecification(criteria.getCriterioDescricao(), Turma_.criterioDescricao));
            }
            if (criteria.getCriterioOrdenacaoNumero() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getCriterioOrdenacaoNumero(), Turma_.criterioOrdenacaoNumero));
            }
            if (criteria.getFazInscricaoDepoisMatricula() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getFazInscricaoDepoisMatricula(), Turma_.fazInscricaoDepoisMatricula));
            }
            if (criteria.getIsDisponivel() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDisponivel(), Turma_.isDisponivel));
            }
            if (criteria.getTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurmaId(), root -> root.join(Turma_.turmas, JoinType.LEFT).get(Turma_.id))
                    );
            }
            if (criteria.getHorariosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHorariosId(), root -> root.join(Turma_.horarios, JoinType.LEFT).get(Horario_.id))
                    );
            }
            if (criteria.getNotasPeriodicaDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotasPeriodicaDisciplinaId(),
                            root -> root.join(Turma_.notasPeriodicaDisciplinas, JoinType.LEFT).get(NotasPeriodicaDisciplina_.id)
                        )
                    );
            }
            if (criteria.getProcessoSelectivoMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoSelectivoMatriculaId(),
                            root -> root.join(Turma_.processoSelectivoMatriculas, JoinType.LEFT).get(ProcessoSelectivoMatricula_.id)
                        )
                    );
            }
            if (criteria.getPlanoAulaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanoAulaId(),
                            root -> root.join(Turma_.planoAulas, JoinType.LEFT).get(PlanoAula_.id)
                        )
                    );
            }
            if (criteria.getMatriculasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculasId(),
                            root -> root.join(Turma_.matriculas, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getResumoAcademicoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResumoAcademicoId(),
                            root -> root.join(Turma_.resumoAcademicos, JoinType.LEFT).get(ResumoAcademico_.id)
                        )
                    );
            }
            if (criteria.getResponsaveisId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsaveisId(),
                            root -> root.join(Turma_.responsaveis, JoinType.LEFT).get(ResponsavelTurma_.id)
                        )
                    );
            }
            if (criteria.getDissertacaoFinalCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDissertacaoFinalCursoId(),
                            root -> root.join(Turma_.dissertacaoFinalCursos, JoinType.LEFT).get(DissertacaoFinalCurso_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(Turma_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUtilizadorId(), root -> root.join(Turma_.utilizador, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getReferenciaId(), root -> root.join(Turma_.referencia, JoinType.LEFT).get(Turma_.id))
                    );
            }
            if (criteria.getPlanoCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanoCurricularId(),
                            root -> root.join(Turma_.planoCurricular, JoinType.LEFT).get(PlanoCurricular_.id)
                        )
                    );
            }
            if (criteria.getTurnoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurnoId(), root -> root.join(Turma_.turno, JoinType.LEFT).get(Turno_.id))
                    );
            }
        }
        return specification;
    }
}
