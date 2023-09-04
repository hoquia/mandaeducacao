package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.domain.*; // for static metamodels
import com.ravunana.longonkelo.domain.TransferenciaSaldo;
import com.ravunana.longonkelo.repository.TransferenciaSaldoRepository;
import com.ravunana.longonkelo.service.criteria.TransferenciaSaldoCriteria;
import com.ravunana.longonkelo.service.dto.TransferenciaSaldoDTO;
import com.ravunana.longonkelo.service.mapper.TransferenciaSaldoMapper;
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
 * Service for executing complex queries for {@link TransferenciaSaldo} entities in the database.
 * The main input is a {@link TransferenciaSaldoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransferenciaSaldoDTO} or a {@link Page} of {@link TransferenciaSaldoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransferenciaSaldoQueryService extends QueryService<TransferenciaSaldo> {

    private final Logger log = LoggerFactory.getLogger(TransferenciaSaldoQueryService.class);

    private final TransferenciaSaldoRepository transferenciaSaldoRepository;

    private final TransferenciaSaldoMapper transferenciaSaldoMapper;

    public TransferenciaSaldoQueryService(
        TransferenciaSaldoRepository transferenciaSaldoRepository,
        TransferenciaSaldoMapper transferenciaSaldoMapper
    ) {
        this.transferenciaSaldoRepository = transferenciaSaldoRepository;
        this.transferenciaSaldoMapper = transferenciaSaldoMapper;
    }

    /**
     * Return a {@link List} of {@link TransferenciaSaldoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransferenciaSaldoDTO> findByCriteria(TransferenciaSaldoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransferenciaSaldo> specification = createSpecification(criteria);
        return transferenciaSaldoMapper.toDto(transferenciaSaldoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransferenciaSaldoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferenciaSaldoDTO> findByCriteria(TransferenciaSaldoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransferenciaSaldo> specification = createSpecification(criteria);
        return transferenciaSaldoRepository.findAll(specification, page).map(transferenciaSaldoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransferenciaSaldoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransferenciaSaldo> specification = createSpecification(criteria);
        return transferenciaSaldoRepository.count(specification);
    }

    /**
     * Function to convert {@link TransferenciaSaldoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransferenciaSaldo> createSpecification(TransferenciaSaldoCriteria criteria) {
        Specification<TransferenciaSaldo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransferenciaSaldo_.id));
            }
            if (criteria.getMontante() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontante(), TransferenciaSaldo_.montante));
            }
            if (criteria.getIsMesmaConta() != null) {
                specification = specification.and(buildSpecification(criteria.getIsMesmaConta(), TransferenciaSaldo_.isMesmaConta));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), TransferenciaSaldo_.timestamp));
            }
            if (criteria.getDeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDeId(), root -> root.join(TransferenciaSaldo_.de, JoinType.LEFT).get(Discente_.id))
                    );
            }
            if (criteria.getParaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParaId(),
                            root -> root.join(TransferenciaSaldo_.para, JoinType.LEFT).get(Discente_.id)
                        )
                    );
            }
            if (criteria.getUtilizadorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUtilizadorId(),
                            root -> root.join(TransferenciaSaldo_.utilizador, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getMotivoTransferenciaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMotivoTransferenciaId(),
                            root -> root.join(TransferenciaSaldo_.motivoTransferencia, JoinType.LEFT).get(LookupItem_.id)
                        )
                    );
            }
            if (criteria.getTransacoesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransacoesId(),
                            root -> root.join(TransferenciaSaldo_.transacoes, JoinType.LEFT).get(Transacao_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
