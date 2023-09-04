package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.DetalhePlanoAula;
import com.ravunana.longonkelo.repository.DetalhePlanoAulaRepository;
import com.ravunana.longonkelo.service.DetalhePlanoAulaService;
import com.ravunana.longonkelo.service.dto.DetalhePlanoAulaDTO;
import com.ravunana.longonkelo.service.mapper.DetalhePlanoAulaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetalhePlanoAula}.
 */
@Service
@Transactional
public class DetalhePlanoAulaServiceImpl implements DetalhePlanoAulaService {

    private final Logger log = LoggerFactory.getLogger(DetalhePlanoAulaServiceImpl.class);

    private final DetalhePlanoAulaRepository detalhePlanoAulaRepository;

    private final DetalhePlanoAulaMapper detalhePlanoAulaMapper;

    public DetalhePlanoAulaServiceImpl(
        DetalhePlanoAulaRepository detalhePlanoAulaRepository,
        DetalhePlanoAulaMapper detalhePlanoAulaMapper
    ) {
        this.detalhePlanoAulaRepository = detalhePlanoAulaRepository;
        this.detalhePlanoAulaMapper = detalhePlanoAulaMapper;
    }

    @Override
    public DetalhePlanoAulaDTO save(DetalhePlanoAulaDTO detalhePlanoAulaDTO) {
        log.debug("Request to save DetalhePlanoAula : {}", detalhePlanoAulaDTO);
        DetalhePlanoAula detalhePlanoAula = detalhePlanoAulaMapper.toEntity(detalhePlanoAulaDTO);
        detalhePlanoAula = detalhePlanoAulaRepository.save(detalhePlanoAula);
        return detalhePlanoAulaMapper.toDto(detalhePlanoAula);
    }

    @Override
    public DetalhePlanoAulaDTO update(DetalhePlanoAulaDTO detalhePlanoAulaDTO) {
        log.debug("Request to update DetalhePlanoAula : {}", detalhePlanoAulaDTO);
        DetalhePlanoAula detalhePlanoAula = detalhePlanoAulaMapper.toEntity(detalhePlanoAulaDTO);
        detalhePlanoAula = detalhePlanoAulaRepository.save(detalhePlanoAula);
        return detalhePlanoAulaMapper.toDto(detalhePlanoAula);
    }

    @Override
    public Optional<DetalhePlanoAulaDTO> partialUpdate(DetalhePlanoAulaDTO detalhePlanoAulaDTO) {
        log.debug("Request to partially update DetalhePlanoAula : {}", detalhePlanoAulaDTO);

        return detalhePlanoAulaRepository
            .findById(detalhePlanoAulaDTO.getId())
            .map(existingDetalhePlanoAula -> {
                detalhePlanoAulaMapper.partialUpdate(existingDetalhePlanoAula, detalhePlanoAulaDTO);

                return existingDetalhePlanoAula;
            })
            .map(detalhePlanoAulaRepository::save)
            .map(detalhePlanoAulaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetalhePlanoAulaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalhePlanoAulas");
        return detalhePlanoAulaRepository.findAll(pageable).map(detalhePlanoAulaMapper::toDto);
    }

    public Page<DetalhePlanoAulaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return detalhePlanoAulaRepository.findAllWithEagerRelationships(pageable).map(detalhePlanoAulaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalhePlanoAulaDTO> findOne(Long id) {
        log.debug("Request to get DetalhePlanoAula : {}", id);
        return detalhePlanoAulaRepository.findOneWithEagerRelationships(id).map(detalhePlanoAulaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetalhePlanoAula : {}", id);
        detalhePlanoAulaRepository.deleteById(id);
    }
}
