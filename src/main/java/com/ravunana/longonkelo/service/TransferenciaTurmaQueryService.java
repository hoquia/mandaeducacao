package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.TransferenciaTurma;
import com.ravunana.longonkelo.repository.TransferenciaTurmaRepository;
import com.ravunana.longonkelo.service.criteria.TransferenciaTurmaCriteria;
import com.ravunana.longonkelo.service.dto.TransferenciaTurmaDTO;
import com.ravunana.longonkelo.service.mapper.TransferenciaTurmaMapper;
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
 * Service for executing complex queries for {@link TransferenciaTurma} entities in the database.
 * The main input is a {@link TransferenciaTurmaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransferenciaTurmaDTO} or a {@link Page} of {@link TransferenciaTurmaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransferenciaTurmaQueryService extends QueryService<TransferenciaTurma> {

    private final Logger log = LoggerFactory.getLogger(TransferenciaTurmaQueryService.class);

    private final TransferenciaTurmaRepository transferenciaTurmaRepository;

    private final TransferenciaTurmaMapper transferenciaTurmaMapper;

    public TransferenciaTurmaQueryService(
        TransferenciaTurmaRepository transferenciaTurmaRepository,
        TransferenciaTurmaMapper transferenciaTurmaMapper
    ) {
        this.transferenciaTurmaRepository = transferenciaTurmaRepository;
        this.transferenciaTurmaMapper = transferenciaTurmaMapper;
    }

    /**
     * Return a {@link List} of {@link TransferenciaTurmaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransferenciaTurmaDTO> findByCriteria(TransferenciaTurmaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransferenciaTurma> specification = createSpecification(criteria);
        return transferenciaTurmaMapper.toDto(transferenciaTurmaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransferenciaTurmaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferenciaTurmaDTO> findByCriteria(TransferenciaTurmaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransferenciaTurma> specification = createSpecification(criteria);
        return transferenciaTurmaRepository.findAll(specification, page).map(transferenciaTurmaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransferenciaTurmaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransferenciaTurma> specification = createSpecification(criteria);
        return transferenciaTurmaRepository.count(specification);
    }

    /**
     * Function to convert {@link TransferenciaTurmaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransferenciaTurma> createSpecification(TransferenciaTurmaCriteria criteria) {
        Specification<TransferenciaTurma> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransferenciaTurma_.id));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), TransferenciaTurma_.timestamp));
            }
            if (criteria.getDeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDeId(), root -> root.join(TransferenciaTurma_.de, JoinType.LEFT).get(Turma_.id))
                    );
            }
            if (criteria.getParaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getParaId(), root -> root.join(TransferenciaTurma_.para, JoinType.LEFT).get(Turma_.id))
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(TransferenciaTurma_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getMotivoTransferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMotivoTransferenciaId(),
                            root -> root.join(TransferenciaTurma_.motivoTransferencia, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getMatriculaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMatriculaId(),
                            root -> root.join(TransferenciaTurma_.matricula, JoinType.LEFT).get(Matricula_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
