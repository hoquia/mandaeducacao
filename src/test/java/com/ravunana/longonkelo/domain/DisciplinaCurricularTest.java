package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DisciplinaCurricularTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisciplinaCurricular.class);
        DisciplinaCurricular disciplinaCurricular1 = new DisciplinaCurricular();
        disciplinaCurricular1.setId(1L);
        DisciplinaCurricular disciplinaCurricular2 = new DisciplinaCurricular();
        disciplinaCurricular2.setId(disciplinaCurricular1.getId());
        assertThat(disciplinaCurricular1).isEqualTo(disciplinaCurricular2);
        disciplinaCurricular2.setId(2L);
        assertThat(disciplinaCurricular1).isNotEqualTo(disciplinaCurricular2);
        disciplinaCurricular1.setId(null);
        assertThat(disciplinaCurricular1).isNotEqualTo(disciplinaCurricular2);
    }
}
