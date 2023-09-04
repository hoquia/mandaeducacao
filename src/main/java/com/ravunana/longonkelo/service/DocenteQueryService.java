package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.repository.DocenteRepository;
import com.ravunana.longonkelo.service.criteria.DocenteCriteria;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.mapper.DocenteMapper;
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
 * Service for executing complex queries for {@link Docente} entities in the database.
 * The main input is a {@link DocenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocenteDTO} or a {@link Page} of {@link DocenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocenteQueryService extends QueryService<Docente> {

    private final Logger log = LoggerFactory.getLogger(DocenteQueryService.class);

    private final DocenteRepository docenteRepository;

    private final DocenteMapper docenteMapper;

    public DocenteQueryService(DocenteRepository docenteRepository, DocenteMapper docenteMapper) {
        this.docenteRepository = docenteRepository;
        this.docenteMapper = docenteMapper;
    }

    /**
     * Return a {@link List} of {@link DocenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocenteDTO> findByCriteria(DocenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Docente> specification = createSpecification(criteria);
        return docenteMapper.toDto(docenteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DocenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocenteDTO> findByCriteria(DocenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Docente> specification = createSpecification(criteria);
        return docenteRepository.findAll(specification, page).map(docenteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Docente> specification = createSpecification(criteria);
        return docenteRepository.count(specification);
    }

    /**
     * Function to convert {@link DocenteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Docente> createSpecification(DocenteCriteria criteria) {
        Specification<Docente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Docente_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Docente_.nome));
            }
            if (criteria.getNascimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNascimento(), Docente_.nascimento));
            }
            if (criteria.getNif() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNif(), Docente_.nif));
            }
            if (criteria.getInss() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInss(), Docente_.inss));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildSpecification(criteria.getSexo(), Docente_.sexo));
            }
            if (criteria.getPai() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPai(), Docente_.pai));
            }
            if (criteria.getMae() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMae(), Docente_.mae));
            }
            if (criteria.getDocumentoNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentoNumero(), Docente_.documentoNumero));
            }
            if (criteria.getDocumentoEmissao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentoEmissao(), Docente_.documentoEmissao));
            }
            if (criteria.getDocumentoValidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocumentoValidade(), Docente_.documentoValidade));
            }
            if (criteria.getResidencia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResidencia(), Docente_.residencia));
            }
            if (criteria.getDataInicioFuncoes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInicioFuncoes(), Docente_.dataInicioFuncoes));
            }
            if (criteria.getTelefonePrincipal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefonePrincipal(), Docente_.telefonePrincipal));
            }
            if (criteria.getTelefoneParente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefoneParente(), Docente_.telefoneParente));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Docente_.email));
            }
            if (criteria.getNumeroAgente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroAgente(), Docente_.numeroAgente));
            }
            if (criteria.getTemAgregacaoPedagogica() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getTemAgregacaoPedagogica(), Docente_.temAgregacaoPedagogica));
            }
            if (criteria.getHash() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHash(), Docente_.hash));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), Docente_.timestamp));
            }
            if (criteria.getOcorrenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOcorrenciaId(),
                            root -> root.join(Docente_.ocorrencias, JoinType.LEFT).get(Ocorrencia_.id)
                        )
                    );
            }
            if (criteria.getHorariosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getHorariosId(), root -> root.join(Docente_.horarios, JoinType.LEFT).get(Horario_.id))
                    );
            }
            if (criteria.getPlanoAulaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPlanoAulaId(),
                            root -> root.join(Docente_.planoAulas, JoinType.LEFT).get(PlanoAula_.id)
                        )
                    );
            }
            if (criteria.getNotasPeriodicaDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotasPeriodicaDisciplinaId(),
                            root -> root.join(Docente_.notasPeriodicaDisciplinas, JoinType.LEFT).get(NotasPeriodicaDisciplina_.id)
                        )
                    );
            }
            if (criteria.getNotasGeralDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotasGeralDisciplinaId(),
                            root -> root.join(Docente_.notasGeralDisciplinas, JoinType.LEFT).get(NotasGeralDisciplina_.id)
                        )
                    );
            }
            if (criteria.getDissertacaoFinalCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDissertacaoFinalCursoId(),
                            root -> root.join(Docente_.dissertacaoFinalCursos, JoinType.LEFT).get(DissertacaoFinalCurso_.id)
                        )
                    );
            }
            if (criteria.getCategoriaOcorrenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriaOcorrenciaId(),
                            root -> root.join(Docente_.categoriaOcorrencias, JoinType.LEFT).get(CategoriaOcorrencia_.id)
                        )
                    );
            }
            if (criteria.getFormacoesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFormacoesId(),
                            root -> root.join(Docente_.formacoes, JoinType.LEFT).get(FormacaoDocente_.id)
                        )
                    );
            }
            if (criteria.getNacionalidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNacionalidadeId(),
                            root -> root.join(Docente_.nacionalidade, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getNaturalidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNaturalidadeId(),
                            root -> root.join(Docente_.naturalidade, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getTipoDocumentoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTipoDocumentoId(),
                            root -> root.join(Docente_.tipoDocumento, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getGrauAcademicoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGrauAcademicoId(),
                            root -> root.join(Docente_.grauAcademico, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getCategoriaProfissionalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriaProfissionalId(),
                            root -> root.join(Docente_.categoriaProfissional, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getUnidadeOrganicaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUnidadeOrganicaId(),
                            root -> root.join(Docente_.unidadeOrganica, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getEstadoCivilId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEstadoCivilId(),
                            root -> root.join(Docente_.estadoCivil, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getResponsavelTurnoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelTurnoId(),
                            root -> root.join(Docente_.responsavelTurno, JoinType.LEFT).get(ResponsavelTurno_.id)
                        )
                    );
            }
            if (criteria.getResponsavelAreaFormacaoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelAreaFormacaoId(),
                            root -> root.join(Docente_.responsavelAreaFormacao, JoinType.LEFT).get(ResponsavelAreaFormacao_.id)
                        )
                    );
            }
            if (criteria.getResponsavelCursoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelCursoId(),
                            root -> root.join(Docente_.responsavelCurso, JoinType.LEFT).get(ResponsavelCurso_.id)
                        )
                    );
            }
            if (criteria.getResponsavelDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelDisciplinaId(),
                            root -> root.join(Docente_.responsavelDisciplina, JoinType.LEFT).get(ResponsavelDisciplina_.id)
                        )
                    );
            }
            if (criteria.getResponsavelTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelTurmaId(),
                            root -> root.join(Docente_.responsavelTurma, JoinType.LEFT).get(ResponsavelTurma_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
