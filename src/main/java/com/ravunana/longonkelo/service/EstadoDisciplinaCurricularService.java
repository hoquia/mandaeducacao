package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular}.
 */
public interface EstadoDisciplinaCurricularService {
    /**
     * Save a estadoDisciplinaCurricular.
     *
     * @param estadoDisciplinaCurricularDTO the entity to save.
     * @return the persisted entity.
     */
    EstadoDisciplinaCurricularDTO save(EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO);

    /**
     * Updates a estadoDisciplinaCurricular.
     *
     * @param estadoDisciplinaCurricularDTO the entity to update.
     * @return the persisted entity.
     */
    EstadoDisciplinaCurricularDTO update(EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO);

    /**
     * Partially updates a estadoDisciplinaCurricular.
     *
     * @param estadoDisciplinaCurricularDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstadoDisciplinaCurricularDTO> partialUpdate(EstadoDisciplinaCurricularDTO estadoDisciplinaCurricularDTO);

    /**
     * Get all the estadoDisciplinaCurriculars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstadoDisciplinaCurricularDTO> findAll(Pageable pageable);

    /**
     * Get all the estadoDisciplinaCurriculars with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstadoDisciplinaCurricularDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" estadoDisciplinaCurricular.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadoDisciplinaCurricularDTO> findOne(Long id);

    /**
     * Delete the "id" estadoDisciplinaCurricular.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
