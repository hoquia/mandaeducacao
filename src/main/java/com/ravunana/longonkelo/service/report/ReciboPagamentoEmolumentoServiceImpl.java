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
public class ReciboPagamentoEmolumentoServiceImpl {

    private final ReportService reportService;
    private final InstituicaoEnsinoServiceImpl instituicaoEnsinoService;
    private final MatriculaServiceImpl matriculaService;
    private final FacturaServiceImpl facturaService;
    private final TransacaoServiceImpl transacaoService;

    private final ItemFacturaServiceImpl itemFacturaService;

    public ReciboPagamentoEmolumentoServiceImpl(
        ReportService reportService,
        InstituicaoEnsinoServiceImpl instituicaoEnsinoService,
        MatriculaServiceImpl matriculaService,
        FacturaServiceImpl facturaService,
        TransacaoServiceImpl transacaoService,
        ItemFacturaServiceImpl itemFacturaService
    ) {
        this.reportService = reportService;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.matriculaService = matriculaService;
        this.facturaService = facturaService;
        this.transacaoService = transacaoService;
        this.itemFacturaService = itemFacturaService;
    }

    public String gerarReciboPdf(Long facturaId) {
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
            Rectangle border = new Rectangle(0f, 0f);
            border.setBorderWidthLeft(1f);
            border.setBorderWidthBottom(0.5f);
            border.setBorderWidthRight(0.5f);
            border.setBorderWidthTop(1f);

            tempFileName = reportService.createTempFile(pdfName, ".pdf");
            file = new FileOutputStream(tempFileName);

            var factura = facturaService.findOne(facturaId).get();

            var matricula = factura.getMatricula();

            final PdfWriter pdfWriter = PdfWriter.getInstance(document, file);

            HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
            HeaderFooter footer = new HeaderFooter(
                new Phrase(String.valueOf(getAssinatura(SecurityUtils.getCurrentUserLogin().get(), matricula.getDiscente().getNome()))),
                new Phrase(".")
            );
            // document.setHeader(header);

            document.open();

            // Pdf Metadatas
            document.addTitle("Extracto de Pagamento " + facturaId);
            document.addSubject(facturaId.toString());
            document.addKeywords("recibo," + "pagamento," + facturaId);
            document.addCreator("ravunana,lda");
            document.addAuthor("ravunana");
            //            document.setFooter(footer);

            PdfPTable layoutTable = new PdfPTable(2);
            layoutTable.setWidthPercentage(100f);

            layoutTable.addCell(
                makeCellTable(
                    getRecibo(facturaId, "Original"),
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
                    getRecibo(facturaId, "Cópia"),
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

    private Image getLogotipo() {
        var instituicaoEnsino = instituicaoEnsinoService.getInstituicao(SecurityUtils.getCurrentUserLogin().get());
        Image image = null;
        try {
            image = Image.getInstance(instituicaoEnsino.getLogotipo());
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
        var nif = "Nif nº " + empresa.getNif();

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

    private PdfPTable getRecibo(Long facturaID, String titulo) {
        var factura = facturaService.findOne(facturaID).get();
        var itemsFactura = itemFacturaService.getItemsFactura(facturaID);

        var matricula = matriculaService.findOne(factura.getMatricula().getId()).get();
        var discente = matricula.getDiscente();
        var turma = matricula.getTurma();
        var planoCurricular = turma.getPlanoCurricular();
        var contas = transacaoService.getUltimaTransacaoMatricula(matricula.getId());

        BigDecimal totalContrato = BigDecimal.ZERO;
        BigDecimal totalPago = BigDecimal.ZERO;
        // BigDecimal saldo = contas.getSaldo();
        BigDecimal saldo = BigDecimal.ZERO;

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

        // Detalhes do DIscente
        PdfPTable detalheMatricula = new PdfPTable(2);
        detalheMatricula.setWidthPercentage(100f);
        detalheMatricula.addCell(
            makeCellText("Discente", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
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
            makeCellText("Classe", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );

        detalheMatricula.addCell(
            makeCellText(
                planoCurricular.getClasse().getDescricao(),
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
            makeCellText("Sala", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheMatricula.addCell(
            makeCellText(
                turma.getSala().toString(),
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
            makeCellText("Turno", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheMatricula.addCell(
            makeCellText(
                turma.getTurno().getNome(),
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

        // Detalhes do Salario
        PdfPTable detalheSalario = new PdfPTable(2);
        detalheSalario.setWidthPercentage(100f);
        detalheSalario.addCell(
            makeCellText("Turma", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheSalario.addCell(
            makeCellText(
                turma.getDescricao(),
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

        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );

        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );

        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );

        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );

        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheSalario.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );

        detalheTable.addCell(
            makeCellTable(detalheMatricula, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        detalheTable.addCell(
            makeCellTable(detalheSalario, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );

        // Items
        PdfPTable ajustesTable = new PdfPTable(6);
        ajustesTable.setWidthPercentage(100f);
        // Calculo Header

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
                "Qtde.",
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
                "Preço Unit.",
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
                "Desc.",
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
                "Multa+Juro",
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

        int NUM_LINHA_ATE_FIM_PAGINA = 46;
        int NUM_LINHA_FACTURA = itemsFactura.size();
        int NUM_LINHA_BRANCA_ADICIONAR = NUM_LINHA_ATE_FIM_PAGINA - NUM_LINHA_FACTURA;

        for (var pagamento : itemsFactura) {
            var emolumento = pagamento.getEmolumento();

            // Total do contrato incluido as multas e juros

            if (pagamento.getEstado().equals(EstadoItemFactura.PAGO)) {
                totalPago = totalPago.add(pagamento.getPrecoTotal());
                totalContrato = totalPago;
            }

            // LinhasDocumento
            getLinhasDocumento(
                ajustesTable,
                fontNormal,
                leading,
                padding,
                borderSmaller,
                emolumento.getNome(),
                pagamento.getQuantidade().toString(),
                Constants.getMoneyFormat(pagamento.getPrecoUnitario()),
                Constants.getMoneyFormat(pagamento.getDesconto()),
                Constants.getMoneyFormat(pagamento.getJuro()),
                Constants.getMoneyFormat(pagamento.getPrecoTotal())
            );
        }

        Rectangle noBorder = new Rectangle(0f, 0f);
        noBorder.setBorderWidthLeft(0f);
        noBorder.setBorderWidthBottom(0f);
        noBorder.setBorderWidthRight(0f);
        noBorder.setBorderWidthTop(0f);

        for (int i = 0; i <= NUM_LINHA_BRANCA_ADICIONAR; i++) {
            // LinhasDocumento em branco
            getLinhasDocumento(ajustesTable, fontNormal, leading, padding, noBorder, "", "", "", "", "", "");
        }

        // Resumo do Pagamento
        PdfPTable resumoPagamentoTable = new PdfPTable(2);
        resumoPagamentoTable.setWidthPercentage(100f);

        // Forma de Pagamento
        PdfPTable formaPagamentoTable = new PdfPTable(2);
        formaPagamentoTable.setWidthPercentage(100f);
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderNone, true, false)
        );
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderNone, true, false)
        );
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        formaPagamentoTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );

        // Totais
        PdfPTable totaisTable = new PdfPTable(2);
        totaisTable.setWidthPercentage(100f);
        totaisTable.addCell(
            makeCellText("Total Contrato", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totaisTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(totalContrato),
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
        totaisTable.addCell(
            makeCellText("Total Pago", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totaisTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(totalPago),
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
        totaisTable.addCell(
            makeCellText("Falta", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totaisTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(totalContrato.subtract(totalPago)),
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
        totaisTable.addCell(
            makeCellText("Saldo", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        totaisTable.addCell(
            makeCellText(
                Constants.getMoneyFormat(saldo),
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

        resumoPagamentoTable.addCell(
            makeCellTable(formaPagamentoTable, Element.ALIGN_TOP, Element.ALIGN_LEFT, leading, padding, borderNone, true, false)
        );
        resumoPagamentoTable.addCell(
            makeCellTable(totaisTable, Element.ALIGN_TOP, Element.ALIGN_RIGHT, leading, padding, borderNone, true, false)
        );

        // Layout Página
        layoutTable.addCell(makeCellTable(headerTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));
        layoutTable.addCell(makeCellTable(subHeader, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));
        layoutTable.addCell(
            makeCellText(
                "Extrato de Pagamento",
                Element.ALIGN_TOP,
                Element.ALIGN_RIGHT,
                fontBoldLarge,
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
                "--------------------------------------",
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
        //        layoutTable.addCell( makeCellText("--------------------------------------", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false ) );
        layoutTable.addCell(
            makeCellTable(ajustesTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellTable(resumoPagamentoTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
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

        return layoutTable;
    }

    private void getLinhasDocumento(
        PdfPTable ajustesTable,
        Font fontNormal,
        float leading,
        float padding,
        Rectangle borderSmaller,
        String descricao,
        String quantidade,
        String precoUnit,
        String desconto,
        String multaJuro,
        String total
    ) {
        // Emolumento
        ajustesTable.addCell(
            makeCellText(descricao, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );

        // Quantidade
        ajustesTable.addCell(
            makeCellText(quantidade, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );

        // ValorUnit
        ajustesTable.addCell(
            makeCellText(precoUnit, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderSmaller, true, false)
        );

        // Desconto
        ajustesTable.addCell(
            makeCellText(desconto, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderSmaller, true, false)
        );

        // Multa
        ajustesTable.addCell(
            makeCellText(multaJuro, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderSmaller, true, false)
        );

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
        Rectangle border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(0f);
        border.setBorderWidthBottom(0f);
        border.setBorderWidthRight(0f);
        border.setBorderWidthTop(0f);

        PdfPTable assinaturaTabler = new PdfPTable(2);
        assinaturaTabler.setWidthPercentage(100f);

        assinaturaTabler.addCell(
            makeCellText(
                data + "\n\n" + "PROCESSOU:" + "\n" + line + "\n" + nomeResponsavel,
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

        assinaturaTabler.addCell(
            makeCellText(
                "\n\nRECEBEU:" + "\n" + line + "\n" + nomeFuncionario,
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
        return cell;
    }

    private static PdfPCell makeCellTable(
        PdfPTable table,
        int vAlignment,
        int hAlignment,
        float leading,
        float padding,
        Rectangle borders,
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
        Rectangle borders,
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

    private Rectangle getBorder(float urx, float ury, float left, float bottom, float right, float top) {
        Rectangle border = new Rectangle(urx, ury);
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
