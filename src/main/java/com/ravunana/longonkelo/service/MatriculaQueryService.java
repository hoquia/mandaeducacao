package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.repository.MatriculaRepository;
import com.ravunana.longonkelo.service.criteria.MatriculaCriteria;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.mapper.MatriculaMapper;
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
 * Service for executing complex queries for {@link Matricula} entities in the database.
 * The main input is a {@link MatriculaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MatriculaDTO} or a {@link Page} of {@link MatriculaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MatriculaQueryService extends QueryService<Matricula> {

    private final Logger log = LoggerFactory.getLogger(MatriculaQueryService.class);

    private final MatriculaRepository matriculaRepository;

    private final MatriculaMapper matriculaMapper;

    public MatriculaQueryService(MatriculaRepository matriculaRepository, MatriculaMapper matriculaMapper) {
        this.matriculaRepository = matriculaRepository;
        this.matriculaMapper = matriculaMapper;
    }

    /**
     * Return a {@link List} of {@link MatriculaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MatriculaDTO> findByCriteria(MatriculaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Matricula> specification = createSpecification(criteria);
        return matriculaMapper.toDto(matriculaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MatriculaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MatriculaDTO> findByCriteria(MatriculaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Matricula> specification = createSpecification(criteria);
        return matriculaRepository.findAll(specification, page).map(matriculaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MatriculaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Matricula> specification = createSpecification(criteria);
        return matriculaRepository.count(specification);
    }

    /**
     * Function to convert {@link MatriculaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Matricula> createSpecification(MatriculaCriteria criteria) {
        Specification<Matricula> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Matricula_.id));
            }
            if (criteria.getChaveComposta1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChaveComposta1(), Matricula_.chaveComposta1));
            }
            if (criteria.getChaveComposta2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChaveComposta2(), Matricula_.chaveComposta2));
            }
            if (criteria.getNumeroMatricula() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroMatricula(), Matricula_.numeroMatricula));
            }
            if (criteria.getNumeroChamada() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroChamada(), Matricula_.numeroChamada));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), Matricula_.estado));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), Matricula_.timestamp));
            }
            if (criteria.getIsAceiteTermosCompromisso() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getIsAceiteTermosCompromisso(), Matricula_.isAceiteTermosCompromisso));
            }
            if (criteria.getMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculaId(),
                            root -> root.join(Matricula_.matriculas, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
            if (criteria.getFacturasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFacturasId(), root -> root.join(Matricula_.facturas, JoinType.LEFT).get(Factura_.id))
                    );
            }
            if (criteria.getTransacoesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransacoesId(),
                            root -> root.join(Matricula_.transacoes, JoinType.LEFT).get(Transacao_.id)
                        )
                    );
            }
            if (criteria.getRecibosId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRecibosId(), root -> root.join(Matricula_.recibos, JoinType.LEFT).get(Recibo_.id))
                    );
            }
            if (criteria.getNotasPeriodicaDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotasPeriodicaDisciplinaId(),
                            root -> root.join(Matricula_.notasPeriodicaDisciplinas, JoinType.LEFT).get(NotasPeriodicaDisciplina_.id)
                        )
                    );
            }
            if (criteria.getNotasGeralDisciplinaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotasGeralDisciplinaId(),
                            root -> root.join(Matricula_.notasGeralDisciplinas, JoinType.LEFT).get(NotasGeralDisciplina_.id)
                        )
                    );
            }
            if (criteria.getTransferenciaTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferenciaTurmaId(),
                            root -> root.join(Matricula_.transferenciaTurmas, JoinType.LEFT).get(TransferenciaTurma_.id)
                        )
                    );
            }
            if (criteria.getOcorrenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOcorrenciaId(),
                            root -> root.join(Matricula_.ocorrencias, JoinType.LEFT).get(Ocorrencia_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(Matricula_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getCategoriasMatriculasId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriasMatriculasId(),
                            root -> root.join(Matricula_.categoriasMatriculas, JoinType.LEFT).get(PlanoDesconto_.id)
                        )
                    );
            }
            if (criteria.getTurmaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTurmaId(), root -> root.join(Matricula_.turma, JoinType.LEFT).get(Turma_.id))
                    );
            }
            if (criteria.getResponsavelFinanceiroId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelFinanceiroId(),
                            root -> root.join(Matricula_.responsavelFinanceiro, JoinType.LEFT).get(EncarregadoEducacao_.id)
                        )
                    );
            }
            if (criteria.getDiscenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDiscenteId(),
                            root -> root.join(Matricula_.discente, JoinType.LEFT).get(Discente_.id)
                        )
                    );
            }
            if (criteria.getReferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReferenciaId(),
                            root -> root.join(Matricula_.referencia, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
