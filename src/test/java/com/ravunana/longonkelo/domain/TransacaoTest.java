package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transacao.class);
        Transacao transacao1 = new Transacao();
        transacao1.setId(1L);
        Transacao transacao2 = new Transacao();
        transacao2.setId(transacao1.getId());
        assertThat(transacao1).isEqualTo(transacao2);
        transacao2.setId(2L);
        assertThat(transacao1).isNotEqualTo(transacao2);
        transacao1.setId(null);
        assertThat(transacao1).isNotEqualTo(transacao2);
    }
}
