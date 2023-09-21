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
import com.ravunana.longonkelo.service.impl.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class FluxoCaixaCategoriaEmolumentoReport {

    private final ReportServiceImpl reportService;
    private final InstituicaoEnsinoServiceImpl instituicaoEnsinoService;
    private final HorarioServiceImpl horarioService;
    private final DocenteServiceImpl docenteService;
    private final ItemFacturaServiceImpl itemFacturaService;
    private final MatriculaServiceImpl matriculaService;
    private final float FONT_ZIZE_NORMAL = 7;
    private final float FONT_SIZE_TITLE = 7;

    public FluxoCaixaCategoriaEmolumentoReport(
        ReportServiceImpl reportService,
        InstituicaoEnsinoServiceImpl instituicaoEnsinoService,
        HorarioServiceImpl horarioService,
        DocenteServiceImpl docenteService,
        ItemFacturaServiceImpl itemFacturaService,
        MatriculaServiceImpl matriculaService
    ) {
        this.reportService = reportService;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.horarioService = horarioService;
        this.docenteService = docenteService;
        this.matriculaService = matriculaService;
        this.itemFacturaService = itemFacturaService;
    }

    public String gerarPdf(Long emolumentoID) {
        Document pdfDocument;
        String pdfName;
        FileOutputStream file;
        pdfName = "fluxo-caixa " + emolumentoID;
        pdfDocument = new Document(PageSize.A4.rotate(), 3, 3, 20, 3);
        String tempFileName;

        try {
            tempFileName = reportService.createTempFile(pdfName, ".pdf");
            file = new FileOutputStream(tempFileName);

            final PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument, file);

            pdfDocument.open();

            //          var docente = itemFacturaService.getItemsFactura()

            // Pdf Metadatas
            pdfDocument.addTitle(getPdfTitulo("fluxo caixa"));
            pdfDocument.addSubject(getPdfSubTitulo());
            pdfDocument.addKeywords(getPdfPalavrasChaves());
            pdfDocument.addCreator(getPdfCriador());
            pdfDocument.addAuthor(getPdfAutor());

            pdfDocument.add(getLogotipo());

            var header = getHeader();
            pdfDocument.add(header);
            pdfDocument.add(getTituloMapa("docente.getNome()"));
            pdfDocument.add(addNewLine());
            var detalhe = getDetalhe(emolumentoID);
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

    private String getPdfTitulo(String docente) {
        return "horario docente: " + docente;
    }

    private String getPdfSubTitulo() {
        return "horario-docente";
    }

    private String getPdfPalavrasChaves() {
        return "ravunana, horario, docente";
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
        var departamento = "Sub-Direcção Pedagógica";

        var nif = "Nif nº " + instituicao.getNif();
        var tituloMapa = "Horário do docente";

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

    private PdfPTable getDetalhe(Long emolumentoID) {
        //        var horarios = horarioService.getHorarioDocente(docenteID);

        Font tableFontNormal = FontFactory.getFont("Helvetica", FONT_ZIZE_NORMAL, Font.NORMAL, Color.BLACK);
        Font tableFontHeader = FontFactory.getFont("Helvetica", FONT_ZIZE_NORMAL, Font.BOLD, Color.BLACK);
        float padding = 2f;
        float leading = tableFontNormal.getSize() * 1.1f;
        Rectangle border = new Rectangle(0f, 0f);
        border.setBorderWidthLeft(1f);
        border.setBorderWidthBottom(0.5f);
        border.setBorderWidthRight(0.5f);
        border.setBorderWidthTop(1f);

        float[] widths = { 0.1f, 0.3f, 0.6f, 0.2f, 0.2f, 0.6f, 0.6f };
        // Nº chamada, Nº processo, Nome completo, idade, sexo, observacoes
        PdfPTable tableDetalhe = new PdfPTable(7);
        tableDetalhe.setWidthPercentage(100);

        // Headers

        tableDetalhe.addCell(
            makeCellBackgroudColor(
                "Classe".toUpperCase(),
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
                "Nº de Discentes".toUpperCase(),
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
                "Preço Unit.".toUpperCase(),
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
                "Total Multa".toUpperCase(),
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
                "Total Juros".toUpperCase(),
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
                "Total Desconto".toUpperCase(),
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
                "Total".toUpperCase(),
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

        return tableDetalhe;
    }

    private void detalhe(
        Long emolumentoID,
        Long classeID,
        Font tableFontNormal,
        float leading,
        float padding,
        Rectangle border,
        PdfPTable tableDetalhe
    ) {
        var itensCategoria = itemFacturaService.getItensFacturaWithCategoria(emolumentoID);
        var discentesClasse = matriculaService.getMatroculaWithClasse(classeID).size();
        var preco = 0;
        var multa = 0;
        var juro = 0;

        var ex = 0;
        for (var item : itensCategoria) {
            var itemClasse = item
                .getEmolumento()
                .getPrecosEmolumentos()
                .stream()
                .filter(pe -> pe.getClasse().getId().equals(classeID))
                .findFirst()
                .get();
            // var p = preco + itemClasse.getPreco();

            // tableDetalhe.addCell(
            //     makeCell(preco.toString(), Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
            // );
            // tableDetalhe.addCell(
            //     makeCell(preco.toString(), Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
            // );
            // tableDetalhe.addCell(
            //     makeCell(preco.toString(), Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
            // );
            // tableDetalhe.addCell(
            //     makeCell(preco.toString(), Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
            // );
            // tableDetalhe.addCell(
            //     makeCell(preco.toString(), Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
            // );
            // tableDetalhe.addCell(
            //     makeCell(preco.toString(), Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
            // );
            // tableDetalhe.addCell(
            //     makeCell(preco.toString(), Element.ALIGN_TOP, Element.ALIGN_CENTER, tableFontNormal, leading, padding, border, true, false)
            // );
        }
    }

    //
    //    private PdfPTable getHorarioTempo(
    //        List<HorarioDTO> horarios,
    //        PeriodoHorarioDTO tempo,
    //        PdfPTable tableDetalhe,
    //        Font tableFontNormal,
    //        float leading,
    //        float padding,
    //        Rectangle border
    //    ) {
    //        var segunda = horarios
    //            .stream()
    //            .filter(x -> x.getPeriodo().getId().equals(tempo.getId()) && x.getDiaSemana().equals(DiaSemana.SEGUNDA))
    //            .findFirst();
    //        var terca = horarios
    //            .stream()
    //            .filter(x -> x.getPeriodo().getId().equals(tempo.getId()) && x.getDiaSemana().equals(DiaSemana.TERCA))
    //            .findFirst();
    //        var quarta = horarios
    //            .stream()
    //            .filter(x -> x.getPeriodo().getId().equals(tempo.getId()) && x.getDiaSemana().equals(DiaSemana.QUARTA))
    //            .findFirst();
    //        var quinta = horarios
    //            .stream()
    //            .filter(x -> x.getPeriodo().getId().equals(tempo.getId()) && x.getDiaSemana().equals(DiaSemana.QUINTA))
    //            .findFirst();
    //        var sexta = horarios
    //            .stream()
    //            .filter(x -> x.getPeriodo().getId().equals(tempo.getId()) && x.getDiaSemana().equals(DiaSemana.SEXTA))
    //            .findFirst();
    //        var sabado = horarios
    //            .stream()
    //            .filter(x -> x.getPeriodo().getId().equals(tempo.getId()) && x.getDiaSemana().equals(DiaSemana.SABADO))
    //            .findFirst();
    //
    //        // Tempos
    //        tableDetalhe.addCell(
    //            makeCell(
    //                tempo.getTempo().toString() + "º",
    //                Element.ALIGN_TOP,
    //                Element.ALIGN_CENTER,
    //                tableFontNormal,
    //                leading,
    //                padding,
    //                border,
    //                true,
    //                false
    //            )
    //        );
    //
    //        // Horas
    //        tableDetalhe.addCell(
    //            makeCell(
    //                tempo.getInicio() + " - " + tempo.getFim(),
    //                Element.ALIGN_TOP,
    //                Element.ALIGN_CENTER,
    //                tableFontNormal,
    //                leading,
    //                padding,
    //                border,
    //                true,
    //                false
    //            )
    //        );
    //
    //        // Seunda-feira
    //
    //        if (segunda.isPresent()) {
    //            tableDetalhe.addCell(
    //                makeCell(
    //                    segunda.get().getDisciplinaCurricular().getDisciplina().getNome() + "\n" + segunda.get().getTurma().getDescricao(),
    //                    Element.ALIGN_CENTER,
    //                    Element.ALIGN_CENTER,
    //                    tableFontNormal,
    //                    leading,
    //                    padding,
    //                    border,
    //                    true,
    //                    false
    //                )
    //            );
    //        } else {
    //            tableDetalhe.addCell(
    //                makeCell("", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFontNormal, leading, padding, border, true, false)
    //            );
    //        }
    //
    //        // Terça-feira
    //        if (terca.isPresent()) {
    //            tableDetalhe.addCell(
    //                makeCell(
    //                    terca.get().getDisciplinaCurricular().getDisciplina().getNome() + "\n" + terca.get().getTurma().getDescricao(),
    //                    Element.ALIGN_CENTER,
    //                    Element.ALIGN_CENTER,
    //                    tableFontNormal,
    //                    leading,
    //                    padding,
    //                    border,
    //                    true,
    //                    false
    //                )
    //            );
    //        } else {
    //            tableDetalhe.addCell(
    //                makeCell("", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFontNormal, leading, padding, border, true, false)
    //            );
    //        }
    //
    //        // Quarta-feira
    //        if (quarta.isPresent()) {
    //            tableDetalhe.addCell(
    //                makeCell(
    //                    quarta.get().getDisciplinaCurricular().getDisciplina().getNome() + "\n" + quarta.get().getTurma().getDescricao(),
    //                    Element.ALIGN_CENTER,
    //                    Element.ALIGN_CENTER,
    //                    tableFontNormal,
    //                    leading,
    //                    padding,
    //                    border,
    //                    true,
    //                    false
    //                )
    //            );
    //        } else {
    //            tableDetalhe.addCell(
    //                makeCell("", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFontNormal, leading, padding, border, true, false)
    //            );
    //        }
    //
    //        // Quinta-feira
    //        if (quinta.isPresent()) {
    //            tableDetalhe.addCell(
    //                makeCell(
    //                    quinta.get().getDisciplinaCurricular().getDisciplina().getNome() + "\n" + quinta.get().getTurma().getDescricao(),
    //                    Element.ALIGN_CENTER,
    //                    Element.ALIGN_CENTER,
    //                    tableFontNormal,
    //                    leading,
    //                    padding,
    //                    border,
    //                    true,
    //                    false
    //                )
    //            );
    //        } else {
    //            tableDetalhe.addCell(
    //                makeCell("", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFontNormal, leading, padding, border, true, false)
    //            );
    //        }
    //
    //        // Sexa-feira
    //        if (sexta.isPresent()) {
    //            tableDetalhe.addCell(
    //                makeCell(
    //                    sexta.get().getDisciplinaCurricular().getDisciplina().getNome() + "\n" + sexta.get().getTurma().getDescricao(),
    //                    Element.ALIGN_CENTER,
    //                    Element.ALIGN_CENTER,
    //                    tableFontNormal,
    //                    leading,
    //                    padding,
    //                    border,
    //                    true,
    //                    false
    //                )
    //            );
    //        } else {
    //            tableDetalhe.addCell(
    //                makeCell("", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFontNormal, leading, padding, border, true, false)
    //            );
    //        }
    //
    //        // Sabado
    //        if (sabado.isPresent()) {
    //            tableDetalhe.addCell(
    //                makeCell(
    //                    sabado.get().getDisciplinaCurricular().getDisciplina().getNome() + "\n" + sabado.get().getTurma().getDescricao(),
    //                    Element.ALIGN_CENTER,
    //                    Element.ALIGN_CENTER,
    //                    tableFontNormal,
    //                    leading,
    //                    padding,
    //                    border,
    //                    true,
    //                    false
    //                )
    //            );
    //        } else {
    //            tableDetalhe.addCell(
    //                makeCell("", Element.ALIGN_TOP, Element.ALIGN_LEFT, tableFontNormal, leading, padding, border, true, false)
    //            );
    //        }
    //
    //        return tableDetalhe;
    //    }

    private Paragraph getTituloMapa(String nomeDocente) {
        String titulo = "\n\nDocente: " + nomeDocente;
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
