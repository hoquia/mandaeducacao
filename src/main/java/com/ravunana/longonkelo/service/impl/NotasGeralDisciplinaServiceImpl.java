package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import com.ravunana.longonkelo.repository.NotasGeralDisciplinaRepository;
import com.ravunana.longonkelo.security.SecurityUtils;
import com.ravunana.longonkelo.service.NotasGeralDisciplinaService;
import com.ravunana.longonkelo.service.UserService;
import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.NotasGeralDisciplinaMapper;
import com.ravunana.longonkelo.service.mapper.UserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotasGeralDisciplina}.
 */
@Service
@Transactional
public class NotasGeralDisciplinaServiceImpl implements NotasGeralDisciplinaService {

    private final Logger log = LoggerFactory.getLogger(NotasGeralDisciplinaServiceImpl.class);

    private final NotasGeralDisciplinaRepository notasGeralDisciplinaRepository;

    private final NotasGeralDisciplinaMapper notasGeralDisciplinaMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public NotasGeralDisciplinaServiceImpl(
        NotasGeralDisciplinaRepository notasGeralDisciplinaRepository,
        NotasGeralDisciplinaMapper notasGeralDisciplinaMapper,
        UserService userService,
        UserMapper userMapper
    ) {
        this.notasGeralDisciplinaRepository = notasGeralDisciplinaRepository;
        this.notasGeralDisciplinaMapper = notasGeralDisciplinaMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public NotasGeralDisciplinaDTO save(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO) {
        log.debug("Request to save NotasGeralDisciplina : {}", notasGeralDisciplinaDTO);
        var chaveComposta = getChaveComposta(notasGeralDisciplinaDTO);
        notasGeralDisciplinaDTO.setChaveComposta(chaveComposta);

        var mediaFinalDisciplina = calcularMediaFinalDisciplina(notasGeralDisciplinaDTO);
        notasGeralDisciplinaDTO.setMediaFinalDisciplina(mediaFinalDisciplina);

        var utilizador = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        notasGeralDisciplinaDTO.setUtilizador(userMapper.toDtoLogin(utilizador));

        NotasGeralDisciplina notasGeralDisciplina = notasGeralDisciplinaMapper.toEntity(notasGeralDisciplinaDTO);
        notasGeralDisciplina = notasGeralDisciplinaRepository.save(notasGeralDisciplina);
        return notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);
    }

    @Override
    public NotasGeralDisciplinaDTO update(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO) {
        log.debug("Request to update NotasGeralDisciplina : {}", notasGeralDisciplinaDTO);

        var mediaFinalDisciplina = calcularMediaFinalDisciplina(notasGeralDisciplinaDTO);
        notasGeralDisciplinaDTO.setMediaFinalDisciplina(mediaFinalDisciplina);

        NotasGeralDisciplina notasGeralDisciplina = notasGeralDisciplinaMapper.toEntity(notasGeralDisciplinaDTO);
        notasGeralDisciplina = notasGeralDisciplinaRepository.save(notasGeralDisciplina);
        return notasGeralDisciplinaMapper.toDto(notasGeralDisciplina);
    }

    @Override
    public Optional<NotasGeralDisciplinaDTO> partialUpdate(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO) {
        log.debug("Request to partially update NotasGeralDisciplina : {}", notasGeralDisciplinaDTO);

        var mediaFinalDisciplina = calcularMediaFinalDisciplina(notasGeralDisciplinaDTO);
        notasGeralDisciplinaDTO.setMediaFinalDisciplina(mediaFinalDisciplina);

        return notasGeralDisciplinaRepository
            .findById(notasGeralDisciplinaDTO.getId())
            .map(existingNotasGeralDisciplina -> {
                notasGeralDisciplinaMapper.partialUpdate(existingNotasGeralDisciplina, notasGeralDisciplinaDTO);

                return existingNotasGeralDisciplina;
            })
            .map(notasGeralDisciplinaRepository::save)
            .map(notasGeralDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotasGeralDisciplinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotasGeralDisciplinas");
        return notasGeralDisciplinaRepository.findAll(pageable).map(notasGeralDisciplinaMapper::toDto);
    }

    public Page<NotasGeralDisciplinaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return notasGeralDisciplinaRepository.findAllWithEagerRelationships(pageable).map(notasGeralDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotasGeralDisciplinaDTO> findOne(Long id) {
        log.debug("Request to get NotasGeralDisciplina : {}", id);
        return notasGeralDisciplinaRepository.findOneWithEagerRelationships(id).map(notasGeralDisciplinaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotasGeralDisciplina : {}", id);
        notasGeralDisciplinaRepository.deleteById(id);
    }

    // matricula-periodoLectivo-disciplina
    @Override
    public String getChaveComposta(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO) {
        Long matriculaId = notasGeralDisciplinaDTO.getMatricula().getId();
        int periodoLectivo = notasGeralDisciplinaDTO.getPeriodoLancamento();
        Long disciplina = notasGeralDisciplinaDTO.getDisciplinaCurricular().getId();
        StringBuilder sb = new StringBuilder();
        sb.append(matriculaId).append(periodoLectivo).append(disciplina);
        return sb.toString();
    }

    @Override
    public Double calcularMediaFinalDisciplina(NotasGeralDisciplinaDTO notasGeralDisciplinaDTO) {
        var media1 = notasGeralDisciplinaDTO.getMedia1();
        var media2 = notasGeralDisciplinaDTO.getMedia2();
        var media3 = notasGeralDisciplinaDTO.getMedia3();
        var recurso = notasGeralDisciplinaDTO.getRecurso();
        var exame = notasGeralDisciplinaDTO.getExame();
        var exameEspecial = notasGeralDisciplinaDTO.getExameEspecial();
        var notaConcelho = notasGeralDisciplinaDTO.getNotaConselho();

        if (media1 == null) {
            media1 = 0.0;
        }
        if (media2 == null) {
            media2 = 0.0;
        }
        if (media3 == null) {
            media3 = 0.0;
        }
        if (recurso == null) {
            recurso = 0.0;
        }
        if (exame == null) {
            exame = 0.0;
        }
        if (exameEspecial == null) {
            exameEspecial = 0.0;
        }
        if (notaConcelho == null) {
            notaConcelho = 0.0;
        }

        var mediaFinalDisciplina = (media1 + media2 + media3 + exame + recurso + exameEspecial + notaConcelho) / 7;

        return mediaFinalDisciplina;
    }
}
