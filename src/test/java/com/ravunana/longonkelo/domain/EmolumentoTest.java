package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmolumentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emolumento.class);
        Emolumento emolumento1 = new Emolumento();
        emolumento1.setId(1L);
        Emolumento emolumento2 = new Emolumento();
        emolumento2.setId(emolumento1.getId());
        assertThat(emolumento1).isEqualTo(emolumento2);
        emolumento2.setId(2L);
        assertThat(emolumento1).isNotEqualTo(emolumento2);
        emolumento1.setId(null);
        assertThat(emolumento1).isNotEqualTo(emolumento2);
    }
}
