package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CampoActuacaoDissertacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CampoActuacaoDissertacaoDTO.class);
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO1 = new CampoActuacaoDissertacaoDTO();
        campoActuacaoDissertacaoDTO1.setId(1L);
        CampoActuacaoDissertacaoDTO campoActuacaoDissertacaoDTO2 = new CampoActuacaoDissertacaoDTO();
        assertThat(campoActuacaoDissertacaoDTO1).isNotEqualTo(campoActuacaoDissertacaoDTO2);
        campoActuacaoDissertacaoDTO2.setId(campoActuacaoDissertacaoDTO1.getId());
        assertThat(campoActuacaoDissertacaoDTO1).isEqualTo(campoActuacaoDissertacaoDTO2);
        campoActuacaoDissertacaoDTO2.setId(2L);
        assertThat(campoActuacaoDissertacaoDTO1).isNotEqualTo(campoActuacaoDissertacaoDTO2);
        campoActuacaoDissertacaoDTO1.setId(null);
        assertThat(campoActuacaoDissertacaoDTO1).isNotEqualTo(campoActuacaoDissertacaoDTO2);
    }
}
