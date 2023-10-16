package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.config.Constants;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.repository.NotasGeralDisciplinaRepository;
import com.ravunana.longonkelo.repository.NotasPeriodicaDisciplinaRepository;
import com.ravunana.longonkelo.security.SecurityUtils;
import com.ravunana.longonkelo.service.NotasPeriodicaDisciplinaService;
import com.ravunana.longonkelo.service.UserService;
import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
import com.ravunana.longonkelo.service.dto.NotasPeriodicaDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.NotasPeriodicaDisciplinaMapper;
import com.ravunana.longonkelo.service.mapper.UserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NotasPeriodicaDisciplina}.
 */
@Service
@Transactional
public class NotasPeriodicaDisciplinaServiceImpl implements NotasPeriodicaDisciplinaService {

    private final Logger log = LoggerFactory.getLogger(NotasPeriodicaDisciplinaServiceImpl.class);

    private final NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository;

    private final NotasPeriodicaDisciplinaMapper notasPeriodicaDisciplinaMapper;

    private final UserService userService;
    private final UserMapper userMapper;

    private final NotasGeralDisciplinaServiceImpl notasGeralDisciplinaService;
    private final NotasGeralDisciplinaRepository notasGeralDisciplinaRepository;

    public static final Double ZERO = 0d;

    public NotasPeriodicaDisciplinaServiceImpl(
        NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository,
        NotasPeriodicaDisciplinaMapper notasPeriodicaDisciplinaMapper,
        UserService userService,
        UserMapper userMapper,
        NotasGeralDisciplinaServiceImpl notasGeralDisciplinaService,
        NotasGeralDisciplinaRepository notasGeralDisciplinaRepository
    ) {
        this.notasPeriodicaDisciplinaRepository = notasPeriodicaDisciplinaRepository;
        this.notasPeriodicaDisciplinaMapper = notasPeriodicaDisciplinaMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.notasGeralDisciplinaService = notasGeralDisciplinaService;
        this.notasGeralDisciplinaRepository = notasGeralDisciplinaRepository;
    }

