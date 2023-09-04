package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanoDescontoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoDesconto.class);
        PlanoDesconto planoDesconto1 = new PlanoDesconto();
        planoDesconto1.setId(1L);
        PlanoDesconto planoDesconto2 = new PlanoDesconto();
        planoDesconto2.setId(planoDesconto1.getId());
        assertThat(planoDesconto1).isEqualTo(planoDesconto2);
        planoDesconto2.setId(2L);
        assertThat(planoDesconto1).isNotEqualTo(planoDesconto2);
        planoDesconto1.setId(null);
        assertThat(planoDesconto1).isNotEqualTo(planoDesconto2);
    }
}
