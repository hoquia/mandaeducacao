package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Conta;
import com.ravunana.longonkelo.repository.ContaRepository;
import com.ravunana.longonkelo.service.ContaService;
import com.ravunana.longonkelo.service.dto.ContaDTO;
import com.ravunana.longonkelo.service.mapper.ContaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Conta}.
 */
@Service
@Transactional
public class ContaServiceImpl implements ContaService {

    private final Logger log = LoggerFactory.getLogger(ContaServiceImpl.class);

    private final ContaRepository contaRepository;

    private final ContaMapper contaMapper;

    public ContaServiceImpl(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    @Override
    public ContaDTO save(ContaDTO contaDTO) {
        log.debug("Request to save Conta : {}", contaDTO);
        Conta conta = contaMapper.toEntity(contaDTO);
        conta = contaRepository.save(conta);
        return contaMapper.toDto(conta);
    }

    @Override
    public ContaDTO update(ContaDTO contaDTO) {
        log.debug("Request to update Conta : {}", contaDTO);
        Conta conta = contaMapper.toEntity(contaDTO);
        conta = contaRepository.save(conta);
        return contaMapper.toDto(conta);
    }

    @Override
    public Optional<ContaDTO> partialUpdate(ContaDTO contaDTO) {
        log.debug("Request to partially update Conta : {}", contaDTO);

        return contaRepository
            .findById(contaDTO.getId())
            .map(existingConta -> {
                contaMapper.partialUpdate(existingConta, contaDTO);

                return existingConta;
            })
            .map(contaRepository::save)
            .map(contaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contas");
        return contaRepository.findAll(pageable).map(contaMapper::toDto);
    }

    public Page<ContaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return contaRepository.findAllWithEagerRelationships(pageable).map(contaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContaDTO> findOne(Long id) {
        log.debug("Request to get Conta : {}", id);
        return contaRepository.findOneWithEagerRelationships(id).map(contaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conta : {}", id);
        contaRepository.deleteById(id);
    }
}
