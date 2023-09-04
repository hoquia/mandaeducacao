package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.TurnoRepository;
import com.ravunana.longonkelo.service.TurnoService;
import com.ravunana.longonkelo.service.dto.TurnoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Turno}.
 */
@RestController
@RequestMapping("/api")
public class TurnoResource {

    private final Logger log = LoggerFactory.getLogger(TurnoResource.class);

    private static final String ENTITY_NAME = "turno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TurnoService turnoService;

    private final TurnoRepository turnoRepository;

    public TurnoResource(TurnoService turnoService, TurnoRepository turnoRepository) {
        this.turnoService = turnoService;
        this.turnoRepository = turnoRepository;
    }

    /**
     * {@code POST  /turnos} : Create a new turno.
     *
     * @param turnoDTO the turnoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new turnoDTO, or with status {@code 400 (Bad Request)} if the turno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/turnos")
    public ResponseEntity<TurnoDTO> createTurno(@Valid @RequestBody TurnoDTO turnoDTO) throws URISyntaxException {
        log.debug("REST request to save Turno : {}", turnoDTO);
        if (turnoDTO.getId() != null) {
            throw new BadRequestAlertException("A new turno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TurnoDTO result = turnoService.save(turnoDTO);
        return ResponseEntity
            .created(new URI("/api/turnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /turnos/:id} : Updates an existing turno.
     *
     * @param id the id of the turnoDTO to save.
     * @param turnoDTO the turnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated turnoDTO,
     * or with status {@code 400 (Bad Request)} if the turnoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the turnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/turnos/{id}")
    public ResponseEntity<TurnoDTO> updateTurno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TurnoDTO turnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Turno : {}, {}", id, turnoDTO);
        if (turnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, turnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!turnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TurnoDTO result = turnoService.update(turnoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, turnoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /turnos/:id} : Partial updates given fields of an existing turno, field will ignore if it is null
     *
     * @param id the id of the turnoDTO to save.
     * @param turnoDTO the turnoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated turnoDTO,
     * or with status {@code 400 (Bad Request)} if the turnoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the turnoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the turnoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/turnos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TurnoDTO> partialUpdateTurno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TurnoDTO turnoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Turno partially : {}, {}", id, turnoDTO);
        if (turnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, turnoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!turnoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TurnoDTO> result = turnoService.partialUpdate(turnoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, turnoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /turnos} : get all the turnos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of turnos in body.
     */
    @GetMapping("/turnos")
    public ResponseEntity<List<TurnoDTO>> getAllTurnos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Turnos");
        Page<TurnoDTO> page = turnoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /turnos/:id} : get the "id" turno.
     *
     * @param id the id of the turnoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the turnoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/turnos/{id}")
    public ResponseEntity<TurnoDTO> getTurno(@PathVariable Long id) {
        log.debug("REST request to get Turno : {}", id);
        Optional<TurnoDTO> turnoDTO = turnoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(turnoDTO);
    }

    /**
     * {@code DELETE  /turnos/:id} : delete the "id" turno.
     *
     * @param id the id of the turnoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/turnos/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        log.debug("REST request to delete Turno : {}", id);
        turnoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
