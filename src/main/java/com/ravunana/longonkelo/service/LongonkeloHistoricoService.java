package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.LongonkeloHistoricoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.LongonkeloHistorico}.
 */
public interface LongonkeloHistoricoService {
    /**
     * Save a longonkeloHistorico.
     *
     * @param longonkeloHistoricoDTO the entity to save.
     * @return the persisted entity.
     */
    LongonkeloHistoricoDTO save(LongonkeloHistoricoDTO longonkeloHistoricoDTO);

    /**
     * Updates a longonkeloHistorico.
     *
     * @param longonkeloHistoricoDTO the entity to update.
     * @return the persisted entity.
     */
    LongonkeloHistoricoDTO update(LongonkeloHistoricoDTO longonkeloHistoricoDTO);

    /**
     * Partially updates a longonkeloHistorico.
     *
     * @param longonkeloHistoricoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LongonkeloHistoricoDTO> partialUpdate(LongonkeloHistoricoDTO longonkeloHistoricoDTO);

    /**
     * Get all the longonkeloHistoricos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LongonkeloHistoricoDTO> findAll(Pageable pageable);

    /**
     * Get all the longonkeloHistoricos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LongonkeloHistoricoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" longonkeloHistorico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LongonkeloHistoricoDTO> findOne(Long id);

    /**
     * Delete the "id" longonkeloHistorico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
