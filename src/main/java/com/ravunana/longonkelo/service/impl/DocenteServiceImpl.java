package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Docente;
import com.ravunana.longonkelo.repository.DocenteRepository;
import com.ravunana.longonkelo.service.DocenteService;
import com.ravunana.longonkelo.service.dto.DocenteDTO;
import com.ravunana.longonkelo.service.mapper.DocenteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Docente}.
 */
@Service
@Transactional
public class DocenteServiceImpl implements DocenteService {

    private final Logger log = LoggerFactory.getLogger(DocenteServiceImpl.class);

    private final DocenteRepository docenteRepository;

    private final DocenteMapper docenteMapper;

    public DocenteServiceImpl(DocenteRepository docenteRepository, DocenteMapper docenteMapper) {
        this.docenteRepository = docenteRepository;
        this.docenteMapper = docenteMapper;
    }

    @Override
    public DocenteDTO save(DocenteDTO docenteDTO) {
        log.debug("Request to save Docente : {}", docenteDTO);
        Docente docente = docenteMapper.toEntity(docenteDTO);
        docente = docenteRepository.save(docente);
        return docenteMapper.toDto(docente);
    }

    @Override
    public DocenteDTO update(DocenteDTO docenteDTO) {
        log.debug("Request to update Docente : {}", docenteDTO);
        Docente docente = docenteMapper.toEntity(docenteDTO);
        docente = docenteRepository.save(docente);
        return docenteMapper.toDto(docente);
    }

    @Override
    public Optional<DocenteDTO> partialUpdate(DocenteDTO docenteDTO) {
        log.debug("Request to partially update Docente : {}", docenteDTO);

        return docenteRepository
            .findById(docenteDTO.getId())
            .map(existingDocente -> {
                docenteMapper.partialUpdate(existingDocente, docenteDTO);

                return existingDocente;
            })
            .map(docenteRepository::save)
            .map(docenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Docentes");
        return docenteRepository.findAll(pageable).map(docenteMapper::toDto);
    }

    public Page<DocenteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return docenteRepository.findAllWithEagerRelationships(pageable).map(docenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocenteDTO> findOne(Long id) {
        log.debug("Request to get Docente : {}", id);
        return docenteRepository.findOneWithEagerRelationships(id).map(docenteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Docente : {}", id);
        docenteRepository.deleteById(id);
    }
}
