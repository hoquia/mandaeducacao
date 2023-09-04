package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProvedorNotificacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProvedorNotificacaoDTO.class);
        ProvedorNotificacaoDTO provedorNotificacaoDTO1 = new ProvedorNotificacaoDTO();
        provedorNotificacaoDTO1.setId(1L);
        ProvedorNotificacaoDTO provedorNotificacaoDTO2 = new ProvedorNotificacaoDTO();
        assertThat(provedorNotificacaoDTO1).isNotEqualTo(provedorNotificacaoDTO2);
        provedorNotificacaoDTO2.setId(provedorNotificacaoDTO1.getId());
        assertThat(provedorNotificacaoDTO1).isEqualTo(provedorNotificacaoDTO2);
        provedorNotificacaoDTO2.setId(2L);
        assertThat(provedorNotificacaoDTO1).isNotEqualTo(provedorNotificacaoDTO2);
        provedorNotificacaoDTO1.setId(null);
        assertThat(provedorNotificacaoDTO1).isNotEqualTo(provedorNotificacaoDTO2);
    }
}
