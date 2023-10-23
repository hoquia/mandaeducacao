package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.config.Constants;
import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import com.ravunana.longonkelo.domain.enumeration.CategoriaClassificacao;
import com.ravunana.longonkelo.repository.NotasGeralDisciplinaRepository;
import com.ravunana.longonkelo.repository.NotasPeriodicaDisciplinaRepository;
import com.ravunana.longonkelo.security.SecurityUtils;
import com.ravunana.longonkelo.service.NotasPeriodicaDisciplinaService;
import com.ravunana.longonkelo.service.UserService;
import com.ravunana.longonkelo.service.dto.EstadoDisciplinaCurricularDTO;
import com.ravunana.longonkelo.service.dto.NotasGeralDisciplinaDTO;
import com.ravunana.longonkelo.service.dto.NotasPeriodicaDisciplinaDTO;
import com.ravunana.longonkelo.service.mapper.EstadoDisciplinaCurricularMapper;
import com.ravunana.longonkelo.service.mapper.NotasGeralDisciplinaMapper;
import com.ravunana.longonkelo.service.mapper.NotasPeriodicaDisciplinaMapper;
import com.ravunana.longonkelo.service.mapper.UserMapper;
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
 * Service Implementation for managing {@link NotasPeriodicaDisciplina}.
 */
@Service
@Transactional
public class NotasPeriodicaDisciplinaServiceImpl implements NotasPeriodicaDisciplinaService {

    private final Logger log = LoggerFactory.getLogger(NotasPeriodicaDisciplinaServiceImpl.class);

    private final NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository;

    private final NotasPeriodicaDisciplinaMapper notasPeriodicaDisciplinaMapper;
    private final EstadoDisciplinaCurricularServiceImpl estadoDisciplinaCurricularService;

    private final NotasGeralDisciplinaMapper notasGeralDisciplinaMapper;

    private final UserService userService;
    private final UserMapper userMapper;

    private final NotasGeralDisciplinaServiceImpl notasGeralDisciplinaService;
    private final NotasGeralDisciplinaRepository notasGeralDisciplinaRepository;
    private final EstadoDisciplinaCurricularMapper estadoDisciplinaCurricularMapper;

    public static final Double ZERO = 0d;

    public NotasPeriodicaDisciplinaServiceImpl(
        NotasPeriodicaDisciplinaRepository notasPeriodicaDisciplinaRepository,
        NotasPeriodicaDisciplinaMapper notasPeriodicaDisciplinaMapper,
        EstadoDisciplinaCurricularServiceImpl estadoDisciplinaCurricularService,
        NotasGeralDisciplinaMapper notasGeralDisciplinaMapper,
        UserService userService,
        UserMapper userMapper,
        NotasGeralDisciplinaServiceImpl notasGeralDisciplinaService,
        NotasGeralDisciplinaRepository notasGeralDisciplinaRepository,
        EstadoDisciplinaCurricularMapper estadoDisciplinaCurricularMapper
    ) {
        this.notasPeriodicaDisciplinaRepository = notasPeriodicaDisciplinaRepository;
        this.notasPeriodicaDisciplinaMapper = notasPeriodicaDisciplinaMapper;
        this.estadoDisciplinaCurricularService = estadoDisciplinaCurricularService;
        this.notasGeralDisciplinaMapper = notasGeralDisciplinaMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.notasGeralDisciplinaService = notasGeralDisciplinaService;
        this.notasGeralDisciplinaRepository = notasGeralDisciplinaRepository;
        this.estadoDisciplinaCurricularMapper = estadoDisciplinaCurricularMapper;
    }

    private EstadoDisciplinaCurricularDTO getEstadoNota(Double media) {
        EstadoDisciplinaCurricularDTO estado = null;
        if (media >= 10d) {
            estado =
                estadoDisciplinaCurricularService
                    .findAll()
                    .stream()
                    .filter(e -> e.getClassificacao().equals(CategoriaClassificacao.APROVADO))
                    .findFirst()
                    .get();
            return estado;
        } else {
            estado =
                estadoDisciplinaCurricularService
                    .findAll()
                    .stream()
                    .filter(e -> e.getClassificacao().equals(CategoriaClassificacao.REPROVADO))
                    .findFirst()
                    .get();
            return estado;
        }
    }

