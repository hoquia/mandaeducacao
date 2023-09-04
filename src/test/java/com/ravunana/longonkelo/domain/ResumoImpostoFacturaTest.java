package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumoImpostoFacturaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumoImpostoFactura.class);
        ResumoImpostoFactura resumoImpostoFactura1 = new ResumoImpostoFactura();
        resumoImpostoFactura1.setId(1L);
        ResumoImpostoFactura resumoImpostoFactura2 = new ResumoImpostoFactura();
        resumoImpostoFactura2.setId(resumoImpostoFactura1.getId());
        assertThat(resumoImpostoFactura1).isEqualTo(resumoImpostoFactura2);
        resumoImpostoFactura2.setId(2L);
        assertThat(resumoImpostoFactura1).isNotEqualTo(resumoImpostoFactura2);
        resumoImpostoFactura1.setId(null);
        assertThat(resumoImpostoFactura1).isNotEqualTo(resumoImpostoFactura2);
    }
}
