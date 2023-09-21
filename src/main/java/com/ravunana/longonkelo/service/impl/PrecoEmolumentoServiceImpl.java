package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.config.LongonkeloException;
import com.ravunana.longonkelo.domain.PrecoEmolumento;
import com.ravunana.longonkelo.repository.PrecoEmolumentoRepository;
import com.ravunana.longonkelo.service.PrecoEmolumentoService;
import com.ravunana.longonkelo.service.dto.PrecoEmolumentoDTO;
import com.ravunana.longonkelo.service.dto.TurmaDTO;
import com.ravunana.longonkelo.service.mapper.PrecoEmolumentoMapper;
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
 * Service Implementation for managing {@link PrecoEmolumento}.
 */
@Service
@Transactional
public class PrecoEmolumentoServiceImpl implements PrecoEmolumentoService {

    private final Logger log = LoggerFactory.getLogger(PrecoEmolumentoServiceImpl.class);

    private final PrecoEmolumentoRepository precoEmolumentoRepository;

    private final PrecoEmolumentoMapper precoEmolumentoMapper;

    public PrecoEmolumentoServiceImpl(PrecoEmolumentoRepository precoEmolumentoRepository, PrecoEmolumentoMapper precoEmolumentoMapper) {
        this.precoEmolumentoRepository = precoEmolumentoRepository;
        this.precoEmolumentoMapper = precoEmolumentoMapper;
    }

    @Override
    public PrecoEmolumentoDTO save(PrecoEmolumentoDTO precoEmolumentoDTO) {
        log.debug("Request to save PrecoEmolumento : {}", precoEmolumentoDTO);

        var preco = precoEmolumentoDTO.getPreco();

        if (preco.doubleValue() == 0) {
            preco = precoEmolumentoDTO.getEmolumento().getPreco();
        }

        precoEmolumentoDTO.setPreco(preco);

        if (precoEmolumentoDTO.getPlanoMulta() == null) {
            precoEmolumentoDTO.setPlanoMulta(precoEmolumentoDTO.getEmolumento().getPlanoMulta());
        }

        if (precoEmolumentoDTO.getIsEspecificoAreaFormacao()) {
            if (precoEmolumentoDTO.getAreaFormacao() == null) {
                throw new LongonkeloException("Deve especificar a Área de formação");
            }
        }

        if (precoEmolumentoDTO.getIsEspecificoClasse()) {
            if (precoEmolumentoDTO.getClasse() == null) {
                throw new LongonkeloException("Deve especificar a Classe");
            }
        }

        if (precoEmolumentoDTO.getIsEspecificoTurno()) {
            if (precoEmolumentoDTO.getTurno() == null) {
                throw new LongonkeloException("Deve especificar o Turno");
            }
        }

        if (precoEmolumentoDTO.getIsEspecificoCurso()) {
            if (precoEmolumentoDTO.getCurso() == null) {
                throw new LongonkeloException("Deve especificar o Curso");
            }
        }

        PrecoEmolumento precoEmolumento = precoEmolumentoMapper.toEntity(precoEmolumentoDTO);
        precoEmolumento = precoEmolumentoRepository.save(precoEmolumento);
        return precoEmolumentoMapper.toDto(precoEmolumento);
    }

    @Override
    public PrecoEmolumentoDTO update(PrecoEmolumentoDTO precoEmolumentoDTO) {
        log.debug("Request to update PrecoEmolumento : {}", precoEmolumentoDTO);
        PrecoEmolumento precoEmolumento = precoEmolumentoMapper.toEntity(precoEmolumentoDTO);
        precoEmolumento = precoEmolumentoRepository.save(precoEmolumento);
        return precoEmolumentoMapper.toDto(precoEmolumento);
    }

