package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoDissertacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoDissertacaoDTO.class);
        EstadoDissertacaoDTO estadoDissertacaoDTO1 = new EstadoDissertacaoDTO();
        estadoDissertacaoDTO1.setId(1L);
        EstadoDissertacaoDTO estadoDissertacaoDTO2 = new EstadoDissertacaoDTO();
        assertThat(estadoDissertacaoDTO1).isNotEqualTo(estadoDissertacaoDTO2);
        estadoDissertacaoDTO2.setId(estadoDissertacaoDTO1.getId());
        assertThat(estadoDissertacaoDTO1).isEqualTo(estadoDissertacaoDTO2);
        estadoDissertacaoDTO2.setId(2L);
        assertThat(estadoDissertacaoDTO1).isNotEqualTo(estadoDissertacaoDTO2);
        estadoDissertacaoDTO1.setId(null);
        assertThat(estadoDissertacaoDTO1).isNotEqualTo(estadoDissertacaoDTO2);
    }
}
