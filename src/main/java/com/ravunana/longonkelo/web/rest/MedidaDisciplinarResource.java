package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.MedidaDisciplinarRepository;
import com.ravunana.longonkelo.service.MedidaDisciplinarQueryService;
import com.ravunana.longonkelo.service.MedidaDisciplinarService;
import com.ravunana.longonkelo.service.criteria.MedidaDisciplinarCriteria;
import com.ravunana.longonkelo.service.dto.MedidaDisciplinarDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.MedidaDisciplinar}.
 */
@RestController
@RequestMapping("/api")
public class MedidaDisciplinarResource {

    private final Logger log = LoggerFactory.getLogger(MedidaDisciplinarResource.class);

    private static final String ENTITY_NAME = "medidaDisciplinar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedidaDisciplinarService medidaDisciplinarService;

    private final MedidaDisciplinarRepository medidaDisciplinarRepository;

    private final MedidaDisciplinarQueryService medidaDisciplinarQueryService;

    public MedidaDisciplinarResource(
        MedidaDisciplinarService medidaDisciplinarService,
        MedidaDisciplinarRepository medidaDisciplinarRepository,
        MedidaDisciplinarQueryService medidaDisciplinarQueryService
    ) {
        this.medidaDisciplinarService = medidaDisciplinarService;
        this.medidaDisciplinarRepository = medidaDisciplinarRepository;
        this.medidaDisciplinarQueryService = medidaDisciplinarQueryService;
    }

    /**
     * {@code POST  /medida-disciplinars} : Create a new medidaDisciplinar.
     *
     * @param medidaDisciplinarDTO the medidaDisciplinarDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medidaDisciplinarDTO, or with status {@code 400 (Bad Request)} if the medidaDisciplinar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medida-disciplinars")
    public ResponseEntity<MedidaDisciplinarDTO> createMedidaDisciplinar(@Valid @RequestBody MedidaDisciplinarDTO medidaDisciplinarDTO)
        throws URISyntaxException {
        log.debug("REST request to save MedidaDisciplinar : {}", medidaDisciplinarDTO);
        if (medidaDisciplinarDTO.getId() != null) {
            throw new BadRequestAlertException("A new medidaDisciplinar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedidaDisciplinarDTO result = medidaDisciplinarService.save(medidaDisciplinarDTO);
        return ResponseEntity
            .created(new URI("/api/medida-disciplinars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medida-disciplinars/:id} : Updates an existing medidaDisciplinar.
     *
     * @param id the id of the medidaDisciplinarDTO to save.
     * @param medidaDisciplinarDTO the medidaDisciplinarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medidaDisciplinarDTO,
     * or with status {@code 400 (Bad Request)} if the medidaDisciplinarDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medidaDisciplinarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medida-disciplinars/{id}")
    public ResponseEntity<MedidaDisciplinarDTO> updateMedidaDisciplinar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MedidaDisciplinarDTO medidaDisciplinarDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MedidaDisciplinar : {}, {}", id, medidaDisciplinarDTO);
        if (medidaDisciplinarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medidaDisciplinarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medidaDisciplinarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MedidaDisciplinarDTO result = medidaDisciplinarService.update(medidaDisciplinarDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medidaDisciplinarDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /medida-disciplinars/:id} : Partial updates given fields of an existing medidaDisciplinar, field will ignore if it is null
     *
     * @param id the id of the medidaDisciplinarDTO to save.
     * @param medidaDisciplinarDTO the medidaDisciplinarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medidaDisciplinarDTO,
     * or with status {@code 400 (Bad Request)} if the medidaDisciplinarDTO is not valid,
     * or with status {@code 404 (Not Found)} if the medidaDisciplinarDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the medidaDisciplinarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medida-disciplinars/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MedidaDisciplinarDTO> partialUpdateMedidaDisciplinar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MedidaDisciplinarDTO medidaDisciplinarDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MedidaDisciplinar partially : {}, {}", id, medidaDisciplinarDTO);
        if (medidaDisciplinarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medidaDisciplinarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medidaDisciplinarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedidaDisciplinarDTO> result = medidaDisciplinarService.partialUpdate(medidaDisciplinarDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medidaDisciplinarDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /medida-disciplinars} : get all the medidaDisciplinars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medidaDisciplinars in body.
     */
    @GetMapping("/medida-disciplinars")
    public ResponseEntity<List<MedidaDisciplinarDTO>> getAllMedidaDisciplinars(
        MedidaDisciplinarCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MedidaDisciplinars by criteria: {}", criteria);
        Page<MedidaDisciplinarDTO> page = medidaDisciplinarQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medida-disciplinars/count} : count all the medidaDisciplinars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/medida-disciplinars/count")
    public ResponseEntity<Long> countMedidaDisciplinars(MedidaDisciplinarCriteria criteria) {
        log.debug("REST request to count MedidaDisciplinars by criteria: {}", criteria);
        return ResponseEntity.ok().body(medidaDisciplinarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /medida-disciplinars/:id} : get the "id" medidaDisciplinar.
     *
     * @param id the id of the medidaDisciplinarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medidaDisciplinarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medida-disciplinars/{id}")
    public ResponseEntity<MedidaDisciplinarDTO> getMedidaDisciplinar(@PathVariable Long id) {
        log.debug("REST request to get MedidaDisciplinar : {}", id);
        Optional<MedidaDisciplinarDTO> medidaDisciplinarDTO = medidaDisciplinarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medidaDisciplinarDTO);
    }

    /**
     * {@code DELETE  /medida-disciplinars/:id} : delete the "id" medidaDisciplinar.
     *
     * @param id the id of the medidaDisciplinarDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medida-disciplinars/{id}")
    public ResponseEntity<Void> deleteMedidaDisciplinar(@PathVariable Long id) {
        log.debug("REST request to delete MedidaDisciplinar : {}", id);
        medidaDisciplinarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
