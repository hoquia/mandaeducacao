package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import com.ravunana.longonkelo.repository.CategoriaOcorrenciaRepository;
import com.ravunana.longonkelo.service.criteria.CategoriaOcorrenciaCriteria;
import com.ravunana.longonkelo.service.dto.CategoriaOcorrenciaDTO;
import com.ravunana.longonkelo.service.mapper.CategoriaOcorrenciaMapper;
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
 * Service for executing complex queries for {@link CategoriaOcorrencia} entities in the database.
 * The main input is a {@link CategoriaOcorrenciaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaOcorrenciaDTO} or a {@link Page} of {@link CategoriaOcorrenciaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaOcorrenciaQueryService extends QueryService<CategoriaOcorrencia> {

    private final Logger log = LoggerFactory.getLogger(CategoriaOcorrenciaQueryService.class);

    private final CategoriaOcorrenciaRepository categoriaOcorrenciaRepository;

    private final CategoriaOcorrenciaMapper categoriaOcorrenciaMapper;

    public CategoriaOcorrenciaQueryService(
        CategoriaOcorrenciaRepository categoriaOcorrenciaRepository,
        CategoriaOcorrenciaMapper categoriaOcorrenciaMapper
    ) {
        this.categoriaOcorrenciaRepository = categoriaOcorrenciaRepository;
        this.categoriaOcorrenciaMapper = categoriaOcorrenciaMapper;
    }

    /**
     * Return a {@link List} of {@link CategoriaOcorrenciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaOcorrenciaDTO> findByCriteria(CategoriaOcorrenciaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoriaOcorrencia> specification = createSpecification(criteria);
        return categoriaOcorrenciaMapper.toDto(categoriaOcorrenciaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoriaOcorrenciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaOcorrenciaDTO> findByCriteria(CategoriaOcorrenciaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoriaOcorrencia> specification = createSpecification(criteria);
        return categoriaOcorrenciaRepository.findAll(specification, page).map(categoriaOcorrenciaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriaOcorrenciaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoriaOcorrencia> specification = createSpecification(criteria);
        return categoriaOcorrenciaRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriaOcorrenciaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategoriaOcorrencia> createSpecification(CategoriaOcorrenciaCriteria criteria) {
        Specification<CategoriaOcorrencia> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategoriaOcorrencia_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), CategoriaOcorrencia_.codigo));
            }
            if (criteria.getSansaoDisicplinar() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSansaoDisicplinar(), CategoriaOcorrencia_.sansaoDisicplinar));
            }
            if (criteria.getIsNotificaEncaregado() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsNotificaEncaregado(), CategoriaOcorrencia_.isNotificaEncaregado));
            }
            if (criteria.getIsSendEmail() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSendEmail(), CategoriaOcorrencia_.isSendEmail));
            }
            if (criteria.getIsSendSms() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSendSms(), CategoriaOcorrencia_.isSendSms));
            }
            if (criteria.getIsSendPush() != null) {
                specification = specification.and(buildSpecification(criteria.getIsSendPush(), CategoriaOcorrencia_.isSendPush));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), CategoriaOcorrencia_.descricao));
            }
            if (criteria.getCategoriaOcorrenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriaOcorrenciaId(),
                            root -> root.join(CategoriaOcorrencia_.categoriaOcorrencias, JoinType.LEFT).get(CategoriaOcorrencia_.id)
                        )
                    );
            }
            if (criteria.getOcorrenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOcorrenciaId(),
                            root -> root.join(CategoriaOcorrencia_.ocorrencias, JoinType.LEFT).get(Ocorrencia_.id)
                        )
                    );
            }
            if (criteria.getEncaminharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEncaminharId(),
                            root -> root.join(CategoriaOcorrencia_.encaminhar, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenciaId(),
                            root -> root.join(CategoriaOcorrencia_.referencia, JoinType.LEFT).get(CategoriaOcorrencia_.id)
                        )
                    );
            }
            if (criteria.getMedidaDisciplinarId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMedidaDisciplinarId(),
                            root -> root.join(CategoriaOcorrencia_.medidaDisciplinar, JoinType.LEFT).get(MedidaDisciplinar_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
