package com.ravunana.longonkelo.service.report;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ravunana.longonkelo.config.Constants;
import com.ravunana.longonkelo.config.LongonkeloException;
import com.ravunana.longonkelo.security.SecurityUtils;
import com.ravunana.longonkelo.service.impl.HorarioServiceImpl;
import com.ravunana.longonkelo.service.impl.InstituicaoEnsinoServiceImpl;
import com.ravunana.longonkelo.service.impl.MatriculaServiceImpl;
import com.ravunana.longonkelo.service.impl.NotasPeriodicaDisciplinaServiceImpl;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class MiniPautaServiceReport {

    private final ReportService reportService;
    private final InstituicaoEnsinoServiceImpl instituicaoEnsinoService;
    private final MatriculaServiceImpl matriculaService;

    private final NotasPeriodicaDisciplinaServiceImpl notasPeriodicaDisciplinaService;
    private final HorarioServiceImpl horarioService;

    public MiniPautaServiceReport(
        ReportService reportService,
        InstituicaoEnsinoServiceImpl instituicaoEnsinoService,
        MatriculaServiceImpl matriculaService,
        NotasPeriodicaDisciplinaServiceImpl notasPeriodicaDisciplinaService,
        HorarioServiceImpl horarioService
    ) {
        this.reportService = reportService;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.matriculaService = matriculaService;
        this.notasPeriodicaDisciplinaService = notasPeriodicaDisciplinaService;
        this.horarioService = horarioService;
    }

    public String gerarPdf(Long horarioID, Integer periodoID) {
        Document document;
        String pdfName;
        FileOutputStream file;
        pdfName = "mini-pauta";
        document = new Document(PageSize.A4, 4f, 4f, 4f, 4f);
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

            // tens o id perido = 5 e vais para a tabela notasperidodica com esse id 5 não vai aparecer
            // nada ao menenos que tenha esse id nessa tabela, mas será um dado errado
            // o filtro seria pelo matriclaID e peridoID cria um metodo na classe notasperiodicasdisciplinaimpl
            // fui. Ta vou aplicar isso e ver o que faco ate o periodo em que poderei te dar um feedback, ate

            //            if (!notaPeriodica.isPresent()) {
            //                throw new RuntimeException("Not Founded!");
            //            }
            //
            //            var notaResult = notaPeriodica.get();
            //            var matricula = notaResult.getMatricula();

            final PdfWriter pdfWriter = PdfWriter.getInstance(document, file);

            HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
            //            HeaderFooter footer = new HeaderFooter(
            //                new Phrase(String.valueOf(getAssinatura(notaResult.getUtilizador().getLogin(), matricula.getDiscente().getNome()))),
            //                new Phrase(".")
            //            );
            // document.setHeader(header);

            document.open();

            // Pdf Metadatas
            document.addTitle("Mini Pauta " + periodoID);
            document.addSubject(periodoID.toString());
            document.addKeywords("mini," + "pauta," + periodoID);
            document.addCreator("ravunana,lda");
            document.addAuthor("ravunana");
            //            document.setFooter(footer);

            PdfPTable layoutTable = new PdfPTable(1);
            layoutTable.setWidthPercentage(100f);

            layoutTable.addCell(
                makeCellTable(
                    getRecibo(horarioID, periodoID),
                    Element.ALIGN_CENTER,
                    Element.ALIGN_LEFT,
                    leading,
                    padding,
                    border,
                    true,
                    false
                )
            );
            //            layoutTable.addCell(
            //                    makeCellTable(
            //                            getRecibo(reciboID, "Cópia"),
            //                            Element.ALIGN_CENTER,
            //                            Element.ALIGN_RIGHT,
            //                            leading,
            //                            padding,
            //                            border,
            //                            true,
            //                            false
            //                    )
            //            );

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

    private PdfPTable getRecibo(Long horarioID, Integer periodo) {
        //        var aplicacaoRecibos = aplicacaoReciboService.getAplicacaoReciboWithRecibo(reciboID);

        //        var notasPeriodicasAluno = notasPeriodicaDisciplinaService.getNotaPeriodicaWithMatriculaPeriodo(matriculas, periodo);
        //        var notaPeriodica = notasPeriodicasAluno.stream().findFirst().get();

        var horarioVar = horarioService.findOne(horarioID).get();

        var turmaID = horarioVar.getTurma().getId();
        var disciplinaID = horarioVar.getDisciplinaCurricular().getId();
        var matriculas = matriculaService.getMatriculas(turmaID);

        var notasPeriodicasWithTurmaDisciplinaPeriodo = notasPeriodicaDisciplinaService.getNotasPeriodicasWithTurmaDisciplinaPeriodo(
            turmaID,
            disciplinaID,
            periodo
        );

        // Pega os itens da factura com estado PENDENTE

        int NUM_LINHA_ATE_FIM_PAGINA = 30;
        int NUM_LINHA_FACTURA = matriculas.size();
        int NUM_LINHA_BRANCA_ADICIONAR = NUM_LINHA_ATE_FIM_PAGINA - NUM_LINHA_FACTURA;

        //        var matricula = matriculaService.findOne(notaPeriodica.getMatricula().getId()).get();
        //        var discente = matricula.getDiscente();
        //        var numeroChamada = matricula.getNumeroChamada();
        //        var numeroMatricula = matricula.getNumeroMatricula();
        var turma = horarioVar.getTurma();
        var curso = turma.getPlanoCurricular().getCurso().getNome();
        var sala = turma.getSala();
        var classe = turma.getPlanoCurricular().getClasse().getDescricao();
        var turno = turma.getTurno();
        var disciplina = horarioVar.getDisciplinaCurricular().getDisciplina().getNome();
        //        String anoLectivo = turma.getDescricao().split("/")[1];
        //        var contas = transacaoService.getUltimaTransacaoMatricula(matricula.getId());

        //        String morada = enderecoDiscenteService.getEnderecoPadrao(matricula.getDiscente().getId());
        //        BigDecimal totalMulta = BigDecimal.ZERO;
        //        BigDecimal totalJuro = BigDecimal.ZERO;
        //        BigDecimal saldoAnterior = BigDecimal.ZERO;
        //        BigDecimal saldoActual = BigDecimal.ZERO;
        //        BigDecimal totalPago = BigDecimal.ZERO;
        //        BigDecimal totalFactura = BigDecimal.ZERO;

        /*
        for (var conta : contas) {
            saldo = saldo.add(conta.getSaldo());
        }
        */

        Font fontNormal = FontFactory.getFont("Helvetica", 8, Font.NORMAL, Color.BLACK);
        Font fontBold = FontFactory.getFont("Helvetica", 7, Font.BOLD, Color.BLACK);
        Font fontBoldLarge = FontFactory.getFont("Helvetica", 12, Font.BOLD, Color.BLACK);

        var borderNone = getBorder(0f, 0f, 0f, 0f, 0f, 0f);
        var borderNormal = getBorder(0f, 0f, 1f, 1f, 1f, 1f);
        var borderSmaller = getBorder(0f, 0f, 0.3f, 0.3f, 0.3f, 0.3f);

        float padding = 1.3f;
        float leading = fontNormal.getSize() * 1.1f;

        PdfPTable layoutTable = new PdfPTable(1);
        layoutTable.setWidthPercentage(100f);

        // Header
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100f);
        headerTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        headerTable.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false));

        // SubHeader
        PdfPTable subHeader = new PdfPTable(1);
        subHeader.setWidthPercentage(50f);

        //        subHeader.addCell(
        //            makeCellText(
        //                getVistoGestor(notaPeriodica.getMatricula().getId(), notaPeriodica.getPeriodoLancamento()),
        //                Element.ALIGN_TOP,
        //                Element.ALIGN_LEFT,
        //                fontBold,
        //                leading,
        //                padding,
        //                borderNone,
        //                true,
        //                false
        //            )
        //        );

        subHeader.addCell(
            makeCellTImage(getLogotipo(), Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));

        subHeader.addCell(
            makeCellText(getInfoEmpresa(), Element.ALIGN_TOP, Element.ALIGN_CENTER, fontNormal, leading, padding, borderNone, true, false)
        );

        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(
            makeCellText(
                "MINI PAUTA DO " + periodo + "º TRIMESTRE",
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                fontBoldLarge,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );

        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));

        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        //        subHeader.addCell(
        //                makeCellText(
        //                        getContactoEmpresa(),
        //                        Element.ALIGN_TOP,
        //                        Element.ALIGN_RIGHT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );

        // SubHeader

        PdfPTable detalheTable = new PdfPTable(2);

        detalheTable.setWidthPercentage(100f);
        // Detalhes do Discente
        //        float width[] = { 2};
        PdfPTable detalheMatricula = new PdfPTable(2);

        detalheMatricula.setWidthPercentage(100f);

        detalheMatricula.addCell(
            makeCellText("Disciplina:", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );

        detalheMatricula.addCell(
            makeCellText(disciplina, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );

        detalheMatricula.addCell(
            makeCellText("Curso:", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );

        detalheMatricula.addCell(
            makeCellText(curso, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );
        detalheMatricula.addCell(
            makeCellText("Turma:", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );

        detalheMatricula.addCell(
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

        //                 Detalhes da factura
        //                float width2[] = { 0.3f, 0.6f, 0.3f, 0.5f, 0.3f, 0.8f};
        PdfPTable detalheFactura = new PdfPTable(2);

        detalheFactura.setWidthPercentage(100f);
        //        detalheFactura.addCell(
        //                makeCellText("Recibo", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        detalheFactura.addCell(
        //                makeCellText(
        //                        recibo.getNumero(),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //

        detalheFactura.addCell(
            makeCellText("Sala:", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );

        detalheFactura.addCell(
            makeCellText(sala.toString(), Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );
        detalheFactura.addCell(
            makeCellText("Turno:", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );

        detalheFactura.addCell(
            makeCellText(turno.getNome(), Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );
        detalheFactura.addCell(
            makeCellText("Classe:", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        );
        detalheFactura.addCell(
            makeCellText(classe, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        );

        detalheTable.addCell(
            makeCellTable(detalheMatricula, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        detalheTable.addCell(
            makeCellTable(detalheFactura, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        //
        //        detalheFactura.addCell(
        //                makeCellText("Data emissão", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        detalheFactura.addCell(
        //                makeCellText(
        //                        Constants.getDateFormat(recibo.getData()),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        detalheFactura.addCell(
        //                makeCellText("Data vencimento", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        detalheFactura.addCell(
        //                makeCellText(
        //                        Constants.getDateFormat(recibo.getVencimento()),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        detalheFactura.addCell(
        //                makeCellText("Data", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        detalheFactura.addCell(
        //                makeCellText(
        //                        Constants.getDateFormat(recibo.getVencimento()),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        detalheFactura.addCell(
        //                makeCellText("Moeda", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        detalheFactura.addCell(
        //            makeCellText(
        //                ,
        //                Element.ALIGN_MIDDLE,
        //                Element.ALIGN_LEFT,
        //                fontNormal,
        //                leading,
        //                padding,
        //                borderNone,
        //                true,
        //                false
        //            )
        //        );

        //        detalheFactura.addCell(
        //                makeCellText("Ano lectivo", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        detalheFactura.addCell(
        //                makeCellText(anoLectivo, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, fontNormal, leading, padding, borderNone, true, false)
        //        );

        // Items
        float[] widths = { 0.1f, 0.4f, 0.6f, 0.2f, 0.1f, 0.1f, 0.1f, 0.1f, 0.3f };

        // Descricao, Qtde, Preco unit, Desc, IVA, Total
        PdfPTable ajustesTable = new PdfPTable(widths);
        ajustesTable.setWidthPercentage(100f);
        // Calculo Header

        ajustesTable.addCell(
            makeCellBackgroudColor("Nº", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBold, leading, padding, borderNormal, true, false)
        );
        ajustesTable.addCell(
            makeCellBackgroudColor(
                "Nº Matricula",
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
                "Nome Completo",
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
                "Género",
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
            makeCellBackgroudColor("MAC", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBold, leading, padding, borderNormal, true, false)
        );
        ajustesTable.addCell(
            makeCellBackgroudColor("NPP", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBold, leading, padding, borderNormal, true, false)
        );
        ajustesTable.addCell(
            makeCellBackgroudColor("NPT", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBold, leading, padding, borderNormal, true, false)
        );
        ajustesTable.addCell(
            makeCellBackgroudColor(
                "Média",
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
                "Estado",
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
        //            makeCellBackgroudColor("FI", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBold, leading, padding, borderNormal, true, false)
        //        );
        //        ajustesTable.addCell(
        //            makeCellBackgroudColor(
        //                "Estado",
        //                Element.ALIGN_MIDDLE,
        //                Element.ALIGN_CENTER,
        //                fontBold,
        //                leading,
        //                padding,
        //                borderNormal,
        //                true,
        //                false
        //            )
        //        );
        //                ajustesTable.addCell(
        //                        makeCellBackgroudColor(
        //                                "Total",
        //                                Element.ALIGN_MIDDLE,
        //                                Element.ALIGN_CENTER,
        //                                fontBold,
        //                                leading,
        //                                padding,
        //                                borderNormal,
        //                                true,
        //                                false
        //                        )
        //                );
        //
        for (var notasAluno : notasPeriodicasWithTurmaDisciplinaPeriodo) {
            var matricula = matriculaService.findOne(notasAluno.getMatricula().getId()).get();

            var nota1 = notasAluno.getNota1();
            var nota2 = notasAluno.getNota2();
            var nota3 = notasAluno.getNota3();
            var media = notasAluno.getMedia();
            var nome = matricula.getDiscente().getNome();
            var numeroMatricula = matricula.getNumeroMatricula();
            var numeroChamada = matricula.getNumeroChamada();
            var genero = matricula.getDiscente().getSexo();
            var estado = notasAluno.getEstado().getDescricao();

            // Total do contrato incluido as multas e juros
            //
            //                    if (item.getEstado().equals(EstadoItemFactura.PAGO)) {
            //                        totalPago = totalPago.add(item.getPrecoTotal());
            //                        totalFactura = totalPago;
            //                    }

            // LinhasDocumento
            getLinhasDocumento(
                ajustesTable,
                fontNormal,
                leading,
                padding,
                borderSmaller,
                nome,
                numeroMatricula,
                numeroChamada.toString(),
                genero.toString(),
                nota1.toString(),
                nota2.toString(),
                nota3.toString(),
                media.toString(),
                estado
            );
        }
        //
        com.lowagie.text.Rectangle noBorder = new com.lowagie.text.Rectangle(0f, 0f);
        noBorder.setBorderWidthLeft(0f);
        noBorder.setBorderWidthBottom(0f);
        noBorder.setBorderWidthRight(0f);
        noBorder.setBorderWidthTop(0f);
        //
        // Linhas Documento em branco para o resumo estar no rodapé
        for (int i = 0; i <= NUM_LINHA_BRANCA_ADICIONAR; i++) {
            getLinhasDocumento(ajustesTable, fontNormal, leading, padding, noBorder, "", "", "", "", "", "", "", "", "");
        }
        // Resumo de imposto
        //        float[] withResumoImposto = { 0.4f, 0.1f, 0.2f, 0.2f };
        //        PdfPTable resumoImpostoTable = new PdfPTable(withResumoImposto);
        //        resumoImpostoTable.setWidthPercentage(50f);
        //        resumoImpostoTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        //        // Descricao
        //        resumoImpostoTable.addCell(
        //            makeCellText("Descrição", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBold, leading, padding, borderSmaller, true, false)
        //        );
        //        resumoImpostoTable.addCell(
        //            makeCellText("Taxa", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBold, leading, padding, borderSmaller, true, false)
        //        );
        //        resumoImpostoTable.addCell(
        //            makeCellText("Incidencia", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBold, leading, padding, borderSmaller, true, false)
        //        );
        //        resumoImpostoTable.addCell(
        //            makeCellText("Imposto", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBold, leading, padding, borderSmaller, true, false)
        //        );
        //
        //        for (var resumo : resumoImpostoFacturaService.getResumoImpostoFactura(facturaID)) {
        //            resumoImpostoTable.addCell(
        //                makeCellText(
        //                    resumo.getDescricao(),
        //                    Element.ALIGN_TOP,
        //                    Element.ALIGN_LEFT,
        //                    fontNormal,
        //                    leading,
        //                    padding,
        //                    borderSmaller,
        //                    true,
        //                    false
        //                )
        //            );
        //            resumoImpostoTable.addCell(
        //                makeCellText(
        //                    resumo.getTaxa().toString(),
        //                    Element.ALIGN_TOP,
        //                    Element.ALIGN_LEFT,
        //                    fontNormal,
        //                    leading,
        //                    padding,
        //                    borderSmaller,
        //                    true,
        //                    false
        //                )
        //            );
        //            resumoImpostoTable.addCell(
        //                makeCellText(
        //                    Constants.getMoneyFormat(resumo.getIncidencia()),
        //                    Element.ALIGN_TOP,
        //                    Element.ALIGN_LEFT,
        //                    fontNormal,
        //                    leading,
        //                    padding,
        //                    borderSmaller,
        //                    true,
        //                    false
        //                )
        //            );
        //            resumoImpostoTable.addCell(
        //                makeCellText(
        //                    Constants.getMoneyFormat(resumo.getMontante()),
        //                    Element.ALIGN_TOP,
        //                    Element.ALIGN_LEFT,
        //                    fontNormal,
        //                    leading,
        //                    padding,
        //                    borderSmaller,
        //                    true,
        //                    false
        //                )
        //            );
        //        }

        // Totais
        //        PdfPTable totalTable = new PdfPTable(2);
        //        totalTable.setWidthPercentage(100f);

        // Detalhes do Discente
        //        PdfPTable totalSaldoTable = new PdfPTable(2);
        //        totalSaldoTable.setWidthPercentage(100f);
        //
        //        totalSaldoTable.addCell(
        //                makeCellText("Multa", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //
        //        totalSaldoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(totalMulta),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        totalSaldoTable.addCell(
        //                makeCellText("Juro", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        totalSaldoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(totalJuro),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //        totalSaldoTable.addCell(
        //                makeCellText("Saldo anterior", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        totalSaldoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(saldoAnterior),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //        totalSaldoTable.addCell(
        //                makeCellText("Saldo actual", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        totalSaldoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(saldoActual),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        // Total pagamento
        //        PdfPTable totalPagamentoTable = new PdfPTable(2);
        //        totalPagamentoTable.setWidthPercentage(25f);
        //        totalPagamentoTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        //
        //        totalPagamentoTable.addCell(
        //                makeCellText("Total Iliquido", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        totalPagamentoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(totalFactura),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        totalPagamentoTable.addCell(
        //                makeCellText(
        //                        "Desconto comercial",
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_RIGHT,
        //                        fontBold,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //        totalPagamentoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(recibo.getTotalDescontoComercial()),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        totalPagamentoTable.addCell(
        //                makeCellText(
        //                        "Desconto financeiro",
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_RIGHT,
        //                        fontBold,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //        totalPagamentoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(recibo.getTotalDescontoFinanceiro()),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        totalPagamentoTable.addCell(
        //                makeCellText("Total IVA", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        totalPagamentoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(recibo.getTotalIVA()),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        totalPagamentoTable.addCell(
        //                makeCellText("Total pago", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        totalPagamentoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(totalPago),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        totalPagamentoTable.addCell(
        //                makeCellText("Total", Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false)
        //        );
        //        totalPagamentoTable.addCell(
        //                makeCellText(
        //                        Constants.getMoneyFormat(totalFactura),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );

        //        totalTable.addCell(
        //                makeCellTable(totalSaldoTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        //        );
        //        totalTable.addCell(
        //                makeCellTable(totalPagamentoTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        //        );

        // Layout Página
        layoutTable.addCell(makeCellTable(headerTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));

        layoutTable.addCell(makeCellTable(subHeader, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        //        layoutTable.addCell(
        //                makeCellText(
        //                        getLinhaTracos(),
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_CENTER,
        //                        fontBoldLarge,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        layoutTable.addCell(
            makeCellTable(detalheTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false)
        );

        layoutTable.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        //
        layoutTable.addCell(
            makeCellTable(ajustesTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        );
        //
        //        layoutTable.addCell(
        //                makeCellText(getLinhaTracos(), Element.ALIGN_TOP, Element.ALIGN_CENTER, fontNormal, leading, padding, borderNone, true, false)
        //        );

        // informacao de entrega
        //        layoutTable.addCell(
        //                makeCellText(
        //                        "Os produtos/serviços foram colocados à disposição do adquirente na data e local do documento",
        //                        Element.ALIGN_MIDDLE,
        //                        Element.ALIGN_LEFT,
        //                        fontNormal,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );

        // Codigo encriptacao da factura
        // layoutTable.addCell(
        //     makeCellText(
        //         "-Processado por programa valido nº.n31.1/AGT20 | Longonkelo",
        //         Element.ALIGN_MIDDLE,
        //         Element.ALIGN_LEFT,
        //         fontNormal,
        //         leading,
        //         padding,
        //         borderNone,
        //         true,
        //         false
        //     )
        // );

        //        layoutTable.addCell(
        //                makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        //        );
        //        layoutTable.addCell(
        //                makeCellText(
        //                        "Resumo do imposto",
        //                        Element.ALIGN_TOP,
        //                        Element.ALIGN_LEFT,
        //                        fontBoldLarge,
        //                        leading,
        //                        padding,
        //                        borderNone,
        //                        true,
        //                        false
        //                )
        //        );
        //
        //        // Resumo Imposto
        //
        //        //        layoutTable.addCell(
        //        //            makeCellTable(resumoImpostoTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
        //        //        );
        //
        //        layoutTable.addCell(
        //                makeCellText(getLinhaTracos(), Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        //        );
        //        layoutTable.addCell(
        //                makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        //        );
        //
        //        // Total Pagamento
        //
        //        layoutTable.addCell(makeCellTable(totalTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));
        //
        //        layoutTable.addCell(
        //                makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        //        );
        //        layoutTable.addCell(
        //                makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        //        );
        //
        //        // Assinatura
        //

        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        //        layoutTable.addCell(
        //            makeCellTable(
        //                getAssinatura(SecurityUtils.getCurrentUserLogin().get(), discente.getNome()),
        //                Element.ALIGN_TOP,
        //                Element.ALIGN_CENTER,
        //                leading,
        //                padding,
        //                borderNone,
        //                true,
        //                false
        //            )
        //        );

        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        layoutTable.addCell(
            makeCellText(
                "MAC: Media Avaliação Continua" +
                "\n" +
                "NPP: Nota Prova Professor" +
                "\n" +
                "NPT: Nota Prova Trimestre" +
                "\n" +
                "FJ: Faltas Justificadas" +
                "\n" +
                "FI: Faltas Injustificadas" +
                "\n",
                Element.ALIGN_TOP,
                Element.ALIGN_LEFT,
                fontNormal,
                leading,
                padding,
                borderNone,
                true,
                false
            )
        );
        return layoutTable;
    }

    //    private String getLinhaTracos() {
    //        return "-------------------------------------------------------------------------------------------------------------------------------------------------------";
    //    }

    private void getLinhasDocumento(
        PdfPTable ajustesTable,
        Font fontNormal,
        float leading,
        float padding,
        com.lowagie.text.Rectangle borderSmaller,
        String nome,
        String numeroMatricula,
        String numeroChamada,
        String genero,
        String nota1,
        String nota2,
        String nota3,
        String media,
        String estado
    ) {
        // Codigo Emolumento
        ajustesTable.addCell(
            makeCellText(
                numeroChamada,
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
            makeCellText(
                numeroMatricula,
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

        // emolumento
        ajustesTable.addCell(
            makeCellText(nome, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );

        //         ValorUnit
        ajustesTable.addCell(
            makeCellText(genero, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );

        // Desconto
        ajustesTable.addCell(
            makeCellText(nota1, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );

        // Multa
        ajustesTable.addCell(
            makeCellText(nota2, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );
        ajustesTable.addCell(
            makeCellText(nota3, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );
        ajustesTable.addCell(
            makeCellText(media, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );
        ajustesTable.addCell(
            makeCellText(estado, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );
        //        ajustesTable.addCell(
        //            makeCellText(
        //                faltasInjustificadas,
        //                Element.ALIGN_MIDDLE,
        //                Element.ALIGN_CENTER,
        //                fontNormal,
        //                leading,
        //                padding,
        //                borderSmaller,
        //                true,
        //                false
        //            )
        //        );
        //        ajustesTable.addCell(
        //            makeCellText(estado, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        //        );
        //
        //         Total
        //                ajustesTable.addCell(
        //                        makeCellText(total, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, fontNormal, leading, padding, borderSmaller, true, false)
        //                );
    }

    private PdfPTable getAssinatura(String nomeResponsavel, String nomeFuncionario) {
        var today = LocalDate.now();
        var data =
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t LUANDA, AOS " +
            today.getDayOfMonth() +
            " DE " +
            Constants.getMesPT(today) +
            " DE " +
            today.getYear();
        var line = "_________________________________";
        Font tableFont = FontFactory.getFont("Helvetica", 6, Font.BOLD, Color.BLACK);
        float padding = 2f;
        float leading = tableFont.getSize() * 1.1f;
        com.lowagie.text.Rectangle border = new com.lowagie.text.Rectangle(0f, 0f);
        border.setBorderWidthLeft(0f);
        border.setBorderWidthBottom(0f);
        border.setBorderWidthRight(0f);
        border.setBorderWidthTop(0f);

        PdfPTable assinaturaTabler = new PdfPTable(2);
        assinaturaTabler.setWidthPercentage(50f);
        assinaturaTabler.setHorizontalAlignment(Element.ALIGN_LEFT);

        assinaturaTabler.addCell(
            makeCellText(
                data + "\n\n\n\n\n\n\n" + "Coordenador" + "\n" + line + "\n" + nomeResponsavel,
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
                "\n\n\n\n\n\n\n" + "Encarregado" + "\n" + line + "\n" + nomeFuncionario,
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

    private String getVistoGestor(Long matriculaID, Integer periodoID) {
        var notaPeriodica = notasPeriodicaDisciplinaService
            .getNotaPeriodicaWithMatriculaPeriodo(matriculaID, periodoID)
            .stream()
            .findFirst()
            .get();

        var responsavel = notaPeriodica.getUtilizador().getLogin();

        //        String descricaoVisto = lookupItemService.getVisto(empresa).toUpperCase();
        return "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tVisto" + ":\n__________________________\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + responsavel;
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