    @Override
    public Optional<PrecoEmolumentoDTO> partialUpdate(PrecoEmolumentoDTO precoEmolumentoDTO) {
        log.debug("Request to partially update PrecoEmolumento : {}", precoEmolumentoDTO);

        return precoEmolumentoRepository
            .findById(precoEmolumentoDTO.getId())
            .map(existingPrecoEmolumento -> {
                precoEmolumentoMapper.partialUpdate(existingPrecoEmolumento, precoEmolumentoDTO);

                return existingPrecoEmolumento;
            })
            .map(precoEmolumentoRepository::save)
            .map(precoEmolumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrecoEmolumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrecoEmolumentos");
        return precoEmolumentoRepository.findAll(pageable).map(precoEmolumentoMapper::toDto);
    }

    public Page<PrecoEmolumentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return precoEmolumentoRepository.findAllWithEagerRelationships(pageable).map(precoEmolumentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrecoEmolumentoDTO> findOne(Long id) {
        log.debug("Request to get PrecoEmolumento : {}", id);
        return precoEmolumentoRepository.findOneWithEagerRelationships(id).map(precoEmolumentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrecoEmolumento : {}", id);
        precoEmolumentoRepository.deleteById(id);
    }

    @Override
    public List<PrecoEmolumentoDTO> getPrecoEmolumento(Long emolumentoID) {
        var result = precoEmolumentoRepository
            .findAll()
            .stream()
            .filter(x -> x.getEmolumento().getId().equals(emolumentoID))
            .collect(Collectors.toList());

        return precoEmolumentoMapper.toDto(result);
    }

    public List<PrecoEmolumentoDTO> getEmolumentosObrigatorioMatricula() {
        var result = precoEmolumentoRepository
            .findAll()
            .stream()
            .filter(x -> x.getEmolumento().getIsObrigatorioMatricula())
            .collect(Collectors.toList());

        return precoEmolumentoMapper.toDto(result);
    }

    public List<PrecoEmolumentoDTO> getEmolumentosObrigatorioConfirmacao() {
        var result = precoEmolumentoRepository
            .findAll()
            .stream()
            .filter(x -> x.getEmolumento().getIsObrigatorioConfirmacao())
            .collect(Collectors.toList());

        return precoEmolumentoMapper.toDto(result);
    }

    public PrecoEmolumentoDTO getPrecoEmolumento(TurmaDTO turmaDTO, Long emolumentoID) {
        var planoCurricular = turmaDTO.getPlanoCurricular();
        var curso = planoCurricular.getCurso();
        var areaFormacaoID = curso.getAreaFormacao().getId();
        var turnoID = turmaDTO.getTurno().getId();
        var classeID = planoCurricular.getClasse().getId();
        Optional<PrecoEmolumento> precoResult = null;

        // 1ª Condicao
        if (areaFormacaoID != null) {
            // somente areaformacao
            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getAreaFormacao().getId().equals(areaFormacaoID) && // areaformacao
                        x.getEmolumento().getId().equals(emolumentoID) // emolumento
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        if (areaFormacaoID != null && classeID != null) {
            // 2ª Condição
            // classe e areaformacao
            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getAreaFormacao().getId().equals(areaFormacaoID) && // areaformacao
                        x.getClasse().getId().equals(classeID) && // classe
                        x.getEmolumento().getId().equals(emolumentoID) // emolumento
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        if (areaFormacaoID != null && curso != null) {
            // 3ª Condição
            // curso e areaformacao
            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getAreaFormacao().getId().equals(areaFormacaoID) && // areaformacao
                        x.getCurso().getId().equals(curso.getId()) && // curso
                        x.getEmolumento().getId().equals(emolumentoID) // emolumento
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        // 1ª Condicao

        if (classeID != null) {
            // somente classe
            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getClasse().getId().equals(classeID) && // classe
                        x.getEmolumento().getId().equals(emolumentoID) // emolumento
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        if (curso != null) {
            // somente curso
            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getCurso().getId().equals(curso.getId()) && // curso
                        x.getEmolumento().getId().equals(emolumentoID)
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }
        if (turnoID != null) {
            // somente turno

            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getTurno().getId().equals(turnoID) && // turno
                        x.getEmolumento().getId().equals(emolumentoID)
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        // 2ª Condição
        if (curso != null && classeID != null) {
            // classe e curso

            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getCurso().getId().equals(curso.getId()) && // curso
                        x.getClasse().getId().equals(classeID) && // classe
                        x.getEmolumento().getId().equals(emolumentoID)
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        if (classeID != null && turnoID != null) {
            // classe e turno

            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getTurno().getId().equals(turnoID) && // turno
                        x.getClasse().getId().equals(classeID) && // classe
                        x.getEmolumento().getId().equals(emolumentoID)
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        // 3ª Condição
        if (curso != null && turnoID != null) {
            // curso e turno

            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getTurno().getId().equals(turnoID) && // turno
                        x.getCurso().getId().equals(curso.getId()) && // curso
                        x.getEmolumento().getId().equals(emolumentoID)
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        if (curso != null && classeID != null) {
            // curso e classe

            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getClasse().getId().equals(classeID) && // classe
                        x.getCurso().getId().equals(curso.getId()) && // curso
                        x.getEmolumento().getId().equals(emolumentoID)
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        // 4ª Condição
        if (curso != null & classeID != null & turnoID != null) {
            // curso, classe e turno

            precoResult =
                precoEmolumentoRepository
                    .findAll()
                    .stream()
                    .filter(x ->
                        x.getClasse().getId().equals(classeID) && // classe
                        x.getCurso().getId().equals(curso.getId()) && // curso
                        x.getTurno().getId().equals(turnoID) && // turno
                        x.getEmolumento().getId().equals(emolumentoID)
                    )
                    .findFirst();
            if (precoResult.isPresent()) {
                return precoEmolumentoMapper.toDto(precoResult.get());
            }
        }

        return null;
    }
}
