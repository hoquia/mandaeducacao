package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoDisciplinaCurricularTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoDisciplinaCurricular.class);
        EstadoDisciplinaCurricular estadoDisciplinaCurricular1 = new EstadoDisciplinaCurricular();
        estadoDisciplinaCurricular1.setId(1L);
        EstadoDisciplinaCurricular estadoDisciplinaCurricular2 = new EstadoDisciplinaCurricular();
        estadoDisciplinaCurricular2.setId(estadoDisciplinaCurricular1.getId());
        assertThat(estadoDisciplinaCurricular1).isEqualTo(estadoDisciplinaCurricular2);
        estadoDisciplinaCurricular2.setId(2L);
        assertThat(estadoDisciplinaCurricular1).isNotEqualTo(estadoDisciplinaCurricular2);
        estadoDisciplinaCurricular1.setId(null);
        assertThat(estadoDisciplinaCurricular1).isNotEqualTo(estadoDisciplinaCurricular2);
    }
}
