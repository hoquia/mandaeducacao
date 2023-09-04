package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ResponsavelTurnoRepository;
import com.ravunana.longonkelo.service.ResponsavelTurnoQueryService;
import com.ravunana.longonkelo.service.ResponsavelTurnoService;
import com.ravunana.longonkelo.service.criteria.ResponsavelTurnoCriteria;
import com.ravunana.longonkelo.service.dto.ResponsavelTurnoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ResponsavelTurno}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelTurnoResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelTurnoResource.class);

    private static final String ENTITY_NAME = "responsavelTurno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelTurnoService responsavelTurnoService;

    private final ResponsavelTurnoRepository responsavelTurnoRepository;

    private final ResponsavelTurnoQueryService responsavelTurnoQueryService;

    public ResponsavelTurnoResource(
        ResponsavelTurnoService responsavelTurnoService,
        ResponsavelTurnoRepository responsavelTurnoRepository,
        ResponsavelTurnoQueryService responsavelTurnoQueryService
    ) {
        this.responsavelTurnoService = responsavelTurnoService;
        this.responsavelTurnoRepository = responsavelTurnoRepository;
        this.responsavelTurnoQueryService = responsavelTurnoQueryService;
    }

    /**
     * {@code POST  /responsavel-turnos} : Create a new responsavelTurno.
     *
     * @param responsavelTurnoDTO the responsavelTurnoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavelTurnoDTO, or with status {@code 400 (Bad Request)} if the responsavelTurno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavel-turnos")
    public ResponseEntity<ResponsavelTurnoDTO> createResponsavelTurno(@Valid @RequestBody ResponsavelTurnoDTO responsavelTurnoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ResponsavelTurno : {}", responsavelTurnoDTO);
        if (responsavelTurnoDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsavelTurno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsavelTurnoDTO result = responsavelTurnoService.save(responsavelTurnoDTO);
        return ResponseEntity
            .created(new URI("/api/responsavel-turnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavel-turnos/:id} : Updates an existing responsavelTurno.
     *
     * @param id the id of the responsavelTurnoDTO to save.
     * @param responsavelTurnoDTO the responsavelTurnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelTurnoDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelTurnoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavelTurnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavel-turnos/{id}")
    public ResponseEntity<ResponsavelTurnoDTO> updateResponsavelTurno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResponsavelTurnoDTO responsavelTurnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ResponsavelTurno : {}, {}", id, responsavelTurnoDTO);
        if (responsavelTurnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelTurnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelTurnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponsavelTurnoDTO result = responsavelTurnoService.update(responsavelTurnoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelTurnoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /responsavel-turnos/:id} : Partial updates given fields of an existing responsavelTurno, field will ignore if it is null
     *
     * @param id the id of the responsavelTurnoDTO to save.
     * @param responsavelTurnoDTO the responsavelTurnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelTurnoDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelTurnoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the responsavelTurnoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the responsavelTurnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/responsavel-turnos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponsavelTurnoDTO> partialUpdateResponsavelTurno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResponsavelTurnoDTO responsavelTurnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponsavelTurno partially : {}, {}", id, responsavelTurnoDTO);
        if (responsavelTurnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsavelTurnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsavelTurnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponsavelTurnoDTO> result = responsavelTurnoService.partialUpdate(responsavelTurnoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavelTurnoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /responsavel-turnos} : get all the responsavelTurnos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavelTurnos in body.
     */
    @GetMapping("/responsavel-turnos")
    public ResponseEntity<List<ResponsavelTurnoDTO>> getAllResponsavelTurnos(
        ResponsavelTurnoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ResponsavelTurnos by criteria: {}", criteria);
        Page<ResponsavelTurnoDTO> page = responsavelTurnoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsavel-turnos/count} : count all the responsavelTurnos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/responsavel-turnos/count")
    public ResponseEntity<Long> countResponsavelTurnos(ResponsavelTurnoCriteria criteria) {
        log.debug("REST request to count ResponsavelTurnos by criteria: {}", criteria);
        return ResponseEntity.ok().body(responsavelTurnoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /responsavel-turnos/:id} : get the "id" responsavelTurno.
     *
     * @param id the id of the responsavelTurnoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavelTurnoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavel-turnos/{id}")
    public ResponseEntity<ResponsavelTurnoDTO> getResponsavelTurno(@PathVariable Long id) {
        log.debug("REST request to get ResponsavelTurno : {}", id);
        Optional<ResponsavelTurnoDTO> responsavelTurnoDTO = responsavelTurnoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavelTurnoDTO);
    }

    /**
     * {@code DELETE  /responsavel-turnos/:id} : delete the "id" responsavelTurno.
     *
     * @param id the id of the responsavelTurnoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavel-turnos/{id}")
    public ResponseEntity<Void> deleteResponsavelTurno(@PathVariable Long id) {
        log.debug("REST request to delete ResponsavelTurno : {}", id);
        responsavelTurnoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
