package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NivelEnsinoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NivelEnsino.class);
        NivelEnsino nivelEnsino1 = new NivelEnsino();
        nivelEnsino1.setId(1L);
        NivelEnsino nivelEnsino2 = new NivelEnsino();
        nivelEnsino2.setId(nivelEnsino1.getId());
        assertThat(nivelEnsino1).isEqualTo(nivelEnsino2);
        nivelEnsino2.setId(2L);
        assertThat(nivelEnsino1).isNotEqualTo(nivelEnsino2);
        nivelEnsino1.setId(null);
        assertThat(nivelEnsino1).isNotEqualTo(nivelEnsino2);
    }
}
