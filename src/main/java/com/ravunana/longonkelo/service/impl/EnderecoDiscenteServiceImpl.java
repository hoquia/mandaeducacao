package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.EnderecoDiscente;
import com.ravunana.longonkelo.repository.EnderecoDiscenteRepository;
import com.ravunana.longonkelo.service.EnderecoDiscenteService;
import com.ravunana.longonkelo.service.dto.EnderecoDiscenteDTO;
import com.ravunana.longonkelo.service.mapper.EnderecoDiscenteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EnderecoDiscente}.
 */
@Service
@Transactional
public class EnderecoDiscenteServiceImpl implements EnderecoDiscenteService {

    private final Logger log = LoggerFactory.getLogger(EnderecoDiscenteServiceImpl.class);

    private final EnderecoDiscenteRepository enderecoDiscenteRepository;

    private final EnderecoDiscenteMapper enderecoDiscenteMapper;

    public EnderecoDiscenteServiceImpl(
        EnderecoDiscenteRepository enderecoDiscenteRepository,
        EnderecoDiscenteMapper enderecoDiscenteMapper
    ) {
        this.enderecoDiscenteRepository = enderecoDiscenteRepository;
        this.enderecoDiscenteMapper = enderecoDiscenteMapper;
    }

    @Override
    public EnderecoDiscenteDTO save(EnderecoDiscenteDTO enderecoDiscenteDTO) {
        log.debug("Request to save EnderecoDiscente : {}", enderecoDiscenteDTO);
        EnderecoDiscente enderecoDiscente = enderecoDiscenteMapper.toEntity(enderecoDiscenteDTO);
        enderecoDiscente = enderecoDiscenteRepository.save(enderecoDiscente);
        return enderecoDiscenteMapper.toDto(enderecoDiscente);
    }

    @Override
    public EnderecoDiscenteDTO update(EnderecoDiscenteDTO enderecoDiscenteDTO) {
        log.debug("Request to update EnderecoDiscente : {}", enderecoDiscenteDTO);
        EnderecoDiscente enderecoDiscente = enderecoDiscenteMapper.toEntity(enderecoDiscenteDTO);
        enderecoDiscente = enderecoDiscenteRepository.save(enderecoDiscente);
        return enderecoDiscenteMapper.toDto(enderecoDiscente);
    }

    @Override
    public Optional<EnderecoDiscenteDTO> partialUpdate(EnderecoDiscenteDTO enderecoDiscenteDTO) {
        log.debug("Request to partially update EnderecoDiscente : {}", enderecoDiscenteDTO);

        return enderecoDiscenteRepository
            .findById(enderecoDiscenteDTO.getId())
            .map(existingEnderecoDiscente -> {
                enderecoDiscenteMapper.partialUpdate(existingEnderecoDiscente, enderecoDiscenteDTO);

                return existingEnderecoDiscente;
            })
            .map(enderecoDiscenteRepository::save)
            .map(enderecoDiscenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnderecoDiscenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EnderecoDiscentes");
        return enderecoDiscenteRepository.findAll(pageable).map(enderecoDiscenteMapper::toDto);
    }

    public Page<EnderecoDiscenteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return enderecoDiscenteRepository.findAllWithEagerRelationships(pageable).map(enderecoDiscenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnderecoDiscenteDTO> findOne(Long id) {
        log.debug("Request to get EnderecoDiscente : {}", id);
        return enderecoDiscenteRepository.findOneWithEagerRelationships(id).map(enderecoDiscenteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EnderecoDiscente : {}", id);
        enderecoDiscenteRepository.deleteById(id);
    }
}
