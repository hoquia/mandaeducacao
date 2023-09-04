package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequenciaDocumentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenciaDocumentoDTO.class);
        SequenciaDocumentoDTO sequenciaDocumentoDTO1 = new SequenciaDocumentoDTO();
        sequenciaDocumentoDTO1.setId(1L);
        SequenciaDocumentoDTO sequenciaDocumentoDTO2 = new SequenciaDocumentoDTO();
        assertThat(sequenciaDocumentoDTO1).isNotEqualTo(sequenciaDocumentoDTO2);
        sequenciaDocumentoDTO2.setId(sequenciaDocumentoDTO1.getId());
        assertThat(sequenciaDocumentoDTO1).isEqualTo(sequenciaDocumentoDTO2);
        sequenciaDocumentoDTO2.setId(2L);
        assertThat(sequenciaDocumentoDTO1).isNotEqualTo(sequenciaDocumentoDTO2);
        sequenciaDocumentoDTO1.setId(null);
        assertThat(sequenciaDocumentoDTO1).isNotEqualTo(sequenciaDocumentoDTO2);
    }
}
