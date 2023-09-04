package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EncarregadoEducacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncarregadoEducacao.class);
        EncarregadoEducacao encarregadoEducacao1 = new EncarregadoEducacao();
        encarregadoEducacao1.setId(1L);
        EncarregadoEducacao encarregadoEducacao2 = new EncarregadoEducacao();
        encarregadoEducacao2.setId(encarregadoEducacao1.getId());
        assertThat(encarregadoEducacao1).isEqualTo(encarregadoEducacao2);
        encarregadoEducacao2.setId(2L);
        assertThat(encarregadoEducacao1).isNotEqualTo(encarregadoEducacao2);
        encarregadoEducacao1.setId(null);
        assertThat(encarregadoEducacao1).isNotEqualTo(encarregadoEducacao2);
    }
}
