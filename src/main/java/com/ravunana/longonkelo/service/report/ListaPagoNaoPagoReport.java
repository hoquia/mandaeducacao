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
public class ListaPagoNaoPagoReport {

    private final ReportServiceImpl reportService;
    private final InstituicaoEnsinoServiceImpl instituicaoEnsinoService;
    private final MatriculaServiceImpl matriculaService;
    private final TurmaServiceImpl turmaService;
    private final ItemFacturaServiceImpl itemFacturaService;
    private final float FONT_ZIZE_NORMAL = 7;
    private final float FONT_SIZE_TITLE = 7;

    public ListaPagoNaoPagoReport(
        ReportServiceImpl reportService,
        InstituicaoEnsinoServiceImpl instituicaoEnsinoService,
        MatriculaServiceImpl matriculaService,
        TurmaServiceImpl turmaService,
        ItemFacturaServiceImpl itemFacturaService
    ) {
        this.reportService = reportService;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.matriculaService = matriculaService;
        this.turmaService = turmaService;
        this.itemFacturaService = itemFacturaService;
    }

    public String gerarListaPagoNaoPagoPdf(Long turmaID, Long emolumentoID) {
        Document pdfDocument;
        String pdfName;
        FileOutputStream file;
        pdfName = "lista-pago-nao-pago-turma " + turmaID;
        pdfDocument = new Document(PageSize.A4.rotate(), 3, 3, 20, 3);
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
            var detalhe = getListaPagoNaoPagoDetalhe(turmaID, emolumentoID);
            pdfDocument.add(detalhe);
            pdfDocument.add(addNewLine());
            pdfDocument.add(addNewLine());

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
        return "Lista de Pago e Não Pago: " + turma;
    }

    private String getPdfSubTitulo() {
        return "lista-de-pago-nao-pago";
    }

    private String getPdfPalavrasChaves() {
        return "ravunana, lista, pago, nao pago, emolumento";
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
        var tituloMapa = "Lista de Pago e Não Pago";

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

    private PdfPTable getListaPagoNaoPagoDetalhe(Long turmaID, Long emolumentoSelecionadoID) {
        var itemsFactura = itemFacturaService.getItemsFacturaByTurmaAndEmolumento(turmaID, emolumentoSelecionadoID);

        Font tableFontNormal = FontFactory.getFont("Helvetica", FONT_ZIZE_NORMAL, Font.NORMAL, Color.BLACK);
        Font tableFontHeader = FontFactory.getFont("Helvetica", FONT_ZIZE_NORMAL, Font.BOLD, Color.BLACK);
        float padding = 2f;
        float leading = tableFontNormal.getSize() * 1.1f;
        Rectangle border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(1f);
        border.setBorderWidthBottom(0.5f);
        border.setBorderWidthRight(0.5f);
        border.setBorderWidthTop(1f);

        float[] widths = { 0.1f, 0.3f, 0.4f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f };
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
                "Jan.".toUpperCase(),
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
                "Fev.".toUpperCase(),
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
                "Mar.".toUpperCase(),
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
                "Abr.".toUpperCase(),
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
                "Mai.".toUpperCase(),
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
                "Jun.".toUpperCase(),
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
                "Jul.".toUpperCase(),
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
                "Ago.".toUpperCase(),
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
                "Set.".toUpperCase(),
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
                "Oct.".toUpperCase(),
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
                "Nov.".toUpperCase(),
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
                "Dez.".toUpperCase(),
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
        for (var item : itemsFactura) {
            var matricula = item.getFactura().getMatricula();
            getLinhaPagoNaoPago(matricula, item, tableDetalhe, tableFontNormal, leading, padding, border);
        }

        return tableDetalhe;
    }

    private void getLinhaPagoNaoPago(
        MatriculaDTO matricula,
        ItemFacturaDTO item,
        PdfPTable tableDetalhe,
        Font tableFontNormal,
        float leading,
        float padding,
        Rectangle border
    ) {
        String jan = "PENDENTE";
        String fev = "PENDENTE";
        String mar = "PENDENTE";
        String abr = "PENDENTE";
        String mai = "PENDENTE";
        String jun = "PENDENTE";
        String jul = "PENDENTE";
        String ago = "PENDENTE";
        String set = "PENDENTE";
        String out = "PENDENTE";
        String nov = "PENDENTE";
        String dez = "PENDENTE";

        if (item.getPeriodo().equals(1)) {
            jan = item.getEstado().name();
        }
        if (item.getPeriodo().equals(2)) {
            fev = item.getEstado().name();
        }
        if (item.getPeriodo().equals(3)) {
            mar = item.getEstado().name();
        }
        if (item.getPeriodo().equals(4)) {
            abr = item.getEstado().name();
        }
        if (item.getPeriodo().equals(5)) {
            mai = item.getEstado().name();
        }
        if (item.getPeriodo().equals(6)) {
            jun = item.getEstado().name();
        }
        if (item.getPeriodo().equals(7)) {
            jul = item.getEstado().name();
        }
        if (item.getPeriodo().equals(8)) {
            ago = item.getEstado().name();
        }
        if (item.getPeriodo().equals(9)) {
            set = item.getEstado().name();
        }
        if (item.getPeriodo().equals(10)) {
            out = item.getEstado().name();
        }
        if (item.getPeriodo().equals(11)) {
            nov = item.getEstado().name();
        }
        if (item.getPeriodo().equals(12)) {
            dez = item.getEstado().name();
        }

        // Nº Chamada
        tableDetalhe.addCell(
            makeCell(
                matricula.getNumeroChamada().toString(),
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
                matricula.getNumeroMatricula(),
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
                matricula.getDiscente().getNome(),
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

        // JAN
        tableDetalhe.addCell(
            makeCell(jan, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // FEV
        tableDetalhe.addCell(
            makeCell(fev, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // MAR
        tableDetalhe.addCell(
            makeCell(mar, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // ABR
        tableDetalhe.addCell(
            makeCell(abr, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // MAI
        tableDetalhe.addCell(
            makeCell(mai, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // JUN
        tableDetalhe.addCell(
            makeCell(jun, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // JUL
        tableDetalhe.addCell(
            makeCell(jul, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // AGO
        tableDetalhe.addCell(
            makeCell(ago, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // SET
        tableDetalhe.addCell(
            makeCell(set, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // OUT
        tableDetalhe.addCell(
            makeCell(out, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // NOV
        tableDetalhe.addCell(
            makeCell(nov, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
        );

        // DEZ
        tableDetalhe.addCell(
            makeCell(dez, Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
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
