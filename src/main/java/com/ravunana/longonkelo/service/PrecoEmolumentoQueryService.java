package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.PrecoEmolumento;
import com.ravunana.longonkelo.repository.PrecoEmolumentoRepository;
import com.ravunana.longonkelo.service.criteria.PrecoEmolumentoCriteria;
import com.ravunana.longonkelo.service.dto.PrecoEmolumentoDTO;
import com.ravunana.longonkelo.service.mapper.PrecoEmolumentoMapper;
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
 * Service for executing complex queries for {@link PrecoEmolumento} entities in the database.
 * The main input is a {@link PrecoEmolumentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PrecoEmolumentoDTO} or a {@link Page} of {@link PrecoEmolumentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrecoEmolumentoQueryService extends QueryService<PrecoEmolumento> {

    private final Logger log = LoggerFactory.getLogger(PrecoEmolumentoQueryService.class);

    private final PrecoEmolumentoRepository precoEmolumentoRepository;

    private final PrecoEmolumentoMapper precoEmolumentoMapper;

    public PrecoEmolumentoQueryService(PrecoEmolumentoRepository precoEmolumentoRepository, PrecoEmolumentoMapper precoEmolumentoMapper) {
        this.precoEmolumentoRepository = precoEmolumentoRepository;
        this.precoEmolumentoMapper = precoEmolumentoMapper;
    }

    /**
     * Return a {@link List} of {@link PrecoEmolumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PrecoEmolumentoDTO> findByCriteria(PrecoEmolumentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PrecoEmolumento> specification = createSpecification(criteria);
        return precoEmolumentoMapper.toDto(precoEmolumentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PrecoEmolumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrecoEmolumentoDTO> findByCriteria(PrecoEmolumentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrecoEmolumento> specification = createSpecification(criteria);
        return precoEmolumentoRepository.findAll(specification, page).map(precoEmolumentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrecoEmolumentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PrecoEmolumento> specification = createSpecification(criteria);
        return precoEmolumentoRepository.count(specification);
    }

    /**
     * Function to convert {@link PrecoEmolumentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PrecoEmolumento> createSpecification(PrecoEmolumentoCriteria criteria) {
        Specification<PrecoEmolumento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PrecoEmolumento_.id));
            }
            if (criteria.getPreco() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreco(), PrecoEmolumento_.preco));
            }
            if (criteria.getIsEspecificoCurso() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEspecificoCurso(), PrecoEmolumento_.isEspecificoCurso));
            }
            if (criteria.getIsEspecificoAreaFormacao() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getIsEspecificoAreaFormacao(), PrecoEmolumento_.isEspecificoAreaFormacao)
                    );
            }
            if (criteria.getIsEspecificoClasse() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsEspecificoClasse(), PrecoEmolumento_.isEspecificoClasse));
            }
            if (criteria.getIsEspecificoTurno() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEspecificoTurno(), PrecoEmolumento_.isEspecificoTurno));
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(PrecoEmolumento_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getEmolumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmolumentoId(),
                            root -> root.join(PrecoEmolumento_.emolumento, JoinType.LEFT).get(Emolumento_.id)
                        )
                    );
            }
            if (criteria.getAreaFormacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAreaFormacaoId(),
                            root -> root.join(PrecoEmolumento_.areaFormacao, JoinType.LEFT).get(AreaFormacao_.id)
                        )
                    );
            }
            if (criteria.getCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCursoId(), root -> root.join(PrecoEmolumento_.curso, JoinType.LEFT).get(Curso_.id))
                    );
            }
            if (criteria.getClasseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClasseId(),
                            root -> root.join(PrecoEmolumento_.classe, JoinType.LEFT).get(Classe_.id)
                        )
                    );
            }
            if (criteria.getTurnoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurnoId(), root -> root.join(PrecoEmolumento_.turno, JoinType.LEFT).get(Turno_.id))
                    );
            }
            if (criteria.getPlanoMultaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanoMultaId(),
                            root -> root.join(PrecoEmolumento_.planoMulta, JoinType.LEFT).get(PlanoMulta_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
