package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelTurmaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelTurmaDTO.class);
        ResponsavelTurmaDTO responsavelTurmaDTO1 = new ResponsavelTurmaDTO();
        responsavelTurmaDTO1.setId(1L);
        ResponsavelTurmaDTO responsavelTurmaDTO2 = new ResponsavelTurmaDTO();
        assertThat(responsavelTurmaDTO1).isNotEqualTo(responsavelTurmaDTO2);
        responsavelTurmaDTO2.setId(responsavelTurmaDTO1.getId());
        assertThat(responsavelTurmaDTO1).isEqualTo(responsavelTurmaDTO2);
        responsavelTurmaDTO2.setId(2L);
        assertThat(responsavelTurmaDTO1).isNotEqualTo(responsavelTurmaDTO2);
        responsavelTurmaDTO1.setId(null);
        assertThat(responsavelTurmaDTO1).isNotEqualTo(responsavelTurmaDTO2);
    }
}
