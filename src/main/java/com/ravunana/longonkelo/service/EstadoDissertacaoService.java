package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.EstadoDissertacaoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.EstadoDissertacao}.
 */
public interface EstadoDissertacaoService {
    /**
     * Save a estadoDissertacao.
     *
     * @param estadoDissertacaoDTO the entity to save.
     * @return the persisted entity.
     */
    EstadoDissertacaoDTO save(EstadoDissertacaoDTO estadoDissertacaoDTO);

    /**
     * Updates a estadoDissertacao.
     *
     * @param estadoDissertacaoDTO the entity to update.
     * @return the persisted entity.
     */
    EstadoDissertacaoDTO update(EstadoDissertacaoDTO estadoDissertacaoDTO);

    /**
     * Partially updates a estadoDissertacao.
     *
     * @param estadoDissertacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstadoDissertacaoDTO> partialUpdate(EstadoDissertacaoDTO estadoDissertacaoDTO);

    /**
     * Get all the estadoDissertacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstadoDissertacaoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" estadoDissertacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadoDissertacaoDTO> findOne(Long id);

    /**
     * Delete the "id" estadoDissertacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
