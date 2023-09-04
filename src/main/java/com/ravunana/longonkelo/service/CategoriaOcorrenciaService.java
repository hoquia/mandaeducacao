package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.CategoriaOcorrenciaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.CategoriaOcorrencia}.
 */
public interface CategoriaOcorrenciaService {
    /**
     * Save a categoriaOcorrencia.
     *
     * @param categoriaOcorrenciaDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriaOcorrenciaDTO save(CategoriaOcorrenciaDTO categoriaOcorrenciaDTO);

    /**
     * Updates a categoriaOcorrencia.
     *
     * @param categoriaOcorrenciaDTO the entity to update.
     * @return the persisted entity.
     */
    CategoriaOcorrenciaDTO update(CategoriaOcorrenciaDTO categoriaOcorrenciaDTO);

    /**
     * Partially updates a categoriaOcorrencia.
     *
     * @param categoriaOcorrenciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoriaOcorrenciaDTO> partialUpdate(CategoriaOcorrenciaDTO categoriaOcorrenciaDTO);

    /**
     * Get all the categoriaOcorrencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriaOcorrenciaDTO> findAll(Pageable pageable);

    /**
     * Get all the categoriaOcorrencias with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriaOcorrenciaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" categoriaOcorrencia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaOcorrenciaDTO> findOne(Long id);

    /**
     * Delete the "id" categoriaOcorrencia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
