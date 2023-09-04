package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.ResumoAcademico;
import com.ravunana.longonkelo.repository.ResumoAcademicoRepository;
import com.ravunana.longonkelo.service.criteria.ResumoAcademicoCriteria;
import com.ravunana.longonkelo.service.dto.ResumoAcademicoDTO;
import com.ravunana.longonkelo.service.mapper.ResumoAcademicoMapper;
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
 * Service for executing complex queries for {@link ResumoAcademico} entities in the database.
 * The main input is a {@link ResumoAcademicoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResumoAcademicoDTO} or a {@link Page} of {@link ResumoAcademicoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResumoAcademicoQueryService extends QueryService<ResumoAcademico> {

    private final Logger log = LoggerFactory.getLogger(ResumoAcademicoQueryService.class);

    private final ResumoAcademicoRepository resumoAcademicoRepository;

    private final ResumoAcademicoMapper resumoAcademicoMapper;

    public ResumoAcademicoQueryService(ResumoAcademicoRepository resumoAcademicoRepository, ResumoAcademicoMapper resumoAcademicoMapper) {
        this.resumoAcademicoRepository = resumoAcademicoRepository;
        this.resumoAcademicoMapper = resumoAcademicoMapper;
    }

    /**
     * Return a {@link List} of {@link ResumoAcademicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResumoAcademicoDTO> findByCriteria(ResumoAcademicoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResumoAcademico> specification = createSpecification(criteria);
        return resumoAcademicoMapper.toDto(resumoAcademicoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResumoAcademicoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResumoAcademicoDTO> findByCriteria(ResumoAcademicoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResumoAcademico> specification = createSpecification(criteria);
        return resumoAcademicoRepository.findAll(specification, page).map(resumoAcademicoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResumoAcademicoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResumoAcademico> specification = createSpecification(criteria);
        return resumoAcademicoRepository.count(specification);
    }

    /**
     * Function to convert {@link ResumoAcademicoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResumoAcademico> createSpecification(ResumoAcademicoCriteria criteria) {
        Specification<ResumoAcademico> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResumoAcademico_.id));
            }
            if (criteria.getTemaProjecto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemaProjecto(), ResumoAcademico_.temaProjecto));
            }
            if (criteria.getNotaProjecto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotaProjecto(), ResumoAcademico_.notaProjecto));
            }
            if (criteria.getLocalEstagio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalEstagio(), ResumoAcademico_.localEstagio));
            }
            if (criteria.getNotaEstagio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotaEstagio(), ResumoAcademico_.notaEstagio));
            }
            if (criteria.getMediaFinalDisciplina() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMediaFinalDisciplina(), ResumoAcademico_.mediaFinalDisciplina));
            }
            if (criteria.getClassificacaoFinal() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getClassificacaoFinal(), ResumoAcademico_.classificacaoFinal));
            }
            if (criteria.getNumeroGrupo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroGrupo(), ResumoAcademico_.numeroGrupo));
            }
            if (criteria.getMesaDefesa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMesaDefesa(), ResumoAcademico_.mesaDefesa));
            }
            if (criteria.getLivroRegistro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLivroRegistro(), ResumoAcademico_.livroRegistro));
            }
            if (criteria.getNumeroFolha() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroFolha(), ResumoAcademico_.numeroFolha));
            }
            if (criteria.getChefeSecretariaPedagogica() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getChefeSecretariaPedagogica(), ResumoAcademico_.chefeSecretariaPedagogica)
                    );
            }
            if (criteria.getSubDirectorPedagogico() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSubDirectorPedagogico(), ResumoAcademico_.subDirectorPedagogico)
                    );
            }
            if (criteria.getDirectorGeral() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDirectorGeral(), ResumoAcademico_.directorGeral));
            }
            if (criteria.getTutorProjecto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTutorProjecto(), ResumoAcademico_.tutorProjecto));
            }
            if (criteria.getJuriMesa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJuriMesa(), ResumoAcademico_.juriMesa));
            }
            if (criteria.getEmpresaEstagio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaEstagio(), ResumoAcademico_.empresaEstagio));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), ResumoAcademico_.hash));
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(ResumoAcademico_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getUltimaTurmaMatriculadaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUltimaTurmaMatriculadaId(),
                            root -> root.join(ResumoAcademico_.ultimaTurmaMatriculada, JoinType.LEFT).get(Turma_.id)
                        )
                    );
            }
            if (criteria.getDiscenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDiscenteId(),
                            root -> root.join(ResumoAcademico_.discente, JoinType.LEFT).get(Discente_.id)
                        )
                    );
            }
            if (criteria.getSituacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSituacaoId(),
                            root -> root.join(ResumoAcademico_.situacao, JoinType.LEFT).get(EstadoDisciplinaCurricular_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
