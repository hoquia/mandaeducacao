package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LicaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Licao.class);
        Licao licao1 = new Licao();
        licao1.setId(1L);
        Licao licao2 = new Licao();
        licao2.setId(licao1.getId());
        assertThat(licao1).isEqualTo(licao2);
        licao2.setId(2L);
        assertThat(licao1).isNotEqualTo(licao2);
        licao1.setId(null);
        assertThat(licao1).isNotEqualTo(licao2);
    }
}
