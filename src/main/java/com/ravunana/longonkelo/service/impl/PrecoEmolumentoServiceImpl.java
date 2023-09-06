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

        // x.getIsEspecificoAreaFormacao() && x.getIsEspecificoClasse() && x.getIsEspecificoCurso() && x.getIsEspecificoTurno()
        //        var result = precoEmolumentoRepository
        //            .findAll()
        //            .stream()
        //            .filter(x ->
        //                x.getIsEspecificoAreaFormacao() && x.getIsEspecificoClasse() && x.getIsEspecificoCurso() && x.getIsEspecificoTurno()
        //            )
        //            .findFirst();

        //        if (result.isPresent()) {
        //            var precoResult = result.filter(x ->
        //                x.getAreaFormacao().getId().equals(areaFormacaoID) &&
        //                x.getCurso().getId().equals(curso.getId()) &&
        //                x.getClasse().getId().equals(classeID) &&
        //                x.getTurno().getId().equals(turnoID)
        //            );
        //            if (precoResult.isPresent()) {
        //                return precoEmolumentoMapper.toDto(precoResult.get());
        //            }
        //        }

        //  x.getIsEspecificoClasse() && x.getIsEspecificoCurso() && x.getIsEspecificoTurno()
        //        result =
        //            precoEmolumentoRepository
        //                .findAll()
        //                .stream()
        //                .filter(x -> x.getIsEspecificoClasse() && x.getIsEspecificoCurso() && x.getIsEspecificoTurno())
        //                .findFirst();
        //
        //        if (result.isPresent()) {
        //            var precoResult = result.filter(x ->
        //                x.getCurso().getId().equals(curso.getId()) && x.getClasse().getId().equals(classeID) && x.getTurno().getId().equals(turnoID)
        //            );
        //            if (precoResult.isPresent()) {
        //                return precoEmolumentoMapper.toDto(precoResult.get());
        //            }
        //        }

        //  x.getIsEspecificoCurso() && x.getIsEspecificoTurno()
        //        result = precoEmolumentoRepository.findAll().stream().filter(x -> x.getIsEspecificoCurso() && x.getIsEspecificoTurno()).findFirst();
        //
        //        if (result.isPresent()) {
        //            var precoResult = result.filter(x -> x.getCurso().getId().equals(curso.getId()) && x.getTurno().getId().equals(turnoID));
        //            if (precoResult.isPresent()) {
        //                return precoEmolumentoMapper.toDto(precoResult.get());
        //            }
        //        }

        //  x.getIsEspecificoTurno()
        //        result = precoEmolumentoRepository.findAll().stream().filter(PrecoEmolumento::getIsEspecificoTurno).findFirst();
        //
        //        if (result.isPresent()) {
        //            var precoResult = result.filter(x -> x.getTurno().getId().equals(turnoID));
        //            if (precoResult.isPresent()) {
        //                return precoEmolumentoMapper.toDto(precoResult.get());
        //            }
        //        }

        //  x.Curso()
        //        result = precoEmolumentoRepository.findAll().stream().filter(PrecoEmolumento::getIsEspecificoCurso).findFirst();
        //
        //        if (result.isPresent()) {
        //            var precoResult = result.filter(x -> x.getCurso().getId().equals(curso.getId()));
        //            if (precoResult.isPresent()) {
        //                return precoEmolumentoMapper.toDto(precoResult.get());
        //            }
        //        }

        //  x.Classe()
        // var result = precoEmolumentoRepository.findAll().stream().filter(PrecoEmolumento::getIsEspecificoClasse).findFirst();

        // if (result.isPresent()) {
        var precoResult = precoEmolumentoRepository
            .findAll()
            .stream()
            .filter(x -> x.getClasse().getId().equals(classeID) && x.getEmolumento().getId().equals(emolumentoID))
            .findFirst();
        if (precoResult.isPresent()) {
            return precoEmolumentoMapper.toDto(precoResult.get());
        }
        // }

        return null;
    }
    //    public PrecoEmolumentoDTO getPrecoEmolumento(TurmaDTO turmaDTO) {
    //        var planoCurricular = turmaDTO.getPlanoCurricular();
    //        var curso = planoCurricular.getCurso();
    //        var areaFormacaoID = curso.getAreaFormacao().getId();
    //        var turnoID = turmaDTO.getTurno().getId();
    //        var classeID = planoCurricular.getClasse().getId();
    //
    //        // x.getIsEspecificoAreaFormacao() && x.getIsEspecificoClasse() && x.getIsEspecificoCurso() && x.getIsEspecificoTurno()
    //        //        var result = precoEmolumentoRepository
    //        //            .findAll()
    //        //            .stream()
    //        //            .filter(x ->
    //        //                x.getIsEspecificoAreaFormacao() && x.getIsEspecificoClasse() && x.getIsEspecificoCurso() && x.getIsEspecificoTurno()
    //        //            )
    //        //            .findFirst();
    //
    //        //        if (result.isPresent()) {
    //        //            var precoResult = result.filter(x ->
    //        //                x.getAreaFormacao().getId().equals(areaFormacaoID) &&
    //        //                x.getCurso().getId().equals(curso.getId()) &&
    //        //                x.getClasse().getId().equals(classeID) &&
    //        //                x.getTurno().getId().equals(turnoID)
    //        //            );
    //        //            if (precoResult.isPresent()) {
    //        //                return precoEmolumentoMapper.toDto(precoResult.get());
    //        //            }
    //        //        }
    //
    //        //  x.getIsEspecificoClasse() && x.getIsEspecificoCurso() && x.getIsEspecificoTurno()
    //        //        result =
    //        //            precoEmolumentoRepository
    //        //                .findAll()
    //        //                .stream()
    //        //                .filter(x -> x.getIsEspecificoClasse() && x.getIsEspecificoCurso() && x.getIsEspecificoTurno())
    //        //                .findFirst();
    //        //
    //        //        if (result.isPresent()) {
    //        //            var precoResult = result.filter(x ->
    //        //                x.getCurso().getId().equals(curso.getId()) && x.getClasse().getId().equals(classeID) && x.getTurno().getId().equals(turnoID)
    //        //            );
    //        //            if (precoResult.isPresent()) {
    //        //                return precoEmolumentoMapper.toDto(precoResult.get());
    //        //            }
    //        //        }
    //
    //        //  x.getIsEspecificoCurso() && x.getIsEspecificoTurno()
    //        //        result = precoEmolumentoRepository.findAll().stream().filter(x -> x.getIsEspecificoCurso() && x.getIsEspecificoTurno()).findFirst();
    //        //
    //        //        if (result.isPresent()) {
    //        //            var precoResult = result.filter(x -> x.getCurso().getId().equals(curso.getId()) && x.getTurno().getId().equals(turnoID));
    //        //            if (precoResult.isPresent()) {
    //        //                return precoEmolumentoMapper.toDto(precoResult.get());
    //        //            }
    //        //        }
    //
    //        //  x.getIsEspecificoTurno()
    //        //        result = precoEmolumentoRepository.findAll().stream().filter(PrecoEmolumento::getIsEspecificoTurno).findFirst();
    //        //
    //        //        if (result.isPresent()) {
    //        //            var precoResult = result.filter(x -> x.getTurno().getId().equals(turnoID));
    //        //            if (precoResult.isPresent()) {
    //        //                return precoEmolumentoMapper.toDto(precoResult.get());
    //        //            }
    //        //        }
    //
    //        //  x.Curso()
    //        //        result = precoEmolumentoRepository.findAll().stream().filter(PrecoEmolumento::getIsEspecificoCurso).findFirst();
    //        //
    //        //        if (result.isPresent()) {
    //        //            var precoResult = result.filter(x -> x.getCurso().getId().equals(curso.getId()));
    //        //            if (precoResult.isPresent()) {
    //        //                return precoEmolumentoMapper.toDto(precoResult.get());
    //        //            }
    //        //        }
    //
    //        //  x.Classe()
    //        // var result = precoEmolumentoRepository.findAll().stream().filter(PrecoEmolumento::getIsEspecificoClasse).findFirst();
    //
    //        // if (result.isPresent()) {
    //        //            var precoResult = result.filter(x -> x.getClasse().getId().equals(classeID));
    //        //            if (precoResult.isPresent()) {
    //        //                return precoEmolumentoMapper.toDto(precoResult.get());
    //        //            }
    //        // }
    //
    //        var precoResult = precoEmolumentoRepository.findAll().stream().filter(x -> x.getClasse().getId().equals(classeID)).findFirst();
    //        if (precoResult.isPresent()) {
    //            return precoEmolumentoMapper.toDto(precoResult.get());
    //        }
    //
    //        return null;
    //    }
}
