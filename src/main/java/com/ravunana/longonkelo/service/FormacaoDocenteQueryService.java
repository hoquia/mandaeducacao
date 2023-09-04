package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.FormacaoDocente;
import com.ravunana.longonkelo.repository.FormacaoDocenteRepository;
import com.ravunana.longonkelo.service.criteria.FormacaoDocenteCriteria;
import com.ravunana.longonkelo.service.dto.FormacaoDocenteDTO;
import com.ravunana.longonkelo.service.mapper.FormacaoDocenteMapper;
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
 * Service for executing complex queries for {@link FormacaoDocente} entities in the database.
 * The main input is a {@link FormacaoDocenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FormacaoDocenteDTO} or a {@link Page} of {@link FormacaoDocenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FormacaoDocenteQueryService extends QueryService<FormacaoDocente> {

    private final Logger log = LoggerFactory.getLogger(FormacaoDocenteQueryService.class);

    private final FormacaoDocenteRepository formacaoDocenteRepository;

    private final FormacaoDocenteMapper formacaoDocenteMapper;

    public FormacaoDocenteQueryService(FormacaoDocenteRepository formacaoDocenteRepository, FormacaoDocenteMapper formacaoDocenteMapper) {
        this.formacaoDocenteRepository = formacaoDocenteRepository;
        this.formacaoDocenteMapper = formacaoDocenteMapper;
    }

    /**
     * Return a {@link List} of {@link FormacaoDocenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FormacaoDocenteDTO> findByCriteria(FormacaoDocenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FormacaoDocente> specification = createSpecification(criteria);
        return formacaoDocenteMapper.toDto(formacaoDocenteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FormacaoDocenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FormacaoDocenteDTO> findByCriteria(FormacaoDocenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FormacaoDocente> specification = createSpecification(criteria);
        return formacaoDocenteRepository.findAll(specification, page).map(formacaoDocenteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FormacaoDocenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FormacaoDocente> specification = createSpecification(criteria);
        return formacaoDocenteRepository.count(specification);
    }

    /**
     * Function to convert {@link FormacaoDocenteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FormacaoDocente> createSpecification(FormacaoDocenteCriteria criteria) {
        Specification<FormacaoDocente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FormacaoDocente_.id));
            }
            if (criteria.getInstituicaoEnsino() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getInstituicaoEnsino(), FormacaoDocente_.instituicaoEnsino));
            }
            if (criteria.getAreaFormacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaFormacao(), FormacaoDocente_.areaFormacao));
            }
            if (criteria.getCurso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurso(), FormacaoDocente_.curso));
            }
            if (criteria.getEspecialidade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEspecialidade(), FormacaoDocente_.especialidade));
            }
            if (criteria.getGrau() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrau(), FormacaoDocente_.grau));
            }
            if (criteria.getInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInicio(), FormacaoDocente_.inicio));
            }
            if (criteria.getFim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFim(), FormacaoDocente_.fim));
            }
            if (criteria.getGrauAcademicoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGrauAcademicoId(),
                            root -> root.join(FormacaoDocente_.grauAcademico, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getDocenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDocenteId(),
                            root -> root.join(FormacaoDocente_.docente, JoinType.LEFT).get(Docente_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
