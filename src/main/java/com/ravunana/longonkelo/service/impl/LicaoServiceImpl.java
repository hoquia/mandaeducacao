package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.domain.enumeration.EstadoLicao;
import com.ravunana.longonkelo.repository.LicaoRepository;
import com.ravunana.longonkelo.security.SecurityUtils;
import com.ravunana.longonkelo.service.LicaoService;
import com.ravunana.longonkelo.service.TurmaService;
import com.ravunana.longonkelo.service.UserService;
import com.ravunana.longonkelo.service.dto.LicaoDTO;
import com.ravunana.longonkelo.service.mapper.LicaoMapper;
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
 * Service Implementation for managing {@link Licao}.
 */
@Service
@Transactional
public class LicaoServiceImpl implements LicaoService {

    private final Logger log = LoggerFactory.getLogger(LicaoServiceImpl.class);

    private final LicaoRepository licaoRepository;

    private final LicaoMapper licaoMapper;

    private final UserService userService;
    private final UserMapper userMapper;

    public LicaoServiceImpl(LicaoRepository licaoRepository, LicaoMapper licaoMapper, UserService userService, UserMapper userMapper) {
        this.licaoRepository = licaoRepository;
        this.licaoMapper = licaoMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public LicaoDTO save(LicaoDTO licaoDTO) {
        log.debug("Request to save Licao : {}", licaoDTO);
        var numeroLicao = gerarNumeroLicao(licaoDTO);
        licaoDTO.setNumero(numeroLicao);

        var chaveComposta = getChaveComposta(licaoDTO);
        licaoDTO.setChaveComposta(chaveComposta);

        var utilizador = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        licaoDTO.setUtilizador(userMapper.toDtoLogin(utilizador));

        Licao licao = licaoMapper.toEntity(licaoDTO);
        licao = licaoRepository.save(licao);
        return licaoMapper.toDto(licao);
    }

    @Override
    public LicaoDTO update(LicaoDTO licaoDTO) {
        log.debug("Request to update Licao : {}", licaoDTO);
        Licao licao = licaoMapper.toEntity(licaoDTO);
        licao = licaoRepository.save(licao);
        return licaoMapper.toDto(licao);
    }

    @Override
    public Optional<LicaoDTO> partialUpdate(LicaoDTO licaoDTO) {
        log.debug("Request to partially update Licao : {}", licaoDTO);

        return licaoRepository
            .findById(licaoDTO.getId())
            .map(existingLicao -> {
                licaoMapper.partialUpdate(existingLicao, licaoDTO);

                return existingLicao;
            })
            .map(licaoRepository::save)
            .map(licaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LicaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Licaos");
        return licaoRepository.findAll(pageable).map(licaoMapper::toDto);
    }

    public Page<LicaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return licaoRepository.findAllWithEagerRelationships(pageable).map(licaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LicaoDTO> findOne(Long id) {
        log.debug("Request to get Licao : {}", id);
        return licaoRepository.findOneWithEagerRelationships(id).map(licaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Licao : {}", id);
        licaoRepository.deleteById(id);
    }

    @Override
    public String getChaveComposta(LicaoDTO licaoDTO) {
        // numero + turma + disciplina + docente + temaAula
        var horario = licaoDTO.getHorario();
        var turmaId = horario.getTurma().getId();
        var disciplinaId = horario.getDisciplinaCurricular().getId();
        int numero = licaoDTO.getNumero();
        var temaAula = licaoDTO.getPlanoAula().getAssunto();
        var docenteId = licaoDTO.getPlanoAula().getDocente().getId();
        StringBuilder sb = new StringBuilder();
        sb.append(numero).append(turmaId).append(disciplinaId).append(temaAula).append(docenteId);
        return sb.toString();
    }

    @Override
    public Integer gerarNumeroLicao(LicaoDTO licaoDTO) {
        long count = 0;
        var horario = licaoDTO.getHorario();
        var turmaId = horario.getTurma().getId();
        var disciplinaID = horario.getDisciplinaCurricular().getId();
        var estado = licaoDTO.getEstado();

        count =
            licaoRepository
                .findAll()
                .stream()
                .filter(x ->
                    x.getHorario().getTurma().getId().equals(turmaId) &&
                    x.getHorario().getDisciplinaCurricular().getId().equals(disciplinaID)
                )
                .count();
        count++;
        /*
        var t = licaoRepository.findAll().stream().filter(l -> l.getHorario().getTurma().getId().equals(turmaId)).findFirst();

        var d = licaoRepository
            .findAll()
            .stream()
            .filter(di -> di.getHorario().getDisciplinaCurricular().getId().equals(disciplina))
            .findFirst();

        var licoes = licaoRepository.findAll().stream().toArray().length;
        for (int i = 0; i < licoes; i++) {
            if (t.isPresent() && d.isPresent() && estado.equals(EstadoLicao.DADA)) {}
        }
        */
        return (int) count;
    }
}
