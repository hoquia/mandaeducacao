package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.TransferenciaTurmaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.TransferenciaTurma}.
 */
public interface TransferenciaTurmaService {
    /**
     * Save a transferenciaTurma.
     *
     * @param transferenciaTurmaDTO the entity to save.
     * @return the persisted entity.
     */
    TransferenciaTurmaDTO save(TransferenciaTurmaDTO transferenciaTurmaDTO);

    /**
     * Updates a transferenciaTurma.
     *
     * @param transferenciaTurmaDTO the entity to update.
     * @return the persisted entity.
     */
    TransferenciaTurmaDTO update(TransferenciaTurmaDTO transferenciaTurmaDTO);

    /**
     * Partially updates a transferenciaTurma.
     *
     * @param transferenciaTurmaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransferenciaTurmaDTO> partialUpdate(TransferenciaTurmaDTO transferenciaTurmaDTO);

    /**
     * Get all the transferenciaTurmas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransferenciaTurmaDTO> findAll(Pageable pageable);

    /**
     * Get all the transferenciaTurmas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransferenciaTurmaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" transferenciaTurma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransferenciaTurmaDTO> findOne(Long id);

    /**
     * Delete the "id" transferenciaTurma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
