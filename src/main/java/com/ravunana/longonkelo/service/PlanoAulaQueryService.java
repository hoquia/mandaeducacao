package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.PlanoAula;
import com.ravunana.longonkelo.repository.PlanoAulaRepository;
import com.ravunana.longonkelo.service.criteria.PlanoAulaCriteria;
import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import com.ravunana.longonkelo.service.mapper.PlanoAulaMapper;
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
 * Service for executing complex queries for {@link PlanoAula} entities in the database.
 * The main input is a {@link PlanoAulaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PlanoAulaDTO} or a {@link Page} of {@link PlanoAulaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlanoAulaQueryService extends QueryService<PlanoAula> {

    private final Logger log = LoggerFactory.getLogger(PlanoAulaQueryService.class);

    private final PlanoAulaRepository planoAulaRepository;

    private final PlanoAulaMapper planoAulaMapper;

    public PlanoAulaQueryService(PlanoAulaRepository planoAulaRepository, PlanoAulaMapper planoAulaMapper) {
        this.planoAulaRepository = planoAulaRepository;
        this.planoAulaMapper = planoAulaMapper;
    }

    /**
     * Return a {@link List} of {@link PlanoAulaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PlanoAulaDTO> findByCriteria(PlanoAulaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PlanoAula> specification = createSpecification(criteria);
        return planoAulaMapper.toDto(planoAulaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PlanoAulaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlanoAulaDTO> findByCriteria(PlanoAulaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlanoAula> specification = createSpecification(criteria);
        return planoAulaRepository.findAll(specification, page).map(planoAulaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlanoAulaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlanoAula> specification = createSpecification(criteria);
        return planoAulaRepository.count(specification);
    }

    /**
     * Function to convert {@link PlanoAulaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlanoAula> createSpecification(PlanoAulaCriteria criteria) {
        Specification<PlanoAula> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PlanoAula_.id));
            }
            if (criteria.getTipoAula() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoAula(), PlanoAula_.tipoAula));
            }
            if (criteria.getSemanaLectiva() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSemanaLectiva(), PlanoAula_.semanaLectiva));
            }
            if (criteria.getAssunto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssunto(), PlanoAula_.assunto));
            }
            if (criteria.getTempoTotalLicao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTempoTotalLicao(), PlanoAula_.tempoTotalLicao));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), PlanoAula_.estado));
            }
            if (criteria.getDetalhesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDetalhesId(),
                            root -> root.join(PlanoAula_.detalhes, JoinType.LEFT).get(DetalhePlanoAula_.id)
                        )
                    );
            }
            if (criteria.getLicaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLicaoId(), root -> root.join(PlanoAula_.licaos, JoinType.LEFT).get(Licao_.id))
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(PlanoAula_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(PlanoAula_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getUnidadeTematicaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUnidadeTematicaId(),
                            root -> root.join(PlanoAula_.unidadeTematica, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getSubUnidadeTematicaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubUnidadeTematicaId(),
                            root -> root.join(PlanoAula_.subUnidadeTematica, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurmaId(), root -> root.join(PlanoAula_.turma, JoinType.LEFT).get(Turma_.id))
                    );
            }
            if (criteria.getDocenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDocenteId(), root -> root.join(PlanoAula_.docente, JoinType.LEFT).get(Docente_.id))
                    );
            }
            if (criteria.getDisciplinaCurricularId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDisciplinaCurricularId(),
                            root -> root.join(PlanoAula_.disciplinaCurricular, JoinType.LEFT).get(DisciplinaCurricular_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
