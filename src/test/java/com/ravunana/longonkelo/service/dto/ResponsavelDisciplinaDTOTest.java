package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelDisciplinaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelDisciplinaDTO.class);
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO1 = new ResponsavelDisciplinaDTO();
        responsavelDisciplinaDTO1.setId(1L);
        ResponsavelDisciplinaDTO responsavelDisciplinaDTO2 = new ResponsavelDisciplinaDTO();
        assertThat(responsavelDisciplinaDTO1).isNotEqualTo(responsavelDisciplinaDTO2);
        responsavelDisciplinaDTO2.setId(responsavelDisciplinaDTO1.getId());
        assertThat(responsavelDisciplinaDTO1).isEqualTo(responsavelDisciplinaDTO2);
        responsavelDisciplinaDTO2.setId(2L);
        assertThat(responsavelDisciplinaDTO1).isNotEqualTo(responsavelDisciplinaDTO2);
        responsavelDisciplinaDTO1.setId(null);
        assertThat(responsavelDisciplinaDTO1).isNotEqualTo(responsavelDisciplinaDTO2);
    }
}
