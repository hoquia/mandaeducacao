package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelDisciplinaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelDisciplina.class);
        ResponsavelDisciplina responsavelDisciplina1 = new ResponsavelDisciplina();
        responsavelDisciplina1.setId(1L);
        ResponsavelDisciplina responsavelDisciplina2 = new ResponsavelDisciplina();
        responsavelDisciplina2.setId(responsavelDisciplina1.getId());
        assertThat(responsavelDisciplina1).isEqualTo(responsavelDisciplina2);
        responsavelDisciplina2.setId(2L);
        assertThat(responsavelDisciplina1).isNotEqualTo(responsavelDisciplina2);
        responsavelDisciplina1.setId(null);
        assertThat(responsavelDisciplina1).isNotEqualTo(responsavelDisciplina2);
    }
}
