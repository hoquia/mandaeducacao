package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula;
import com.ravunana.longonkelo.repository.ProcessoSelectivoMatriculaRepository;
import com.ravunana.longonkelo.service.criteria.ProcessoSelectivoMatriculaCriteria;
import com.ravunana.longonkelo.service.dto.ProcessoSelectivoMatriculaDTO;
import com.ravunana.longonkelo.service.mapper.ProcessoSelectivoMatriculaMapper;
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
 * Service for executing complex queries for {@link ProcessoSelectivoMatricula} entities in the database.
 * The main input is a {@link ProcessoSelectivoMatriculaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcessoSelectivoMatriculaDTO} or a {@link Page} of {@link ProcessoSelectivoMatriculaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessoSelectivoMatriculaQueryService extends QueryService<ProcessoSelectivoMatricula> {

    private final Logger log = LoggerFactory.getLogger(ProcessoSelectivoMatriculaQueryService.class);

    private final ProcessoSelectivoMatriculaRepository processoSelectivoMatriculaRepository;

    private final ProcessoSelectivoMatriculaMapper processoSelectivoMatriculaMapper;

    public ProcessoSelectivoMatriculaQueryService(
        ProcessoSelectivoMatriculaRepository processoSelectivoMatriculaRepository,
        ProcessoSelectivoMatriculaMapper processoSelectivoMatriculaMapper
    ) {
        this.processoSelectivoMatriculaRepository = processoSelectivoMatriculaRepository;
        this.processoSelectivoMatriculaMapper = processoSelectivoMatriculaMapper;
    }

    /**
     * Return a {@link List} of {@link ProcessoSelectivoMatriculaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessoSelectivoMatriculaDTO> findByCriteria(ProcessoSelectivoMatriculaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProcessoSelectivoMatricula> specification = createSpecification(criteria);
        return processoSelectivoMatriculaMapper.toDto(processoSelectivoMatriculaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProcessoSelectivoMatriculaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessoSelectivoMatriculaDTO> findByCriteria(ProcessoSelectivoMatriculaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProcessoSelectivoMatricula> specification = createSpecification(criteria);
        return processoSelectivoMatriculaRepository.findAll(specification, page).map(processoSelectivoMatriculaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessoSelectivoMatriculaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProcessoSelectivoMatricula> specification = createSpecification(criteria);
        return processoSelectivoMatriculaRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessoSelectivoMatriculaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProcessoSelectivoMatricula> createSpecification(ProcessoSelectivoMatriculaCriteria criteria) {
        Specification<ProcessoSelectivoMatricula> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProcessoSelectivoMatricula_.id));
            }
            if (criteria.getLocalTeste() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLocalTeste(), ProcessoSelectivoMatricula_.localTeste));
            }
            if (criteria.getDataTeste() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataTeste(), ProcessoSelectivoMatricula_.dataTeste));
            }
            if (criteria.getNotaTeste() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotaTeste(), ProcessoSelectivoMatricula_.notaTeste));
            }
            if (criteria.getIsAdmitido() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAdmitido(), ProcessoSelectivoMatricula_.isAdmitido));
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(ProcessoSelectivoMatricula_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(ProcessoSelectivoMatricula_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTurmaId(),
                            root -> root.join(ProcessoSelectivoMatricula_.turma, JoinType.LEFT).get(Turma_.id)
                        )
                    );
            }
            if (criteria.getDiscenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDiscenteId(),
                            root -> root.join(ProcessoSelectivoMatricula_.discente, JoinType.LEFT).get(Discente_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
