package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.AreaFormacaoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.AreaFormacao}.
 */
public interface AreaFormacaoService {
    /**
     * Save a areaFormacao.
     *
     * @param areaFormacaoDTO the entity to save.
     * @return the persisted entity.
     */
    AreaFormacaoDTO save(AreaFormacaoDTO areaFormacaoDTO);

    /**
     * Updates a areaFormacao.
     *
     * @param areaFormacaoDTO the entity to update.
     * @return the persisted entity.
     */
    AreaFormacaoDTO update(AreaFormacaoDTO areaFormacaoDTO);

    /**
     * Partially updates a areaFormacao.
     *
     * @param areaFormacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AreaFormacaoDTO> partialUpdate(AreaFormacaoDTO areaFormacaoDTO);

    /**
     * Get all the areaFormacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AreaFormacaoDTO> findAll(Pageable pageable);

    /**
     * Get all the areaFormacaos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AreaFormacaoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" areaFormacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AreaFormacaoDTO> findOne(Long id);

    /**
     * Delete the "id" areaFormacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
