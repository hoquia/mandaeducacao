package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcessoSelectivoMatriculaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessoSelectivoMatriculaDTO.class);
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO1 = new ProcessoSelectivoMatriculaDTO();
        processoSelectivoMatriculaDTO1.setId(1L);
        ProcessoSelectivoMatriculaDTO processoSelectivoMatriculaDTO2 = new ProcessoSelectivoMatriculaDTO();
        assertThat(processoSelectivoMatriculaDTO1).isNotEqualTo(processoSelectivoMatriculaDTO2);
        processoSelectivoMatriculaDTO2.setId(processoSelectivoMatriculaDTO1.getId());
        assertThat(processoSelectivoMatriculaDTO1).isEqualTo(processoSelectivoMatriculaDTO2);
        processoSelectivoMatriculaDTO2.setId(2L);
        assertThat(processoSelectivoMatriculaDTO1).isNotEqualTo(processoSelectivoMatriculaDTO2);
        processoSelectivoMatriculaDTO1.setId(null);
        assertThat(processoSelectivoMatriculaDTO1).isNotEqualTo(processoSelectivoMatriculaDTO2);
    }
}
