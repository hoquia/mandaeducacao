package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EncarregadoEducacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncarregadoEducacaoDTO.class);
        EncarregadoEducacaoDTO encarregadoEducacaoDTO1 = new EncarregadoEducacaoDTO();
        encarregadoEducacaoDTO1.setId(1L);
        EncarregadoEducacaoDTO encarregadoEducacaoDTO2 = new EncarregadoEducacaoDTO();
        assertThat(encarregadoEducacaoDTO1).isNotEqualTo(encarregadoEducacaoDTO2);
        encarregadoEducacaoDTO2.setId(encarregadoEducacaoDTO1.getId());
        assertThat(encarregadoEducacaoDTO1).isEqualTo(encarregadoEducacaoDTO2);
        encarregadoEducacaoDTO2.setId(2L);
        assertThat(encarregadoEducacaoDTO1).isNotEqualTo(encarregadoEducacaoDTO2);
        encarregadoEducacaoDTO1.setId(null);
        assertThat(encarregadoEducacaoDTO1).isNotEqualTo(encarregadoEducacaoDTO2);
    }
}
