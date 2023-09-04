package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.AplicacaoReciboDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.AplicacaoRecibo}.
 */
public interface AplicacaoReciboService {
    /**
     * Save a aplicacaoRecibo.
     *
     * @param aplicacaoReciboDTO the entity to save.
     * @return the persisted entity.
     */
    AplicacaoReciboDTO save(AplicacaoReciboDTO aplicacaoReciboDTO);

    /**
     * Updates a aplicacaoRecibo.
     *
     * @param aplicacaoReciboDTO the entity to update.
     * @return the persisted entity.
     */
    AplicacaoReciboDTO update(AplicacaoReciboDTO aplicacaoReciboDTO);

    /**
     * Partially updates a aplicacaoRecibo.
     *
     * @param aplicacaoReciboDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AplicacaoReciboDTO> partialUpdate(AplicacaoReciboDTO aplicacaoReciboDTO);

    /**
     * Get all the aplicacaoRecibos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AplicacaoReciboDTO> findAll(Pageable pageable);

    /**
     * Get all the aplicacaoRecibos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AplicacaoReciboDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" aplicacaoRecibo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AplicacaoReciboDTO> findOne(Long id);

    /**
     * Delete the "id" aplicacaoRecibo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
