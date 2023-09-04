package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiscenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscenteDTO.class);
        DiscenteDTO discenteDTO1 = new DiscenteDTO();
        discenteDTO1.setId(1L);
        DiscenteDTO discenteDTO2 = new DiscenteDTO();
        assertThat(discenteDTO1).isNotEqualTo(discenteDTO2);
        discenteDTO2.setId(discenteDTO1.getId());
        assertThat(discenteDTO1).isEqualTo(discenteDTO2);
        discenteDTO2.setId(2L);
        assertThat(discenteDTO1).isNotEqualTo(discenteDTO2);
        discenteDTO1.setId(null);
        assertThat(discenteDTO1).isNotEqualTo(discenteDTO2);
    }
}
