package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ReciboDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Recibo}.
 */
public interface ReciboService {
    /**
     * Save a recibo.
     *
     * @param reciboDTO the entity to save.
     * @return the persisted entity.
     */
    ReciboDTO save(ReciboDTO reciboDTO);

    /**
     * Updates a recibo.
     *
     * @param reciboDTO the entity to update.
     * @return the persisted entity.
     */
    ReciboDTO update(ReciboDTO reciboDTO);

    /**
     * Partially updates a recibo.
     *
     * @param reciboDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReciboDTO> partialUpdate(ReciboDTO reciboDTO);

    /**
     * Get all the recibos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReciboDTO> findAll(Pageable pageable);

    /**
     * Get all the recibos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReciboDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" recibo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReciboDTO> findOne(Long id);

    /**
     * Delete the "id" recibo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
