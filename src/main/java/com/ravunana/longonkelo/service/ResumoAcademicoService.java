package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ResumoAcademicoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ResumoAcademico}.
 */
public interface ResumoAcademicoService {
    /**
     * Save a resumoAcademico.
     *
     * @param resumoAcademicoDTO the entity to save.
     * @return the persisted entity.
     */
    ResumoAcademicoDTO save(ResumoAcademicoDTO resumoAcademicoDTO);

    /**
     * Updates a resumoAcademico.
     *
     * @param resumoAcademicoDTO the entity to update.
     * @return the persisted entity.
     */
    ResumoAcademicoDTO update(ResumoAcademicoDTO resumoAcademicoDTO);

    /**
     * Partially updates a resumoAcademico.
     *
     * @param resumoAcademicoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResumoAcademicoDTO> partialUpdate(ResumoAcademicoDTO resumoAcademicoDTO);

    /**
     * Get all the resumoAcademicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResumoAcademicoDTO> findAll(Pageable pageable);

    /**
     * Get all the resumoAcademicos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResumoAcademicoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" resumoAcademico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResumoAcademicoDTO> findOne(Long id);

    /**
     * Delete the "id" resumoAcademico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
