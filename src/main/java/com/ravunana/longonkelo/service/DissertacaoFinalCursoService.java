package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.DissertacaoFinalCursoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.DissertacaoFinalCurso}.
 */
public interface DissertacaoFinalCursoService {
    /**
     * Save a dissertacaoFinalCurso.
     *
     * @param dissertacaoFinalCursoDTO the entity to save.
     * @return the persisted entity.
     */
    DissertacaoFinalCursoDTO save(DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO);

    /**
     * Updates a dissertacaoFinalCurso.
     *
     * @param dissertacaoFinalCursoDTO the entity to update.
     * @return the persisted entity.
     */
    DissertacaoFinalCursoDTO update(DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO);

    /**
     * Partially updates a dissertacaoFinalCurso.
     *
     * @param dissertacaoFinalCursoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DissertacaoFinalCursoDTO> partialUpdate(DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO);

    /**
     * Get all the dissertacaoFinalCursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DissertacaoFinalCursoDTO> findAll(Pageable pageable);

    /**
     * Get all the dissertacaoFinalCursos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DissertacaoFinalCursoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" dissertacaoFinalCurso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DissertacaoFinalCursoDTO> findOne(Long id);

    /**
     * Delete the "id" dissertacaoFinalCurso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
