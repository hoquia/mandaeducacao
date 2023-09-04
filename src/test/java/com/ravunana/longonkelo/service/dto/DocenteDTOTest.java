package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocenteDTO.class);
        DocenteDTO docenteDTO1 = new DocenteDTO();
        docenteDTO1.setId(1L);
        DocenteDTO docenteDTO2 = new DocenteDTO();
        assertThat(docenteDTO1).isNotEqualTo(docenteDTO2);
        docenteDTO2.setId(docenteDTO1.getId());
        assertThat(docenteDTO1).isEqualTo(docenteDTO2);
        docenteDTO2.setId(2L);
        assertThat(docenteDTO1).isNotEqualTo(docenteDTO2);
        docenteDTO1.setId(null);
        assertThat(docenteDTO1).isNotEqualTo(docenteDTO2);
    }
}
