package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.AnoLectivoRepository;
import com.ravunana.longonkelo.service.AnoLectivoService;
import com.ravunana.longonkelo.service.dto.AnoLectivoDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ravunana.longonkelo.domain.AnoLectivo}.
 */
@RestController
@RequestMapping("/api")
public class AnoLectivoResource {

    private final Logger log = LoggerFactory.getLogger(AnoLectivoResource.class);

    private static final String ENTITY_NAME = "anoLectivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnoLectivoService anoLectivoService;

    private final AnoLectivoRepository anoLectivoRepository;

    public AnoLectivoResource(AnoLectivoService anoLectivoService, AnoLectivoRepository anoLectivoRepository) {
        this.anoLectivoService = anoLectivoService;
        this.anoLectivoRepository = anoLectivoRepository;
    }

    /**
     * {@code POST  /ano-lectivos} : Create a new anoLectivo.
     *
     * @param anoLectivoDTO the anoLectivoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anoLectivoDTO, or with status {@code 400 (Bad Request)} if the anoLectivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ano-lectivos")
    public ResponseEntity<AnoLectivoDTO> createAnoLectivo(@Valid @RequestBody AnoLectivoDTO anoLectivoDTO) throws URISyntaxException {
        log.debug("REST request to save AnoLectivo : {}", anoLectivoDTO);
        if (anoLectivoDTO.getId() != null) {
            throw new BadRequestAlertException("A new anoLectivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnoLectivoDTO result = anoLectivoService.save(anoLectivoDTO);
        return ResponseEntity
            .created(new URI("/api/ano-lectivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ano-lectivos/:id} : Updates an existing anoLectivo.
     *
     * @param id the id of the anoLectivoDTO to save.
     * @param anoLectivoDTO the anoLectivoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anoLectivoDTO,
     * or with status {@code 400 (Bad Request)} if the anoLectivoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anoLectivoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ano-lectivos/{id}")
    public ResponseEntity<AnoLectivoDTO> updateAnoLectivo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnoLectivoDTO anoLectivoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnoLectivo : {}, {}", id, anoLectivoDTO);
        if (anoLectivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anoLectivoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anoLectivoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnoLectivoDTO result = anoLectivoService.update(anoLectivoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anoLectivoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ano-lectivos/:id} : Partial updates given fields of an existing anoLectivo, field will ignore if it is null
     *
     * @param id the id of the anoLectivoDTO to save.
     * @param anoLectivoDTO the anoLectivoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anoLectivoDTO,
     * or with status {@code 400 (Bad Request)} if the anoLectivoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anoLectivoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anoLectivoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ano-lectivos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnoLectivoDTO> partialUpdateAnoLectivo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnoLectivoDTO anoLectivoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnoLectivo partially : {}, {}", id, anoLectivoDTO);
        if (anoLectivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anoLectivoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anoLectivoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnoLectivoDTO> result = anoLectivoService.partialUpdate(anoLectivoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anoLectivoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ano-lectivos} : get all the anoLectivos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anoLectivos in body.
     */
    @GetMapping("/ano-lectivos")
    public ResponseEntity<List<AnoLectivoDTO>> getAllAnoLectivos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of AnoLectivos");
        Page<AnoLectivoDTO> page;
        if (eagerload) {
            page = anoLectivoService.findAllWithEagerRelationships(pageable);
        } else {
            page = anoLectivoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ano-lectivos/:id} : get the "id" anoLectivo.
     *
     * @param id the id of the anoLectivoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anoLectivoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ano-lectivos/{id}")
    public ResponseEntity<AnoLectivoDTO> getAnoLectivo(@PathVariable Long id) {
        log.debug("REST request to get AnoLectivo : {}", id);
        Optional<AnoLectivoDTO> anoLectivoDTO = anoLectivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anoLectivoDTO);
    }

    /**
     * {@code DELETE  /ano-lectivos/:id} : delete the "id" anoLectivo.
     *
     * @param id the id of the anoLectivoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ano-lectivos/{id}")
    public ResponseEntity<Void> deleteAnoLectivo(@PathVariable Long id) {
        log.debug("REST request to delete AnoLectivo : {}", id);
        anoLectivoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
