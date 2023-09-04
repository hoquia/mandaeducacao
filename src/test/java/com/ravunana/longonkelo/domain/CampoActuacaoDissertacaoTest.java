package com.ravunana.longonkelo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CampoActuacaoDissertacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampoActuacaoDissertacao.class);
        CampoActuacaoDissertacao campoActuacaoDissertacao1 = new CampoActuacaoDissertacao();
        campoActuacaoDissertacao1.setId(1L);
        CampoActuacaoDissertacao campoActuacaoDissertacao2 = new CampoActuacaoDissertacao();
        campoActuacaoDissertacao2.setId(campoActuacaoDissertacao1.getId());
        assertThat(campoActuacaoDissertacao1).isEqualTo(campoActuacaoDissertacao2);
        campoActuacaoDissertacao2.setId(2L);
        assertThat(campoActuacaoDissertacao1).isNotEqualTo(campoActuacaoDissertacao2);
        campoActuacaoDissertacao1.setId(null);
        assertThat(campoActuacaoDissertacao1).isNotEqualTo(campoActuacaoDissertacao2);
    }
}
