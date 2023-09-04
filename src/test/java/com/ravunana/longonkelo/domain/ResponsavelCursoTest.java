package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelCursoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelCurso.class);
        ResponsavelCurso responsavelCurso1 = new ResponsavelCurso();
        responsavelCurso1.setId(1L);
        ResponsavelCurso responsavelCurso2 = new ResponsavelCurso();
        responsavelCurso2.setId(responsavelCurso1.getId());
        assertThat(responsavelCurso1).isEqualTo(responsavelCurso2);
        responsavelCurso2.setId(2L);
        assertThat(responsavelCurso1).isNotEqualTo(responsavelCurso2);
        responsavelCurso1.setId(null);
        assertThat(responsavelCurso1).isNotEqualTo(responsavelCurso2);
    }
}
