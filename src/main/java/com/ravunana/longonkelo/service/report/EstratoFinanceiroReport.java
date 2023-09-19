package com.ravunana.longonkelo.service.report;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ravunana.longonkelo.config.Constants;
import com.ravunana.longonkelo.security.SecurityUtils;
import com.ravunana.longonkelo.service.UserService;
import com.ravunana.longonkelo.service.dto.ItemFacturaDTO;
import com.ravunana.longonkelo.service.dto.MatriculaDTO;
import com.ravunana.longonkelo.service.impl.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EstratoFinanceiroReport {

    private final ReportServiceImpl reportService;
    private final InstituicaoEnsinoServiceImpl instituicaoEnsinoService;
    private final MatriculaServiceImpl matriculaService;
    private final TurmaServiceImpl turmaService;
    private final UserService userService;
    private final ItemFacturaServiceImpl itemFacturaService;
    private final TransacaoServiceImpl transacaoService;
    private final float FONT_ZIZE_NORMAL = 7;
    private final float FONT_SIZE_TITLE = 7;

    public EstratoFinanceiroReport(
        ReportServiceImpl reportService,
        InstituicaoEnsinoServiceImpl instituicaoEnsinoService,
        MatriculaServiceImpl matriculaService,
        TurmaServiceImpl turmaService,
        FacturaServiceImpl facturaService,
        UserService userService,
        ItemFacturaServiceImpl itemFacturaService,
        TransacaoServiceImpl transacaoService
    ) {
        this.reportService = reportService;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.matriculaService = matriculaService;
        this.turmaService = turmaService;
        this.userService = userService;
        this.itemFacturaService = itemFacturaService;
        this.transacaoService = transacaoService;
    }

    public String gerarPdf(Long turmaID) {
        Document pdfDocument;
        String pdfName;
        FileOutputStream file;
        pdfName = "lista-pago-nao-pago-turma " + turmaID;
        pdfDocument = new Document(PageSize.A4, 3, 3, 20, 3);
        String tempFileName;

        try {
            tempFileName = reportService.createTempFile(pdfName, ".pdf");
            file = new FileOutputStream(tempFileName);

            final PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument, file);

            pdfDocument.open();

            var turmaDTO = turmaService.findOne(turmaID).get();
            var planoCurricular = turmaDTO.getPlanoCurricular();

            // Pdf Metadatas
            pdfDocument.addTitle(getPdfTitulo(turmaDTO.getDescricao()));
            pdfDocument.addSubject(getPdfSubTitulo());
            pdfDocument.addKeywords(getPdfPalavrasChaves());
            pdfDocument.addCreator(getPdfCriador());
            pdfDocument.addAuthor(getPdfAutor());

            pdfDocument.add(getLogotipo());

            String curso = planoCurricular.getCurso().getNome();
            String classe = planoCurricular.getClasse().getDescricao();
            String sala = turmaDTO.getSala().toString();
            String turno = turmaDTO.getTurno().getCodigo();

            var header = getHeader();
            pdfDocument.add(header);
            pdfDocument.add(getTituloMapa(curso, classe, sala, turno, turmaDTO.getDescricao()));
            pdfDocument.add(addNewLine());
            var detalhe = getDetalhe(turmaID);
            pdfDocument.add(detalhe);
            pdfDocument.add(addNewLine());
            pdfDocument.add(addNewLine());
            //pdfDocument.add(getAssinatura(empresa));
            //            pdfWriter.setPageEvent(new PdfPageEventHelper() {
            //                @Override
            //                public void onEndPage(PdfWriter writer, Document document) {
            //                    PdfContentByte cb = writer.getDirectContent();
            //                    cb.rectangle(header);
            //                    cb.rectangle(footer);
            //                }
            //            });

            pdfDocument.close();
            pdfWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tempFileName;
    }

    private String getPdfTitulo(String turma) {
        return "Estrato Financeiro Turma: " + turma;
    }

    private String getPdfSubTitulo() {
        return "lista-presenca";
    }

    private String getPdfPalavrasChaves() {
        return "ravunana, mapa, salario";
    }

    private String getPdfCriador() {
        return "ravunana.com";
    }

    private String getPdfAutor() {
        return SecurityUtils.getCurrentUserLogin().get();
    }

    private Paragraph addNewLine() {
        return new Paragraph(Chunk.NEWLINE);
    }

    private Image getLogotipo() {
        var instituicao = instituicaoEnsinoService.getInstituicao(SecurityUtils.getCurrentUserLogin().get());
        Image image = null;
        try {
            image = Image.getInstance(instituicao.getLogotipo());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        image.scaleAbsolute(80f, 80);
        image.setAlignment(Element.ALIGN_CENTER);

        return image;
    }

    private String getInfoEmpresa() {
        var instituicao = instituicaoEnsinoService.getInstituicao(SecurityUtils.getCurrentUserLogin().get());
        var nome = instituicao.getUnidadeOrganica();
        var departamento = "Secretária-geral";

        var nif = "Nif nº " + instituicao.getNif();
        var tituloMapa = "Lista de Presença";

        return (nome + "\n" + nif + "\n" + departamento + "\n" + tituloMapa);
    }

    private PdfPTable getHeader() {
        Font tableFont = FontFactory.getFont("Helvetica", FONT_SIZE_TITLE, Font.NORMAL, Color.BLACK);
        float padding = 2f;
        float leading = tableFont.getSize() * 1.1f;
        Rectangle border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(0f);
        border.setBorderWidthBottom(0f);
        border.setBorderWidthRight(0f);
        border.setBorderWidthTop(0f);

        PdfPTable tableHeader = new PdfPTable(3);
        tableHeader.setWidthPercentage(100f);

        var vistoCell = makeCell(
            getVistoGestor(),
            Element.ALIGN_TOP,
            Element.ALIGN_CENTER,
            tableFont,
            leading,
            padding,
            border,
            true,
            false
        );
        var infoEmpresaCell = makeCell(
            getInfoEmpresa(),
            Element.ALIGN_TOP,
            Element.ALIGN_CENTER,
            tableFont,
            leading,
            padding,
            border,
            true,
            false
        );

        var dataCell = makeCell(
            "Actualização: " +
            Constants.getDateFormat(Constants.DATE_TIME.toLocalDate()) +
            " " +
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            Element.ALIGN_TOP,
            Element.ALIGN_RIGHT,
            tableFont,
            leading,
            padding,
            border,
            true,
            false
        );

        tableHeader.addCell(vistoCell);
        tableHeader.addCell(infoEmpresaCell);
        tableHeader.addCell(dataCell);

        return tableHeader;
    }

    private PdfPTable getDetalhe(Long turmaID) {
        var itemsFactura = itemFacturaService.getItemsFacturaByTurmaAndEmolumento(turmaID, 6L);

        int contador = 1;

        Font tableFontNormal = FontFactory.getFont("Helvetica", FONT_ZIZE_NORMAL, Font.NORMAL, Color.BLACK);
        Font tableFontHeader = FontFactory.getFont("Helvetica", FONT_ZIZE_NORMAL, Font.BOLD, Color.BLACK);
        float padding = 2f;
        float leading = tableFontNormal.getSize() * 1.1f;
        Rectangle border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(1f);
        border.setBorderWidthBottom(0.5f);
        border.setBorderWidthRight(0.5f);
        border.setBorderWidthTop(1f);

        float[] widths = { 0.1f, 0.3f, 0.4f, 0.2f, 0.3f, 0.3f, 0.3f, 0.3f };
        // Nº chamada, Nº processo, Nome completo, idade, sexo, observacoes
        PdfPTable tableDetalhe = new PdfPTable(widths);
        tableDetalhe.setWidthPercentage(100);

        // Headers
        tableDetalhe.addCell(
            makeCellBackgroudColor("Nº", Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontHeader, leading, padding, border, true, false)
        );

        tableDetalhe.addCell(
            makeCellBackgroudColor(
                "Nº Matricula".toUpperCase(),
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                tableFontHeader,
                leading,
                padding,
                border,
                true,
                false
            )
        );

        tableDetalhe.addCell(
            makeCellBackgroudColor(
                "Nome Completo".toUpperCase(),
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                tableFontHeader,
                leading,
                padding,
                border,
                true,
                false
            )
        );

        tableDetalhe.addCell(
            makeCellBackgroudColor(
                "Conta".toUpperCase(),
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                tableFontHeader,
                leading,
                padding,
                border,
                true,
                false
            )
        );

        tableDetalhe.addCell(
            makeCellBackgroudColor(
                "Referência".toUpperCase(),
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                tableFontHeader,
                leading,
                padding,
                border,
                true,
                false
            )
        );

        tableDetalhe.addCell(
            makeCellBackgroudColor(
                "Data de Transação".toUpperCase(),
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                tableFontHeader,
                leading,
                padding,
                border,
                true,
                false
            )
        );
        tableDetalhe.addCell(
            makeCellBackgroudColor(
                "Data de Pagamento".toUpperCase(),
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                tableFontHeader,
                leading,
                padding,
                border,
                true,
                false
            )
        );
        tableDetalhe.addCell(
            makeCellBackgroudColor(
                "Utilizador".toUpperCase(),
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                tableFontHeader,
                leading,
                padding,
                border,
                true,
                false
            )
        );

        // Content

        // o for será de todos os alunos da turma
        for (var matricula : matriculaService.getMatriculas(turmaID)) {
            var discente = matricula.getDiscente();

            getLinhaPagoNaoPago(matricula.getId(), tableDetalhe, tableFontNormal, leading, padding, border);
        }

        return tableDetalhe;
    }

    private void getLinhaPagoNaoPago(
        Long matricula,
        PdfPTable tableDetalhe,
        Font tableFontNormal,
        float leading,
        float padding,
        Rectangle border
    ) {
        var utilizador = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        var matriculaTra = transacaoService.getUltimaTransacaoMatricula(matricula).getMatricula();
        var conta = transacaoService.getUltimaTransacaoMatricula(matricula).getConta().getTitulo();
        var referencia = transacaoService.getUltimaTransacaoMatricula(matricula).getReferencia().toString();
        var dataTransacao = transacaoService.getUltimaTransacaoMatricula(matricula).getTimestamp().toLocalDate().toString();
        var dataPagamento = transacaoService.getUltimaTransacaoMatricula(matricula).getData().toString();
        //        var user = transacaoService.getUltimaTransacaoMatricula(matricula).getUtilizador();

        // Nº Chamada
        tableDetalhe.addCell(
            makeCell(
                matriculaTra.getNumeroChamada().toString(),
                Element.ALIGN_TOP,
                Element.ALIGN_CENTER,
                tableFontNormal,
                leading,
                padding,
                border,
                true,
                false
            )
        );

        // Nº processo
        tableDetalhe.addCell(
            makeCell(
                matriculaTra.getNumeroMatricula(),
                Element.ALIGN_TOP,
                Element.ALIGN_LEFT,
                tableFontNormal,
                leading,
                padding,
                border,
                true,
                false
            )
        );

        // Nome Completo
        tableDetalhe.addCell(
            makeCell(
                matriculaTra.getDiscente().getNome(),
                Element.ALIGN_TOP,
                Element.ALIGN_LEFT,
                tableFontNormal,
                leading,
                padding,
                border,
                true,
                false
            )
        );

        // CONTA
        tableDetalhe.addCell(
            makeCell(conta, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // REF
        tableDetalhe.addCell(
            makeCell(referencia, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // DATA TRANSACAO
        tableDetalhe.addCell(
            makeCell(dataTransacao, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // DATA PAGAMENTO
        tableDetalhe.addCell(
            makeCell(dataPagamento, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // UTILIZADOR
        tableDetalhe.addCell(
            makeCell(utilizador.getLogin(), Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );
    }

    private Paragraph getTituloMapa(String curso, String classe, String sala, String turno, String turma) {
        String titulo =
            "\n\nTurma: " +
            turma +
            "\n\n" +
            "Curso: " +
            curso +
            " | " +
            "Classe: " +
            classe +
            " | " +
            "Sala: " +
            sala +
            " | " +
            "Turno: " +
            turno +
            " ";
        Paragraph p = new Paragraph(titulo.toUpperCase());
        Font font = FontFactory.getFont("Helvetica", 6, Font.BOLD, Color.BLACK);
        p.setFont(font);

        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(4f);
        p.setSpacingBefore(4f);

        return p;
    }

    private String getVistoGestor() {
        String descricaoVisto = "Visto";
        String descricaoSupervisor = "";

        return descricaoVisto + ":\n__________________________\n" + descricaoSupervisor;
    }

    private static PdfPCell makeCell(
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
