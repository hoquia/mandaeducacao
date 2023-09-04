package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.AreaFormacao;
import com.ravunana.longonkelo.repository.AreaFormacaoRepository;
import com.ravunana.longonkelo.service.AreaFormacaoService;
import com.ravunana.longonkelo.service.dto.AreaFormacaoDTO;
import com.ravunana.longonkelo.service.mapper.AreaFormacaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AreaFormacao}.
 */
@Service
@Transactional
public class AreaFormacaoServiceImpl implements AreaFormacaoService {

    private final Logger log = LoggerFactory.getLogger(AreaFormacaoServiceImpl.class);

    private final AreaFormacaoRepository areaFormacaoRepository;

    private final AreaFormacaoMapper areaFormacaoMapper;

    public AreaFormacaoServiceImpl(AreaFormacaoRepository areaFormacaoRepository, AreaFormacaoMapper areaFormacaoMapper) {
        this.areaFormacaoRepository = areaFormacaoRepository;
        this.areaFormacaoMapper = areaFormacaoMapper;
    }

    @Override
    public AreaFormacaoDTO save(AreaFormacaoDTO areaFormacaoDTO) {
        log.debug("Request to save AreaFormacao : {}", areaFormacaoDTO);
        AreaFormacao areaFormacao = areaFormacaoMapper.toEntity(areaFormacaoDTO);
        areaFormacao = areaFormacaoRepository.save(areaFormacao);
        return areaFormacaoMapper.toDto(areaFormacao);
    }

    @Override
    public AreaFormacaoDTO update(AreaFormacaoDTO areaFormacaoDTO) {
        log.debug("Request to update AreaFormacao : {}", areaFormacaoDTO);
        AreaFormacao areaFormacao = areaFormacaoMapper.toEntity(areaFormacaoDTO);
        areaFormacao = areaFormacaoRepository.save(areaFormacao);
        return areaFormacaoMapper.toDto(areaFormacao);
    }

    @Override
    public Optional<AreaFormacaoDTO> partialUpdate(AreaFormacaoDTO areaFormacaoDTO) {
        log.debug("Request to partially update AreaFormacao : {}", areaFormacaoDTO);

        return areaFormacaoRepository
            .findById(areaFormacaoDTO.getId())
            .map(existingAreaFormacao -> {
                areaFormacaoMapper.partialUpdate(existingAreaFormacao, areaFormacaoDTO);

                return existingAreaFormacao;
            })
            .map(areaFormacaoRepository::save)
            .map(areaFormacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AreaFormacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AreaFormacaos");
        return areaFormacaoRepository.findAll(pageable).map(areaFormacaoMapper::toDto);
    }

    public Page<AreaFormacaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return areaFormacaoRepository.findAllWithEagerRelationships(pageable).map(areaFormacaoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AreaFormacaoDTO> findOne(Long id) {
        log.debug("Request to get AreaFormacao : {}", id);
        return areaFormacaoRepository.findOneWithEagerRelationships(id).map(areaFormacaoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AreaFormacao : {}", id);
        areaFormacaoRepository.deleteById(id);
    }
}
