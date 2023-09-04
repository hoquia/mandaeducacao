package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.EncarregadoEducacao;
import com.ravunana.longonkelo.repository.EncarregadoEducacaoRepository;
import com.ravunana.longonkelo.service.criteria.EncarregadoEducacaoCriteria;
import com.ravunana.longonkelo.service.dto.EncarregadoEducacaoDTO;
import com.ravunana.longonkelo.service.mapper.EncarregadoEducacaoMapper;
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
 * Service for executing complex queries for {@link EncarregadoEducacao} entities in the database.
 * The main input is a {@link EncarregadoEducacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EncarregadoEducacaoDTO} or a {@link Page} of {@link EncarregadoEducacaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EncarregadoEducacaoQueryService extends QueryService<EncarregadoEducacao> {

    private final Logger log = LoggerFactory.getLogger(EncarregadoEducacaoQueryService.class);

    private final EncarregadoEducacaoRepository encarregadoEducacaoRepository;

    private final EncarregadoEducacaoMapper encarregadoEducacaoMapper;

    public EncarregadoEducacaoQueryService(
        EncarregadoEducacaoRepository encarregadoEducacaoRepository,
        EncarregadoEducacaoMapper encarregadoEducacaoMapper
    ) {
        this.encarregadoEducacaoRepository = encarregadoEducacaoRepository;
        this.encarregadoEducacaoMapper = encarregadoEducacaoMapper;
    }

    /**
     * Return a {@link List} of {@link EncarregadoEducacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EncarregadoEducacaoDTO> findByCriteria(EncarregadoEducacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EncarregadoEducacao> specification = createSpecification(criteria);
        return encarregadoEducacaoMapper.toDto(encarregadoEducacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EncarregadoEducacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EncarregadoEducacaoDTO> findByCriteria(EncarregadoEducacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EncarregadoEducacao> specification = createSpecification(criteria);
        return encarregadoEducacaoRepository.findAll(specification, page).map(encarregadoEducacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EncarregadoEducacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EncarregadoEducacao> specification = createSpecification(criteria);
        return encarregadoEducacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link EncarregadoEducacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EncarregadoEducacao> createSpecification(EncarregadoEducacaoCriteria criteria) {
        Specification<EncarregadoEducacao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EncarregadoEducacao_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), EncarregadoEducacao_.nome));
            }
            if (criteria.getNascimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNascimento(), EncarregadoEducacao_.nascimento));
            }
            if (criteria.getNif() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNif(), EncarregadoEducacao_.nif));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildSpecification(criteria.getSexo(), EncarregadoEducacao_.sexo));
            }
            if (criteria.getDocumentoNumero() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDocumentoNumero(), EncarregadoEducacao_.documentoNumero));
            }
            if (criteria.getTelefonePrincipal() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTelefonePrincipal(), EncarregadoEducacao_.telefonePrincipal));
            }
            if (criteria.getTelefoneAlternativo() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getTelefoneAlternativo(), EncarregadoEducacao_.telefoneAlternativo)
                    );
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), EncarregadoEducacao_.email));
            }
            if (criteria.getResidencia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResidencia(), EncarregadoEducacao_.residencia));
            }
            if (criteria.getEnderecoTrabalho() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEnderecoTrabalho(), EncarregadoEducacao_.enderecoTrabalho));
            }
            if (criteria.getRendaMensal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRendaMensal(), EncarregadoEducacao_.rendaMensal));
            }
            if (criteria.getEmpresaTrabalho() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEmpresaTrabalho(), EncarregadoEducacao_.empresaTrabalho));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), EncarregadoEducacao_.hash));
            }
            if (criteria.getDiscentesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDiscentesId(),
                            root -> root.join(EncarregadoEducacao_.discentes, JoinType.LEFT).get(Discente_.id)
                        )
                    );
            }
            if (criteria.getMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculaId(),
                            root -> root.join(EncarregadoEducacao_.matriculas, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getGrauParentescoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGrauParentescoId(),
                            root -> root.join(EncarregadoEducacao_.grauParentesco, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getTipoDocumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoDocumentoId(),
                            root -> root.join(EncarregadoEducacao_.tipoDocumento, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getProfissaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProfissaoId(),
                            root -> root.join(EncarregadoEducacao_.profissao, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
