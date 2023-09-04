package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcessoSelectivoMatriculaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessoSelectivoMatricula.class);
        ProcessoSelectivoMatricula processoSelectivoMatricula1 = new ProcessoSelectivoMatricula();
        processoSelectivoMatricula1.setId(1L);
        ProcessoSelectivoMatricula processoSelectivoMatricula2 = new ProcessoSelectivoMatricula();
        processoSelectivoMatricula2.setId(processoSelectivoMatricula1.getId());
        assertThat(processoSelectivoMatricula1).isEqualTo(processoSelectivoMatricula2);
        processoSelectivoMatricula2.setId(2L);
        assertThat(processoSelectivoMatricula1).isNotEqualTo(processoSelectivoMatricula2);
        processoSelectivoMatricula1.setId(null);
        assertThat(processoSelectivoMatricula1).isNotEqualTo(processoSelectivoMatricula2);
    }
}