    @Override
    public NotasPeriodicaDisciplinaDTO save(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        log.debug("Request to save NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);

        var chaveComposta = getChaveComposta(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplinaDTO.setChaveComposta(chaveComposta);

        var media = calcularMedia(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplinaDTO.setMedia(media);

        var utilizador = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        notasPeriodicaDisciplinaDTO.setUtilizador(userMapper.toDtoLogin(utilizador));

        // TODO.RC: Atribuir o estado pela media do aluno

        // TODO: Pegar o docente pelo utilizador logado no sistema

        getNotaGeralDisciplina(notasPeriodicaDisciplinaDTO);

        NotasPeriodicaDisciplina notasPeriodicaDisciplina = notasPeriodicaDisciplinaMapper.toEntity(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplina = notasPeriodicaDisciplinaRepository.save(notasPeriodicaDisciplina);

        //        // Salvando a nota geral disciplina
        //        var notaGeralDisciplina = notasGeralDisciplinaService.getAllNotasWithMatriculaDisciplinaPeriodoLancamento(
        //            notasPeriodicaDisciplinaDTO
        //        );
        //
        //        if (notaGeralDisciplina.isPresent()) {
        //            getNotaGeralDisciplina(notasPeriodicaDisciplinaDTO);
        //        } else {
        //           getNotaGeralDisciplina(notasPeriodicaDisciplinaDTO);
        //        }

        return notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);
    }

    @Override
    public NotasPeriodicaDisciplinaDTO update(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        log.debug("Request to update NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);

        var media = calcularMedia(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplinaDTO.setMedia(media);

        NotasPeriodicaDisciplina notasPeriodicaDisciplina = notasPeriodicaDisciplinaMapper.toEntity(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplina = notasPeriodicaDisciplinaRepository.save(notasPeriodicaDisciplina);
        return notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);
    }

    @Override
    public Optional<NotasPeriodicaDisciplinaDTO> partialUpdate(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        log.debug("Request to partially update NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);

        var media = calcularMedia(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplinaDTO.setMedia(media);

        return notasPeriodicaDisciplinaRepository
            .findById(notasPeriodicaDisciplinaDTO.getId())
            .map(existingNotasPeriodicaDisciplina -> {
                notasPeriodicaDisciplinaMapper.partialUpdate(existingNotasPeriodicaDisciplina, notasPeriodicaDisciplinaDTO);

                return existingNotasPeriodicaDisciplina;
            })
            .map(notasPeriodicaDisciplinaRepository::save)
            .map(notasPeriodicaDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotasPeriodicaDisciplinaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotasPeriodicaDisciplinas");
        return notasPeriodicaDisciplinaRepository.findAll(pageable).map(notasPeriodicaDisciplinaMapper::toDto);
    }

    public Page<NotasPeriodicaDisciplinaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return notasPeriodicaDisciplinaRepository.findAllWithEagerRelationships(pageable).map(notasPeriodicaDisciplinaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotasPeriodicaDisciplinaDTO> findOne(Long id) {
        log.debug("Request to get NotasPeriodicaDisciplina : {}", id);
        return notasPeriodicaDisciplinaRepository.findOneWithEagerRelationships(id).map(notasPeriodicaDisciplinaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotasPeriodicaDisciplina : {}", id);
        notasPeriodicaDisciplinaRepository.deleteById(id);
    }

    @Override
    public String getChaveComposta(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        // periodolectivo-matricula-disciplina-turma
        var turma = notasPeriodicaDisciplinaDTO.getTurma();
        int periodoLectivo = notasPeriodicaDisciplinaDTO.getPeriodoLancamento();
        Long matriculaID = notasPeriodicaDisciplinaDTO.getMatricula().getId();
        Long disciplinaID = notasPeriodicaDisciplinaDTO.getDisciplinaCurricular().getId();
        StringBuilder sb = new StringBuilder();
        sb.append(periodoLectivo).append(matriculaID).append(disciplinaID).append(turma.getId());
        return sb.toString();
    }

    public String getChaveCompostaNotaGeral(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        var discente = notasPeriodicaDisciplinaDTO.getMatricula().getDiscente().getId();
        var disciplina = notasPeriodicaDisciplinaDTO.getDisciplinaCurricular().getId();
        var ano = notasPeriodicaDisciplinaDTO.getTurma().getId();

        StringBuilder chave = new StringBuilder();
        chave.append(ano).append(disciplina).append(discente);
        var chaveComposta = chave.toString();

        return chaveComposta;
    }

    @Override
    public Double calcularMedia(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        // nota1 + nota2 + nota3 / 3

        Double nota1 = notasPeriodicaDisciplinaDTO.getNota1();
        Double nota2 = notasPeriodicaDisciplinaDTO.getNota2();
        Double nota3 = notasPeriodicaDisciplinaDTO.getNota3();

        if (nota1 == null) {
            nota1 = 0.0;
        }

        if (nota2 == null) {
            nota2 = 0.0;
        }

        if (nota3 == null) {
            nota3 = 0.0;
        }

        Double media = (nota1 + nota2 + nota3) / 3;

        return media;
    }

    public void getNotaGeralDisciplina(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        var notaGeralDisciplinaDTO = new NotasGeralDisciplinaDTO();
        var chaveCompostaNotaGeralDisciplina = getChaveCompostaNotaGeral(notasPeriodicaDisciplinaDTO);
        //        var media = ZERO;

        notaGeralDisciplinaDTO.setMatricula(notasPeriodicaDisciplinaDTO.getMatricula());
        notaGeralDisciplinaDTO.setDisciplinaCurricular(notasPeriodicaDisciplinaDTO.getDisciplinaCurricular());
        notaGeralDisciplinaDTO.setDocente(notasPeriodicaDisciplinaDTO.getDocente());
        notaGeralDisciplinaDTO.setPeriodoLancamento(notasPeriodicaDisciplinaDTO.getPeriodoLancamento());
        notaGeralDisciplinaDTO.setFaltaJusticada(notasPeriodicaDisciplinaDTO.getFaltaJusticada());
        notaGeralDisciplinaDTO.setFaltaInjustificada(notasPeriodicaDisciplinaDTO.getFaltaInjustificada());
        notaGeralDisciplinaDTO.setChaveComposta(chaveCompostaNotaGeralDisciplina);
        notaGeralDisciplinaDTO.setUtilizador(notasPeriodicaDisciplinaDTO.getUtilizador());

        var notaGeralEncontrada = notasGeralDisciplinaRepository
            .findAll()
            .stream()
            .filter(nge ->
                nge.getMatricula().getId().equals(notasPeriodicaDisciplinaDTO.getMatricula().getId()) &&
                nge.getDisciplinaCurricular().getId().equals(notasPeriodicaDisciplinaDTO.getDisciplinaCurricular().getId())
            )
            .findFirst();

        if (notasPeriodicaDisciplinaDTO.getPeriodoLancamento().equals(1)) {
            notaGeralDisciplinaDTO.setMedia1(notasPeriodicaDisciplinaDTO.getMedia());
            notaGeralDisciplinaDTO.setMedia2(ZERO);
            notaGeralDisciplinaDTO.setMedia3(ZERO);
        } else if (notasPeriodicaDisciplinaDTO.getPeriodoLancamento().equals(2)) {
            notaGeralDisciplinaDTO.setMedia1(notaGeralEncontrada.get().getMedia1());
            notaGeralDisciplinaDTO.setMedia2(notasPeriodicaDisciplinaDTO.getMedia());
            notaGeralDisciplinaDTO.setMedia3(ZERO);
        } else {
            notaGeralDisciplinaDTO.setMedia1(notaGeralEncontrada.get().getMedia1());
            notaGeralDisciplinaDTO.setMedia2(notaGeralEncontrada.get().getMedia2());
            notaGeralDisciplinaDTO.setMedia3(notasPeriodicaDisciplinaDTO.getMedia());
        }

        var mediaFinalDisciplina = calcuarMediaFinalDisciplina(
            notaGeralDisciplinaDTO.getMedia1(),
            notaGeralDisciplinaDTO.getMedia2(),
            notaGeralDisciplinaDTO.getMedia3()
        );

        notaGeralDisciplinaDTO.setMediaFinalDisciplina(mediaFinalDisciplina);

        notaGeralDisciplinaDTO.setTimestamp(Constants.DATE_TIME);

        if (notaGeralEncontrada.isPresent()) {
            notaGeralDisciplinaDTO.setId(notaGeralEncontrada.get().getId());
            notasGeralDisciplinaService.partialUpdate(notaGeralDisciplinaDTO);
        } else {
            notasGeralDisciplinaService.save(notaGeralDisciplinaDTO);
        }
    }

    public Double calcuarMediaFinalDisciplina(Double nota1, Double nota2, Double nota3) {
        var media1 = nota1;
        var media2 = nota2;
        var media3 = nota3;

        Double media = (media1 + media2 + media3) / 3;

        return media;
    }
}
