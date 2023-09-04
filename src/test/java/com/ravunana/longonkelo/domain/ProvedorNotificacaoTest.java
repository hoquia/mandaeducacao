package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProvedorNotificacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProvedorNotificacao.class);
        ProvedorNotificacao provedorNotificacao1 = new ProvedorNotificacao();
        provedorNotificacao1.setId(1L);
        ProvedorNotificacao provedorNotificacao2 = new ProvedorNotificacao();
        provedorNotificacao2.setId(provedorNotificacao1.getId());
        assertThat(provedorNotificacao1).isEqualTo(provedorNotificacao2);
        provedorNotificacao2.setId(2L);
        assertThat(provedorNotificacao1).isNotEqualTo(provedorNotificacao2);
        provedorNotificacao1.setId(null);
        assertThat(provedorNotificacao1).isNotEqualTo(provedorNotificacao2);
    }
}
