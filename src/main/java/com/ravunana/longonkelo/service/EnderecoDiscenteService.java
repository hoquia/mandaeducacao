package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.EnderecoDiscenteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.EnderecoDiscente}.
 */
public interface EnderecoDiscenteService {
    /**
     * Save a enderecoDiscente.
     *
     * @param enderecoDiscenteDTO the entity to save.
     * @return the persisted entity.
     */
    EnderecoDiscenteDTO save(EnderecoDiscenteDTO enderecoDiscenteDTO);

    /**
     * Updates a enderecoDiscente.
     *
     * @param enderecoDiscenteDTO the entity to update.
     * @return the persisted entity.
     */
    EnderecoDiscenteDTO update(EnderecoDiscenteDTO enderecoDiscenteDTO);

    /**
     * Partially updates a enderecoDiscente.
     *
     * @param enderecoDiscenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EnderecoDiscenteDTO> partialUpdate(EnderecoDiscenteDTO enderecoDiscenteDTO);

    /**
     * Get all the enderecoDiscentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EnderecoDiscenteDTO> findAll(Pageable pageable);

    /**
     * Get all the enderecoDiscentes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EnderecoDiscenteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" enderecoDiscente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnderecoDiscenteDTO> findOne(Long id);

    /**
     * Delete the "id" enderecoDiscente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
