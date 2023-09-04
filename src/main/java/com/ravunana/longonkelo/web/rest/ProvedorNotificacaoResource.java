package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ProvedorNotificacaoRepository;
import com.ravunana.longonkelo.service.ProvedorNotificacaoService;
import com.ravunana.longonkelo.service.dto.ProvedorNotificacaoDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ProvedorNotificacao}.
 */
@RestController
@RequestMapping("/api")
public class ProvedorNotificacaoResource {

    private final Logger log = LoggerFactory.getLogger(ProvedorNotificacaoResource.class);

    private static final String ENTITY_NAME = "provedorNotificacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvedorNotificacaoService provedorNotificacaoService;

    private final ProvedorNotificacaoRepository provedorNotificacaoRepository;

    public ProvedorNotificacaoResource(
        ProvedorNotificacaoService provedorNotificacaoService,
        ProvedorNotificacaoRepository provedorNotificacaoRepository
    ) {
        this.provedorNotificacaoService = provedorNotificacaoService;
        this.provedorNotificacaoRepository = provedorNotificacaoRepository;
    }

    /**
     * {@code POST  /provedor-notificacaos} : Create a new provedorNotificacao.
     *
     * @param provedorNotificacaoDTO the provedorNotificacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new provedorNotificacaoDTO, or with status {@code 400 (Bad Request)} if the provedorNotificacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/provedor-notificacaos")
    public ResponseEntity<ProvedorNotificacaoDTO> createProvedorNotificacao(
        @Valid @RequestBody ProvedorNotificacaoDTO provedorNotificacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProvedorNotificacao : {}", provedorNotificacaoDTO);
        if (provedorNotificacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new provedorNotificacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProvedorNotificacaoDTO result = provedorNotificacaoService.save(provedorNotificacaoDTO);
        return ResponseEntity
            .created(new URI("/api/provedor-notificacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /provedor-notificacaos/:id} : Updates an existing provedorNotificacao.
     *
     * @param id the id of the provedorNotificacaoDTO to save.
     * @param provedorNotificacaoDTO the provedorNotificacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provedorNotificacaoDTO,
     * or with status {@code 400 (Bad Request)} if the provedorNotificacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provedorNotificacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/provedor-notificacaos/{id}")
    public ResponseEntity<ProvedorNotificacaoDTO> updateProvedorNotificacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProvedorNotificacaoDTO provedorNotificacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProvedorNotificacao : {}, {}", id, provedorNotificacaoDTO);
        if (provedorNotificacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, provedorNotificacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provedorNotificacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProvedorNotificacaoDTO result = provedorNotificacaoService.update(provedorNotificacaoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provedorNotificacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /provedor-notificacaos/:id} : Partial updates given fields of an existing provedorNotificacao, field will ignore if it is null
     *
     * @param id the id of the provedorNotificacaoDTO to save.
     * @param provedorNotificacaoDTO the provedorNotificacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provedorNotificacaoDTO,
     * or with status {@code 400 (Bad Request)} if the provedorNotificacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the provedorNotificacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the provedorNotificacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/provedor-notificacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProvedorNotificacaoDTO> partialUpdateProvedorNotificacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProvedorNotificacaoDTO provedorNotificacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProvedorNotificacao partially : {}, {}", id, provedorNotificacaoDTO);
        if (provedorNotificacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, provedorNotificacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!provedorNotificacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProvedorNotificacaoDTO> result = provedorNotificacaoService.partialUpdate(provedorNotificacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provedorNotificacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /provedor-notificacaos} : get all the provedorNotificacaos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provedorNotificacaos in body.
     */
    @GetMapping("/provedor-notificacaos")
    public ResponseEntity<List<ProvedorNotificacaoDTO>> getAllProvedorNotificacaos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ProvedorNotificacaos");
        Page<ProvedorNotificacaoDTO> page;
        if (eagerload) {
            page = provedorNotificacaoService.findAllWithEagerRelationships(pageable);
        } else {
            page = provedorNotificacaoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /provedor-notificacaos/:id} : get the "id" provedorNotificacao.
     *
     * @param id the id of the provedorNotificacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provedorNotificacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/provedor-notificacaos/{id}")
    public ResponseEntity<ProvedorNotificacaoDTO> getProvedorNotificacao(@PathVariable Long id) {
        log.debug("REST request to get ProvedorNotificacao : {}", id);
        Optional<ProvedorNotificacaoDTO> provedorNotificacaoDTO = provedorNotificacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provedorNotificacaoDTO);
    }

    /**
     * {@code DELETE  /provedor-notificacaos/:id} : delete the "id" provedorNotificacao.
     *
     * @param id the id of the provedorNotificacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/provedor-notificacaos/{id}")
    public ResponseEntity<Void> deleteProvedorNotificacao(@PathVariable Long id) {
        log.debug("REST request to delete ProvedorNotificacao : {}", id);
        provedorNotificacaoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
