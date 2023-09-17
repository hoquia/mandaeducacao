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
import com.ravunana.longonkelo.service.PlanoAulaService;
import com.ravunana.longonkelo.service.dto.PlanoAulaDTO;
import com.ravunana.longonkelo.service.impl.DetalhePlanoAulaServiceImpl;
import com.ravunana.longonkelo.service.impl.InstituicaoEnsinoServiceImpl;
import com.ravunana.longonkelo.service.impl.MatriculaServiceImpl;
import com.ravunana.longonkelo.service.impl.TurmaServiceImpl;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PlanoAulaReport {

    private final ReportServiceImpl reportService;
    private final InstituicaoEnsinoServiceImpl instituicaoEnsinoService;
    private final PlanoAulaService planoAulaService;
    private final DetalhePlanoAulaServiceImpl detalhePlanoAulaService;
    private final float FONT_ZIZE_NORMAL = 7;
    private final float FONT_SIZE_TITLE = 7;

    public PlanoAulaReport(
        ReportServiceImpl reportService,
        InstituicaoEnsinoServiceImpl instituicaoEnsinoService,
        PlanoAulaService planoAulaService,
        DetalhePlanoAulaServiceImpl detalhePlanoAulaService
    ) {
        this.reportService = reportService;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.detalhePlanoAulaService = detalhePlanoAulaService;
        this.planoAulaService = planoAulaService;
    }

    public String gerarPdf(Long planoAulaId) {
        Document pdfDocument;
        String pdfName;
        FileOutputStream file;
        pdfName = "Plano de Aula " + planoAulaId;
        pdfDocument = new Document(PageSize.A4.rotate(), 3, 3, 20, 3);
        String tempFileName;

        try {
            tempFileName = reportService.createTempFile(pdfName, ".pdf");
            file = new FileOutputStream(tempFileName);

            final PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument, file);

            pdfDocument.open();

            var planoAulaDTO = planoAulaService.findOne(planoAulaId).get();
            var turma = planoAulaDTO.getTurma();
            var planoCurricular = turma.getPlanoCurricular();

            // Pdf Metadatas
            pdfDocument.addTitle(getPdfTitulo(turma.getDescricao()));
            pdfDocument.addSubject(getPdfSubTitulo());
            pdfDocument.addKeywords(getPdfPalavrasChaves());
            pdfDocument.addCreator(getPdfCriador());
            pdfDocument.addAuthor(getPdfAutor());

            pdfDocument.add(getLogotipo());

            String curso = planoCurricular.getCurso().getNome();
            String classe = planoCurricular.getClasse().getDescricao();
            String sala = turma.getSala().toString();
            String turno = turma.getTurno().getNome();

            var header = getHeader();
            pdfDocument.add(header);
            pdfDocument.add(getTituloMapa(curso, classe, sala, turno, turma.getDescricao()));
            pdfDocument.add(addNewLine());

            // contentLeft
            pdfDocument.add(getContentLeft(planoAulaDTO));
            // contentRigth
            pdfDocument.add(getContentRight(planoAulaDTO));

            pdfDocument.add(addNewLine());

            var detalhe = getDetalhe(planoAulaId);
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
        return "Lista de Presença da Turma: " + turma;
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
        //        var departamento = "Secretária-geral";

        var nif = "Nif nº " + instituicao.getNif();
        var tituloMapa = "Plano de Aula";

        return (nome + "\n" + nif + "\n" + tituloMapa);
    }

    private Paragraph getContentLeft(PlanoAulaDTO planoAulaDTO) {
        var docente = "Docente: " + planoAulaDTO.getDocente().getNome() + "\n";
        var disciplina = "Disciplina: " + planoAulaDTO.getDisciplinaCurricular().getDisciplina().getNome() + "\n";
        var unidadeTematica = "Unidade: " + planoAulaDTO.getUnidadeTematica().getDescricao() + "\n";
        // var subUnidade = "Sub unidade: " + planoAulaDTO.getSubUnidadeTematica().getDescricao() + "\n";
        var assunto = "Assunto: " + planoAulaDTO.getAssunto() + "\n";
        var tipoAula = "Tipo de aula: " + planoAulaDTO.getDocente().getNome() + "\n";
        var duracao = "Duração: " + planoAulaDTO.getTempoTotalLicao() + " minutos" + "\n";

        Paragraph p = new Paragraph(docente + disciplina + unidadeTematica + assunto + tipoAula + duracao);
        Font font = FontFactory.getFont("Helvetica", 6, Font.BOLD, Color.BLACK);
        p.setFont(font);

        p.setAlignment(Element.ALIGN_LEFT);
        p.setSpacingAfter(4f);
        p.setSpacingBefore(4f);

        return p;
    }

    private Paragraph getContentRight(PlanoAulaDTO planoAulaDTO) {
        var objectivoGeral = "Objectivo Geral: " + planoAulaDTO.getObjectivoGeral() + "\n";
        var objectivoEspecifico = "Objectivos Especificos: " + planoAulaDTO.getObjectivosEspecificos() + "\n";
        var perfilEntrada = "Perfil de entrada: " + planoAulaDTO.getPerfilEntrada() + "\n";
        var perfilSaida = "Perfil de saída: " + planoAulaDTO.getPerfilSaida() + "\n";

        Paragraph p = new Paragraph(objectivoGeral + objectivoEspecifico + perfilEntrada + perfilSaida);
        Font font = FontFactory.getFont("Helvetica", 6, Font.BOLD, Color.BLACK);
        p.setFont(font);

        p.setAlignment(Element.ALIGN_RIGHT);
        p.setSpacingAfter(4f);
        p.setSpacingBefore(4f);

        return p;
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

    private PdfPTable getDetalhe(Long planoAulaId) {
        var planoAula = planoAulaService.findOne(planoAulaId).get();
        var detalhePlanoAulaCOllection = detalhePlanoAulaService.getDetalhePlanoAula(planoAulaId);

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

        float[] widths = { 0.1f, 0.3f, 0.6f, 0.2f, 0.2f, 0.6f };
        // Nº chamada, Nº processo, Nome completo, idade, sexo, observacoes
        PdfPTable tableDetalhe = new PdfPTable(9);
        tableDetalhe.setWidthPercentage(100);

        // Headers

        tableDetalhe.addCell(
            makeCellBackgroudColor(
                "Título Actividade".toUpperCase(),
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
                "Estratégia".toUpperCase(),
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
                "Actvidade Docente".toUpperCase(),
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
                "Actividade Discente".toUpperCase(),
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
                "Tempo".toUpperCase(),
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
                "Recursos de Ensino".toUpperCase(),
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
                "Avaliação".toUpperCase(),
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
                "Bibliografia".toUpperCase(),
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
                "Observação".toUpperCase(),
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

        for (var detalhe : detalhePlanoAulaCOllection) {
            //            int idade = Constants.ANO - matricula.getDiscente().getNascimento().getYear();

            // Titulo Actividade
            tableDetalhe.addCell(
                makeCell(
                    detalhe.getTituloActividade(),
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

            // Estrategia
            tableDetalhe.addCell(
                makeCell(
                    detalhe.getEstrategiaAula(),
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

            // Actividade Docente
            tableDetalhe.addCell(
                makeCell(
                    detalhe.getActividadesDocente(),
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

            // Actividade Discente
            tableDetalhe.addCell(
                makeCell(
                    detalhe.getActividadesDiscentes(),
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

            // Tempo
            tableDetalhe.addCell(
                makeCell(
                    detalhe.getTempoActividade() + " min.",
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

            // Recurso de Ensino
            tableDetalhe.addCell(
                makeCell(
                    detalhe.getRecursosEnsino(),
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

            // Avaliação
            tableDetalhe.addCell(
                makeCell(
                    detalhe.getAvaliacao(),
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

            // Bibliografia
            tableDetalhe.addCell(
                makeCell(
                    detalhe.getBibliografia(),
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

            // Observacao
            tableDetalhe.addCell(
                makeCell(
                    detalhe.getObservacao(),
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
        }

        return tableDetalhe;
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
