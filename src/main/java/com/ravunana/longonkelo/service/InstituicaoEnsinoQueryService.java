package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.InstituicaoEnsino;
import com.ravunana.longonkelo.repository.InstituicaoEnsinoRepository;
import com.ravunana.longonkelo.service.criteria.InstituicaoEnsinoCriteria;
import com.ravunana.longonkelo.service.dto.InstituicaoEnsinoDTO;
import com.ravunana.longonkelo.service.mapper.InstituicaoEnsinoMapper;
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
 * Service for executing complex queries for {@link InstituicaoEnsino} entities in the database.
 * The main input is a {@link InstituicaoEnsinoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InstituicaoEnsinoDTO} or a {@link Page} of {@link InstituicaoEnsinoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InstituicaoEnsinoQueryService extends QueryService<InstituicaoEnsino> {

    private final Logger log = LoggerFactory.getLogger(InstituicaoEnsinoQueryService.class);

    private final InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    private final InstituicaoEnsinoMapper instituicaoEnsinoMapper;

    public InstituicaoEnsinoQueryService(
        InstituicaoEnsinoRepository instituicaoEnsinoRepository,
        InstituicaoEnsinoMapper instituicaoEnsinoMapper
    ) {
        this.instituicaoEnsinoRepository = instituicaoEnsinoRepository;
        this.instituicaoEnsinoMapper = instituicaoEnsinoMapper;
    }

    /**
     * Return a {@link List} of {@link InstituicaoEnsinoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InstituicaoEnsinoDTO> findByCriteria(InstituicaoEnsinoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InstituicaoEnsino> specification = createSpecification(criteria);
        return instituicaoEnsinoMapper.toDto(instituicaoEnsinoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InstituicaoEnsinoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InstituicaoEnsinoDTO> findByCriteria(InstituicaoEnsinoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InstituicaoEnsino> specification = createSpecification(criteria);
        return instituicaoEnsinoRepository.findAll(specification, page).map(instituicaoEnsinoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InstituicaoEnsinoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InstituicaoEnsino> specification = createSpecification(criteria);
        return instituicaoEnsinoRepository.count(specification);
    }

    /**
     * Function to convert {@link InstituicaoEnsinoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InstituicaoEnsino> createSpecification(InstituicaoEnsinoCriteria criteria) {
        Specification<InstituicaoEnsino> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InstituicaoEnsino_.id));
            }
            if (criteria.getUnidadeOrganica() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getUnidadeOrganica(), InstituicaoEnsino_.unidadeOrganica));
            }
            if (criteria.getNomeFiscal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeFiscal(), InstituicaoEnsino_.nomeFiscal));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), InstituicaoEnsino_.numero));
            }
            if (criteria.getNif() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNif(), InstituicaoEnsino_.nif));
            }
            if (criteria.getCae() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCae(), InstituicaoEnsino_.cae));
            }
            if (criteria.getNiss() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNiss(), InstituicaoEnsino_.niss));
            }
            if (criteria.getFundador() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFundador(), InstituicaoEnsino_.fundador));
            }
            if (criteria.getFundacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFundacao(), InstituicaoEnsino_.fundacao));
            }
            if (criteria.getDimensao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDimensao(), InstituicaoEnsino_.dimensao));
            }
            if (criteria.getSlogam() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlogam(), InstituicaoEnsino_.slogam));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), InstituicaoEnsino_.telefone));
            }
            if (criteria.getTelemovel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelemovel(), InstituicaoEnsino_.telemovel));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), InstituicaoEnsino_.email));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), InstituicaoEnsino_.website));
            }
            if (criteria.getCodigoPostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigoPostal(), InstituicaoEnsino_.codigoPostal));
            }
            if (criteria.getEnderecoDetalhado() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEnderecoDetalhado(), InstituicaoEnsino_.enderecoDetalhado));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), InstituicaoEnsino_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), InstituicaoEnsino_.longitude));
            }
            if (criteria.getIsComparticipada() != null) {
                specification = specification.and(buildSpecification(criteria.getIsComparticipada(), InstituicaoEnsino_.isComparticipada));
            }
            if (criteria.getInstituicaoEnsinoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInstituicaoEnsinoId(),
                            root -> root.join(InstituicaoEnsino_.instituicaoEnsinos, JoinType.LEFT).get(InstituicaoEnsino_.id)
                        )
                    );
            }
            if (criteria.getProvedorNotificacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProvedorNotificacaoId(),
                            root -> root.join(InstituicaoEnsino_.provedorNotificacaos, JoinType.LEFT).get(ProvedorNotificacao_.id)
                        )
                    );
            }
            if (criteria.getCategoriaInstituicaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriaInstituicaoId(),
                            root -> root.join(InstituicaoEnsino_.categoriaInstituicao, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getUnidadePagadoraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUnidadePagadoraId(),
                            root -> root.join(InstituicaoEnsino_.unidadePagadora, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getTipoVinculoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoVinculoId(),
                            root -> root.join(InstituicaoEnsino_.tipoVinculo, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getTipoInstalacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoInstalacaoId(),
                            root -> root.join(InstituicaoEnsino_.tipoInstalacao, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getSedeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSedeId(),
                            root -> root.join(InstituicaoEnsino_.sede, JoinType.LEFT).get(InstituicaoEnsino_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
