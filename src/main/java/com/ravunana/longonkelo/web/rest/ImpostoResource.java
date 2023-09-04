package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ImpostoRepository;
import com.ravunana.longonkelo.service.ImpostoService;
import com.ravunana.longonkelo.service.dto.ImpostoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.Imposto}.
 */
@RestController
@RequestMapping("/api")
public class ImpostoResource {

    private final Logger log = LoggerFactory.getLogger(ImpostoResource.class);

    private static final String ENTITY_NAME = "imposto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpostoService impostoService;

    private final ImpostoRepository impostoRepository;

    public ImpostoResource(ImpostoService impostoService, ImpostoRepository impostoRepository) {
        this.impostoService = impostoService;
        this.impostoRepository = impostoRepository;
    }

    /**
     * {@code POST  /impostos} : Create a new imposto.
     *
     * @param impostoDTO the impostoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new impostoDTO, or with status {@code 400 (Bad Request)} if the imposto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/impostos")
    public ResponseEntity<ImpostoDTO> createImposto(@Valid @RequestBody ImpostoDTO impostoDTO) throws URISyntaxException {
        log.debug("REST request to save Imposto : {}", impostoDTO);
        if (impostoDTO.getId() != null) {
            throw new BadRequestAlertException("A new imposto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImpostoDTO result = impostoService.save(impostoDTO);
        return ResponseEntity
            .created(new URI("/api/impostos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /impostos/:id} : Updates an existing imposto.
     *
     * @param id the id of the impostoDTO to save.
     * @param impostoDTO the impostoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoDTO,
     * or with status {@code 400 (Bad Request)} if the impostoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the impostoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/impostos/{id}")
    public ResponseEntity<ImpostoDTO> updateImposto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ImpostoDTO impostoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Imposto : {}, {}", id, impostoDTO);
        if (impostoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ImpostoDTO result = impostoService.update(impostoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /impostos/:id} : Partial updates given fields of an existing imposto, field will ignore if it is null
     *
     * @param id the id of the impostoDTO to save.
     * @param impostoDTO the impostoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoDTO,
     * or with status {@code 400 (Bad Request)} if the impostoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the impostoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the impostoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/impostos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ImpostoDTO> partialUpdateImposto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ImpostoDTO impostoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Imposto partially : {}, {}", id, impostoDTO);
        if (impostoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, impostoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!impostoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ImpostoDTO> result = impostoService.partialUpdate(impostoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /impostos} : get all the impostos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impostos in body.
     */
    @GetMapping("/impostos")
    public ResponseEntity<List<ImpostoDTO>> getAllImpostos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Impostos");
        Page<ImpostoDTO> page;
        if (eagerload) {
            page = impostoService.findAllWithEagerRelationships(pageable);
        } else {
            page = impostoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /impostos/:id} : get the "id" imposto.
     *
     * @param id the id of the impostoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impostoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/impostos/{id}")
    public ResponseEntity<ImpostoDTO> getImposto(@PathVariable Long id) {
        log.debug("REST request to get Imposto : {}", id);
        Optional<ImpostoDTO> impostoDTO = impostoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impostoDTO);
    }

    /**
     * {@code DELETE  /impostos/:id} : delete the "id" imposto.
     *
     * @param id the id of the impostoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/impostos/{id}")
    public ResponseEntity<Void> deleteImposto(@PathVariable Long id) {
        log.debug("REST request to delete Imposto : {}", id);
        impostoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
