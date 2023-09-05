package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.AnoLectivo;
import com.ravunana.longonkelo.repository.AnoLectivoRepository;
import com.ravunana.longonkelo.service.AnoLectivoService;
import com.ravunana.longonkelo.service.dto.AnoLectivoDTO;
import com.ravunana.longonkelo.service.mapper.AnoLectivoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnoLectivo}.
 */
@Service
@Transactional
public class AnoLectivoServiceImpl implements AnoLectivoService {

    private final Logger log = LoggerFactory.getLogger(AnoLectivoServiceImpl.class);

    private final AnoLectivoRepository anoLectivoRepository;

    private final AnoLectivoMapper anoLectivoMapper;

    public AnoLectivoServiceImpl(AnoLectivoRepository anoLectivoRepository, AnoLectivoMapper anoLectivoMapper) {
        this.anoLectivoRepository = anoLectivoRepository;
        this.anoLectivoMapper = anoLectivoMapper;
    }

    @Override
    public AnoLectivoDTO save(AnoLectivoDTO anoLectivoDTO) {
        log.debug("Request to save AnoLectivo : {}", anoLectivoDTO);
        AnoLectivo anoLectivo = anoLectivoMapper.toEntity(anoLectivoDTO);
        anoLectivo = anoLectivoRepository.save(anoLectivo);
        return anoLectivoMapper.toDto(anoLectivo);
    }

    @Override
    public AnoLectivoDTO update(AnoLectivoDTO anoLectivoDTO) {
        log.debug("Request to update AnoLectivo : {}", anoLectivoDTO);
        AnoLectivo anoLectivo = anoLectivoMapper.toEntity(anoLectivoDTO);
        anoLectivo = anoLectivoRepository.save(anoLectivo);
        return anoLectivoMapper.toDto(anoLectivo);
    }

    @Override
    public Optional<AnoLectivoDTO> partialUpdate(AnoLectivoDTO anoLectivoDTO) {
        log.debug("Request to partially update AnoLectivo : {}", anoLectivoDTO);

        return anoLectivoRepository
            .findById(anoLectivoDTO.getId())
            .map(existingAnoLectivo -> {
                anoLectivoMapper.partialUpdate(existingAnoLectivo, anoLectivoDTO);

                return existingAnoLectivo;
            })
            .map(anoLectivoRepository::save)
            .map(anoLectivoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnoLectivoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnoLectivos");
        return anoLectivoRepository.findAll(pageable).map(anoLectivoMapper::toDto);
    }

    public Page<AnoLectivoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return anoLectivoRepository.findAllWithEagerRelationships(pageable).map(anoLectivoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnoLectivoDTO> findOne(Long id) {
        log.debug("Request to get AnoLectivo : {}", id);
        return anoLectivoRepository.findOneWithEagerRelationships(id).map(anoLectivoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnoLectivo : {}", id);
        anoLectivoRepository.deleteById(id);
    }

    @Override
    public AnoLectivoDTO getAnoLectivoActual() {
        var result = anoLectivoRepository.findAll().stream().filter(AnoLectivo::getIsActual).findFirst().get();

        return anoLectivoMapper.toDto(result);
    }
}
