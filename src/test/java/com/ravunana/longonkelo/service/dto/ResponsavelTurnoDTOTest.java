package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelTurnoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelTurnoDTO.class);
        ResponsavelTurnoDTO responsavelTurnoDTO1 = new ResponsavelTurnoDTO();
        responsavelTurnoDTO1.setId(1L);
        ResponsavelTurnoDTO responsavelTurnoDTO2 = new ResponsavelTurnoDTO();
        assertThat(responsavelTurnoDTO1).isNotEqualTo(responsavelTurnoDTO2);
        responsavelTurnoDTO2.setId(responsavelTurnoDTO1.getId());
        assertThat(responsavelTurnoDTO1).isEqualTo(responsavelTurnoDTO2);
        responsavelTurnoDTO2.setId(2L);
        assertThat(responsavelTurnoDTO1).isNotEqualTo(responsavelTurnoDTO2);
        responsavelTurnoDTO1.setId(null);
        assertThat(responsavelTurnoDTO1).isNotEqualTo(responsavelTurnoDTO2);
    }
}
