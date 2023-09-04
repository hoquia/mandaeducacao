package com.ravunana.longonkelo.web.rest;

import com.ravunana.longonkelo.repository.ItemFacturaRepository;
import com.ravunana.longonkelo.service.ItemFacturaQueryService;
import com.ravunana.longonkelo.service.ItemFacturaService;
import com.ravunana.longonkelo.service.criteria.ItemFacturaCriteria;
import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
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
 * REST controller for managing {@link com.ravunana.longonkelo.domain.ItemFactura}.
 */
@RestController
@RequestMapping("/api")
public class ItemFacturaResource {

    private final Logger log = LoggerFactory.getLogger(ItemFacturaResource.class);

    private static final String ENTITY_NAME = "itemFactura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemFacturaService itemFacturaService;

    private final ItemFacturaRepository itemFacturaRepository;

    private final ItemFacturaQueryService itemFacturaQueryService;

    public ItemFacturaResource(
        ItemFacturaService itemFacturaService,
        ItemFacturaRepository itemFacturaRepository,
        ItemFacturaQueryService itemFacturaQueryService
    ) {
        this.itemFacturaService = itemFacturaService;
        this.itemFacturaRepository = itemFacturaRepository;
        this.itemFacturaQueryService = itemFacturaQueryService;
    }

    /**
     * {@code POST  /item-facturas} : Create a new itemFactura.
     *
     * @param itemFacturaDTO the itemFacturaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemFacturaDTO, or with status {@code 400 (Bad Request)} if the itemFactura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-facturas")
    public ResponseEntity<ItemFacturaDTO> createItemFactura(@Valid @RequestBody ItemFacturaDTO itemFacturaDTO) throws URISyntaxException {
        log.debug("REST request to save ItemFactura : {}", itemFacturaDTO);
        if (itemFacturaDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemFactura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemFacturaDTO result = itemFacturaService.save(itemFacturaDTO);
        return ResponseEntity
            .created(new URI("/api/item-facturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-facturas/:id} : Updates an existing itemFactura.
     *
     * @param id the id of the itemFacturaDTO to save.
     * @param itemFacturaDTO the itemFacturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemFacturaDTO,
     * or with status {@code 400 (Bad Request)} if the itemFacturaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemFacturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-facturas/{id}")
    public ResponseEntity<ItemFacturaDTO> updateItemFactura(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ItemFacturaDTO itemFacturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ItemFactura : {}, {}", id, itemFacturaDTO);
        if (itemFacturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemFacturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemFacturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemFacturaDTO result = itemFacturaService.update(itemFacturaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemFacturaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-facturas/:id} : Partial updates given fields of an existing itemFactura, field will ignore if it is null
     *
     * @param id the id of the itemFacturaDTO to save.
     * @param itemFacturaDTO the itemFacturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemFacturaDTO,
     * or with status {@code 400 (Bad Request)} if the itemFacturaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the itemFacturaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemFacturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item-facturas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemFacturaDTO> partialUpdateItemFactura(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ItemFacturaDTO itemFacturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemFactura partially : {}, {}", id, itemFacturaDTO);
        if (itemFacturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemFacturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemFacturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemFacturaDTO> result = itemFacturaService.partialUpdate(itemFacturaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemFacturaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /item-facturas} : get all the itemFacturas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemFacturas in body.
     */
    @GetMapping("/item-facturas")
    public ResponseEntity<List<ItemFacturaDTO>> getAllItemFacturas(
        ItemFacturaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ItemFacturas by criteria: {}", criteria);
        Page<ItemFacturaDTO> page = itemFacturaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-facturas/count} : count all the itemFacturas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/item-facturas/count")
    public ResponseEntity<Long> countItemFacturas(ItemFacturaCriteria criteria) {
        log.debug("REST request to count ItemFacturas by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemFacturaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-facturas/:id} : get the "id" itemFactura.
     *
     * @param id the id of the itemFacturaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemFacturaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-facturas/{id}")
    public ResponseEntity<ItemFacturaDTO> getItemFactura(@PathVariable Long id) {
        log.debug("REST request to get ItemFactura : {}", id);
        Optional<ItemFacturaDTO> itemFacturaDTO = itemFacturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemFacturaDTO);
    }

    /**
     * {@code DELETE  /item-facturas/:id} : delete the "id" itemFactura.
     *
     * @param id the id of the itemFacturaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-facturas/{id}")
    public ResponseEntity<Void> deleteItemFactura(@PathVariable Long id) {
        log.debug("REST request to delete ItemFactura : {}", id);
        itemFacturaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
