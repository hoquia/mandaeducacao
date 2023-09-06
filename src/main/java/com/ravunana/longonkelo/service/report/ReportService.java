package com.ravunana.longonkelo.service.report;

import com.ravunana.longonkelo.domain.report.ReportMetadata;

public interface ReportService {
    public String createTempFile(String fileName, String fileExtension);

    public ReportMetadata setMetadata(ReportMetadata metadata);
}
