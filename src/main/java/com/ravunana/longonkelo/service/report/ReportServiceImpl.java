package com.ravunana.longonkelo.service.report;

import com.ravunana.longonkelo.domain.report.ReportMetadata;
import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public String createTempFile(String filePrefix, String fileExtension) {
        File temp = null;
        try {
            temp = File.createTempFile(filePrefix, fileExtension);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return temp.getAbsolutePath();
    }

    @Override
    public ReportMetadata setMetadata(ReportMetadata metadata) {
        return new ReportMetadata(
            metadata.getTitulo(),
            metadata.getSubTitulo(),
            metadata.getPalavrasChave(),
            metadata.getCriador(),
            metadata.getAutor()
        );
    }
}
