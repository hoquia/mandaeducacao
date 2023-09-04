package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanoMultaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoMulta.class);
        PlanoMulta planoMulta1 = new PlanoMulta();
        planoMulta1.setId(1L);
        PlanoMulta planoMulta2 = new PlanoMulta();
        planoMulta2.setId(planoMulta1.getId());
        assertThat(planoMulta1).isEqualTo(planoMulta2);
        planoMulta2.setId(2L);
        assertThat(planoMulta1).isNotEqualTo(planoMulta2);
        planoMulta1.setId(null);
        assertThat(planoMulta1).isNotEqualTo(planoMulta2);
    }
}
