package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.PeriodoHorario;
import com.ravunana.longonkelo.repository.PeriodoHorarioRepository;
import com.ravunana.longonkelo.security.SecurityUtils;
import com.ravunana.longonkelo.service.InstituicaoEnsinoService;
import com.ravunana.longonkelo.service.PeriodoHorarioService;
import com.ravunana.longonkelo.service.dto.PeriodoHorarioDTO;
import com.ravunana.longonkelo.service.mapper.PeriodoHorarioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PeriodoHorario}.
 */
@Service
@Transactional
public class PeriodoHorarioServiceImpl implements PeriodoHorarioService {

    private final Logger log = LoggerFactory.getLogger(PeriodoHorarioServiceImpl.class);

    private final PeriodoHorarioRepository periodoHorarioRepository;

    private final PeriodoHorarioMapper periodoHorarioMapper;
    private final InstituicaoEnsinoService instituicaoEnsinoService;

    public PeriodoHorarioServiceImpl(
        PeriodoHorarioRepository periodoHorarioRepository,
        PeriodoHorarioMapper periodoHorarioMapper,
        InstituicaoEnsinoService instituicaoEnsinoService
    ) {
        this.periodoHorarioRepository = periodoHorarioRepository;
        this.periodoHorarioMapper = periodoHorarioMapper;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
    }

    @Override
    public PeriodoHorarioDTO save(PeriodoHorarioDTO periodoHorarioDTO) {
        log.debug("Request to save PeriodoHorario : {}", periodoHorarioDTO);

        var inicio = periodoHorarioDTO.getInicio().substring(0, 5);
        var fim = periodoHorarioDTO.getFim().substring(0, 5);
        //
        //        if (!inicio.contains(":")) {
        //            throw new LongokeloException("Formato da Hora de inicio incorreta, formato certo: HH:mm");
        //        }
        //
        //        if (!fim.contains(":")) {
        //            throw new LongokeloException("Formato da Hora final incorreta, formato certo: HH:mm");
        //        }

        periodoHorarioDTO.setInicio(inicio);
        periodoHorarioDTO.setFim(fim);

        String descricao =
            periodoHorarioDTO.getTempo() +
            "º Tempo, " +
            periodoHorarioDTO.getTurno().getNome() +
            " das " +
            periodoHorarioDTO.getInicio() +
            " as " +
            periodoHorarioDTO.getFim();
        periodoHorarioDTO.setDescricao(descricao);

        PeriodoHorario periodoHorario = periodoHorarioMapper.toEntity(periodoHorarioDTO);
        periodoHorario = periodoHorarioRepository.save(periodoHorario);
        return periodoHorarioMapper.toDto(periodoHorario);
    }

    @Override
    public PeriodoHorarioDTO update(PeriodoHorarioDTO periodoHorarioDTO) {
        log.debug("Request to update PeriodoHorario : {}", periodoHorarioDTO);
        PeriodoHorario periodoHorario = periodoHorarioMapper.toEntity(periodoHorarioDTO);
        periodoHorario = periodoHorarioRepository.save(periodoHorario);
        return periodoHorarioMapper.toDto(periodoHorario);
    }

    @Override
    public Optional<PeriodoHorarioDTO> partialUpdate(PeriodoHorarioDTO periodoHorarioDTO) {
        log.debug("Request to partially update PeriodoHorario : {}", periodoHorarioDTO);

        return periodoHorarioRepository
            .findById(periodoHorarioDTO.getId())
            .map(existingPeriodoHorario -> {
                periodoHorarioMapper.partialUpdate(existingPeriodoHorario, periodoHorarioDTO);

                return existingPeriodoHorario;
            })
            .map(periodoHorarioRepository::save)
            .map(periodoHorarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PeriodoHorarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PeriodoHorarios");
        return periodoHorarioRepository.findAll(pageable).map(periodoHorarioMapper::toDto);
    }

    public Page<PeriodoHorarioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return periodoHorarioRepository.findAllWithEagerRelationships(pageable).map(periodoHorarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodoHorarioDTO> findOne(Long id) {
        log.debug("Request to get PeriodoHorario : {}", id);
        return periodoHorarioRepository.findOneWithEagerRelationships(id).map(periodoHorarioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PeriodoHorario : {}", id);
        periodoHorarioRepository.deleteById(id);
    }
}
