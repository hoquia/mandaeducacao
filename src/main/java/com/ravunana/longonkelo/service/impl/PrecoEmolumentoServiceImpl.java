package com.ravunana.longonkelo.service.impl;

import com.ravunana.longokelo.config.LongonkeloException;
import com.ravunana.longonkelo.domain.PrecoEmolumento;
import com.ravunana.longonkelo.repository.PrecoEmolumentoRepository;
import com.ravunana.longonkelo.service.PrecoEmolumentoService;
import com.ravunana.longonkelo.service.dto.PrecoEmolumentoDTO;
import com.ravunana.longonkelo.service.mapper.PrecoEmolumentoMapper;
import java.math.BigDecimal;
import java.util.Optional;
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

        if (preco == BigDecimal.ZERO) {
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
}
