package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrecoEmolumentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecoEmolumento.class);
        PrecoEmolumento precoEmolumento1 = new PrecoEmolumento();
        precoEmolumento1.setId(1L);
        PrecoEmolumento precoEmolumento2 = new PrecoEmolumento();
        precoEmolumento2.setId(precoEmolumento1.getId());
        assertThat(precoEmolumento1).isEqualTo(precoEmolumento2);
        precoEmolumento2.setId(2L);
        assertThat(precoEmolumento1).isNotEqualTo(precoEmolumento2);
        precoEmolumento1.setId(null);
        assertThat(precoEmolumento1).isNotEqualTo(precoEmolumento2);
    }
}
