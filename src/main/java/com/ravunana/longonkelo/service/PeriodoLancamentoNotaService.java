package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.PeriodoLancamentoNotaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.PeriodoLancamentoNota}.
 */
public interface PeriodoLancamentoNotaService {
    /**
     * Save a periodoLancamentoNota.
     *
     * @param periodoLancamentoNotaDTO the entity to save.
     * @return the persisted entity.
     */
    PeriodoLancamentoNotaDTO save(PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO);

    /**
     * Updates a periodoLancamentoNota.
     *
     * @param periodoLancamentoNotaDTO the entity to update.
     * @return the persisted entity.
     */
    PeriodoLancamentoNotaDTO update(PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO);

    /**
     * Partially updates a periodoLancamentoNota.
     *
     * @param periodoLancamentoNotaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PeriodoLancamentoNotaDTO> partialUpdate(PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO);

    /**
     * Get all the periodoLancamentoNotas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PeriodoLancamentoNotaDTO> findAll(Pageable pageable);

    /**
     * Get all the periodoLancamentoNotas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PeriodoLancamentoNotaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" periodoLancamentoNota.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeriodoLancamentoNotaDTO> findOne(Long id);

    /**
     * Delete the "id" periodoLancamentoNota.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
