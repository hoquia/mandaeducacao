package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.CategoriaEmolumentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.CategoriaEmolumento}.
 */
public interface CategoriaEmolumentoService {
    /**
     * Save a categoriaEmolumento.
     *
     * @param categoriaEmolumentoDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriaEmolumentoDTO save(CategoriaEmolumentoDTO categoriaEmolumentoDTO);

    /**
     * Updates a categoriaEmolumento.
     *
     * @param categoriaEmolumentoDTO the entity to update.
     * @return the persisted entity.
     */
    CategoriaEmolumentoDTO update(CategoriaEmolumentoDTO categoriaEmolumentoDTO);

    /**
     * Partially updates a categoriaEmolumento.
     *
     * @param categoriaEmolumentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoriaEmolumentoDTO> partialUpdate(CategoriaEmolumentoDTO categoriaEmolumentoDTO);

    /**
     * Get all the categoriaEmolumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriaEmolumentoDTO> findAll(Pageable pageable);

    /**
     * Get all the categoriaEmolumentos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriaEmolumentoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" categoriaEmolumento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaEmolumentoDTO> findOne(Long id);

    /**
     * Delete the "id" categoriaEmolumento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
