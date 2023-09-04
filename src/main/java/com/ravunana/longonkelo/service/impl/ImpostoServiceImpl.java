package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Imposto;
import com.ravunana.longonkelo.repository.ImpostoRepository;
import com.ravunana.longonkelo.service.ImpostoService;
import com.ravunana.longonkelo.service.dto.ImpostoDTO;
import com.ravunana.longonkelo.service.mapper.ImpostoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Imposto}.
 */
@Service
@Transactional
public class ImpostoServiceImpl implements ImpostoService {

    private final Logger log = LoggerFactory.getLogger(ImpostoServiceImpl.class);

    private final ImpostoRepository impostoRepository;

    private final ImpostoMapper impostoMapper;

    public ImpostoServiceImpl(ImpostoRepository impostoRepository, ImpostoMapper impostoMapper) {
        this.impostoRepository = impostoRepository;
        this.impostoMapper = impostoMapper;
    }

    @Override
    public ImpostoDTO save(ImpostoDTO impostoDTO) {
        log.debug("Request to save Imposto : {}", impostoDTO);
        Imposto imposto = impostoMapper.toEntity(impostoDTO);
        imposto = impostoRepository.save(imposto);
        return impostoMapper.toDto(imposto);
    }

    @Override
    public ImpostoDTO update(ImpostoDTO impostoDTO) {
        log.debug("Request to update Imposto : {}", impostoDTO);
        Imposto imposto = impostoMapper.toEntity(impostoDTO);
        imposto = impostoRepository.save(imposto);
        return impostoMapper.toDto(imposto);
    }

    @Override
    public Optional<ImpostoDTO> partialUpdate(ImpostoDTO impostoDTO) {
        log.debug("Request to partially update Imposto : {}", impostoDTO);

        return impostoRepository
            .findById(impostoDTO.getId())
            .map(existingImposto -> {
                impostoMapper.partialUpdate(existingImposto, impostoDTO);

                return existingImposto;
            })
            .map(impostoRepository::save)
            .map(impostoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImpostoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Impostos");
        return impostoRepository.findAll(pageable).map(impostoMapper::toDto);
    }

    public Page<ImpostoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return impostoRepository.findAllWithEagerRelationships(pageable).map(impostoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImpostoDTO> findOne(Long id) {
        log.debug("Request to get Imposto : {}", id);
        return impostoRepository.findOneWithEagerRelationships(id).map(impostoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Imposto : {}", id);
        impostoRepository.deleteById(id);
    }
}
