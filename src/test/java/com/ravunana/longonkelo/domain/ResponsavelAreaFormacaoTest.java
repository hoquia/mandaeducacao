package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelAreaFormacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelAreaFormacao.class);
        ResponsavelAreaFormacao responsavelAreaFormacao1 = new ResponsavelAreaFormacao();
        responsavelAreaFormacao1.setId(1L);
        ResponsavelAreaFormacao responsavelAreaFormacao2 = new ResponsavelAreaFormacao();
        responsavelAreaFormacao2.setId(responsavelAreaFormacao1.getId());
        assertThat(responsavelAreaFormacao1).isEqualTo(responsavelAreaFormacao2);
        responsavelAreaFormacao2.setId(2L);
        assertThat(responsavelAreaFormacao1).isNotEqualTo(responsavelAreaFormacao2);
        responsavelAreaFormacao1.setId(null);
        assertThat(responsavelAreaFormacao1).isNotEqualTo(responsavelAreaFormacao2);
    }
}
