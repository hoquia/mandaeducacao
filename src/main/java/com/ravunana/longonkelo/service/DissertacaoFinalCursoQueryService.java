package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.repository.DissertacaoFinalCursoRepository;
import com.ravunana.longonkelo.service.criteria.DissertacaoFinalCursoCriteria;
import com.ravunana.longonkelo.service.dto.DissertacaoFinalCursoDTO;
import com.ravunana.longonkelo.service.mapper.DissertacaoFinalCursoMapper;
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
 * Service for executing complex queries for {@link DissertacaoFinalCurso} entities in the database.
 * The main input is a {@link DissertacaoFinalCursoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DissertacaoFinalCursoDTO} or a {@link Page} of {@link DissertacaoFinalCursoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DissertacaoFinalCursoQueryService extends QueryService<DissertacaoFinalCurso> {

    private final Logger log = LoggerFactory.getLogger(DissertacaoFinalCursoQueryService.class);

    private final DissertacaoFinalCursoRepository dissertacaoFinalCursoRepository;

    private final DissertacaoFinalCursoMapper dissertacaoFinalCursoMapper;

    public DissertacaoFinalCursoQueryService(
        DissertacaoFinalCursoRepository dissertacaoFinalCursoRepository,
        DissertacaoFinalCursoMapper dissertacaoFinalCursoMapper
    ) {
        this.dissertacaoFinalCursoRepository = dissertacaoFinalCursoRepository;
        this.dissertacaoFinalCursoMapper = dissertacaoFinalCursoMapper;
    }

    /**
     * Return a {@link List} of {@link DissertacaoFinalCursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DissertacaoFinalCursoDTO> findByCriteria(DissertacaoFinalCursoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DissertacaoFinalCurso> specification = createSpecification(criteria);
        return dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCursoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DissertacaoFinalCursoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DissertacaoFinalCursoDTO> findByCriteria(DissertacaoFinalCursoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DissertacaoFinalCurso> specification = createSpecification(criteria);
        return dissertacaoFinalCursoRepository.findAll(specification, page).map(dissertacaoFinalCursoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DissertacaoFinalCursoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DissertacaoFinalCurso> specification = createSpecification(criteria);
        return dissertacaoFinalCursoRepository.count(specification);
    }

    /**
     * Function to convert {@link DissertacaoFinalCursoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DissertacaoFinalCurso> createSpecification(DissertacaoFinalCursoCriteria criteria) {
        Specification<DissertacaoFinalCurso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DissertacaoFinalCurso_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), DissertacaoFinalCurso_.numero));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), DissertacaoFinalCurso_.timestamp));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), DissertacaoFinalCurso_.data));
            }
            if (criteria.getTema() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTema(), DissertacaoFinalCurso_.tema));
            }
            if (criteria.getObjectivoGeral() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getObjectivoGeral(), DissertacaoFinalCurso_.objectivoGeral));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), DissertacaoFinalCurso_.hash));
            }
            if (criteria.getIsAceiteTermosCompromisso() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getIsAceiteTermosCompromisso(), DissertacaoFinalCurso_.isAceiteTermosCompromisso)
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(DissertacaoFinalCurso_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(DissertacaoFinalCurso_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTurmaId(),
                            root -> root.join(DissertacaoFinalCurso_.turma, JoinType.LEFT).get(Turma_.id)
                        )
                    );
            }
            if (criteria.getOrientadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrientadorId(),
                            root -> root.join(DissertacaoFinalCurso_.orientador, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
            if (criteria.getEspecialidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEspecialidadeId(),
                            root -> root.join(DissertacaoFinalCurso_.especialidade, JoinType.LEFT).get(AreaFormacao_.id)
                        )
                    );
            }
            if (criteria.getDiscenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDiscenteId(),
                            root -> root.join(DissertacaoFinalCurso_.discente, JoinType.LEFT).get(Discente_.id)
                        )
                    );
            }
            if (criteria.getEstadoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstadoId(),
                            root -> root.join(DissertacaoFinalCurso_.estado, JoinType.LEFT).get(EstadoDissertacao_.id)
                        )
                    );
            }
            if (criteria.getNaturezaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNaturezaId(),
                            root -> root.join(DissertacaoFinalCurso_.natureza, JoinType.LEFT).get(NaturezaTrabalho_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