    @Override
    public NotasPeriodicaDisciplinaDTO save(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        log.debug("Request to save NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);

        // na primeira fase desse codigo ou lancamento de notas do periodo
        /*
        tens tres variaveis nota1, nota2, nota3
        lancas a nota1 = 10
        o codigo corre e salva
        questão? qual é o valor da nota2 e nota3?
        É atribuido o valor 0 para elas, ok
        lanço a notas2 = 12 qual o valor da nota1 e nota 3?
        A nota1 permanece 10 e a nota2 é actualizada para 12 e a nota3 recebe 0 e é actualizada a media, testa esse cenario. Just Look
        * */

        var chaveComposta = getChaveComposta(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplinaDTO.setChaveComposta(chaveComposta);

        var media = calcularMedia(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplinaDTO.setMedia(media);

        var utilizador = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        notasPeriodicaDisciplinaDTO.setUtilizador(userMapper.toDtoLogin(utilizador));

        // TODO.RC: Atribuir o estado pela media do aluno
        notasPeriodicaDisciplinaDTO.setEstado(getEstadoNota(media));

        // TODO: Pegar o docente pelo utilizador logado no sistema

        NotasPeriodicaDisciplina notasPeriodicaDisciplina = notasPeriodicaDisciplinaMapper.toEntity(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplina = notasPeriodicaDisciplinaRepository.save(notasPeriodicaDisciplina);

        // por causa da transacao a entidade que depend da outra fica depois da principal salvar o seu dado na bd
        // e deves passar a entidade salva
        getNotaGeralDisciplina(notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina));

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
    @Transactional(readOnly = false)
    public NotasPeriodicaDisciplinaDTO update(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        log.debug("Request to update NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);

        var media = calcularMedia(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplinaDTO.setMedia(media);

        notasPeriodicaDisciplinaDTO.setEstado(getEstadoNota(media));

        NotasPeriodicaDisciplina notasPeriodicaDisciplina = notasPeriodicaDisciplinaMapper.toEntity(notasPeriodicaDisciplinaDTO);
        notasPeriodicaDisciplina = notasPeriodicaDisciplinaRepository.save(notasPeriodicaDisciplina);

        getNotaGeralDisciplina(notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina));

        return notasPeriodicaDisciplinaMapper.toDto(notasPeriodicaDisciplina);
    }

    @Override
    public Optional<NotasPeriodicaDisciplinaDTO> partialUpdate(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        log.debug("Request to partially update NotasPeriodicaDisciplina : {}", notasPeriodicaDisciplinaDTO);

        getNotaGeralDisciplina(notasPeriodicaDisciplinaDTO);

        //        var media = calcularMedia(notasPeriodicaDisciplinaDTO);
        //        notasPeriodicaDisciplinaDTO.setMedia(media);

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

    @Transactional(readOnly = false)
    public void getNotaGeralDisciplina(NotasPeriodicaDisciplinaDTO notasPeriodicaDisciplinaDTO) {
        var notaGeralDisciplinaDTO = new NotasGeralDisciplinaDTO();
        var chaveCompostaNotaGeralDisciplina = getChaveCompostaNotaGeral(notasPeriodicaDisciplinaDTO);
        //        var media = ZERO;

        var notaGeralEncontrada = notasGeralDisciplinaRepository
            .findAll()
            .stream()
            .filter(nge ->
                nge.getMatricula().getId().equals(notasPeriodicaDisciplinaDTO.getMatricula().getId()) &&
                nge.getDisciplinaCurricular().getId().equals(notasPeriodicaDisciplinaDTO.getDisciplinaCurricular().getId())
            )
            .findFirst();

        notaGeralDisciplinaDTO.setFaltaJusticada(notasPeriodicaDisciplinaDTO.getFaltaJusticada());
        notaGeralDisciplinaDTO.setFaltaInjustificada(notasPeriodicaDisciplinaDTO.getFaltaInjustificada());

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

        if (mediaFinalDisciplina >= 10d) {
            var aprovado = estadoDisciplinaCurricularService
                .findAll()
                .stream()
                .filter(e -> e.getClassificacao().equals(CategoriaClassificacao.APROVADO))
                .findFirst()
                .get();
            notaGeralDisciplinaDTO.setEstado(aprovado);
        } else {
            var reprovado = estadoDisciplinaCurricularService
                .findAll()
                .stream()
                .filter(e -> e.getClassificacao().equals(CategoriaClassificacao.REPROVADO))
                .findFirst()
                .get();
            notaGeralDisciplinaDTO.setEstado(reprovado);
        }

        notaGeralDisciplinaDTO.setTimestamp(Constants.DATE_TIME);
        notaGeralDisciplinaDTO.setChaveComposta(chaveCompostaNotaGeralDisciplina);

        // Não vou mexer muinto no teu codigo, só tentnado entender.ok

        // voce tem dosi estado diferentes para tua entidade novo e actualizado
        // tens que saber quais os campos que quando já existir na bd
        // não voa ser mais actualizados
        // na entidade que estas actualizar deves passar esses campos actualizados
        // Ex. o que precisas nesse momento é: passar os dados actualizados na entidade--> notaGeralncontrada

        if (notaGeralEncontrada.isPresent()) {
            notaGeralDisciplinaDTO.setId(notaGeralEncontrada.get().getId());
            var entidadeUpdate = notaGeralEncontrada.get();
            var estado = estadoDisciplinaCurricularMapper.toEntity(notasPeriodicaDisciplinaDTO.getEstado());
            entidadeUpdate.setPeriodoLancamento(notasPeriodicaDisciplinaDTO.getPeriodoLancamento());
            entidadeUpdate.setEstado(estado);
            entidadeUpdate.setMedia1(notaGeralDisciplinaDTO.getMedia1());
            entidadeUpdate.setMedia2(notaGeralDisciplinaDTO.getMedia2());
            entidadeUpdate.setMedia3(notaGeralDisciplinaDTO.getMedia3());
            entidadeUpdate.setMediaFinalDisciplina(notaGeralDisciplinaDTO.getMediaFinalDisciplina());
            // nao precisa setar o id por já tem no momento que você vai fazer um find. ok
            //            Nesse trecho a baixo, é setado a notaGeral que já existe e depois salvamos, porquê? Dessa forma ele não vai actualizar
            //            notaGeralDisciplinaDTO = notasGeralDisciplinaMapper.toDto(notaGeralEncontrada.get());
            // La em cima nos actualizamos alguns valores, entao apenas envio esse lancamento. Esse e o e
            notasGeralDisciplinaService.partialUpdate(notasGeralDisciplinaMapper.toDto(entidadeUpdate));
            // como vais fazer isso, não sei (mas sei), mas evita duplicar dados DRY KISS YAGNI
            // entendido, agora preciso avançar outras cenas. Acredito que sim, to apensar em como fazer...
            // Ok, bons pensamentos. Obrigado
            // O pdf coloca na vertical. Ta bem
            // até... Ate.
            // no codigo acima estas a passar a mesma entidade
        } else {
            // esses dados não podem ser lterados uma vez lançado. ok
            notaGeralDisciplinaDTO.setMatricula(notasPeriodicaDisciplinaDTO.getMatricula());
            notaGeralDisciplinaDTO.setDisciplinaCurricular(notasPeriodicaDisciplinaDTO.getDisciplinaCurricular());
            notaGeralDisciplinaDTO.setDocente(notasPeriodicaDisciplinaDTO.getDocente());
            notaGeralDisciplinaDTO.setPeriodoLancamento(notasPeriodicaDisciplinaDTO.getPeriodoLancamento());
            notaGeralDisciplinaDTO.setUtilizador(notasPeriodicaDisciplinaDTO.getUtilizador());
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

    public List<NotasPeriodicaDisciplinaDTO> getNotaPeriodicaWithMatriculaPeriodo(Long matriculaID, Integer periodo) {
        var notas = notasPeriodicaDisciplinaRepository
            .findAll()
            .stream()
            .filter(npd -> npd.getMatricula().getId().equals(matriculaID) && npd.getPeriodoLancamento().equals(periodo))
            .collect(Collectors.toList());

        return notasPeriodicaDisciplinaMapper.toDto(notas);
    }
}
