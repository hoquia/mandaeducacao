package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.DisciplinaCurricularDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.DisciplinaCurricular}.
 */
public interface DisciplinaCurricularService {
    /**
     * Save a disciplinaCurricular.
     *
     * @param disciplinaCurricularDTO the entity to save.
     * @return the persisted entity.
     */
    DisciplinaCurricularDTO save(DisciplinaCurricularDTO disciplinaCurricularDTO);

    /**
     * Updates a disciplinaCurricular.
     *
     * @param disciplinaCurricularDTO the entity to update.
     * @return the persisted entity.
     */
    DisciplinaCurricularDTO update(DisciplinaCurricularDTO disciplinaCurricularDTO);

    /**
     * Partially updates a disciplinaCurricular.
     *
     * @param disciplinaCurricularDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DisciplinaCurricularDTO> partialUpdate(DisciplinaCurricularDTO disciplinaCurricularDTO);

    /**
     * Get all the disciplinaCurriculars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisciplinaCurricularDTO> findAll(Pageable pageable);

    /**
     * Get all the disciplinaCurriculars with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisciplinaCurricularDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" disciplinaCurricular.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DisciplinaCurricularDTO> findOne(Long id);

    /**
     * Delete the "id" disciplinaCurricular.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
