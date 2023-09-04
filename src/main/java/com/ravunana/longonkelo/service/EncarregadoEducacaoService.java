package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.EncarregadoEducacaoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.EncarregadoEducacao}.
 */
public interface EncarregadoEducacaoService {
    /**
     * Save a encarregadoEducacao.
     *
     * @param encarregadoEducacaoDTO the entity to save.
     * @return the persisted entity.
     */
    EncarregadoEducacaoDTO save(EncarregadoEducacaoDTO encarregadoEducacaoDTO);

    /**
     * Updates a encarregadoEducacao.
     *
     * @param encarregadoEducacaoDTO the entity to update.
     * @return the persisted entity.
     */
    EncarregadoEducacaoDTO update(EncarregadoEducacaoDTO encarregadoEducacaoDTO);

    /**
     * Partially updates a encarregadoEducacao.
     *
     * @param encarregadoEducacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EncarregadoEducacaoDTO> partialUpdate(EncarregadoEducacaoDTO encarregadoEducacaoDTO);

    /**
     * Get all the encarregadoEducacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EncarregadoEducacaoDTO> findAll(Pageable pageable);

    /**
     * Get all the encarregadoEducacaos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EncarregadoEducacaoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" encarregadoEducacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EncarregadoEducacaoDTO> findOne(Long id);

    /**
     * Delete the "id" encarregadoEducacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
