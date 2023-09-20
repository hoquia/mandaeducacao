package com.ravunana.longonkelo.service.impl;

import com.ravunana.longonkelo.config.Constants;
import com.ravunana.longonkelo.domain.Matricula;
import com.ravunana.longonkelo.domain.enumeration.*;
import com.ravunana.longonkelo.repository.MatriculaRepository;
import com.ravunana.longonkelo.service.MatriculaService;
import com.ravunana.longonkelo.service.dto.FacturaDTO;
import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.mapper.MatriculaMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    private final List<ItemFacturaDTO> ITEMS_FACTURA = new ArrayList<ItemFacturaDTO>();
    private final Logger log = LoggerFactory.getLogger(MatriculaServiceImpl.class);

    private final MatriculaRepository matriculaRepository;

    private final MatriculaMapper matriculaMapper;
    private final AnoLectivoServiceImpl anoLectivoService;
    private final PrecoEmolumentoServiceImpl precoEmolumentoService;
    private final FacturaServiceImpl facturaService;
    private final ItemFacturaServiceImpl itemFacturaService;
    private final DocumentoComercialServiceImpl documentoComercialService;

    private final TurmaServiceImpl turmaService;

    public MatriculaServiceImpl(
        MatriculaRepository matriculaRepository,
        MatriculaMapper matriculaMapper,
        AnoLectivoServiceImpl anoLectivoService,
        PrecoEmolumentoServiceImpl precoEmolumentoService,
        FacturaServiceImpl facturaService,
        ItemFacturaServiceImpl itemFacturaService,
        DocumentoComercialServiceImpl documentoComercialService,
        TurmaServiceImpl turmaService
    ) {
        this.matriculaRepository = matriculaRepository;
        this.matriculaMapper = matriculaMapper;
        this.anoLectivoService = anoLectivoService;
        this.precoEmolumentoService = precoEmolumentoService;
        this.facturaService = facturaService;
        this.itemFacturaService = itemFacturaService;
        this.documentoComercialService = documentoComercialService;
        this.turmaService = turmaService;
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

        criarFacturaActoMatricula(matriculaMapper.toDto(matricula));

        // actualizar confirmados na turma
        var turma = turmaService.findOne(matricula.getTurma().getId()).get();
        turma.setConfirmado(turma.getConfirmado() + 1);

        if (turma.getConfirmado().equals(turma.getLotacao())) {
            turma.setIsDisponivel(false);
        }

        turmaService.partialUpdate(turma);

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

    @Override
    public List<MatriculaDTO> getMatroculaWithClasse(Long classeID) {
        var result = matriculaRepository
            .findAll()
            .stream()
            .filter(m -> m.getTurma().getPlanoCurricular().getClasse().getId().equals(classeID))
            .collect(Collectors.toList());

        return matriculaMapper.toDto(result);
    }

    private void criarFacturaActoMatricula(MatriculaDTO matriculaDTO) {
        var anoLectivo = anoLectivoService.getAnoLectivoActual();
        var serieDocumentoComercial = documentoComercialService.getSerieDocumentoComercialActivoByTipoFiscal(
            anoLectivo.getAno(),
            DocumentoFiscal.FT
        );
        var totalFactura = BigDecimal.ZERO;
        var sequenciaResult = documentoComercialService.getSequenciaDocumento(serieDocumentoComercial.getId());
        Long sequencia = 1L;

        if (sequenciaResult.isPresent()) {
            sequencia = sequenciaResult.get().getSequencia();
            sequencia++;
        }

        sequencia = facturaService.findAll(Pageable.ofSize(1000000)).getTotalElements();
        sequencia++;

        var factura = new FacturaDTO();

        factura.setCambio(BigDecimal.ZERO);
        factura.setCae("");
        factura.setEstado(EstadoDocumentoComercial.P);
        factura.setMatricula(matriculaDTO);
        factura.setCodigoEntrega("");
        factura.setDataEmissao(anoLectivo.getInicio());
        factura.setDataVencimento(anoLectivo.getFim());
        factura.setDocumentoComercial(serieDocumentoComercial.getTipoDocumento());
        factura.setFimTransporte(ZonedDateTime.now().plusMonths(9));
        factura.setInicioTransporte(ZonedDateTime.now());
        factura.setIsAutoFacturacao(false);
        factura.setIsEmitidaNomeEContaTerceiro(false);
        factura.setIsFiscalizado(false);
        factura.setIsMoedaEntrangeira(false);
        factura.setIsNovo(true);
        factura.setIsRegimeCaixa(false);
        factura.setKeyVersion(1);
        factura.setMoeda("AKZ");
        factura.setMotivoAnulacao(null);
        factura.setOrigem("P");
        factura.setHash("");
        factura.setHashControl("");
        factura.setHashShort("");
        factura.setSignText("");

        factura.setNumero(
            serieDocumentoComercial.getTipoDocumento().getSiglaInterna() + " " + serieDocumentoComercial.getSerie() + "/" + sequencia
        );

        factura.setCredito(factura.getTotalFactura());
        factura.setDebito(BigDecimal.ZERO);

        var pacoteEmolumento = precoEmolumentoService.getEmolumentosObrigatorioMatricula();

        if (matriculaDTO.getEstado().equals(EstadoAcademico.CONFIRMADO)) {
            pacoteEmolumento = precoEmolumentoService.getEmolumentosObrigatorioConfirmacao();
        }

        for (var p : pacoteEmolumento) {
            var emolumento = p.getEmolumento();

            if (ITEMS_FACTURA.stream().anyMatch(x -> Objects.equals(x.getEmolumento().getId(), emolumento.getId()))) {
                continue;
            }

            var itemFactura = new ItemFacturaDTO();
            var imposto = emolumento.getImposto();
            var precoUnitario = p.getPreco();

            var precoEspecifico = precoEmolumentoService.getPrecoEmolumento(matriculaDTO.getTurma(), emolumento.getId());

            if (precoEspecifico != null) {
                precoUnitario = precoEspecifico.getPreco();
            }

            itemFactura.setDesconto(BigDecimal.ZERO);
            itemFactura.setEmolumento(p.getEmolumento());
            itemFactura.setEstado(EstadoItemFactura.PENDENTE);
            itemFactura.setJuro(BigDecimal.ZERO);
            itemFactura.setMulta(BigDecimal.ZERO);
            itemFactura.setDescricao("");
            itemFactura.setEmissao(factura.getDataEmissao());
            itemFactura.setExpiracao(factura.getDataVencimento());
            itemFactura.setTaxCode(imposto.getCodigoImposto().getDescricao());
            itemFactura.setTaxCountryRegion(imposto.getPais());
            itemFactura.setTaxExemptionCode(imposto.getMotivoIsencaoCodigo().getDescricao());
            itemFactura.setTaxType(imposto.getTipoImposto().getDescricao());
            itemFactura.setTaxPercentage(imposto.getTaxa());
            itemFactura.setTaxExemptionReason(imposto.getMotivoDescricao());
            itemFactura.setQuantidade(emolumento.getQuantidade());
            itemFactura.setPrecoUnitario(precoUnitario);
            itemFactura.setPrecoTotal(BigDecimal.valueOf(itemFactura.getQuantidade() * itemFactura.getPrecoUnitario().doubleValue()));
            itemFactura.setPeriodo(emolumento.getPeriodo());

            if (p.getPlanoMulta() != null) {
                int periodoEmolumento = emolumento.getPeriodo();
                int diaExpiracao = p.getPlanoMulta().getDiaAplicacaoMulta() - 1;

                if (p.getPlanoMulta().getMetodoAplicacaoMulta().equals(MetodoAplicacaoMulta.DEPOIS_MES_EMOLUMENTO)) {
                    periodoEmolumento++;
                }

                itemFactura.setExpiracao(LocalDate.of(factura.getDataEmissao().getYear(), periodoEmolumento, diaExpiracao));
            }

            totalFactura = totalFactura.add(itemFactura.getPrecoTotal());

            ITEMS_FACTURA.add(itemFactura);
        }

        factura.setTotalFactura(totalFactura);
        factura.setTotalDiferenca(BigDecimal.ZERO);
        factura.setTotalDescontoComercial(BigDecimal.ZERO);
        factura.setTotalIliquido(totalFactura);
        factura.setTotalDescontoFinanceiro(BigDecimal.ZERO);
        factura.setTotalImpostoEspecialConsumo(BigDecimal.ZERO);
        factura.setTotalImpostoIVA(BigDecimal.ZERO);
        factura.setTotalImpostoRetencaoFonte(BigDecimal.ZERO);
        factura.setTotalMoedaEntrangeira(BigDecimal.ZERO);
        factura.setTotalPagar(totalFactura);
        factura.setTotalLiquido(totalFactura);
        factura.setTotalPago(BigDecimal.ZERO);
        factura.setTimestamp(Constants.DATE_TIME);

        // Salvar a factura

        var facturaSalva = facturaService.save(factura);

        // salvar os items da factura
        for (var item : ITEMS_FACTURA) {
            item.setFactura(facturaSalva);
            itemFacturaService.save(item);
        }

        ITEMS_FACTURA.clear();
    }
}
