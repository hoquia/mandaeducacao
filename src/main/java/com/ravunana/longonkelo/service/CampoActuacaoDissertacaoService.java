package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.CampoActuacaoDissertacaoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.CampoActuacaoDissertacao}.
 */
public interface CampoActuacaoDissertacaoService {
    /**
     * Save a campoActuacaoDissertacao.
     *
     * @param campoActuacaoDissertacaoDTO the entity to save.
     * @return the persisted entity.
     */
    CampoActuacaoDissertacaoDTO save(CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO);

    /**
     * Updates a campoActuacaoDissertacao.
     *
     * @param campoActuacaoDissertacaoDTO the entity to update.
     * @return the persisted entity.
     */
    CampoActuacaoDissertacaoDTO update(CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO);

    /**
     * Partially updates a campoActuacaoDissertacao.
     *
     * @param campoActuacaoDissertacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CampoActuacaoDissertacaoDTO> partialUpdate(CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO);

    /**
     * Get all the campoActuacaoDissertacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampoActuacaoDissertacaoDTO> findAll(Pageable pageable);

    /**
     * Get all the campoActuacaoDissertacaos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampoActuacaoDissertacaoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" campoActuacaoDissertacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampoActuacaoDissertacaoDTO> findOne(Long id);

    /**
     * Delete the "id" campoActuacaoDissertacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
