package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumoAcademicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumoAcademico.class);
        ResumoAcademico resumoAcademico1 = new ResumoAcademico();
        resumoAcademico1.setId(1L);
        ResumoAcademico resumoAcademico2 = new ResumoAcademico();
        resumoAcademico2.setId(resumoAcademico1.getId());
        assertThat(resumoAcademico1).isEqualTo(resumoAcademico2);
        resumoAcademico2.setId(2L);
        assertThat(resumoAcademico1).isNotEqualTo(resumoAcademico2);
        resumoAcademico1.setId(null);
        assertThat(resumoAcademico1).isNotEqualTo(resumoAcademico2);
    }
}
