package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.ResumoAcademico;
import com.ravunana.longonkelo.repository.ResumoAcademicoRepository;
import com.ravunana.longonkelo.service.ResumoAcademicoService;
import com.ravunana.longonkelo.service.dto.ResumoAcademicoDTO;
import com.ravunana.longonkelo.service.mapper.ResumoAcademicoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResumoAcademico}.
 */
@Service
@Transactional
public class ResumoAcademicoServiceImpl implements ResumoAcademicoService {

    private final Logger log = LoggerFactory.getLogger(ResumoAcademicoServiceImpl.class);

    private final ResumoAcademicoRepository resumoAcademicoRepository;

    private final ResumoAcademicoMapper resumoAcademicoMapper;

    public ResumoAcademicoServiceImpl(ResumoAcademicoRepository resumoAcademicoRepository, ResumoAcademicoMapper resumoAcademicoMapper) {
        this.resumoAcademicoRepository = resumoAcademicoRepository;
        this.resumoAcademicoMapper = resumoAcademicoMapper;
    }

    @Override
    public ResumoAcademicoDTO save(ResumoAcademicoDTO resumoAcademicoDTO) {
        log.debug("Request to save ResumoAcademico : {}", resumoAcademicoDTO);
        ResumoAcademico resumoAcademico = resumoAcademicoMapper.toEntity(resumoAcademicoDTO);
        resumoAcademico = resumoAcademicoRepository.save(resumoAcademico);
        return resumoAcademicoMapper.toDto(resumoAcademico);
    }

    @Override
    public ResumoAcademicoDTO update(ResumoAcademicoDTO resumoAcademicoDTO) {
        log.debug("Request to update ResumoAcademico : {}", resumoAcademicoDTO);
        ResumoAcademico resumoAcademico = resumoAcademicoMapper.toEntity(resumoAcademicoDTO);
        resumoAcademico = resumoAcademicoRepository.save(resumoAcademico);
        return resumoAcademicoMapper.toDto(resumoAcademico);
    }

    @Override
    public Optional<ResumoAcademicoDTO> partialUpdate(ResumoAcademicoDTO resumoAcademicoDTO) {
        log.debug("Request to partially update ResumoAcademico : {}", resumoAcademicoDTO);

        return resumoAcademicoRepository
            .findById(resumoAcademicoDTO.getId())
            .map(existingResumoAcademico -> {
                resumoAcademicoMapper.partialUpdate(existingResumoAcademico, resumoAcademicoDTO);

                return existingResumoAcademico;
            })
            .map(resumoAcademicoRepository::save)
            .map(resumoAcademicoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResumoAcademicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResumoAcademicos");
        return resumoAcademicoRepository.findAll(pageable).map(resumoAcademicoMapper::toDto);
    }

    public Page<ResumoAcademicoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return resumoAcademicoRepository.findAllWithEagerRelationships(pageable).map(resumoAcademicoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResumoAcademicoDTO> findOne(Long id) {
        log.debug("Request to get ResumoAcademico : {}", id);
        return resumoAcademicoRepository.findOneWithEagerRelationships(id).map(resumoAcademicoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResumoAcademico : {}", id);
        resumoAcademicoRepository.deleteById(id);
    }
}
