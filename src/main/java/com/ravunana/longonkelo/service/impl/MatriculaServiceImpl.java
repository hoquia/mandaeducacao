package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.repository.MatriculaRepository;
import com.ravunana.longonkelo.service.MatriculaService;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.mapper.MatriculaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Matricula}.
 */
@Service
@Transactional
public class MatriculaServiceImpl implements MatriculaService {

    private final Logger log = LoggerFactory.getLogger(MatriculaServiceImpl.class);

    private final MatriculaRepository matriculaRepository;

    private final MatriculaMapper matriculaMapper;

    public MatriculaServiceImpl(MatriculaRepository matriculaRepository, MatriculaMapper matriculaMapper) {
        this.matriculaRepository = matriculaRepository;
        this.matriculaMapper = matriculaMapper;
    }

    @Override
    public MatriculaDTO save(MatriculaDTO matriculaDTO) {
        log.debug("Request to save Matricula : {}", matriculaDTO);
        Matricula matricula = matriculaMapper.toEntity(matriculaDTO);
        matricula = matriculaRepository.save(matricula);
        return matriculaMapper.toDto(matricula);
    }

    @Override
    public MatriculaDTO update(MatriculaDTO matriculaDTO) {
        log.debug("Request to update Matricula : {}", matriculaDTO);
        Matricula matricula = matriculaMapper.toEntity(matriculaDTO);
        matricula = matriculaRepository.save(matricula);
        return matriculaMapper.toDto(matricula);
    }

    @Override
    public Optional<MatriculaDTO> partialUpdate(MatriculaDTO matriculaDTO) {
        log.debug("Request to partially update Matricula : {}", matriculaDTO);

        return matriculaRepository
            .findById(matriculaDTO.getId())
            .map(existingMatricula -> {
                matriculaMapper.partialUpdate(existingMatricula, matriculaDTO);

                return existingMatricula;
            })
            .map(matriculaRepository::save)
            .map(matriculaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatriculaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Matriculas");
        return matriculaRepository.findAll(pageable).map(matriculaMapper::toDto);
    }

    public Page<MatriculaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return matriculaRepository.findAllWithEagerRelationships(pageable).map(matriculaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatriculaDTO> findOne(Long id) {
        log.debug("Request to get Matricula : {}", id);
        return matriculaRepository.findOneWithEagerRelationships(id).map(matriculaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Matricula : {}", id);
        matriculaRepository.deleteById(id);
    }
}
