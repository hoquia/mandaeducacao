package com.ravunana.longonkelo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ravunana.longonkelo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelAreaFormacaoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelAreaFormacaoDTO.class);
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO1 = new ResponsavelAreaFormacaoDTO();
        responsavelAreaFormacaoDTO1.setId(1L);
        ResponsavelAreaFormacaoDTO responsavelAreaFormacaoDTO2 = new ResponsavelAreaFormacaoDTO();
        assertThat(responsavelAreaFormacaoDTO1).isNotEqualTo(responsavelAreaFormacaoDTO2);
        responsavelAreaFormacaoDTO2.setId(responsavelAreaFormacaoDTO1.getId());
        assertThat(responsavelAreaFormacaoDTO1).isEqualTo(responsavelAreaFormacaoDTO2);
        responsavelAreaFormacaoDTO2.setId(2L);
        assertThat(responsavelAreaFormacaoDTO1).isNotEqualTo(responsavelAreaFormacaoDTO2);
        responsavelAreaFormacaoDTO1.setId(null);
        assertThat(responsavelAreaFormacaoDTO1).isNotEqualTo(responsavelAreaFormacaoDTO2);
    }
}
