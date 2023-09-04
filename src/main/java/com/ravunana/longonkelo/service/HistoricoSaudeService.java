package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.HistoricoSaudeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.HistoricoSaude}.
 */
public interface HistoricoSaudeService {
    /**
     * Save a historicoSaude.
     *
     * @param historicoSaudeDTO the entity to save.
     * @return the persisted entity.
     */
    HistoricoSaudeDTO save(HistoricoSaudeDTO historicoSaudeDTO);

    /**
     * Updates a historicoSaude.
     *
     * @param historicoSaudeDTO the entity to update.
     * @return the persisted entity.
     */
    HistoricoSaudeDTO update(HistoricoSaudeDTO historicoSaudeDTO);

    /**
     * Partially updates a historicoSaude.
     *
     * @param historicoSaudeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HistoricoSaudeDTO> partialUpdate(HistoricoSaudeDTO historicoSaudeDTO);

    /**
     * Get all the historicoSaudes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HistoricoSaudeDTO> findAll(Pageable pageable);

    /**
     * Get all the historicoSaudes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HistoricoSaudeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" historicoSaude.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HistoricoSaudeDTO> findOne(Long id);

    /**
     * Delete the "id" historicoSaude.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
