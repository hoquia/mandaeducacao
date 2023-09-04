package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.TransferenciaSaldoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.TransferenciaSaldo}.
 */
public interface TransferenciaSaldoService {
    /**
     * Save a transferenciaSaldo.
     *
     * @param transferenciaSaldoDTO the entity to save.
     * @return the persisted entity.
     */
    TransferenciaSaldoDTO save(TransferenciaSaldoDTO transferenciaSaldoDTO);

    /**
     * Updates a transferenciaSaldo.
     *
     * @param transferenciaSaldoDTO the entity to update.
     * @return the persisted entity.
     */
    TransferenciaSaldoDTO update(TransferenciaSaldoDTO transferenciaSaldoDTO);

    /**
     * Partially updates a transferenciaSaldo.
     *
     * @param transferenciaSaldoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransferenciaSaldoDTO> partialUpdate(TransferenciaSaldoDTO transferenciaSaldoDTO);

    /**
     * Get all the transferenciaSaldos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransferenciaSaldoDTO> findAll(Pageable pageable);

    /**
     * Get all the transferenciaSaldos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransferenciaSaldoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" transferenciaSaldo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransferenciaSaldoDTO> findOne(Long id);

    /**
     * Delete the "id" transferenciaSaldo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
