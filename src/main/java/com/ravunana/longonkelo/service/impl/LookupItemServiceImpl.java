package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.LookupItem;
import com.ravunana.longonkelo.repository.LookupItemRepository;
import com.ravunana.longonkelo.service.LookupItemService;
import com.ravunana.longonkelo.service.dto.LookupItemDTO;
import com.ravunana.longonkelo.service.mapper.LookupItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LookupItem}.
 */
@Service
@Transactional
public class LookupItemServiceImpl implements LookupItemService {

    private final Logger log = LoggerFactory.getLogger(LookupItemServiceImpl.class);

    private final LookupItemRepository lookupItemRepository;

    private final LookupItemMapper lookupItemMapper;

    public LookupItemServiceImpl(LookupItemRepository lookupItemRepository, LookupItemMapper lookupItemMapper) {
        this.lookupItemRepository = lookupItemRepository;
        this.lookupItemMapper = lookupItemMapper;
    }

    @Override
    public LookupItemDTO save(LookupItemDTO lookupItemDTO) {
        log.debug("Request to save LookupItem : {}", lookupItemDTO);
        LookupItem lookupItem = lookupItemMapper.toEntity(lookupItemDTO);
        lookupItem = lookupItemRepository.save(lookupItem);
        return lookupItemMapper.toDto(lookupItem);
    }

    @Override
    public LookupItemDTO update(LookupItemDTO lookupItemDTO) {
        log.debug("Request to update LookupItem : {}", lookupItemDTO);
        LookupItem lookupItem = lookupItemMapper.toEntity(lookupItemDTO);
        lookupItem = lookupItemRepository.save(lookupItem);
        return lookupItemMapper.toDto(lookupItem);
    }

    @Override
    public Optional<LookupItemDTO> partialUpdate(LookupItemDTO lookupItemDTO) {
        log.debug("Request to partially update LookupItem : {}", lookupItemDTO);

        return lookupItemRepository
            .findById(lookupItemDTO.getId())
            .map(existingLookupItem -> {
                lookupItemMapper.partialUpdate(existingLookupItem, lookupItemDTO);

                return existingLookupItem;
            })
            .map(lookupItemRepository::save)
            .map(lookupItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LookupItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LookupItems");
        return lookupItemRepository.findAll(pageable).map(lookupItemMapper::toDto);
    }

    public Page<LookupItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return lookupItemRepository.findAllWithEagerRelationships(pageable).map(lookupItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LookupItemDTO> findOne(Long id) {
        log.debug("Request to get LookupItem : {}", id);
        return lookupItemRepository.findOneWithEagerRelationships(id).map(lookupItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LookupItem : {}", id);
        lookupItemRepository.deleteById(id);
    }
}
