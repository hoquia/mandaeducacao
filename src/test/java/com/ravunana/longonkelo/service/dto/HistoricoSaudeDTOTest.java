package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoricoSaudeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoricoSaudeDTO.class);
        HistoricoSaudeDTO historicoSaudeDTO1 = new HistoricoSaudeDTO();
        historicoSaudeDTO1.setId(1L);
        HistoricoSaudeDTO historicoSaudeDTO2 = new HistoricoSaudeDTO();
        assertThat(historicoSaudeDTO1).isNotEqualTo(historicoSaudeDTO2);
        historicoSaudeDTO2.setId(historicoSaudeDTO1.getId());
        assertThat(historicoSaudeDTO1).isEqualTo(historicoSaudeDTO2);
        historicoSaudeDTO2.setId(2L);
        assertThat(historicoSaudeDTO1).isNotEqualTo(historicoSaudeDTO2);
        historicoSaudeDTO1.setId(null);
        assertThat(historicoSaudeDTO1).isNotEqualTo(historicoSaudeDTO2);
    }
}
