package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotasGeralDisciplinaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotasGeralDisciplina.class);
        NotasGeralDisciplina notasGeralDisciplina1 = new NotasGeralDisciplina();
        notasGeralDisciplina1.setId(1L);
        NotasGeralDisciplina notasGeralDisciplina2 = new NotasGeralDisciplina();
        notasGeralDisciplina2.setId(notasGeralDisciplina1.getId());
        assertThat(notasGeralDisciplina1).isEqualTo(notasGeralDisciplina2);
        notasGeralDisciplina2.setId(2L);
        assertThat(notasGeralDisciplina1).isNotEqualTo(notasGeralDisciplina2);
        notasGeralDisciplina1.setId(null);
        assertThat(notasGeralDisciplina1).isNotEqualTo(notasGeralDisciplina2);
    }
}
