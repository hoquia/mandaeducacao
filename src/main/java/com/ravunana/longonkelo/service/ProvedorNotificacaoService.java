package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.ProvedorNotificacaoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.ProvedorNotificacao}.
 */
public interface ProvedorNotificacaoService {
    /**
     * Save a provedorNotificacao.
     *
     * @param provedorNotificacaoDTO the entity to save.
     * @return the persisted entity.
     */
    ProvedorNotificacaoDTO save(ProvedorNotificacaoDTO provedorNotificacaoDTO);

    /**
     * Updates a provedorNotificacao.
     *
     * @param provedorNotificacaoDTO the entity to update.
     * @return the persisted entity.
     */
    ProvedorNotificacaoDTO update(ProvedorNotificacaoDTO provedorNotificacaoDTO);

    /**
     * Partially updates a provedorNotificacao.
     *
     * @param provedorNotificacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProvedorNotificacaoDTO> partialUpdate(ProvedorNotificacaoDTO provedorNotificacaoDTO);

    /**
     * Get all the provedorNotificacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProvedorNotificacaoDTO> findAll(Pageable pageable);

    /**
     * Get all the provedorNotificacaos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProvedorNotificacaoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" provedorNotificacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProvedorNotificacaoDTO> findOne(Long id);

    /**
     * Delete the "id" provedorNotificacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
