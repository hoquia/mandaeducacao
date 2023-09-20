package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ItemFactura;
import com.ravunana.longonkelo.domain.PrecoEmolumento;
import com.ravunana.longonkelo.domain.enumeration.EstadoItemFactura;
import com.ravunana.longonkelo.repository.ItemFacturaRepository;
import com.ravunana.longonkelo.service.ItemFacturaService;
import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
import com.ravunana.longonkelo.service.mapper.ItemFacturaMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ItemFactura}.
 */
@Service
@Transactional
public class ItemFacturaServiceImpl implements ItemFacturaService {

    private final Logger log = LoggerFactory.getLogger(ItemFacturaServiceImpl.class);

    private final ItemFacturaRepository itemFacturaRepository;

    private final ItemFacturaMapper itemFacturaMapper;

    public ItemFacturaServiceImpl(ItemFacturaRepository itemFacturaRepository, ItemFacturaMapper itemFacturaMapper) {
        this.itemFacturaRepository = itemFacturaRepository;
        this.itemFacturaMapper = itemFacturaMapper;
    }

    @Override
    public ItemFacturaDTO save(ItemFacturaDTO itemFacturaDTO) {
        log.debug("Request to save ItemFactura : {}", itemFacturaDTO);
        ItemFactura itemFactura = itemFacturaMapper.toEntity(itemFacturaDTO);
        itemFactura = itemFacturaRepository.save(itemFactura);
        return itemFacturaMapper.toDto(itemFactura);
    }

    @Override
    public ItemFacturaDTO update(ItemFacturaDTO itemFacturaDTO) {
        log.debug("Request to update ItemFactura : {}", itemFacturaDTO);
        ItemFactura itemFactura = itemFacturaMapper.toEntity(itemFacturaDTO);
        itemFactura = itemFacturaRepository.save(itemFactura);
        return itemFacturaMapper.toDto(itemFactura);
    }

    @Override
    public Optional<ItemFacturaDTO> partialUpdate(ItemFacturaDTO itemFacturaDTO) {
        log.debug("Request to partially update ItemFactura : {}", itemFacturaDTO);

        return itemFacturaRepository
            .findById(itemFacturaDTO.getId())
            .map(existingItemFactura -> {
                itemFacturaMapper.partialUpdate(existingItemFactura, itemFacturaDTO);

                return existingItemFactura;
            })
            .map(itemFacturaRepository::save)
            .map(itemFacturaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemFacturaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemFacturas");
        return itemFacturaRepository.findAll(pageable).map(itemFacturaMapper::toDto);
    }

    public Page<ItemFacturaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return itemFacturaRepository.findAllWithEagerRelationships(pageable).map(itemFacturaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemFacturaDTO> findOne(Long id) {
        log.debug("Request to get ItemFactura : {}", id);
        return itemFacturaRepository.findOneWithEagerRelationships(id).map(itemFacturaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemFactura : {}", id);
        itemFacturaRepository.deleteById(id);
    }

    @Override
    public List<ItemFacturaDTO> getItemsFacturaByTurmaAndEmolumento(Long turmaID, Long emolumentoID) {
        var result = itemFacturaRepository
            .findAll()
            .stream()
            .filter(x -> x.getFactura().getMatricula().getTurma().getId().equals(turmaID) && x.getEmolumento().getId().equals(emolumentoID))
            .collect(Collectors.toList());

        return itemFacturaMapper.toDto(result);
    }

    @Override
    public List<ItemFactura> getItensFacturaWithCategoria(Long catregoriaEmolumentoID) {
        var itensCategoria = itemFacturaRepository
            .findAll()
            .stream()
            .filter(i -> i.getEmolumento().getCategoria().getId().equals(catregoriaEmolumentoID))
            .collect(Collectors.toList());

        return itensCategoria;
    }

    public List<ItemFacturaDTO> getItemsFactura(Long facturaID) {
        var result = itemFacturaRepository
            .findAll()
            .stream()
            .filter(x -> x.getFactura().getId().equals(facturaID) && x.getEstado().equals(EstadoItemFactura.PAGO))
            .collect(Collectors.toList());

        return itemFacturaMapper.toDto(result);
    }
}
