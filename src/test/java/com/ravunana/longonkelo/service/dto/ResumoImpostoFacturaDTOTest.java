package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumoImpostoFacturaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumoImpostoFacturaDTO.class);
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO1 = new ResumoImpostoFacturaDTO();
        resumoImpostoFacturaDTO1.setId(1L);
        ResumoImpostoFacturaDTO resumoImpostoFacturaDTO2 = new ResumoImpostoFacturaDTO();
        assertThat(resumoImpostoFacturaDTO1).isNotEqualTo(resumoImpostoFacturaDTO2);
        resumoImpostoFacturaDTO2.setId(resumoImpostoFacturaDTO1.getId());
        assertThat(resumoImpostoFacturaDTO1).isEqualTo(resumoImpostoFacturaDTO2);
        resumoImpostoFacturaDTO2.setId(2L);
        assertThat(resumoImpostoFacturaDTO1).isNotEqualTo(resumoImpostoFacturaDTO2);
        resumoImpostoFacturaDTO1.setId(null);
        assertThat(resumoImpostoFacturaDTO1).isNotEqualTo(resumoImpostoFacturaDTO2);
    }
}
