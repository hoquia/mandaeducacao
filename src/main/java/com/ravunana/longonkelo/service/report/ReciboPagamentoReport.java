package com.ravunana.longonkelo.service.report;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ravunana.longonkelo.config.Constants;
import com.ravunana.longonkelo.domain.enumeration.EstadoItemFactura;
import com.ravunana.longonkelo.security.SecurityUtils;
import com.ravunana.longonkelo.service.impl.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class ReciboPagamentoReport {

    private final ReportService reportService;
    private final InstituicaoEnsinoServiceImpl instituicaoEnsinoService;
    private final MatriculaServiceImpl matriculaService;
    private final FacturaServiceImpl facturaService;
    private final TransacaoServiceImpl transacaoService;
    private final EnderecoDiscenteServiceImpl enderecoDiscenteService;
    private final ItemFacturaServiceImpl itemFacturaService;
    private final ResumoImpostoFacturaServiceImpl resumoImpostoFacturaService;
    private final ReciboServiceImpl reciboService;

    public ReciboPagamentoReport(
        ReportService reportService,
        InstituicaoEnsinoServiceImpl instituicaoEnsinoService,
        MatriculaServiceImpl matriculaService,
        FacturaServiceImpl facturaService,
        TransacaoServiceImpl transacaoService,
        EnderecoDiscenteServiceImpl enderecoDiscenteService,
        ItemFacturaServiceImpl itemFacturaService,
        ResumoImpostoFacturaServiceImpl resumoImpostoFacturaService,
        ReciboServiceImpl reciboService
    ) {
        this.reportService = reportService;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.matriculaService = matriculaService;
        this.facturaService = facturaService;
        this.transacaoService = transacaoService;
        this.enderecoDiscenteService = enderecoDiscenteService;
        this.itemFacturaService = itemFacturaService;
        this.resumoImpostoFacturaService = resumoImpostoFacturaService;
        this.reciboService = reciboService;
    }

    public String gerarReciboPdf(Long reciboID) {
        Document document;
        String pdfName;
        FileOutputStream file;
        pdfName = "recibo-pagamento";
        document = new Document(PageSize.A4.rotate(), 4f, 4f, 4f, 4f);
        String tempFileName = "./" + pdfName + ".pdf";

        try {
            Font fontNormal = FontFactory.getFont("Helvetica", 7, Font.NORMAL, Color.BLACK);
            Font fontBold = FontFactory.getFont("Helvetica", 7, Font.BOLD, Color.BLACK);
            float padding = 2f;
            float leading = fontNormal.getSize() * 1.2f;
            com.lowagie.text.Rectangle border = new com.lowagie.text.Rectangle(0f, 0f);
            border.setBorderWidthLeft(0f);
            border.setBorderWidthBottom(0f);
            border.setBorderWidthRight(0f);
            border.setBorderWidthTop(0f);

            tempFileName = reportService.createTempFile(pdfName, ".pdf");
            file = new FileOutputStream(tempFileName);

            var recibo = reciboService.findOne(reciboID).get();

            var matricula = recibo.getMatricula();

            final PdfWriter pdfWriter = PdfWriter.getInstance(document, file);

            HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
            HeaderFooter footer = new HeaderFooter(
                new Phrase(String.valueOf(getAssinatura(SecurityUtils.getCurrentUserLogin().get(), matricula.getDiscente().getNome()))),
                new Phrase(".")
            );
            // document.setHeader(header);

            document.open();

            // Pdf Metadatas
            document.addTitle("Extracto de Pagamento " + reciboID);
            document.addSubject(reciboID.toString());
            document.addKeywords("recibo," + "pagamento," + reciboID);
            document.addCreator("ravunana,lda");
            document.addAuthor("ravunana");
            //            document.setFooter(footer);

            PdfPTable layoutTable = new PdfPTable(2);
            layoutTable.setWidthPercentage(100f);

            layoutTable.addCell(
                makeCellTable(
                    getRecibo(reciboID, "Original"),
                    Element.ALIGN_CENTER,
                    Element.ALIGN_LEFT,
                    leading,
                    padding,
                    border,
                    true,
                    false
                )
            );
            layoutTable.addCell(
                makeCellTable(
                    getRecibo(reciboID, "Cópia"),
                    Element.ALIGN_CENTER,
                    Element.ALIGN_RIGHT,
                    leading,
                    padding,
                    border,
                    true,
                    false
                )
            );

            document.add(layoutTable);

            document.close();
            pdfWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tempFileName;
    }

    private com.lowagie.text.Image getLogotipo() {
        var instituicaoEnsino = instituicaoEnsinoService.getInstituicao(SecurityUtils.getCurrentUserLogin().get());
        com.lowagie.text.Image image = null;
        try {
            image = com.lowagie.text.Image.getInstance(instituicaoEnsino.getLogotipo());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        image.scaleAbsolute(60f, 40);
        image.setAlignment(Element.ALIGN_CENTER);

        return image;
    }

    private String getInfoEmpresa() {
        //        var info1 = "REPÚBLICA DE ANGOLA";
        //        var ministerio = "MINISTÉRIO DA INDÚSTRIA E COMÉRCIO";
        var empresa = instituicaoEnsinoService.getInstituicao(SecurityUtils.getCurrentUserLogin().get()); // TODO: Pesquisar pelo id da empresa do usuario logado
        var nome = empresa.getUnidadeOrganica();
        var departamento = "Secretária-geral";
        String nif = "NIF nº ";
        if (empresa.getNif() != null) {
            nif = "NIF nº " + empresa.getNif();
        }

        return (nome + "\n" + departamento + "\n" + nif);
    }

    private String getContactoEmpresa() {
        var empresa = instituicaoEnsinoService.getInstituicao(SecurityUtils.getCurrentUserLogin().get()); // TODO: Pesquisar pelo id da empresa do usuario logado
        var endereco = empresa.getEnderecoDetalhado();
        String email = "";
        var telefone = "";
        if (empresa.getTelefone() != null) {
            telefone = empresa.getTelefone();
        }

        if (empresa.getEmail() != null) {
            email = empresa.getEmail();
        }

        if (empresa.getTelemovel() != null) {
            telefone = telefone + " | " + empresa.getTelemovel();
        }

        return (endereco + "\n" + email + "\n" + telefone);
    }

    private PdfPTable getRecibo(Long reciboID, String titulo) {
        var recibo = reciboService.findOne(reciboID).get();
        var factura = facturaService.findAll().stream().filter(ft -> ft.getNumero().equals(recibo.getNumero())).findFirst().get();
        var facturaID = factura.getId();
        var itemsFactura = itemFacturaService.getItemsFactura(facturaID);

        int NUM_LINHA_ATE_FIM_PAGINA = 30;
        int NUM_LINHA_FACTURA = itemsFactura.size();
        int NUM_LINHA_BRANCA_ADICIONAR = NUM_LINHA_ATE_FIM_PAGINA - NUM_LINHA_FACTURA;

        var matricula = matriculaService.findOne(recibo.getMatricula().getId()).get();
        var discente = matricula.getDiscente();
        var turma = matricula.getTurma();
        var planoCurricular = turma.getPlanoCurricular();
        var contas = transacaoService.getUltimaTransacaoMatricula(matricula.getId());
        String anoLectivo = turma.getDescricao().split("/")[1];
        String morada = enderecoDiscenteService.getEnderecoPadrao(matricula.getDiscente().getId());
        BigDecimal totalMulta = BigDecimal.ZERO;
        BigDecimal totalJuro = BigDecimal.ZERO;
        BigDecimal saldoAnterior = BigDecimal.ZERO;
        BigDecimal saldoActual = BigDecimal.ZERO;
        BigDecimal totalPago = BigDecimal.ZERO;
        BigDecimal totalFactura = BigDecimal.ZERO;

        /*
        for (var conta : contas) {
            saldo = saldo.add(conta.getSaldo());
        }
        */

        Font fontNormal = FontFactory.getFont("Helvetica", 8, Font.NORMAL, Color.BLACK);
        Font fontBold = FontFactory.getFont("Helvetica", 7, Font.BOLD, Color.BLACK);
        Font fontBoldLarge = FontFactory.getFont("Helvetica", 8, Font.BOLD, Color.BLACK);

        var borderNone = getBorder(0f, 0f, 0f, 0f, 0f, 0f);
        var borderNormal = getBorder(0f, 0f, 1f, 1f, 1f, 1f);
        var borderSmaller = getBorder(0f, 0f, 0.3f, 0.3f, 0.3f, 0.3f);

        float padding = 2f;
        float leading = fontNormal.getSize() * 1.1f;

        PdfPTable layoutTable = new PdfPTable(1);
        layoutTable.setWidthPercentage(50f);

        // Header
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100f);
        headerTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        headerTable.addCell(
            makeCellText(titulo, Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );

        // SubHeader
        PdfPTable subHeader = new PdfPTable(2);
        subHeader.setWidthPercentage(100f);
        subHeader.addCell(
            makeCellTImage(getLogotipo(), Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(
            makeCellText(getInfoEmpresa(), Element.ALIGN_TOP, Element.ALIGN_CENTER, fontNormal, leading, padding, borderNone, true, false)
        );
        subHeader.addCell(
            makeCellText(
                getContactoEmpresa(),
                Element.ALIGN_TOP,
                Element.ALIGN_RIGHT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        // SubHeader

        PdfPTable detalheTable = new PdfPTable(2);
        detalheTable.setWidthPercentage(100f);

        // Detalhes do Discente
        PdfPTable detalheMatricula = new PdfPTable(2);
        detalheMatricula.setWidthPercentage(100f);

        detalheMatricula.addCell(
            makeCellText("Nome", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheMatricula.addCell(
            makeCellText(
                discente.getNome(),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        detalheMatricula.addCell(
            makeCellText("Morada", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheMatricula.addCell(
            makeCellText(morada, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );

        detalheMatricula.addCell(
            makeCellText("Processo nº", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheMatricula.addCell(
            makeCellText(
                matricula.getNumeroMatricula(),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        detalheMatricula.addCell(
            makeCellText("Curso", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheMatricula.addCell(
            makeCellText(
                planoCurricular.getCurso().getNome(),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        detalheMatricula.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );
        detalheMatricula.addCell(
            makeCellText(
                planoCurricular.getClasse().getDescricao() + "Sala: " + turma.getSala() + " " + turma.getTurno().getNome(),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        detalheMatricula.addCell(
            makeCellText("Turma", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheMatricula.addCell(
            makeCellText(
                turma.getDescricao().split("/")[0],
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        // Detalhes da factura
        PdfPTable detalheFactura = new PdfPTable(2);
        detalheFactura.setWidthPercentage(100f);
        detalheFactura.addCell(
            makeCellText("Factura/Recibo", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheFactura.addCell(
            makeCellText(
                recibo.getId().toString(),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        detalheTable.addCell(
            makeCellTable(detalheMatricula, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        detalheTable.addCell(
            makeCellTable(detalheFactura, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );

        detalheFactura.addCell(
            makeCellText("Data emissão", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheFactura.addCell(
            makeCellText(
                Constants.getDateFormat(recibo.getData()),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        detalheFactura.addCell(
            makeCellText("Data vencimento", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheFactura.addCell(
            makeCellText(
                Constants.getDateFormat(recibo.getVencimento()),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        detalheFactura.addCell(
            makeCellText("Data", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheFactura.addCell(
            makeCellText(
                Constants.getDateFormat(recibo.getVencimento()),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        detalheFactura.addCell(
            makeCellText("Moeda", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheFactura.addCell(
            makeCellText(
                factura.getMoeda(),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        detalheFactura.addCell(
            makeCellText("Ano lectivo", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheFactura.addCell(
            makeCellText(anoLectivo, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );

        // Items
        float[] widths = { 0.2f, 0.6f, 0.2f, 0.3f, 0.2f };
        // Descricao, Qtde, Preco unit, Desc, IVA, Total
        PdfPTable ajustesTable = new PdfPTable(widths);
        ajustesTable.setWidthPercentage(100f);
        // Calculo Header

        ajustesTable.addCell(
            makeCellBackgroudColor(
                "Código",
                Element.ALIGN_MIDDLE,
                Element.ALIGN_CENTER,
                fontBold,
                leading,
                padding,
                borderNormal,
                true,
                false
            )
        );

        ajustesTable.addCell(
            makeCellBackgroudColor(
                "Data",
                Element.ALIGN_MIDDLE,
                Element.ALIGN_CENTER,
                fontBold,
                leading,
                padding,
                borderNormal,
                true,
                false
            )
        );

        ajustesTable.addCell(
            makeCellBackgroudColor(
                "Descrição",
                Element.ALIGN_MIDDLE,
                Element.ALIGN_CENTER,
                fontBold,
                leading,
                padding,
                borderNormal,
                true,
                false
            )
        );
        ajustesTable.addCell(
            makeCellBackgroudColor(
                "Preço unit.",
                Element.ALIGN_MIDDLE,
                Element.ALIGN_CENTER,
                fontBold,
                leading,
                padding,
                borderNormal,
                true,
                false
            )
        );
        //        ajustesTable.addCell(
        //                makeCellBackgroudColor(
        //                        "Desc.(%)",
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_CENTER,
        //                        fontBold,
        //                        leading,
        //                        padding,
        //                        borderNormal,
        //                        true,
        //                        false
        //                )
        //        );
        //        ajustesTable.addCell(
        //                makeCellBackgroudColor(
        //                        "Taxa(%)",
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_CENTER,
        //                        fontBold,
        //                        leading,
        //                        padding,
        //                        borderNormal,
        //                        true,
        //                        false
        //                )
        //        );
        ajustesTable.addCell(
            makeCellBackgroudColor(
                "Total",
                Element.ALIGN_MIDDLE,
                Element.ALIGN_CENTER,
                fontBold,
                leading,
                padding,
                borderNormal,
                true,
                false
            )
        );

        for (var item : itemsFactura) {
            var emolumento = item.getEmolumento();

            // Total do contrato incluido as multas e juros

            if (item.getEstado().equals(EstadoItemFactura.PAGO)) {
                totalPago = totalPago.add(item.getPrecoTotal());
                totalFactura = totalPago;
            }

            // LinhasDocumento
            getLinhasDocumento(
                ajustesTable,
                fontNormal,
                leading,
                padding,
                borderSmaller,
                emolumento.getNumero(), // Codigo
                recibo.getData().toString(), // data
                emolumento.getNome(), // descricao
                Constants.getMoneyFormat(item.getPrecoUnitario()), // Preco unitario
                Constants.getMoneyFormat(item.getPrecoTotal()) // Total
            );
        }

        com.lowagie.text.Rectangle noBorder = new com.lowagie.text.Rectangle(0f, 0f);
        noBorder.setBorderWidthLeft(0f);
        noBorder.setBorderWidthBottom(0f);
        noBorder.setBorderWidthRight(0f);
        noBorder.setBorderWidthTop(0f);

        // Linhas Documento em branco para o resumo estar no rodapé
        for (int i = 0; i <= NUM_LINHA_BRANCA_ADICIONAR; i++) {
            getLinhasDocumento(ajustesTable, fontNormal, leading, padding, noBorder, "", "", "", "", "");
        }

        // Resumo de imposto
        float[] withResumoImposto = { 0.4f, 0.1f, 0.2f, 0.2f };
        PdfPTable resumoImpostoTable = new PdfPTable(withResumoImposto);
        resumoImpostoTable.setWidthPercentage(50f);
        resumoImpostoTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        // Descricao
        resumoImpostoTable.addCell(
            makeCellText("Descrição", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBold, leading, padding, borderSmaller, true, false)
        );
        resumoImpostoTable.addCell(
            makeCellText("Taxa", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBold, leading, padding, borderSmaller, true, false)
        );
        resumoImpostoTable.addCell(
            makeCellText("Incidencia", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBold, leading, padding, borderSmaller, true, false)
        );
        resumoImpostoTable.addCell(
            makeCellText("Imposto", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBold, leading, padding, borderSmaller, true, false)
        );

        for (var resumo : resumoImpostoFacturaService.getResumoImpostoFactura(facturaID)) {
            resumoImpostoTable.addCell(
                makeCellText(
                    resumo.getDescricao(),
                    Element.ALIGN_TOP,
                    Element.ALIGN_LEFT,
                    fontNormal,
                    leading,
                    padding,
                    borderSmaller,
                    true,
                    false
                )
            );
            resumoImpostoTable.addCell(
                makeCellText(
                    resumo.getTaxa().toString(),
                    Element.ALIGN_TOP,
                    Element.ALIGN_LEFT,
                    fontNormal,
                    leading,
                    padding,
                    borderSmaller,
                    true,
                    false
                )
            );
            resumoImpostoTable.addCell(
                makeCellText(
                    Constants.getMoneyFormat(resumo.getIncidencia()),
                    Element.ALIGN_TOP,
                    Element.ALIGN_LEFT,
                    fontNormal,
                    leading,
                    padding,
                    borderSmaller,
                    true,
                    false
                )
            );
            resumoImpostoTable.addCell(
                makeCellText(
                    Constants.getMoneyFormat(resumo.getMontante()),
                    Element.ALIGN_TOP,
                    Element.ALIGN_LEFT,
                    fontNormal,
                    leading,
                    padding,
                    borderSmaller,
                    true,
                    false
                )
            );
        }

        // Totais
        PdfPTable totalTable = new PdfPTable(2);
        totalTable.setWidthPercentage(100f);

        // Detalhes do Discente
        PdfPTable totalSaldoTable = new PdfPTable(2);
        totalSaldoTable.setWidthPercentage(100f);

        totalSaldoTable.addCell(
            makeCellText("Multa", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );

        totalSaldoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(totalMulta),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        totalSaldoTable.addCell(
            makeCellText("Juro", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totalSaldoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(totalJuro),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );
        totalSaldoTable.addCell(
            makeCellText("Saldo anterior", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totalSaldoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(saldoAnterior),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );
        totalSaldoTable.addCell(
            makeCellText("Saldo actual", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totalSaldoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(saldoActual),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        // Total pagamento
        PdfPTable totalPagamentoTable = new PdfPTable(2);
        totalPagamentoTable.setWidthPercentage(25f);
        totalPagamentoTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        totalPagamentoTable.addCell(
            makeCellText("Total Iliquido", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totalPagamentoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(totalFactura),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        totalPagamentoTable.addCell(
            makeCellText(
                "Desconto comercial",
                Element.ALIGN_MIDDLE,
                Element.ALIGN_RIGHT,
                fontBold,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );
        totalPagamentoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(factura.getTotalDescontoComercial()),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        totalPagamentoTable.addCell(
            makeCellText(
                "Desconto financeiro",
                Element.ALIGN_MIDDLE,
                Element.ALIGN_RIGHT,
                fontBold,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );
        totalPagamentoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(factura.getTotalDescontoFinanceiro()),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        totalPagamentoTable.addCell(
            makeCellText("Total IVA", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totalPagamentoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(factura.getTotalImpostoIVA()),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        totalPagamentoTable.addCell(
            makeCellText("Total pago", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totalPagamentoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(totalPago),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        totalPagamentoTable.addCell(
            makeCellText("Total", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totalPagamentoTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(totalFactura),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        totalTable.addCell(
            makeCellTable(totalSaldoTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        totalTable.addCell(
            makeCellTable(totalPagamentoTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );

        // Layout Página
        layoutTable.addCell(makeCellTable(headerTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));
        layoutTable.addCell(makeCellTable(subHeader, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText(
                getLinhaTracos(),
                Element.ALIGN_MIDDLE,
                Element.ALIGN_CENTER,
                fontBoldLarge,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );
        layoutTable.addCell(
            makeCellTable(detalheTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );

        layoutTable.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false)
        );

        layoutTable.addCell(
            makeCellTable(ajustesTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );

        layoutTable.addCell(
            makeCellText(getLinhaTracos(), Element.ALIGN_TOP, Element.ALIGN_CENTER, fontNormal, leading, padding, borderNone, true, false)
        );

        // informacao de entrega
        layoutTable.addCell(
            makeCellText(
                "Os produtos/serviços foram colocados à disposição do adquirente na data e local do documento",
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        // Codigo encriptacao da factura
        layoutTable.addCell(
            makeCellText(
                factura.getHashShort() + "-Processado por programa valido nº.n31.1/AGT20 | Longonkelo",
                Element.ALIGN_MIDDLE,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText(
                "Resumo do imposto",
                Element.ALIGN_TOP,
                Element.ALIGN_LEFT,
                fontBoldLarge,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        // Resumo Imposto

        layoutTable.addCell(
            makeCellTable(resumoImpostoTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );

        layoutTable.addCell(
            makeCellText(getLinhaTracos(), Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );

        // Total Pagamento

        layoutTable.addCell(makeCellTable(totalTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));

        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );

        // Assinatura

        layoutTable.addCell(
            makeCellTable(
                getAssinatura(SecurityUtils.getCurrentUserLogin().get(), discente.getNome()),
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("Página 1/1", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderNone, true, false)
        );

        return layoutTable;
    }

    private String getLinhaTracos() {
        return "-------------------------------------------------------------------------------------------------------------------------------------------------------";
    }

    private void getLinhasDocumento(
        PdfPTable ajustesTable,
        Font fontNormal,
        float leading,
        float padding,
        com.lowagie.text.Rectangle borderSmaller,
        String codigoEmolumento,
        String data,
        String descricao,
        String precoUnit,
        String total
    ) {
        // Codigo Emolumento
        ajustesTable.addCell(
            makeCellText(
                codigoEmolumento,
                Element.ALIGN_MIDDLE,
                Element.ALIGN_CENTER,
                fontNormal,
                leading,
                padding,
                borderSmaller,
                true,
                false
            )
        );

        // data
        ajustesTable.addCell(
            makeCellText(data, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );

        // emolumento
        ajustesTable.addCell(
            makeCellText(descricao, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );

        // ValorUnit
        ajustesTable.addCell(
            makeCellText(precoUnit, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderSmaller, true, false)
        );

        //        // Desconto
        //        ajustesTable.addCell(
        //                makeCellText(desconto, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderSmaller, true, false)
        //        );
        //
        //        // Multa
        //        ajustesTable.addCell(
        //                makeCellText(multaJuro, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderSmaller, true, false)
        //        );

        // Total
        ajustesTable.addCell(
            makeCellText(total, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderSmaller, true, false)
        );
    }

    private PdfPTable getAssinatura(String nomeResponsavel, String nomeFuncionario) {
        var today = LocalDate.now();
        var data = "LUANDA, AOS " + today.getDayOfMonth() + " DE " + Constants.getMesPT(today) + " DE " + today.getYear();
        var line = "_________________________________";
        Font tableFont = FontFactory.getFont("Helvetica", 6, Font.BOLD, Color.BLACK);
        float padding = 2f;
        float leading = tableFont.getSize() * 1.1f;
        com.lowagie.text.Rectangle border = new com.lowagie.text.Rectangle(0f, 0f);
        border.setBorderWidthLeft(0f);
        border.setBorderWidthBottom(0f);
        border.setBorderWidthRight(0f);
        border.setBorderWidthTop(0f);

        PdfPTable assinaturaTabler = new PdfPTable(1);
        assinaturaTabler.setWidthPercentage(50f);
        assinaturaTabler.setHorizontalAlignment(Element.ALIGN_LEFT);

        assinaturaTabler.addCell(
            makeCellText(
                data + "\n\n" + "Utilizador" + "\n" + line + "\n" + nomeResponsavel,
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                tableFont,
                leading,
                padding,
                border,
                true,
                false
            )
        );

        return assinaturaTabler;
    }

    private static PdfPCell makeCellText(
        String text,
        int vAlignment,
        int hAlignment,
        Font font,
        float leading,
        float padding,
        com.lowagie.text.Rectangle borders,
        boolean ascender,
        boolean descender
    ) {
        Paragraph p = new Paragraph(text, font);
        p.setLeading(leading);

        PdfPCell cell = new PdfPCell(p);
        cell.setLeading(leading, 0);
        cell.setVerticalAlignment(vAlignment);
        cell.setHorizontalAlignment(hAlignment);
        cell.cloneNonPositionParameters(borders);
        cell.setUseAscender(ascender);
        cell.setUseDescender(descender);
        cell.setUseBorderPadding(true);
        cell.setPadding(padding);
        return cell;
    }

    private static PdfPCell makeCellTable(
        PdfPTable table,
        int vAlignment,
        int hAlignment,
        float leading,
        float padding,
        com.lowagie.text.Rectangle borders,
        boolean ascender,
        boolean descender
    ) {
        PdfPCell cell = new PdfPCell(table);
        cell.setLeading(leading, 0);
        cell.setVerticalAlignment(vAlignment);
        cell.setHorizontalAlignment(hAlignment);
        cell.cloneNonPositionParameters(borders);
        cell.setUseAscender(ascender);
        cell.setUseDescender(descender);
        cell.setUseBorderPadding(true);
        cell.setPadding(padding);
        return cell;
    }

    private static PdfPCell makeCellTImage(
        Image image,
        int vAlignment,
        int hAlignment,
        float leading,
        float padding,
        com.lowagie.text.Rectangle borders,
        boolean ascender,
        boolean descender
    ) {
        PdfPCell cell = new PdfPCell(image);
        cell.setLeading(leading, 0);
        cell.setVerticalAlignment(vAlignment);
        cell.setHorizontalAlignment(hAlignment);
        cell.cloneNonPositionParameters(borders);
        cell.setUseAscender(ascender);
        cell.setUseDescender(descender);
        cell.setUseBorderPadding(true);
        cell.setPadding(padding);
        return cell;
    }

    private com.lowagie.text.Rectangle getBorder(float urx, float ury, float left, float bottom, float right, float top) {
        com.lowagie.text.Rectangle border = new com.lowagie.text.Rectangle(urx, ury);
        border.setBorderWidthLeft(left);
        border.setBorderWidthBottom(bottom);
        border.setBorderWidthRight(right);
        border.setBorderWidthTop(top);
        return border;
    }

    private static PdfPCell makeCellBackgroudColor(
        String text,
        int vAlignment,
        int hAlignment,
        Font font,
        float leading,
        float padding,
        Rectangle borders,
        boolean ascender,
        boolean descender
    ) {
        Paragraph p = new Paragraph(text, font);
        p.setLeading(leading);

        PdfPCell cell = new PdfPCell(p);
        cell.setLeading(leading, 0);
        cell.setVerticalAlignment(vAlignment);
        cell.setHorizontalAlignment(hAlignment);
        cell.cloneNonPositionParameters(borders);
        cell.setUseAscender(ascender);
        cell.setUseDescender(descender);
        cell.setUseBorderPadding(true);
        cell.setPadding(padding);
        cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
        return cell;
    }
}
