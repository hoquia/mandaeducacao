package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TurnoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TurnoDTO.class);
        TurnoDTO turnoDTO1 = new TurnoDTO();
        turnoDTO1.setId(1L);
        TurnoDTO turnoDTO2 = new TurnoDTO();
        assertThat(turnoDTO1).isNotEqualTo(turnoDTO2);
        turnoDTO2.setId(turnoDTO1.getId());
        assertThat(turnoDTO1).isEqualTo(turnoDTO2);
        turnoDTO2.setId(2L);
        assertThat(turnoDTO1).isNotEqualTo(turnoDTO2);
        turnoDTO1.setId(null);
        assertThat(turnoDTO1).isNotEqualTo(turnoDTO2);
    }
}
