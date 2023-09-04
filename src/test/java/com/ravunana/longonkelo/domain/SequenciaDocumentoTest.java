package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequenciaDocumentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenciaDocumento.class);
        SequenciaDocumento sequenciaDocumento1 = new SequenciaDocumento();
        sequenciaDocumento1.setId(1L);
        SequenciaDocumento sequenciaDocumento2 = new SequenciaDocumento();
        sequenciaDocumento2.setId(sequenciaDocumento1.getId());
        assertThat(sequenciaDocumento1).isEqualTo(sequenciaDocumento2);
        sequenciaDocumento2.setId(2L);
        assertThat(sequenciaDocumento1).isNotEqualTo(sequenciaDocumento2);
        sequenciaDocumento1.setId(null);
        assertThat(sequenciaDocumento1).isNotEqualTo(sequenciaDocumento2);
    }
}
