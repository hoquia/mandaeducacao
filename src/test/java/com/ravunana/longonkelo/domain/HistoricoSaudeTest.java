package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoricoSaudeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricoSaude.class);
        HistoricoSaude historicoSaude1 = new HistoricoSaude();
        historicoSaude1.setId(1L);
        HistoricoSaude historicoSaude2 = new HistoricoSaude();
        historicoSaude2.setId(historicoSaude1.getId());
        assertThat(historicoSaude1).isEqualTo(historicoSaude2);
        historicoSaude2.setId(2L);
        assertThat(historicoSaude1).isNotEqualTo(historicoSaude2);
        historicoSaude1.setId(null);
        assertThat(historicoSaude1).isNotEqualTo(historicoSaude2);
    }
}
