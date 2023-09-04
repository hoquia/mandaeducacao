package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NaturezaTrabalhoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NaturezaTrabalho.class);
        NaturezaTrabalho naturezaTrabalho1 = new NaturezaTrabalho();
        naturezaTrabalho1.setId(1L);
        NaturezaTrabalho naturezaTrabalho2 = new NaturezaTrabalho();
        naturezaTrabalho2.setId(naturezaTrabalho1.getId());
        assertThat(naturezaTrabalho1).isEqualTo(naturezaTrabalho2);
        naturezaTrabalho2.setId(2L);
        assertThat(naturezaTrabalho1).isNotEqualTo(naturezaTrabalho2);
        naturezaTrabalho1.setId(null);
        assertThat(naturezaTrabalho1).isNotEqualTo(naturezaTrabalho2);
    }
}
