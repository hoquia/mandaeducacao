package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import com.ravunana.longonkelo.repository.DissertacaoFinalCursoRepository;
import com.ravunana.longonkelo.service.DissertacaoFinalCursoService;
import com.ravunana.longonkelo.service.dto.DissertacaoFinalCursoDTO;
import com.ravunana.longonkelo.service.mapper.DissertacaoFinalCursoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DissertacaoFinalCurso}.
 */
@Service
@Transactional
public class DissertacaoFinalCursoServiceImpl implements DissertacaoFinalCursoService {

    private final Logger log = LoggerFactory.getLogger(DissertacaoFinalCursoServiceImpl.class);

    private final DissertacaoFinalCursoRepository dissertacaoFinalCursoRepository;

    private final DissertacaoFinalCursoMapper dissertacaoFinalCursoMapper;

    public DissertacaoFinalCursoServiceImpl(
        DissertacaoFinalCursoRepository dissertacaoFinalCursoRepository,
        DissertacaoFinalCursoMapper dissertacaoFinalCursoMapper
    ) {
        this.dissertacaoFinalCursoRepository = dissertacaoFinalCursoRepository;
        this.dissertacaoFinalCursoMapper = dissertacaoFinalCursoMapper;
    }

    @Override
    public DissertacaoFinalCursoDTO save(DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO) {
        log.debug("Request to save DissertacaoFinalCurso : {}", dissertacaoFinalCursoDTO);
        DissertacaoFinalCurso dissertacaoFinalCurso = dissertacaoFinalCursoMapper.toEntity(dissertacaoFinalCursoDTO);
        dissertacaoFinalCurso = dissertacaoFinalCursoRepository.save(dissertacaoFinalCurso);
        return dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);
    }

    @Override
    public DissertacaoFinalCursoDTO update(DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO) {
        log.debug("Request to update DissertacaoFinalCurso : {}", dissertacaoFinalCursoDTO);
        DissertacaoFinalCurso dissertacaoFinalCurso = dissertacaoFinalCursoMapper.toEntity(dissertacaoFinalCursoDTO);
        dissertacaoFinalCurso = dissertacaoFinalCursoRepository.save(dissertacaoFinalCurso);
        return dissertacaoFinalCursoMapper.toDto(dissertacaoFinalCurso);
    }

    @Override
    public Optional<DissertacaoFinalCursoDTO> partialUpdate(DissertacaoFinalCursoDTO dissertacaoFinalCursoDTO) {
        log.debug("Request to partially update DissertacaoFinalCurso : {}", dissertacaoFinalCursoDTO);

        return dissertacaoFinalCursoRepository
            .findById(dissertacaoFinalCursoDTO.getId())
            .map(existingDissertacaoFinalCurso -> {
                dissertacaoFinalCursoMapper.partialUpdate(existingDissertacaoFinalCurso, dissertacaoFinalCursoDTO);

                return existingDissertacaoFinalCurso;
            })
            .map(dissertacaoFinalCursoRepository::save)
            .map(dissertacaoFinalCursoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DissertacaoFinalCursoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DissertacaoFinalCursos");
        return dissertacaoFinalCursoRepository.findAll(pageable).map(dissertacaoFinalCursoMapper::toDto);
    }

    public Page<DissertacaoFinalCursoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return dissertacaoFinalCursoRepository.findAllWithEagerRelationships(pageable).map(dissertacaoFinalCursoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DissertacaoFinalCursoDTO> findOne(Long id) {
        log.debug("Request to get DissertacaoFinalCurso : {}", id);
        return dissertacaoFinalCursoRepository.findOneWithEagerRelationships(id).map(dissertacaoFinalCursoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DissertacaoFinalCurso : {}", id);
        dissertacaoFinalCursoRepository.deleteById(id);
    }
}
