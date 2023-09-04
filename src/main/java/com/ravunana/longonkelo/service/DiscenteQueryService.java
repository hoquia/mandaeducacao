package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Discente;
import com.ravunana.longonkelo.repository.DiscenteRepository;
import com.ravunana.longonkelo.service.criteria.DiscenteCriteria;
import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import com.ravunana.longonkelo.service.mapper.DiscenteMapper;
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
 * Service for executing complex queries for {@link Discente} entities in the database.
 * The main input is a {@link DiscenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DiscenteDTO} or a {@link Page} of {@link DiscenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiscenteQueryService extends QueryService<Discente> {

    private final Logger log = LoggerFactory.getLogger(DiscenteQueryService.class);

    private final DiscenteRepository discenteRepository;

    private final DiscenteMapper discenteMapper;

    public DiscenteQueryService(DiscenteRepository discenteRepository, DiscenteMapper discenteMapper) {
        this.discenteRepository = discenteRepository;
        this.discenteMapper = discenteMapper;
    }

    /**
     * Return a {@link List} of {@link DiscenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DiscenteDTO> findByCriteria(DiscenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Discente> specification = createSpecification(criteria);
        return discenteMapper.toDto(discenteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DiscenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiscenteDTO> findByCriteria(DiscenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Discente> specification = createSpecification(criteria);
        return discenteRepository.findAll(specification, page).map(discenteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiscenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Discente> specification = createSpecification(criteria);
        return discenteRepository.count(specification);
    }

    /**
     * Function to convert {@link DiscenteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Discente> createSpecification(DiscenteCriteria criteria) {
        Specification<Discente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Discente_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Discente_.nome));
            }
            if (criteria.getNascimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNascimento(), Discente_.nascimento));
            }
            if (criteria.getDocumentoNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentoNumero(), Discente_.documentoNumero));
            }
            if (criteria.getDocumentoEmissao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentoEmissao(), Discente_.documentoEmissao));
            }
            if (criteria.getDocumentoValidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentoValidade(), Discente_.documentoValidade));
            }
            if (criteria.getNif() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNif(), Discente_.nif));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildSpecification(criteria.getSexo(), Discente_.sexo));
            }
            if (criteria.getPai() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPai(), Discente_.pai));
            }
            if (criteria.getMae() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMae(), Discente_.mae));
            }
            if (criteria.getTelefonePrincipal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonePrincipal(), Discente_.telefonePrincipal));
            }
            if (criteria.getTelefoneParente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefoneParente(), Discente_.telefoneParente));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Discente_.email));
            }
            if (criteria.getIsEncarregadoEducacao() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEncarregadoEducacao(), Discente_.isEncarregadoEducacao));
            }
            if (criteria.getIsTrabalhador() != null) {
                specification = specification.and(buildSpecification(criteria.getIsTrabalhador(), Discente_.isTrabalhador));
            }
            if (criteria.getIsFilhoAntigoConbatente() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsFilhoAntigoConbatente(), Discente_.isFilhoAntigoConbatente));
            }
            if (criteria.getIsAtestadoPobreza() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAtestadoPobreza(), Discente_.isAtestadoPobreza));
            }
            if (criteria.getNomeMedico() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeMedico(), Discente_.nomeMedico));
            }
            if (criteria.getTelefoneMedico() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefoneMedico(), Discente_.telefoneMedico));
            }
            if (criteria.getInstituicaoParticularSaude() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getInstituicaoParticularSaude(), Discente_.instituicaoParticularSaude)
                    );
            }
            if (criteria.getAltura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAltura(), Discente_.altura));
            }
            if (criteria.getPeso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeso(), Discente_.peso));
            }
            if (criteria.getIsAsmatico() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAsmatico(), Discente_.isAsmatico));
            }
            if (criteria.getIsAlergico() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAlergico(), Discente_.isAlergico));
            }
            if (criteria.getIsPraticaEducacaoFisica() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsPraticaEducacaoFisica(), Discente_.isPraticaEducacaoFisica));
            }
            if (criteria.getIsAutorizadoMedicacao() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAutorizadoMedicacao(), Discente_.isAutorizadoMedicacao));
            }
            if (criteria.getNumeroProcesso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroProcesso(), Discente_.numeroProcesso));
            }
            if (criteria.getDataIngresso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataIngresso(), Discente_.dataIngresso));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), Discente_.hash));
            }
            if (criteria.getEnderecosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEnderecosId(),
                            root -> root.join(Discente_.enderecos, JoinType.LEFT).get(EnderecoDiscente_.id)
                        )
                    );
            }
            if (criteria.getProcessosSelectivoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessosSelectivoId(),
                            root -> root.join(Discente_.processosSelectivos, JoinType.LEFT).get(ProcessoSelectivoMatricula_.id)
                        )
                    );
            }
            if (criteria.getAnexoDiscenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAnexoDiscenteId(),
                            root -> root.join(Discente_.anexoDiscentes, JoinType.LEFT).get(AnexoDiscente_.id)
                        )
                    );
            }
            if (criteria.getMatriculasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculasId(),
                            root -> root.join(Discente_.matriculas, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getResumoAcademicoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResumoAcademicoId(),
                            root -> root.join(Discente_.resumoAcademicos, JoinType.LEFT).get(ResumoAcademico_.id)
                        )
                    );
            }
            if (criteria.getHistoricosSaudeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHistoricosSaudeId(),
                            root -> root.join(Discente_.historicosSaudes, JoinType.LEFT).get(HistoricoSaude_.id)
                        )
                    );
            }
            if (criteria.getDissertacaoFinalCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDissertacaoFinalCursoId(),
                            root -> root.join(Discente_.dissertacaoFinalCursos, JoinType.LEFT).get(DissertacaoFinalCurso_.id)
                        )
                    );
            }
            if (criteria.getNacionalidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNacionalidadeId(),
                            root -> root.join(Discente_.nacionalidade, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getNaturalidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNaturalidadeId(),
                            root -> root.join(Discente_.naturalidade, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getTipoDocumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoDocumentoId(),
                            root -> root.join(Discente_.tipoDocumento, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getProfissaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProfissaoId(),
                            root -> root.join(Discente_.profissao, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getGrupoSanguinioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGrupoSanguinioId(),
                            root -> root.join(Discente_.grupoSanguinio, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getNecessidadeEspecialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNecessidadeEspecialId(),
                            root -> root.join(Discente_.necessidadeEspecial, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getEncarregadoEducacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEncarregadoEducacaoId(),
                            root -> root.join(Discente_.encarregadoEducacao, JoinType.LEFT).get(EncarregadoEducacao_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
