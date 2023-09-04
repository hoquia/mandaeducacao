package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.MedidaDisciplinarDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.MedidaDisciplinar}.
 */
public interface MedidaDisciplinarService {
    /**
     * Save a medidaDisciplinar.
     *
     * @param medidaDisciplinarDTO the entity to save.
     * @return the persisted entity.
     */
    MedidaDisciplinarDTO save(MedidaDisciplinarDTO medidaDisciplinarDTO);

    /**
     * Updates a medidaDisciplinar.
     *
     * @param medidaDisciplinarDTO the entity to update.
     * @return the persisted entity.
     */
    MedidaDisciplinarDTO update(MedidaDisciplinarDTO medidaDisciplinarDTO);

    /**
     * Partially updates a medidaDisciplinar.
     *
     * @param medidaDisciplinarDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MedidaDisciplinarDTO> partialUpdate(MedidaDisciplinarDTO medidaDisciplinarDTO);

    /**
     * Get all the medidaDisciplinars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedidaDisciplinarDTO> findAll(Pageable pageable);

    /**
     * Get the "id" medidaDisciplinar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedidaDisciplinarDTO> findOne(Long id);

    /**
     * Delete the "id" medidaDisciplinar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
