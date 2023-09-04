package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelTurmaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelTurma.class);
        ResponsavelTurma responsavelTurma1 = new ResponsavelTurma();
        responsavelTurma1.setId(1L);
        ResponsavelTurma responsavelTurma2 = new ResponsavelTurma();
        responsavelTurma2.setId(responsavelTurma1.getId());
        assertThat(responsavelTurma1).isEqualTo(responsavelTurma2);
        responsavelTurma2.setId(2L);
        assertThat(responsavelTurma1).isNotEqualTo(responsavelTurma2);
        responsavelTurma1.setId(null);
        assertThat(responsavelTurma1).isNotEqualTo(responsavelTurma2);
    }
}
