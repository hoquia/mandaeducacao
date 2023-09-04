package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelTurnoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelTurno.class);
        ResponsavelTurno responsavelTurno1 = new ResponsavelTurno();
        responsavelTurno1.setId(1L);
        ResponsavelTurno responsavelTurno2 = new ResponsavelTurno();
        responsavelTurno2.setId(responsavelTurno1.getId());
        assertThat(responsavelTurno1).isEqualTo(responsavelTurno2);
        responsavelTurno2.setId(2L);
        assertThat(responsavelTurno1).isNotEqualTo(responsavelTurno2);
        responsavelTurno1.setId(null);
        assertThat(responsavelTurno1).isNotEqualTo(responsavelTurno2);
    }
}
