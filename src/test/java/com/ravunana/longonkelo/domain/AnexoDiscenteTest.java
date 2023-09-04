package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoDiscenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoDiscente.class);
        AnexoDiscente anexoDiscente1 = new AnexoDiscente();
        anexoDiscente1.setId(1L);
        AnexoDiscente anexoDiscente2 = new AnexoDiscente();
        anexoDiscente2.setId(anexoDiscente1.getId());
        assertThat(anexoDiscente1).isEqualTo(anexoDiscente2);
        anexoDiscente2.setId(2L);
        assertThat(anexoDiscente1).isNotEqualTo(anexoDiscente2);
        anexoDiscente1.setId(null);
        assertThat(anexoDiscente1).isNotEqualTo(anexoDiscente2);
    }
}
