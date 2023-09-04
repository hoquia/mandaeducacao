package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeioPagamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeioPagamento.class);
        MeioPagamento meioPagamento1 = new MeioPagamento();
        meioPagamento1.setId(1L);
        MeioPagamento meioPagamento2 = new MeioPagamento();
        meioPagamento2.setId(meioPagamento1.getId());
        assertThat(meioPagamento1).isEqualTo(meioPagamento2);
        meioPagamento2.setId(2L);
        assertThat(meioPagamento1).isNotEqualTo(meioPagamento2);
        meioPagamento1.setId(null);
        assertThat(meioPagamento1).isNotEqualTo(meioPagamento2);
    }
}
