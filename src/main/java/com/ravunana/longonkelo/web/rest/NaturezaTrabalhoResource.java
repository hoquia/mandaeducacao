package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.NaturezaTrabalhoRepository;
import com.ravunana.longonkelo.service.NaturezaTrabalhoQueryService;
import com.ravunana.longonkelo.service.NaturezaTrabalhoService;
import com.ravunana.longonkelo.service.criteria.NaturezaTrabalhoCriteria;
import com.ravunana.longonkelo.service.dto.NaturezaTrabalhoDTO;
import com.ravunana.longonkelo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ravunana.longonkelo.domain.NaturezaTrabalho}.
 */
@RestController
@RequestMapping("/api")
public class NaturezaTrabalhoResource {

    private final Logger log = LoggerFactory.getLogger(NaturezaTrabalhoResource.class);

    private static final String ENTITY_NAME = "naturezaTrabalho";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaturezaTrabalhoService naturezaTrabalhoService;

    private final NaturezaTrabalhoRepository naturezaTrabalhoRepository;

    private final NaturezaTrabalhoQueryService naturezaTrabalhoQueryService;

    public NaturezaTrabalhoResource(
        NaturezaTrabalhoService naturezaTrabalhoService,
        NaturezaTrabalhoRepository naturezaTrabalhoRepository,
        NaturezaTrabalhoQueryService naturezaTrabalhoQueryService
    ) {
        this.naturezaTrabalhoService = naturezaTrabalhoService;
        this.naturezaTrabalhoRepository = naturezaTrabalhoRepository;
        this.naturezaTrabalhoQueryService = naturezaTrabalhoQueryService;
    }

    /**
     * {@code POST  /natureza-trabalhos} : Create a new naturezaTrabalho.
     *
     * @param naturezaTrabalhoDTO the naturezaTrabalhoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naturezaTrabalhoDTO, or with status {@code 400 (Bad Request)} if the naturezaTrabalho has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/natureza-trabalhos")
    public ResponseEntity<NaturezaTrabalhoDTO> createNaturezaTrabalho(@Valid @RequestBody NaturezaTrabalhoDTO naturezaTrabalhoDTO)
        throws URISyntaxException {
        log.debug("REST request to save NaturezaTrabalho : {}", naturezaTrabalhoDTO);
        if (naturezaTrabalhoDTO.getId() != null) {
            throw new BadRequestAlertException("A new naturezaTrabalho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NaturezaTrabalhoDTO result = naturezaTrabalhoService.save(naturezaTrabalhoDTO);
        return ResponseEntity
            .created(new URI("/api/natureza-trabalhos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /natureza-trabalhos/:id} : Updates an existing naturezaTrabalho.
     *
     * @param id the id of the naturezaTrabalhoDTO to save.
     * @param naturezaTrabalhoDTO the naturezaTrabalhoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naturezaTrabalhoDTO,
     * or with status {@code 400 (Bad Request)} if the naturezaTrabalhoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the naturezaTrabalhoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/natureza-trabalhos/{id}")
    public ResponseEntity<NaturezaTrabalhoDTO> updateNaturezaTrabalho(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NaturezaTrabalhoDTO naturezaTrabalhoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NaturezaTrabalho : {}, {}", id, naturezaTrabalhoDTO);
        if (naturezaTrabalhoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naturezaTrabalhoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naturezaTrabalhoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NaturezaTrabalhoDTO result = naturezaTrabalhoService.update(naturezaTrabalhoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, naturezaTrabalhoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /natureza-trabalhos/:id} : Partial updates given fields of an existing naturezaTrabalho, field will ignore if it is null
     *
     * @param id the id of the naturezaTrabalhoDTO to save.
     * @param naturezaTrabalhoDTO the naturezaTrabalhoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naturezaTrabalhoDTO,
     * or with status {@code 400 (Bad Request)} if the naturezaTrabalhoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the naturezaTrabalhoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the naturezaTrabalhoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/natureza-trabalhos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NaturezaTrabalhoDTO> partialUpdateNaturezaTrabalho(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NaturezaTrabalhoDTO naturezaTrabalhoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NaturezaTrabalho partially : {}, {}", id, naturezaTrabalhoDTO);
        if (naturezaTrabalhoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, naturezaTrabalhoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!naturezaTrabalhoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NaturezaTrabalhoDTO> result = naturezaTrabalhoService.partialUpdate(naturezaTrabalhoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, naturezaTrabalhoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /natureza-trabalhos} : get all the naturezaTrabalhos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naturezaTrabalhos in body.
     */
    @GetMapping("/natureza-trabalhos")
    public ResponseEntity<List<NaturezaTrabalhoDTO>> getAllNaturezaTrabalhos(
        NaturezaTrabalhoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get NaturezaTrabalhos by criteria: {}", criteria);
        Page<NaturezaTrabalhoDTO> page = naturezaTrabalhoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /natureza-trabalhos/count} : count all the naturezaTrabalhos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/natureza-trabalhos/count")
    public ResponseEntity<Long> countNaturezaTrabalhos(NaturezaTrabalhoCriteria criteria) {
        log.debug("REST request to count NaturezaTrabalhos by criteria: {}", criteria);
        return ResponseEntity.ok().body(naturezaTrabalhoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /natureza-trabalhos/:id} : get the "id" naturezaTrabalho.
     *
     * @param id the id of the naturezaTrabalhoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naturezaTrabalhoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/natureza-trabalhos/{id}")
    public ResponseEntity<NaturezaTrabalhoDTO> getNaturezaTrabalho(@PathVariable Long id) {
        log.debug("REST request to get NaturezaTrabalho : {}", id);
        Optional<NaturezaTrabalhoDTO> naturezaTrabalhoDTO = naturezaTrabalhoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(naturezaTrabalhoDTO);
    }

    /**
     * {@code DELETE  /natureza-trabalhos/:id} : delete the "id" naturezaTrabalho.
     *
     * @param id the id of the naturezaTrabalhoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/natureza-trabalhos/{id}")
    public ResponseEntity<Void> deleteNaturezaTrabalho(@PathVariable Long id) {
        log.debug("REST request to delete NaturezaTrabalho : {}", id);
        naturezaTrabalhoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
