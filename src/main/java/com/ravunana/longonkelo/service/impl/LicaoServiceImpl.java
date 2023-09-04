package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Licao;
import com.ravunana.longonkelo.repository.LicaoRepository;
import com.ravunana.longonkelo.service.LicaoService;
import com.ravunana.longonkelo.service.dto.LicaoDTO;
import com.ravunana.longonkelo.service.mapper.LicaoMapper;
import java.util.Optional;
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

    public LicaoServiceImpl(LicaoRepository licaoRepository, LicaoMapper licaoMapper) {
        this.licaoRepository = licaoRepository;
        this.licaoMapper = licaoMapper;
    }

    @Override
    public LicaoDTO save(LicaoDTO licaoDTO) {
        log.debug("Request to save Licao : {}", licaoDTO);
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
}
