package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.MeioPagamentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.MeioPagamento}.
 */
public interface MeioPagamentoService {
    /**
     * Save a meioPagamento.
     *
     * @param meioPagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    MeioPagamentoDTO save(MeioPagamentoDTO meioPagamentoDTO);

    /**
     * Updates a meioPagamento.
     *
     * @param meioPagamentoDTO the entity to update.
     * @return the persisted entity.
     */
    MeioPagamentoDTO update(MeioPagamentoDTO meioPagamentoDTO);

    /**
     * Partially updates a meioPagamento.
     *
     * @param meioPagamentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MeioPagamentoDTO> partialUpdate(MeioPagamentoDTO meioPagamentoDTO);

    /**
     * Get all the meioPagamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MeioPagamentoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" meioPagamento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MeioPagamentoDTO> findOne(Long id);

    /**
     * Delete the "id" meioPagamento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
