package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.NivelEnsino;
import com.ravunana.longonkelo.repository.NivelEnsinoRepository;
import com.ravunana.longonkelo.service.criteria.NivelEnsinoCriteria;
import com.ravunana.longonkelo.service.dto.NivelEnsinoDTO;
import com.ravunana.longonkelo.service.mapper.NivelEnsinoMapper;
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
 * Service for executing complex queries for {@link NivelEnsino} entities in the database.
 * The main input is a {@link NivelEnsinoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NivelEnsinoDTO} or a {@link Page} of {@link NivelEnsinoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NivelEnsinoQueryService extends QueryService<NivelEnsino> {

    private final Logger log = LoggerFactory.getLogger(NivelEnsinoQueryService.class);

    private final NivelEnsinoRepository nivelEnsinoRepository;

    private final NivelEnsinoMapper nivelEnsinoMapper;

    public NivelEnsinoQueryService(NivelEnsinoRepository nivelEnsinoRepository, NivelEnsinoMapper nivelEnsinoMapper) {
        this.nivelEnsinoRepository = nivelEnsinoRepository;
        this.nivelEnsinoMapper = nivelEnsinoMapper;
    }

    /**
     * Return a {@link List} of {@link NivelEnsinoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NivelEnsinoDTO> findByCriteria(NivelEnsinoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NivelEnsino> specification = createSpecification(criteria);
        return nivelEnsinoMapper.toDto(nivelEnsinoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NivelEnsinoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NivelEnsinoDTO> findByCriteria(NivelEnsinoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NivelEnsino> specification = createSpecification(criteria);
        return nivelEnsinoRepository.findAll(specification, page).map(nivelEnsinoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NivelEnsinoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NivelEnsino> specification = createSpecification(criteria);
        return nivelEnsinoRepository.count(specification);
    }

    /**
     * Function to convert {@link NivelEnsinoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NivelEnsino> createSpecification(NivelEnsinoCriteria criteria) {
        Specification<NivelEnsino> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NivelEnsino_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), NivelEnsino_.codigo));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), NivelEnsino_.nome));
            }
            if (criteria.getIdadeMinima() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdadeMinima(), NivelEnsino_.idadeMinima));
            }
            if (criteria.getIdadeMaxima() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdadeMaxima(), NivelEnsino_.idadeMaxima));
            }
            if (criteria.getDuracao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuracao(), NivelEnsino_.duracao));
            }
            if (criteria.getUnidadeDuracao() != null) {
                specification = specification.and(buildSpecification(criteria.getUnidadeDuracao(), NivelEnsino_.unidadeDuracao));
            }
            if (criteria.getClasseInicial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClasseInicial(), NivelEnsino_.classeInicial));
            }
            if (criteria.getClasseFinal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClasseFinal(), NivelEnsino_.classeFinal));
            }
            if (criteria.getClasseExame() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClasseExame(), NivelEnsino_.classeExame));
            }
            if (criteria.getTotalDisciplina() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDisciplina(), NivelEnsino_.totalDisciplina));
            }
            if (criteria.getResponsavelTurno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponsavelTurno(), NivelEnsino_.responsavelTurno));
            }
            if (criteria.getResponsavelAreaFormacao() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getResponsavelAreaFormacao(), NivelEnsino_.responsavelAreaFormacao)
                    );
            }
            if (criteria.getResponsavelCurso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponsavelCurso(), NivelEnsino_.responsavelCurso));
            }
            if (criteria.getResponsavelDisciplina() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getResponsavelDisciplina(), NivelEnsino_.responsavelDisciplina));
            }
            if (criteria.getResponsavelTurma() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponsavelTurma(), NivelEnsino_.responsavelTurma));
            }
            if (criteria.getResponsavelGeral() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponsavelGeral(), NivelEnsino_.responsavelGeral));
            }
            if (criteria.getResponsavelPedagogico() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getResponsavelPedagogico(), NivelEnsino_.responsavelPedagogico));
            }
            if (criteria.getResponsavelAdministrativo() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getResponsavelAdministrativo(), NivelEnsino_.responsavelAdministrativo)
                    );
            }
            if (criteria.getResponsavelSecretariaGeral() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getResponsavelSecretariaGeral(), NivelEnsino_.responsavelSecretariaGeral)
                    );
            }
            if (criteria.getResponsavelSecretariaPedagogico() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getResponsavelSecretariaPedagogico(),
                            NivelEnsino_.responsavelSecretariaPedagogico
                        )
                    );
            }
            if (criteria.getDescricaoDocente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricaoDocente(), NivelEnsino_.descricaoDocente));
            }
            if (criteria.getDescricaoDiscente() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDescricaoDiscente(), NivelEnsino_.descricaoDiscente));
            }
            if (criteria.getNivelEnsinoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNivelEnsinoId(),
                            root -> root.join(NivelEnsino_.nivelEnsinos, JoinType.LEFT).get(NivelEnsino_.id)
                        )
                    );
            }
            if (criteria.getAreaFormacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAreaFormacaoId(),
                            root -> root.join(NivelEnsino_.areaFormacaos, JoinType.LEFT).get(AreaFormacao_.id)
                        )
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenciaId(),
                            root -> root.join(NivelEnsino_.referencia, JoinType.LEFT).get(NivelEnsino_.id)
                        )
                    );
            }
            if (criteria.getAnoLectivosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnoLectivosId(),
                            root -> root.join(NivelEnsino_.anoLectivos, JoinType.LEFT).get(AnoLectivo_.id)
                        )
                    );
            }
            if (criteria.getClassesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClassesId(), root -> root.join(NivelEnsino_.classes, JoinType.LEFT).get(Classe_.id))
                    );
            }
        }
        return specification;
    }
}
