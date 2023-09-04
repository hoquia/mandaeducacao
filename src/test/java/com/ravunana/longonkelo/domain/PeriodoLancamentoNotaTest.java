package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodoLancamentoNotaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoLancamentoNota.class);
        PeriodoLancamentoNota periodoLancamentoNota1 = new PeriodoLancamentoNota();
        periodoLancamentoNota1.setId(1L);
        PeriodoLancamentoNota periodoLancamentoNota2 = new PeriodoLancamentoNota();
        periodoLancamentoNota2.setId(periodoLancamentoNota1.getId());
        assertThat(periodoLancamentoNota1).isEqualTo(periodoLancamentoNota2);
        periodoLancamentoNota2.setId(2L);
        assertThat(periodoLancamentoNota1).isNotEqualTo(periodoLancamentoNota2);
        periodoLancamentoNota1.setId(null);
        assertThat(periodoLancamentoNota1).isNotEqualTo(periodoLancamentoNota2);
    }
}
