package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.FormacaoDocenteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.FormacaoDocente}.
 */
public interface FormacaoDocenteService {
    /**
     * Save a formacaoDocente.
     *
     * @param formacaoDocenteDTO the entity to save.
     * @return the persisted entity.
     */
    FormacaoDocenteDTO save(FormacaoDocenteDTO formacaoDocenteDTO);

    /**
     * Updates a formacaoDocente.
     *
     * @param formacaoDocenteDTO the entity to update.
     * @return the persisted entity.
     */
    FormacaoDocenteDTO update(FormacaoDocenteDTO formacaoDocenteDTO);

    /**
     * Partially updates a formacaoDocente.
     *
     * @param formacaoDocenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormacaoDocenteDTO> partialUpdate(FormacaoDocenteDTO formacaoDocenteDTO);

    /**
     * Get all the formacaoDocentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormacaoDocenteDTO> findAll(Pageable pageable);

    /**
     * Get all the formacaoDocentes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormacaoDocenteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" formacaoDocente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormacaoDocenteDTO> findOne(Long id);

    /**
     * Delete the "id" formacaoDocente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
