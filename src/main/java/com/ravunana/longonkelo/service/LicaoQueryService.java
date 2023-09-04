package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.repository.LicaoRepository;
import com.ravunana.longonkelo.service.criteria.LicaoCriteria;
import com.ravunana.longonkelo.service.dto.LicaoDTO;
import com.ravunana.longonkelo.service.mapper.LicaoMapper;
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
 * Service for executing complex queries for {@link Licao} entities in the database.
 * The main input is a {@link LicaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LicaoDTO} or a {@link Page} of {@link LicaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LicaoQueryService extends QueryService<Licao> {

    private final Logger log = LoggerFactory.getLogger(LicaoQueryService.class);

    private final LicaoRepository licaoRepository;

    private final LicaoMapper licaoMapper;

    public LicaoQueryService(LicaoRepository licaoRepository, LicaoMapper licaoMapper) {
        this.licaoRepository = licaoRepository;
        this.licaoMapper = licaoMapper;
    }

    /**
     * Return a {@link List} of {@link LicaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LicaoDTO> findByCriteria(LicaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Licao> specification = createSpecification(criteria);
        return licaoMapper.toDto(licaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LicaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LicaoDTO> findByCriteria(LicaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Licao> specification = createSpecification(criteria);
        return licaoRepository.findAll(specification, page).map(licaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LicaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Licao> specification = createSpecification(criteria);
        return licaoRepository.count(specification);
    }

    /**
     * Function to convert {@link LicaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Licao> createSpecification(LicaoCriteria criteria) {
        Specification<Licao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Licao_.id));
            }
            if (criteria.getChaveComposta() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChaveComposta(), Licao_.chaveComposta));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumero(), Licao_.numero));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), Licao_.estado));
            }
            if (criteria.getOcorrenciasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOcorrenciasId(),
                            root -> root.join(Licao_.ocorrencias, JoinType.LEFT).get(Ocorrencia_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivoId(),
                            root -> root.join(Licao_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUtilizadorId(), root -> root.join(Licao_.utilizador, JoinType.LEFT).get(User_.id))
                    );
            }
            if (criteria.getPlanoAulaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPlanoAulaId(), root -> root.join(Licao_.planoAula, JoinType.LEFT).get(PlanoAula_.id))
                    );
            }
            if (criteria.getHorarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHorarioId(), root -> root.join(Licao_.horario, JoinType.LEFT).get(Horario_.id))
                    );
            }
        }
        return specification;
    }
}
