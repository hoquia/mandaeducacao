package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ResponsavelAreaFormacaoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ResponsavelAreaFormacao}.
 */
public interface ResponsavelAreaFormacaoService {
    /**
     * Save a responsavelAreaFormacao.
     *
     * @param responsavelAreaFormacaoDTO the entity to save.
     * @return the persisted entity.
     */
    ResponsavelAreaFormacaoDTO save(ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO);

    /**
     * Updates a responsavelAreaFormacao.
     *
     * @param responsavelAreaFormacaoDTO the entity to update.
     * @return the persisted entity.
     */
    ResponsavelAreaFormacaoDTO update(ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO);

    /**
     * Partially updates a responsavelAreaFormacao.
     *
     * @param responsavelAreaFormacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponsavelAreaFormacaoDTO> partialUpdate(ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO);

    /**
     * Get all the responsavelAreaFormacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelAreaFormacaoDTO> findAll(Pageable pageable);

    /**
     * Get all the responsavelAreaFormacaos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsavelAreaFormacaoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" responsavelAreaFormacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsavelAreaFormacaoDTO> findOne(Long id);

    /**
     * Delete the "id" responsavelAreaFormacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
