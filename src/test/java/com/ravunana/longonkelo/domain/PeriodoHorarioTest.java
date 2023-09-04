package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodoHorarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoHorario.class);
        PeriodoHorario periodoHorario1 = new PeriodoHorario();
        periodoHorario1.setId(1L);
        PeriodoHorario periodoHorario2 = new PeriodoHorario();
        periodoHorario2.setId(periodoHorario1.getId());
        assertThat(periodoHorario1).isEqualTo(periodoHorario2);
        periodoHorario2.setId(2L);
        assertThat(periodoHorario1).isNotEqualTo(periodoHorario2);
        periodoHorario1.setId(null);
        assertThat(periodoHorario1).isNotEqualTo(periodoHorario2);
    }
}
