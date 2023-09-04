package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Ocorrencia;
import com.ravunana.longonkelo.repository.OcorrenciaRepository;
import com.ravunana.longonkelo.service.criteria.OcorrenciaCriteria;
import com.ravunana.longonkelo.service.dto.OcorrenciaDTO;
import com.ravunana.longonkelo.service.mapper.OcorrenciaMapper;
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
 * Service for executing complex queries for {@link Ocorrencia} entities in the database.
 * The main input is a {@link OcorrenciaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OcorrenciaDTO} or a {@link Page} of {@link OcorrenciaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OcorrenciaQueryService extends QueryService<Ocorrencia> {

    private final Logger log = LoggerFactory.getLogger(OcorrenciaQueryService.class);

    private final OcorrenciaRepository ocorrenciaRepository;

    private final OcorrenciaMapper ocorrenciaMapper;

    public OcorrenciaQueryService(OcorrenciaRepository ocorrenciaRepository, OcorrenciaMapper ocorrenciaMapper) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ocorrenciaMapper = ocorrenciaMapper;
    }

    /**
     * Return a {@link List} of {@link OcorrenciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OcorrenciaDTO> findByCriteria(OcorrenciaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ocorrencia> specification = createSpecification(criteria);
        return ocorrenciaMapper.toDto(ocorrenciaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OcorrenciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OcorrenciaDTO> findByCriteria(OcorrenciaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ocorrencia> specification = createSpecification(criteria);
        return ocorrenciaRepository.findAll(specification, page).map(ocorrenciaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OcorrenciaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ocorrencia> specification = createSpecification(criteria);
        return ocorrenciaRepository.count(specification);
    }

    /**
     * Function to convert {@link OcorrenciaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ocorrencia> createSpecification(OcorrenciaCriteria criteria) {
        Specification<Ocorrencia> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ocorrencia_.id));
            }
            if (criteria.getUniqueOcorrencia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUniqueOcorrencia(), Ocorrencia_.uniqueOcorrencia));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), Ocorrencia_.hash));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), Ocorrencia_.timestamp));
            }
            if (criteria.getOcorrenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOcorrenciaId(),
                            root -> root.join(Ocorrencia_.ocorrencias, JoinType.LEFT).get(Ocorrencia_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(Ocorrencia_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(Ocorrencia_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenciaId(),
                            root -> root.join(Ocorrencia_.referencia, JoinType.LEFT).get(Ocorrencia_.id)
                        )
                    );
            }
            if (criteria.getDocenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDocenteId(), root -> root.join(Ocorrencia_.docente, JoinType.LEFT).get(Docente_.id))
                    );
            }
            if (criteria.getMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculaId(),
                            root -> root.join(Ocorrencia_.matricula, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getEstadoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstadoId(),
                            root -> root.join(Ocorrencia_.estado, JoinType.LEFT).get(CategoriaOcorrencia_.id)
                        )
                    );
            }
            if (criteria.getLicaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLicaoId(), root -> root.join(Ocorrencia_.licao, JoinType.LEFT).get(Licao_.id))
                    );
            }
        }
        return specification;
    }
}
