package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelCursoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelCursoDTO.class);
        ResponsavelCursoDTO responsavelCursoDTO1 = new ResponsavelCursoDTO();
        responsavelCursoDTO1.setId(1L);
        ResponsavelCursoDTO responsavelCursoDTO2 = new ResponsavelCursoDTO();
        assertThat(responsavelCursoDTO1).isNotEqualTo(responsavelCursoDTO2);
        responsavelCursoDTO2.setId(responsavelCursoDTO1.getId());
        assertThat(responsavelCursoDTO1).isEqualTo(responsavelCursoDTO2);
        responsavelCursoDTO2.setId(2L);
        assertThat(responsavelCursoDTO1).isNotEqualTo(responsavelCursoDTO2);
        responsavelCursoDTO1.setId(null);
        assertThat(responsavelCursoDTO1).isNotEqualTo(responsavelCursoDTO2);
    }
}
