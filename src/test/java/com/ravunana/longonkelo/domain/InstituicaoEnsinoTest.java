package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InstituicaoEnsinoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstituicaoEnsino.class);
        InstituicaoEnsino instituicaoEnsino1 = new InstituicaoEnsino();
        instituicaoEnsino1.setId(1L);
        InstituicaoEnsino instituicaoEnsino2 = new InstituicaoEnsino();
        instituicaoEnsino2.setId(instituicaoEnsino1.getId());
        assertThat(instituicaoEnsino1).isEqualTo(instituicaoEnsino2);
        instituicaoEnsino2.setId(2L);
        assertThat(instituicaoEnsino1).isNotEqualTo(instituicaoEnsino2);
        instituicaoEnsino1.setId(null);
        assertThat(instituicaoEnsino1).isNotEqualTo(instituicaoEnsino2);
    }
}
