package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ContaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Conta}.
 */
public interface ContaService {
    /**
     * Save a conta.
     *
     * @param contaDTO the entity to save.
     * @return the persisted entity.
     */
    ContaDTO save(ContaDTO contaDTO);

    /**
     * Updates a conta.
     *
     * @param contaDTO the entity to update.
     * @return the persisted entity.
     */
    ContaDTO update(ContaDTO contaDTO);

    /**
     * Partially updates a conta.
     *
     * @param contaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContaDTO> partialUpdate(ContaDTO contaDTO);

    /**
     * Get all the contas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContaDTO> findAll(Pageable pageable);

    /**
     * Get all the contas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" conta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContaDTO> findOne(Long id);

    /**
     * Delete the "id" conta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
