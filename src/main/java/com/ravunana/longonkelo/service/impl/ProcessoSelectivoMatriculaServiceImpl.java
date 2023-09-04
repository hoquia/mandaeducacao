package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula;
import com.ravunana.longonkelo.repository.ProcessoSelectivoMatriculaRepository;
import com.ravunana.longonkelo.service.ProcessoSelectivoMatriculaService;
import com.ravunana.longonkelo.service.dto.ProcessoSelectivoMatriculaDTO;
import com.ravunana.longonkelo.service.mapper.ProcessoSelectivoMatriculaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProcessoSelectivoMatricula}.
 */
@Service
@Transactional
public class ProcessoSelectivoMatriculaServiceImpl implements ProcessoSelectivoMatriculaService {

    private final Logger log = LoggerFactory.getLogger(ProcessoSelectivoMatriculaServiceImpl.class);

    private final ProcessoSelectivoMatriculaRepository processoSelectivoMatriculaRepository;

    private final ProcessoSelectivoMatriculaMapper processoSelectivoMatriculaMapper;

    public ProcessoSelectivoMatriculaServiceImpl(
        ProcessoSelectivoMatriculaRepository processoSelectivoMatriculaRepository,
        ProcessoSelectivoMatriculaMapper processoSelectivoMatriculaMapper
    ) {
        this.processoSelectivoMatriculaRepository = processoSelectivoMatriculaRepository;
        this.processoSelectivoMatriculaMapper = processoSelectivoMatriculaMapper;
    }

    @Override
    public ProcessoSelectivoMatriculaDTO save(ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO) {
        log.debug("Request to save ProcessoSelectivoMatricula : {}", processoSelectivoMatriculaDTO);
        ProcessoSelectivoMatricula processoSelectivoMatricula = processoSelectivoMatriculaMapper.toEntity(processoSelectivoMatriculaDTO);
        processoSelectivoMatricula = processoSelectivoMatriculaRepository.save(processoSelectivoMatricula);
        return processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);
    }

    @Override
    public ProcessoSelectivoMatriculaDTO update(ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO) {
        log.debug("Request to update ProcessoSelectivoMatricula : {}", processoSelectivoMatriculaDTO);
        ProcessoSelectivoMatricula processoSelectivoMatricula = processoSelectivoMatriculaMapper.toEntity(processoSelectivoMatriculaDTO);
        processoSelectivoMatricula = processoSelectivoMatriculaRepository.save(processoSelectivoMatricula);
        return processoSelectivoMatriculaMapper.toDto(processoSelectivoMatricula);
    }

    @Override
    public Optional<ProcessoSelectivoMatriculaDTO> partialUpdate(ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO) {
        log.debug("Request to partially update ProcessoSelectivoMatricula : {}", processoSelectivoMatriculaDTO);

        return processoSelectivoMatriculaRepository
            .findById(processoSelectivoMatriculaDTO.getId())
            .map(existingProcessoSelectivoMatricula -> {
                processoSelectivoMatriculaMapper.partialUpdate(existingProcessoSelectivoMatricula, processoSelectivoMatriculaDTO);

                return existingProcessoSelectivoMatricula;
            })
            .map(processoSelectivoMatriculaRepository::save)
            .map(processoSelectivoMatriculaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcessoSelectivoMatriculaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessoSelectivoMatriculas");
        return processoSelectivoMatriculaRepository.findAll(pageable).map(processoSelectivoMatriculaMapper::toDto);
    }

    public Page<ProcessoSelectivoMatriculaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return processoSelectivoMatriculaRepository.findAllWithEagerRelationships(pageable).map(processoSelectivoMatriculaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessoSelectivoMatriculaDTO> findOne(Long id) {
        log.debug("Request to get ProcessoSelectivoMatricula : {}", id);
        return processoSelectivoMatriculaRepository.findOneWithEagerRelationships(id).map(processoSelectivoMatriculaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessoSelectivoMatricula : {}", id);
        processoSelectivoMatriculaRepository.deleteById(id);
    }
}
