package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.DiscenteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Discente}.
 */
public interface DiscenteService {
    /**
     * Save a discente.
     *
     * @param discenteDTO the entity to save.
     * @return the persisted entity.
     */
    DiscenteDTO save(DiscenteDTO discenteDTO);

    /**
     * Updates a discente.
     *
     * @param discenteDTO the entity to update.
     * @return the persisted entity.
     */
    DiscenteDTO update(DiscenteDTO discenteDTO);

    /**
     * Partially updates a discente.
     *
     * @param discenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DiscenteDTO> partialUpdate(DiscenteDTO discenteDTO);

    /**
     * Get all the discentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiscenteDTO> findAll(Pageable pageable);

    /**
     * Get all the discentes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiscenteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" discente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiscenteDTO> findOne(Long id);

    /**
     * Delete the "id" discente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    String getNumeroProcesso(String numeroDocumento, String anoLectivo);
}
