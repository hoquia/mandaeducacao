package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AplicacaoReciboDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AplicacaoReciboDTO.class);
        AplicacaoReciboDTO aplicacaoReciboDTO1 = new AplicacaoReciboDTO();
        aplicacaoReciboDTO1.setId(1L);
        AplicacaoReciboDTO aplicacaoReciboDTO2 = new AplicacaoReciboDTO();
        assertThat(aplicacaoReciboDTO1).isNotEqualTo(aplicacaoReciboDTO2);
        aplicacaoReciboDTO2.setId(aplicacaoReciboDTO1.getId());
        assertThat(aplicacaoReciboDTO1).isEqualTo(aplicacaoReciboDTO2);
        aplicacaoReciboDTO2.setId(2L);
        assertThat(aplicacaoReciboDTO1).isNotEqualTo(aplicacaoReciboDTO2);
        aplicacaoReciboDTO1.setId(null);
        assertThat(aplicacaoReciboDTO1).isNotEqualTo(aplicacaoReciboDTO2);
    }
}
