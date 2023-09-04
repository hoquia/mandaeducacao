package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ResponsavelCurso;
import com.ravunana.longonkelo.repository.ResponsavelCursoRepository;
import com.ravunana.longonkelo.service.ResponsavelCursoService;
import com.ravunana.longonkelo.service.dto.ResponsavelCursoDTO;
import com.ravunana.longonkelo.service.mapper.ResponsavelCursoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResponsavelCurso}.
 */
@Service
@Transactional
public class ResponsavelCursoServiceImpl implements ResponsavelCursoService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelCursoServiceImpl.class);

    private final ResponsavelCursoRepository responsavelCursoRepository;

    private final ResponsavelCursoMapper responsavelCursoMapper;

    public ResponsavelCursoServiceImpl(
        ResponsavelCursoRepository responsavelCursoRepository,
        ResponsavelCursoMapper responsavelCursoMapper
    ) {
        this.responsavelCursoRepository = responsavelCursoRepository;
        this.responsavelCursoMapper = responsavelCursoMapper;
    }

    @Override
    public ResponsavelCursoDTO save(ResponsavelCursoDTO responsavelCursoDTO) {
        log.debug("Request to save ResponsavelCurso : {}", responsavelCursoDTO);
        ResponsavelCurso responsavelCurso = responsavelCursoMapper.toEntity(responsavelCursoDTO);
        responsavelCurso = responsavelCursoRepository.save(responsavelCurso);
        return responsavelCursoMapper.toDto(responsavelCurso);
    }

    @Override
    public ResponsavelCursoDTO update(ResponsavelCursoDTO responsavelCursoDTO) {
        log.debug("Request to update ResponsavelCurso : {}", responsavelCursoDTO);
        ResponsavelCurso responsavelCurso = responsavelCursoMapper.toEntity(responsavelCursoDTO);
        responsavelCurso = responsavelCursoRepository.save(responsavelCurso);
        return responsavelCursoMapper.toDto(responsavelCurso);
    }

    @Override
    public Optional<ResponsavelCursoDTO> partialUpdate(ResponsavelCursoDTO responsavelCursoDTO) {
        log.debug("Request to partially update ResponsavelCurso : {}", responsavelCursoDTO);

        return responsavelCursoRepository
            .findById(responsavelCursoDTO.getId())
            .map(existingResponsavelCurso -> {
                responsavelCursoMapper.partialUpdate(existingResponsavelCurso, responsavelCursoDTO);

                return existingResponsavelCurso;
            })
            .map(responsavelCursoRepository::save)
            .map(responsavelCursoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsavelCursoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResponsavelCursos");
        return responsavelCursoRepository.findAll(pageable).map(responsavelCursoMapper::toDto);
    }

    public Page<ResponsavelCursoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return responsavelCursoRepository.findAllWithEagerRelationships(pageable).map(responsavelCursoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsavelCursoDTO> findOne(Long id) {
        log.debug("Request to get ResponsavelCurso : {}", id);
        return responsavelCursoRepository.findOneWithEagerRelationships(id).map(responsavelCursoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResponsavelCurso : {}", id);
        responsavelCursoRepository.deleteById(id);
    }
}
