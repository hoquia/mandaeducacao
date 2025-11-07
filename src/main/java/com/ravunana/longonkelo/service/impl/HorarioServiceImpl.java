package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.config.LongonkeloException;
import com.ravunana.longonkelo.domain.Horario;
import com.ravunana.longonkelo.repository.HorarioRepository;
import com.ravunana.longonkelo.service.HorarioService;
import com.ravunana.longonkelo.service.dto.HorarioDTO;
import com.ravunana.longonkelo.service.mapper.HorarioMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Horario}.
 */
@Service
@Transactional
public class HorarioServiceImpl implements HorarioService {

    private final Logger log = LoggerFactory.getLogger(HorarioServiceImpl.class);

    private final HorarioRepository horarioRepository;

    private final HorarioMapper horarioMapper;

    public HorarioServiceImpl(HorarioRepository horarioRepository, HorarioMapper horarioMapper) {
        this.horarioRepository = horarioRepository;
        this.horarioMapper = horarioMapper;
    }

    @Override
    public HorarioDTO save(HorarioDTO horarioDTO) {
        log.debug("Request to save Horario : {}", horarioDTO);

        // turmaId-tempo-diaSemana-DocenteId
        // O docente já tem um tempo definido nesse periodo para essa turma
        var chaveComposta1 =
            horarioDTO.getTurma().getId() +
            horarioDTO.getPeriodo().getTempo() +
            horarioDTO.getDiaSemana().name() +
            horarioDTO.getDocente().getId();

        var chaveComposta1Result = horarioRepository
            .findAll()
            .stream()
            .filter(x -> x.getChaveComposta1().equals(chaveComposta1))
            .findFirst();

        if (chaveComposta1Result.isPresent()) {
            throw new LongonkeloException(
                "O Docente " +
                horarioDTO.getDocente().getNome() +
                " já tem um tempo definido nesse periodo para essa turma, altera o periodo ou turma"
            );
        }

        // tipoHorarioDocente-pofessorId-tempo-diaSemana
        // O docente não pode estar no mesmo periodo em duas turmas diferentes
        var chaveComposta2 =
            horarioDTO.getDocente().getId() +
            horarioDTO.getPeriodo().getTempo() +
            horarioDTO.getDiaSemana().name() +
            horarioDTO.getTurma().getId();

        var chaveComposta2Result = horarioRepository
            .findAll()
            .stream()
            .filter(x -> x.getChaveComposta2().equals(chaveComposta2))
            .findFirst();

        if (chaveComposta2Result.isPresent()) {
            throw new LongonkeloException(
                "O Docente " +
                horarioDTO.getDocente().getNome() +
                " não pode estar no mesmo periodo e turmas diferentes, altera o perido de aula"
            );
        }

        horarioDTO.setChaveComposta1(chaveComposta1);
        horarioDTO.setChaveComposta2(chaveComposta2);

        Horario horario = horarioMapper.toEntity(horarioDTO);
        horario = horarioRepository.save(horario);
        return horarioMapper.toDto(horario);
    }

    @Override
    public HorarioDTO update(HorarioDTO horarioDTO) {
        log.debug("Request to update Horario : {}", horarioDTO);
        Horario horario = horarioMapper.toEntity(horarioDTO);
        horario = horarioRepository.save(horario);
        return horarioMapper.toDto(horario);
    }

    @Override
    public Optional<HorarioDTO> partialUpdate(HorarioDTO horarioDTO) {
        log.debug("Request to partially update Horario : {}", horarioDTO);

        return horarioRepository
            .findById(horarioDTO.getId())
            .map(existingHorario -> {
                horarioMapper.partialUpdate(existingHorario, horarioDTO);

                return existingHorario;
            })
            .map(horarioRepository::save)
            .map(horarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HorarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Horarios");
        return horarioRepository.findAll(pageable).map(horarioMapper::toDto);
    }

    public Page<HorarioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return horarioRepository.findAllWithEagerRelationships(pageable).map(horarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HorarioDTO> findOne(Long id) {
        log.debug("Request to get Horario : {}", id);
        return horarioRepository.findOneWithEagerRelationships(id).map(horarioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Horario : {}", id);
        horarioRepository.deleteById(id);
    }

    @Override
    public List<HorarioDTO> getHorarioDiscente(Long turmaID) {
        var result = horarioRepository.findAll().stream().filter(x -> x.getTurma().getId().equals(turmaID)).collect(Collectors.toList());
        return horarioMapper.toDto(result);
    }

    @Override
    public List<HorarioDTO> getHorarioDocente(Long docenteID) {
        var result = horarioRepository
            .findAll()
            .stream()
            .filter(x -> x.getDocente().getId().equals(docenteID))
            .collect(Collectors.toList());
        return horarioMapper.toDto(result);
    }
}
