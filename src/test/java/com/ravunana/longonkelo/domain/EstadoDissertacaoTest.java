package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoDissertacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoDissertacao.class);
        EstadoDissertacao estadoDissertacao1 = new EstadoDissertacao();
        estadoDissertacao1.setId(1L);
        EstadoDissertacao estadoDissertacao2 = new EstadoDissertacao();
        estadoDissertacao2.setId(estadoDissertacao1.getId());
        assertThat(estadoDissertacao1).isEqualTo(estadoDissertacao2);
        estadoDissertacao2.setId(2L);
        assertThat(estadoDissertacao1).isNotEqualTo(estadoDissertacao2);
        estadoDissertacao1.setId(null);
        assertThat(estadoDissertacao1).isNotEqualTo(estadoDissertacao2);
    }
}
