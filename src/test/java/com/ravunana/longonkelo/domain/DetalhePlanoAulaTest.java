package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetalhePlanoAulaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalhePlanoAula.class);
        DetalhePlanoAula detalhePlanoAula1 = new DetalhePlanoAula();
        detalhePlanoAula1.setId(1L);
        DetalhePlanoAula detalhePlanoAula2 = new DetalhePlanoAula();
        detalhePlanoAula2.setId(detalhePlanoAula1.getId());
        assertThat(detalhePlanoAula1).isEqualTo(detalhePlanoAula2);
        detalhePlanoAula2.setId(2L);
        assertThat(detalhePlanoAula1).isNotEqualTo(detalhePlanoAula2);
        detalhePlanoAula1.setId(null);
        assertThat(detalhePlanoAula1).isNotEqualTo(detalhePlanoAula2);
    }
}
