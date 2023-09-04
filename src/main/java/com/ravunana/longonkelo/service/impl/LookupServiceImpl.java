package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Lookup;
import com.ravunana.longonkelo.repository.LookupRepository;
import com.ravunana.longonkelo.service.LookupService;
import com.ravunana.longonkelo.service.dto.LookupDTO;
import com.ravunana.longonkelo.service.mapper.LookupMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Lookup}.
 */
@Service
@Transactional
public class LookupServiceImpl implements LookupService {

    private final Logger log = LoggerFactory.getLogger(LookupServiceImpl.class);

    private final LookupRepository lookupRepository;

    private final LookupMapper lookupMapper;

    public LookupServiceImpl(LookupRepository lookupRepository, LookupMapper lookupMapper) {
        this.lookupRepository = lookupRepository;
        this.lookupMapper = lookupMapper;
    }

    @Override
    public LookupDTO save(LookupDTO lookupDTO) {
        log.debug("Request to save Lookup : {}", lookupDTO);
        Lookup lookup = lookupMapper.toEntity(lookupDTO);
        lookup = lookupRepository.save(lookup);
        return lookupMapper.toDto(lookup);
    }

    @Override
    public LookupDTO update(LookupDTO lookupDTO) {
        log.debug("Request to update Lookup : {}", lookupDTO);
        Lookup lookup = lookupMapper.toEntity(lookupDTO);
        lookup = lookupRepository.save(lookup);
        return lookupMapper.toDto(lookup);
    }

    @Override
    public Optional<LookupDTO> partialUpdate(LookupDTO lookupDTO) {
        log.debug("Request to partially update Lookup : {}", lookupDTO);

        return lookupRepository
            .findById(lookupDTO.getId())
            .map(existingLookup -> {
                lookupMapper.partialUpdate(existingLookup, lookupDTO);

                return existingLookup;
            })
            .map(lookupRepository::save)
            .map(lookupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LookupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lookups");
        return lookupRepository.findAll(pageable).map(lookupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LookupDTO> findOne(Long id) {
        log.debug("Request to get Lookup : {}", id);
        return lookupRepository.findById(id).map(lookupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lookup : {}", id);
        lookupRepository.deleteById(id);
    }
}
