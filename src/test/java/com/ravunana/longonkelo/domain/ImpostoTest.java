package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImpostoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imposto.class);
        Imposto imposto1 = new Imposto();
        imposto1.setId(1L);
        Imposto imposto2 = new Imposto();
        imposto2.setId(imposto1.getId());
        assertThat(imposto1).isEqualTo(imposto2);
        imposto2.setId(2L);
        assertThat(imposto1).isNotEqualTo(imposto2);
        imposto1.setId(null);
        assertThat(imposto1).isNotEqualTo(imposto2);
    }
}
