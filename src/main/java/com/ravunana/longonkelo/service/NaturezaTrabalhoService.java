package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.NaturezaTrabalhoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.NaturezaTrabalho}.
 */
public interface NaturezaTrabalhoService {
    /**
     * Save a naturezaTrabalho.
     *
     * @param naturezaTrabalhoDTO the entity to save.
     * @return the persisted entity.
     */
    NaturezaTrabalhoDTO save(NaturezaTrabalhoDTO naturezaTrabalhoDTO);

    /**
     * Updates a naturezaTrabalho.
     *
     * @param naturezaTrabalhoDTO the entity to update.
     * @return the persisted entity.
     */
    NaturezaTrabalhoDTO update(NaturezaTrabalhoDTO naturezaTrabalhoDTO);

    /**
     * Partially updates a naturezaTrabalho.
     *
     * @param naturezaTrabalhoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NaturezaTrabalhoDTO> partialUpdate(NaturezaTrabalhoDTO naturezaTrabalhoDTO);

    /**
     * Get all the naturezaTrabalhos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NaturezaTrabalhoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" naturezaTrabalho.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NaturezaTrabalhoDTO> findOne(Long id);

    /**
     * Delete the "id" naturezaTrabalho.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
