package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodoLancamentoNotaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodoLancamentoNotaDTO.class);
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO1 = new PeriodoLancamentoNotaDTO();
        periodoLancamentoNotaDTO1.setId(1L);
        PeriodoLancamentoNotaDTO periodoLancamentoNotaDTO2 = new PeriodoLancamentoNotaDTO();
        assertThat(periodoLancamentoNotaDTO1).isNotEqualTo(periodoLancamentoNotaDTO2);
        periodoLancamentoNotaDTO2.setId(periodoLancamentoNotaDTO1.getId());
        assertThat(periodoLancamentoNotaDTO1).isEqualTo(periodoLancamentoNotaDTO2);
        periodoLancamentoNotaDTO2.setId(2L);
        assertThat(periodoLancamentoNotaDTO1).isNotEqualTo(periodoLancamentoNotaDTO2);
        periodoLancamentoNotaDTO1.setId(null);
        assertThat(periodoLancamentoNotaDTO1).isNotEqualTo(periodoLancamentoNotaDTO2);
    }
}
