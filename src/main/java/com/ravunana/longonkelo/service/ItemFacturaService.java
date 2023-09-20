package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ItemFactura}.
 */
public interface ItemFacturaService {
    /**
     * Save a itemFactura.
     *
     * @param itemFacturaDTO the entity to save.
     * @return the persisted entity.
     */
    ItemFacturaDTO save(ItemFacturaDTO itemFacturaDTO);

    /**
     * Updates a itemFactura.
     *
     * @param itemFacturaDTO the entity to update.
     * @return the persisted entity.
     */
    ItemFacturaDTO update(ItemFacturaDTO itemFacturaDTO);

    /**
     * Partially updates a itemFactura.
     *
     * @param itemFacturaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ItemFacturaDTO> partialUpdate(ItemFacturaDTO itemFacturaDTO);

    /**
     * Get all the itemFacturas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemFacturaDTO> findAll(Pageable pageable);

    /**
     * Get all the itemFacturas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemFacturaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" itemFactura.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemFacturaDTO> findOne(Long id);

    /**
     * Delete the "id" itemFactura.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ItemFacturaDTO> getItemsFacturaByTurmaAndEmolumento(Long turmaID, Long emolumentoID);

    List<ItemFacturaDTO> getItensFacturaWithCategoria(Long catregoriaEmolumentoID, Long classeID);
}
