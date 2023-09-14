package com.ravunana.longonkelo.service;

import com.ravunana.longonkelo.service.dto.LicaoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.ravunana.longonkelo.domain.Licao}.
 */
public interface LicaoService {
    /**
     * Save a licao.
     *
     * @param licaoDTO the entity to save.
     * @return the persisted entity.
     */
    LicaoDTO save(LicaoDTO licaoDTO);

    /**
     * Updates a licao.
     *
     * @param licaoDTO the entity to update.
     * @return the persisted entity.
     */
    LicaoDTO update(LicaoDTO licaoDTO);

    /**
     * Partially updates a licao.
     *
     * @param licaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LicaoDTO> partialUpdate(LicaoDTO licaoDTO);

    /**
     * Get all the licaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LicaoDTO> findAll(Pageable pageable);

    /**
     * Get all the licaos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LicaoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" licao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LicaoDTO> findOne(Long id);

    /**
     * Delete the "id" licao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Integer gerarNumeroLicao(LicaoDTO licaoDTO);

    String getChaveComposta(LicaoDTO licaoDTO);
}
