package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotasPeriodicaDisciplinaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotasPeriodicaDisciplina.class);
        NotasPeriodicaDisciplina notasPeriodicaDisciplina1 = new NotasPeriodicaDisciplina();
        notasPeriodicaDisciplina1.setId(1L);
        NotasPeriodicaDisciplina notasPeriodicaDisciplina2 = new NotasPeriodicaDisciplina();
        notasPeriodicaDisciplina2.setId(notasPeriodicaDisciplina1.getId());
        assertThat(notasPeriodicaDisciplina1).isEqualTo(notasPeriodicaDisciplina2);
        notasPeriodicaDisciplina2.setId(2L);
        assertThat(notasPeriodicaDisciplina1).isNotEqualTo(notasPeriodicaDisciplina2);
        notasPeriodicaDisciplina1.setId(null);
        assertThat(notasPeriodicaDisciplina1).isNotEqualTo(notasPeriodicaDisciplina2);
    }
}
