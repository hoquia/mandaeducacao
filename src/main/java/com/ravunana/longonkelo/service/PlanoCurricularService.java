package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.PlanoCurricularDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.PlanoCurricular}.
 */
public interface PlanoCurricularService {
    /**
     * Save a planoCurricular.
     *
     * @param planoCurricularDTO the entity to save.
     * @return the persisted entity.
     */
    PlanoCurricularDTO save(PlanoCurricularDTO planoCurricularDTO);

    /**
     * Updates a planoCurricular.
     *
     * @param planoCurricularDTO the entity to update.
     * @return the persisted entity.
     */
    PlanoCurricularDTO update(PlanoCurricularDTO planoCurricularDTO);

    /**
     * Partially updates a planoCurricular.
     *
     * @param planoCurricularDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanoCurricularDTO> partialUpdate(PlanoCurricularDTO planoCurricularDTO);

    /**
     * Get all the planoCurriculars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoCurricularDTO> findAll(Pageable pageable);

    /**
     * Get all the planoCurriculars with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanoCurricularDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" planoCurricular.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanoCurricularDTO> findOne(Long id);

    /**
     * Delete the "id" planoCurricular.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    String getDescricaoPlanoCurricular(String curso, String classe);
}
