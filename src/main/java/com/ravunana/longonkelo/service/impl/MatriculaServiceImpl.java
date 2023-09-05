package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.enumeration.EstadoAcademico;
import com.ravunana.longonkelo.domain.enumeration.EstadoItemFactura;
import com.ravunana.longonkelo.repository.MatriculaRepository;
import com.ravunana.longonkelo.service.MatriculaService;
import com.ravunana.longonkelo.service.dto.FacturaDTO;
import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.mapper.MatriculaMapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
 * Service Implementation for managing {@link Matricula}.
 */
@Service
@Transactional
public class MatriculaServiceImpl implements MatriculaService {

    private final Logger log = LoggerFactory.getLogger(MatriculaServiceImpl.class);

    private final MatriculaRepository matriculaRepository;

    private final MatriculaMapper matriculaMapper;
    private final AnoLectivoServiceImpl anoLectivoService;

    private final PrecoEmolumentoServiceImpl precoEmolumentoService;

    private final FacturaServiceImpl facturaService;
    private final ItemFacturaServiceImpl itemFacturaService;

    public MatriculaServiceImpl(
        MatriculaRepository matriculaRepository,
        MatriculaMapper matriculaMapper,
        AnoLectivoServiceImpl anoLectivoService,
        PrecoEmolumentoServiceImpl precoEmolumentoService,
        FacturaServiceImpl facturaService,
        ItemFacturaServiceImpl itemFacturaService
    ) {
        this.matriculaRepository = matriculaRepository;
        this.matriculaMapper = matriculaMapper;
        this.anoLectivoService = anoLectivoService;
        this.precoEmolumentoService = precoEmolumentoService;
        this.facturaService = facturaService;
        this.itemFacturaService = itemFacturaService;
    }

    @Override
    public MatriculaDTO save(MatriculaDTO matriculaDTO) {
        log.debug("Request to save Matricula : {}", matriculaDTO);

        /*
        if ( !matriculaDTO.getEstado().name().equals(EstadoAcademico.CONFIRMADO.name()) || !matriculaDTO.getEstado().name().equals(EstadoAcademico.MATRICULADO.name()) ) {
            throw new LongonkeloException("No acto da matricula só podes definir o estado CONFIRMADO ou MATRICULADO");
        }
        */

        int numeroChamada = matriculaDTO.getNumeroChamada();
        String numeroMatricula = matriculaDTO.getNumeroMatricula();
        matriculaDTO.setTimestamp(ZonedDateTime.now());
        var anoLectivo = anoLectivoService.getAnoLectivoActual();

        if (matriculaDTO.getResponsavelFinanceiro() == null) {
            matriculaDTO.setResponsavelFinanceiro(matriculaDTO.getDiscente().getEncarregadoEducacao());
        }

        if (numeroChamada == 0) {
            numeroChamada = atribuirNumeroChamada(matriculaDTO);
        }

        matriculaDTO.setNumeroChamada(numeroChamada);

        if (numeroMatricula.equals("NA")) {
            numeroMatricula = getNumero(matriculaDTO.getDiscente().getDocumentoNumero(), anoLectivo.getDescricao());
        }

        matriculaDTO.setNumeroMatricula(numeroMatricula);

        matriculaDTO = getUniqueFields(matriculaDTO);

        Matricula matricula = matriculaMapper.toEntity(matriculaDTO);
        matricula = matriculaRepository.save(matricula);

        // pegar todos os emolumentos obrigaorios da turma do aluno matricula/confirmado

        return matriculaMapper.toDto(matricula);
    }

    @Override
    public MatriculaDTO update(MatriculaDTO matriculaDTO) {
        log.debug("Request to update Matricula : {}", matriculaDTO);
        Matricula matricula = matriculaMapper.toEntity(matriculaDTO);
        matricula = matriculaRepository.save(matricula);
        return matriculaMapper.toDto(matricula);
    }

