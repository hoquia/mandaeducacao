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
import com.ravunana.longonkelo.service.impl.InstituicaoEnsinoServiceImpl;
import com.ravunana.longonkelo.service.impl.MatriculaServiceImpl;
import com.ravunana.longonkelo.service.impl.NotasGeralDisciplinaServiceImpl;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class CertificadoServiceReport {

    private final ReportService reportService;
    private final InstituicaoEnsinoServiceImpl instituicaoEnsinoService;
    private final MatriculaServiceImpl matriculaService;

    private final NotasGeralDisciplinaServiceImpl notasGeralDisciplinaService;

    public CertificadoServiceReport(
        ReportService reportService,
        InstituicaoEnsinoServiceImpl instituicaoEnsinoService,
        MatriculaServiceImpl matriculaService,
        NotasGeralDisciplinaServiceImpl notasGeralDisciplinaService
    ) {
        this.reportService = reportService;
        this.instituicaoEnsinoService = instituicaoEnsinoService;
        this.matriculaService = matriculaService;
        this.notasGeralDisciplinaService = notasGeralDisciplinaService;
    }

    public String gerarCertificadoPdf(Long matriculaID) {
        Document document;
        String pdfName;
        FileOutputStream file;
        pdfName = "certificado";
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

            final PdfWriter pdfWriter = PdfWriter.getInstance(document, file);

            document.open();

            // Pdf Metadatas
            document.addTitle("certificado" + matriculaID);
            document.addSubject(matriculaID.toString());
            document.addKeywords("certificado");
            document.addCreator("ravunana,lda");
            document.addAuthor("ravunana");

            PdfPTable layoutTable = new PdfPTable(1);
            layoutTable.setWidthPercentage(85f);

            layoutTable.addCell(
                makeCellTable(getCertificado(matriculaID), Element.ALIGN_CENTER, Element.ALIGN_LEFT, leading, padding, border, true, false)
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
        var empresa = instituicaoEnsinoService.getInstituicao(SecurityUtils.getCurrentUserLogin().get()); // TODO: Pesquisar pelo id da empresa do usuario logado
        var nome = empresa.getUnidadeOrganica();
        var departamento = "Secretária-geral";
        String nif = "NIF nº ";
        if (empresa.getNif() != null) {
            nif = "NIF nº " + empresa.getNif();
        }

        return (nome + "\n" + departamento + "\n" + nif);
    }

    private PdfPTable getCertificado(Long matriculaID) {
        var listNotasGeraisAluno = notasGeralDisciplinaService.getNotaWithMatricula(matriculaID);
        var notaGeralAluno = listNotasGeraisAluno.stream().findFirst().get();

        int NUM_LINHA_ATE_FIM_PAGINA = 30;
        int NUM_LINHA_FACTURA = listNotasGeraisAluno.size();
        int NUM_LINHA_BRANCA_ADICIONAR = NUM_LINHA_ATE_FIM_PAGINA - NUM_LINHA_FACTURA;

        var matricula = matriculaService.findOne(notaGeralAluno.getMatricula().getId()).get();
        var discente = matricula.getDiscente();

        String textoDeclaracaoPrimeiro =
            "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";

        String textoDeclaracaoSegundo =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        Font fontNormal = FontFactory.getFont("Helvetica", 8, Font.NORMAL, Color.BLACK);
        Font fontBold = FontFactory.getFont("Helvetica", 7, Font.BOLD, Color.BLACK);
        Font fontBoldLarge = FontFactory.getFont("Helvetica", 12, Font.BOLD, Color.BLACK);
        Font fontNormalText = FontFactory.getFont("Helvetica", 12, Font.NORMAL, Color.BLACK);

        var borderNone = getBorder(0f, 0f, 0f, 0f, 0f, 0f);
        var borderNormal = getBorder(0f, 0f, 1f, 1f, 1f, 1f);
        var borderSmaller = getBorder(0f, 0f, 0.3f, 0.3f, 0.3f, 0.3f);

        float padding = 1.3f;
        float paddingTexto = 14f;
        float leading = fontNormal.getSize() * 1.1f;
        float leadingTexto = fontNormal.getSize() * 2f;

        PdfPTable layoutTable = new PdfPTable(1);
        layoutTable.setWidthPercentage(100f);

        // Header
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(80f);
        headerTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_LEFT, fontBoldLarge, leading, padding, borderNone, true, false)
        );
        headerTable.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBold, leading, padding, borderNone, true, false));

        // SubHeader
        PdfPTable subHeader = new PdfPTable(1);
        subHeader.setWidthPercentage(80f);

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
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));

        Paragraph tituloDocumento = new Paragraph("CERTIFICADO DE HABILITACAO");

        subHeader.addCell(
            makeCellText(
                tituloDocumento.toString(),
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
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));
        subHeader.addCell(makeCellText("", Element.ALIGN_TOP, Element.ALIGN_CENTER, fontBold, leading, padding, borderNone, true, false));

        // SubHeader
        PdfPTable detalheTable = new PdfPTable(2);
        detalheTable.setWidthPercentage(80f);

        PdfPTable detalheMatricula = new PdfPTable(2);
        detalheMatricula.setWidthPercentage(80f);

        float[] widths = { 0.6f, 0.3f };
        PdfPTable ajustesTable = new PdfPTable(widths);
        ajustesTable.setWidthPercentage(80f);

        ajustesTable.addCell(
            makeCellBackgroudColor(
                "Disciplina",
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

        for (var nota : listNotasGeraisAluno) {
            var disciplinaCurricular = nota.getDisciplinaCurricular().getDisciplina().getNome();
            var media = nota.getMediaFinalDisciplina();

            // LinhasDocumento
            getLinhasDocumento(ajustesTable, fontNormal, leading, padding, borderSmaller, disciplinaCurricular, media.toString());
        }

        com.lowagie.text.Rectangle noBorder = new com.lowagie.text.Rectangle(0f, 0f);
        noBorder.setBorderWidthLeft(0f);
        noBorder.setBorderWidthBottom(0f);
        noBorder.setBorderWidthRight(0f);
        noBorder.setBorderWidthTop(0f);

        // Linhas Documento em branco para o resumo estar no rodapé
        for (int i = 0; i <= NUM_LINHA_BRANCA_ADICIONAR; i++) {
            getLinhasDocumento(ajustesTable, fontNormal, leading, padding, noBorder, "", "");
        }

        // Layout Página
        layoutTable.addCell(makeCellTable(headerTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));

        layoutTable.addCell(makeCellTable(subHeader, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false));
        layoutTable.addCell(
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );

        Paragraph textoPrincipal = new Paragraph(textoDeclaracaoPrimeiro);
        textoPrincipal.setSpacingAfter(8f);

        layoutTable.addCell(
            makeCellText(
                textoPrincipal.toString(),
                Element.ALIGN_JUSTIFIED,
                Element.ALIGN_JUSTIFIED,
                fontNormalText,
                leadingTexto,
                paddingTexto,
                borderNone,
                true,
                false
            )
        );

        Paragraph textoSegundo = new Paragraph(textoDeclaracaoSegundo);
        textoPrincipal.setSpacingAfter(8f);
        layoutTable.addCell(
            makeCellText(
                textoSegundo.toString(),
                Element.ALIGN_JUSTIFIED_ALL,
                Element.ALIGN_JUSTIFIED_ALL,
                fontNormalText,
                leadingTexto,
                paddingTexto,
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
            makeCellText("", Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontBoldLarge, leading, padding, borderNone, true, false)
        );

        layoutTable.addCell(
            makeCellTable(ajustesTable, Element.ALIGN_TOP, Element.ALIGN_CENTER, leading, padding, borderNone, true, false)
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
            makeCellText("", Element.ALIGN_TOP, Element.ALIGN_RIGHT, fontBoldLarge, leading, padding, borderNone, true, false)
        );

        return layoutTable;
    }

    private void getLinhasDocumento(
        PdfPTable ajustesTable,
        Font fontNormal,
        float leading,
        float padding,
        com.lowagie.text.Rectangle borderSmaller,
        String disciplinaCurricular,
        String media
    ) {
        // data
        ajustesTable.addCell(
            makeCellText(
                disciplinaCurricular,
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

        // Multa
        ajustesTable.addCell(
            makeCellText(media, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER, fontNormal, leading, padding, borderSmaller, true, false)
        );
    }

    private PdfPTable getAssinatura(String nomeResponsavel, String nomeFuncionario) {
        var today = LocalDate.now();
        var data = " LUANDA, AOS " + today.getDayOfMonth() + " DE " + Constants.getMesPT(today) + " DE " + today.getYear();
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
        assinaturaTabler.setHorizontalAlignment(Element.ALIGN_CENTER);

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

        return assinaturaTabler;
    }

    private String getVistoGestor(Long matriculaID) {
        var notaGeralAluno = notasGeralDisciplinaService.getNotaWithMatricula(matriculaID).stream().findFirst().get();

        var responsavel = notaGeralAluno.getUtilizador().getLogin();

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
