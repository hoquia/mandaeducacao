package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodoHorarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoHorarioDTO.class);
        PeriodoHorarioDTO periodoHorarioDTO1 = new PeriodoHorarioDTO();
        periodoHorarioDTO1.setId(1L);
        PeriodoHorarioDTO periodoHorarioDTO2 = new PeriodoHorarioDTO();
        assertThat(periodoHorarioDTO1).isNotEqualTo(periodoHorarioDTO2);
        periodoHorarioDTO2.setId(periodoHorarioDTO1.getId());
        assertThat(periodoHorarioDTO1).isEqualTo(periodoHorarioDTO2);
        periodoHorarioDTO2.setId(2L);
        assertThat(periodoHorarioDTO1).isNotEqualTo(periodoHorarioDTO2);
        periodoHorarioDTO1.setId(null);
        assertThat(periodoHorarioDTO1).isNotEqualTo(periodoHorarioDTO2);
    }
}
