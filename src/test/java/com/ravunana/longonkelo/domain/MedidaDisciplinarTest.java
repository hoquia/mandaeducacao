package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedidaDisciplinarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedidaDisciplinar.class);
        MedidaDisciplinar medidaDisciplinar1 = new MedidaDisciplinar();
        medidaDisciplinar1.setId(1L);
        MedidaDisciplinar medidaDisciplinar2 = new MedidaDisciplinar();
        medidaDisciplinar2.setId(medidaDisciplinar1.getId());
        assertThat(medidaDisciplinar1).isEqualTo(medidaDisciplinar2);
        medidaDisciplinar2.setId(2L);
        assertThat(medidaDisciplinar1).isNotEqualTo(medidaDisciplinar2);
        medidaDisciplinar1.setId(null);
        assertThat(medidaDisciplinar1).isNotEqualTo(medidaDisciplinar2);
    }
}