    @Override
    public Optional<MatriculaDTO> partialUpdate(MatriculaDTO matriculaDTO) {
        log.debug("Request to partially update Matricula : {}", matriculaDTO);

        return matriculaRepository
            .findById(matriculaDTO.getId())
            .map(existingMatricula -> {
                matriculaMapper.partialUpdate(existingMatricula, matriculaDTO);

                return existingMatricula;
            })
            .map(matriculaRepository::save)
            .map(matriculaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatriculaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Matriculas");
        return matriculaRepository.findAll(pageable).map(matriculaMapper::toDto);
    }

    public Page<MatriculaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return matriculaRepository.findAllWithEagerRelationships(pageable).map(matriculaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatriculaDTO> findOne(Long id) {
        log.debug("Request to get Matricula : {}", id);
        return matriculaRepository.findOneWithEagerRelationships(id).map(matriculaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Matricula : {}", id);
        matriculaRepository.deleteById(id);
    }

    @Override
    public MatriculaDTO getUniqueFields(MatriculaDTO matriculaDTO) {
        // matriculaId-turmaId-instituicaoID
        // chaveComposta1 String unique,
        // numeroChamada-turmaId-instituicaoID
        // chaveComposta2 String unique,
        matriculaDTO.setChaveComposta1(matriculaDTO.getNumeroMatricula() + "." + matriculaDTO.getTurma().getId());
        matriculaDTO.setChaveComposta2(matriculaDTO.getNumeroChamada() + "." + matriculaDTO.getTurma().getId());
        return matriculaDTO;
    }

    @Override
    public String getNumero(String numeroDocumento, String anoLectivo) {
        StringBuilder numeroDocumentoBuilder = new StringBuilder(numeroDocumento);
        do {
            numeroDocumentoBuilder.append("0");
        } while (numeroDocumentoBuilder.length() < 9);
        numeroDocumento = numeroDocumentoBuilder.toString();

        return numeroDocumento.substring(0, 9) + "/" + anoLectivo;
    }

    @Override
    public int atribuirNumeroChamada(MatriculaDTO matriculaDTO) {
        long numero = 0;
        var matriculas = matriculaRepository
            .findAll()
            .stream()
            .filter(x -> x.getTurma().getId().longValue() == matriculaDTO.getTurma().getId().longValue())
            .collect(Collectors.toList());

        if (matriculas.isEmpty()) {
            return 1;
        }

        numero = matriculas.size();
        numero++;

        /*if (matriculaDTO.getTurma().getCriterioOrdenacaoNumero().equals(CriterioNumeroChamada.DATA_CONFIRMACAO)) {

            numero = matriculas.size();
            numero++;

        }*/

        return (int) numero;
    }

    @Override
    public List<MatriculaDTO> getMatriculas(Long turmaID) {
        var result = matriculaRepository.findAll().stream().filter(x -> x.getTurma().getId().equals(turmaID)).collect(Collectors.toList());
        return matriculaMapper.toDto(result);
    }

    private void criarFacturaActoMatricula(MatriculaDTO matriculaDTO) {
        if (matriculaDTO.getEstado().equals(EstadoAcademico.MATRICULADO)) {
            var factura = new FacturaDTO();

            // Salvar a factura
            facturaService.save(factura);

            var pacoteEmolumentoMatricula = precoEmolumentoService.getEmolumentosObrigatorioMatricula();

            for (var p : pacoteEmolumentoMatricula) {
                var itemFactura = new ItemFacturaDTO();
                itemFactura.setDesconto(BigDecimal.ZERO);
                itemFactura.setEmolumento(p.getEmolumento());
                itemFactura.setEstado(EstadoItemFactura.PENDENTE);
                itemFactura.setJuro(BigDecimal.ZERO);
                itemFactura.setMulta(BigDecimal.ZERO);

                itemFactura.setFactura(factura);

                // salvar os items da factura
                itemFacturaService.save(itemFactura);
            }
        }
    }
}
